/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/FileCopyException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.007 2006.03.05 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.FileCopyException
**
*/
package cx.ath.choisnet.io;

import java.io.IOException;

/**
** <p>
** Exception déclanchée, par certaines méthodes, lors de la tentative
** échouée de suppression d'un fichier
** </p>
**
**
** @author Claude CHOISNET
** @since 3.01.007
** @version 3.01.007
*/
public class FileCopyException
    extends IOException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public FileCopyException( String message ) // -----------------------------
{
 super( message );
}

/**
**
*/
public FileCopyException( String message, Throwable cause ) // ------------
{
 super( message );

 super.initCause( cause );
}

} // class
