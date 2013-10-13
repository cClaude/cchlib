/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/FileIterator.java
** Description   :
** Encodage      : ANSI
**
**  2.00.005 2005.09.29 Claude CHOISNET
**  2.01.004 2005.10.03 Claude CHOISNET - Version initiale
**                      Implémente java.io.Serializable
**                      Ajout de la classe statique DirectoriesOnlyFileFilter
**                      Ajout de la méthode statique getCounts( File )
**  2.01.008 2005.10.05 Claude CHOISNET - Version initiale
**                      Optimisations, reprise de l'implémentation de Count
**                      afin de limiter l'allocations d'objets.
**  2.01.013 2005.10.09 Claude CHOISNET - Version initiale
**                      Correction d'un bug lorsqu'un des objects File
**                      donné n'existait pas.
**                      Correction de getCounts( File )
**  2.01.014 2005.10.11 Claude CHOISNET - Version initiale
**                      Ajout de la classe statique :
**                          NoDirectoriesFileFilter
**  2.01.034 2005.11.22 Claude CHOISNET - Version initiale
**                      Le dossier initial n'est plus retourné dans
**                      l'itération, le constructeur à base de plusieurs
**                      dossier n'est plus disponible.
**  2.02.006 2005.12.04 Claude CHOISNET - Version initiale
**                      Dù aux nombreux bug de la sérialisation, cet objet
**                      n'est plus déclaré comme "Serializable"
**  2.02.041 2006.01.09 Claude CHOISNET
**                      Nouvelle version dérécursifiée, basée sur
**                          cx.ath.choisnet.io.FileFolderIterator
**                      Cette version gère correctement la sérialisation,
**                      utilise moins de mémoire et va plus vite (10%) que
**                      la version précédente.
**  3.02.036 2006.08.04 Claude CHOISNET
**                      Suppression des inner-classes obsolètes.
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.FileIterator
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
** @since   2.00.005
** @version 3.02.036
**
** @see cx.ath.choisnet.util.IteratorWrapper
** @see DirectoryIterator
*/
public class FileIterator
    extends DirectoryIterator
{
/** serialVersionUID */
private static final long serialVersionUID = 3L;

/** Liste des fichiers du dossier courant */
private LinkedList<File> filesList = new LinkedList<File>();

/**
**
*/
public FileIterator( // ---------------------------------------------------
    final File rootFolderFile
    )
{
 super( rootFolderFile );
}

/**
**
*/
public FileIterator( // ---------------------------------------------------
    final File          rootFolderFile,
    final FileFilter    fileFilter
    )
{
 super( rootFolderFile, fileFilter );
}

/**
**
*/
protected void addArray( final File[] folderContentFiles ) // -------------
{
 if( folderContentFiles != null ) {
    for( File f : folderContentFiles ) {

        if( f.isDirectory() ) {
            this.addFolder( f );
            }
        else {
            this.filesList.add( f );
            }

        }
    }
}

/**
**
*/
public File next() // -----------------------------------------------------
    throws java.util.NoSuchElementException
{
 try {
    return this.filesList.removeLast();
    }
 catch( java.util.NoSuchElementException e ) {
    // continue
    }

 return super.next();
/*
 final File folder = this.foldersList.removeLast();

 addArray( folder.listFiles() );

 return folder;
*/
}

/**
**
*/
public boolean hasNext() // -----------------------------------------------
{
 if( this.filesList.size() > 0 ) {
    return true;
    }
 else {
    return super.hasNext();
    }
}

/**
**
public File computeNext() // ---------------------------------------------
    throws java.util.NoSuchElementException
{
 File cFile;

 if( this.folderFile != null ) {
    //
    // Traite le répertoire courant
    //
    cFile = this.folderFile;

    this.folderFile = null;

    return cFile;
    }

 for(;;) {
    if( this.currentFileIterator.hasNext() ) {
        return this.currentFileIterator.next();
        }

    try {
        cFile = this.folderContentFiles[ this.folderContentIndex++ ];
//System.out.println( "CN:" + cFile + " : " + this.folderContentIndex );
        }
    catch( ArrayIndexOutOfBoundsException e ) {
        throw new java.util.NoSuchElementException();
        }

    if( cFile.isDirectory() ) {
        this.currentFileIterator
                = new FileIterator(
                        cFile,
                        this.fileFilter,
                        cFile.listFiles( this.fileFilter )
                        );
        }
    else {
        break;
        }
    }

 return cFile;
}
*/

/**
** This method is not supported. Be careful to supporte this function
** you probably need to overwrite next() to keep in memory last value.
**
** @throws UnsupportedOperationException
*/
public void remove() // ---------------------------------------------------
    throws UnsupportedOperationException
{
    throw new UnsupportedOperationException();
}

/**
**
*/
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
 // Réinitialisation des champs non sauvegardés
 //
 this.folderContentIndex = stream.readInt();

 debugSerilization( "R:" );
}
*/

/**
**
private void debugSerilization( String prefix ) // ------------------------
{
//System.out.println( prefix + "fileFilter           : " + this.fileFilter );
//System.out.println( prefix + "currentFileIterator  : " + this.currentFileIterator );
//System.out.println( prefix + "folderFile           : " + this.folderFile );
//System.out.println( prefix + "folderContentFiles.l : " + this.folderContentFiles.length );

 for( int i = 0; i < this.folderContentFiles.length; i++ ) {
    File f = this.folderContentFiles[ i ];

    if( this.folderContentIndex == i ) {
        System.out.println( prefix + "folderContentFiles   : " + f + " <==" );
        }
    else {
        System.out.println( prefix + "folderContentFiles   : " + f );
        }
    }

 System.out.println( prefix + "folderContentIndex   : " + this.folderContentIndex );
}
*/

/**
**
private void debug() // ---------------------------------------------------
{
 System.out.println( "------------------------------------------" );
 debug( "" );
 System.out.println( "------------------------------------------" );
}
*/

/**
**
private void debug( String tab ) // ---------------------------------------
{
 System.out.println( tab + "fileFilter           : " + this.fileFilter );
 System.out.println( tab + "folderFile           : " + this.folderFile );

 for( int i = 0; i < this.folderContentFiles.length; i++ ) {
    File f = this.folderContentFiles[ i ];

    if( this.folderContentIndex == i ) {
        System.out.println( tab + "folderContentFiles   : " + f + " <==" );
        }
    else {
        System.out.println( tab + "folderContentFiles   : " + f );
        }
    }

 System.out.println( tab + "folderContentIndex   : " + this.folderContentIndex );

 if( this.currentFileIterator instanceof FileIterator ) {
    FileIterator fi = (FileIterator)( this.currentFileIterator );

    fi.debug( tab + " " );
    }
 else {
    System.out.println( tab + "currentFileIterator  : " + this.currentFileIterator );
    }
}
*/


/**
** Permet de compter le nombre de fichier et de dossier d'une arborescance
**
** @since 2.01.004
*/
public static class Count implements java.io.Serializable
    {
        /** serialVersionUID */
        private static final long serialVersionUID = 2L;

        /** Nombre de dossiers */
        private transient int folderCount;

        /** Nombre de fichiers */
        private transient int filesCount;

        /** Nombre d'octets */
        private transient long sizeCount;

        /**
        **
        */
        protected Count( // -----------------------------------------------
            final int   folderCount,
            final int   filesCount,
            final long  sizeCount
            )
        {
            this.folderCount    = folderCount;
            this.filesCount     = filesCount;
            this.sizeCount      = sizeCount;
        }

        /** Nombre de dossiers trouvés */
        public int getDirectories() { return this.folderCount; }

        /** Nombre de fichiers trouvés */
        public int getFiles() { return this.filesCount; }

        /** Nombre d'octets de l'ensemble des fichiers trouvés */
        public long getBytes() { return this.sizeCount; }

        /**
        ** java.io.Serializable
        **
        ** @since 2.01.008
        */
        private void writeObject( java.io.ObjectOutputStream stream ) // --
            throws java.io.IOException
        {
            stream.defaultWriteObject();

            //
            // On ne sauvegarde pas le contenu des champs transient
            //
            stream.writeInt( this.folderCount );
            stream.writeInt( this.filesCount );
            stream.writeLong( this.sizeCount );
        }

        /**
        ** java.io.Serializable
        **
        ** @since 2.01.008
        */
        private void readObject( java.io.ObjectInputStream stream ) // ----
            throws java.io.IOException, ClassNotFoundException
        {
            stream.defaultReadObject();

            //
            // Réinitialisation des champs non sauvegardés
            //
            this.folderCount    = stream.readInt();
            this.filesCount     = stream.readInt();
            this.sizeCount      = stream.readLong();
        }
}
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

/**
** @since 2.01.004
*/
public static Count getCounts( final File rootFile ) // -------------------
{
/*
 final FileIteratorDeprecated fi = new FileIteratorDeprecated(
            rootFile,
            new FileIterator.DirectoriesOnlyFileFilter()
            );
---------------------
Count1: racine             : C:\SYSTEM
Count1: iteration a faire  : 10 + 1000000
Count1: nombre de dossiers : 29
Count1: nombre de fichiers : 5449
Count1: nombre d'octets    : 253688475 o
Count1: delais             : 984 ms
---------------------
Count: iteration faites   : 11
Count: nombre de dossiers : 29
Count: nombre de fichiers : 5449
Count: nombre d'octets    : 253688475 o
Count: delais             : 10094 ms
---------------------
*/
/*
 final FileIterator fi = new FileIterator(
            rootFile,
            new FileIterator.DirectoriesOnlyFileFilter()
            );
---------------------
Count: racine             : C:\SYSTEM
Count: iteration a faire  : 10 + 1000000
Count: nombre de dossiers : 29
Count: nombre de fichiers : 5449
Count: nombre d'octets    : 253688475 o
Count2: delais             : 906 ms
---------------------
Count: iteration faites   : 11
Count: nombre de dossiers : 29
Count: nombre de fichiers : 5449
Count: nombre d'octets    : 253688475 o
Count: delais             : 9937 ms
---------------------
*/
 final DirectoryIterator fi = new DirectoryIterator( rootFile );
/*
---------------------
Count: racine             : C:\SYSTEM
Count: iteration a faire  : 10 + 1000000
Count: nombre de dossiers : 29
Count: nombre de fichiers : 5449
Count: nombre d'octets    : 253688475 o
Count3: delais             : 890 ms
---------------------
Count: iteration faites   : 11
Count: nombre de dossiers : 29
Count: nombre de fichiers : 5449
Count: nombre d'octets    : 253688475 o
Count: delais             : 9437 ms
---------------------
*/
 int   dCount = 0;
 int   fCount = 0;
 long  sCount = 0;

 while( fi.hasNext() ) {
    final java.io.File[]  files = fi.next().listFiles();

    for( java.io.File f : files ) {
        if( f.isFile() ) {
            sCount += f.length();
            fCount++;
            }
        }

    dCount++;
    }

 return new Count( dCount, fCount, sCount );
}

/**
** java -cp build/classes cx.ath.choisnet.io.FileIterator C:\SYSTEM
*/
public static void main( String[] args ) // -------------------------------
    throws Exception
{
 Iterator iter = new FileIterator( new File( "doesNotExists" ) );

 if( iter.hasNext() ) {
    System.out.println( "ok: this file '" + iter.next() + "' should not exist..." );
    }

 if( iter.hasNext() ) {
    System.out.println( "*** error: this Iterator should be empty" );
    System.err.println( "*** error: " + iter.next() );
    }

 File   rootFile    = new File( args[ 0 ] );
 int    fCount      = 0;
 long   begin       = System.currentTimeMillis();
 long   end;

    {
    Count       count   = FileIterator.getCounts( rootFile );
    end                 = System.currentTimeMillis();
    final int   max1    = 19;

    System.out.println( "---------------------" );
    System.out.println( "Count: racine             : " + rootFile );
    System.out.println( "Count: iteration a faire  : " + max1 );
    System.out.println( "Count: nombre de dossiers : " + count.getDirectories() );
    System.out.println( "Count: nombre de fichiers : " + count.getFiles() );
    System.out.println( "Count: nombre d'octets    : " + count.getBytes() + " o");
    System.out.println( "Count: delais             : " + (end-begin ) + " ms" );
    System.out.println( "---------------------" );

    for( int i = 0; i<max1; i++ ) {
        count = FileIterator.getCounts( rootFile );
        }

    end = System.currentTimeMillis();

    System.out.println( "Count: iteration faites   : " + (max1 + 1) );
    System.out.println( "Count: nombre de dossiers : " + count.getDirectories() );
    System.out.println( "Count: nombre de fichiers : " + count.getFiles() );
    System.out.println( "Count: nombre d'octets    : " + count.getBytes() + " o");
    System.out.println( "Count: delais             : " + (end-begin ) + " ms" );
    System.out.println( "---------------------" );
    }


    {
    FileIterator fi = new FileIterator( rootFile );

    while( fi.hasNext() ) {
        File file = fi.next();
        fCount++;
        }
    }
 end = System.currentTimeMillis();

 System.out.println( "racine             : " + rootFile );
// System.out.println( "nombre de 'FILE's  : " + (count.getDirectories() + count.getFiles()) );
 System.out.println( "nombre next()      : " + fCount );
 System.out.println( "delais             : " + (end -begin ) + " ms" );
 System.out.println( "---------------------" );

 int fCount2 = 0;

    {
    FileIterator fi1 = new FileIterator( rootFile );

    while( fCount2<(fCount/2) && fi1.hasNext() ) {
        File file = fi1.next();
// System.out.println( "file1 : " + file );
        fCount2++;
        }

//    FileIterator fi2 = cx.ath.choisnet.test.TestSerializable.test( fi1, FileIterator.class );
    FileIterator fi2 = cx.ath.choisnet.io.Serialization.clone( fi1, FileIterator.class );

//fi1.debug();
//fi2.debug();

    while( fi1.hasNext() ) {
        File file = fi1.next();
// System.out.println( "file1 : " + file );
        }

    while( fi2.hasNext() ) {
        File file = fi2.next();
// System.out.println( "file2 : " + file );
        fCount2++;
        }
    }

 end = System.currentTimeMillis();

 System.out.println( "racine             : " + rootFile );
// System.out.println( "nombre de 'FILE's  : " + (count.getDirectories() + count.getFiles()) );
 System.out.println( "nombre next() v1   : " + fCount );
 System.out.println( "nombre next() v2   : " + fCount2 );
 System.out.println( "delais             : " + (end -begin ) + " ms" );
 System.out.println( "---------------------" );
}

} // class


/**
** Ne limite pas la recherche
**
** @deprecated use FileFilterHelper
** @see FileFilterHelper#trueFileFilter()
@Deprecated
public static class TRUEFileFilter
    implements java.io.FileFilter, java.io.Serializable
    {
        ** serialVersionUID *
        private static final long serialVersionUID = 2L;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean accept( File file ) // - - - - - - - - - - - - - - -
        {
            return true;
        }
    }
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
*/

/**
** Limite la recherche seulement aux dossiers.
**
** @deprecated use FileFilterHelper
** @see FileFilterHelper#directoryFileFilter()
** @see DirectoryIterator
@Deprecated
public static class DirectoriesOnlyFileFilter
    implements java.io.FileFilter, java.io.Serializable
    {
        ** serialVersionUID *
        private static final long serialVersionUID = 2L;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean accept( File file ) // - - - - - - - - - - - - - - -
        {
            return file.isDirectory();
        }
    }
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
*/

/**
** Permet de filter les fichiers n'étant pas des dossiers.
**
** @deprecated use FileFilterHelper
** @see FileFilterHelper#directoryFileFilter()
** @see FileFilterHelper#not(java.io.FileFilter)
@Deprecated
public static class NoDirectoriesFileFilter
    implements java.io.FileFilter, java.io.Serializable
    {
        ** serialVersionUID *
        private static final long serialVersionUID = 2L;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean accept( File file ) // - - - - - - - - - - - - - - -
        {
            return ! file.isDirectory();
        }
    }
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
*/