package jrpdk.servlet.debug;

import javax.servlet.http.HttpServletRequest;

/*
 * Classe permettant d'armoniser la gestion des traces pour les
 * erreurs.
 * <P>
 * Cette doit étre utilisée pour récupérer des exceptions qui sont
 * typiquement des bugs (problémes d'algoritmique, ...).
 */
public class ServletLoggin
{
    private ServletLoggin()
    {
        // Static
    }

    public static void trace(
        final Object    object,
        final Exception exception,
        final String    message
        )
    {
        trace( object, exception, null, message );
    }

    public static void trace(
        final Object             object,
        final Exception          exception,
        final HttpServletRequest request,
        final String             message
        )
    {
        // Fichier de log du serveur
        // System.err.println( "***$$HTMLBuilderDebugger : " + exception );
        // exception.printStackTrace( System.err );

        TraceToFile.println( "object:" + object );
        TraceToFile.println( "exception:" + exception );
        TraceToFile.println( "request:" + request );
        TraceToFile.println( "Message:" + message );

        /*// Emission d'un mail SendAnException.sendAnException( ProjectID.TOOLS_HTML_BUILDER, object, exception,
         * request, message ); */
    }

    public static void println( final String str )
    {
        TraceToFile.println( str );
    }
}