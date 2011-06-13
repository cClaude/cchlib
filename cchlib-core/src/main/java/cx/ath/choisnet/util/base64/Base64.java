package cx.ath.choisnet.util.base64;

/**
 *
 */
abstract class Base64
{
    protected static final int DEFAULT_BUFFER_SIZE = 8192;

    protected static final char[] BASE64 = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', '+', '/'
    };

    // Mapping table from 6-bit nibbles to Base64 characters.
    protected static char[]    map1 = new char[64];
    static {
        int i=0;
        for(char c='A'; c<='Z'; c++) {
            map1[i++] = c;
            }
        for(char c='a'; c<='z'; c++) {
            map1[i++] = c;
            }
        for(char c='0'; c<='9'; c++) {
            map1[i++] = c;
            }
        map1[i++] = '+';
        map1[i++] = '/';
    }

    // Mapping table from Base64 characters to 6-bit nibbles.
    protected static byte[] map2 = new byte[128];
    static {
        for (int i=0; i<map2.length; i++) {
            map2[i] = -1;
            }
        for (int i=0; i<64; i++) {
            map2[map1[i]] = (byte)i;
            }
    }

}
