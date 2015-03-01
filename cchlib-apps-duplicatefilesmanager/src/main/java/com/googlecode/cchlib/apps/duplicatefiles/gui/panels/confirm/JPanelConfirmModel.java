package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.confirm;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.util.MapSetHelper;

/**
 *
 */
class JPanelConfirmModel extends AbstractList<KeyFileState>
    implements List<KeyFileState>,
               Serializable
{
    private static final long serialVersionUID = 1L;
    private final Map<String, Set<KeyFileState>> dupFiles;
    private int size;
    private final List<KeyFileState> cache = new ArrayList<>();
    private Boolean[] deleted; // FIXME Tree states boolean array ??????
    // need to cache files lengths, to display length even when
    // a file will be deleted.
    private final List<Long> cacheFileLength = new ArrayList<>();

    public JPanelConfirmModel(
        final Map<String, Set<KeyFileState>> duplicateFiles
        )
    {
        this.dupFiles = duplicateFiles;

        buildCache();
    }

    private void buildCache()
    {
        int index = 0;

        for( final KeyFileState f : MapSetHelper.valuesIterable( this.dupFiles ) ) {
            if( f.isSelectedToDelete() ) {
                this.cache.add( f );
                this.cacheFileLength.add( Long.valueOf( f.getLength() ) );
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
        return this.deleted[ index ];
    }

    /**
     * @param deleted the isDeleted to set
     */
    public void setDeleted( final int index, final boolean deleted )
    {
        this.deleted[ index ] = Boolean.valueOf( deleted );
    }
}
