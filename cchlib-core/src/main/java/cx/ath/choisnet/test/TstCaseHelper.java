package cx.ath.choisnet.test;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import cx.ath.choisnet.io.FileFilterHelper;
import cx.ath.choisnet.io.FileIterator;

/**
 * @deprected use {@link AssertHelper} instead
 */
@Deprecated
final
public class TstCaseHelper
{
    private TstCaseHelper()
    {//All static
    }

    /**
     * use {@link AssertHelper#getTmpDirFile()} instead
     */
    @Deprecated
    public final static File getTmpDirFile()
    {
        return new File( System.getProperty("java.io.tmpdir" ) );
    }

    /**
     * use {@link AssertHelper#getUserHomeDirFile()} instead
     */
    @Deprecated
    public final static File getUserHomeDirFile()
    {
        return new File( System.getProperty("user.home") );
    }

    /**
     * use {@link AssertHelper#getSystemRootFile()} instead
     */
    @Deprecated
    public final static File getSystemRootFile()
    {
        return new File( "/" );
    }

    /**
     * use {@link AssertHelper#getFilesFrom(File, FileFilter)} instead
     */
    @Deprecated
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
