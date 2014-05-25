package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import javax.swing.table.TableModel;

// NOT public
interface CurrentFilesTableModel extends TableModel
{
    void setColumnName( int columnLabel, String string );

    void clear();
}
