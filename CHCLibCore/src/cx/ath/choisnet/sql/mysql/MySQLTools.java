package cx.ath.choisnet.sql.mysql;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class MySQLTools
{

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

    public static String parseFieldValue(String fieldValue, int maxLength)
    {
        int len = fieldValue.length();

        if(len > maxLength) {
            return MySQLTools.parseFieldValue(fieldValue.substring(0, maxLength - 1));
        }
        else {
            return MySQLTools.parseFieldValue(fieldValue);
        }
    }

    public static String parseFieldValue(String input)
    {
        Iterator<Map.Entry<String,String>> entryIt = replacePhrases.entrySet().iterator();
        StringBuilder output = null;

        do {
            if(!entryIt.hasNext()) {
                break;
            }

            Map.Entry<String,String> entry = entryIt.next();
            StringTokenizer s = new StringTokenizer(input, entry.getKey());

            if(s.countTokens() > 1) {
                output = new StringBuilder(s.nextToken());

                while(s.hasMoreTokens()) {
                    output.append(entry.getValue()).append(s.nextToken());
                }
            }
        } while(true);

        if(output == null) {
            return input;
        }
        else {
            return output.toString();
        }
    }
}
