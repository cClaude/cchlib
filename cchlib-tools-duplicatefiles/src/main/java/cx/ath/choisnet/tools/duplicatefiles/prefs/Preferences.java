package cx.ath.choisnet.tools.duplicatefiles.prefs;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public interface Preferences extends Serializable
{
    public boolean getUserLevel();
    public String getLookAndFeelName();

    /**
     * Returns a Collection of RootFile
     * @return a Collection of RootFile
     */
    public Collection<RootFile> getCompareRootFileCollection();
}
