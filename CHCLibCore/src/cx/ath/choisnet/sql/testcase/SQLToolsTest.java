/**
 * 
 */
package cx.ath.choisnet.sql.testcase;

import org.apache.log4j.Logger;
import cx.ath.choisnet.sql.SQLTools;
import junit.framework.TestCase;

/**
 * 
 * @author Claude CHOISNET
 *
 */
public class SQLToolsTest extends TestCase 
{
    private static Logger slogger = Logger.getLogger( SQLToolsTest.class );
    
    public void test_parseFieldValue1()
    {
        final String s = "T1: AbCdEfGhIj";
        
        test_parseFieldValue( s, s );
    }
    
    public void test_parseFieldValue2()
    {
        test_parseFieldValue( "T2: L\\'élève", "T2: L'élève" );
    }
    
    public void test_parseFieldValue21()
    {
        test_parseFieldValue( "T21: L\\'élève L\\'élève", "T21: L'élève L'élève" );
    }
    
    public void test_parseFieldValue3()
    {
        test_parseFieldValue( "T3: Le \\\\bô\\\\ texte", "T3: Le \\bô\\ texte" );
    }
    
    public void test_parseFieldValue4()
    {
        test_parseFieldValue( 
            "T4: 1\\\\2\\\\3\\\\4\\\\5\\\\6",
            "T4: 1\\2\\3\\4\\5\\6" 
            );
    }
    
    public void test_parseFieldValue5()
    {
        test_parseFieldValue( 
            "T5: L\\'élève est \\\\bô\\\\!",
            "T5: L'élève est \\bô\\!"
            );
    }
    
    public void test_parseFieldValue6()
    {
        test_parseFieldValue( 
            "T6: -\\'-\\\\-\\'-\\\\-\\'-\\\\-\\'-\\\\-\\'-\\\\-\\'-", 
            "T6: -'-\\-'-\\-'-\\-'-\\-'-\\-'-" 
            );
    }
    
    public void test_parseFieldValue7()
    {
        test_parseFieldValue( 
            "category 4 (\\'en\\' only)", 
            "category 4 ('en' only)" 
            );
    }
    
    public void test_parseFieldValue8()
    {
        test_parseFieldValue( 
            "category 4 description(\\'en\\' only)", 
            "category 4 description('en' only)" 
            );
    }

    private static void test_parseFieldValue( String expStr, String s )
    {
        String r = SQLTools.parseFieldValue( s );
        
        slogger.info( "s      = [" + s + "]" );
        slogger.info( "r      = [" + r + "]" );
        slogger.info( "expStr = [" + expStr + "]" );
        
        assertEquals("Bad result for: [" + s + "]",expStr,r);
    }
}
