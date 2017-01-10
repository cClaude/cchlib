package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.FilesConfig;
import com.googlecode.cchlib.apps.editresourcesbundle.files.CustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FormattedCustomProperties;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.AutoI18n;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.lang.StringHelper;

/**
 * {@link TableModel} for {@link CompareResourcesBundleFrame}
 * <p>
 * public for ResourceBuilder only
 */
@I18nName("CompareResourcesBundleTableModel")
public class CompareResourcesBundleTableModel
    extends AbstractTableModel
        implements I18nAutoUpdatable
{
    private static final Logger LOGGER = Logger.getLogger(CompareResourcesBundleTableModel.class);
    private static final long serialVersionUID = 3L;

    public static class Colunms implements Serializable
    {
        private static final long serialVersionUID = 3L;

        private int   colunmCount;
        private int[] colunmLine;  // column number for line number or -1 if hidden
        private int[] colunmValue; // column number for value
        private int   colunmKey;   // column number for key

        protected int getColunmKey()
        {
            return this.colunmKey;
        }

        final int getColunmLineIndex( final int column )
        {//column == colunms.colunmRightLine
            for( int i = 0; i<this.colunmLine.length; i++ ) {
                if( column == this.colunmLine[ i ] ) {
                    return i;
                    }
                }
            return -1;
        }

        final int getColunmValueIndex( final int column )
        {//column == colunms.colunmRightValue
            for( int i = 0; i<this.colunmValue.length; i++ ) {
                if( column == this.colunmValue[ i ] ) {
                    return i;
                    }
                }
            throw new IllegalStateException( "column [" + column + "] not found" );
        }

        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();

            builder.append("Colunms [colunmCount=");
            builder.append(this.colunmCount);
            builder.append(", colunmKey=");
            builder.append(this.colunmKey);
            builder.append(", colunmLine=");
            builder.append(Arrays.toString(this.colunmLine));
            builder.append(", colunmValue=");
            builder.append(Arrays.toString(this.colunmValue));
            builder.append(']');

            return builder.toString();
        }
    }

    final Colunms colunms = new Colunms();

    @I18nString private String txtNoFile;
    @I18nString private String columnKeyNames;
    private final String[] columnOtherNames = {
            "#",
            "(%s%s)",
            };
    private final ArrayList<String>  keyList = new ArrayList<>();
    private final CustomProperties[] customProperties;
    //private final AutoI18n           autoI18n;

    /**
     *
     * @param filesConfig
     * @param autoI18n
     */
    public CompareResourcesBundleTableModel(
        final FilesConfig filesConfig
        )
    {
        beSureNotFinal();

        this.customProperties = new CustomProperties[ filesConfig.getNumberOfFiles() ];

        for( int i = 0; i<this.customProperties.length; i++ ) {
            this.customProperties[ i ] = filesConfig.getCustomProperties( i );

            if( this.customProperties[ i ] == null ) {
                throw new IllegalStateException( "customProperties[" + i + "] is null" );
                }

            this.customProperties[ i ].addChangeListener( e -> fireTableStructureChanged() );

            LOGGER.info( "CP[" + i + "]=" + this.customProperties[ i ] );
            }

        showLineNumberIfPossible( filesConfig );

        final SortedSet<String> keyBuilderSet = new TreeSet<>();

        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "colunms = " + this.colunms );
            }

        for( int index = 0; index<this.customProperties.length; index++ ) {
            keyBuilderSet.addAll( this.customProperties[ index ].stringPropertyNames() );
            }

        for( final String s : keyBuilderSet ) {
            this.keyList.add( s );
            }

        keyBuilderSet.clear();

        LOGGER.info( "keys:" + this.keyList.size()) ;
        LOGGER.info( "this:" + this  );
    }

    private void beSureNotFinal()
    {
        this.txtNoFile      = "<<NoFile>>";
        this.columnKeyNames = "Key";
    }

    private void showLineNumberIfPossible( final FilesConfig filesConfig )
    {
        boolean showLineNumberIfPossible = filesConfig.isShowLineNumbers();

        if( showLineNumberIfPossible ) {
            boolean canNotShowLineNumber = true;

            for( int i = 0; i<filesConfig.getNumberOfFiles(); i++ ) {
                if( this.customProperties[ i ] instanceof FormattedCustomProperties ) {
                    canNotShowLineNumber = false;
                    break;
                    }
                }

            showLineNumberIfPossible = canNotShowLineNumber;
            }

        if( showLineNumberIfPossible ) {
            this.colunms.colunmCount = 1 + ( this.customProperties.length * 2);
            this.colunms.colunmKey   = 0;
            this.colunms.colunmLine  = new int[ this.customProperties.length ];
            this.colunms.colunmValue = new int[ this.customProperties.length ];

            for( int i = 0; i<this.customProperties.length; i++ ) {
                this.colunms.colunmLine [ i ] = 1 + i;
                this.colunms.colunmValue[ i ] = 2 + i;
                }
            }
        else {
            this.colunms.colunmCount = 1 + this.customProperties.length;
            this.colunms.colunmKey   = 0;
            this.colunms.colunmLine  = new int[ this.customProperties.length ];
            this.colunms.colunmValue = new int[ this.customProperties.length ];

            for( int i = 0; i<this.customProperties.length; i++ ) {
                this.colunms.colunmLine [ i ] = -1;
                this.colunms.colunmValue[ i ] = 1 + i;
                }
            }
    }

    @Override
    public int getColumnCount()
    {
        return this.colunms.colunmCount;
    }

    @Override
    public String getColumnName( final int column )
    {
        final int computedIndex;

        if( column == this.colunms.colunmKey ) {
            return this.columnKeyNames;
            }
        else if( this.colunms.getColunmLineIndex( column ) != -1 ) {
            return this.columnOtherNames[0];
            }
        else if( (computedIndex = this.colunms.getColunmValueIndex( column ) ) != -1 ) {
            String prefix;

            if( this.customProperties[ computedIndex ].isEdited() ) {
                prefix = "*";
                }
            else {
                prefix = StringHelper.EMPTY;
                }

            return String.format(
                this.columnOtherNames[1],
                prefix,
                this.customProperties[ computedIndex ].getFileObject().getDisplayName( this.txtNoFile )
                );
            }

        return null;
    }

    @Override
    public Class<?> getColumnClass( final int columnIndex )
    {
        if( this.colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return Integer.class;
            }

        return String.class;
    }

    @Override
    public boolean isCellEditable( final int rowIndex, final int columnIndex )
    {
        final int computedIndex;

        if( columnIndex == this.colunms.colunmKey ) {
            return false;
            }
        else if( this.colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return false;
            }
        else if( (computedIndex = this.colunms.getColunmValueIndex( columnIndex ) ) != -1 ) {
            if( this.customProperties[ computedIndex ].getFileObject().isReadOnly() ) {
                return false;
                }
            else {
                final String key = this.keyList.get( rowIndex );
                return isStringEditable( this.customProperties[ computedIndex ].getProperty( key ) );
                }
            }

        return false;
    }


    @Override
    public int getRowCount()
    {
        return this.keyList.size();
    }

    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex)
    {
        final String key = this.keyList.get( rowIndex );
        int          computedIndex;

        if( columnIndex == this.colunms.colunmKey ) {
            return key;
            }
        else if( (computedIndex = this.colunms.getColunmLineIndex( columnIndex )) != -1 ) {
            return Integer.valueOf( this.customProperties[ computedIndex ].getLineNumber( key ) );
            }
        else if( (computedIndex = this.colunms.getColunmValueIndex( columnIndex )) != -1 ) {
            return this.customProperties[ computedIndex ].getProperty( key );
            }

        return null;
    }

    @Override
    public void setValueAt( final Object aValue, final int rowIndex, final int columnIndex )
    {
        if( columnIndex == this.colunms.colunmKey ) {
            return;
            }
        if( this.colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return;
            }

        final String key           = this.keyList.get( rowIndex );
        final String value         = String.class.cast( aValue );
        final int    computedIndex;

        if( (computedIndex = this.colunms.getColunmValueIndex( columnIndex )) != -1 ) {
            this.customProperties[ computedIndex ].setProperty( key, value );
            }
    }

    public CompareResourcesBundleTable getJTable()
    {
        return new CompareResourcesBundleTable( this, this.colunms );
    }

    public CustomProperties getCustomProperties( final int index )
    {
        return this.customProperties[ index ];
    }

    public void setColumnWidth( final JTable table )
    {// Not Override
        final Font font = table.getFont();
        final FontMetrics fm = table.getFontMetrics( font );

        for( int ci = 0; ci < getColumnCount(); ci++ ) {
            int maxWidth = 0;

            // Find max for this column
            for( int ri = 0; ri < this.keyList.size(); ri++ ) {
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

    static boolean isStringEditable( final String str )
    {
        if( str == null ) {
            return true;
            }
        else if( str.contains( "\n" ) || str.contains( "\r" ) ) {
            return false;
            }

        return true;
    }

    @Override // I18nAutoUpdatable
    public void performeI18n( final AutoI18n autoI18n )
    {
        // Perform internationalization
        autoI18n.performeI18n(this,this.getClass());
    }
}
