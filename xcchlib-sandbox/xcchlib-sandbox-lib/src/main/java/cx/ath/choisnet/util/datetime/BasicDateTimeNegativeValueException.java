/*
** $VER: BasicDateTimeNegativeValueException.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/datetime/BasicDateTimeNegativeValueException.java
** Description   :
**
** 1.50 2005.05.20 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.datetime.BasicDateTimeNegativeValueException
**
*/
package cx.ath.choisnet.util.datetime;

/**
** Indique une tentative de création d'une valeur date
** ou d'un heure négative
**
** @author Claude CHOISNET
** @since 1.50
** @version 1.50
*/
public class BasicDateTimeNegativeValueException
    extends BasicDateTimeException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public BasicDateTimeNegativeValueException() // ---------------------------
{
 super();
}

/**
**
*/
public BasicDateTimeNegativeValueException( // ----------------------------
    String      message,
    Throwable   cause
    )
{
 super( message, cause );
}

/**
**
*/
public BasicDateTimeNegativeValueException( // ----------------------------
    Throwable cause
    )
{
 super( cause );
}

} // class
