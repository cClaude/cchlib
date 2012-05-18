package cx.ath.choisnet.tools.duplicatefiles.gui.panel.confirm;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.util.HashMapSet;

/**
 *
 */
class JPanelConfirmModel extends AbstractList<KeyFileState>
    implements List<KeyFileState>,
               Serializable
{
    private static final long serialVersionUID = 1L;
    private HashMapSet<String, KeyFileState> dupFiles;
    private int size;
    private ArrayList<KeyFileState> cache = new ArrayList<>();
    private Boolean[] isDeleted;
    // need to cache files lengths, to display length even when
    // a file will be deleted.
    private ArrayList<Long> cacheFileLength = new ArrayList<>();

    public JPanelConfirmModel(
        final HashMapSet<String,KeyFileState> dupFiles
        )
    {
        this.dupFiles = dupFiles;

        buildCache();
    }

    private void buildCache()
    {
        int index = 0;

        for( KeyFileState f:dupFiles ) {
            if( f.isSelectedToDelete() ) {
                this.cache.add( f );
                this.cacheFileLength.add( f.getFile().length() );
                index++;
                }
            }

        this.size = index;
        this.isDeleted  = new Boolean[ index ] ;
    }

    @Override
    public int size()
    {
        return this.size;
    }

    @Override
    public KeyFileState get( final int index )
    {
        return this.cache.get( index );
    }

    /**
     *
     * @param index
     * @return
     */
    public long getFileLength( int index )
    {
        return this.cacheFileLength.get( index );
    }

    /**
     * @return the isDeleted
     */
    public Boolean isDeleted( int index )
    {
        return isDeleted[ index ];
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setDeleted( int index, boolean deleted )
    {
        this.isDeleted[ index ] = deleted;
    }
}
