/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/SimpleQueryException.java
** Description   :
** Encodage      : ANSI
**
**  2.02.009 2005.12.14 Claude CHOISNET - Deprecated !
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.SimpleQueryException
**
*/
package cx.ath.choisnet.sql;

/**
**
** @author Claude CHOISNET
**
** @deprecated
*/
@Deprecated
public class SimpleQueryException
    extends SimpleDataSourceException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public SimpleQueryException( String message, Throwable cause ) // ---------
{
 super( message, cause );
}

/**
**
*/
public SimpleQueryException( String message ) // --------------------------
{
 super( message );
}

} // class
