package cx.ath.choisnet.test;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import cx.ath.choisnet.io.FileFilterHelper;
import cx.ath.choisnet.io.FileIterator;

/**
 * Provide some tools to build test cases
 *
 * @author Claude CHOISNET
 */
final
public class AssertHelper
{
    private AssertHelper()
    {//All static
    }

    /**
     * Returns File object for tmp directory
     * according to java.io.tmpdir Java property.
     *
     * @return File object for tmp directory
     */
    public final static File getTmpDirFile()
    {
        return new File( System.getProperty("java.io.tmpdir" ) );
    }

    /**
     * Returns File object for current user home directory
     * according to user.home Java property.
     *
     * @return File object for current user home directory
     */
    public final static File getUserHomeDirFile()
    {
        return new File( System.getProperty("user.home") );
    }

    /**
     * Returns File object for root system directory
     * @return File object for root system directory
     */
    public final static File getSystemRootFile()
    {
        return new File( "/" );
    }



    /**
     * Returns File iterator from given directory (does
     * not return directory File object)
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
