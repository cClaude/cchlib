package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractListModel;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;
import cx.ath.choisnet.tools.duplicatefiles.KeyFiles;
import cx.ath.choisnet.util.HashMapSet;

/**
 *
 */
public class JPanelResultDefaultListModel
    extends AbstractListModel<KeyFiles>
{
    private static final long serialVersionUID = 1L;
    private List<KeyFiles> duplicatesFileCacheList = new ArrayList<>();

    public JPanelResultDefaultListModel(
        final HashMapSet<String,KeyFileState> duplicateFiles
        )
    {
      for( Map.Entry<String,Set<KeyFileState>> e : duplicateFiles.entrySet() ) {
          String              k    = e.getKey();
          Set<KeyFileState>   sf   = e.getValue();

          duplicatesFileCacheList.add( new KeyFiles( k, sf ) );
//          listModelDuplicatesFiles.addElement(
//              new KeyFiles( k, sf )
//              );
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
}
