package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.EventListener;

/**
 *
 *
 */
public interface EmptyDirectoriesListener
    extends EventListener
{
    /**
     * Returns true to cancel scan
     * @return true to cancel scan
     */
    public boolean isCancel();

    /**
     *
     * @param emptyDirectoryFile
     */
    public void newEntry( File emptyDirectoryFile );

    /**
     *
     */
    public void findStarted();

    /**
     *
     */
    public void findDone();

}
