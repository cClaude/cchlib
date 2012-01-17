package cx.ath.choisnet.tools.analysis;

import javax.swing.JTextArea;

/**
 *
 *
 */
public class JTextAreaXLogger
    extends Log4JXLogger
        implements XLogger
{
    private final JTextArea textArea;
    private final StringBuilder sb;

    public JTextAreaXLogger( final JTextArea textArea )
    {
        super();
        this.textArea = textArea;
        this.textArea.setEditable( false );
        this.sb = new StringBuilder( this.textArea.getText() );
    }

    @Override // XLogger
    public void info( String message )
    {
        super.info( message );
//        final String contenttxt = textArea.getText();
//        textArea.setText( contenttxt + message );
        this.sb.append( message );
        this.textArea.setText( this.toString() );
    }

    @Override // XLogger
    public void error( String message, Exception e )
    {
        super.error( message, e );
        this.sb.append( message );
        this.textArea.setText( this.toString() );
    }
}
