package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public final class KeyFiles implements Serializable, Iterable<KeyFileState>
{
    private static final long serialVersionUID = 2L;
    private String              key;
    private Set<KeyFileState>   files;
    private File                firstFileCache;
    //private String              firstFileDisplayCache;
    //private long                firstFileSizeCache;

    /**
     *
     * @param key
     * @param files
     */
    public KeyFiles(String key,Set<KeyFileState> files)
    {
        this.key   = key;
        this.files = files;

        //FIXME: have a better choice than first one !
        // Get first File.
        this.firstFileCache = this.files.iterator().next().getFile();
        //this.firstFileDisplayCache = f.getName();
        //this.firstFileSizeCache = this.firstFileCache.length();
    }

    /**
     * @return String use by UI display
     */
    @Override
    public String toString()
    {
        return this.firstFileCache.getName();
        //return firstFileDisplayCache;
    }

    /**
     * Returns 'first' File for this KeyFile
     * @return 'first' File for this KeyFile
     */
    public File getFirstFile()
    {
        return this.firstFileCache;
    }

    public long length()
    {
        //return this.firstFileSizeCache;
        return this.firstFileCache.length();
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return the file set
     */
    public Set<KeyFileState> getFiles()
    {
        return files;
    }

    @Override
    public Iterator<KeyFileState> iterator()
    {
        return files.iterator();
    }
}
