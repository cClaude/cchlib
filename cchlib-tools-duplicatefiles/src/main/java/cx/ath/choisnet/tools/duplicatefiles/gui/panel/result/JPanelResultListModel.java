package cx.ath.choisnet.tools.duplicatefiles.gui.panel.result;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
import org.apache.log4j.Logger;

import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.duplicatefiles.KeyFiles;
import cx.ath.choisnet.util.HashMapSet;

/**
 *
 */
public class JPanelResultListModel
    extends AbstractListModel<KeyFiles>
{
    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger( JPanelResultListModel.class );
    private HashMapSet<String,KeyFileState> duplicateFiles;
    private SortMode sortMode;

    private List<KeyFiles> duplicatesFileCacheList = new ArrayList<>();

    private Comparator<? super KeyFiles> filenameComparator;
    private Comparator<? super KeyFiles> pathComparator;
    private Comparator<? super KeyFiles> sizeComparator;

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

        updateCache(
            new HashMapSet<String,KeyFileState>(),
            SortMode.FILESIZE
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

        for( Map.Entry<String,Set<KeyFileState>> e : duplicateFiles.entrySet() ) {
            duplicatesFileCacheList.add(
                new DefaultKeyFiles( e.getKey(), e.getValue() )
                );
            }

        Comparator<? super KeyFiles> cmp;

        switch( sortMode ) {
            case FIRST_FILENAME :
                cmp = this.filenameComparator;
                break;

            case FIRST_FILEPATH :
                cmp = this.pathComparator;
                break;

            default : //case FILESIZE :
                cmp = this.sizeComparator;
                break;
            }
        Collections.sort( duplicatesFileCacheList, cmp );

        super.fireIntervalRemoved( this, 0, prevLastIndex );
        super.fireContentsChanged( this, 0, duplicatesFileCacheList.size() );
    }

    /**
     *
     * @param duplicateFiles
     */
    public void updateCache(
            final HashMapSet<String,KeyFileState> duplicateFiles,
            final SortMode                        sortMode
            )
        {
            this.duplicateFiles = duplicateFiles;
            this.sortMode = sortMode;
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
        return duplicateFiles.get( key );
    }

    public Set<Entry<String,Set<KeyFileState>>> getStateEntrySet()
    {
        return this.duplicateFiles.entrySet();
    }

    public Iterable<KeyFileState> iter()
    {
        return new Iterable<KeyFileState>()
        {
            @Override
            public Iterator<KeyFileState> iterator()
            {
                return duplicateFiles.iterator();
            }
        };
    }

    private JPanelResultKeyFileStateListModel listModelKeptIntact;
    private JPanelResultKeyFileStateListModel listModelWillBeDeleted;
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
    KeyFileStateListModel getWillBeDeletedListModel()
    {
        if( listModelWillBeDeleted == null ) {
            listModelWillBeDeleted = new JPanelResultKeyFileStateListModel();
            }
        return listModelWillBeDeleted;
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
        this.key = key;

        if( logger.isTraceEnabled() ) {
            logger.trace( "setKeepDelete: " + s );
            }
//      Set<KeyFileState>       s  = duplicateFiles.get( key );
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
        for( KeyFileState kfs : duplicateFiles ) {
            kfs.setSelectedToDelete( false );
            }

        //
        // Update current models
        //
        Set<KeyFileState> kfs = duplicateFiles.get( this.key );

        setKeepDelete( this.key, kfs );

        //logger.info( "clearSelected() done" );
    }

}
