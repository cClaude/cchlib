package com.googlecode.cchlib.apps.emptyfiles.panel.remove;

import java.io.File;
import java.util.Date;
import java.util.function.BiFunction;
import javax.swing.Icon;
import com.googlecode.cchlib.swing.table.JTableColumnsAutoSizer;

enum Columns
{
    FILE_ICON(
        16 + JTableColumnsAutoSizer.DEFAULT_COLUMN_MARGIN,
        (m,f) -> m.getIconValue( f ),
        Icon.class
        ),
    FILE_SELECTED( (m,f) -> m.getSelectedValue( f ), Boolean.class ),
    FILE_FILE( (m,f) -> f, String.class ),
    FILE_SIZE( (m,f) -> m.getFileInfo( f ).getLengthString(), String.class ),
    FILE_DATE( (m,f) -> m.getFileInfo( f ).getLastModifiedDate(), Date.class ),
    FILE_ATTR( (m,f) -> m.getFileInfo( f ).getFileAttributsString(), String.class ),
    ;
    private final int                                       forceColumnWidth;
    private final BiFunction<WorkingTableModel,File,Object> valueShowView;
    private final Class<?>                                  type;

    private Columns(
        final BiFunction<WorkingTableModel,File,Object> valueShowView,
        final Class<?>                                  type
        )
    {
        this( 0, valueShowView, type );
    }

    private Columns(
        final int                                       forceColumnWidth,
        final BiFunction<WorkingTableModel,File,Object> valueShowView,
        final Class<?>                                  type
        )
    {
        this.forceColumnWidth = forceColumnWidth;
        this.valueShowView    = valueShowView;
        this.type             = type;
    }

    public int getForceColumnWidth()
    {
        return this.forceColumnWidth;
    }

    public Object getValue( final WorkingTableModel model, final File file )
    {
        return this.valueShowView.apply( model, file );
    }

    public Class<?> getColumnClass()
    {
        return this.type;
    }

    public String getLabel( final String[] columnNames )
    {
        return columnNames[ super.ordinal() ];
    }
}
