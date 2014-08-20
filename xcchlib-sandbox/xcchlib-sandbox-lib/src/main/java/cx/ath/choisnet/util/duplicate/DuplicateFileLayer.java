/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/DuplicateFileLayer.java
** Description   :
** Encodage      : ANSI
**
**  3.02.046 2007.01.16 Claude CHOISNET
**                      Adaptation de  cx.ath.choisnet.util.duplicate.DuplicateLayer
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.DuplicateFileLayer
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.CollectionFilter;
import cx.ath.choisnet.util.IteratorFilter;
import cx.ath.choisnet.util.Selectable;
import cx.ath.choisnet.util.SortedMapIterator;
import cx.ath.choisnet.util.Wrappable;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
** Classe permettant de retrouver, de gerer et d'effectuer des traitements
** lie aux fichiers trouve en double.
**
** @author Claude CHOISNET
** @since   3.02.046
** @version 3.02.046
**
** @see DuplicateLayer
*/
public class DuplicateFileLayer<
                T extends File,
                K extends File,
                W extends Wrappable<File,T>
                >
// public class DuplicateFileLayer<T extends File & Wrappable<File,T>,K extends File>
    implements
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

    /**
    ** Parametre pour les methodes delete et select
    **
    ** @see #select(FileFilter,EnumSet)
    */
    public enum Params
    {
        /**
        **
        **
        */
        SELECT_DO_NOT_SELECT_ALL_OCCURENCES

    };

/**
** Collection d'un SortedSet de fichiers identiques (avec des noms differents)
*/
private Collection<SortedSet<T>> duplicatesFiles;

/**
** Liste des fichiers (ou dossiers) devant etre conserves.
*/
private List<K> filesToKeep;

/**
** Expression reguliere des fichiers (ou dossier) devant etre conserves.
*/
private List<Pattern> filesToKeepRegExp;

/**
**
*/
private W wrapper;
// private Wrappable<File,T> wrapper;

/**
** Construction d'un {@link DuplicateFileLayer} vide.
*/
public DuplicateFileLayer( // ---------------------------------------------
    // final Wrappable<File,T> wrapper
    final W wrapper
    )
{
 this.duplicatesFiles   = new ArrayList<SortedSet<T>>();
 this.filesToKeep       = new ArrayList<K>();
 this.filesToKeepRegExp = new ArrayList<Pattern>();
 this.wrapper           = wrapper;
}

/**
**
*/
public W getWrapper() // --------------------------------------------------
//public Wrappable<File,T> getWrapper() // ----------------------------------
{
 return this.wrapper;
}

/**
** Retourne une collection non modifiable de collections de fichier
**
** @return une liste ({@link Collection}) de listes triees ({@link SortedSet})
** d'objets {@link File}.
*/
public Collection<? extends SortedSet<T>> getDuplicateFilesList() //-------
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
public DuplicateFileLayer<T,K,W> filesToIgnore(// -------------------------
    final FileFilter fileFilter // ----------------------------------------
    )
{
 final Iterator<? extends Collection<T>> iter1 = this.duplicatesFiles.iterator();

 while( iter1.hasNext() ) {
    final Collection<T> collection = iter1.next();

    for( Iterator<T> iter2 = collection.iterator(); iter2.hasNext(); ) {

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
public DuplicateFileLayer<T,K,W> addFileToKeep( // ------------------------
    final K file // -------------------------------------------------------
    )
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
public DuplicateFileLayer<T,K,W> addFileToKeep( // ------------------------
    final String regexp // ------------------------------------------------
    )
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
 final List<File>   result              = new ArrayList<File>();
 final List<File>   currentSelectList   = new ArrayList<File>();
 final boolean      selectAllAllowed    = ! params.contains( Params.SELECT_DO_NOT_SELECT_ALL_OCCURENCES );

 final Iterator<? extends Collection<T>> iter = this.duplicatesFiles.iterator();

 while( iter.hasNext() ) {
    final Collection<T> filesList = iter.next();

    for( T f : filesList ) {

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
public Collection<T> select( // ----------------------------------------
    final CollectionFilter<T> collectionFileFilter
    )
{
 final Collection<T> result = new java.util.LinkedList<T>();

 for( Collection<T> files : this.getDuplicateFilesList() ) {
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
public DuplicateFileLayer<T,K,W> updateList() //---------------------------
{
 final Iterator<? extends Collection<T>> iter1 = this.duplicatesFiles.iterator();

 while( iter1.hasNext() ) {
    final Collection<T> collection = iter1.next();

    for( Iterator<T> iter2 = collection.iterator(); iter2.hasNext(); ) {
        T f = iter2.next();

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
public DuplicateFileLayer<T,K,W> set( // ----------------------------------
    final MD5FileCollection absoluteMD5FileCollection
    )
{
 this.duplicatesFiles.clear();
 this.filesToKeep.clear();
 this.filesToKeepRegExp.clear();

 for( Set<File> item : DuplicateFileLayer.getDupFiles( absoluteMD5FileCollection ) ) {
    SortedSet<T> tree = new TreeSet<T>();

    for( File f : item ) {
        tree.add( this.wrapper.wrappe( f ) );
        }

    this.duplicatesFiles.add( tree );
    }

 return this;
}

} // class
