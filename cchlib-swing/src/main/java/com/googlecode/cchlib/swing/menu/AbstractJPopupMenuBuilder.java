package com.googlecode.cchlib.swing.menu;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

/**
 * Handle context menu.
 */
public abstract class AbstractJPopupMenuBuilder extends JPopupMenuHelper implements Serializable
{
    private static final long serialVersionUID = 1L;

    private transient MouseListener menuMouseListener;

    ///
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///

    /**
     * {@link MouseListener} that must be add on object that
     * need a JPopupMenu
     */
    protected abstract void addMouseListener(MouseListener l);

    /**
     * remove previous {@link MouseListener}
     */
    protected abstract void removeMouseListener( MouseListener l );

    /**
     * Must implement display or hide of menu
     */
    protected abstract void maybeShowPopup(MouseEvent e);

    /**
     * Install context menu for specified object.
     *
     * @see #addMouseListener(MouseListener)
     * @see #maybeShowPopup(MouseEvent)
     */
    public void addMenu()
    {
        if( menuMouseListener != null ) {
            removeMouseListener( menuMouseListener );
            }
        menuMouseListener = newMenu();
        addMouseListener( menuMouseListener );
    }

    private MouseAdapter newMenu()
    {
        return new MouseAdapter()
        {
            @Override
            public void mousePressed( final MouseEvent e )
            {
                maybeShowPopup( e );
            }
            @Override
            public void mouseReleased( final MouseEvent e )
            {
                maybeShowPopup( e );
            }
        };
    }

    ///
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///

    /**
     * Test if clipboard contain text.
     *
     * @param requestor the object requesting the clip data (not used)
     *
     * @return true if clip-board contains text.
     */
    public final static boolean isClipboardContainingText(
        final Object requestor
        )
    {
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        return t != null
                && (t.isDataFlavorSupported( DataFlavor.stringFlavor )
                        /*|| t.isDataFlavorSupported( DataFlavor.plainTextFlavor )*/);
    }

    /**
     * Set String content to clipboard
     *
     * @param s String to put in clipboard
     *
     * @throws IllegalStateException if the clipboard is currently
     * unavailable. For example, on some platforms, the system clipboard
     * is unavailable while it is accessed by another application.
     */
    public final static void setClipboardContents(
        final String s
        )
        throws IllegalStateException
    {
        final StringSelection selection = new StringSelection( s );
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                    .setContents(
                            selection,
                            selection
                            );
    }

    /**
     * Returns String representing the current contents of
     * the clipboard. If the clipboard currently has no
     * contents, it returns null.
     *
     * @param requestor the object requesting the clip data (not used)
     *
     * @return String representing the current contents of the clipboard.
     *         If the clipboard currently has no contents, it returns null.
     *
     * @throws IllegalStateException if the clipboard is currently
     *         unavailable. For example, on some platforms, the system
     *         clipboard is unavailable while it is accessed by another
     *         application.
     */
    public final static String getClipboardContents( final Object requestor )
        throws IllegalStateException
    {
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );

        if( t != null ) {
            final DataFlavor df = DataFlavor.stringFlavor;

            if( df != null ) {
                try {
                    final Reader        r       = df.getReaderForText( t );
                    final char[]        charBuf = new char[ 512 ];
                    final StringBuilder buf     = new StringBuilder();
                    int                 n;

                    while( (n = r.read( charBuf, 0, charBuf.length )) > 0 ) {
                        buf.append( charBuf, 0, n );
                        }

                    r.close();

                    return buf.toString();
                    }
                catch( final IOException e ) {
                    // log, but ignore
                    e.printStackTrace();
                    }
                catch( final UnsupportedFlavorException e ) {
                    // log, but ignore
                    e.printStackTrace();
                    }
                }
            }

        return null;
    }

}
