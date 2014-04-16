/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/MD5CollectionImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.impl.MD5CollectionImpl
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.impl.MD5CollectionImpl
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.MD5CollectionImpl
**
*/
package cx.ath.choisnet.util.duplicate.impl;

import cx.ath.choisnet.util.duplicate.MD5Collection;
import cx.ath.choisnet.util.duplicate.MD5CollectionXML;
import cx.ath.choisnet.util.checksum.MD5Tree;
import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.checksum.MD5TreeNode;
import java.io.File;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
**
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see cx.ath.choisnet.util.duplicate.DuplicateLayer
** @see cx.ath.choisnet.util.duplicate.DuplicateFileLayer
** @see MD5Tree
*/
public class MD5CollectionImpl
    extends AbstractMD5Collection
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
private File baseFolderFile;

/**
**
*/
private transient String baseFolderFilePath;

/**
**
*/
private transient int baseFolderFilePathLen;

/**
** Construction d'un objet MD5CollectionImpl vide
*/
protected MD5CollectionImpl() // ------------------------------------------
{
 super();
}

/**
** Construction d'un objet MD5Collection relative � un dossier.
**
** @param md5tree           Arbre contenant l'arboressance
** @param baseFolderFile    Dossier correspondant � la racine de
**                          l'arboressance.
**
**
*/
public MD5CollectionImpl( // ----------------------------------------------
    final MD5Tree   md5tree,
    final File      baseFolderFile
    )
{
 this();

 this.setBaseFolderFile( baseFolderFile );
 this.load( md5tree );
}

/**
** Permet de modifier le dossier de r�f�rance (cas du d�placement).
*/
private final void setBaseFolderFile( final File baseFolderFile ) // ------
{
 this.baseFolderFile        = baseFolderFile;
 this.baseFolderFilePath    = baseFolderFile.getPath();
 this.baseFolderFilePathLen = this.baseFolderFilePath.length();
}

/**
** Construction du chemin relatif d'un nom de fichier
private String getRelativePath( final File absoluteFile ) // ---------------
{
 String relFilePath = absoluteFile.getPath();

 if( relFilePath.startsWith( this.baseFolderFilePath ) ) {
    //
    // On retire le chemin vers la r�f�rance.
    //
    relFilePath = relFilePath.substring( this.baseFolderFilePathLen );
    }

 return relFilePath;
}
*/

/**
** Construction du chemin relatif d'un nom de fichier
*/
private String getName( final File file ) // ------------------------------
{
 final String relFilePath = file.getPath();

 if( relFilePath.startsWith( this.baseFolderFilePath ) ) {
    //
    // On retire le chemin vers la r�f�rance.
    //
    return relFilePath.substring( this.baseFolderFilePathLen );
    }

 return relFilePath;
}

/**
** Construction du nouveau object File relatif
public File getRelativeFile( final File absoluteFile ) // -----------------
{
 return new File( getRelativePath( absoluteFile ) );
}
*/

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
private void load( final MD5Tree tree ) // --------------------------------
{
 for( MD5TreeNode node : tree.nodes() ) {
    final String name = getName( node.getFile() );

    if( name.length() > 0 ) {
        super.dbFoldersFilenames.add( name );
        }

    for( Map.Entry<String,MD5TreeEntry> entry : node.getFileEntries().entrySet() ) {
        final MD5TreeEntry  md  = entry.getValue();
        SortedSet<String>   set = super.dbMessageDigestFilenames.get( md );

        if( set == null ) {
            set = new TreeSet<String>();

            super.dbMessageDigestFilenames.put( md, set );
            }

        set.add( name + File.separator + entry.getKey() );
        }
    }
}

/**
** java.io.Serializable
*/
private void writeObject( final java.io.ObjectOutputStream stream ) // ----
     throws java.io.IOException
{
 stream.defaultWriteObject();

 // stream.writeInt( this.baseFolderFilePathLen  );
}

/**
** java.io.Serializable
*/
private void readObject( final java.io.ObjectInputStream stream ) // ------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

// this.baseFolderFilePathLen  = stream.readInt();
 setBaseFolderFile( this.baseFolderFile );
}

/**
** <pre>
** java cx.ath.choisnet.util.duplicate.impl.MD5CollectionImpl FOLDER_TO_EXAMIN resultFile.xml
** </pre>
**
*/
public static void main( final String[] args ) // -------------------------
    throws java.io.IOException
{
 final File folder  = new File( args[ 0 ] );
 final File file    = new File( args[ 1 ] );

 if( ! folder.isDirectory() ) {
    System.err.println( "Not a folder : " + folder );
    System.exit( 1 );
    }

 final MD5CollectionFactory factory = new MD5CollectionFactory()
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

 final MD5Collection        instance    = factory.getMD5Collection( folder );
 final java.io.StringWriter sw          = new java.io.StringWriter();

 MD5CollectionXML.toXML( instance, sw );

 java.io.Reader reader  = new java.io.StringReader( sw.toString() );
 java.io.Writer writer  = new java.io.FileWriter( file );

 cx.ath.choisnet.io.ReaderHelper.copy( reader, writer );

 writer.close();
 reader.close();
}

/**
**
private void writeObject( final java.io.ObjectOutputStream stream ) // ----
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeInt( this.baseFolderFilePathLen  );
}
*/

/**
**
private void readObject( final java.io.ObjectInputStream stream ) // ------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.baseFolderFilePathLen  = stream.readInt();
}
*/

} // class
