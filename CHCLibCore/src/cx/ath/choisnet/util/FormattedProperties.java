package cx.ath.choisnet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The FormattedProperties class is an extension
 * of {@link Properties} to allow retention of comment
 * lines and blank (whitespace only) lines in the
 * properties file.
 * <br/>
 * <br/>
 * Written for Java version 1.5
 */
public class FormattedProperties
    extends Properties
{
    private static final long serialVersionUID = 1L;
    /** {@value} */
    final static public String ENCODING_ISO_8859_1 = "ISO-8859-1";
    /** {@value} */
    final static protected Pattern PATTERN_BR_ADD_BEFORE = Pattern.compile("<[bB][rR][^/]*[/]?>.*");
    /** {@value} */
    final static protected Pattern PATTERN_BR_ADD_AFTER = Pattern.compile(".*<[bB][rR][^/]*[/]?>");
    /** {@value} */
    final static protected Pattern PATTERN_P_BEGIN_ADD_BEFORE = Pattern.compile("<[pP][^/]*[/]?>.*");
    /** {@value} */
    final static protected Pattern PATTERN_P_END_ADD_AFTER = Pattern.compile(".*</[pP]>");
    /** @serial */
    private Lines lines = new Lines();

//    public final static void main(String[]args)
//    {
//        Matcher m = PATTERN_P_BEGIN_ADD_BEFORE.matcher("<p style='k'/>sdfdsf");
//        boolean b = m.matches();
//        System.out.println("b="+b);
//    }
    /**
     * Configure store operation.
     *
     * @author Claude CHOISNET
     */
    public enum Store {
        /**
         * Cut line after character 0x10 (\n)
         * <br/>
         * <br/>
         * More formally replace 0x10 by String "\\n\\\n"
         */
        CUT_LINE_AFTER_NEW_LINE,

        /**
         * Cut line after &lt;br&gt; or &lt;br/&gt;
         * <br/>
         * <br/>
         * More formally looking for &lt;br *[/]&gt; and
         * cut line after this value
         */
        CUT_LINE_AFTER_HTML_BR,

        /**
         * Cut line before &lt;br&gt; or &lt;br/&gt;
         * <br/>
         * <br/>
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
    };

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
    public synchronized void load(InputStream in) throws IOException
    {
        // The specifications says that the file must
        // be encoded using ISO-8859-1.
        BufferedReader reader = new BufferedReader(
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
    public synchronized void load( Reader aReader ) throws IOException
    {
        BufferedReader  reader;
        String          line;

        if( aReader instanceof BufferedReader ) {
            reader = BufferedReader.class.cast( aReader );
        }
        else {
            reader = new BufferedReader( aReader );
        }

        while ((line = reader.readLine()) != null) {
            char    c   = 0;
            int     pos = 0;

            // Leading whitespace must be deleted first.
            while ( pos < line.length()
                    && Character.isWhitespace(c = line.charAt(pos))) {
                pos++;
            }

            // If empty line or begins with a comment character, save this line
            // in lineData and save a "" in keyData.
            if (    (line.length() - pos) == 0
                    || line.charAt(pos) == '#'
                    || line.charAt(pos) == '!') {
                addCommentLine(line);
                continue;
            }

            // The characters up to the next Whitespace, ':', or '='
            // describe the key.  But look for escape sequences.
            // Try to short-circuit when there is no escape char.
            int     start       = pos;
            boolean needsEscape = line.indexOf('\\', pos) != -1;
            StringBuilder key = needsEscape ? new StringBuilder() : null;

            while ( pos < line.length()
                    && ! Character.isWhitespace(c = line.charAt(pos++))
                    && c != '=' && c != ':') {
                if (needsEscape && c == '\\') {
                    if (pos == line.length()) {
                        // The line continues on the next line.  If there
                        // is no next line, just treat it as a key with an
                        // empty value.
                        line = reader.readLine();
                        if(line == null) {
                            line = "";
                        }
                        pos = 0;
                        while( pos < line.length()
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
                                if (pos + 4 <= line.length()) {
                                    char uni = (char) Integer.parseInt
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

            boolean isDelim = (c == ':' || c == '=');
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

            while ( pos < line.length()
                    && Character.isWhitespace(c = line.charAt(pos))) {
                pos++;
            }

            if (! isDelim && (c == ':' || c == '=')) {
                pos++;
                while ( pos < line.length()
                        && Character.isWhitespace(c = line.charAt(pos))) {
                    pos++;
                }
            }

            // Short-circuit if no escape chars found.
            if( !needsEscape ) {
                //super.put( keyString, line.substring(pos) );
                //lines.addKey(keyString);
                this.put( keyString, line.substring(pos) );
                continue;
            }

            // Escape char found so iterate through the rest of the line.
            StringBuilder element = new StringBuilder(line.length() - pos);
            while (pos < line.length()) {
                c = line.charAt(pos++);
                if (c == '\\') {
                    if (pos == line.length()) {
                        // The line continues on the next line.
                        line = reader.readLine();

                        // We might have seen a backslash at the end of
                        // the file.  The JDK ignores the backslash in
                        // this case, so we follow for compatibility.
                        if (line == null)
                            break;

                        pos = 0;
                        while ( pos < line.length()
                                && Character.isWhitespace(c = line.charAt(pos)))
                            pos++;
                        element.ensureCapacity(line.length() - pos +
                                               element.length());
                    }
                    else {
                        c = line.charAt(pos++);
                        switch (c) {
                            case 'n':
                                element.append('\n');
                                break;
                            case 't':
                                element.append('\t');
                                break;
                            case 'r':
                                element.append('\r');
                                break;
                            case 'u':
                                if (pos + 4 <= line.length()) {
                                    char uni = (char) Integer.parseInt
                                               (line.substring(pos, pos + 4), 16);
                                    element.append(uni);
                                    pos += 4;
                                }   // else throw exception?
                                break;
                            default:
                                element.append(c);
                                break;
                        }
                    }
                } else
                    element.append(c);
            }
            //super.put(keyString, element.toString());
            //lines.addKey(keyString);
            this.put(keyString, element.toString());
        }
    }

    /**
     * Write the properties to the specified OutputStream.
     * <p>
     * Overloads the store method in Properties so we can
     * put back comment and blank lines.
     * </p>
     * @param out       The OutputStream to write to.
     * @param comment   Ignored, here for compatibility w/ Properties.
     *
     * @exception IOException
     */
    @Override
    public synchronized void store(OutputStream out, String comment)
        throws IOException
    {
        // The spec says that the file must be encoded using ISO-8859-1.
        PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(
                        out, 
                        ENCODING_ISO_8859_1
                        )
                );

        store(writer,EnumSet.noneOf( Store.class ));
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
    public synchronized void save( OutputStream out, String comment )
    {
        try {
            store(out,comment);
        }
        catch( IOException e ) {
            e.printStackTrace();
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
     *
     * @exception IOException
     */
    @Override
    public synchronized void store( Writer out, String comment )
        throws IOException
    {
        store(out,EnumSet.noneOf( Store.class ));
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
         *
         * @exception IOException
         */
        public synchronized void store(
                Writer          out,
                EnumSet<Store>  config
                )
            throws IOException
    {
        EnumSet<Store> attribs;

        if( config == null ) {
            attribs = EnumSet.noneOf( Store.class );
        }
        else {
            attribs = EnumSet.copyOf( config );
        }

        PrintWriter writer;

        if( out instanceof PrintWriter ) {
            writer = PrintWriter.class.cast( out );
        }
        else {
            writer = new PrintWriter( out );
        }
        // We ignore the header, because if we prepend a
        // commented header then read it back in it is
        // now a comment, which will be saved and then
        // when we write again we would prepend Another
        // header...
        StringBuilder sb = new StringBuilder();

        for( Line line:lines ) {
            if( line.isComment ) {
                // was a blank or comment line, so just restore it
                writer.println(line.content);
            }
            else {
                // This is a 'property' line, so rebuild it
                formatForOutput(
                        line.content,
                        sb,
                        true,
                        null
                        );
                sb.append ('=');
                formatForOutput(
                        String.class.cast(
                                get(line.content)
                                ),
                        sb,
                        false,
                        attribs
                        );
                writer.println (sb);
            }
        }
        writer.flush ();
    }

    /**
     * Format value for output.
     *
     * @param str    - the string to format
     * @param buffer - buffer to hold the string
     * @param key    - true if 'str' the key is formatted,
     *                 false if the value is formatted
     * @param config - Configure how store operation will be
     *                  handle, see {@link Store}.
     */
    protected void formatForOutput(
            String          str,
            StringBuilder   buffer,
            boolean         key,
            EnumSet<Store>  config
            )
    {
        if( key ) {
            buffer.setLength(0);
            buffer.ensureCapacity(str.length());
            }
        else {
            buffer.ensureCapacity(buffer.length() + str.length());
            }

        boolean head = true;
        int     size = str.length();

        for( int i = 0; i < size; i++ ) {
            char c = str.charAt(i);
            switch(c) {
                case '\n':
                    buffer.append("\\n");
                    if( !key ) {
                        if( config.contains( Store.CUT_LINE_AFTER_NEW_LINE) ) {
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
                    if( !key ) {
                        boolean cutLine = false;
                        if( config.contains( Store.CUT_LINE_BEFORE_HTML_BR )) {
                            if( matches(PATTERN_BR_ADD_BEFORE,str.substring( i )) ) {
                                cutLine = true;
                            }
                        }
                        if( config.contains( Store.CUT_LINE_BEFORE_HTML_BEGIN_P )) {
                            if( matches(PATTERN_P_BEGIN_ADD_BEFORE,str.substring( i )) ) {
                                cutLine = true;
                            }
                        }

                        if( cutLine ) {
                            buffer.append( "\\\n" );
                        }
                    }
                default:
                    if( isISO_8859_1( c ) ) {
                        buffer.append(c);
                    }
                    else {
                        String hex = Integer.toHexString(c);
                        buffer.append("\\u0000".substring(0, 6 - hex.length()));
                        buffer.append(hex);
                        }

                    if( !key && c == '>' ) {
                        boolean cutLine = false;

                        if(config.contains( Store.CUT_LINE_AFTER_HTML_BR )) {
                            if( matches(PATTERN_BR_ADD_AFTER,str.substring(0,i+1)) ) {
                                cutLine = true;
                            }
                        }
                        if(config.contains( Store.CUT_LINE_AFTER_HTML_END_P )) {
                            if( matches(PATTERN_P_END_ADD_AFTER,str.substring(0,i+1)) ) {
                                cutLine = true;
                            }
                        }

                        if( cutLine ) {
                            // To keep Properties consistent,
                            // must add all next Whitespaces
                            // before add new line
                            int i2 = i + 1;

                            while( i2 < size ) {
                                char c2 = str.charAt(i2);
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

            if(c != ' ') {
                head = key;
            }
        }
    }

    /**
     *
     * @param p Pattern to use
     * @param s String to test
     * @return true if string matches with pattern
     */
    protected boolean matches(Pattern p,String s)
    {
        return p.matcher( s ).matches();
    }

    /**
     * Determines if the specified character is reasonable
     * value for ISO-8859-1.
     * <br/>
     * This method is call to choose if a character must
     * be encoded or not.
     * <p>
     * White spaces are consider has non reasonable
     * values.
     * </p>
     * @param c character to analyze
     * @return true if character does not need to be encoded,
     *         false otherwise.
     */
    protected boolean isISO_8859_1(char c)
    {
        if (c < 0x20 ) { // before space
            return false;
        }
        if( c > 0x00FF ) {
            return false;
        }
        if( c < 0x7F ) { // last value '~', 0x7F exclude
            return true;
        }
        if( c > 0xA0 ) { // first value '¡', 0xA0 exclude
            return true;
        }

        return false;
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
    public Object put(
            Object keyString,
            Object valueString
            )
        throws IllegalArgumentException
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
            Object prev = super.put( keyString, valueString );

            if( prev == null ) {
                throw new RuntimeException(
                        "Internal error can't find key in Properties"
                        );
                }
            if( !lines.contains( keyString ) ) {
                throw new RuntimeException(
                        "Internal error key exist in Hashmap, but not in lines"
                        );
                }
            return prev;
            }
        else {
            if( lines.contains( keyString ) ) {
                throw new RuntimeException(
                        "Internal error key found in lines but not in Hashmap"
                        );
                }

            // Not found add entry
            super.put( keyString, valueString );
            lines.addKey(
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
    public void addCommentLine(String line)
        throws IllegalArgumentException
    {
        String check = line.trim();

        if( !check.startsWith( "!" )
                && !check.startsWith( "#" )
                && !(check.length() == 0)
                ) {
            throw new IllegalArgumentException("Must be a comment line");
        }

        lines.addCommentLine( line );
    }

    /**
     * Add a blank line or comment to
     * the end of the FormattedProperties.
     */
    public void addBlankLine()
    {
        lines.addCommentLine( "" );
    }

    /**
     * @return an unmodifiable {@link List} of {@link Line}
     */
    public List<Line> getLines()
    {
        return Collections.unmodifiableList( lines.lines );
    }
    // ---------------------------------------------
    // ---------------------------------------------

    /**
     * @see Properties#list(PrintStream)
     */
    @Override
    public void list( PrintStream out )
    {
        super.list( out );
    }

    /**
     * @see Properties#list(PrintWriter)
     */
    @Override
    public void list( PrintWriter out )
    {
        super.list( out );
    }

    /**
     * Same has {@link #put(Object,Object)}
     */
    @Override
    public synchronized Object setProperty( String key, String value )
    {
        return this.put(key,value);
    }

    /**
     * Clears this hashtable that contains properties,
     * and also clears comments
     */
    @Override
    public synchronized void clear()
    {
        super.clear();
        lines.clear();
    }

    /* (non-Javadoc)
     * @see java.util.Hashtable#elements()
     */
    @Override
    public synchronized Enumeration<Object> elements()
    {
        return super.elements();
    }

    /**
     * Unlike {@link java.util.Hashtable#entrySet()}
     * return an <b>unmodifiable</b> Set
     * @see java.util.Hashtable#entrySet()
     */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet()
    {
        // TODO must be modifiable
        // return super.entrySet();
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
        // TODO must be modifiable
        // return super.keySet();
        return Collections.unmodifiableSet(
                super.keySet()
                );
    }

    /* (non-Javadoc)
     * @see java.util.Hashtable#keys()
     */
    @Override
    public synchronized Enumeration<Object> keys()
    {
        return super.keys();
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
    public synchronized void putAll( Map<? extends Object, ? extends Object> t )
    {
        for(Map.Entry<? extends Object, ? extends Object> e:t.entrySet()) {
            put(    e.getKey(),
                    e.getValue()
                    );
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
    public synchronized Object remove( Object key )
    {
        if( super.containsKey( key ) ) {
            Line   line = lines.remove(key);
            Object prev = super.remove( key );

            if( line == null ) {
                throw new RuntimeException(
                        "Internal error key exist in Hashmap, but not in lines"
                        );
            }
            else {
                return prev;
            }
        }
        else {
            if( lines.contains( key ) ) {
                throw new RuntimeException(
                        "Internal error key exist in lines, but not in Hashmap"
                        );
            }
            else {
                return null; // nothing to do !
            }
        }
    }

    /**
     * Unlike {@link java.util.Hashtable#values()}
     * return an <b>unmodifiable</b> Collection
     * @see java.util.Hashtable#values()
     */
    @Override
    public Collection<Object> values()
    {
        // TODO must be modifiable
        return Collections.unmodifiableCollection(
                super.values()
                );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((lines == null) ? 0 : lines.hashCode());
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
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( !super.equals( obj ) ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        FormattedProperties other = (FormattedProperties)obj;

        if( lines == null ) {
            if( other.lines != null ) {
                return false;
            }
        }
        else if( !lines.equals( other.lines ) ) {
            return false;
        }
        return true;
    }

    /**
     * Creates a copy of this FormattedProperties.
     * All the structure of the FormattedProperties
     * itself is copied, <b>include</b> the keys,
     * the values, and stored lines informations.
     * <br/>
     * <br/>
     * This break rules from {@link java.util.Hashtable#clone()}.
     * <br/>
     * <br/>
     *
     *
     * @return a clone of the FormattedProperties
     */
    public synchronized Object clone()
    {
        FormattedProperties clone = new FormattedProperties();

        for( Line line:lines ) {
            if( line.isComment ) {
                clone.addCommentLine( line.content );
            }
            else {
                clone.put(
                        line.content,
                        this.getProperty( line.content )
                        );
            }
        }

        return clone;
    }

    // ---------------------------------------------
    // ---------------------------------------------

    // ---------------------------------------------
    /**
     * Line structure
     */
    public class Line implements Serializable
    {
        private static final long serialVersionUID = 1L;
        /**
         * true if line is a Comment
         * @serial
         */
        boolean isComment;
        /**
         * Full comment line or key if not a comment
         * @serial
         */
        String content;

        private Line()
        { // Can't build line outside this class
        }

        /**
         * @return true if Line is a comment, false otherwise
         */
        public boolean isComment()
        {
            return isComment;
        }
        /**
         * @return the comment if Line is a comment,
         *         null otherwise
         */
        public String getComment()
        {
            if( isComment ) {
                return content;
            }
            return null;
        }
        /**
         * @return return null if Line is a Comment,
         *         return the key property if Line otherwise
         */
        public String getKey()
        {
            if( !isComment ) {
                return content;
            }
            return null;
        }

        /**
         *
         */
        @Override
        public String toString()
        {
            if( isComment ) {
                return content;
            }
            else {
                //TODO: encode?
                return content + "=" + getProperty(content);
            }
        }
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + super.hashCode();
            result = prime * result
                    + ((content == null) ? 0 : content.hashCode());
            result = prime * result + (isComment ? 1231 : 1237);
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals( Object obj )
        {
            if( this == obj ) {
                return true;
            }
            if( obj == null ) {
                return false;
            }
            if( getClass() != obj.getClass() ) {
                return false;
            }
            Line other = (Line)obj;
            if( content == null ) {
                if( other.content != null ) {
                    return false;
                }
            }
            else if( !content.equals( other.content ) ) {
                return false;
            }
            if( isComment != other.isComment ) {
                return false;
            }
            return true;
        }
    }
    // ---------------------------------------------

    // ---------------------------------------------
    class Lines implements Iterable<Line>,
                           Serializable
    {
        private static final long serialVersionUID = 1L;
        private ArrayList<Line> lines = new ArrayList<Line> ();

        private Lines()
        { // Can't build lines outside this class
        }

        private Line buildCommentLine(String comment)
        {
            Line line = new Line();
            line.isComment = true;
            line.content = comment;
            return line;
        }

        private Line buildPropertiesLine(String key)
        {
            Line line = new Line();
            line.isComment = false;
            line.content = key;
            return line;
        }

        public void addKey(String key)
        {
            lines.add( buildPropertiesLine( key ) );
        }

        public void addCommentLine(String comment)
        {
            lines.add( buildCommentLine( comment ) );
        }

        public boolean contains(Object key)
        {
            for(Line line:lines) {
                if( ! line.isComment ) {
                    if( line.content.equals( key )) {
                        return true;
                    }
                }
            }
            return false;
        }

        public Line remove(Object key)
        {
            Iterator<Line> iter = lines.iterator();

            while( iter.hasNext() ) {
                Line line = iter.next();

                if( ! line.isComment ) {
                    if( line.content.equals( key )) {
                        iter.remove();
                        return line;
                    }
                }
            }
            return null;
        }
        @Override
        public Iterator<Line> iterator()
        {
            return lines.iterator();
        }

        public void clear()
        {
            lines.clear();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + super.hashCode();
            result = prime * result + ((lines == null) ? 0 : lines.hashCode());
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals( Object obj )
        {
            if( this == obj ) {
                return true;
            }
            if( obj == null ) {
                return false;
            }
            if( getClass() != obj.getClass() ) {
                return false;
            }
            Lines other = (Lines)obj;

            if( lines == null ) {
                if( other.lines != null ) {
                    return false;
                }
            }
            else if( !lines.equals( other.lines ) ) {
                return false;
            }

            return true;
        }
    }
    // ---------------------------------------------
}
