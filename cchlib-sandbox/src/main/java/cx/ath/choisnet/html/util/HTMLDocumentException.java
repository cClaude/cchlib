/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLDocumentException.java
** Description   :
**
**  1.00.___ 2000.09.29 Claude CHOISNET - Version initiale
**  1.30.___ 2005.05.16 Claude CHOISNET
**                      Migration vers JDK 1.4+
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLDocumentException
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLDocumentException
**
*/
package cx.ath.choisnet.html.util;

/**
** <p>
** Classe principales des exceptions pour les créations des objects HTML
** </p>
**
** @author Claude CHOISNET
** @version .02.037
*/
public class HTMLDocumentException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
**
*/
public HTMLDocumentException( // ------------------------------------------
    final String message
    )
{
 super( message );
}

/**
**
*/
public HTMLDocumentException( // ------------------------------------------
    final Throwable cause
    )
{
 super( cause );
}

/**
**
*/
public HTMLDocumentException( // ------------------------------------------
    final String    message,
    final Throwable cause
    )
{
 super( message, cause );
}

}