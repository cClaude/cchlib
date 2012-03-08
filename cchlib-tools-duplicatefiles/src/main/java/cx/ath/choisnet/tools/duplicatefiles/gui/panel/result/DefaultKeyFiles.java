package cx.ath.choisnet.tools.duplicatefiles.gui.panel.result;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;

/**
 *
 */
// not public
final class DefaultKeyFiles extends AbstractKeyFiles
{
    private static final long serialVersionUID = 3L;
    private Set<KeyFileState>   files;
    private KeyFileState        firstFileCache;

    /**
     *
     * @param key    Key for theses files (hash code, MD5, ...)
     * @param files Set with at least 2 entries
     */
    public DefaultKeyFiles(
        final String             key,
        final Set<KeyFileState>  files
        )
    {
        super( key );

        this.files = files;

        //Collections.sort( this.files );

        //FIXME: have a better choice than first one !
        // Get first File.
        //this.firstFileCache = this.files.get( 0 ).getFile();
        //
        this.firstFileCache = this.files.iterator().next();
        //this.firstFileDisplayCache = f.getName();
        //this.firstFileSizeCache = this.firstFileCache.length();
    }

    @Override
    public File getFirstFile()
    {
        return this.firstFileCache.getFile();
    }

    @Override
    public Collection<KeyFileState> getFiles()
    {
        return files;
    }

    @Override
    public int getDepth()
    {
        return this.firstFileCache.getDepth();
    }
}
