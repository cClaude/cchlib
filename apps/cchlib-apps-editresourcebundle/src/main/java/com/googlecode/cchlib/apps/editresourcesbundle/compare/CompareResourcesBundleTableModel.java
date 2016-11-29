// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.FilesConfig;
import com.googlecode.cchlib.apps.editresourcesbundle.files.CustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FormattedCustomProperties;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.table.LeftDotTableCellRenderer;

/**
 * {@link TableModel} for {@link CompareResourcesBundleFrame}
 */
class CompareResourcesBundleTableModel
    extends AbstractTableModel
{
    private static Logger LOGGER = Logger.getLogger(CompareResourcesBundleTableModel.class);
    private static final long serialVersionUID = 3L;

    public static class Colunms implements Serializable
    {
        private static final long serialVersionUID = 3L;

        private int   colunmCount;
        private int   colunmKey;   // column number for key
        private int[] colunmLine;  // column number for line number or -1 if hidden
        private int[] colunmValue; // column number for value

        protected int getColunmKey()
        {
            return colunmKey;
        }
        final int getColunmLineIndex( final int column )
        {//column == colunms.colunmRightLine
            for( int i = 0; i<colunmLine.length; i++ ) {
                if( column == colunmLine[ i ] ) {
                    return i;
                    }
                }
            return -1;
        }
        final int getColunmValueIndex( final int column )
        {//column == colunms.colunmRightValue
            for( int i = 0; i<colunmValue.length; i++ ) {
                if( column == colunmValue[ i ] ) {
                    return i;
                    }
                }
            throw new IllegalStateException( "column [" + column + "] not found" );
        }
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Colunms [colunmCount=");
            builder.append(colunmCount);
            builder.append(", colunmKey=");
            builder.append(colunmKey);
            builder.append(", colunmLine=");
            builder.append(Arrays.toString(colunmLine));
            builder.append(", colunmValue=");
            builder.append(Arrays.toString(colunmValue));
            builder.append(']');
            return builder.toString();
        }
    }
    private final Colunms colunms = new Colunms();

    @I18nString private final String txtNoFile = "<<NoFile>>";
    @I18nString private final String columnKeyNames = "Key";
    private final String[] columnOtherNames = {
            "#",
            "(%s%s)",
            };
    private final ArrayList<String> keyList = new ArrayList<String>();
    private final CustomProperties[] customProperties;
    private final AutoI18nCore       autoI18n;

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "CompareResourcesBundleTableModel [getColumnCount()=" );
        builder.append( getColumnCount() );
        builder.append( ", getRowCount()=" );
        builder.append( getRowCount() );
        builder.append( ']' );
        return builder.toString();
    }

    /**
     *
     * @param filesConfig
     * @param autoI18n
     */
    public CompareResourcesBundleTableModel(
        final FilesConfig   filesConfig,
        final AutoI18nCore  autoI18n
        )
    {
        this.autoI18n = autoI18n;

        // Perform internationalization
        autoI18n.performeI18n(this,this.getClass());

        final ChangeListener cpChangeLstener = e -> fireTableStructureChanged();

        customProperties = new CustomProperties[ filesConfig.getNumberOfFiles() ];

        for( int i = 0; i<customProperties.length; i++ ) {
            customProperties[ i ] = filesConfig.getCustomProperties( i );

            if( customProperties[ i ] == null ) {
                throw new IllegalArgumentException( "customProperties[" + i + "] is null" );
                }

            customProperties[ i ].addChangeListener( cpChangeLstener );

            LOGGER.info( "CP[" + i + "]=" + customProperties[ i ] );
            }

        showLineNumberIfPossible( filesConfig );

        final SortedSet<String> keyBuilderSet = new TreeSet<String>();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "colunms = " + colunms );
            }

        for( int index = 0; index<customProperties.length; index++ ) {
            keyBuilderSet.addAll( customProperties[ index ].stringPropertyNames() );
            }

        for( final String s : keyBuilderSet ) {
            keyList.add( s );
            }
        keyBuilderSet.clear();

        LOGGER.info( "keys:" + keyList.size()) ;
        LOGGER.info( "this:" + this  );
    }

    private void showLineNumberIfPossible( final FilesConfig filesConfig )
    {
        boolean showLineNumberIfPossible = filesConfig.isShowLineNumbers();

        if( showLineNumberIfPossible ) {
            boolean canNotShowLineNumber = true;

            for( int i = 0; i<filesConfig.getNumberOfFiles(); i++ ) {
                if( customProperties[ i ] instanceof FormattedCustomProperties ) {
                    canNotShowLineNumber = false;
                    break;
                    }
                }

            showLineNumberIfPossible = canNotShowLineNumber;
            }

        if( showLineNumberIfPossible ) {
            colunms.colunmCount      = 1 + ( customProperties.length * 2);
            colunms.colunmKey        = 0;
            colunms.colunmLine  = new int[ customProperties.length ];
            colunms.colunmValue = new int[ customProperties.length ];

            for( int i = 0; i<customProperties.length; i++ ) {
                colunms.colunmLine [ i ] = 1 + i;
                colunms.colunmValue[ i ] = 2 + i;
                }
            }
        else {
            colunms.colunmCount      = 1 + customProperties.length;
            colunms.colunmKey        = 0;
            colunms.colunmLine  = new int[ customProperties.length ];
            colunms.colunmValue = new int[ customProperties.length ];

            for( int i = 0; i<customProperties.length; i++ ) {
                colunms.colunmLine [ i ] = -1;
                colunms.colunmValue[ i ] = 1 + i;
                }
            }
    }

    @Override
    public int getColumnCount()
    {
        return colunms.colunmCount;
    }

    @Override
    public String getColumnName( final int column )
    {
        final int computedIndex;

        if( column == colunms.colunmKey ) {
            return this.columnKeyNames;
            }
        else if( colunms.getColunmLineIndex( column ) != -1 ) {
            return this.columnOtherNames[0];
            }
        else if( (computedIndex = colunms.getColunmValueIndex( column ) ) != -1 ) {
            String prefix;

            if( customProperties[ computedIndex ].isEdited() ) {
                prefix = "*";
                }
            else {
                prefix = StringHelper.EMPTY;
                }

            return String.format(
                this.columnOtherNames[1],
                prefix,
                customProperties[ computedIndex ].getFileObject().getDisplayName( txtNoFile )
                );
            }

        return null;
    }

    @Override
    public Class<?> getColumnClass( final int columnIndex )
    {
        if( colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return Integer.class;
            }

        return String.class;
    }

    @Override
    public boolean isCellEditable( final int rowIndex, final int columnIndex )
    {
        final int computedIndex;

        if( columnIndex == colunms.colunmKey ) {
            return false;
            }
        else if( colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return false;
            }
        else if( (computedIndex = colunms.getColunmValueIndex( columnIndex ) ) != -1 ) {
            if( this.customProperties[ computedIndex ].getFileObject().isReadOnly() ) {
                return false;
                }
            else {
                final String key = keyList.get( rowIndex );
                return isStringEditable( customProperties[ computedIndex ].getProperty( key ) );
                }
            }

        return false;
    }

    private boolean isStringEditable( final String str )
    {
        if( str == null ) {
            return true;
            }
        else if( str.contains( "\n" ) ) {
            return false;
            }
        else if( str.contains( "\r" ) ) {
            return false;
            }
        return true;
    }

    @Override
    public int getRowCount()
    {
        return keyList.size();
    }

    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex)
    {
        final String key = keyList.get( rowIndex );
        int          computedIndex;

        if( columnIndex == colunms.colunmKey ) {
            return key;
            }
        else if( (computedIndex = colunms.getColunmLineIndex( columnIndex )) != -1 ) {
            return Integer.valueOf( customProperties[ computedIndex ].getLineNumber( key ) );
            }
        else if( (computedIndex = colunms.getColunmValueIndex( columnIndex )) != -1 ) {
            return customProperties[ computedIndex ].getProperty( key );
            }

        return null;
    }

    @Override
    public void setValueAt( final Object aValue, final int rowIndex, final int columnIndex )
    {
        if( columnIndex == colunms.colunmKey ) {
            return;
            }
        if( colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return;
            }

        final String key           = keyList.get( rowIndex );
        final String value         = String.class.cast( aValue );
        final int    computedIndex;

        if( (computedIndex = colunms.getColunmValueIndex( columnIndex )) != -1 ) {
            customProperties[ computedIndex ].setProperty( key, value );
            }
    }

    public JTable getJTable()
    {
        final JTable jTable = new JTable(CompareResourcesBundleTableModel.this)
        {
            private static final long serialVersionUID = 1L;
            final MyJLabel myJLabel = new MyJLabel();
            final DefaultTableCellRenderer leftDotRenderer
                = new LeftDotTableCellRenderer()
                {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Component getTableCellRendererComponent(
                            final JTable  table,
                            final Object  value,
                            final boolean isSelected,
                            final boolean hasFocus,
                            final int     row,
                            final int     column
                            )
                        {
                            //Component c =
                            super.getTableCellRendererComponent(
                                    table,
                                    value,
                                    isSelected,
                                    hasFocus,
                                    row,
                                    column
                                    );
                            setBackground( Color.LIGHT_GRAY );

                            return this;
                        }
                };

            @Override
            public TableCellRenderer getCellRenderer( final int row, final int column )
            {
                if( column == colunms.colunmKey ) {
                    return leftDotRenderer;
                    }
                else if( colunms.getColunmValueIndex( column ) != -1 ) {
                    return myJLabel;
                    }
                else {
                    return super.getCellRenderer(row, column);
                    }
            }

            class MyJLabel extends JLabel implements TableCellRenderer
            {
                private static final long serialVersionUID = 1L;

                public MyJLabel()
                {
                    super();
                    setOpaque(true);
                }
                @Override
                public Component getTableCellRendererComponent(
                        final JTable  table,
                        final Object  value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int     row,
                        final int     column
                        )
                {
                    final String str = String.class.cast( value );

                    if( value == null ) {
                        setBackground( Color.RED );
                        }
                    else if( isStringEditable( str ) ) {
                        if( str.trim().length() == 0 ) {
                            // WhiteCharacters are ignored by properties
                            setBackground( Color.YELLOW );
                        }
                        else {
                            setBackground( Color.WHITE );
                            }
                        }
                    else {
                        setBackground( Color.LIGHT_GRAY );
                        }

                    setText( str );

                    return this;
                }
            // The following methods override the defaults for performance reasons
            @Override public void validate() {}
            @Override public void revalidate() {}
            @Override protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {}
            @Override public void firePropertyChange(final String propertyName, final boolean oldValue, final boolean newValue) {}
            }
        };

        final CompareResourcesBundlePopupMenu popupMenu
            = new CompareResourcesBundlePopupMenu(
                    jTable,
                    this,
                    this.colunms,
                    autoI18n
                    );

        popupMenu.addMenu();

        return jTable;
    }

    public CustomProperties getCustomProperties( final int index )
    {
        return this.customProperties[ index ];
    }

    public void setColumnWidth( final JTable table ) // $codepro.audit.disable blockDepth
    {//Not Override
        final Font        font    = table.getFont();
        final FontMetrics fm      = table.getFontMetrics( font );

        for( int ci = 0; ci < getColumnCount(); ci++ ) {
            int maxWidth = 0;

            // Find max for this column
            for( int ri = 0; ri < keyList.size(); ri++ ) {
                final Object content = getValueAt( ri, ci );

                if( content instanceof String ) {
                    final String s = String.class.cast( content );

                    final int width = fm.stringWidth( s );

                    if( width > maxWidth ) {
                        maxWidth = width;
                        }
                    }
                }

            final TableColumn column = table.getColumnModel().getColumn( ci );
            column.setPreferredWidth( maxWidth );
            }
    }
}