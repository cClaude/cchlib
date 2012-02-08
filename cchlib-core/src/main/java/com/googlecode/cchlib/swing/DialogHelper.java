package com.googlecode.cchlib.swing;

import java.awt.Window;
import javax.swing.JDialog;
import com.googlecode.cchlib.io.ExceptionHelper;
import com.googlecode.cchlib.resources.ResourcesLoader;
import com.googlecode.cchlib.resources.ResourcesLoaderException;

/**
 *
 *
 */
public class DialogHelper
{
    private DialogHelper()
    {
        // All static
    }

    /**
     * TODO: Doc:
     *
     * @param title
     * @param exception
     */
    public static void showMessageExceptionDialog(
            final String    title,
            final Throwable exception
        )
    {
        showMessageExceptionDialog(
            null,
            title,
            exception
            );
    }

    /**
     * TODO: Doc:
     *
     * @param parentWindow
     * @param title
     * @param exception
     */
    public static void showMessageExceptionDialog(
        final Window  	parentWindow,
        final String    title,
        final Throwable exception
        )
    {
        final StringBuilder msg = new StringBuilder();

        msg.append( "<html><b>" );
        msg.append( exception.getLocalizedMessage() );
        msg.append( "</b><br/>\n" );

        for( String l : ExceptionHelper.getStackTraceHasLines( exception ) ) {
            msg.append( "<pre>" );
            msg.append( l );
            msg.append( "</pre>\n" );
            }
        msg.append( "</html>" );

        final CustomDialogWB dialog = new CustomDialogWB(
        		parentWindow,
                false,
                title,
                msg.toString()
                );

        try {
            dialog.getJButtonOk().setIcon(
                ResourcesLoader.getImageIcon( ResourcesLoader.OK_ICON_16x16 )
                );
            }
        catch( ResourcesLoaderException e ) {
            dialog.setJButtonOk( "OK" );
            }

        dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        dialog.setVisible( true );
    }
}
