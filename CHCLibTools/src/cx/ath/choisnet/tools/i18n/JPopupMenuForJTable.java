package cx.ath.choisnet.tools.i18n;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Reader;
import javax.swing.CellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * 
 * @author Claude CHOISNET
 * <p>
 * Code inspired from
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 * </p>
 */
public abstract class JPopupMenuForJTable
{
    private JTable jTable;

    /**
     * Create JPopupMenuForJTable
     * 
     * @param jTable to use.
     */
    public JPopupMenuForJTable(JTable jTable)
    {
        this.jTable = jTable;
    }
    
    /**
     * @return JTable
     */
    public JTable getJTable()
    {
        return jTable;
    }

    /**
     * @return get TableModel for current JTable
     */
    public TableModel getTableModel()
    {
        return jTable.getModel();
    }
    
    final
    public void setMenu()
    {        
        jTable.addMouseListener( 
            new MouseAdapter() 
            {
                
                private void maybeShowPopup( MouseEvent e )
                {
                    if( e.isPopupTrigger() && jTable.isEnabled() ) {
                        Point p = new Point( e.getX(), e.getY() );
                        int col = jTable.columnAtPoint( p );
                        int row = jTable.rowAtPoint( p );

                        // translate table index to model index
                        int mcol = jTable
                            .getColumn( jTable.getColumnName( col ) )
                            .getModelIndex();

                        if( row >= 0 && row < jTable.getRowCount() ) {
                            cancelCellEditing();

                        // create popup menu...
                        JPopupMenu contextMenu = createContextMenu( row, mcol );

                        // ... and show it
                        if( contextMenu != null
                                && contextMenu.getComponentCount() > 0 ) {
                            contextMenu.show( jTable, p.x, p.y );
                        }
                    }
                }
            }

            public void mousePressed( MouseEvent e )
            {
                maybeShowPopup( e );
            }

            public void mouseReleased( MouseEvent e )
            {
                maybeShowPopup( e );
            }
        } );
    }
    
    final
    protected void cancelCellEditing()
    {
        CellEditor ce = jTable.getCellEditor();
        
        if( ce != null ) {
            ce.cancelCellEditing();
        }
    }

    /**
     * To override, if needed !
     * 
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    protected JPopupMenu createContextMenu( 
            final int rowIndex,
            final int columnIndex 
            )
    {
        JPopupMenu contextMenu = new JPopupMenu();

        addCopyMenuItem(contextMenu, rowIndex, columnIndex);
        addPasteMenuItem(contextMenu, rowIndex, columnIndex);
        
//        addShowHTMLMenuItem(contextMenu, rowIndex, columnIndex);


//        switch( columnIndex ) {
//            case ExampleTableModel.COLUMN_NAME:
//                break;
//            case ExampleTableModel.COLUMN_PRICE:
//                break;
//            case ExampleTableModel.COLUMN_QUANTITY:
//                contextMenu.addSeparator();
//                ActionListener changer = new ActionListener() {
//
//                    public void actionPerformed( ActionEvent e )
//                    {
//                        JMenuItem sourceItem = (JMenuItem)e.getSource();
//                        Object value = sourceItem
//                                .getClientProperty( PROP_CHANGE_QUANTITY );
//                        if( value instanceof Integer ) {
//                            Integer changeValue = (Integer)value;
//                            Integer currentValue = (Integer)getTableModel()
//                                    .getValueAt( rowIndex, columnIndex );
//                            getTableModel().setValueAt(
//                                    new Integer( currentValue.intValue()
//                                            + changeValue.intValue() ),
//                                    rowIndex, columnIndex );
//                        }
//                    }
//                };
//                JMenuItem changeItem = new JMenuItem();
//                changeItem.setText( "+1" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( 1 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "-1" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( -1 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "+10" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( 10 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = new JMenuItem();
//                changeItem.setText( "-10" );
//                changeItem.putClientProperty( PROP_CHANGE_QUANTITY,
//                        new Integer( -10 ) );
//                changeItem.addActionListener( changer );
//                contextMenu.add( changeItem );
//
//                changeItem = null;
//                break;
//            case ExampleTableModel.COLUMN_AMOUNT:
//                break;
//            default:
//                break;
//        }
        return contextMenu;
    }
    
    final
    protected void addCopyMenuItem(
            JPopupMenu  contextMenu,
            final int   rowIndex,
            final int   columnIndex 
            )
    {
        JMenuItem copyMenu = new JMenuItem();
        
        copyMenu.setText( "Copy" );
        copyMenu.addActionListener(
                new ActionListener() 
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getTableModel()
                            .getValueAt( rowIndex, columnIndex );
                        
                        setClipboardContents( value == null ? "" : value.toString() );
                    }
                });
        
        contextMenu.add( copyMenu );
    }
    
    final
    protected void addPasteMenuItem(
            JPopupMenu  contextMenu,
            final int   rowIndex,
            final int   columnIndex 
            )
    {
        JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText( "Paste" );
        
        if( isClipboardContainingText( this )
                && getTableModel().isCellEditable( rowIndex, columnIndex ) ) {
            pasteMenu.addActionListener( 
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent e )
                        {
                            String value = getClipboardContents( JPopupMenuForJTable.this );
                            
                            getTableModel().setValueAt( value, rowIndex, columnIndex );
                        }
                    });
            }    
        else {
            pasteMenu.setEnabled( false );
        }
        
        contextMenu.add( pasteMenu );
    }

    final
    public static String getClipboardContents( Object requestor )
    {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        if( t != null ) {
            DataFlavor df = DataFlavor.stringFlavor;
            if( df != null ) {
                try {
                    Reader r = df.getReaderForText( t );
                    char[] charBuf = new char[512];
                    StringBuffer buf = new StringBuffer();
                    int n;
                    while( (n = r.read( charBuf, 0, charBuf.length )) > 0 ) {
                        buf.append( charBuf, 0, n );
                    }
                    r.close();
                    return (buf.toString());
                }
                catch( IOException ex ) {
                    ex.printStackTrace();
                }
                catch( UnsupportedFlavorException ex ) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    final
    public static boolean isClipboardContainingText( Object requestor )
    {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        return t != null
                && (t.isDataFlavorSupported( DataFlavor.stringFlavor )
                        /*|| t.isDataFlavorSupported( DataFlavor.plainTextFlavor )*/);
    }

    final
    public static void setClipboardContents(String s) 
    {
        StringSelection selection = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                selection, selection);
    }
}
