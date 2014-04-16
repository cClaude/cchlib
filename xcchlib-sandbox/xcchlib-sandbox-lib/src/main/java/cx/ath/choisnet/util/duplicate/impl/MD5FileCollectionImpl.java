/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/MD5FileCollectionImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionImpl
**
*/
package cx.ath.choisnet.util.duplicate.impl;

import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.checksum.MD5TreeNode;
import cx.ath.choisnet.util.duplicate.MD5Collection;
import cx.ath.choisnet.util.duplicate.MD5CollectionHelper;
import cx.ath.choisnet.util.duplicate.MD5CollectionXML;
import cx.ath.choisnet.util.duplicate.MD5FileCollection;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
** <p>
** Vue par empreintes d'un ensemble de fichiers, object semblable �
** l'objet {@link MD5Collection}, mais permet de traiter des fichiers
** directement � partir de l'objet File des fichiers.
** <br/>
** <br/>
** Cette classe est adapt�e pour une utilisation avec
** {@link cx.ath.choisnet.util.duplicate.MD5FileCollectionCompator}
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see cx.ath.choisnet.util.duplicate.DuplicateFileLayer
** @see cx.ath.choisnet.util.duplicate.DuplicateLayer
** @see cx.ath.choisnet.util.duplicate.MD5FileCollectionCompator
** @see MD5Tree
*/
public class MD5FileCollectionImpl
    implements MD5FileCollection
{

/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Liste des objets File correspondant aux dossiers
*/
protected SortedSet<File> dbFoldersFiles;

/**
** Couple (empreinte, liste de File fichiers)
*/
protected SortedMap<MD5TreeEntry,SortedSet<File>> dbMessageDigestFiles;

/**
** Cache pour dbFoldersFiles
*/
private transient SortedSet<String> _transient_dbFoldersFilenames = null;

/**
** Cache pour dbMessageDigestFiles
*/
private transient SortedMap<MD5TreeEntry,SortedSet<String>> _transient_dbMessageDigestFilenames = null;

/**
** Mise en cache du nombre de fichiers.
*/
private transient int _transient_filesCount = 0;

/**
** Construction d'un objet MD5FileCollection vide.
*/
public MD5FileCollectionImpl() // -----------------------------------------
{
 this.dbFoldersFiles        = new TreeSet<File>();
 this.dbMessageDigestFiles  = new TreeMap<MD5TreeEntry,SortedSet<File>>();
}

/**
** Retourne un ensemble, non modifiable, d'objet {@link File} repr�sentant le
** le nom complet de l'ensemble des dossiers connus de cette collection.
**
** @return un objet Set<String> non null, mais �ventuellement vide.
**
** @see MD5Collection#getFolderFilenames()
*/
@Override
public Set<File> getFolderFiles() // --------------------------------------
{
 return this.dbFoldersFiles;
}

/**
** Retourne un dictionnaire, non modifiable, des fichiers sous forme d'un
** object Map contenant un couple form� de l'empreinte du fichier {@link MD5TreeEntry}
** et d'un ensemble de {@link File} repr�sentant le nom complet de chacune des
** instances de ce fichier (il doit y avoir au moins une entr�e).
**
** @return un Map<MD5TreeEntry,? extends Set<File>> non null, et non vide.
**
** @see MD5Collection#getEntryFilenames()
*/
@Override
public Map<MD5TreeEntry,? extends Set<File>> getEntryFiles() // -----------
{
 return this.dbMessageDigestFiles;
}

/**
** Retourne l'ensemble des noms de fichier complet correspondant �
** l'empreinte donn�e.
**
** @param md5 Empreinte recherch�e
**
** @return un Set<File> si au moins un fichier correspond au MD5TreeEntry
** donn�, retourne null autrement.
**
** @see MD5Collection#getEntryFilenames(MD5TreeEntry)
*/
@Override
public Set<File> getEntryFiles( final MD5TreeEntry md5 ) // ---------------
{
 return this.getEntryFiles().get( md5 );
}

/**
** Retourne le nombre de fichiers connus
*/
@Override
public int getEntryCount() // ---------------------------------------------
{
 if( this._transient_filesCount == 0 ) {
    this. _transient_filesCount = MD5CollectionHelper.getEntryCount( this );
    }

 return this._transient_filesCount;
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
@Override
public int compareTo( final MD5FileCollection anOtherCollection ) // ------
{
 if( super.equals( anOtherCollection ) ) {
    return 0; // on s'arr�te l�, c'est le m�me objet !
    }

 return MD5CollectionHelper.compare( this, anOtherCollection );
}

/**
** Indicates whether some other MD5Collection is "equal to" this one.
**
*/
public boolean equals( final MD5FileCollection anOtherCollection ) // -----
{
 if( super.equals( anOtherCollection ) ) {
    return true; // on s'arr�te l�, c'est le m�me objet !
    }

 return MD5CollectionHelper.compare( this, anOtherCollection ) == 0;
}

/**
** <p>
** Charge l'arbre dans cette collection.
** <br/>
** <br/>
** Ces listes permettent, en particulier, d'avoir une vue des dossier bas�e
** sur l'empreinte des fichiers et non pas leur localisation.
** </p>
**
*/
public void add( final MD5Tree tree ) // ----------------------------------
{
 for( MD5TreeNode node : tree.nodes() ) {
    final File folderFile = node.getFile();

    this.dbFoldersFiles.add( folderFile );

    for( Map.Entry<String,MD5TreeEntry> entry : node.getFileEntries().entrySet() ) {
        final MD5TreeEntry  md  = entry.getValue();
        SortedSet<File>     set = this.dbMessageDigestFiles.get( md );

        if( set == null ) {
            set = new TreeSet<File>();
            this.dbMessageDigestFiles.put( md, set );
            }

        set.add( new File( folderFile, entry.getKey() ) );
        }
    }
}

/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
 return MD5CollectionHelper.toString( this );
}

/**
** <pre>
** java cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionImpl FOLDER_TO_EXAMIN resultFile.xml
** </pre>
**
*/
public static void main( final String[] args ) // -------------------------
    throws
        java.io.IOException,
        javax.xml.parsers.ParserConfigurationException,
        cx.ath.choisnet.util.duplicate.MD5CollectionXMLException,
        org.xml.sax.SAXException,
        cx.ath.choisnet.xml.XMLParserException
{
 final File folder  = new File( args[ 0 ] );
 final File file    = new File( args[ 1 ] );

 if( ! folder.isDirectory() ) {
    System.err.println( "Not a folder : " + folder );
    System.exit( 1 );
    }

 final MD5FileCollectionFactory factory = new MD5FileCollectionFactory()
    {
                @Override
                public void handleIOException(
                    File                file,
                    java.io.IOException cause
                    )
                {
                    // on ignore les erreurs...
                    System.err.println( "***warn : " + file + " - " + cause );
                }

    };

 String xmlInstance;

    {
    final MD5Collection            instance1    = factory.getMD5Collection( folder );
    final java.io.StringWriter     sw          = new java.io.StringWriter();

    MD5CollectionXML.toXML( instance1, sw );

    xmlInstance = sw.toString();

    System.out.println( "Premiere instance : " + instance1.getEntryCount() );
    }

 System.out.println( "Sauvegarde du XML"  );

    {
    java.io.Reader              reader  = new java.io.StringReader( xmlInstance );
    java.io.OutputStreamWriter  writer  = new java.io.OutputStreamWriter(
                                            new java.io.FileOutputStream( file ),
                                            "UTF-8"
                                            );

    final String encoding = writer.getEncoding();

    System.out.println( "Encoding: " + encoding );

    writer.write( "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>\n" );

    cx.ath.choisnet.io.ReaderHelper.copy( reader, writer );

    writer.close();
    reader.close();
    }

 System.out.println( "Creation d'un MD5CollectionXML" );

 final java.io.StringWriter sw      = new java.io.StringWriter();
 final java.io.InputStream  stream  = new java.io.FileInputStream( file );

 MD5CollectionXML instance2
        = new MD5CollectionXML(
                new cx.ath.choisnet.xml.impl.XMLParserDOM2Impl(
                    stream,
                    java.util.EnumSet.noneOf( cx.ath.choisnet.xml.impl.XMLParserDOM2Impl.Attributs.class ),
                    new cx.ath.choisnet.xml.XMLParserErrorHandler(
                        new java.io.PrintWriter( sw )
                        )
                    )
                );

 System.out.println( "-----" );
 System.out.println( "ERROR - begin" );
 System.out.println( sw.toString() );
 System.out.println( "ERROR - end" );
 System.out.println( "-----" );
 System.out.println( "Seconde instance via XML : " + instance2.getEntryCount() );
// System.out.print( MD5CollectionXML.toXML( instance2 ) );
 System.out.println( "-----" );
}

} // class

