/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/util/ContextInitializationException.java
** Description   :
** Encodage      : ANSI
**
**  3.02.042 2007.01.08 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.util.ContextInitializationException
**
*/
package cx.ath.choisnet.servlet.util;

/**
**
** @author Claude CHOISNET
** @since   3.02.042
** @version 3.02.042
*/
public class ContextInitializationException
    extends Exception
{

/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public ContextInitializationException( // ---------------------------------
    String      name,
    Throwable   cause
    )
{
 super( name, cause );
}

/**
**
*/
public ContextInitializationException( // ---------------------------------
    String      name
    )
{
 super( name );
}

}

