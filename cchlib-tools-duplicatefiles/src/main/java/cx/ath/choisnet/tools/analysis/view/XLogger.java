package cx.ath.choisnet.tools.analysis.view;

/**
 *
 *
 */
public interface XLogger
{
    void info( String message );

    void error( String message, Exception e );
}
