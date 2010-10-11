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
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * The FormattedProperties class is an extension
 * of {@link Properties} to allow retention of comment
 * lines and blank (whitespace only) lines in the
 * properties file.
 * <br/>
 * <br/>
 * Written for Java version 1.5
 * <p>
 * <b>Warning</b>: Some method are not (yet?) supported.
 * </p>
 */
public class FormattedProperties
    extends Properties
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private Lines lines = new Lines();

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
                        "ISO-8859-1"
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
                super.put( keyString, line.substring(pos) );
                lines.addKey(keyString);
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
            super.put(keyString, element.toString());
            lines.addKey(keyString);
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
                new OutputStreamWriter(out, "ISO-8859-1")
                );

        store(writer, comment);
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
     * Calls the store(OutputStream out, String comment) method and
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
        StringBuilder   sb = new StringBuilder();

        for( Line line:lines ) {
            if( line.isComment ) {
                // was a blank or comment line, so just restore it
                writer.println (line.content);
            }
            else {
                // This is a 'property' line, so rebuild it
                formatForOutput(line.content, sb, true);
                sb.append ('=');
                formatForOutput( (String)get(line.content), sb, false );
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
     */
    protected void formatForOutput(
            String          str,
            StringBuilder   buffer,
            boolean         key
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
                default:
                    if( isISO_8859_1( c ) ) {
                        buffer.append(c);
                    }
                    else {
                        String hex = Integer.toHexString(c);
                        buffer.append("\\u0000".substring(0, 6 - hex.length()));
                        buffer.append(hex);
                        }
            }

            if(c != ' ') {
                head = key;
            }
        }
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

//    /**
//     * Add a Property to the end of the
//     * FormattedProperties.
//     *
//     * @param   keyString    The Property key.
//     * @param   value        The value of this Property.
//     */
//    public String add(String keyString, String value)
//    {
//        return String.class.cast(
//                this.put(keyString, value)
//                );
//    }

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
     * Add a comment or blank line or comment to
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
     * Call {@link #put(Object,Object)}
     */
    @Override
    public synchronized Object setProperty( String key, String value )
    {
        return this.put(key,value);
    }


    /**
     * Clears this hashtable so that it contains no keys,
     * but also clears comments
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
     * return an unmodifiable Set
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
     * return an unmodifiable Set
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

    /* (non-Javadoc)
     * @see java.util.Hashtable#putAll(java.util.Map)
     */
    @Override
    public synchronized void putAll( Map<? extends Object, ? extends Object> t )
    {
        // super.putAll( t );
        for(Map.Entry<? extends Object, ? extends Object> e:t.entrySet()) {
            put(    e.getKey(),
                    e.getValue()
                    );
        }
    }

    /* (non-Javadoc)
     * @see java.util.Hashtable#remove(java.lang.Object)
     */
    /**
     * @throws UnsupportedOperationException
     */
    @Override
    public synchronized Object remove( Object key )
    {
        // TODO must be modifiable
        // return super.remove( key );
        throw new UnsupportedOperationException();
    }

    /**
     * Unlike {@link java.util.Hashtable#values()}
     * return an unmodifiable Collection
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

//  /* (non-Javadoc)
//  * @see java.util.Hashtable#equals(java.lang.Object)
//  */
// @Override
// public synchronized boolean equals( Object o )
// {
//     // TODO Auto-generated method stub
//     return super.equals( o );
// }

// /* (non-Javadoc)
//  * @see java.util.Hashtable#hashCode()
//  */
// @Override
// public synchronized int hashCode()
// {
//     // TODO Auto-generated method stub
//     return super.hashCode();
// }

//    /* (non-Javadoc)
//     * @see java.util.Hashtable#clone()
//     */
//    @Override
//    public synchronized Object clone()
//    {
//        // TODO Auto-generated method stub
//        return super.clone();
//    }
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
    }
    // ---------------------------------------------

    // ---------------------------------------------
    class Lines implements Iterable<Line>,
                           Serializable
    {
        private static final long serialVersionUID = 1L;
        private ArrayList<Line> lines = new ArrayList<Line> ();

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
        @Override
        public Iterator<Line> iterator()
        {
            return lines.iterator();
        }

        public void clear()
        {
            lines.clear();
        }
    }
    // ---------------------------------------------

    public static void main(String[] args)
    {
        for(char c=' '; c<128; c++ ) {
            System.out.print(c);
        }
        System.out.println();
        for(char c=128; c<256; c++ ) {
            System.out.print(c);
        }
        System.out.println();
    }
}
