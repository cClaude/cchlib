package alpha.com.googlecode.cchlib.swing;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;

/**
 *
 *
 */
public class DialogHelper
{
    private DialogHelper()
    {
        // static
    }

    public static void showMessageExceptionDialog(
            final JFrame 	parentFrame,
            final String 	title,
            final Exception exception
            )
    {
        StringBuilder 	msg = new StringBuilder();
        StringWriter 	sw 	= new StringWriter();
        PrintWriter 	pw	= new PrintWriter( sw );
        exception.printStackTrace( pw );
        
        msg.append( "<html><i>" );
        msg.append( exception.getLocalizedMessage() );
        msg.append( "</i>" );
        
        for( String line : sw.toString().split( "\\n") ) {
            msg.append( "<pre>" );
            msg.append( line );
            msg.append( "</pre>" );
        	}
        msg.append( "</html>" );
        CustomDialogWB 	dialog 	= new CustomDialogWB( parentFrame, title, msg .toString(), false );

        dialog.setVisible(true);

    }


    public static void main(String[] args)
    {
		String title = "my title";
    	Exception exception = new Exception( "test", new Exception( "retest" ) );
		DialogHelper.showMessageExceptionDialog(null, title , exception);
    }
}
