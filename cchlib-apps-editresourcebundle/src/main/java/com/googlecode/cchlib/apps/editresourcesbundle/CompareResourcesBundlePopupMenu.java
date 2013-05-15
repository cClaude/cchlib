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
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;

/**
 *
 */
class CompareResourcesBundlePopupMenu
    extends JPopupMenuForJTable
{
	private static final long serialVersionUID = 1L;
	//
    // http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
    //
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
        JPopupMenu contextMenu = new JPopupMenu();

        addCopyMenuItem(contextMenu, txtCopy, rowIndex, columnIndex);
        addPasteMenuItem(contextMenu, txtPaste, rowIndex, columnIndex);
        contextMenu.addSeparator();

        addShowHTMLMenuItem(contextMenu, rowIndex, columnIndex);
        addEditMultiLineMenuItem(contextMenu, rowIndex, columnIndex);

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
//        else if( columnIndex == colunms.colunmLeftLine ) {
//            return;
//            }
        //else if( columnIndex == colunms.colunmRightLine ) {
        else if( colunms.getColunmLineIndex( columnIndex ) != -1 ) {
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
                            HTMLPreviewDialog d = new HTMLPreviewDialog(
                                    getFrame(),
                                    txtHTMLPreview,
                                    value.toString()
                                    );
                            d.pack();
                            d.setVisible( true );
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
//        else if( columnIndex == colunms.colunmLeftLine ) {
//            return;
//            }
        //else if( columnIndex == colunms.colunmRightLine ) {
        else if( colunms.getColunmLineIndex( columnIndex ) != -1 ) {
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
            final String    contentText,
            final int       rowIndex,
            final int       columnIndex
            )
    {
        if( logger.isTraceEnabled() ) {
            logger.trace(
                    String.format(
                        "openMultiLineEditor @(%d;%d)\n",
                        rowIndex,
                        columnIndex
                        )
                    );
            }

        MultiLineEditorDialog.StoreResult storeResult = new MultiLineEditorDialog.StoreResult()
        {
            @Override
            public void storeResult( String text )
            {
                if( logger.isTraceEnabled() ) {
                    logger.trace(
                            String.format(
                                "Update value @(%d;%d)\n",
                                rowIndex,
                                columnIndex
                                )
                            );
                    }

                setValueAt(text, rowIndex, columnIndex);

                // Update display
                int row = getJTable().convertRowIndexToModel( rowIndex );
                int col = getJTable().convertColumnIndexToModel( columnIndex );

                if( logger.isTraceEnabled() ) {
                    logger.trace(
                            String.format(
                                "Update display @(%d;%d)\n",
                                row,
                                col
                                )
                            );
                    }

                abstractTableModel.fireTableCellUpdated(
                        row,
                        col
                        );
            }
        };
        MultiLineEditorDialog d = new MultiLineEditorDialog(
            getFrame(),
            storeResult , txtEditLines,
            contentText
            );
        d.pack();
        d.setModal( true );
        d.setVisible( true );
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
