package alpha.com.googlecode.cchlib.swing;

import javax.swing.JFrame;

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
		StringBuilder 	msg 	= new StringBuilder();
		CustomDialogWB 	dialog 	= new CustomDialogWB( parentFrame, title, msg .toString(), false );

		dialog.setVisible(true);

	}


}
