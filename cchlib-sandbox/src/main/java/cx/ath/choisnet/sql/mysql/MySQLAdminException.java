/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/sql/mysql/MySQLAdminException.java
** Description   :
** Encodage      : ANSI
**
**  1.49.002 2004.05.24 Claude CHOISNET - Version initiale
**                      Nom de la classe
**                          cam.amt.techno.drs.drsbasique.connection.sql.MySQLAdminException
**  1.49.023 2004.06.09 Claude CHOISNET
**                      Changement de package :
**                          cam.amt.techno.drs.sql.MySQLAdminException
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.sql.mysql.MySQLAdminException
**
*/
package cx.ath.choisnet.sql.mysql;

import cx.ath.choisnet.util.ExternalAppException;

/**
**
** @author Claude CHOISNET
** @version 1.00
** @since   1.00
*/
public class MySQLAdminException
    extends ExternalAppException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public MySQLAdminException( String msg ) // -------------------------------
{
 super( msg );
}

/**
**
*/
public MySQLAdminException( Throwable cause ) // --------------------------
{
 super( cause.getMessage(), cause );
}

/**
**
*/
public MySQLAdminException( String msg, Throwable cause ) // --------------
{
 super( msg, cause );
}

} // class
