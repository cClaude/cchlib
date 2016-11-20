/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5CollectionCompator.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.MD5CollectionCompator
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.MD5CollectionCompator
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5CollectionCompator
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.tasks.Task;
import cx.ath.choisnet.util.duplicate.tasks.TasksFactory;
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
** elle permet defectuer des traitements defini par {@link TasksFactory}
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
public class MD5CollectionCompator
{
/**
** Liste des fichiers e l'origine
*/
private MD5Collection reference;

/**
** Liste des fichiers que l'on souhaite obtenir
*/
private MD5Collection toUpdate;

/**
**
*/
private TasksFactory<String> tasksFactory;

/**
**
*/
private LinkedList<Task> tasksList = new LinkedList<Task>();

/**
**
*/
public MD5CollectionCompator( // ------------------------------------------
    final MD5Collection         referenceMD5Collection,
    final MD5Collection         toUpdateMD5Collection,
    final TasksFactory<String>  tasksFactory
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
 // Dossiers e ajouter (resultat)
 //
 for( String folder : this.reference.getFolderFilenames() ) {
    if( ! this.toUpdate.getFolderFilenames().contains( folder ) ) {
        //
        // Pas trouve, il faut creer le dossier....
        //
        addTask(
            this.tasksFactory.buildActionLocalCreateFolder( folder )
            );
        }
    }

 //
 // Dossiers e supprimer (resultat)
 //
    {
    final SortedSet<String> foldersToDelete = new TreeSet<String>( new DirComparator() );

    for( String folder : this.toUpdate.getFolderFilenames() ) {
        if( ! this.reference.getFolderFilenames().contains( folder ) ) {
            //
            // Pas trouve, il faut supprimer le dossier....
            //
            foldersToDelete.add( folder );
            }
        }

    for( String folder : foldersToDelete ) {
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
 // Fichiers de references - FIXE
 //
 final Map<MD5TreeEntry,? extends Set<String>> referenceFiles = this.reference.getEntryFilenames();

 //
 // Fichiers actuellements presents - FIXE
 //
 final Map<MD5TreeEntry,? extends Set<String>> toUpdateFiles = this.toUpdate.getEntryFilenames();

 //
 // Recherche les fichiers supprimes (en terme d'empreintes)
 //
 for( Map.Entry<MD5TreeEntry,? extends Set<String>> toUpdate : toUpdateFiles.entrySet() ) {
    MD5TreeEntry    md5             = toUpdate.getKey();
    Set<String>     toUpdateList    = toUpdate.getValue();
    Set<String>     refList         = referenceFiles.get( md5 );

    if( refList == null ) {
        //
        // Les fichiers associes e cette matiere (md5) n'existent plus.
        //
        for( String f : toUpdateList ) {
            addTask(
                this.tasksFactory.buildActionLocalDeleteFile( f )
                );
            }
        }
    // else { on traite plus loin }
    }

 //
 // Traitement des deplacements et ajout de matiere
 //

//        System.err.println( "** this.reference " + this.reference );
//        System.err.println( "** this.toUpdate " + this.toUpdate );

 for( Map.Entry<MD5TreeEntry,? extends Set<String>> refItem : referenceFiles.entrySet() ) {
    MD5TreeEntry    md5     = refItem.getKey();
    Set<String>     refList = refItem.getValue();
    Set<String>     newList = toUpdateFiles.get( md5 );

    if( newList == null ) {
        //
        // Ce fichier est dans la reference, mais pas dans la destination
        //      il recuperer la matiere et creer les fichiers
        //
        addTask( this.tasksFactory.buildActionCopyFileFromSource( md5 ) );

        //
        // Creation de tous les fichiers associes
        //
        for( String f : refList ) {
            addTask( this.tasksFactory.buildActionLocalCopyFile( md5, f ) );
            }
        }
    else {
        //
        // Ce fichier existe deje dans la destination,
        // il n'y a pas besoin de recuperer la matiere.
        //
        // Pour le traitement, on recherche un fichier de referance
        // correspondant e l'empreinte (qui ne bougera pas).
        //
        String currentMD5RefFile = null;

        //
        // On recherche les fichiers a supprimer
        //
        final LinkedList<String> to2del  = new LinkedList<String>();

        for( String f : newList ) {
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
        // On recherche les fichiers a creer
        //
        final LinkedList<String> to2create = new LinkedList<String>();

        for( String f : refList ) {
            if( ! newList.contains( f ) ) {
                to2create.add( f );
                }
            }

        //
        // Analyse des changements.
        //
        final Iterator<String> to2delIter       = to2del.iterator();
        final Iterator<String> to2createIter    = to2create.iterator();

        //
        // Deplacements
        //
        while( to2delIter.hasNext() && to2createIter.hasNext() ) {
            final String to2delFile       = to2delIter.next();
            final String to2createFile    = to2createIter.next();

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
            final String to2delFile = to2delIter.next();

            addTask(
                this.tasksFactory.buildActionLocalDeleteFile( to2delFile )
                );
            }

        //
        // Ajout
        //
        while( to2createIter.hasNext() ) {
            final String to2createFile = to2createIter.next(); // refFile

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
    private static class DirComparator implements Comparator<String>
    {
        /**
        **
        */
        @Override
        public int compare( final String o1, final String o2 )
        {
            return - o1.compareTo( o2 );
        }
    };

} // class

