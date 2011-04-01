/**
 * 
 */
package cx.ath.choisnet.system.testcase;

import java.util.Collection;
import org.apache.log4j.Logger;
import deprecated.cx.ath.choisnet.system.EnvArcManager;
import junit.framework.TestCase;

/**
 * @author Claude CHOISNET
 *
 */
@Deprecated
public class EnvArcTest extends TestCase 
{
    final private static Logger slogger = Logger.getLogger(EnvArcTest.class);

    public void testManager()
    {
        Collection<String> names= EnvArcManager.getEnvArcNames();
        
        for( String name : names ) {
            slogger.info( "getEnvArcNames: " + name );
        }
    }
}
