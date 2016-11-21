/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/dns/PublicIPException.java
** Description   :
**
** 1.00 2005.02.27 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.dns.PublicIPTaskUpdate
**
*/
package cx.ath.choisnet.dns;

/**
**
** @author Claude CHOISNET
** @version 1.0
*/
public class PublicIPException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public PublicIPException( // ----------------------------------------------
    String      message,
    Throwable   cause
    )
{
 super( message, cause );
}

/**
**
*/
public PublicIPException( // ----------------------------------------------
    Throwable cause
    )
{
 super( cause );
}

/**
**
*/
public PublicIPException( // ----------------------------------------------
    String message
    )
{
 super( message );
}

} // class
