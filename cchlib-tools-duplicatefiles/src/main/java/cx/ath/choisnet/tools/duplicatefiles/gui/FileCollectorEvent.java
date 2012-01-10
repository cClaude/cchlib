package cx.ath.choisnet.tools.duplicatefiles.gui;

import java.io.File;

/**
 *
 */
public interface FileCollectorEvent
{
    /**
     *
     * @param directoryFile
     */
    public void openDirectory(File directoryFile);

    /**
     *
     * @param file
     */
    public void discoverFile(File file);

    /**
     *
     * @param directoryFile
     */
    public void closeDirectory(File directoryFile);
}
