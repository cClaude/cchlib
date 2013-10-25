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
** Classe permettant de retrouver, de g�rer et d'effectuer des traitements
** li� aux fichiers trouv� en double.
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
    ** Param�tre pour les m�thodes delete et select
    **
    ** @see #select(FileFilter,EnumSet)
    */
    public enum Params
    {

        /**
        ** La suppression de toutes les occurences est autoris�e, par
        ** d�faut si la suppression engendre la suppression de toutes
        ** les occurences trouv�s AUCUN fichier n'est supprim�e.
        ** deprecated no remplacement
        Deprecated
        deleteAllAllowed,
        */

        /**
        ** Supprime physiquement le fichier. Par d�faut le fichier n'est
        ** pas effac�.
        ** deprecated no remplacement
        Deprecated
        doDelete,
        */

        /**
        ** Ne supprime pas de la liste interne. Par d�faut
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
** Collection d'un SortedSet de fichiers identiques (avec des noms diff�rents)
*/
private Collection<SortedSet<File>> duplicatesFiles;

/**
** Liste des fichiers (ou dossiers) devant �tre conserv�s.
*/
private List<File> filesToKeep;

/**
** Expression r�guli�re des fichiers (ou dossier) devant �tre conserv�s.
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
** @return une liste ({@link Collection}) de listes tri�es ({@link SortedSet})
** d'objets {@link File}.
*/
public Collection<? extends SortedSet<File>> getDuplicateFilesList() //---
{
 return Collections.unmodifiableCollection( this.duplicatesFiles );
}

/**
** <p>
** Permet de d�finir un filtre (FileFilter) des fichiers � ignorer. Les
** entr�es correspondant � ce filtre sont supprimer de la liste de
** r�f�rence : ainsi s'il n'existe qu'un seul double celui-ci sera
** lui aussi ignor�.
** </p>
** <p>
** Cette m�thode peut �tre appell� plusieurs fois avec des filtres diff�rents.
** </p>
** @param fileFilter    Object FileFilter valide permetant d'identifier les
**                      fichiers � ignorer
**
** @return l'objet DuplicateLayer lui-m�me.
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
            // Supprime l'entr�e
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
** @return l'objet DuplicateLayer lui-m�me.
*/
public DuplicateLayer addFileToKeep( final File file ) // -----------------
{
 this.filesToKeep.add( file );

 return this;
}

/**
**
** @return l'objet DuplicateLayer lui-m�me.
**
** @see java.util.regex.Pattern
*/
public DuplicateLayer addFileToKeep( final String regexp ) // -------------
{
 this.filesToKeepRegExp.add( Pattern.compile( regexp ) );

 return this;
}

/**
** Permet de savoir si un fichier doit �tre prot�g� ou non
**
** @return l'objet DuplicateLayer lui-m�me.
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
** Construit une collection de fichier  � partir de la liste des
** fichiers interne et d'un filtre ({@link FileFilter}) donn�
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
**                          d'identifier les fichiers � copier dans
**                          la liste.
** @param params           Param�tres permettant d'affiner le traitement.
**
** @return une collection des fichiers qui correspondent aux crit�res.
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
            // est-il dans la liste des fichiers prot�g�s
            //
            if( isDeletable( f ) ) {
                currentSelectList.add( f );
                }
            }
        }

    final int size = currentSelectList.size();

    if( size > 0 ) {
        //
        // Il y'a quelque chose � selectionner
        //

        if( selectAllAllowed || size < filesList.size() ) {
            //
            // Tous les �l�ments non pas �t� s�lectionn�
            // ou bien on est autoris� � s�lectionner tous les
            // �l�ments
            //
            result.addAll( currentSelectList );
            }

        currentSelectList.clear();
        }
    }

 return result;
}

/**
** Construit une collection de fichier  � partir de la liste des
** fichiers interne et d'un filtre ({@link CollectionFilter}) donn�
**
** @param collectionFileFilter Objet {@link CollectionFilter} valide
**          permettant de filters pour chaques occurances d'un fichier
**          le ou les fichiers qui devront �tre retourn�s.
**
** @return une collection des fichiers qui correspondent aux crit�res.
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
** Met � jour la liste des fichiers en double, en supprimant de la liste
** les fichiers n'existes plus physiquement. Cas d'une suppression par un
** autre thread, par exemple, ou d'un nettoyage manuel...
**
** @return l'objet DuplicateLayer lui-m�me.
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

