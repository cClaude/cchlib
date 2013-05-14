package cx.ath.choisnet.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.googlecode.cchlib.io.FileFilterHelper;
import com.googlecode.cchlib.io.FileIterator;

/**
 * Provide some tools to build test cases
 * @deprecated use {@link com.googlecode.cchlib.test.FilesTestCaseHelper} instead
 */
@Deprecated
final public class AssertHelper
{
    private AssertHelper()
    {//All static
    }

    /**
     * Returns a {@link File} {@link Iterator} from given directory (directories
     * will not be include in result)
     * @param fileDirectory file directory to explore
     * @param fileFilter    file filter for result files
     *                      (does not filter directories,
     *                      could be null).
     * @return {@link File} {@link Iterator} from given directory
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

    /**
     * Returns a {@link File} {@link List} from given directory (directories
     * will not be include in result)
     * @param fileDirectory file directory to explore
     * @param fileFilter    file filter for result files
     *                      (does not filter directories,
     *                      could be null).
     * @return {@link File} {@link List} from given directory
     * @since 4.1.7
     */
    public final static List<File> getFilesListFrom(
        final File        fileDirectory,
        final FileFilter  fileFilter
        )
    {
        final List<File>        list = new ArrayList<File>();
        final Iterator<File>    iter = getFilesFrom( fileDirectory, fileFilter );

        while( iter.hasNext() ) {
            list.add( iter.next() );
            }

        return list;
    }

}
