/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/MD5FileCollectionBridgeImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.MD5FileCollectionBridgeImpl
**
*/
package cx.ath.choisnet.util.duplicate.impl;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.MD5Collection;
import cx.ath.choisnet.util.duplicate.MD5CollectionHelper;
import cx.ath.choisnet.util.duplicate.MD5FileCollection;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
** <p>
** Permet d'obtenir un {@link MD5FileCollection} à partir d'un objet
** {@link MD5Collection} en applicant la règle défini par la méthode
** {@link #toFile(String)}.
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
*/
public class MD5FileCollectionBridgeImpl
    implements MD5FileCollection
{

/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Objet MD5Collection source.
*/
private MD5Collection masterMD5Collection;

/**
** Cache pour {@link MD5Collection#getFolderFilenames()}
*/
private transient SortedSet<File> _transient_dbFoldersFiles = null;

/**
** Cache pour {@link MD5Collection#getEntryFilenames()}
*/
private transient SortedMap<MD5TreeEntry,SortedSet<File>> _transient_dbMessageDigestFiles = null;

/**
** Construction d'un objet MD5FileCollection vide.
*/
public MD5FileCollectionBridgeImpl( // ------------------------------------
    final MD5Collection masterMD5Collection
    )
{
 this.masterMD5Collection = masterMD5Collection;
}

/**
** Retourne le nombre de fichiers connus
*/
public int getEntryCount() // ---------------------------------------------
{
 return this.masterMD5Collection.getEntryCount();
}

/**
**
*/
public String toString() // -----------------------------------------------
{
 return MD5CollectionHelper.toString( this.masterMD5Collection );
}

/**
** Retourne un ensemble, non modifiable, d'objet {@link File} représentant le
** le nom complet de l'ensemble des dossiers connus de cette collection.
**
** @return un objet Set<String> non null, mais éventuellement vide.
**
** @see MD5Collection#getFolderFilenames()
*/
public Set<File> getFolderFiles() // --------------------------------------
{
 if( this._transient_dbFoldersFiles == null ) {
    this._transient_dbFoldersFiles = toSetOfFile(
                                        this.masterMD5Collection.getFolderFilenames()
                                        );
    }

 return this._transient_dbFoldersFiles;
}

/**
** Retourne un dictionnaire, non modifiable, des fichiers sous forme d'un
** object Map contenant un couple formé de l'empreinte du fichier {@link MD5TreeEntry}
** et d'un ensemble de {@link File} représentant le nom complet de chacune des
** instances de ce fichier (il doit y avoir au moins une entrée).
**
** @return un Map<MD5TreeEntry,? extends Set<File>> non null, et non vide.
**
** @see MD5Collection#getEntryFilenames()
*/
public Map<MD5TreeEntry,? extends Set<File>> getEntryFiles() // -----------
{
 if( this._transient_dbMessageDigestFiles == null ) {
    this._transient_dbMessageDigestFiles = toMapOfFile(
                                            this.masterMD5Collection.getEntryFilenames()
                                            );
    }

 return this._transient_dbMessageDigestFiles;
}

/**
** Retourne l'ensemble des noms de fichier complet correspondant à
** l'empreinte donnée.
**
** @param md5 Empreinte recherchée
**
** @return un Set<File> si au moins un fichier correspond au MD5TreeEntry
** donné, retourne null autrement.
**
** @see MD5Collection#getEntryFilenames(MD5TreeEntry)
*/
public Set<File> getEntryFiles( final MD5TreeEntry md5 ) // ---------------
{
 return this.getEntryFiles().get( md5 );
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
public int compareTo( final MD5FileCollection anOtherCollection ) // ------
{
 if( super.equals( anOtherCollection ) ) {
    return 0; // on s'arrête là, c'est le même objet !
    }

 return MD5CollectionHelper.compare( this, anOtherCollection );
}

/**
** Indicates whether some other MD5Collection is "equal to" this one.
**
*/
public boolean equals( final MD5FileCollection anOtherCollection ) // -----
{
 if( super.equals( anOtherCollection ) ) {
    return true; // on s'arrête là, c'est le même objet !
    }

 return MD5CollectionHelper.compare( this, anOtherCollection ) == 0;
}

/**
** Méthode de transformation du nom des répertoire et des fichiers en objet
** {@link File}.
** <br/>
** Cette méthode est destinée à être surchargée.
**
** @param path chaîne décrivant le nom complet du répertoire ou du fichier.
**
** @return un objet File valide (non null).
*/
public File toFile( final String path ) // --------------------------------
{
 return new File( path );
}

/**
** Transforme un ensemble de nom de fichiers en un ensemble trié d'objets File
*/
private final SortedSet<File> toSetOfFile( final Set<String> paths ) // ---
{
 final SortedSet<File> set = new TreeSet<File>();

 for( String p : paths ) {
    set.add( toFile( p ) );
    }

 return set;
}

/**
**
*/
private final SortedMap<MD5TreeEntry,SortedSet<File>> toMapOfFile( // -----
    final Map<MD5TreeEntry,? extends Set<String>> mapsrc
    )
{
 final SortedMap<MD5TreeEntry,SortedSet<File>> map
   = new TreeMap<MD5TreeEntry,SortedSet<File>>();

 for( Map.Entry<MD5TreeEntry,? extends Set<String>> entry : mapsrc.entrySet() ) {
    map.put(
        entry.getKey(),
        toSetOfFile( entry.getValue() )
        );
    }

 return map;
}

} // class
