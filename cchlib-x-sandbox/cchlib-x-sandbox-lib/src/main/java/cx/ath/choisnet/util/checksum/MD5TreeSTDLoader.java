/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5TreeSTDLoader.java
** Description   :
** Encodage      : ANSI
**
**  2.02.037 2005.10.25 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5TreeSTDLoader
**
*/
package cx.ath.choisnet.util.checksum;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;

/**
** Initialisateur pour la classe {@link MD5Tree}
**
** @author Claude CHOISNET
** @since   2.02.037
** @version 2.02.037
**
** @see MD5Tree
*/
public final class MD5TreeSTDLoader
    implements MD5Tree.Loader
{
/** Gestion des erreurs lors du chargement */
private final MD5Tree.ExceptionHandler exceptionHandler;

/** */
private final MD5Tree.AppendFileListener appendFileListener;

/** buffer MD5 */
private final byte[] buffer;

/** calcul MD5 */
private final MD5 md5;

/**
** Construit un objet {@link MD5TreeSTDLoader} devant �tre initialis�.
*/
public MD5TreeSTDLoader() // ----------------------------------------------
{
 this.exceptionHandler      = null;
 this.appendFileListener    = null;
 this.buffer                = null;
 this.md5                   = null;
}

/**
**
*/
public MD5TreeSTDLoader( // -----------------------------------------------
    final MD5Tree.AppendFileListener    appendFileListener,
    final MD5Tree.ExceptionHandler      errorHandler,
    final int                           bufferSize
    )
{
 this.exceptionHandler      = errorHandler;
 this.appendFileListener    = appendFileListener;
 this.buffer                = new byte[ bufferSize ];
 this.md5                   = new MD5();
}

/**
**
** {@inheritDoc}
*/
@Override
public MD5Tree.Loader newInstance( // -------------------------------------
    final MD5Tree.AppendFileListener    appendFileListener,
    final MD5Tree.ExceptionHandler      errorHandler,
    final int                           bufferSize
    )
{
 return new MD5TreeSTDLoader( appendFileListener, errorHandler, bufferSize );
}

/**
**
** {@inheritDoc}
*/
@Override
public final void addFolder( // -------------------------------------------
    final MD5TreeNode   node,
    final File          folder
    )
    throws java.io.IOException
{
    appendFileListener.appendFolder( folder );

    final File[] files = folder.listFiles();

    if( files != null ) {
        for( File file : files ) {
            if( file.isDirectory() ) {
                addFolder( node.addNode( file.getName() ), file );
                }
            else {
                try {
                    //
                    // addFile( node, file );
                    //
                    md5.reset();

                    appendFileListener.appendFile( file );

                    final BufferedInputStream bis
                        = new BufferedInputStream(
                            new FileInputStream( file )
                            );
                    int len;

                    try {
                        while( (len = bis.read( buffer, 0, buffer.length )) != -1 ) {
                            md5.update( buffer, 0, len );
                            }
                        }
                    finally {
                        bis.close();
                        }

                    node.add( file.getName(), new MD5TreeEntry( md5.getValue() ) );
                    }
                catch( java.io.IOException e ) {
                    this.exceptionHandler.handleIOException( file, e );
                    }
                }
            }
        }
    else {
        this.exceptionHandler.handleIOException(
                folder,
                new java.io.IOException( "Not a valid directory." )
                );
        }
}

/**
**
private final void addFile( // ------------------------------------
    MD5TreeNode node,
    final File  file
    )
    throws java.io.IOException
{
    md5.reset();

    appendFileListener.appendFile( file );

    final BufferedInputStream bis
        = new BufferedInputStream(
            new FileInputStream( file )
            );
    int len;

    try {
        while( (len = bis.read( buffer, 0, buffer.length )) != -1 ) {
          md5.update( buffer, 0, len );
            }
        }
    finally {
        bis.close();
        }

    node.add( file.getName(), new MD5TreeEntry( md5.getValue() ) );
}
*/

} // class

