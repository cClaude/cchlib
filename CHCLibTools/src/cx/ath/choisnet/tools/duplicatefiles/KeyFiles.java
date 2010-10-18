package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author Claude CHOISNET
 */
final class KeyFiles implements Serializable, Iterable<KeyFileState> 
{
    private static final long serialVersionUID = 1L;
    private String              key;
    private Set<KeyFileState>   files;
    private String              firstFileDisplayCache;
    private long                firstFileSizeCache;
    
    /**
     * 
     * @param key
     * @param files
     */
    public KeyFiles(String key,Set<KeyFileState> files)
    {
        this.key   = key;
        this.files = files;
        
        // Get first File.
        File f = this.files.iterator().next().getFile();
        this.firstFileDisplayCache = f.getName();
        this.firstFileSizeCache = f.length();
    }
    
    @Override
    public String toString()
    {
        return firstFileDisplayCache;
    }

    public long length()
    {
        return this.firstFileSizeCache;
    }
    
    /**
     * @return the key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return the files
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
