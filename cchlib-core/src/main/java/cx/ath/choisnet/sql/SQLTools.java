package cx.ath.choisnet.sql;

import java.io.IOException;
import cx.ath.choisnet.util.StringHelper;

/**
 * Conversions tools for create SQL requests.
 *
 * @author Claude CHOISNET
 */
public class SQLTools
{
    //private static Logger slogger = Logger.getLogger( SQLTools.class );
    /**
     * (Expected String,Replace String)
     */
    private final static String[][] REMPLACEPHRASE = {
            {"'", "\\'"},
            {"\\", "\\\\"}
        };

    private SQLTools()
    {
        // All static !:
    }

    /**
     * Returns convert raw String (from user or database) to be usable as
     * an SQL String.
     * <p>
     * See {@link #parseFieldValue(String)} for more details.
     * </p>
     * @param fieldValue value to convert.
     * @param maxLength maximum length for this field, to ensure result could
     *                  be written in column.
     * @return valid SQL String
     * @throws NullPointerException if input is null
     * @see #parseFieldValue(String)
     */
    public static String parseFieldValue(
            final String    fieldValue,
            final int       maxLength
            )
    {
        final int len = fieldValue.length();

        if(len > maxLength) {
            return SQLTools.parseFieldValue(fieldValue.substring(0, maxLength - 1));
            }
        else {
            return SQLTools.parseFieldValue(fieldValue);
            }
    }

    /**
     * Returns convert raw String (from user or database) to be usable as
     * an SQL String.
     * <p>
     * String result = parseFieldValue( fieldValue );
     * </p>
     * <ul>
     * <li>fieldValue = "I can't";</li>
     * <li>result = "I can\'t";</li>
     * </ul>
     * <p>
     * String sql = "SELECT * FROM `mytable` WHERE `myfield`='" + result + "';";
     * </p>
     * @param fieldValue value to convert.
     * @return valid SQL String
     * @throws NullPointerException if input is null
     * @see #parseFieldValue(String, int)
     */
    public static String parseFieldValue( final String fieldValue )
    {
        if( fieldValue == null ) {
            throw new NullPointerException("'fieldValue' param is null");
            }

        final StringBuilder sb = new StringBuilder();

        try {
            private_parseFieldValue( sb, fieldValue, 0);
            }
        catch( IOException ignore ) {}

        return sb.toString();
    }

    /**
     * Recursive internal converter.
     *
     * @param a             A valid {@link Appendable} object, for result.
     * @param fieldValue    value to convert.
     * @param idx           index in {@link #REMPLACEPHRASE}
     * @throws IOException  If can't append to appender 'a'
     */
    private static void private_parseFieldValue(
            final Appendable    a,
            final String        value,
            final int           idx
            )
        throws IOException
    {
        if( idx >= REMPLACEPHRASE.length ) {
            a.append( value );
            return;
            }
        if( value.length() == 0 ) {
            return;
            }
/*
//        //slogger.info( "'value' = " + value );
//
//        final StringTokenizer st = new StringTokenizer(value, REMPLACEPHRASE[idx][0]);
//
//        //slogger.info( "> S = " + REMPLACEPHRASE[idx][0] );
//        //slogger.info( "> R = " + REMPLACEPHRASE[idx][1] );
//        //slogger.info( "> st = " + st.countTokens() );
//        //slogger.info( ">A["+idx+"] = " + a );
//
//         private_parseFieldValue( a, st.nextToken(), idx + 1 );
//
//         //slogger.info( ">B["+idx+"] = " + a );
//
//         while( st.hasMoreTokens() ) {
//             a.append( REMPLACEPHRASE[idx][1] );
//
//            private_parseFieldValue( a, st.nextToken(), idx + 1 );
//            //slogger.info( ">C["+idx+"] = " + a );
//            }
//
//         //slogger.info( ">D["+idx+"] = " + a );
*/
         String[] parts = StringHelper.split( value, REMPLACEPHRASE[idx][0] );

         for(int pi = 0; pi<parts.length; pi++ ) {
             private_parseFieldValue( a, parts[ pi ], idx + 1 );

             if( pi < parts.length - 1 ) {
                 a.append( REMPLACEPHRASE[idx][1] );
                 }
             }

    }
}
