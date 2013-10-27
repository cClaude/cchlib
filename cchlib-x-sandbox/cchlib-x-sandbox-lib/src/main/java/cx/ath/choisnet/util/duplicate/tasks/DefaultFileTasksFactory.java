/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/DefaultFileTasksFactory.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.DefaultFileTasksFactory
**
*/
package cx.ath.choisnet.util.duplicate.tasks;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.MD5FileCollection;
import java.util.Set;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
public class DefaultFileTasksFactory
    implements TasksFactory<File>
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
public DefaultFileTasksFactory( // ----------------------------------------
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
** Construction d'un object File local et temporaire � partir d'un
** object MD5TreeEntry
*/
@Override
public File getLocalTmpName( final MD5TreeEntry md5 ) // ------------------
{
 return new File( this.baseTempFolder, md5.toString() );
}

/**
** Retourne un flux permettant de r�cup�rer la mati�re associ�e � l'empreinte
** donn�e.
**
** @return un object InputStream valide permettant de recopie la mati�re
**         depuis la source.
*/
@Override
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
 // On r�cup�re 1 �l�ment dans la liste
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
    final File relativeFile
    )
{
 return new File( this.baseDestinationFolder, relativeFile.getPath() );
}


/**
** <p>
** Action par d�faut pour la suppression d'un fichier.
** </p>
*/
@Override
public DefaultTask buildActionLocalDeleteFile( final File f ) // ----------
{
 return new DefaultTask.ActionLocalDeleteFile( getAbsoluteDestinationFile( f ) );
}

/**
**
*/
@Override
public DefaultTask buildActionLocalCreateFolder( final File f ) // --------
{
 return new DefaultTask.ActionLocalCreateFolder( getAbsoluteDestinationFile( f ) );
}

/**
**
*/
@Override
public DefaultTask buildActionLocalDeleteFolder( final File f ) // --------
{
 return new DefaultTask.ActionLocalDeleteFolder( getAbsoluteDestinationFile( f ) );
}

/**
**
*/
@Override
public DefaultTask buildActionLocalCopyFile( // ---------------------------
    final MD5TreeEntry  md5,
    final File          f
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
@Override
public DefaultTask buildActionLocalCopyFile( // ---------------------------
    final File sourceFile,
    final File destfinationFile
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
@Override
public DefaultTask buildActionCopyFileFromSource( // ----------------------
    final MD5TreeEntry md5
    )
{
 return new DefaultTask.ActionCopyFileFromSource( this, md5, getLocalTmpName( md5 ) );
}

/**
**
*/
@Override
public DefaultTask buildActionLocalMoveFile( // ---------------------------
    final File sourceFile,
    final File destfinationFile
    )
{
 return new DefaultTask.ActionLocalMoveFile(
                    getAbsoluteDestinationFile( sourceFile ),
                    getAbsoluteDestinationFile( destfinationFile )
                    );
}

} // class

