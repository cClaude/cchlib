package com.googlecode.cchlib.swing.textfield;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import com.googlecode.cchlib.Const;
import com.googlecode.cchlib.swing.menu.AbstractJPopupMenuBuilder;

/**
 * JPopupMenuForJTextField is a context menu builder
 * for {@link JTextField}.
 */
public abstract class JPopupMenuForJTextField
    extends AbstractJPopupMenuBuilder
{
    private static final long serialVersionUID = 1L;
    private JTextField jTextField;

    /**
     * Create JPopupMenuForJTextField
     *
     * @param jTextField {@link JTextField} to use.
     */
    public JPopupMenuForJTextField( final JTextField jTextField )
    {
        this.jTextField = jTextField;
    }

    /**
     * Returns {@link JTextField} for this menu
     * @return {@link JTextField} for this menu
     */
    final
    protected JTextField getJTextField()
    {
        return this.jTextField;
    }

    /**
     * Returns value from Model
     * @return value from Model
     */
    final
    protected String getValue()
    {
        return this.jTextField.getText();
    }

    /**
     * Sets the value in the cell at rowIndex and
     * columnIndex to aValue.
     *
     * @param aValue the new value
     */
    final
    protected void setValue( final String aValue )
    {
        this.jTextField.setText( aValue );
    }

    @Override
    protected void addMouseListener( MouseListener l )
    {
        this.jTextField.addMouseListener( l );
    }

    @Override
    protected void maybeShowPopup( MouseEvent e )
    {
        if( e.isPopupTrigger() && jTextField.isEnabled() ) {
            Point p = new Point( e.getX(), e.getY() );

            // create popup menu...
            JPopupMenu contextMenu = createContextMenu();

            // ... and show it
            if( contextMenu != null
                    && contextMenu.getComponentCount() > 0 ) {
                contextMenu.show( jTextField, p.x, p.y );
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
     * @deprecated use {@link #addCopyMenuItem(JPopupMenu, String)} instead
     */
    @Deprecated
    public void addCopyMenuItem( final JPopupMenu contextMenu )
    {
        addCopyMenuItem( contextMenu, "Copy" );
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @param textForCopy
     * @return TODOC
     */
    final
    public JMenuItem addCopyMenuItem(
        final JPopupMenu contextMenu,
        final String     textForCopy
        )
    {
        JMenuItem menu = buildCopyJMenuItem( textForCopy );

        addJMenuItem( contextMenu, menu );

        return menu;
    }

    /**
     * TODOC
     *
     * @param textForCopy
     * @return TODOC
     */
    final
    protected JMenuItem buildCopyJMenuItem( final String textForCopy )
    {
        JMenuItem m = new JMenuItem(textForCopy);
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
    final
    protected ActionListener copyActionListener()
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                String value = getValue();
                setClipboardContents( value == null ? Const.EMPTY_STRING : value );
            }
        };
    }

    /**
     * @deprecated use {@link #addPasteMenuItem(JPopupMenu, String)} instead
     */
    @Deprecated
    protected void addPasteMenuItem( final JPopupMenu contextMenu )
    {
        addPasteMenuItem( contextMenu, "Paste" );
    }

    /**
     * TODOC
     *
     * @param contextMenu
     * @return TODOC
     */
    final
    protected JMenuItem addPasteMenuItem(
        final JPopupMenu contextMenu,
        final String     textForPaste
        )
    {
        JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText( textForPaste );

        if( isClipboardContainingText( this ) ) {
            pasteMenu.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed( ActionEvent e )
                        {
                            String value = getClipboardContents( JPopupMenuForJTextField.this );

                            setValue( value );
                        }
                    });
            }
        else {
            pasteMenu.setEnabled( false );
            }

        contextMenu.add( pasteMenu );

        return pasteMenu;
    }
}
