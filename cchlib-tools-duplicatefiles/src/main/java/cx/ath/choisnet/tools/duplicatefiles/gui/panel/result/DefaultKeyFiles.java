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
    private File                firstFileCache;

    /**
     *
     * @param key
     * @param files
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
        this.firstFileCache = this.files.iterator().next().getFile();
        //this.firstFileDisplayCache = f.getName();
        //this.firstFileSizeCache = this.firstFileCache.length();
    }

    @Override
    public File getFirstFile()
    {
        return this.firstFileCache;
    }

    @Override
    public Collection<KeyFileState> getFiles()
    {
        return files;
    }
}
