package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import com.googlecode.cchlib.swing.table.LeftDotTableCellRenderer;

/**
 * {@link TableModel} for {@link CompareResourcesBundleFrame}
 *
 */
class CompareResourcesBundleTableModel
    extends AbstractTableModel
{
    private static Logger logger = Logger.getLogger(CompareResourcesBundleTableModel.class);
    private static final long serialVersionUID = 2L;

    public class Colunms implements Serializable
    {
        private static final long serialVersionUID = 1L;
        int colunmCount;
        int colunmKey;
        int colunmLeftLine;
        int colunmLeftValue;
        int colunmRightLine;
        int colunmRightValue;
    }
    private Colunms colunms = new Colunms();

    @I18nString private String txtNoFile = "<<NoFile>>";
    @I18nString
    private String[] columnNames = {
            "Key",
            "#",
            "(%s%s)",
            "#",
            "(%s%s)"
            };
    private final ArrayList<String> keyList = new ArrayList<String>();
    private CustomProperties leftCProperties;
    private CustomProperties rightCProperties;
    private AutoI18n autoI18n;

    /**
     *
     * @param filesConfig
     * @param autoI18n
     */
    public CompareResourcesBundleTableModel(
        final FilesConfig   filesConfig,
        final AutoI18n      autoI18n
        )
    {
        this.autoI18n = autoI18n;

        // Perform internationalization
        autoI18n.performeI18n(this,this.getClass());

        leftCProperties  = filesConfig.createLeftCustomProperties();
        rightCProperties = filesConfig.createRightCustomProperties();

        if(leftCProperties == null || rightCProperties == null) {
            throw new IllegalArgumentException();
            }

        final ChangeListener cpChangeLstener = new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                //logger.info( "fireTableStructureChanged()" );
                fireTableStructureChanged();
            }
        };
        leftCProperties.addChangeListener(cpChangeLstener);
        rightCProperties.addChangeListener(cpChangeLstener);

        boolean showLineNumberIfPossible = filesConfig.isShowLineNumbers();

        if( showLineNumberIfPossible ) {
            if( filesConfig.getLeftFormattedProperties() != null
                    && filesConfig.getRightFormattedProperties() != null
                    ) {
                colunms.colunmCount      = 5;
                colunms.colunmKey        = 0;
                colunms.colunmLeftLine   = 1;
                colunms.colunmLeftValue  = 2;
                colunms.colunmRightLine  = 3;
                colunms.colunmRightValue = 4;
                }
            else {
                // Not supported
                showLineNumberIfPossible = false;
                }
            }

        if( !showLineNumberIfPossible ) {
            colunms.colunmCount      = 3;
            colunms.colunmKey        = 0;
            colunms.colunmLeftLine   = -1;
            colunms.colunmLeftValue  = 1;
            colunms.colunmRightLine  = -1;
            colunms.colunmRightValue = 2;
            }

        SortedSet<String> keyBuilderSet = new TreeSet<String>();

        logger.info( "leftCProperties.stringPropertyNames()=" + leftCProperties.stringPropertyNames() );
        keyBuilderSet.addAll( leftCProperties.stringPropertyNames() );
        keyBuilderSet.addAll( rightCProperties.stringPropertyNames() );

        for(String s:keyBuilderSet) {
            keyList.add( s );
            }
        keyBuilderSet.clear();

        logger.info("keys:" + keyList.size());
    }

    @Override
    public int getColumnCount()
    {
        return colunms.colunmCount;
    }

    @Override
    public String getColumnName(int column)
    {
        if( column == colunms.colunmKey ) {
            return this.columnNames[0];
            }
        else if( column == colunms.colunmLeftLine ) {
            return this.columnNames[1];
            }
        else if( column == colunms.colunmLeftValue ) {
            String prefix;

            if( leftCProperties.isEdited() ) {
                prefix = "*";
                }
            else {
                prefix = "";
                }

            return String.format(
                this.columnNames[2],
                prefix,
                leftCProperties.getFileObject().getDisplayName( txtNoFile )
                );
            }
        else if( column == colunms.colunmRightLine ) {
            return this.columnNames[3];
            }
        else if( column == colunms.colunmRightValue ) {
            String prefix;

            if( rightCProperties.isEdited() ) {
                prefix = "*";
                }
            else {
                prefix = "";
                }

            return String.format(
                this.columnNames[4],
                prefix,
                rightCProperties.getFileObject().getDisplayName( txtNoFile )
                );
            }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if( columnIndex == colunms.colunmLeftLine ) {
            return Integer.class;
        }
        else if( columnIndex == colunms.colunmRightLine ) {
            return Integer.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if( columnIndex == colunms.colunmKey ) {
            return false;
            }
        else if( columnIndex == colunms.colunmLeftLine ) {
            return false;
            }
        else if( columnIndex == colunms.colunmLeftValue ) {
            if( this.leftCProperties.getFileObject().isReadOnly() ) {
                return false;
                }
             else {
                 String key = keyList.get( rowIndex );
                 return isStringEditable( leftCProperties.getProperty( key ) );
                 }
            }
        else if( columnIndex == colunms.colunmRightLine ) {
            return false;
            }
        else if( columnIndex == colunms.colunmRightValue ) {
            if( this.rightCProperties.getFileObject().isReadOnly() ) {
                return false;
                }
            else {
                String key = keyList.get( rowIndex );
                return isStringEditable( rightCProperties.getProperty( key ) );
                }
            }
        return false;
    }

    private boolean isStringEditable(String str)
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
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        String key = keyList.get( rowIndex );

        if( columnIndex == colunms.colunmKey ) {
            return key;
            }
        else if( columnIndex == colunms.colunmLeftLine ) {
            return leftCProperties.getLineNumber( key );
            }
        else if( columnIndex == colunms.colunmLeftValue ) {
            return leftCProperties.getProperty( key );
            }
        else if( columnIndex == colunms.colunmRightLine ) {
            return rightCProperties.getLineNumber( key );
            }
        else if( columnIndex == colunms.colunmRightValue ) {
            return rightCProperties.getProperty( key );
            }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if( columnIndex == colunms.colunmKey ) {
            return;
            }
        if( columnIndex == colunms.colunmLeftLine ) {
            return;
            }
        if( columnIndex == colunms.colunmRightLine ) {
            return;
            }

        String key   = keyList.get( rowIndex );
        String value = String.class.cast( aValue );

        if( columnIndex == colunms.colunmLeftValue ) {
            leftCProperties.setProperty( key, value );
            }
        else if( columnIndex == colunms.colunmRightValue ) {
            rightCProperties.setProperty( key, value );
            }
    }

    public JTable getJTable()
    {
        JTable jTable = new JTable(CompareResourcesBundleTableModel.this)
        {
            private static final long serialVersionUID = 1L;
            final MyJLabel myJLabel = new MyJLabel();
            final DefaultTableCellRenderer leftDotRenderer
                = new LeftDotTableCellRenderer()
                {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable  table,
                            Object  value,
                            boolean isSelected,
                            boolean hasFocus,
                            int     row,
                            int     column
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
                            return this; //c;
                        }
                };

            @Override
            public TableCellRenderer getCellRenderer(int row, int column)
            {
                if( column == colunms.colunmKey ) {
                    return leftDotRenderer;
                    }
                else if( column == colunms.colunmLeftValue ) {
                    return myJLabel;
                    }
                else if( column == colunms.colunmRightValue ) {
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
                        JTable  table,
                        Object  value,
                        boolean isSelected,
                        boolean hasFocus,
                        int     row,
                        int     column
                        )
                {
                    String str = String.class.cast( value );

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

                    //slogger.info( "value = " + value );
                    setText( str );

                    return this;
                }
            // The following methods override the defaults for performance reasons
            @Override public void validate() {}
            @Override public void revalidate() {}
            @Override protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
            @Override public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
            }
        };

//        JPopupMenuAssistant             assistant   = new JPopupMenuAssistant();
//        CompareRessourceBundlePopupMenu popup       = new CompareRessourceBundlePopupMenu( jTable, assistant );
//
//        assistant.installListener( jTable );
        CompareResourcesBundlePopupMenu popupMenu
            = new CompareResourcesBundlePopupMenu(
                    jTable,
                    this,
                    this.colunms,
                    autoI18n
                    );

        popupMenu.setMenu();

        return jTable;
    }


//    public boolean saveLeftFile( FileObject  fileObject )
//        throws FileNotFoundException, IOException
//    {//Not Override
//        slogger.info( "trying to save left: " + fileObject );
//
//        return this.leftCProperties.store();
//     }

    public CustomProperties getLeftCustomProperties()
    {
        return this.leftCProperties;
    }

//    public boolean saveRightFile( FileObject fileObject )
//        throws FileNotFoundException, IOException
//    {//Not Override
//        slogger.info( "trying to save right: " + fileObject );
//
//        return this.rightCProperties.store();
//    }

    public CustomProperties getRightCustomProperties()
    {
        return this.rightCProperties;
    }


    public void setColumnWidth(JTable table)
    {//Not Override
        Font        font    = table.getFont();
        FontMetrics fm      = table.getFontMetrics( font );

        for( int ci = 0; ci < getColumnCount(); ci++ ) {
            int maxWidth = 0;

            // Find max for this column
            for( int ri = 0; ri < keyList.size(); ri++ ) {
                Object content = getValueAt( ri, ci );

                if( content instanceof String ) {
                    String s = String.class.cast( content );

                    int width = fm.stringWidth( s );

                    if( width > maxWidth ) {
                        maxWidth = width;
                        }
                    }
                }

            TableColumn column = table.getColumnModel().getColumn( ci );
            column.setPreferredWidth( maxWidth );
            }

//        for( int i = 0; i < getColumnCount(); i++ ) {
//            TableColumn column = table.getColumnModel().getColumn( i );
//
//            if( i == colunms.colunmKey ) {
//                column.setPreferredWidth( 100 );
//                }
//            else if( i == colunms.colunmLeftLine ) {
//                column.setPreferredWidth( 30 );
//                }
//            else if( i == colunms.colunmLeftValue ) {
//                column.setPreferredWidth( 300 );
//                }
//            else if( i == colunms.colunmRightLine ) {
//                column.setPreferredWidth( 30 );
//                }
//            else if( i == colunms.colunmRightValue ) {
//                column.setPreferredWidth( 300 );
//                }
//        }
    }
}
