package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import javax.swing.ListModel;
import cx.ath.choisnet.tools.duplicatefiles.KeyFileState;

/**
 *
 */
public interface KeyFileStateListModel extends ListModel<KeyFileState>
{
    public KeyFileState remove( int index );
}
