package com.googlecode.cchlib.swing.label;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.clipboard.ClipboardHelper;
import com.googlecode.cchlib.swing.list.JPopupMenuForJList;
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;
import com.googlecode.cchlib.swing.table.JPopupMenuForJTable;
import com.googlecode.cchlib.swing.textfield.JPopupMenuForJTextField;

/**
 * {@link JPopupMenuForJLabel} is a context menu builder
 * for {@link JLabel}.
 *
 * @see JPopupMenuForJTextField
 * @see JPopupMenuForJList
 * @see JPopupMenuForJTable
 */
public abstract class JPopupMenuForJLabel
    extends AbstractJPopupMenuBuilder
{
    private static final long serialVersionUID = 1L;
    private final JLabel label;

    /**
     * Create {@link JPopupMenuForJLabel}
     *
     * @param label {@link JLabel} to use.
     */
    public JPopupMenuForJLabel( final JLabel label )
    {
        super( null );

        this.label = label;
    }

    /**
     * Returns {@link JLabel} for this menu
     * @return {@link JLabel} for this menu
     */
    protected final JLabel getJLabel()
    {
        return this.label;
    }

    /**
     * Returns value from Model
     * @return value from Model
     */
    protected final String getValue()
    {
        return this.label.getText();
    }

    /**
     * Sets the value on the model.
     *
     * @param aValue the new value
     */
    protected final void setValue( final String aValue )
    {
        this.label.setText( aValue );
    }

    @Override
    protected void addMouseListener( final MouseListener listener )
    {
        this.label.addMouseListener( listener );
    }

    @Override
    protected void removeMouseListener( final MouseListener listener )
    {
        this.label.removeMouseListener( listener );
    }

    @Override
    protected void maybeShowPopup( final MouseEvent e )
    {
        if( e.isPopupTrigger() && this.label.isEnabled() ) {
            final Point p = new Point( e.getX(), e.getY() );

            // create popup menu...
            final JPopupMenu contextMenu = createContextMenu();

            // ... and show it
            if( (contextMenu != null)
                    && (contextMenu.getComponentCount() > 0) ) {
                contextMenu.show( this.label, p.x, p.y );
                }
            }
    }

    /**
     * You must overwrite this method !
     * <P>
     * Create a JPopupMenu
     *
     * <pre>
     * protected abstract JPopupMenu createContextMenu()
     *  {
     *      JPopupMenu contextMenu = new JPopupMenu();
     *
     *      addCopyMenuItem(contextMenu);
     *      addPasteMenuItem(contextMenu);
     *
     *      return contextMenu;
     *  }
     * </pre>
     * @return {@link JPopupMenu} for related {@link JLabel}
     */
    protected abstract JPopupMenu createContextMenu();

    /**
     * Create a menu for copy action (using default listener)
     *
     * @param contextMenu Context menu
     * @param textForCopy Label text for paste menu
     * @return a menu for copy action
    */
    public final JMenuItem addCopyMenuItem(
        final JPopupMenu contextMenu,
        final String     textForCopy
        )
    {
        final JMenuItem menu = buildCopyJMenuItem( textForCopy );
        addJMenuItem( contextMenu, menu );
        return menu;
    }

    /**
     * Create a menu for copy action (using default listener)
     *
     * @param textForCopy Label text for paste menu
     * @return a menu for copy action
     */
    protected final JMenuItem buildCopyJMenuItem( final String textForCopy )
    {
        final JMenuItem m = new JMenuItem( textForCopy );
        m.addActionListener( copyActionListener() );
        return m;
    }

    /**
     * Returns an ActionListener that copy text content of
     * cell at specified row an column.
     *
     * @return an ActionListener
     */
    protected final ActionListener copyActionListener()
    {
        return this::copyAction;
    }

    private void copyAction(
        @SuppressWarnings("squid:S1172") // ActionListener.actionPerformed(ActionEvent)
        final ActionEvent event
        )
    {
        final String value = getValue();

        ClipboardHelper.setClipboardContents( (value == null) ? StringHelper.EMPTY : value );
    }

    /**
     * Create a menu for paste action (using default listener)
     *
     * @param contextMenu NEEDDOC
     * @param textForPaste Label text for paste menu
     * @return a menu for paste action
     */
    protected final JMenuItem addPasteMenuItem(
        final JPopupMenu contextMenu,
        final String     textForPaste
        )
    {
        final JMenuItem pasteMenu = new JMenuItem();

        pasteMenu.setText( textForPaste );

        if( ClipboardHelper.isClipboardContainingText( this ) ) {
            pasteMenu.addActionListener( this::pasteAction );
            }
        else {
            pasteMenu.setEnabled( false );
            }

        contextMenu.add( pasteMenu );

        return pasteMenu;
    }

    private void pasteAction(
        @SuppressWarnings("squid:S1172") // ActionListener.actionPerformed(ActionEvent)
        final ActionEvent event
        )
    {
        final String value = ClipboardHelper.getClipboardContents( JPopupMenuForJLabel.this );

        setValue( value );
    }
}
