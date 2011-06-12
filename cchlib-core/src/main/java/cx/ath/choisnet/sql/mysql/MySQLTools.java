package cx.ath.choisnet.sql.mysql;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 *
 * @deprecated use {@link cx.ath.choisnet.sql.SQLTools} instead.
 */
public class MySQLTools
{
    @Deprecated
    protected static Map<String,String> replacePhrases;

    static
    {
        replacePhrases = new HashMap<String,String>();
        replacePhrases.put("'", "\\'");
        replacePhrases.put("\\", "\\\\");
    }

    private MySQLTools()
    {
        // All static !:
    }

    /**
     * BUG for complex expressions
     * @see cx.ath.choisnet.sql.SQLTools#parseFieldValue(String, int)
     */
    @Deprecated
    public static String parseFieldValue(
            final String    fieldValue,
            final int       maxLength
            )
    {
        int len = fieldValue.length();

        if(len > maxLength) {
            return MySQLTools.parseFieldValue(fieldValue.substring(0, maxLength - 1));
        }
        else {
            return MySQLTools.parseFieldValue(fieldValue);
        }
    }

    /**
     * BUG for complex expressions
     * @see cx.ath.choisnet.sql.SQLTools#parseFieldValue(String)
     */
    @Deprecated
    public static String parseFieldValue( final String input )
    {
        StringBuilder                       output                   = null;
        Iterator<Map.Entry<String,String>>  replacePhrasesIterator   = replacePhrases.entrySet().iterator();

        do {
            if( !replacePhrasesIterator.hasNext() ) {
                break;
                }

            Map.Entry<String,String> entry  = replacePhrasesIterator.next();
            StringTokenizer          st     = new StringTokenizer(input, entry.getKey());

            if( st.countTokens() > 1) {
                output = new StringBuilder( st.nextToken() );

                while( st.hasMoreTokens() ) {
                    output.append(entry.getValue()).append( st.nextToken() );
                    }
            }
        } while(true);

        if( output == null ) {
            // No change !
            return input;
            }
        else {
            return output.toString();
            }
    }
}
