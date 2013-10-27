/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/HTMLGadgetNotFoundException.java
** Description   :
**
**  1.00.___ 2000.09.29 Claude CHOISNET - Version initiale
**  3.02.037 2006.08.07 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.html.util.HTMLFormException
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.HTMLGadgetNotFoundException
**
*/
package cx.ath.choisnet.html.util;

/**
** <p>
** Exception indiquant un probléme de concistance entre le
** formulaire émis et le formulaire analysé.
** </p>
**
** @author Claude CHOISNET
** @version 3.02.037
*/
public class HTMLGadgetNotFoundException
    extends HTMLFormException
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
**
*/
public HTMLGadgetNotFoundException( // ------------------------------------
    final String    message,
    final Throwable cause
    )
{
 super( message, cause );
}

/**
**
*/
public HTMLGadgetNotFoundException( // ------------------------------------
    final String message
    )
{
 super( message );
}

/**
**
*/
public HTMLGadgetNotFoundException( // ------------------------------------
    final Throwable cause
    )
{
 super( cause );
}

}