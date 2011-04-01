package deprecated.cx.ath.choisnet.system.impl.win32;

import java.util.Iterator;
import org.apache.log4j.Logger;
import junit.framework.TestCase;

/**
 * @author Claude CHOISNET
 */
public class RegWin32Test extends TestCase
{
    final private static Logger slogger = Logger.getLogger(RegWin32Test.class);
    private RegWin32 anRegWin32;
    private static final String SYSTEM_SessionManager_BASE = "HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Session Manager";

    public void setUp() throws EnvArcRegWin32EnvArcException
    {
        this.anRegWin32 = new RegWin32();
    }

    public void testGetRegString()
    {
        //TODO
//    public String getRegString(String regName, String regValue)
    }

    public void testSetRegString()
    {
        //TODO
//      public void setRegString(String regName, String regValue, String regData)
    }

    public void testGetRegInteger()
    {
        //TODO
//      public Integer getRegInteger(String regName, String regValue)
    }

    public void testSetRegInteger()
    {
        //TODO
//      public void setRegInteger(String regName, String regValue, int regData)
    }
    
    public void test_getSubKeyNamesIterator() throws EnvArcRegWin32EnvArcException
    {
        Iterator<String> iStr = anRegWin32.getSubKeyNamesIterator(SYSTEM_SessionManager_BASE);

        while( iStr.hasNext() ) {
            String subKeyName = iStr.next();
            slogger.info( "SubKeyName:" + subKeyName );
        }
    }
    
    public void test_getValuesIterator() throws EnvArcRegWin32EnvArcException
    {
        Iterator<String> iStr = anRegWin32.getValuesIterator(SYSTEM_SessionManager_BASE);

        while( iStr.hasNext() ) {
            String valueName = iStr.next();
            slogger.info( "valueName:" + valueName );
        }
    }

}
