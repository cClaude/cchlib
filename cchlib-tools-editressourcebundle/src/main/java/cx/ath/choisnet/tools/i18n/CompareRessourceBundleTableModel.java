/**
 *
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.Color;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;

import cx.ath.choisnet.swing.table.LeftDotTableCellRenderer;

/**
 * {@link TableModel} for {@link CompareRessourceBundleFrame}
 *
 * @author Claude CHOISNET
 */
class CompareRessourceBundleTableModel
    extends AbstractTableModel
{
    private static Logger slogger = Logger.getLogger(CompareRessourceBundleTableModel.class);
    private static final long serialVersionUID = 1L;

    public class Colunms implements Serializable
    {
        private static final long serialVersionUID = 1L;
        /** @serial */
        int colunmCount;
        /** @serial */
        int colunmKey;
        /** @serial */
        int colunmLeftLine;
        /** @serial */
        int colunmLeftValue;
        /** @serial */
        int colunmRightLine;
        /** @serial */
        int colunmRightValue;
    }
    /** @serial */
    private Colunms colunms = new Colunms();

    /** @serial */
    private String[] columnNames = {
                "Key",
                "#",
                "(%s)",
                "#",
                "(%s)"
                };
    /** @serial */
    private final ArrayList<String> keyList = new ArrayList<String>();
    /** @serial */
    private CustomPropertiesInterface leftCProperties;
    /** @serial */
    private CustomPropertiesInterface rightCProperties;
    
    /**
     * 
     * @param filesConfig 
     */
    public CompareRessourceBundleTableModel(
            FilesConfig filesConfig
            )
    {
        leftCProperties  = filesConfig.createLeftCustomProperties();
        rightCProperties = filesConfig.createRightCustomProperties();

        if(leftCProperties == null || rightCProperties == null) {
            throw new IllegalArgumentException();
        }
        
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

        keyBuilderSet.addAll( leftCProperties.stringPropertyNames() );
        keyBuilderSet.addAll( rightCProperties.stringPropertyNames() );

        for(String s:keyBuilderSet) {
            keyList.add( s );
        }
        keyBuilderSet.clear();
        
        slogger.info("keys:" + keyList.size());
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
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
            return String.format( this.columnNames[2],leftCProperties.getFileObject().getDisplayName());
        }
        else if( column == colunms.colunmRightLine ) {
            return this.columnNames[3];
        }
        else if( column == colunms.colunmRightValue ) {
            return String.format( this.columnNames[4],rightCProperties.getFileObject().getDisplayName());
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
        JTable jTable = new JTable(CompareRessourceBundleTableModel.this)
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
        CompareRessourceBundlePopupMenu popupMenu
            = new CompareRessourceBundlePopupMenu( 
                    jTable,
                    this,
                    this.colunms
                    );
        
        popupMenu.setMenu();
        
        return jTable;
    }

    
    public boolean saveLeftFile( FileObject  fileObject )
        throws FileNotFoundException, IOException
    {//Not Override
        slogger.info( "trying to save left: " + fileObject );
        
        return this.leftCProperties.store();
     }

    public boolean saveRightFile( FileObject fileObject )
        throws FileNotFoundException, IOException
    {//Not Override
        slogger.info( "trying to save right: " + fileObject );

        return this.rightCProperties.store();
    }

    public void setColumnWidth(JTable table)
    {//Not Override
        TableColumn column = null;

        for( int i = 0; i < getColumnCount(); i++ ) {
            column = table.getColumnModel().getColumn( i );
            
            if( i == colunms.colunmKey ) {
                column.setPreferredWidth( 100 );
            }
            else if( i == colunms.colunmLeftLine ) {
                column.setPreferredWidth( 30 );
            }
            else if( i == colunms.colunmLeftValue ) {
                column.setPreferredWidth( 300 );
            }
            else if( i == colunms.colunmRightLine ) {
                column.setPreferredWidth( 30 );
            }
            else if( i == colunms.colunmRightValue ) {
                column.setPreferredWidth( 300 );
            }
        }
    }
}
