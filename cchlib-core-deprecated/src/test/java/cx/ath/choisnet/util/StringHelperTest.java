// $codepro.audit.disable concatenationInAppend
package cx.ath.choisnet.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.googlecode.cchlib.test.ArrayAssert;

@Deprecated
public class StringHelperTest
{
    public StringHelperTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of split method, of class StringHelper.
     */
    @Test
    public void testSplit_String_char()
    {
        {
            final String    str         = "*AA**BB*C*";
            final char      token       = '*';
            final String[]  expResult   = {"","AA","","BB","C",""};

            testSplit_String_char( str, token, expResult );
        }
        {
            final String    str         = "*AA**BB*C";
            final char      token       = '*';
            final String[]  expResult   = {"","AA","","BB","C"};

            testSplit_String_char( str, token, expResult );
        }
        {
            final String    str         = "boo:and:foo";
            final char      token       = ':';
            final String[]  expResult   = {"boo","and","foo"};

            testSplit_String_char( str, token, expResult );
        }
        {
            final String    str         = "boo:and:foo";
            final char      token       = 'o';
            final String[]  expResult   = {"b","",":and:f","",""};

            testSplit_String_char( str, token, expResult );
        }
    }

    private static void testSplit_String_char( final String str, final char token, String[] expResult )
    {
        System.out.println("split(String,char) : " + str);

        String[] result = StringHelper.split(str, token);

        for( String r:result ) {
            System.out.println( "R=" + r );
            }

        ArrayAssert.assertEquals(expResult, result);
    }

    /**
     * Test of split method, of class StringHelper.
     */
    @Test
    public void testSplit_String_String()
    {
        {
            final String    str         = "*AA**BB*C*";
            final String    token       = "*";
            final String[]  expResult   = {"","AA","","BB","C",""};

            testSplit_String_String( str, token, expResult );
        }
        {
            final String    str         = "123AA123123BB123C123";
            final String    token       = "123";
            final String[]  expResult   = {"","AA","","BB","C",""};

            testSplit_String_String( str, token, expResult );
        }
        {
            final String    str         = "123AA123123BB123C";
            final String    token       = "123";
            final String[]  expResult   = {"","AA","","BB","C"};

            testSplit_String_String( str, token, expResult );
        }
        {
            final String    str         = "boo:and:foo";
            final String    token       = ":";
            final String[]  expResult   = {"boo","and","foo"};

            testSplit_String_String( str, token, expResult );
        }
        {
            final String    str         = "boo:and:foo";
            final String    token       = "o";
            final String[]  expResult   = {"b","",":and:f","",""};

            testSplit_String_String( str, token, expResult );
        }
        {
            final String    str         = "boo:and:foo";
            final String    token       = "b";
            final String[]  expResult   = {"","oo:and:foo"};

            testSplit_String_String( str, token, expResult );
        }
    }

    private static void testSplit_String_String(
            final String    str,
            final String    token,
            final String[]  expResult
            )
    {
        System.out.println("split(String,String) : " + str );

        String[] result = StringHelper.split(str, token);

        for( String r:result ) {
            System.out.println( "R2=" + r );
            }

        ArrayAssert.assertEquals(expResult, result);
    }

//    public static void main( String[] args )
//    {
//        String value = "a'comment'b";
//        String[] parts = value.split( "'" );
//        java.util.StringTokenizer st = new java.util.StringTokenizer(value, "'");
//
//        System.out.println( "value: [" + value + "]");
//
//        for(String p:parts) {
//            System.out.println( "p: [" + p + "]");
//            }
//        while( st.hasMoreElements() ) {
//            System.out.println( "T: [" + st.nextToken() + "]");
//            }
//
//        value = "'A'B'";
//        parts = value.split( "'" );
//        st = new java.util.StringTokenizer(value, "'");
//
//        System.out.println( "value: [" + value + "]");
//
//        for(String p:parts) {
//            System.out.println( "p: [" + p + "]");
//            }
//        while( st.hasMoreElements() ) {
//            System.out.println( "T: [" + st.nextToken() + "]");
//            }
//        parts = StringHelper.split(value, '\'');
//        for(String p:parts) {
//            System.out.println( "p2: [" + p + "]");
//            }
//        System.out.println( "XX: [" + value.indexOf("'", 0) + "]");
//   }
}
