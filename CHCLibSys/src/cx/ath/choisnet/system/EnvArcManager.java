/**
 * 
 */
package cx.ath.choisnet.system;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import cx.ath.choisnet.system.impl.EnvArcDefaultImpl;
import cx.ath.choisnet.system.impl.win32.EnvArcRegWin32EnvArcException;
import cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadOnly;
import cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadWrite;

/**
 * @author Claude CHOISNET
 *
 */
public final class EnvArcManager 
{
    private static EnvArc defaultEnvArc = new EnvArcDefaultImpl();
    private static EnvArc currentEnvArc;
    private static final Map<String,EnvArc> envArcMap = new HashMap<String,EnvArc>();

    static {
        // Add native implementation
        addEnvArcEntry( defaultEnvArc );
   
        perfomeInit();
    }
    
    private EnvArcManager()
    {//All static
    } 

    private static void perfomeInit()
    {
        // TODO: 1. identify operating system
        // TODO: 2. load EnvArc implementation(s?) (using reflexion)
        
        try {
            EnvArcRegWin32ReadOnly envArcRegWin32 = new EnvArcRegWin32ReadOnly();
            addEnvArcEntry( envArcRegWin32 );
            currentEnvArc = envArcRegWin32;
        }
        catch( EnvArcRegWin32EnvArcException notSupported ) {
        }
        
        try {
            addEnvArcEntry( new EnvArcRegWin32ReadWrite() );
        }
        catch( EnvArcRegWin32EnvArcException notSupported ) {
        }
         
        // Initialize current (if not yet done)
        if( currentEnvArc == null ) {
            currentEnvArc = defaultEnvArc;
        }
    }
    
    private static void addEnvArcEntry(EnvArc anEnvArc)
    {
        addEnvArcEntry( anEnvArc.getClass().getName(), anEnvArc );
    }

    private static void addEnvArcEntry(String name, EnvArc anEnvArc)
    {
        if( envArcMap.get( name ) != null ) {
            throw new EnvArcAllReadyDefineException(name);
        }
        
        envArcMap.put( anEnvArc.getClass().getName(), anEnvArc );
    }

    public static EnvArc getDefaultEnvArc()
    {
        return defaultEnvArc;
    }
    
    /**
     * @return Current EnvArc
     */
    public static EnvArc getEnvArc()
    {
        return currentEnvArc;
    }
    
    /**
     * Set Current EnvArc
     * @param name EnvArc name to set
     * @return true if EnvArc has been set, false otherwise
     */
    public static boolean setEnvArc(String name)
    {
        EnvArc envArc = envArcMap.get( name );
        
        if( envArc != null ) {
            currentEnvArc = envArc;
            return true;
        }
        
        return false;
    }
    
    public static Collection<String> getEnvArcNames()
    {
        return Collections.unmodifiableCollection( envArcMap.keySet() );
    }
}
