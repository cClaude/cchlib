package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
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
    private HashMapSet<String,KeyFileState> duplicateFiles;
    private List<KeyFiles> duplicatesFileCacheList = new ArrayList<>();

    public JPanelResultListModel(
        final HashMapSet<String,KeyFileState> duplicateFiles
        )
    {
        this.duplicateFiles = duplicateFiles;

        for( Map.Entry<String,Set<KeyFileState>> e : duplicateFiles.entrySet() ) {
            String              k    = e.getKey();
            Set<KeyFileState>   sf   = e.getValue();

            duplicatesFileCacheList.add( new KeyFiles( k, sf ) );
            }
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
    //private List<KeyFileState> listKeptIntact = new ArrayList<>();
    private JPanelResultKeyFileStateListModel listModelWillBeDeleted;
    //private List<KeyFileState> listWillBeDeleted = new ArrayList<>();

    public KeyFileStateListModel getKeptIntactListModel()
    {
        if( listModelKeptIntact == null ) {
            listModelKeptIntact = new JPanelResultKeyFileStateListModel();
            }
        return listModelKeptIntact;
    }

    public KeyFileStateListModel getWillBeDeletedListModel()
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

    public void setKeepDelete( Set<KeyFileState> s )
    {
//      Set<KeyFileState>       s  = duplicateFiles.get( key );
      SortedSet<KeyFileState> ss = new TreeSet<KeyFileState>();

      ss.addAll( s );

      for( KeyFileState sf : ss ) {
          if( sf.isSelectedToDelete() ) {
              listModelWillBeDeleted.add( sf );
              }
          else {
              listModelKeptIntact.add( sf );
              }
          }
      ss.clear();
    }

}
