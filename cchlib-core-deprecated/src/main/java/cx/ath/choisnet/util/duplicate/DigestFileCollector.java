package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.duplicate.DigestFileCollector} instead
 */
public interface DigestFileCollector
    extends Serializable
{
    /**
     * Returns a Map that contain an
     * unique Id (String digest), and a
     * collection of Files for that match
     * to this unique Id.
     * @return Map
     */
    public Map<String,Set<File>> getFiles();

    /**
     * Remove duplicate files in collection, and
     * returns number of file removed in Collection.
     * <br/>
     * More formally, remove all entry with a Set<File>
     * size > 1
     *
     * @return number of file removed in Collection
     * @throws UnsupportedOperationException if not supported
     */
    public int removeDuplicate()
        throws UnsupportedOperationException;

    /**
     * Remove non duplicate files in collection, and
     * returns number of file removed in Collection.
     * <br/>
     * More formally, remove all entry with a Set<File>
     * size < 2
     *
     * @return number of file removed in Collection
     * @throws UnsupportedOperationException if not supported
     */
    public int removeNonDuplicate()
        throws UnsupportedOperationException;

    /**
     * Removes all of the mappings. The map will
     * be empty after this call returns.
     */
    public void clear();

    /**
     * Return count of Set that contain more than
     * on file
     * .
     * @return count of Set that contain more than
     * on file.
     */
    public int getDuplicateSetsCount();

    /**
     * Return count of File in a Set that contain
     * more than on file.
     *
     * @return count of File in a Set that contain
     * more than on file
     */
    public int getDuplicateFilesCount();

    /**
     * Add a DigestEventListener to this DigestFileCollector
     *
     * @param listener the listener to add
     */
    public void addDigestEventListener(DigestEventListener listener);

    /**
     * Remove a DigestEventListener to this DigestFileCollector
     *
     * @param listener the listener to remove
     */
    public void removeDigestEventListener(DigestEventListener listener);
    }
