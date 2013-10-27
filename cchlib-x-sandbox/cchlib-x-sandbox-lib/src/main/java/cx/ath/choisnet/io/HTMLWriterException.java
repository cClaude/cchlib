/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/HTMLWriterException.java
** Description   :
** Encodage      : ANSI
**
**  3.02.025 2006.07.18 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.HTMLWriterException
**
*/
package cx.ath.choisnet.io;

import java.io.IOException;

/**
** <p>
** Exception déclanchée, par certaine méthode, lors de la tentative
** échouée de suppression d'un fichier
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.025
** @version 3.02.025
*/
public class HTMLWriterException
    extends IOException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public HTMLWriterException( // --------------------------------------------
    final String    message,
    final Throwable cause
    )
{
 super( message );

 initCause( cause );
}

/**
**
*/
public HTMLWriterException( // --------------------------------------------
    final String message
    )
{
 super( message );
}

/**
**
*/
public HTMLWriterException( // --------------------------------------------
    final Throwable cause
    )
{
 super();

 initCause( cause );
}

} // class
