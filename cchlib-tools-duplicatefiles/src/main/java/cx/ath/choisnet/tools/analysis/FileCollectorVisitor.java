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
    public void openRootDirectory(File rootDirectoryFile);
    
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

//    /**
//     *
//     * @param directoryFile
//     */
//    public void closeDirectory(/*File directoryFile*/);
}
