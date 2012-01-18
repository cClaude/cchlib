package cx.ath.choisnet.tools.analysis.view;

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

    public JTextAreaXLogger( final JTextArea textArea )
    {
        super();
        this.textArea = textArea;
        this.textArea.setEditable( false );
    }

    @Override // XLogger
    public void info( final String message )
    {
        super.info( message );
        this.textArea.append( message );
    }

    @Override // XLogger
    public void error( final String message, final Exception e )
    {
        super.error( message, e );
        this.textArea.append( message );
    }
}
