package cx.ath.choisnet.tools.analysis;

import java.io.File;

/**
 *
 */
public interface FileCollectorVisitor
{
    /**
     *
     * @param directoryFile
     */
    void openRootDirectory(File rootDirectoryFile);

    /**
     *
     * @param directoryFile
     */
    void openDirectory(File directoryFile);

    /**
     *
     * @param file
     */
    void discoverFile(File file);
}
