package com.googlecode.cchlib.apps.editresourcesbundle.compare;

import java.awt.Container;
import java.awt.Frame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.html.HTMLPreviewDialog;
import com.googlecode.cchlib.apps.editresourcesbundle.multilineeditor.MultiLineEditorDialog;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;

/** public for ResourceBuilder only */
@I18nName("CompareResourcesBundlePopupMenu")
public class CompareResourcesBundlePopupMenu
    extends JPopupMenuForJTable
        implements I18nAutoUpdatable
{
    private static final long serialVersionUID = 1L;
    //
    // http://www.velocityreviews.com/forums/t146956-popupmenu-for-a-cell-in-a-jtable.html
    //
    private static final Logger LOGGER = Logger.getLogger( CompareResourcesBundlePopupMenu.class );
    private CompareResourcesBundleFrame frame;
    private final AbstractTableModel  abstractTableModel;
    private final CompareResourcesBundleTableModel.Colunms colunms;

    @I18nString protected String txtHTMLPreview = "HTML Preview";
    @I18nString protected String txtEditLines = "Edit lines";
    @I18nString protected String txtCopy  = "Copy";
    @I18nString protected String txtPaste = "Paste";

    public CompareResourcesBundlePopupMenu(
            final JTable                                   jTable,
            final AbstractTableModel                       abstractTableModel,
            final CompareResourcesBundleTableModel.Colunms colunms
            )
    {
        super( jTable );

        this.abstractTableModel = abstractTableModel;
        this.colunms            = colunms;
    }

    @Override
    protected JPopupMenu createContextMenu(
        final int rowIndex,
        final int columnIndex
        )
    {
        final JPopupMenu contextMenu = new JPopupMenu();

        addCopyMenuItem(contextMenu, this.txtCopy, rowIndex, columnIndex);
        addPasteMenuItem(contextMenu, this.txtPaste, rowIndex, columnIndex);
        contextMenu.addSeparator();

        addShowHTMLMenuItem(contextMenu, rowIndex, columnIndex);
        addEditMultiLineMenuItem(contextMenu, rowIndex, columnIndex);

        return contextMenu;
    }

    protected void addShowHTMLMenuItem(
        final JPopupMenu  contextMenu,
        final int         rowIndex,
        final int         columnIndex
        )
    {
        if( columnIndex == this.colunms.getColunmKey()) {
            return;
            }
        else if( this.colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return;
            }

        final JMenuItem previewMenu = new JMenuItem();

        previewMenu.setText( this.txtHTMLPreview );
        previewMenu.addActionListener(
                e -> {
                    final Object value = getValueAt( rowIndex, columnIndex );

                    if( value instanceof String ) {
                        final HTMLPreviewDialog d = new HTMLPreviewDialog(
                                getFrame(),
                                this.txtHTMLPreview,
                                value.toString()
                                );
                        d.pack();
                        d.setVisible( true );
                        }
                });

        contextMenu.add( previewMenu );
    }

    protected void addEditMultiLineMenuItem(
        final JPopupMenu  contextMenu,
        final int         rowIndex,
        final int         columnIndex
        )
    {
        if( columnIndex == this.colunms.getColunmKey() ) {
            return;
            }
        else if( this.colunms.getColunmLineIndex( columnIndex ) != -1 ) {
            return;
            }

        final JMenuItem copyMenu = new JMenuItem();

        copyMenu.setText( this.txtEditLines );
        copyMenu.addActionListener(
                e -> {
                    final Object value = getValueAt( rowIndex, columnIndex );

                    if( value instanceof String ) {
                        openMultiLineEditor(
                                //txtEditLines,
                                String.class.cast( value ),
                                rowIndex,
                                columnIndex
                                );
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
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace(
                    String.format(
                        "openMultiLineEditor @(%d;%d)%n",
                        Integer.valueOf( rowIndex ),
                        Integer.valueOf( columnIndex )
                        )
                    );
            }

        final MultiLineEditorDialog.StoreResult storeResult = text -> {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace(
                    String.format(
                        "Update value @(%d;%d)%n",
                        Integer.valueOf( rowIndex ),
                        Integer.valueOf( columnIndex )
                        )
                    );
                }

            setValueAt(text, rowIndex, columnIndex);

            // Update display
            final int row = getJTable().convertRowIndexToModel( rowIndex );
            final int col = getJTable().convertColumnIndexToModel( columnIndex );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace(
                    String.format(
                        "Update display @(%d;%d)%n",
                        Integer.valueOf( row ),
                        Integer.valueOf( col )
                        )
                    );
                }

            this.abstractTableModel.fireTableCellUpdated(
                    row,
                    col
                    );
        };

        final MultiLineEditorDialog d = new MultiLineEditorDialog(
            getFrame(),
            storeResult , this.txtEditLines,
            contentText
            );
        d.pack();
        d.setModal( true );
        d.setVisible( true );
    }

    private CompareResourcesBundleFrame getFrame()
    {
        if( this.frame == null ) {
            for( Container c = getJTable(); c != null; c = c.getParent() ) {
                if( c instanceof CompareResourcesBundleFrame ) {
                    this.frame = CompareResourcesBundleFrame.class.cast( c );
                    break;
                    }
                else if( c instanceof Frame ) {
                    LOGGER.fatal( "Found a frame but not expected one !" + c );
                    break;
                   }
                }

            if( this.frame == null ) {
                LOGGER.fatal( "Parent frame not found" );
                }
            }

        return this.frame;
    }

    @Override
    public void performeI18n( final AutoI18n autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }
}
