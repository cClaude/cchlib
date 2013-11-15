/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/DeleteFileException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.007 2006.03.05 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.DeleteFileException
**
*/
package cx.ath.choisnet.io;

import java.io.File;

/**
** <p>
** Exception déclanchée, par certaine méthode, lors de la tentative
** échouée de suppression d'un fichier
** </p>
**
**
** @author Claude CHOISNET
** @since 3.01.007
** @version 3.01.007
*/
public class DeleteFileException
    extends DeleteException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public DeleteFileException( File file ) // --------------------------------
{
 super( file );
}

/**
**
*/
public DeleteFileException( String message ) // ---------------------------
{
 super( message );
}

/**
**
*/
public DeleteFileException( String message, Throwable cause ) // ----------
{
 super( message, cause );
}

} // class