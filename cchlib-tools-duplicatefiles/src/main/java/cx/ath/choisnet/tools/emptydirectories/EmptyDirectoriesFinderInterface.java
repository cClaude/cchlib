package cx.ath.choisnet.tools.emptydirectories;

import java.io.FileFilter;
import cx.ath.choisnet.util.CancelRequestException;

/**
 * Find empty directories
 *
 */
public interface EmptyDirectoriesFinderInterface
{
    /**
     * Clear previous list and compute current list of empty directories
     * (should be call at least once)
     *
     * @return current object for initialization chaining
     * @throws CancelRequestException
     */
    public void find() throws CancelRequestException;

    /**
     * Clear previous list and compute current list of empty directories
     *
     * @param excludeDirectoriesFile {@link FileFilter} to identify directories to exclude.
     * @return current object for initialization chaining
     * @throws CancelRequestException
     */
    public void find( FileFilter excludeDirectoriesFile )
        throws CancelRequestException;

}
