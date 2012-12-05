package cx.ath.choisnet.test;

/**
 * @deprecated use {@link Assert#assertEquals(byte[],byte[])} or {@link Assert#assertEquals(String,byte[],byte[])} instead
 */
@Deprecated
final
public class ExtendTestCase // <- remove avoid JUnit tests crash under eclipse
{
//    /**
//     * Compare byte arrays
//     *
//     * @param message
//     * @param expected
//     * @param actual
//     */
    /**
     * @deprecated use {@link Assert#assertEquals(String,byte[],byte[])} instead
     */
    public static void assertEquals(
            String message,
            byte[] expected,
            byte[] actual
            )
    {
//        Test Case.assertEquals(
//                String.format(
//                        "%s - Not same size",
//                        message
//                        ),
//                expected.length,
//                actual.length
//                );
//
//        for(int i=0; i<expected.length;i++) {
//            Test Case.assertEquals(
//                String.format(
//                        "%s - Not same value offet %d",
//                        message,
//                        i
//                        ),
//                expected[i],
//                actual[i]
//               );
//        }
        Assert.assertEquals( message, expected, actual );
    }

//    /**
//     * Compare byte arrays
//     *
//     * @param expected
//     * @param actual
//     */
    /**
     * @deprecated use {@link Assert#assertEquals(byte[],byte[])} instead
     */
    public static void assertEquals(
            byte[] expected,
            byte[] actual
            )
    {
//        assertEquals("byte[] not equals", expected, actual);
        Assert.assertEquals(expected, actual);
    }
}
