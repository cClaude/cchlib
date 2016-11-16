package com.googlecode.cchlib.swing.textfield;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;

/**
 * JPopupMenuForJTextField is a context menu builder
 * for {@link JTextField}.
 */
public abstract class JPopupMenuForJTextField
    extends AbstractJPopupMenuBuilder
{
    private static final long serialVersionUID = 1L;
    private final JTextField jTextField;

    /**
     * Create JPopupMenuForJTextField
     *
     * @param jTextField {@link JTextField} to use.
     */
    public JPopupMenuForJTextField( final JTextField jTextField )
    {
        super( null );

        this.jTextField = jTextField;
    }

    /**
     * Returns {@link JTextField} for this menu
     * @return {@link JTextField} for this menu
     */
    protected final JTextField getJTextField()
    {
        return this.jTextField;
    }

    /**
     * Returns value from Model
     * @return value from Model
     */
    protected final String getValue()
    {
        return this.jTextField.getText();
    }

    /**
     * Sets the value in the cell at rowIndex and
     * columnIndex to aValue.
     *
     * @param aValue the new value
     */
    protected final void setValue( final String aValue )
    {
        this.jTextField.setText( aValue );
    }

    @Override
    protected void addMouseListener( final MouseListener l )
    {
        this.jTextField.addMouseListener( l );
    }

    @Override
    protected void removeMouseListener( final MouseListener l )
    {
        this.jTextField.removeMouseListener( l );
    }

    @Override
    protected void maybeShowPopup( final MouseEvent e )
    {
        if( e.isPopupTrigger() && this.jTextField.isEnabled() ) {
            final Point p = new Point( e.getX(), e.getY() );

            // create popup menu...
            final JPopupMenu contextMenu = createContextMenu();

            // ... and show it
            if( (contextMenu != null)
                    && (contextMenu.getComponentCount() > 0) ) {
                contextMenu.show( this.jTextField, p.x, p.y );
                }
            }
    }

    /**
     * <P>
     * You must overwrite this method !
     * </P>
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
     * @return {@link JPopupMenu} for this {@link JTextField}
     */
    protected abstract JPopupMenu createContextMenu();

    /**
     * NEEDDOC
     *
     * @param contextMenu
     * @param textForCopy
     * @return NEEDDOC
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
     * NEEDDOC
     *
     * @param textForCopy
     * @return NEEDDOC
     */
    protected final JMenuItem buildCopyJMenuItem( final String textForCopy )
    {
        final JMenuItem m = new JMenuItem(textForCopy);
        m.addActionListener(
                copyActionListener()
                );
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
        return e -> {
            final String value = getValue();
            setClipboardContents( (value == null) ? StringHelper.EMPTY : value );
        };
    }

    /**
     * NEEDDOC
     *
     * @param contextMenu
     * @return NEEDDOC
     */
    protected final JMenuItem addPasteMenuItem(
        final JPopupMenu contextMenu,
        final String     textForPaste
        )
    {
        final JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText( textForPaste );

        if( isClipboardContainingText( this ) ) {
            pasteMenu.addActionListener(
                    e -> {
                        final String value = getClipboardContents( JPopupMenuForJTextField.this );

                        setValue( value );
                    });
            }
        else {
            pasteMenu.setEnabled( false );
            }

        contextMenu.add( pasteMenu );

        return pasteMenu;
    }
}
