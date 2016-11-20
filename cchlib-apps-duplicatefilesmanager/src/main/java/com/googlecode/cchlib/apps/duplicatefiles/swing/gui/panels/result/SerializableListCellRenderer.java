package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result;

import java.io.Serializable;
import javax.swing.ListCellRenderer;

interface SerializableListCellRenderer<T>
    extends ListCellRenderer<T>, Serializable
{
    // To be able to have ListCellRenderer Serializable object
}
