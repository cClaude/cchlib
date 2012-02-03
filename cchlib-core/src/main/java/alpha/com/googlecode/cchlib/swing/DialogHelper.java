package alpha.com.googlecode.cchlib.swing;

import java.awt.Component;
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

//    /**
//     *
//     * @param parentComponent
//     * @param exception
//     */
//    public static void showMessageExceptionDialog(
//        final Component parentComponent,
//        final Throwable exception
//        )
//    {
//        showMessageExceptionDialog(
//            parentComponent,
//            exception.getLocalizedMessage(),
//            exception
//            );
//    }

    /**
     * TODO: Doc:
     *
     * @param parentComponent
     * @param title
     * @param exception
     */
    public static void showMessageExceptionDialog(
        final Component parentComponent,
        final String    title,
        final Throwable exception
        )
    {
        StringBuilder msg = new StringBuilder();
        msg.append( "<HTML><b>" );
        msg.append( exception.getLocalizedMessage() );
        msg.append( "</b><br/>\n" );

        for( String l : ExceptionHelper.getStackTraceHasLines( exception ) ) {
            msg.append( "<pre>" );
            msg.append( l );
            msg.append( "</pre>\n" );
            }
        msg.append( "</HTML>" );

        MessageDialog dialog = new MessageDialog(
                title,
                msg.toString(),
                null
                );

        try {
            dialog.getJButtonOK().setIcon(
                ResourcesLoader.getImageIcon( ResourcesLoader.OK_ICON_16x16 )
                );
            }
        catch( ResourcesLoaderException e ) {
            dialog.setJButtonOK( "OK" );
            }
        dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        dialog.setVisible( true );
    }
}
