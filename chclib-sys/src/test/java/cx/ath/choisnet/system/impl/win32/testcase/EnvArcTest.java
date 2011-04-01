package cx.ath.choisnet.system.impl.win32.testcase;

/*import java.util.Collection;
import org.apache.log4j.Logger;
import deprecated.cx.ath.choisnet.system.EnvArc;
import deprecated.cx.ath.choisnet.system.EnvArcException;
import deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32EnvArcException;
import deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadOnly;
import deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadWrite;
*/
import junit.framework.TestCase;

/**
 * @author Claude CHOISNET
 */
@Deprecated
public class EnvArcTest extends TestCase
{
    /*
    final private static Logger slogger = Logger.getLogger(EnvArcTest.class);
    private EnvArcRegWin32ReadOnly anEnvArcRegWin32ReadOnly;
    private EnvArcRegWin32ReadWrite anEnvArcRegWin32ReadWrite;
    public void setUp() throws EnvArcRegWin32EnvArcException
    {
        this.anEnvArcRegWin32ReadOnly  = new EnvArcRegWin32ReadOnly();
        this.anEnvArcRegWin32ReadWrite = new EnvArcRegWin32ReadWrite();
    }
    
    @Deprecated
    public void testBasicGet() throws EnvArcException
    {
        testBasicGet(this.anEnvArcRegWin32ReadOnly);
        testBasicGet(this.anEnvArcRegWin32ReadWrite);
    }

    @Deprecated
    public void testBasicGet(EnvArc envarc) throws EnvArcException
    {
        String varname = "JAVA_HOME";
        String value   = envarc.getVar( varname );

        slogger.info( "[" + varname + "] = [" + value + "]" );

        try {
            varname = "*Must$Not_Exists";
            value   = envarc.getVar( varname );

            slogger.info( "[" + varname + "] = [" + value + "]" );
            fail("Should fail here!");
        }
        catch( EnvArcException ok ) {
            
        }
    }

    public void testBasicSet() throws EnvArcException
    {
        try {
            this.anEnvArcRegWin32ReadOnly.setVar( this.getClass().getName(), this.getClass().getName() );
            
            fail("Should fail here!");
        }
        catch( UnsupportedOperationException ok ) {
            
        }
        
        this.anEnvArcRegWin32ReadWrite.setVar( this.getClass().getName(), this.getClass().getName() );
    }

    @Deprecated
    public void test_getVarNameList() throws EnvArcException
    {
        test_getVarNameList(anEnvArcRegWin32ReadOnly);
        test_getVarNameList(anEnvArcRegWin32ReadWrite);
    }
    
    @Deprecated
    public void test_getVarNameList(EnvArc envarc) throws EnvArcException
    {
        Collection<String> c = envarc.getVarNameList();

        slogger.info( "getVarNameList() : " + c );
        slogger.info( "getVarNameList() # : " + c.size());

        for(String name: c) {
            slogger.info( "Known var name : " 
                    + name 
                    + " = " 
                    + envarc.getVar( name )
                    );
        }
    }*/

}
