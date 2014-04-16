/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5TreeNIOLoader.java
** Description   :
** Encodage      : ANSI
**
**  2.02.037 2005.10.25 Claude CHOISNET - Version initiale
**  2.02.042 2006.01.09 Claude CHOISNET
**                      Optimisations : suppression de la r�cursition.
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5TreeNIOLoader
**
*/
package cx.ath.choisnet.util.checksum;

import java.io.File;
import java.util.TreeMap;
import java.util.SortedMap;

/**
** Initialisateur pour la classe {@link MD5Tree}
**
** @author Claude CHOISNET
** @since   2.02.037
** @version 2.02.042
**
** @see MD5Tree
*/
public final class MD5TreeNIOLoader
    implements MD5Tree.Loader
{
/** Gestion des erreurs lors du chargement */
private final MD5Tree.ExceptionHandler exceptionHandler;

/** */
private final MD5Tree.AppendFileListener appendFileListener;

/** calcul MD5 */
private final SimpleMD5 sMD5;

/**
** Construit un objet {@link MD5TreeNIOLoader} devant �tre initialis�.
*/
public MD5TreeNIOLoader() // ----------------------------------------------
{
 this.exceptionHandler   = null;
 this.appendFileListener = null;
 this.sMD5               = null;
}

/**
**
*/
private MD5TreeNIOLoader( // ----------------------------------------------
    final MD5Tree.AppendFileListener    appendFileListener,
    final MD5Tree.ExceptionHandler      errorHandler,
    final int                           bufferSize
    )
{
 this.exceptionHandler   = errorHandler;
 this.appendFileListener = appendFileListener;
 this.sMD5               = new SimpleMD5( bufferSize );
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
 return new MD5TreeNIOLoader( appendFileListener, errorHandler, bufferSize );
}

/**
**
** {@inheritDoc}
*/
@Override
public final void addFolder( // -------------------------------------------
    MD5TreeNode node,
    File        folder
    )
    throws java.io.IOException
{
 final SortedMap<MD5TreeNode,File> map = new TreeMap<MD5TreeNode,File>();

 for(;;) {
    appendFileListener.appendFolder( folder );

    final File[] files = folder.listFiles();

    if( files != null ) {

        for( File file : files ) {
            if( file.isDirectory() ) {
                map.put( node.addNode( file.getName() ), file );
                }
            else {
                try {
                    //
                    // addFile( node, file );
                    //
                    appendFileListener.appendFile( file );

                    this.sMD5.compute( file );

                    node.add( file.getName(), new MD5TreeEntry( this.sMD5.getValue() ) );
                    }
                catch( java.io.IOException e ) {
                    this.exceptionHandler.handleIOException( file, e );
                    }
                }
            }

        if( map.size() == 0 ) {
            //
            // Plus de dossier � traiter, on sort !
            //
            break;
            }

        node    = map.firstKey();
        folder  = map.remove( node );
        }
    else {
        this.exceptionHandler.handleIOException(
            folder,
            new java.io.IOException( "Not a valid directory." )
            );
        }
    }
}

} // class

