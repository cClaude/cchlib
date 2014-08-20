/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/util/duplicate/MD5FileCollectionCompatorDebug.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  3.01.042 2006.05.24 Claude CHOISNET
 **                      Reprise de la classe:
 **                          cx.ath.choisnet.util.checksum.MD5CollectionCompatorDebug
 **                      sous le nom:
 **                          cx.ath.choisnet.util.duplicate.MD5FileCollectionCompatorDebug
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.util.duplicate.MD5FileCollectionCompatorDebug
 **
 */
package cx.ath.choisnet.util.duplicate;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionBridgeImpl;
import cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionFactory;
import cx.ath.choisnet.util.duplicate.tasks.DefaultFileTasksFactory;
import cx.ath.choisnet.xml.XMLParserErrorHandler;
import cx.ath.choisnet.xml.impl.XMLParserDOM2Impl;

/**
 ** Classe réservée au débuggage de {@link MD5CollectionCompator}
 **
 **
 ** @author Claude CHOISNET
 ** @since 3.01.042
 ** @version 3.01.042
 */
public class MD5FileCollectionCompatorDebug {

    /**
**
*/
    public static void main( final String[] args ) // -------------------------
            throws Exception
    {
        if( args.length == 4 ) {
            main( new File( args[ 0 ] ), args[ 1 ], new File( args[ 2 ] ),
                    new File( args[ 3 ] )// ,
            );
        } else {
            System.err.println( "Mauvais nombre d'arguments...\n" );
            System.err.println( "Creation..." );
            System.err
                    .println( "\tDossierRacineSource DossierSource DossierAMettreAJour DossierTravail" );
            System.err.println( "ex: (les dossiers doivent existes)" );
            System.err.println( "\tD:/Donnees/ _XPI_ D:/XNEW D:/XWORK\n" );
        }
    }

    /**
**
*/
    public static void main(
            // -----------------------------------------------
            final File rootFile, final String sourceFolder,
            final File toUpdateFolderFile, final File tmpFolderFile )
            throws Exception
    {
        final File sourceFolderFile = new File( rootFile, sourceFolder );

        System.out.println( "--------------" );
        System.out.println( "rootFile = " + rootFile + " : "
                + rootFile.isDirectory() );
        System.out.println( "sourceFolderFile = " + sourceFolderFile + " : "
                + sourceFolderFile.isDirectory() );
        System.out.println( "toUpdateFolderFile = " + toUpdateFolderFile
                + " : " + toUpdateFolderFile.isDirectory() );
        System.out.println( "tmpFolderFile = " + tmpFolderFile + " : "
                + tmpFolderFile.isDirectory() );
        System.out.println( "--------------" );

        final MD5FileCollection md5Reference = getMD5FileCollection( sourceFolderFile );

        System.out.println( "md5Reference  = " + md5Reference );

        final MD5FileCollection md5ToUpdate = getMD5FileCollection( toUpdateFolderFile );

        System.out.println( "md5ToUpdate  = " + md5ToUpdate );

        /*main( md5Reference, md5ToUpdate, fileReferenceFolder,
         * fileToUpdateFolder, fileLocalTmpFolder ); */
    }

    /**
**
*/
    public static void main(
            // -----------------------------------------------
            final MD5FileCollection md5Reference,
            final MD5FileCollection toUpdateMD5C, final File referenceFolder,
            final File toUpdateFolder, final File tmpFolder ) throws Exception
    {
        System.out.println( "Initialisaton de MD5CollectionCompator" );

        final MD5FileCollectionCompator cmp = new MD5FileCollectionCompator(
                md5Reference, toUpdateMD5C, new DefaultFileTasksFactory(
                        md5Reference, referenceFolder, toUpdateFolder,
                        tmpFolder ) );

        cmp.init();
        cmp.runTasks();
    }

    /**
**
*/
    private static MD5FileCollection getMD5FileCollection( // -----------------
            final File folder ) throws java.io.IOException
    {
        if( !folder.isDirectory() ) {
            throw new java.io.IOException( "***Fail: not a folder : " + folder );
        }

        final MD5FileCollectionFactory factory = new MD5FileCollectionFactory() {
            @Override
            public void handleIOException( final File file, final java.io.IOException cause )
            {
                // on ignore les erreurs...
                System.err.println( "***warn : " + file + " - " + cause );
            }
        };

        return factory.getMD5FileCollection( folder );
    }

    @SuppressWarnings("resource")
    public static MD5FileCollection loadFromXML( final File file ) // ---------
            throws Exception
    {
        final StringWriter sw = new StringWriter();

        final MD5CollectionXML instance = new MD5CollectionXML(
                new XMLParserDOM2Impl(
                        new FileInputStream( file ),
                        XMLParserDOM2Impl.DEFAULT_ATTRIBUTS,
                        new XMLParserErrorHandler( new java.io.PrintWriter( sw ) ) ) );

        if( sw.getBuffer().length() > 0 ) {
            System.err.print( sw.toString() );
            }

        return new MD5FileCollectionBridgeImpl( instance );
    }

}
