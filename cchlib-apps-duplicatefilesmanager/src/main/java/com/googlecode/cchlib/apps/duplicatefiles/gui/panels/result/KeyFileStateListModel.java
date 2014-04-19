package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import javax.swing.ListModel;

/**
 *
 */
interface KeyFileStateListModel extends ListModel<KeyFileState>
{
    KeyFileState remove( int index );
}
