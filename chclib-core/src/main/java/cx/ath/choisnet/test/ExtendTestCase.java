/**
 * 
 */
package cx.ath.choisnet.test;

import junit.framework.TestCase;

/**
 * Provide some tools to build test cases
 * 
 * @author Claude CHOISNET
 */
public class ExtendTestCase extends TestCase
{

    /**
     * Compare byte arrays
     * 
     * @param message
     * @param expected
     * @param actual
     */
    public void assertEquals(
            String message,
            byte[] expected,
            byte[] actual
            )
    {
        assertEquals(
                String.format( 
                        "%s - Not same size",
                        message 
                        ),
                expected.length,
                actual.length
                );

        for(int i=0; i<expected.length;i++) {
            assertEquals(
                String.format( 
                        "%s - Not same value offet %d", 
                        message,
                        i 
                        ),
                expected[i],
                actual[i]
               );
        }
    }
    
    /**
     * Compare byte arrays
     * 
     * @param expected
     * @param actual
     */
    public void assertEquals(
            byte[] expected,
            byte[] actual
            )
    {
        assertEquals("byte[] not equals", expected, actual);
    }
}
