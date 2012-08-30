package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import javax.swing.ListModel;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;

/**
 *
 */
//public
interface KeyFileStateListModel extends ListModel<KeyFileState>
{
    public KeyFileState remove( int index );
}
