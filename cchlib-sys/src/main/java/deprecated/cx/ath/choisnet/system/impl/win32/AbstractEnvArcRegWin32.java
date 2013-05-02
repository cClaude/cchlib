package deprecated.cx.ath.choisnet.system.impl.win32;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @deprecated No replacement
 */
@Deprecated
public abstract class AbstractEnvArcRegWin32
    extends RegWin32
        implements deprecated.cx.ath.choisnet.system.EnvArc
{
    /**
     * @throws EnvArcRegWin32EnvArcException
     */
    public AbstractEnvArcRegWin32() throws EnvArcRegWin32EnvArcException
    {
        super();
    }

    /**
     * @return a Collection of String varName
     * @throws EnvArcRegWin32EnvArcException
     * @see EnvArc#getVarNameList()
     */
    @Override
    public Collection<String> getVarNameList()
        throws EnvArcRegWin32EnvArcException
    {
        List<String> list = new ArrayList<String>();

        Iterator<String> iStr = getValuesIterator(SYSTEM_ENVIRONMENT_BASE);

        while(iStr.hasNext()) {
            String name = iStr.next();

            try {
                getVar(name);
                // TxOxDxO: improve this, must have a
                // look in native code of registry-3.1.3.zip
                // there is probably a bug, or something
                // didn't work with Windows XP family service pack 3
                list.add( name ); // workaround : hide entries who don't work!
            }
            catch( deprecated.cx.ath.choisnet.system.EnvArcException e ) {
            }
        }

        return Collections.unmodifiableCollection( list );
    }
}
