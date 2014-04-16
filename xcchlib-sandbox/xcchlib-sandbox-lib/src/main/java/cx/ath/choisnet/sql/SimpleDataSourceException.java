/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/SimpleDataSourceException.java
** Description   :
** Encodage      : ANSI
**
**  2.02.009 2005.12.14 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.SimpleDataSourceException
**
*/
package cx.ath.choisnet.sql;


/**
**
** @author Claude CHOISNET
** @since   2.02.009
** @version 2.02.009
*/
public class SimpleDataSourceException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public SimpleDataSourceException( String message, Throwable cause ) // ----
{
 super( message, cause );
}

/**
**
*/
public SimpleDataSourceException( String message ) // ---------------------
{
 super( message );
}

} // class
