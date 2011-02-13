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
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
 *
 * @author Claude CHOISNET
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

    /**
     * 
     * @param fieldValue
     * @param maxLength
     * @return
     */
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

    /**
     * 
     * @param input
     * @return
     */
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
