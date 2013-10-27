/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/TasksFactory.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.TasksFactory
**
*/
package cx.ath.choisnet.util.duplicate.tasks;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import java.io.File;
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
public interface TasksFactory<T>
{
/**
** Construction d'un object File local et temporaire é partir d'un
** object MD5TreeEntry
**
** @return un object File valide permettant de stocker puis de retrouver
**         la matiére qui y sera déposée.
*/
public File getLocalTmpName( MD5TreeEntry md5 ); // -----------------------

/**
** Retourne un flux permettant de récupérer la matiére associée é l'empreinte
** donnée depuis la source.
**
** @return un object InputStream valide permettant de recopie la matiére
**         depuis la source.
*/
public InputStream getInputStreamFromSource( MD5TreeEntry md5 ) // --------
    throws java.io.IOException;

/**
** <p>
** Action par défaut pour la suppression d'un fichier.
** </p>
*/
public Task buildActionLocalDeleteFile( // --------------------------------
    final T file
    );

/**
**
*/
public Task buildActionLocalCreateFolder( // ------------------------------
    final T file
    );

/**
**
*/
public Task buildActionLocalDeleteFolder( // ------------------------------
    final T file
    );

/**
**
*/
public Task buildActionLocalCopyFile( // ----------------------------------
    final MD5TreeEntry  MD5TreeEntry,
    final T             file
    );

/**
**
*/
public Task buildActionLocalCopyFile( // ----------------------------------
    final T fsource,
    final T fdest
    );

/**
**
*/
public Task buildActionCopyFileFromSource( // -----------------------------
    final MD5TreeEntry  MD5TreeEntry
    );

/**
**
*/
public Task buildActionLocalMoveFile( // ----------------------------------
    final T fsource,
    final T fdest
    );

} // interface
