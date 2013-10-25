/*
** $VER: ServletLoggin.java
*/
package jrpdk.servlet.debug;

//import commun.util.mail.SendAnException;
import javax.servlet.http.HttpServletRequest;
//import commun.util.mail.ProjectID;

/*
** Classe permettant d'armoniser la gestion des traces pour les
** erreurs.
** <P>
** Cette doit être utilisée pour récupérer des exceptions qui sont
** typiquement des bugs (problèmes d'algoritmique, ...).
**
**
** @author Claude CHOISNET
** @version 1.00 22/11/2000
**
*/
public class ServletLoggin
{

/**
**
*/
public static void trace( // ----------------------------------------------
    Object      object,
    Exception   exception,
    String      message
    )
{
 trace( object, exception, null, message );
}

/**
**
*/
public static void trace( // ----------------------------------------------
    Object              object,
    Exception           exception,
    HttpServletRequest  request,
    String              message
    )
{
 // Fichier de log du serveur
// System.err.println( "***$$HTMLBuilderDebugger : " + exception );
// exception.printStackTrace( System.err );

 TraceToFile.println( "object:" + object );
 TraceToFile.println( "exception:" + exception );
 TraceToFile.println( "request:" + request );
 TraceToFile.println( "Message:" + message );

/*
 // Emission d'un mail
 SendAnException.sendAnException(
    ProjectID.TOOLS_HTML_BUILDER,
    object,
    exception,
    request,
    message
    );
*/
}

/**
**
*/
public static void println( String s ) // ---------------------------------
{
 TraceToFile.println( s );
}


} // class
