package cx.ath.choisnet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * The FormattedProperties class is an extension
 * of {@link Properties} to allow retention of comment
 * lines and blank (whitespace only) lines in the
 * properties file.
 * <BR>
 * <BR>
 * Written for Java version 1.5
 */
public final class FormattedProperties
    extends Properties
{
    private static final long serialVersionUID = 1L;
    /** {@value} */
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1"; // $codepro.audit.disable constantNamingConvention
    protected static final Pattern PATTERN_BR_ADD_BEFORE = Pattern.compile("<[bB][rR][^/]*[/]?>[^\\n].*",Pattern.DOTALL);
    protected static final Pattern PATTERN_BR_ADD_AFTER = Pattern.compile(".*[^\\n]<[bB][rR][^/]*[/]?>",Pattern.DOTALL);
    protected static final Pattern PATTERN_P_BEGIN_ADD_BEFORE = Pattern.compile("<[pP][^/]*[/]?>[^\\n].*",Pattern.DOTALL);
    protected static final Pattern PATTERN_P_END_ADD_AFTER = Pattern.compile(".*[^\\n]</[pP]>",Pattern.DOTALL);
    /** @serial */
    private final FormattedPropertiesLines lines = new FormattedPropertiesLines(this);
    /** @serial */
    private final EnumSet<Store> storeOptions;

    /**
     * Configure store operation.
     */
    public enum Store {
        /**
         * Cut line after character 0x10 (\n)
         * <BR>
         * <BR>
         * More formally replace 0x10 by String "\\n\\\n"
         */
        CUT_LINE_AFTER_NEW_LINE,

        /**
         * Cut line after &lt;br&gt; or &lt;br/&gt;
         * <BR>
         * <BR>
         * More formally looking for &lt;br *[/]&gt; and
         * cut line after this value
         */
        CUT_LINE_AFTER_HTML_BR,

        /**
         * Cut line before &lt;br&gt; or &lt;br/&gt;
         * <BR>
         * <BR>
         * More formally looking for &lt;br *[/]&gt; and
         * cut line before this value
         */
        CUT_LINE_BEFORE_HTML_BR,

        /**
         * Cut line before &lt;p&gt;
         */
        CUT_LINE_BEFORE_HTML_BEGIN_P,

        /**
         * Cut line after &lt;/p&gt;
         */
        CUT_LINE_AFTER_HTML_END_P
    }

    /**
     * Creates an empty property list with
     * no default values. Does not configure
     * specifics {@link Store storeOptions}
     */
    public FormattedProperties()
    {
        this(null,null);
    }

    /**
     * Creates an empty property list with
     * no default values.
     *
     * @param storeOptions Configure how store operation will be
     *                     handle, see {@link Store}.
     */
    public FormattedProperties( final Set<Store> storeOptions )
    {
        this(null,storeOptions);
    }

    /**
     * Creates an empty property list
     * with the specified defaults. Does
     * not configure specifics {@link Store storeOptions}
     *
     * @param defaults defaults values
     */
    public FormattedProperties( final Properties defaults )
    {
        this(defaults,null);
    }

    /**
     * Creates an empty property list
     * with the specified defaults.
     *
     * @param defaults     The defaults
     * @param storeOptions Configure how store operation will be
     *                     handle, see {@link Store}.
     */
    public FormattedProperties(
        final Properties  defaults,
        final Set<Store>  storeOptions
        )
    {
        super(defaults);

        this.storeOptions = storeOptions == null ? null : EnumSet.copyOf( storeOptions );
    }

    /**
     * Load properties from the specified InputStream.
     * <p>
     * Overload the load method in Properties so we can
     * keep comment and blank lines. According to Properties
     * specifications presume ISO-8859-1 encoding.
     * </p>
     * @param in The InputStream to read.
     * @see #load(Reader)
     */
    @Override
    public synchronized void load( final InputStream in) throws IOException
    {
        // The specifications says that the file must
        // be encoded using ISO-8859-1.
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        in,
                        ENCODING_ISO_8859_1
                        )
                );
        load( reader );
    }

    /**
     * Load properties from the specified Reader.
     * <p>
     * Overload the load method in Properties so we can
     * keep comment and blank lines.
     * </p>
     * @param aReader The Reader to read.
     */
    @Override
    public synchronized void load( final Reader aReader ) throws IOException
    {
        try( final BufferedReader reader = FormattedPropertiesHelper.toBufferedReader( aReader ) ) {
            String line;

            while ((line = reader.readLine()) != null) {
                load( line, reader );
            }
        }
    }

    @SuppressWarnings({
        "squid:MethodCyclomaticComplexity",
        "squid:S1151", // switch size is OK here
        "squid:S134", // Deep conditions are OK here
        "null"})
    private void load( final String currentLine, final BufferedReader reader )
        throws IOException
    {
        String  line = currentLine;
        char    c    = 0;
        int     pos  = 0;

        // Leading whitespace must be deleted first.
        final int lineLength = line.length();
        while ( (pos < lineLength)
                && Character.isWhitespace(c = line.charAt(pos))) {
            pos++;
            }

        // If empty line or begins with a comment character, save this line
        // in lineData and save a "" in keyData.
        if (    ((lineLength - pos) == 0)
                || (line.charAt(pos) == '#')
                || (line.charAt(pos) == '!')) {
            addCommentLine(line);
            return;
            }

        // The characters up to the next Whitespace, ':', or '='
        // describe the key.  But look for escape sequences.
        // Try to short-circuit when there is no escape char.
        final int     start       = pos;
        final boolean needsEscape = line.indexOf('\\', pos) != -1;
        final StringBuilder key = needsEscape ? new StringBuilder() : null; // $codepro.audit.disable avoidInstantiationInLoops

        while ( (pos < lineLength)
                && ! Character.isWhitespace(c = line.charAt(pos++))
                && (c != '=') && (c != ':')) {
            if (needsEscape && (c == '\\')) {
                if (pos == lineLength) {
                    // The line continues on the next line.  If there
                    // is no next line, just treat it as a key with an
                    // empty value.
                    line = readLine( reader );
                    pos = 0;
                    while( (pos < lineLength)
                            && Character.isWhitespace(c = line.charAt(pos))) {
                        pos++;
                        }
                    }
                else {
                    c = line.charAt(pos++);
                    switch(c) {
                        case 'n':
                            key.append('\n');
                            break;
                        case 't':
                            key.append('\t');
                            break;
                        case 'r':
                            key.append('\r');
                            break;
                        case 'u':
                            if ((pos + 4) <= lineLength) {
                                final char uni = (char) Integer.parseInt
                                           (line.substring(pos, pos + 4), 16);
                                key.append(uni);
                                pos += 4;
                                }   // else throw exception?
                            break;
                        default:
                            key.append(c);
                            break;
                        }
                    }
                }
            else if(needsEscape) {
                key.append(c);
                }
            }

        final boolean isDelim = (c == ':') || (c == '=');
        String  keyString;

        if( needsEscape ) {
            keyString = key.toString();
            }
        else if( isDelim || Character.isWhitespace(c) ) {
            keyString = line.substring(start, pos - 1);
            }
        else {
            keyString = line.substring(start, pos);
            }

        while ( (pos < lineLength)
                && Character.isWhitespace(c = line.charAt(pos))) {
            pos++;
            }

        if (! isDelim && ((c == ':') || (c == '='))) {
            pos++;
            while ( (pos < lineLength)
                    && Character.isWhitespace( line.charAt(pos) ) ) {
                pos++;
                }
            }

        // Short-circuit if no escape chars found.
        if( !needsEscape ) {
            this.put( keyString, line.substring(pos) );
            }
        else {
            // Escape char found so iterate through the rest of the line.
            final StringBuilder element = FormattedPropertiesHelper.handleEscapeChar( reader, line, pos );

            this.put(keyString, element.toString());
        }
    }

    private static String readLine( final BufferedReader reader ) throws IOException
    {
        String line = reader.readLine();

        if(line == null) {
            line = StringHelper.EMPTY;
            }
        return line;
    }

    /**
     * Write the properties to the specified OutputStream.
     * <p>
     * Overloads the store method in Properties so we can
     * put back comment and blank lines.
     * </p>
     * @param out       The OutputStream to write to.
     * @param comment   Ignored, here for compatibility w/ Properties.
     * @exception IOException if writing this property list to the
     *            specified output stream throws an IOException.
     */
    @Override
    public synchronized void store(final OutputStream out, final String comment)
        throws IOException
    {
        // The spec says that the file must be encoded using ISO-8859-1.
        final PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(
                        out,
                        ENCODING_ISO_8859_1
                        )
                );

        store(writer,this.storeOptions);
    }

    /**
     * Same has {@link #store(OutputStream, String)} but suppresses
     * IOExceptions that were thrown.
     *
     * @deprecated This method does not throw an IOException if
     *             an I/O error occurs while saving the property
     *             list. The preferred way to save a properties
     *             list is via the store(OutputStream out, String comments)
     *             method.
     * <p>
     * Calls the store(out,comment,null) and
     * suppresses IOExceptions that were thrown.
     * </p>
     */
    @Override
    @Deprecated
    @SuppressWarnings("squid:S1133") // Required by API
    public synchronized void save( final OutputStream out, final String comment )
    {
        try {
            store(out,comment);
            }
        catch( final IOException cause ) {
            throw new FormattedPropertiesRuntimeException( cause ); // Show exception to the world :)
            }
    }

    /**
     * Write the properties to the specified Writer.
     * <p>
     * Overloads the store method in Properties so we can
     * put back comment and blank lines.
     * </p>
     * @param out       The OutputStream to write to.
     * @param comment   Ignored, here for compatibility w/ Properties.
     * @exception IOException if writing this property list to the
     *            specified writer throws an IOException.
     */
    @Override
    public synchronized void store( final Writer out, final String comment )
        throws IOException
    {
        store(out,this.storeOptions);
    }

    /**
     * Write the properties to the specified Writer.
     * <p>
     * Overloads the store method in Properties so we can
     * put back comment and blank lines.
     * </p>
     * @param out       The OutputStream to write to.
     * @param config    Configure how store operation will be
     *                  handle, see {@link Store}.
     *                  Overwrite storeOptions initialized
     *                  by some constructors.
     * @exception IOException if writing this property list to the
     *            specified writer throws an IOException.
     */
    public synchronized void store(
        final Writer      out,
        final Set<Store>  config
        ) throws IOException
    {
        final EnumSet<Store> attribs;

        if( config == null ) {
            attribs = EnumSet.noneOf( Store.class );
            }
        else {
            attribs = EnumSet.copyOf( config );
            }

        try( final PrintWriter writer = FormattedPropertiesHelper.toPrintWriter( out ) ) {
            storeInternal( writer, attribs );
            writer.flush ();
        }
    }

    private void storeInternal(final PrintWriter writer, final EnumSet<Store> attribs  )
    {
        // We ignore the header, because if we prepend a
        // commented header then read it back in it is
        // now a comment, which will be saved and then
        // when we write again we would prepend Another
        // header...
        final StringBuilder sb = new StringBuilder();

        for( final FormattedPropertiesLine line : this.lines ) {
            if( line.isComment() ) {
                // was a blank or comment line, so just restore it
                writer.println(line.getContent());
                }
            else {
                // This is a 'property' line, so rebuild it
                formatForOutput(
                        line.getContent(),
                        sb,
                        true,
                        null
                        );
                sb.append ('=');
                formatForOutput(
                        String.class.cast(
                                get(line.getContent())
                                ),
                        sb,
                        false,
                        attribs
                        );
                writer.println( sb );
                }
            }
    }

    /**
     * Format value for output.
     *
     * @param str    - the string to format
     * @param buffer - buffer to hold the string
     * @param isKey  - true if 'str' the key is formatted,
     *                 false if the value is formatted
     * @param config - Configure how store operation will be
     *                  handle, see {@link Store}.
     */
    @SuppressWarnings({
        "squid:MethodCyclomaticComplexity",
        "squid:S1066",
        "squid:S1151", // switch size is OK : this an automate
        "squid:S134", // Deep conditions are OK here
        "squid:S128", // Allow to not use break in switch
        "squid:ForLoopCounterChangedCheck"
        })
    protected void formatForOutput(
        final String            str,
        final StringBuilder     buffer,
        final boolean           isKey,
        final EnumSet<Store>    config
        )
    {
        if( isKey ) {
            buffer.setLength(0);
            buffer.ensureCapacity(str.length());
            }
        else {
            buffer.ensureCapacity(buffer.length() + str.length());
            }

        // If 'str' contains one \n we don't add extra \n (already formatted)
        final boolean isAlreadyFormatted = str.contains( "\n" );

        boolean head = true;
        final int     size = str.length();

        for( int i = 0; i < size; i++ ) {
            final char c = str.charAt(i);
            switch(c) {
                case '\n':
                    buffer.append("\\n");
                    if( !isKey ) {
                        if( ! isAlreadyFormatted && config.contains( Store.CUT_LINE_AFTER_NEW_LINE) ) {
                            buffer.append("\\\n");
                            }
                        }
                    break;
                case '\r':
                    buffer.append("\\r");
                    break;
                case '\t':
                    buffer.append("\\t");
                    break;
                case ' ':
                    buffer.append(head ? "\\ " : " ");
                    break;
                case '\\':
                case '!':
                case '#':
                case '=':
                case ':':
                    buffer.append('\\').append(c);
                    break;
                case '<':
                    if( !isKey ) {
                        if( !isAlreadyFormatted ) {
                            boolean cutLine = false;
                            if( config.contains( Store.CUT_LINE_BEFORE_HTML_BR )) {
                                if( FormattedPropertiesHelper.matches(PATTERN_BR_ADD_BEFORE,str.substring( i )) ) {
                                    cutLine = true;
                                    }
                                }
                            if( config.contains( Store.CUT_LINE_BEFORE_HTML_BEGIN_P )) {
                                if( FormattedPropertiesHelper.matches(PATTERN_P_BEGIN_ADD_BEFORE,str.substring( i )) ) {
                                    cutLine = true;
                                    }
                                }

                            if( cutLine ) {
                                buffer.append( "\\\n" );
                                }
                            }
                    }
                default:
                    if( FormattedPropertiesHelper.isISO_8859_1( c ) ) {
                        buffer.append(c);
                        }
                    else {
                        final String hex = Integer.toHexString(c);
                        buffer.append("\\u0000".substring(0, 6 - hex.length()));
                        buffer.append(hex);
                        }

                    if( !isKey && (c == '>') ) {
                        if( !isAlreadyFormatted ) {
                            boolean cutLine = false;

                            if(config.contains( Store.CUT_LINE_AFTER_HTML_BR )) {
                                if( FormattedPropertiesHelper.matches(PATTERN_BR_ADD_AFTER,str.substring(0,i+1)) ) {
                                    cutLine = true;
                                    }
                                }
                            if(config.contains( Store.CUT_LINE_AFTER_HTML_END_P )) {
                                if( FormattedPropertiesHelper.matches(PATTERN_P_END_ADD_AFTER,str.substring(0,i+1)) ) {
                                    cutLine = true;
                                    }
                                }

                            if( cutLine ) {
                                // To keep Properties consistent,
                                // must add all next Whitespaces
                                // before add new line
                                int i2 = i + 1;

                                while( i2 < size ) {
                                    final char c2 = str.charAt(i2);
                                    if( Character.isWhitespace(c2) ) {
                                        buffer.append( c2 );
                                        i++;
                                        i2++;
                                        }
                                    else {
                                        break;
                                        }
                                    }

                                buffer.append( "\\\n" );
                                }
                            }
                        }
                }

            if(c != ' ') {
                head = isKey;
                }
            }
    }


    /**
     * If Property already define, replace value
     * and return old value. If Property is not
     * define, add Property to the end of the
     * FormattedProperties.
     * <p>
     * Only support String types.
     * </p>
     * @param  keyString   The Property key.
     * @param  valueString The value of this Property.
     * @throws IllegalArgumentException if key or value are not
     *         Strings
     */
    @Override
    @SuppressWarnings({
        "squid:MethodCyclomaticComplexity",
        "squid:RedundantThrowsDeclarationCheck"})
    public synchronized Object put(
        final Object keyString,
        final Object valueString
        ) throws IllegalArgumentException
    {
        if( !(keyString instanceof String) ) {
            throw new IllegalArgumentException(
                    "Support only String for key"
                    );
            }
        if( !(valueString instanceof String) ) {
            throw new IllegalArgumentException(
                    "Support only String for key"
                    );
            }
        if( super.containsKey( keyString ) ) {
            final Object prev = super.put( keyString, valueString );

            if( prev == null ) {
                throw new FormattedPropertiesRuntimeException(
                        "Internal error can't find key in Properties"
                        );
                }
            if( !this.lines.contains( keyString ) ) {
                throw new FormattedPropertiesRuntimeException(
                        "Internal error key exist in Hashmap, but not in lines"
                        );
                }
            return prev;
            }
        else {
            if( this.lines.contains( keyString ) ) {
                throw new FormattedPropertiesRuntimeException(
                        "Internal error key found in lines but not in Hashmap"
                        );
                }

            // Not found add entry
            super.put( keyString, valueString );
            this.lines.addKey(
                    String.class.cast(keyString)
                    );
            return null;
        }
    }

    /**
     * Add a comment or blank line to
     * the end of the FormattedProperties.
     *
     * @param line  The string to add to the end,
     *              make sure this is a comment
     *              or a 'whitespace' line.
     * @throws IllegalArgumentException if line is not
     *         a comment line.
     */
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public void addCommentLine( final String line )
        throws IllegalArgumentException
    {
        final String trimLine = line.trim();

        if( ! trimLine.isEmpty() ) {
            final char firstChar = trimLine.charAt( 0 );

            if( (firstChar != '!') && (firstChar != '#') ) {
                throw new IllegalArgumentException("Must be a comment line [" + line + ']');
                }
            }

        this.lines.addCommentLine( line );
    }

    /**
     * Add a blank line or comment to
     * the end of the FormattedProperties.
     */
    public void addBlankLine()
    {
        this.lines.addCommentLine( StringHelper.EMPTY );
    }

    /**
     * @return an unmodifiable {@link List} of {@link FormattedPropertiesLine}
     */
    public List<FormattedPropertiesLine> getLines()
    {
        return Collections.unmodifiableList( this.lines.getLines() );
    }
    // ---------------------------------------------

    /**
     * Same has {@link #put(Object,Object)}
     */
    @Override
    public synchronized Object setProperty( final String key, final String value )
    {
        return this.put( key, value );
    }

    /**
     * Clears this hashtable that contains properties,
     * and also clears comments
     */
    @Override
    public synchronized void clear()
    {
        super.clear();
        this.lines.clear();
    }

    /**
     * Unlike {@link java.util.Hashtable#entrySet()}
     * return an <b>unmodifiable</b> Set
     * @see java.util.Hashtable#entrySet()
     */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet()
    {
        return Collections.unmodifiableSet(
                super.entrySet()
                );
    }

    /**
     * According to {@link Properties#isEmpty()} return
     * true if there is no Key define, ignore
     * existing comment lines
     * @see java.util.Hashtable#isEmpty()
     */
    @Override
    @SuppressWarnings("squid:S1185") // Override just for documentation
    public synchronized boolean isEmpty()
    {
        return super.isEmpty();
    }

    /**
     * Unlike {@link java.util.Hashtable#keySet()}
     * return an <b>unmodifiable</b> Set
     * @see java.util.Hashtable#keySet()
     */
    @Override
    public Set<Object> keySet()
    {
        return Collections.unmodifiableSet(
                super.keySet()
                );
    }

    /**
     * Copies all of the mappings from the specified map
     * to properties hashtable. These mappings will replace
     * any mappings that this hashtable had for any of the
     * keys currently in the specified map.
     *
     * @param t - mappings to be stored in this map
     * @throws NullPointerException - if the specified map is
     *         null
     * @see #put(Object, Object)
     */
    @Override
    public synchronized void putAll( final Map<? extends Object, ? extends Object> t )
    {
        for( final Map.Entry<? extends Object, ? extends Object> e:t.entrySet() ) {
            put( e.getKey(), e.getValue() );
            }
    }

    /**
     * <p>
     * Removes the mapping for a key from this FormattedProperties
     * if it is present (optional operation).
     * </p>
     * <p>
     * Returns the value to which this map previously associated
     * the key, or null if the FormattedProperties contained no
     * mapping for the key.
     * </p>
     * @param key - key whose mapping is to be removed from
     *        the FormattedProperties
     * @return the previous value associated with key,
     *         or null if there was no mapping for key.
     * @throws ClassCastException - if the key is of an
     *         inappropriate type for this map (optional)
     * @throws NullPointerException - if the specified key
     *         is null.
     */
    @Override
    public synchronized Object remove( final Object key )
    {
        if( super.containsKey( key ) ) {
            final FormattedPropertiesLine line = this.lines.remove(key);
            final Object                  prev = super.remove( key );

            if( line == null ) {
                throw new FormattedPropertiesRuntimeException(
                        "Internal error key exist in Hashmap, but not in lines"
                        );
                }
            else {
                return prev;
                }
        }
        else {
            if( this.lines.contains( key ) ) {
                throw new FormattedPropertiesRuntimeException(
                        "Internal error key exist in lines, but not in Hashmap"
                        );
                }
            else {
                return null; // nothing to do !
                }
            }
    }

    /**
     * Unlike super class {@link java.util.Hashtable#values()} return an <b>unmodifiable</b> Collection
     * @see java.util.Hashtable#values()
     */
    @Override
    public Collection<Object> values()
    {
        return Collections.unmodifiableCollection(
                super.values()
                );
    }

    @Override
    public synchronized int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = (prime * result) + ((this.lines == null) ? 0 : this.lines.hashCode());
        return result;
    }

    /**
     * Compares the specified Object with this
     * FormattedProperties for equality.
     *
     * @param obj - object to be compared for equality
     *              with this FormattedProperties
     * @return true if the specified Object is equal
     *         to this FormattedProperties
     */
    @Override
    public synchronized boolean equals( final Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
    {
        if( this == obj ) {
            return true;
            }
        if( !super.equals( obj ) ) {
            return false;
            }
        if( getClass() != obj.getClass() ) { // $codepro.audit.disable useEquals
            return false;
            }
        final FormattedProperties other = (FormattedProperties)obj;

        if( this.lines == null ) {
            if( other.lines != null ) {
                return false;
                }
            }
        else if( !this.lines.equals( other.lines ) ) {
            return false;
            }
        return true;
    }

    /**
     * Creates a copy of this FormattedProperties.
     * All the structure of the FormattedProperties
     * itself is copied, <b>include</b> the keys,
     * the values, and stored lines informations.
     * <BR>
     * <BR>
     * This break rules from {@link java.util.Hashtable#clone()}.
     * <BR>
     * <BR>
     *
     * @return a clone of the FormattedProperties
     * @see #newFormattedProperties(FormattedProperties)
     */
    @Override
    @SuppressWarnings({"squid:S2975","squid:S1182"})
    public synchronized Object clone()
    {
        return newFormattedProperties( this );
    }

    /**
     * Copy a {@link FormattedProperties}
     * @param source {@link FormattedProperties} to clone
     * @return a new {@link FormattedProperties}
     */
    public static FormattedProperties newFormattedProperties(
        final FormattedProperties source
        )
    {
        final FormattedProperties clone;

        synchronized( source ) {
            clone = new FormattedProperties(
                    source.defaults,
                    source.storeOptions
                    );

                for( final FormattedPropertiesLine line : source.lines ) {
                    if( line.isComment() ) {
                        clone.addCommentLine( line.getContent() );
                        }
                    else {
                        clone.put(
                            line.getContent(),
                            source.getProperty( line.getContent() )
                            );
                        }
                    }
            }

        return clone;
    }

    // ---------------------------------------------
    // ---------------------------------------------

}
