/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLFormException.java
** Description   :
**
**  1.00.___ 2000.09.29 Claude CHOISNET - Version initiale
**                      Nom: cx.ath.choisnet.html.HTMLFormException
**  1.50.___ 2005.05.20 Claude CHOISNET
**                      Ajout des constructeur gérant la transmission de
**                      l'exception d'origine.
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLFormException
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLFormException
**
*/
package cx.ath.choisnet.html.util;

/**
** <p>
** Exception indiquant un problème de concistance entre le
** formulaire émis et le formulaire analysé.
** </p>
**
** @author Claude CHOISNET
** @version 3.02.037
*/
public class HTMLFormException
    extends HTMLDocumentException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
** @since 1.50
*/
public HTMLFormException( // ----------------------------------------------
    final String    message,
    final Throwable cause
    )
{
 super( message, cause );
}

/**
**
*/
public HTMLFormException( // ----------------------------------------------
    final String message
    )
{
 super( message );
}

/**
**
** @since 1.50
*/
/**
**
*/
public HTMLFormException( // ----------------------------------------------
    final Throwable cause
    )
{
 super( cause );
}

}