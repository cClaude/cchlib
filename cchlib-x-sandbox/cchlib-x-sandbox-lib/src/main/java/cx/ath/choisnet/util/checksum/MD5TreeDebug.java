/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/checksum/MD5TreeDebug.java
** Description   :
** Encodage      : ANSI
**
**  2.01.022 2005.10.31 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.checksum.MD5TreeDebug
**
*/
package cx.ath.choisnet.util.checksum;

import java.io.File;

/**
** Conteneur pour une empreinte : "Message Digest"
**
** @author Claude CHOISNET
** @since   2.01.022
** @version 2.01.022
*/
public class MD5TreeDebug
    extends MD5Tree
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

/**
** Construction d'un MD5Tree
*/
public MD5TreeDebug() // --------------------------------------------------
{
 super();
}

/**
** Retourne le sous-arbre sous forme ASCII dans la cha�ne
*/
public String tree() // ---------------------------------------------------
{
 return tree( this );
}

/**
** Retourne le sous-arbre sous forme ASCII dans la cha�ne
*/
final static public String tree( final MD5Tree aMD5Tree ) // --------------
{
 final MD5TreeNode rootNode = aMD5Tree.getRootNode();

 if( rootNode == null ) {
    return "<<EMPTY>>";
    }

 final Appendable sb = new StringBuilder( rootNode.getFile() + "\n" );

 try {
    tree( rootNode, sb, "" );
    }
 catch( java.io.IOException ignore ) {
    // ignore
    }

 return sb.toString();
}

/**
** Construit le sous-arbre sous forme ASCII l'objet Appendable
*/
final static private void tree( // ----------------------------------------
    final MD5TreeNode   node,
    Appendable          out,
    final String        tab
    )
    throws java.io.IOException
{
 out.append( tab );
 out.append( "+-" );
 out.append( node.getFile().getName() );
 out.append( "\n" );

 for( MD5TreeNode n : node.getNodes() ) {
    tree( n, out, tab + "| " );
    }

 for( String name : node.getFileEntries().keySet() ) {
    out.append( tab );
    out.append( "| " );
    out.append( name );
    out.append( "\n" );
    }
}

    /**
    **
    */
    public static class PrintStdOutAppendFileListener
        implements AppendFileListener
    {
        /**
        **
        */
        @Override
        public void appendFile( File file )
        {
            // System.out.println( file.getPath() );
        }
        /**

        **
        */
        @Override
        public void appendFolder( File file )
        {
            System.out.println( file.getPath() );
        }
    }

    /**
    **
    */
    public static class PrintStdErrExceptionHandler
        implements ExceptionHandler
    {
        /**
        ** Se contente de reproduire l'exception.
        */
        @Override
        public void handleIOException( File file, java.io.IOException e )
            throws java.io.IOException
        {
            if( e instanceof java.io.FileNotFoundException ) {
                System.err.println( "*** File not found : " + file.getPath() );
                }
            else {
                System.err.println( "*** " + e.getClass().getName() + ":" + file );
                }
        }

    }

/**
** java -cp build\classes cx.ath.choisnet.util.checksum.MD5TreeDebug c:
*
public static void main( String[] args ) // -------------------------------
    throws java.io.IOException
{
 boolean    useNewIO = false;
 File       folder   = null;

 for( int i = 0; i<args.length; i++ ) {
    if( args[ i ].equals( "-n" ) ) {
        useNewIO = true;
        }
    else {
        folder = new File( args[ i ] );
        }
    }

 final long         begin           = System.currentTimeMillis();
 final MD5TreeDebug instanceMD5Tree = new MD5TreeDebug();

 if( useNewIO ) {
    instanceMD5Tree.load(
            folder,
            new MD5TreeNIOLoader(),
            new PrintStdOutAppendFileListener(),
            new PrintStdErrExceptionHandler(),
            1048576
            );
    }
 else {
     instanceMD5Tree.load(
            folder,
            new MD5TreeSTDLoader(),
            new PrintStdOutAppendFileListener(),
            new PrintStdErrExceptionHandler(),
            1048576
            );
     }

 final long step1 = System.currentTimeMillis();

 System.out.println( "step1 = " + ( step1 - begin ) );

// System.out.println( instanceMD5Tree.tree() );

// MD5Collection collectionMD5 = new MD5CollectionFactory( instanceMD5Tree );
 MD5Collection collectionMD5 = new cx.ath.choisnet.util.duplicate.impl.MD5CollectionImpl( instanceMD5Tree, folder );

 final long step2 = System.currentTimeMillis();

 System.out.println( "step2 = " + ( step2 - begin ) );
 System.out.println( "delta = " + ( step2 - step1 ) );

 DuplicateLayer duplicateLayer = new DuplicateLayer( collectionMD5 );

 final long step3 = System.currentTimeMillis();

 System.out.println( "step3 = " + ( step3 - begin ) );
 System.out.println( "delta = " + ( step3 - step2 ) );
}

*/

} // class
