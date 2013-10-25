/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/DeleteException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.007 2006.03.05 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.DeleteException
**
*/
package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.File;

/**
** <p>
** Exception déclanchée, par certaine méthode, lors de la tentative
** échouée de suppression d'un fichier ou d'un dossier
** </p>
**
**
** @author Claude CHOISNET
** @since 3.01.007
** @version 3.01.007
*/
public class DeleteException
    extends IOException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public DeleteException( File file ) // ------------------------------------
{
 this( file.getPath() );
}

/**
**
*/
public DeleteException( String message ) // -------------------------------
{
 super( message );
}

/**
**
*/
public DeleteException( String message, Throwable cause ) // --------------
{
 super( message );

 super.initCause( cause );
}

} // class
