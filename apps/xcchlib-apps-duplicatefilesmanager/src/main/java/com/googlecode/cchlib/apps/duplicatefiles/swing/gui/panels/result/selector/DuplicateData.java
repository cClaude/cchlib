package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import java.io.Serializable;
import com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.JPanelResultListModel;

public interface DuplicateData extends Serializable
{
    JPanelResultListModel getListModelDuplicatesFiles();

    void updateDisplay();
}
