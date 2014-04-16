/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/DirectoryIterator.java
** Description   :
** Encodage      : ANSI
**
**  2.02.041 2006.01.09 Claude CHOISNET - Version initiale
**                      cx.ath.choisnet.io.FileFolderIterator
**  2.02.043 2006.01.10 Claude CHOISNET
**                      Nouveau nom: cx.ath.choisnet.io.DirectoryIterator
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.DirectoryIterator
**
**
*/
package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;

/**
**
** @author Claude CHOISNET
** @since   2.02.041
** @version 2.02.041
**
** @see cx.ath.choisnet.util.IteratorWrapper
** @see FileIterator
*/
public class DirectoryIterator
    implements
        Iterator<File>,
        Iterable<File>,
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** Liste des dossiers en cours d'analyse */
private LinkedList<File> foldersList;

/** */
private FileFilter fileFilter;

/**
**
*/
public DirectoryIterator( // ----------------------------------------------
    File rootFolderFile
    )
{
 this( rootFolderFile, null );
}

/**
**
*/
public DirectoryIterator( // ----------------------------------------------
    File        rootFolderFile,
    FileFilter  fileFilter
    )
{
 this( fileFilter, rootFolderFile.listFiles() );
}

/**
**
*/
private DirectoryIterator( // ---------------------------------------------
    final FileFilter    fileFilter,
    final File[]        folderContentFiles
    )
{
 this.foldersList   = new LinkedList<File>();
 this.fileFilter    = fileFilter;

 addArray( folderContentFiles );
}

/**
** Ajoute un dossier dans le r�sultat de l'it�rateur.
*/
protected void addFolder( File folderFile ) // ----------------------------
{
 this.foldersList.add( folderFile );
}

/**
**
*/
protected void addArray( final File[] folderContentFiles ) // -------------
{
 if( folderContentFiles != null ) {
    for( File f : folderContentFiles ) {
        if( f.isDirectory() ) {
            this.foldersList.add( f );
            }
        }
    }
}

/**
**
*/
@Override
public File next() // -----------------------------------------------------
    throws java.util.NoSuchElementException
{
 final File folder = this.foldersList.removeLast();

 if( fileFilter == null ) {
    addArray( folder.listFiles() );
    }
 else {
    addArray( folder.listFiles( fileFilter ) );
    }

 return folder;
}

/**
**
*/
@Override
public boolean hasNext() // -----------------------------------------------
{
 return this.foldersList.size() > 0;
}

/**
** This method is not supported. Be careful to supporte this function
** you probably need to overwrite next() to keep in memory last value.
**
** @throws UnsupportedOperationException
*/
@Override
public void remove() // ---------------------------------------------------
    throws UnsupportedOperationException
{
    throw new UnsupportedOperationException();
}

/**
**
*/
@Override
public Iterator<File> iterator() // ---------------------------------------
{
 return this;
}

/**
** java.io.Serializable
**
** @since 2.01.004
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
    throws java.io.IOException
{
 debugSerilization( "W:" );
 stream.defaultWriteObject();

 //
 // On ne sauvegarde pas le contenu des champs transient
 //
 stream.writeInt( this.folderContentIndex );
}
*/

/**
** java.io.Serializable
**
** @since 2.01.004
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws java.io.IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 //
 // R�initialisation des champs non sauvegard�s
 //
 this.folderContentIndex = stream.readInt();

 debugSerilization( "R:" );
}
*/

} // class
