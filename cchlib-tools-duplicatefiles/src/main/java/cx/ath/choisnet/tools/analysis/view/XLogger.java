package cx.ath.choisnet.tools.analysis.view;

/**
 *
 *
 */
public interface XLogger
{
    public void info( String message );

    public void error( String message, Exception e );
}