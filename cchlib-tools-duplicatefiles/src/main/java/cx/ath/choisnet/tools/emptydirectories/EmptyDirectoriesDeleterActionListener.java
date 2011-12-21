package cx.ath.choisnet.tools.emptydirectories;

import java.io.File;
import java.util.EventListener;

/**
 *
 *
 */
public interface EmptyDirectoriesDeleterActionListener
    extends EventListener
{
    /**
     *
     * @param emptyFolderFile
     * @return
     */
    public void workingOn( File emptyFolderFile );
}
