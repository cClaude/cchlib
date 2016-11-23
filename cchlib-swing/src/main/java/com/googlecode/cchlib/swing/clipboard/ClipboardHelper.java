package com.googlecode.cchlib.swing.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Reader;

/**
 * Clipboard manipulations
 *
 * @since 4.2
 *
 */
public class ClipboardHelper
{
    private ClipboardHelper()
    {
        // All static
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
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static final String getClipboardContents( final Object requestor )
        throws IllegalStateException
    {
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );

        if( t != null ) {
            final DataFlavor df = DataFlavor.stringFlavor;

            if( df != null ) {
                return getClipboardContents( t, df );
                }
            }

        return null;
    }

    private static String getClipboardContents(
            final Transferable transferable,
            final DataFlavor   metaInformation
            )
    {
        try( final Reader r = metaInformation.getReaderForText( transferable ) ) {
            final char[]        charBuf = new char[ 512 ];
            final StringBuilder buf     = new StringBuilder();
            int                 n;

            while( (n = r.read( charBuf, 0, charBuf.length )) > 0 ) {
                buf.append( charBuf, 0, n );
                }
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

        return null;
    }

    /**
     * Set String content to clipboard
     *
     * @param str String to put in clipboard
     *
     * @throws IllegalStateException if the clipboard is currently
     * unavailable. For example, on some platforms, the system clipboard
     * is unavailable while it is accessed by another application.
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    public static final void setClipboardContents( final String str ) //
            throws IllegalStateException
    {
        final StringSelection selection = new StringSelection( str );

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents( selection, selection );
    }

    /**
     * Test if clipboard contain text.
     *
     * @param requestor the object requesting the clip data (not used)
     *
     * @return true if clip-board contains text.
     */
    public static final boolean isClipboardContainingText(
        final Object requestor
        )
    {
        final Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
                .getContents( requestor );
        return (t != null)
                && (t.isDataFlavorSupported( DataFlavor.stringFlavor )
                        /*|| t.isDataFlavorSupported( DataFlavor.plainTextFlavor )*/);
    }
}
