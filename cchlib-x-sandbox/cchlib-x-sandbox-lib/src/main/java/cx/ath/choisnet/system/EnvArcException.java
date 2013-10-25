/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/system/EnvArc.java
** Description   :
** Encodage      : ANSI
**
**  3.02.017 2006.06.28 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.system.EnvArc
**
*/
package cx.ath.choisnet.system;

/**
**
** @author Claude CHOISNET
** @since   3.02.017
** @version 3.02.017
*/
public class EnvArcException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public EnvArcException( // ------------------------------------------------
    final String    message,
    final Throwable cause
    )
{
 super( message, cause );
}

/**
**
*/
public EnvArcException( // ------------------------------------------------
    final String message
    )
{
 super( message );
}

/**
**
*/
public EnvArcException( // ------------------------------------------------
    final Throwable cause
    )
{
 super( cause );
}

} // class
