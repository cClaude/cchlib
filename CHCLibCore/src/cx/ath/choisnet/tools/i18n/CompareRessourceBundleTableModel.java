/**
 *
 */
package cx.ath.choisnet.tools.i18n;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
    private String noFile = "<<NoFile>>";
    /** @serial */
    private File leftFile;
    /** @serial */
    private File rightFile;
    /** @serial */
    private final Properties leftProperties  = new Properties();
    /** @serial */
    private final Properties rightProperties = new Properties();
    /** @serial */
    private SortedSet<String> keyBuilderSet = new TreeSet<String>();
    /** @serial */
    private final List<String> keyList = new ArrayList<String>();

    /**
     * 
     * @param leftFile
     * @param rightFile
     * @throws IOException
     * @throws FileNotFoundException
     */
    public CompareRessourceBundleTableModel(
            File leftFile,
            File rightFile
            )
    throws FileNotFoundException, IOException
    {
        this.leftFile = leftFile;
        this.rightFile = rightFile;

        load( leftProperties, leftFile );
        load( rightProperties, rightFile );

        for(String s:keyBuilderSet) {
            keyList.add( s );
        }
        keyBuilderSet.clear();
        keyBuilderSet = null;
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
            case 1 : return String.format( this.columnNames[column],leftFile==null?noFile:leftFile.getName());
            case 2 : return String.format( this.columnNames[column],rightFile==null?noFile:rightFile.getName());
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
        if( columnIndex == 1 || columnIndex == 2 ) {
            //slogger.info( "isCellEditable( " + rowIndex + "," + columnIndex + ")" );
            return true;
        }

        return false;
    }
    @Override
    public int getRowCount()
    {
        //slogger.info( "getRowCount() = " + keyList.size());
        return keyList.size();
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        String key = keyList.get( rowIndex );

        switch( columnIndex ) {
            case 0 : return key;
            case 1 : return leftProperties.getProperty( key );
            case 2 : return rightProperties.getProperty( key );
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
            leftProperties.setProperty( key, value );
        }
        else if( columnIndex == 2 ) {
            rightProperties.setProperty( key, value );
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
                    if( value == null ) {
                        setBackground( Color.RED );
                        }
                    else {
                        setBackground( Color.WHITE );
                    }

                    //slogger.info( "value = " + value );
                    setText(String.class.cast( value ));

                    return this;
                }
            // The following methods override the defaults for performance reasons
            @Override public void validate() {}
            @Override public void revalidate() {}
            @Override protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
            @Override public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
            }
        };

        JPopupMenuAssistant             assistant   = new JPopupMenuAssistant();
        CompareRessourceBundlePopupMenu popup       = new CompareRessourceBundlePopupMenu( assistant );

        assistant.installListener( jTable );
        
        return jTable;
    }
    
//    class LeftDotRenderer extends DefaultTableCellRenderer
//    {
//        private static final long serialVersionUID = 1L;
//        private final String DOTS = "...";
//
//        public Component getTableCellRendererComponent(
//            JTable table, Object value, boolean isSelected,
//            boolean hasFocus, int row, int column)
//        {
//            super.getTableCellRendererComponent(table, value,
//                isSelected, hasFocus, row, column);
//
//            int availableWidth = table.getColumnModel().getColumn(column).getWidth();
//            availableWidth -= table.getIntercellSpacing().getWidth();
//            Insets borderInsets = getBorder().getBorderInsets((Component)this);
//            availableWidth -= (borderInsets.left + borderInsets.right);
//            String cellText = getText();
//            FontMetrics fm = getFontMetrics( getFont() );
//
//            if (fm.stringWidth(cellText) > availableWidth) {
//                int textWidth = fm.stringWidth( DOTS );
//                int nChars = cellText.length() - 1;
//                for(; nChars > 0; nChars--) {
//                    textWidth += fm.charWidth(cellText.charAt(nChars));
//
//                    if (textWidth > availableWidth) {
//                        break;
//                    }
//                }
//
//                setText( DOTS + cellText.substring(nChars + 1) );
//            }
//            setBackground( Color.LIGHT_GRAY );
//
//            return this;
//        }
//    }

    private void load( Properties prop, File file )
        throws FileNotFoundException, IOException
    {//Not Override
        if( file != null ) {
            FileInputStream is = new FileInputStream(file);
            prop.load( is );
            is.close();

            keyBuilderSet.addAll( prop.stringPropertyNames() );
        }
    }

    private void save(
            Properties  prop,
            File        file,
            String      comment
            )
        throws FileNotFoundException, IOException
    {//Not Override
        //keyBuilderSet ... ??? add empty ??
        FileOutputStream os = new FileOutputStream(file);
        prop.store( 
                os, 
                "Creat by "
                    + CompareRessourceBundleFrame.class
                    + " :" 
                    + comment 
                );
        os.close();
        slogger.info( "Save : " + file );
    }

    public void saveLeftFile( File file, String comment )
        throws FileNotFoundException, IOException
    {//Not Override
        save( leftProperties, file, comment );
    }

    public void saveRightFile( File file, String comment )
        throws FileNotFoundException, IOException
    {//Not Override
        save( rightProperties, file, comment );
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
