package cx.ath.choisnet.test;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import cx.ath.choisnet.io.FileFilterHelper;
import cx.ath.choisnet.io.FileIterator;

/**
 * Provide some tools to build test cases
 *
 */
final
public class AssertHelper
{
    private AssertHelper()
    {//All static
    }

//    /**
//     * Returns File object for tmp directory
//     * according to java.io.tmpdir Java property.
//     *
//     * @return File object for tmp directory
//     * @ deprecated use {@link com.googlecode.cchlib.io.FileHelper#getTmpDirFile()} instead
//     */
//    public final static File getTmpDirFile()
//    {
//        return new File( System.getProperty("java.io.tmpdir" ) );
//    }

//    /**
//     * Returns File object for current user home directory
//     * according to user.home Java property.
//     *
//     * @return File object for current user home directory
//     * @ deprecated use {@link com.googlecode.cchlib.io.FileHelper#getUserHomeDirFile()} instead
//     */
//    public final static File getUserHomeDirFile()
//    {
//        return new File( System.getProperty("user.home") );
//    }

//    /**
//     * Returns File object for root system directory
//     * @return File object for root system directory
//     * @ deprecated use {@link com.googlecode.cchlib.io.FileHelper#getSystemRootFile()} instead
//     */
//    public final static File getSystemRootFile()
//    {
//        return new File( "/" );
//    }

    /**
     * Returns File iterator from given directory (fileDirectory
     * will be not include in iterator result)
     * @param fileDirectory file directory to explore
     * @param fileFilter    file filter for result files
     *                      (does not filter directories,
     *                      could be null).
     * @return File iterator from given directory
     */
    public final static Iterator<File> getFilesFrom(
            File        fileDirectory,
            FileFilter  fileFilter
            )
    {
        FileFilter justFileFilter = FileFilterHelper.not(
                    FileFilterHelper.directoryFileFilter()
                    );
        FileFilter privateFileFilter;

        if( fileFilter == null ) {
            privateFileFilter = justFileFilter;
            }
        else {
            privateFileFilter = FileFilterHelper.and(
                    justFileFilter,
                    fileFilter
                    );
            }

        return new FileIterator(
                fileDirectory,
                privateFileFilter
                );
    }
}
