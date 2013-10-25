/*
** $VER: BasicDateTimeException.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/BasicDateTimeException.java
** Description   :
**
**  1.00 2000.10.29 Claude CHOISNET - Version initiale
**  1.50 2005.05.20 Claude CHOISNET
**                  Le constructeur vide est maintenant protected,
**                  ajout des constructeurs surportant les exceptions
**                  parentes.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.BasicDateTimeException
**
*/
package cx.ath.choisnet.util.datetime;

/**
** Exceptions pour les classes du package
**
** @author Claude CHOISNET
** @version 1.00
*/
public class BasicDateTimeException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
protected BasicDateTimeException() // -------------------------------------
{

 super();
}

/**
**
*/
public BasicDateTimeException( String message ) // ------------------------
{
 super( message );
}

/**
**
*/
public BasicDateTimeException( String message, Throwable cause ) // -------
{
 super( message, cause );
}

/**
**
*/
protected BasicDateTimeException( Throwable cause ) // --------------------
{
 super( cause );
}

} // class
