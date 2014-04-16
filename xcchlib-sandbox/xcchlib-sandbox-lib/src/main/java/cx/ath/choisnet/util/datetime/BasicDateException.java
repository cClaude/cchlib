/*
** $VER: BasicDateException.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/BasicDateException.java
** Description   :
**
** 1.00 2000.10.29 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.BasicDateException
**
*/
package cx.ath.choisnet.util.datetime;

/**
** Exceptions pour la classe BasicDate
**
** @author Claude CHOISNET
** @version 1.00
*/
public class BasicDateException
    extends BasicDateTimeException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
protected BasicDateException() // -----------------------------------------
{
 super();
}

/**
**
*/
public BasicDateException( String message ) // ----------------------------
{
 super( message );
}

/**
**
*/
public BasicDateException( String message, Throwable cause ) // -----------
{
 super( message, cause );
}

/**
**
*/
protected BasicDateException( Throwable cause ) // ------------------------
{
 super( cause );
}



} // class
