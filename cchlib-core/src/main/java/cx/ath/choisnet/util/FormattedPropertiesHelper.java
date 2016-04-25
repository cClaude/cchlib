package cx.ath.choisnet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Pattern;

//NOT public
final class FormattedPropertiesHelper {

    private static final int HEXA_LENGTH = 4;
    private static final int HEXA_DECIMAL = 16;
    private static final int SPACE_CHAR = 0x20;

    /**
     * Does not create a new {@link BufferedReader} if <code>aReader</code>
     * is already a {@link BufferedReader}
     * @param aReader {@link Reader} to convert (if needed)
     * @return a {@link BufferedReader} based on <code>aReader</code>
     */
    static BufferedReader toBufferedReader( final Reader aReader )
    {
        if( aReader instanceof BufferedReader ) {
            return BufferedReader.class.cast( aReader );
            }
        else {
            return new BufferedReader( aReader );
            }
    }

    /**
     *
     * @param p Pattern to use
     * @param s String to test
     * @return true if string matches with pattern
     */
    static boolean matches( final Pattern p, final String s )
    {
        return p.matcher( s ).matches();
    }

    /**
     * Does not create a new {@link PrintWriter} if <code>out</code>
     * is already a {@link PrintWriter}
     * @param out {@link Writer} to convert (if needed)
     * @return a {@link PrintWriter} based on <code>out</code>
     */
    static PrintWriter toPrintWriter( final Writer out )
    {
        if( out instanceof PrintWriter ) {
            return PrintWriter.class.cast( out );
            }
        else {
            return new PrintWriter( out );
            }
    }

    /**
     * Determines if the specified character is reasonable value for ISO-8859-1.
     * <br>
     * This method is call to choose if a character must be encoded or not.
     * <p>
     * White spaces are consider has non reasonable values.
     * </p>
     * @param c character to analyze
     * @return true if character does not need to be encoded,
     *         false otherwise.
     */
    static boolean isISO_8859_1( final char c )
    {
        if (c < SPACE_CHAR ) { // before space
            return false;
            }
        else if( c > 0x00FF ) {
            return false;
            }
        else if( c < 0x7F ) { // last value '~', 0x7F exclude
            return true;
            }
        else if( c > 0xA0 ) { // first value, 0xA0 exclude
            return true;
            }

        return false;
    }

    static StringBuilder handleEscapeChar(
        final BufferedReader reader,
        String               line,
        int                  pos
        ) throws IOException, NumberFormatException
    {
        // Escape char found so iterate through the rest of the line.
        final StringBuilder element = new StringBuilder(line.length() - pos);
        char c;

        while (pos < line.length()) {
            c = line.charAt(pos++);
            if (c == '\\') {
                if (pos == line.length()) {
                    // The line continues on the next line.
                    line = reader.readLine();

                    // We might have seen a backslash at the end of
                    // the file.  The JDK ignores the backslash in
                    // this case, so we follow for compatibility.
                    if (line == null) {
                        break;
                        }

                    pos = 0;
                    while ( (pos < line.length()) && Character.isWhitespace(c = line.charAt(pos))) { // NOSONAR
                        pos++;
                        }
                    element.ensureCapacity( (line.length() - pos) + element.length() );
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
                            if( (pos + HEXA_LENGTH) <= line.length() ) {
                                final char uni = (char) Integer.parseInt(line.substring(pos, pos + HEXA_LENGTH), HEXA_DECIMAL);
                                element.append(uni);
                                pos += HEXA_LENGTH;
                                }
                            // else throw exception?
                            break;
                        default:
                            element.append(c);
                            break;
                        }
                    }
                }
            else {
                element.append(c);
                }
            }
        return element;
    }

}
