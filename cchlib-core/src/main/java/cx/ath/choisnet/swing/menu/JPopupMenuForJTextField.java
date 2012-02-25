package cx.ath.choisnet.swing.menu;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 * JPopupMenuForJTextField is a context menu builder
 * for {@link JTextField}.
 */
public abstract class JPopupMenuForJTextField
    extends AbstractJPopupMenuBuilder
{
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
     * @param aValue      the new value
     */
    final
    protected void setValue( String aValue )
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
     * TODOC
     *
     * @param contextMenu
     *
     */
    final
    public void addCopyMenuItem( final JPopupMenu contextMenu )
    {
        add(
            contextMenu,
            buildCopyJMenuItem( "Copy" )
            );
    }

    /**
     * TODOC
     *
     * @param textForCopy
     * @return
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
                setClipboardContents( value == null ? "" : value );
            }
        };
    }

    /**
     * TODOC
     *
     * @param contextMenu
     */
    final
    protected void addPasteMenuItem( final JPopupMenu contextMenu )
    {
        JMenuItem pasteMenu = new JMenuItem();
        pasteMenu.setText( "Paste" );

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
    }

}
