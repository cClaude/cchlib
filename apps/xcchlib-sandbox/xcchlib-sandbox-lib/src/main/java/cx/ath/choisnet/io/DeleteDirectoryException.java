/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/DeleteDirectoryException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.007 2006.03.05 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.DeleteDirectoryException
**
*/
package cx.ath.choisnet.io;

import java.io.File;

/**
** <p>
** Exception déclanchée, par certaine méthode, lors de la tentative
** échouée de suppression d'un d'un dossier
** </p>
**
**
** @author Claude CHOISNET
** @since 3.01.007
** @version 3.01.007
*/
public class DeleteDirectoryException
    extends DeleteException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public DeleteDirectoryException( File file ) // ---------------------------
{
 super( file );
}

/**
**
*/
public DeleteDirectoryException( String message ) // ----------------------
{
 super( message );
}

/**
**
*/
public DeleteDirectoryException( String message, Throwable cause ) // -----
{
 super( message, cause );
}

} // class
