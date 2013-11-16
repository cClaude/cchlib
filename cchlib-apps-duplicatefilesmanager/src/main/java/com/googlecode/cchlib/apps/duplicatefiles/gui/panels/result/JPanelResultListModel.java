// $codepro.audit.disable largeNumberOfFields
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
import javax.swing.ListCellRenderer;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;
import com.googlecode.cchlib.util.HashMapSet;

/**
 *
 */
public class JPanelResultListModel
    extends AbstractListModel<KeyFiles>
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( JPanelResultListModel.class );
    
    private HashMapSet<String,KeyFileState> duplicateFiles; // $codepro.audit.disable declareAsInterface

    private List<KeyFiles> duplicatesFileCacheList = new ArrayList<>();

    private SortMode sortMode;
    private Comparator<? super KeyFiles> filenameComparator;
    private Comparator<? super KeyFiles> pathComparator;
    private Comparator<? super KeyFiles> sizeComparator;
    private Comparator<? super KeyFiles> numberOfDuplicateComparator;
    private Comparator<? super KeyFiles> depthComparator;

    private SelectFirstMode selectFirstMode;
    private Comparator<? super KeyFileState> fileDepthAscendingComparator;
    private Comparator<? super KeyFileState> fileDepthDescendingComparator;

    public JPanelResultListModel()
    {
        filenameComparator = new Comparator<KeyFiles>() {
                @Override
                public int compare( KeyFiles o1, KeyFiles o2 )
                {
                    return o1.toString().compareTo( o2.toString() );
                }
            };
        pathComparator = new Comparator<KeyFiles>() {
                @Override
                public int compare( KeyFiles o1, KeyFiles o2 )
                {
                    return o1.getFirstFile().getPath().compareTo(
                            o2.getFirstFile().getPath()
                            );
                }
            };
        sizeComparator = new Comparator<KeyFiles>() {
                @Override
                public int compare( KeyFiles o1, KeyFiles o2 )
                {
                    return (int)(
                        o1.getFirstFile().length() -
                            o2.getFirstFile().length()
                            );
                }
            };
        numberOfDuplicateComparator = new Comparator<KeyFiles>() {
                @Override
                public int compare( KeyFiles o1, KeyFiles o2 )
                {
                    return o1.getFiles().size() - o2.getFiles().size();
                }
            };
        depthComparator = new Comparator<KeyFiles>() {
                @Override
                public int compare( KeyFiles o1, KeyFiles o2 )
                {
                    return o1.getDepth() - o2.getDepth();
                }
            };
        fileDepthAscendingComparator = new Comparator<KeyFileState>() {
                @Override
                public int compare(KeyFileState o1, KeyFileState o2)
                {
                    return o1.getDepth() - o2.getDepth();
                }
            };
        fileDepthDescendingComparator = new Comparator<KeyFileState>() {
                @Override
                public int compare(KeyFileState o1, KeyFileState o2)
                {
                    return o1.getDepth() - o2.getDepth();
                }
            };

        updateCache(
            new HashMapSet<String,KeyFileState>(),
            SortMode.FILESIZE,
            SelectFirstMode.QUICK
            );
    }

    public void updateCache()
    {
        final int prevLastIndex;

        if( this.duplicatesFileCacheList.size() == 0 ) {
            prevLastIndex = 0;
            }
        else {
            prevLastIndex = this.duplicatesFileCacheList.size() - 1;
            }

        duplicatesFileCacheList.clear();

        for( Map.Entry<String,Set<KeyFileState>> e : getDuplicateFiles().entrySet() ) {
            Collection<KeyFileState> files = e.getValue();
            final KeyFileState       firstFile;

            switch( this.selectFirstMode ) {
                case QUICK :
                    firstFile = files.iterator().next();
                    break;

                case FILEDEPTH_ASCENDING_ORDER :
                    {
                    List<KeyFileState> l = new ArrayList<KeyFileState>( files );
                    Collections.sort( l, fileDepthAscendingComparator );

                    files = l;
                    firstFile = l.get( 0 );
                    }
                    break;

                case FILEDEPTH_DESCENDING_ORDER :
                    {
                    List<KeyFileState> l = new ArrayList<KeyFileState>( files );
                    Collections.sort( l, fileDepthDescendingComparator );

                    files = l;
                    firstFile = l.get( 0 );
                    }
                    break;

                default :
                    throw new UnsupportedOperationException(
                        "SelectFirstMode: " + this.selectFirstMode
                        );
                    //break;
                }

            duplicatesFileCacheList.add(
                new DefaultKeyFiles( e.getKey(), files, firstFile )
                );
            }

        Comparator<? super KeyFiles> cmp = null;

        switch( sortMode ) {
            case FIRST_FILENAME :
                cmp = this.filenameComparator;
                break;

            case FIRST_FILEPATH :
                cmp = this.pathComparator;
                break;

            case FILESIZE :
                cmp = this.sizeComparator;
                break;

            case FIRST_FILEDEPTH :
                cmp = this.depthComparator;
                break;

            case NUMBER_OF_DUPLICATE:
                cmp = this.numberOfDuplicateComparator;
                break;
            }

        if( cmp != null ) {
            try {
                Collections.sort( duplicatesFileCacheList, cmp );
                }
            catch( IllegalArgumentException e ) {
                LOGGER.error( "Can not sort : sortMode = " + sortMode, e );
                }
            }

        super.fireIntervalRemoved( this, 0, prevLastIndex );
        super.fireContentsChanged( this, 0, duplicatesFileCacheList.size() );
    }

    /**
     *
     * @param duplicateFiles
     */
    public void updateCache(
            final HashMapSet<String,KeyFileState> duplicateFiles, // $codepro.audit.disable declareAsInterface
            final SortMode                        sortMode,
            final SelectFirstMode                 selectFirstMode
            )
    {
        this.duplicateFiles  = duplicateFiles;
        this.sortMode        = sortMode;
        this.selectFirstMode = selectFirstMode;

        updateCache();
    }

    public SortMode getSortMode()
    {
        return this.sortMode;
    }

    public SelectFirstMode getSelectFirstMode()
    {
        return this.selectFirstMode;
    }

    /**
     * TODOC
     * @param sortMode
     */
    public void updateCache( SortMode sortMode )
    {
        this.sortMode = sortMode;
        updateCache();
    }

    /**
     * TODOC
     * @param selectFirstMode
     */
    public void updateCache( SelectFirstMode selectFirstMode )
    {
        this.selectFirstMode = selectFirstMode;
        updateCache();
    }

    @Override
    public int getSize()
    {
        return duplicatesFileCacheList.size();
    }

    @Override
    public KeyFiles getElementAt( int index )
    {
        return duplicatesFileCacheList.get( index );
    }

    public Set<KeyFileState> getStateSet( final String key )
    {
        return getDuplicateFiles().get( key );
    }

    public Set<Entry<String,Set<KeyFileState>>> getStateEntrySet()
    {
        return this.getDuplicateFiles().entrySet();
    }

    public Iterable<KeyFileState> getAllDuplicates()
    {
        return new Iterable<KeyFileState>()
        {
            @Override
            public Iterator<KeyFileState> iterator()
            {
                return getDuplicateFiles().iterator();
            }
        };
    }

    /**
     * @return the duplicateFiles
     */
    public HashMapSet<String,KeyFileState> getDuplicateFiles() // $codepro.audit.disable declareAsInterface
    {
        return duplicateFiles;
    }

    private JPanelResultKeyFileStateListModel listModelKeptIntact;
    private ListCellRenderer<KeyFileState> listCellRendererlKeptIntact;
    private JPanelResultKeyFileStateListModel listModelWillBeDeleted;
    private ListCellRenderer<KeyFileState> listCellRendererlWillBeDeleted;
    private String key;

    //not public
    KeyFileStateListModel getKeptIntactListModel()
    {
        if( listModelKeptIntact == null ) {
            listModelKeptIntact = new JPanelResultKeyFileStateListModel();
            }
        return listModelKeptIntact;
    }

    //not public
    ListCellRenderer<? super KeyFileState> getKeptIntactListCellRenderer()
    {
        if( listCellRendererlKeptIntact == null ) {
            listCellRendererlKeptIntact = new JPanelResultKeyFileStateListCellRenderer();
            }
        return listCellRendererlKeptIntact;
    }

    //not public
    KeyFileStateListModel getWillBeDeletedListModel()
    {
        if( listModelWillBeDeleted == null ) {
            listModelWillBeDeleted = new JPanelResultKeyFileStateListModel();
            }
        return listModelWillBeDeleted;
    }

    //not public
    ListCellRenderer<KeyFileState> getWillBeDeletedListCellRenderer()
    {
        if( listCellRendererlWillBeDeleted == null ) {
            listCellRendererlWillBeDeleted = new JPanelResultKeyFileStateListCellRenderer();
            }
        return listCellRendererlWillBeDeleted;
    }

    public void clearKeepDelete()
    {
        listModelKeptIntact.clear();
        listModelWillBeDeleted.clear();
    }

    public void setKeepDelete(
        final String            key,
        final Set<KeyFileState> s
        )
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "setKeepDelete: " + s );
            }
        this.key = key;

      SortedSet<KeyFileState> ss = new TreeSet<KeyFileState>();

      ss.addAll( s );

      listModelWillBeDeleted.clear();
      listModelKeptIntact.clear();

      for( KeyFileState sf : ss ) {
          if( sf.isSelectedToDelete() ) {
              listModelWillBeDeleted.private_add( sf );
              }
          else {
              listModelKeptIntact.private_add( sf );
              }
          }
      listModelWillBeDeleted.private_fireAddedAll();
      listModelKeptIntact.private_fireAddedAll();

      ss.clear();
    }

    public void clearSelected()
    {
        //logger.info( "clearSelected() start" );
        //
        // Update global list
        //
        for( KeyFileState kfs : getDuplicateFiles() ) {
            kfs.setSelectedToDelete( false );
            }

        //
        // Update current models
        //
        Set<KeyFileState> kfs = getDuplicateFiles().get( this.key );

        if( kfs != null ) {
            setKeepDelete( this.key, kfs );
            }
        // else no values

        // TODO: update display ??

        //logger.info( "clearSelected() done" );
    }

    public void refreshList()
    {
        Iterator<Entry<String, Set<KeyFileState>>> mainIterator = getDuplicateFiles().entrySet().iterator();
        int index  = 0;
        int index0 = -1;
        int index1 = -1;

        LOGGER.info( "duplicateFiles.size() = " + getDuplicateFiles().size() );

        while( mainIterator.hasNext() ) {
            Entry<String,Set<KeyFileState>> entry       = mainIterator.next();
            Set<KeyFileState>               kfsSet      = entry.getValue();
            Iterator<KeyFileState>          kfsIterator = kfsSet.iterator();

            while( kfsIterator.hasNext() ) {
                KeyFileState kfs = kfsIterator.next();

                if( ! kfs.getFile().exists() ) {
                    // File no more exist (delete by an other process)
                    LOGGER.info( "File \"" + kfs + "\" no more exist" );
                    kfsIterator.remove();
                    }
                }

            LOGGER.info( "kfsSet.size() = " + kfsSet.size() );

            if( kfsSet.size() < 2 ) {
                // No more duplicate here !
                mainIterator.remove();

                if( index0 < 0 ) {
                    index0 = index;
                    }
                index1 = index;
                }

            index++;
            }

        LOGGER.info( "duplicateFiles.size() = " + getDuplicateFiles().size() + " * index0=" + index0 + " index1=" + index1 );

        if( index0 >= 0 ) {
            updateCache();
            super.fireContentsChanged( this, index0, index1 );
            }
    }
}
