package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.JPanelResultListModel;

public interface DuplicateData
{
    JPanelResultListModel getListModelDuplicatesFiles();
    void updateDisplay();
}
