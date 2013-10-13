/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/DefaultStringTasksFactory.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.DefaultStringTasksFactory
**
*/
package cx.ath.choisnet.util.duplicate.tasks;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.MD5FileCollection;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

/**
** <p>
** </p>
**
** <p>
** </p>
**
** @author Claude CHOISNET
** @since   3.01.008
** @version 3.01.009
**
*/
public class DefaultStringTasksFactory
    implements TasksFactory<String>
{
/** */
private MD5FileCollection sourceMD5Collection;

/** */
private File baseSourceFolder;

/** */
private File baseDestinationFolder;

/** */
private File baseTempFolder;

/**
**
*/
public DefaultStringTasksFactory( // --------------------------------------
    final MD5FileCollection sourceMD5Collection,
    final File              baseSourceFolder,
    final File              baseDestinationFolder,
    final File              baseTempFolder
    )
{
 this.sourceMD5Collection       = sourceMD5Collection;
 this.baseSourceFolder          = baseSourceFolder;
 this.baseDestinationFolder     = baseDestinationFolder;
 this.baseTempFolder            = baseTempFolder;
}

/**
** Construction d'un object File local et temporaire à partir d'un
** object MD5TreeEntry
*/
public File getLocalTmpName( final MD5TreeEntry md5 ) // ------------------
{
 return new File( this.baseTempFolder, md5.toString() );
}


/**
** Retourne un flux permettant de récupérer la matière associée à l'empreinte
** donnée.
**
** @return un object InputStream valide permettant de recopie la matière
**         depuis la source.
*/
public InputStream getInputStreamFromSource( final MD5TreeEntry md5 ) // --
    throws java.io.FileNotFoundException
{
 final Set<File> list = this.sourceMD5Collection.getEntryFiles( md5 );

//System.out.println( "---> this.sourceMD5Collection " );
//System.out.println( this.sourceMD5Collection );
//System.out.println( "<--- this.sourceMD5Collection "  );

 if( list == null ) {
    throw new RuntimeException( "MD not found : " + md5.toString() );
    }

 //
 // On récupère 1 élément dans la liste
 //
 final String relativeName = list.iterator().next().getPath();

 return new FileInputStream(
            new File( this.baseSourceFolder, relativeName )
            );
}

/**
**
*/
private File getAbsoluteDestinationFile( // -------------------------------
    final String relativeFile
    )
{
 // return new File( this.baseDestinationFolder, relativeFile.getPath() );

 return new File( this.baseDestinationFolder, relativeFile );
}


/**
** <p>
** Action par défaut pour la suppression d'un fichier.
** </p>
*/
public DefaultTask buildActionLocalDeleteFile( final String f ) // --------
{
 return new DefaultTask.ActionLocalDeleteFile(
                getAbsoluteDestinationFile( f )
                );
}

/**
**
*/
public DefaultTask buildActionLocalCreateFolder( final String f ) // ------
{
 return new DefaultTask.ActionLocalCreateFolder(
                getAbsoluteDestinationFile( f )
                );
}

/**
**
*/
public DefaultTask buildActionLocalDeleteFolder( final String f ) // ------
{
 return new DefaultTask.ActionLocalDeleteFolder(
                getAbsoluteDestinationFile( f )
                );
}

/**
**
*/
public DefaultTask buildActionLocalCopyFile( // ---------------------------
    final MD5TreeEntry  md5,
    final String        f
    )
{
 return new DefaultTask.ActionLocalCopyFile(
                getLocalTmpName( md5 ),
                getAbsoluteDestinationFile( f )
                );
}

/**
**
*/
public DefaultTask buildActionLocalCopyFile( // ---------------------------
    final String sourceFile,
    final String destfinationFile
    )
{
 return new DefaultTask.ActionLocalCopyFile(
                getAbsoluteDestinationFile( sourceFile ),
                getAbsoluteDestinationFile( destfinationFile )
                );
}

/**
**
*/
public DefaultTask buildActionCopyFileFromSource( // ----------------------
    final MD5TreeEntry md5
    )
{
 return new DefaultTask.ActionCopyFileFromSource( this, md5, getLocalTmpName( md5 ) );
}

/**
**
*/
public DefaultTask buildActionLocalMoveFile( // ---------------------------
    final String sourceFile,
    final String destfinationFile
    )
{
 return new DefaultTask.ActionLocalMoveFile(
                    getAbsoluteDestinationFile( sourceFile ),
                    getAbsoluteDestinationFile( destfinationFile )
                    );
}

} // class

