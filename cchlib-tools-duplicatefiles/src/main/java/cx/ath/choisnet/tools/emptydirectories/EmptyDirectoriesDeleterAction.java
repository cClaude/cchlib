package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;

/**
 *
 *
 */
public interface EmptyDirectoriesDeleterAction
{
    /**
     *
     * @param emptyFolderFile
     */
    public boolean doAction( File emptyFolderFile );
}
