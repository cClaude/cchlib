/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/DuplicateLayer.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.MD5CollectionXMLException
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.MD5CollectionXMLException
**  3.02.041 2006.09.14 Claude CHOISNET
**                      L'objet interne 'duplicatesFiles' change de type
**                      de: private Collection<Collection<File>>
**                      il deviend: private Collection<SortedSet<File>>
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.DuplicateLayer
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.CollectionFilter;
import cx.ath.choisnet.util.IteratorFilter;
import cx.ath.choisnet.util.Selectable;
import cx.ath.choisnet.util.SortedMapIterator;
import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.TreeMap;

/**
** Classe permettant de retrouver, de gerer et d'effectuer des traitements
** lie aux fichiers trouve en double.
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.02.041
**
** @see DuplicateFileLayer
*/
public class DuplicateLayer
    implements java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 2L;

    /**
    ** Parametre pour les methodes delete et select
    **
    ** @see #select(FileFilter,EnumSet)
    */
    public enum Params
    {

        /**
        ** La suppression de toutes les occurences est autorisee, par
        ** defaut si la suppression engendre la suppression de toutes
        ** les occurences trouves AUCUN fichier n'est supprimee.
        ** deprecated no remplacement
        Deprecated
        deleteAllAllowed,
        */

        /**
        ** Supprime physiquement le fichier. Par defaut le fichier n'est
        ** pas efface.
        ** deprecated no remplacement
        Deprecated
        doDelete,
        */

        /**
        ** Ne supprime pas de la liste interne. Par defaut
        ** deprecated no remplacement
        Deprecated
        doNotRemoveInDuplicateLayer,
        */

        /**
        **
        **
        */
        SELECT_DO_NOT_SELECT_ALL_OCCURENCES

    };

/**
** Collection d'un SortedSet de fichiers identiques (avec des noms differents)
*/
private Collection<SortedSet<File>> duplicatesFiles;

/**
** Liste des fichiers (ou dossiers) devant etre conserves.
*/
private List<File> filesToKeep;

/**
** Expression reguliere des fichiers (ou dossier) devant etre conserves.
*/
private List<Pattern> filesToKeepRegExp;

/**
** Construction d'un DuplicateLayer vide (cas limite)
**
** @since 3.01.035
*/
public DuplicateLayer() // ------------------------------------------------
{
 this.duplicatesFiles   = new LinkedList<SortedSet<File>>();
 this.filesToKeep       = new LinkedList<File>();
 this.filesToKeepRegExp = new LinkedList<Pattern>();
}

/**
** Construction d'un DuplicateLayer
*/
public DuplicateLayer( // -------------------------------------------------
    final MD5FileCollection absoluteMD5FileCollection
    )
{
 this();

 addMD5Collection( absoluteMD5FileCollection );
}


/**
** Retourne une collection non modifiable de collections de fichier
**
** @return une liste ({@link Collection}) de listes triees ({@link SortedSet})
** d'objets {@link File}.
*/
public Collection<? extends SortedSet<File>> getDuplicateFilesList() //---
{
 return Collections.unmodifiableCollection( this.duplicatesFiles );
}

/**
** <p>
** Permet de definir un filtre (FileFilter) des fichiers e ignorer. Les
** entrees correspondant e ce filtre sont supprimer de la liste de
** reference : ainsi s'il n'existe qu'un seul double celui-ci sera
** lui aussi ignore.
** </p>
** <p>
** Cette methode peut etre appelle plusieurs fois avec des filtres differents.
** </p>
** @param fileFilter    Object FileFilter valide permetant d'identifier les
**                      fichiers e ignorer
**
** @return l'objet DuplicateLayer lui-meme.
**
** @see cx.ath.choisnet.io.FileFilterHelper
** @see cx.ath.choisnet.io.PatternFileFilter
*/
public DuplicateLayer filesToIgnore( final FileFilter fileFilter ) // -----
{
 final Iterator<? extends Collection<File>> iter1 = this.duplicatesFiles.iterator();

 while( iter1.hasNext() ) {
    final Collection<File> collection = iter1.next();

    for( Iterator<File> iter2 = collection.iterator(); iter2.hasNext(); ) {

        if( fileFilter.accept( iter2.next() ) ) {
            //
            // Supprime l'entree
            //
            iter2.remove();
            }
        }

    if( collection.size() < 2 ) {
        //
        // Plus de double dans cette collection
        //
        iter1.remove();
        }
    }

 return this;
}

/**
**
**
** @return l'objet DuplicateLayer lui-meme.
*/
public DuplicateLayer addFileToKeep( final File file ) // -----------------
{
 this.filesToKeep.add( file );

 return this;
}

/**
**
** @return l'objet DuplicateLayer lui-meme.
**
** @see java.util.regex.Pattern
*/
public DuplicateLayer addFileToKeep( final String regexp ) // -------------
{
 this.filesToKeepRegExp.add( Pattern.compile( regexp ) );

 return this;
}

/**
** Permet de savoir si un fichier doit etre protege ou non
**
** @return l'objet DuplicateLayer lui-meme.
**
** @see #addFileToKeep
*/
public boolean isDeletable( final File file ) // --------------------------
{
 for( File f : this.filesToKeep ) {
    if( f.equals( file ) ) {
        return false;
        }
    }

 final String path = file.getPath();

 for( Pattern p : this.filesToKeepRegExp ) {
    if( p.matcher( path ).matches() ) {
        return false;
        }
    }

 return true;
}

/**
** Construit une collection de fichier  e partir de la liste des
** fichiers interne et d'un filtre ({@link FileFilter}) donne
** <p>
** Exemple d'utilisation:
** <pre>
**  final List&lt;Integer&gt; listOfHashCodes = ...
**
**  Collection&lt;File&gt; selectedList = aDuplicateLayerObject.select(
**          new FileFilter()
**              {
**                  public boolean accept( final File file )
**                  {
**                      return listOfHashCodes.contains( Integer.valueOf( file.hashCode() ) );
**                  }
**              },
**          params
**          );
** </pre>
** </p>
**
** @param selectFileFilter  Objet {@link FileFilter} valide permettant
**                          d'identifier les fichiers e copier dans
**                          la liste.
** @param params           Parametres permettant d'affiner le traitement.
**
** @return une collection des fichiers qui correspondent aux criteres.
**
** @see DuplicateLayer.Params#SELECT_DO_NOT_SELECT_ALL_OCCURENCES
** @see cx.ath.choisnet.io.FileFilterHelper
** @see cx.ath.choisnet.io.PatternFileFilter
**
*/
public Collection<File> select( // ----------------------------------------
    final FileFilter        selectFileFilter,
    final EnumSet<Params>   params
    )
{
 final List<File>   result              = new LinkedList<File>();
 final List<File>   currentSelectList   = new LinkedList<File>();
 final boolean      selectAllAllowed    = ! params.contains( Params.SELECT_DO_NOT_SELECT_ALL_OCCURENCES );

 final Iterator<? extends Collection<File>> iter = this.duplicatesFiles.iterator();

 while( iter.hasNext() ) {
    final Collection<File> filesList = iter.next();

    for( File f : filesList ) {

        if( selectFileFilter.accept( f ) ) {
            //
            // Correspond au motif de suppression, mais
            // est-il dans la liste des fichiers proteges
            //
            if( isDeletable( f ) ) {
                currentSelectList.add( f );
                }
            }
        }

    final int size = currentSelectList.size();

    if( size > 0 ) {
        //
        // Il y'a quelque chose e selectionner
        //

        if( selectAllAllowed || size < filesList.size() ) {
            //
            // Tous les elements non pas ete selectionne
            // ou bien on est autorise e selectionner tous les
            // elements
            //
            result.addAll( currentSelectList );
            }

        currentSelectList.clear();
        }
    }

 return result;
}

/**
** Construit une collection de fichier  e partir de la liste des
** fichiers interne et d'un filtre ({@link CollectionFilter}) donne
**
** @param collectionFileFilter Objet {@link CollectionFilter} valide
**          permettant de filters pour chaques occurances d'un fichier
**          le ou les fichiers qui devront etre retournes.
**
** @return une collection des fichiers qui correspondent aux criteres.
**
** @see CollectionFilter#apply
**
*/
public Collection<File> select( // ----------------------------------------
    final CollectionFilter<File> collectionFileFilter
    )
{
 final Collection<File> result = new java.util.LinkedList<File>();

 for( Collection<File> files : this.getDuplicateFilesList() ) {
    result.addAll(
        collectionFileFilter.apply( files )
        );
    }

 return result;
}

/**
** Met e jour la liste des fichiers en double, en supprimant de la liste
** les fichiers n'existes plus physiquement. Cas d'une suppression par un
** autre thread, par exemple, ou d'un nettoyage manuel...
**
** @return l'objet DuplicateLayer lui-meme.
**
*/
public DuplicateLayer updateList() // -------------------------------------
{
 final Iterator<? extends Collection<File>> iter1 = this.duplicatesFiles.iterator();

 while( iter1.hasNext() ) {
    final Collection<File> collection = iter1.next();

    for( Iterator<File> iter2 = collection.iterator(); iter2.hasNext(); ) {
        File f = iter2.next();

        if( ! f.exists() ) {
            //
            // Le fichier n'existe plus....
            //
            iter2.remove();
            }
        }

    if( collection.size() < 2 ) {
        //
        // Plus de double dans cette collection
        //
        iter1.remove();
        }
    }

 return this;
}

/**
**
*/
private static final IteratorFilter<Set<File>> getDupFiles( // ------------
    final MD5FileCollection absoluteMD5FileCollection
    )
{
 final SortedMap<MD5TreeEntry,Set<File>> map
        = new TreeMap<MD5TreeEntry, Set<File>>( absoluteMD5FileCollection.getEntryFiles() );

 return new IteratorFilter<Set<File>>(
    new SortedMapIterator<MD5TreeEntry,Set<File>>( map ),
    new Selectable<Set<File>>()
        {
            //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            @Override
            public boolean isSelected( final Set<File> object ) //- - - - -
            {
                return object.size() > 1;
            }
            //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        }
    );
}

/**
**
**
*/
private final void addMD5Collection( // -----------------------------------
    final MD5FileCollection absoluteMD5FileCollection
    )
{
 for( Set<File> item : DuplicateLayer.getDupFiles( absoluteMD5FileCollection ) ) {
    this.duplicatesFiles.add( new TreeSet<File>( item ) );
    }
}

} // class
