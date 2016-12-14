package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import javax.swing.ListModel;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;

/**
 *
 */
interface KeyFileStateListModel extends ListModel<KeyFileState>
{
    KeyFileState remove( int index );
}
