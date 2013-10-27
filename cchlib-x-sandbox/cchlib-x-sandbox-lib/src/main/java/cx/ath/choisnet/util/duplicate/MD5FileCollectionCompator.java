/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5FileCollectionCompator.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.MD5CollectionCompator
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.MD5FileCollectionCompator
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5FileCollectionCompator
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.tasks.Task;
import cx.ath.choisnet.util.duplicate.tasks.TasksFactory;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
** <p>
** Cette classe prend en charge la comparaison de deux {@link MD5Collection},
** elle permet d�fectuer des traitements d�fini par {@link TasksFactory}
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see MD5Collection
** @see Task
** @see TasksFactory
*/
public class MD5FileCollectionCompator
{
/**
** Liste des fichiers � l'origine
*/
private MD5FileCollection reference;

/**
** Liste des fichiers que l'on souhaite obtenir
*/
private MD5FileCollection toUpdate;

/**
**
*/
private TasksFactory<File> tasksFactory;

/**
**
*/
private LinkedList<Task> tasksList = new LinkedList<Task>();

/**
**
*/
public MD5FileCollectionCompator( // --------------------------------------
    final MD5FileCollection     referenceMD5Collection,
    final MD5FileCollection     toUpdateMD5Collection,
    final TasksFactory<File>    tasksFactory
    )
{
 this.reference     = referenceMD5Collection;
 this.toUpdate      = toUpdateMD5Collection;
 this.tasksFactory  = tasksFactory;
}

/**
**
*/
public void runTasks() // -------------------------------------------------
    throws cx.ath.choisnet.util.duplicate.tasks.TaskFailException
{
 while( this.tasksList.size() > 0 ) {
    Task t = this.tasksList.removeFirst();

    System.out.println( "DO:" + t );

    try {
        t.doJob();
        }
    catch( cx.ath.choisnet.util.duplicate.tasks.TaskRetryLaterException e ) {
        System.out.println( "***Warn: " + e.getCause() );

        //
        // On le remet pour plus tard
        //
        addTask( t );
        }
    }
}

/**
**
*/
private void addTask( final Task aTask ) // -------------------------------
{
 this.tasksList.addLast( aTask );
}

/**
**
*/
public List<Task> getTasksList() // ---------------------------------------
{
 return Collections.unmodifiableList( this.tasksList );
}

/**
**
**
*/
public void init() // -----------------------------------------------------
{
 //# ##################################################################
 //#
 //# Traitement des dossiers
 //#
 //# ##################################################################

 //
 // Dossiers � ajouter (r�sultat)
 //
 for( File folder : this.reference.getFolderFiles() ) {
    if( ! this.toUpdate.getFolderFiles().contains( folder ) ) {
        //
        // Pas trouv�, il faut cr�er le dossier....
        //
        addTask(
            this.tasksFactory.buildActionLocalCreateFolder( folder )
            );
        }
    }

 //
 // Dossiers � supprimer (r�sultat)
 //
    {
    final SortedSet<File> foldersToDelete = new TreeSet<File>( new DirComparator() );

    for( File folder : this.toUpdate.getFolderFiles() ) {
        if( ! this.reference.getFolderFiles().contains( folder ) ) {
            //
            // Pas trouv�, il faut supprimer le dossier....
            //
            foldersToDelete.add( folder );
            }
        }

    for( File folder : foldersToDelete ) {
        addTask(
            this.tasksFactory.buildActionLocalDeleteFolder( folder )
            );
        }
    }

 //# ##################################################################
 //#
 //# Traitement des fichiers
 //#
 //# ##################################################################

 //
 // Fichiers de r�f�rences - FIXE
 //
 final Map<MD5TreeEntry,? extends Set<File>> referenceFiles = this.reference.getEntryFiles();

 //
 // Fichiers actuellements pr�sents - FIXE
 //
 final Map<MD5TreeEntry,? extends Set<File>> toUpdateFiles = this.toUpdate.getEntryFiles();

 //
 // Recherche les fichiers supprim�s (en terme d'empreintes)
 //
 for( Map.Entry<MD5TreeEntry,? extends Set<File>> toUpdate : toUpdateFiles.entrySet() ) {
    MD5TreeEntry    md5             = toUpdate.getKey();
    Set<File>       toUpdateList    = toUpdate.getValue();
    Set<File>       refList         = referenceFiles.get( md5 );

    if( refList == null ) {
        //
        // Les fichiers associ�s � cette mati�re (md5) n'existent plus.
        //
        for( File f : toUpdateList ) {
            addTask(
                this.tasksFactory.buildActionLocalDeleteFile( f )
                );
            }
        }
    // else { on traite plus loin }
    }

 //
 // Traitement des d�placements et ajout de mati�re
 //

//        System.err.println( "** this.reference " + this.reference );
//        System.err.println( "** this.toUpdate " + this.toUpdate );

 for( Map.Entry<MD5TreeEntry,? extends Set<File>> refItem : referenceFiles.entrySet() ) {
    MD5TreeEntry    md5     = refItem.getKey();
    Set<File>       refList = refItem.getValue();
    Set<File>       newList = toUpdateFiles.get( md5 );

    if( newList == null ) {
        //
        // Ce fichier est dans la reference, mais pas dans la destination
        //      il r�cup�rer la mati�re et cr�er les fichiers
        //
        addTask( this.tasksFactory.buildActionCopyFileFromSource( md5 ) );

        //
        // Cr�ation de tous les fichiers associ�s
        //
        for( File f : refList ) {
            addTask( this.tasksFactory.buildActionLocalCopyFile( md5, f ) );
            }
        }
    else {
        //
        // Ce fichier existe d�j� dans la destination,
        // il n'y a pas besoin de r�cup�rer la mati�re.
        //
        // Pour le traitement, on recherche un fichier de r�f�rance
        // correspondant � l'empreinte (qui ne bougera pas).
        //
        File currentMD5RefFile = null;

        //
        // On recherche les fichiers a supprimer
        //
        final LinkedList<File> to2del  = new LinkedList<File>();

        for( File f : newList ) {
            if( ! refList.contains( f ) ) {
                to2del.add( f );
                }
            else {
                if( currentMD5RefFile == null ) {
                    currentMD5RefFile = f; // ce fichier correspond au MD5
                    }
                }
            }

        //
        // On recherche les fichiers a cr�er
        //
        final LinkedList<File> to2create = new LinkedList<File>();

        for( File f : refList ) {
            if( ! newList.contains( f ) ) {
                to2create.add( f );
                }
            }

        //
        // Analyse des changements.
        //
        final Iterator<File> to2delIter       = to2del.iterator();
        final Iterator<File> to2createIter    = to2create.iterator();

        //
        // D�placements
        //
        while( to2delIter.hasNext() && to2createIter.hasNext() ) {
            final File to2delFile       = to2delIter.next();
            final File to2createFile    = to2createIter.next();

            addTask(
                this.tasksFactory.buildActionLocalMoveFile( to2delFile, to2createFile )
                );

            if( currentMD5RefFile == null ) {
                currentMD5RefFile = to2createFile;
                }
            }

        //
        // Suppression
        //
        while( to2delIter.hasNext() ) {
            final File to2delFile = to2delIter.next();

            addTask(
                this.tasksFactory.buildActionLocalDeleteFile( to2delFile )
                );
            }

        //
        // Ajout
        //
        while( to2createIter.hasNext() ) {
            final File to2createFile = to2createIter.next(); // refFile

            addTask(
                this.tasksFactory.buildActionLocalCopyFile( currentMD5RefFile, to2createFile )
                );
            }
        }
    }

}


    /**
    **
    */
    private static class DirComparator implements Comparator<File>
    {
        /**
        **
        */
        @Override
        public int compare( File o1, File o2 )
        {
            return - o1.compareTo( o2 );
        }
    };

} // class

