package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.confirm;

import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.util.HashMapSet;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
class JPanelConfirmModel extends AbstractList<KeyFileState>
    implements List<KeyFileState>,
               Serializable
{
    private static final long serialVersionUID = 1L;
    private final HashMapSet<String, KeyFileState> dupFiles; // $codepro.audit.disable declareAsInterface
    private int size;
    private final List<KeyFileState> cache = new ArrayList<>();
    private Boolean[] deleted; // Tree states boolean array ?????? FIXME
    // need to cache files lengths, to display length even when
    // a file will be deleted.
    private final List<Long> cacheFileLength = new ArrayList<>();

    public JPanelConfirmModel(
        final HashMapSet<String,KeyFileState> dupFiles // $codepro.audit.disable declareAsInterface
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
                this.cacheFileLength.add( Long.valueOf( f.getFile().length() ) );
                index++;
                }
            }

        this.size = index;
        this.deleted  = new Boolean[ index ] ;
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
    public Long getFileLength( final int index )
    {
        return this.cacheFileLength.get( index );
    }

    /**
     * @return the isDeleted
     */
    public boolean isDeleted( final int index )
    {
        final Boolean b = getDeleted( index );

        if( b != null ) {
            return b.booleanValue();
            }

        return false;
    }

    /**
     * @return the deleted flag for index file (could be null = never selected - so not mark delete)
     */
    public Boolean getDeleted( final int index )
    {
        return deleted[ index ];
    }

    /**
     * @param deleted the isDeleted to set
     */
    public void setDeleted( int index, boolean deleted )
    {
        this.deleted[ index ] = Boolean.valueOf( deleted );
    }
}
