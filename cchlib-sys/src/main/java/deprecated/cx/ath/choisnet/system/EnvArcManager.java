package deprecated.cx.ath.choisnet.system;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated No replacement
 */
@Deprecated
public final class EnvArcManager
{
    private static EnvArc defaultEnvArc = new deprecated.cx.ath.choisnet.system.impl.EnvArcDefaultImpl();
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
        // TxOxDxO: 1. identify operating system
        // TxOxDxO: 2. load EnvArc implementation(s?) (using reflexion)

//        try {
//            deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadOnly envArcRegWin32 = new deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadOnly();
//            addEnvArcEntry( envArcRegWin32 );
//            currentEnvArc = envArcRegWin32;
//        }
//        catch( deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32EnvArcException notSupported ) {
//        }

//        try {
//            addEnvArcEntry( new deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32ReadWrite() );
//        }
//        catch( deprecated.cx.ath.choisnet.system.impl.win32.EnvArcRegWin32EnvArcException notSupported ) {
//        }

        // Initialize current (if not yet done)
        if( currentEnvArc == null ) {
            currentEnvArc = defaultEnvArc;
        }
    }

    @Deprecated
    private static void addEnvArcEntry(EnvArc anEnvArc)
    {
        addEnvArcEntry( anEnvArc.getClass().getName(), anEnvArc );
    }

    @Deprecated
    private static void addEnvArcEntry(String name, EnvArc anEnvArc)
    {
        if( envArcMap.get( name ) != null ) {
            throw new EnvArcAllReadyDefineException(name);
        }

        envArcMap.put( anEnvArc.getClass().getName(), anEnvArc );
    }

    @Deprecated
    public static EnvArc getDefaultEnvArc()
    {
        return defaultEnvArc;
    }

    /**
     * @return Current EnvArc
     */
    @Deprecated
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
