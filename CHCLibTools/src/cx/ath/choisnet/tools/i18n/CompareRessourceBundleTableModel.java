/**
 *
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.Color;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import cx.ath.choisnet.swing.table.LeftDotRenderer;

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

    /** @serial */
    private String[] columnNames  = {
                            "Key",
                            "Left value (%s)",
                            "Right value (%s)"
                            };
    /** @serial */
    private final ArrayList<String> keyList = new ArrayList<String>();
    /** @serial */
    private CustomProperties leftCProperties;
    /** @serial */
    private CustomProperties rightCProperties;
    
    /**
     * 
     * @param leftFile
     * @param rightFile
     * @throws IOException
     * @throws FileNotFoundException
     */
    public CompareRessourceBundleTableModel(
            FileObject leftFile,
            FileObject rightFile
            )
    throws FileNotFoundException, IOException
    {
        SortedSet<String> keyBuilderSet = new TreeSet<String>();

        this.leftCProperties = new PropertiesStream(leftFile);
        this.leftCProperties.load(keyBuilderSet);

        this.rightCProperties = new PropertiesStream(rightFile);
        this.rightCProperties.load(keyBuilderSet);
        
        for(String s:keyBuilderSet) {
            keyList.add( s );
        }
        keyBuilderSet.clear();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount()
    {
        //slogger.info( "getColumnCount() = " + colmunDisplay.length);
        return columnNames.length;
    }
    @Override
    public String getColumnName(int column)
    {
        switch( column ) {
            case 0 : return this.columnNames[column];
            case 1 : return String.format( this.columnNames[column],leftCProperties.getFileObject().getDisplayName());
            case 2 : return String.format( this.columnNames[column],rightCProperties.getFileObject().getDisplayName());
            default: return null;
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        switch( columnIndex ) {
            case 0 : return false;
            case 1 : if( this.leftCProperties.getFileObject().isReadOnly() ) {
                        return false;
                        }
                     else {
                         String key = keyList.get( rowIndex );
                         return isStringEditable( leftCProperties.getProperty( key ) );
                         }
            case 2 : if( this.rightCProperties.getFileObject().isReadOnly() ) {
                        return false;
                        }
                    else {
                        String key = keyList.get( rowIndex );
                        return isStringEditable( rightCProperties.getProperty( key ) );
                        }
            default: return false;
        }
    }
    
    public boolean isStringEditable(String str)
    {
        if( str.contains( "\n" ) ) {
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

        switch( columnIndex ) {
            case 0 : return key;
            case 1 : return leftCProperties.getProperty( key );
            case 2 : return rightCProperties.getProperty( key );
        }
        return null;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if( columnIndex == 0 ) {
            return;
        }
        String key   = keyList.get( rowIndex );
        String value = String.class.cast( aValue );

        if( columnIndex == 1 ) {
            leftCProperties.setProperty( key, value );
        }
        else if( columnIndex == 2 ) {
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
                = new LeftDotRenderer()
                {
                    private static final long serialVersionUID = 1L;

                    public Component getTableCellRendererComponent(
                            JTable  table, 
                            Object  value, 
                            boolean isSelected,
                            boolean hasFocus,
                            int     row, 
                            int     column
                            )
                        {
                            Component c = super.getTableCellRendererComponent(
                                    table, 
                                    value,
                                    isSelected,
                                    hasFocus, 
                                    row, 
                                    column
                                    ); 
                            setBackground( Color.LIGHT_GRAY );
                            return c;
                        }
                };

            @Override
            public TableCellRenderer getCellRenderer(int row, int column)
            {
                if( column == 0 ) {
                    return leftDotRenderer;
                }

                return myJLabel;
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
                        setBackground( Color.WHITE );
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
        CompareRessourceBundlePopupMenu popupMenu = new CompareRessourceBundlePopupMenu( jTable, this );
        
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
            switch( i ) {
                case 0:
                    column.setPreferredWidth( 100 );
                    break;
                default:
                    column.setPreferredWidth( 300 );
                    break;
            }
        }
    }
}
