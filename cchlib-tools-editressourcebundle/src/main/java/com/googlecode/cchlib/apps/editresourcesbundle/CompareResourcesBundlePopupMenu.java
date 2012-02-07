package com.googlecode.cchlib.apps.editresourcesbundle;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.I18nString;
import cx.ath.choisnet.swing.table.JPopupMenuForJTable;

/**
 *
 * @author Claude CHOISNET
 * http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
 */
class CompareResourcesBundlePopupMenu
    extends JPopupMenuForJTable
{
    private final static transient Logger logger = Logger.getLogger( CompareResourcesBundlePopupMenu.class );
    private CompareResourcesBundleFrame frame;
    private AbstractTableModel  abstractTableModel;
    /** @serial */
    private CompareResourcesBundleTableModel.Colunms colunms;

    @I18nString
    private String txtHTMLPreview = "HTML Preview";
    @I18nString
    private String txtEditLines = "Edit lines";
    @I18nString
    private String txtCopy = "Copy";
    @I18nString
    private String txtPaste = "Paste";

    /**
     * @param jTable
     * @param abstractTableModel
     * @param colunms
     * @param autoI18n
     */
    public CompareResourcesBundlePopupMenu(
            JTable                                      jTable,
            AbstractTableModel                          abstractTableModel,
            CompareResourcesBundleTableModel.Colunms    colunms,
            AutoI18n                                    autoI18n
            )
    {
        super( jTable );

        autoI18n.performeI18n(this,this.getClass());

        this.abstractTableModel = abstractTableModel;
        this.colunms            = colunms;
    }

    @Override
    protected JPopupMenu createContextMenu(
            final int rowIndex,
            final int columnIndex
            )
    {
        //JPopupMenu contextMenu = super.createContextMenu(rowIndex, columnIndex);
        JPopupMenu contextMenu = new JPopupMenu();

        addCopyMenuItem(contextMenu, txtCopy, rowIndex, columnIndex);
        addPasteMenuItem(contextMenu, txtPaste, rowIndex, columnIndex);
        contextMenu.addSeparator();

        addShowHTMLMenuItem(contextMenu, rowIndex, columnIndex);
        addEditMultiLineMenuItem(contextMenu, rowIndex, columnIndex);

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

    protected void addShowHTMLMenuItem(
            JPopupMenu  contextMenu,
            final int   rowIndex,
            final int   columnIndex
            )
    {
        if( columnIndex == colunms.colunmKey) {
            return;
            }
        else if( columnIndex == colunms.colunmLeftLine ) {
            return;
            }
        else if( columnIndex == colunms.colunmRightLine ) {
            return;
            }

        JMenuItem previewMenu = new JMenuItem();

        previewMenu.setText( txtHTMLPreview );
        previewMenu.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getValueAt( rowIndex, columnIndex );

                        if( value instanceof String ) {
                            new HTMLPreviewDialog(
                                    getFrame(),
                                    txtHTMLPreview,
                                    "<html>" + value + "<html>"
                                    );
                        }
                    }
                });

        //contextMenu.addSeparator();
        contextMenu.add( previewMenu );
    }

    protected void addEditMultiLineMenuItem(
            JPopupMenu  contextMenu,
            final int   rowIndex,
            final int   columnIndex
            )
    {
        if( columnIndex == colunms.colunmKey ) {
            return;
            }
        else if( columnIndex == colunms.colunmLeftLine ) {
            return;
            }
        else if( columnIndex == colunms.colunmRightLine ) {
            return;
            }

        JMenuItem copyMenu = new JMenuItem();

        copyMenu.setText( txtEditLines );
        copyMenu.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed( ActionEvent e )
                    {
                        Object value = getValueAt( rowIndex, columnIndex );

                        if( value instanceof String ) {
                            openMultiLineEditor(
                                    //txtEditLines,
                                    String.class.cast( value ),
                                    rowIndex,
                                    columnIndex
                                    );
                        }
                    }
                });

        contextMenu.addSeparator();
        contextMenu.add( copyMenu );
    }

    protected void openMultiLineEditor(
            final String    title,
            final int       rowIndex,
            final int       columnIndex
            )
    {
        new MultiLineEditorDialog(
            getFrame(),
            getJTable(),
            abstractTableModel,
            title,
            txtEditLines,
            columnIndex,
            columnIndex
            )
        {
            private static final long serialVersionUID = 1L;
            @Override
            protected void setValueAt(
                    String  text,
                    int     rowIndex,
                    int     columnIndex )
            {
                setValueAt( text, rowIndex, columnIndex );
            }
        };
    }

    private CompareResourcesBundleFrame getFrame()
    {
        if( frame == null ) {
            Container c = getJTable();

            while( c != null ) {
                if( c instanceof Frame ) {
                    if( c instanceof CompareResourcesBundleFrame ) {
                        frame = CompareResourcesBundleFrame.class.cast( c );
                        }
                    else {
                        logger.fatal( "Found a frame but not expected one !" + c );
                        }
                    break;
                    }
                c = c.getParent();
                }
            if( frame == null ) {
                logger.fatal( "Parent frame not found" );
            }
        }

        return frame;
    }
}
