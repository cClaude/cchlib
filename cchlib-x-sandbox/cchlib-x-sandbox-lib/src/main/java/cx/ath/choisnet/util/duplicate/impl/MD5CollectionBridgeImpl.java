/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/MD5CollectionBridgeImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.MD5CollectionBridgeImpl
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
** Permet d'obtenir un {@link MD5Collection} à partir d'un objet
** {@link MD5FileCollection} en applicant la règle défini par la
** méthode {@link #toString(File)}.
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
*/
public class MD5CollectionBridgeImpl
    implements MD5Collection
{

/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Objet MD5FileCollection source.
*/
private MD5FileCollection masterMD5FileCollection;

/**
** Cache pour {@link MD5Collection#getFolderFiles()}
*/
private transient SortedSet<String> _transient_dbFoldersFilenames = null;

/**
** Cache pour {@link MD5Collection#getEntryFiles()}
*/
private transient SortedMap<MD5TreeEntry,SortedSet<String>> _transient_dbMessageDigestFilenames = null;

/**
** Construction d'un objet MD5FileCollection vide.
*/
public MD5CollectionBridgeImpl( // ----------------------------------------
    final MD5FileCollection masterMD5FileCollection
    )
{
 this.masterMD5FileCollection = masterMD5FileCollection;
}

/**
** Retourne le nombre de fichiers connus
*/
public int getEntryCount() // ---------------------------------------------
{
 return this.masterMD5FileCollection.getEntryCount();
}

/**
**
*/
public String toString() // -----------------------------------------------
{
 return MD5CollectionHelper.toString( this.masterMD5FileCollection );
}

/**
** Retourne un ensemble, non modifiable, de chaîne représentant le
** le nom complet de l'ensemble des dossiers connus de cette collection.
**
** @return un objet Set<String> non null, mais éventuellement vide.
**
** @see MD5FileCollection#getFolderFiles()
*/
public Set<String> getFolderFilenames() // --------------------------------
{
 if( this._transient_dbFoldersFilenames == null ) {
    this._transient_dbFoldersFilenames = toSetOfPath(
                                        this.masterMD5FileCollection.getFolderFiles()
                                        );
    }

 return this._transient_dbFoldersFilenames;
}

/**
** Retourne un dictionnaire, non modifiable, des fichiers sous forme d'un
** object Map contenant un couple formé de l'empreinte du fichier {@link MD5TreeEntry}
** et d'un ensemble chaînes représentant le nom complet de chacune des
** instances de ce fichier (il doit y avoir au moins une entrée).
**
** @return un Map<MD5TreeEntry,? extends Set<String>> non null, et non vide.
**
** @see MD5FileCollection#getEntryFiles()
*/
public Map<MD5TreeEntry,? extends Set<String>> getEntryFilenames() // -----
{
 if( this._transient_dbMessageDigestFilenames == null ) {
    this._transient_dbMessageDigestFilenames = toMapOfPath(
                                            this.masterMD5FileCollection.getEntryFiles()
                                            );
    }

 return this._transient_dbMessageDigestFilenames;
}

/**
** Retourne l'ensemble des noms de fichier complet correspondant à
** l'empreinte donnée.
**
** @param md5 Empreinte recherchée
**
** @return un Set<String> si au moins un fichier correspond au MD5TreeEntry
** donné, retourne null autrement.
**
** @see MD5FileCollection#getEntryFiles(MD5TreeEntry)
*/
public Set<String> getEntryFilenames( final MD5TreeEntry md5 ) // ---------
{
 return this.getEntryFilenames().get( md5 );
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
public int compareTo( final MD5Collection anOtherCollection ) // ------
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
public boolean equals( final MD5Collection anOtherCollection ) // -----
{
 if( super.equals( anOtherCollection ) ) {
    return true; // on s'arrête là, c'est le même objet !
    }

 return MD5CollectionHelper.compare( this, anOtherCollection ) == 0;
}

/**
** Méthode de transformation des objets {@link File} des répertoire et des
** fichiers en chaîne.
** <br/>
** Cette méthode est destinée à être surchargée.
**
** @param file Objet File correspondant à un répertoire ou un fichier.
**
** @return une chaîne correspondant au nom complet de répertoire ou du
**         dossier (non null).
*/
public String toString( final File file ) // ------------------------------
{
 return file.getPath();
}

/**
** Transforme un ensemble d'objet File en un ensemble trié de noms de fichiers
*/
private final SortedSet<String> toSetOfPath( final Set<File> files ) // ---
{
 final SortedSet<String> set = new TreeSet<String>();

 for( File f : files ) {
    set.add( toString( f ) );
    }

 return set;
}

/**
**
*/
private final SortedMap<MD5TreeEntry,SortedSet<String>> toMapOfPath( // ---
    final Map<MD5TreeEntry,? extends Set<File>> mapsrc
    )
{
 final SortedMap<MD5TreeEntry,SortedSet<String>> map
   = new TreeMap<MD5TreeEntry,SortedSet<String>>();

 for( Map.Entry<MD5TreeEntry,? extends Set<File>> entry : mapsrc.entrySet() ) {
    map.put(
        entry.getKey(),
        toSetOfPath( entry.getValue() )
        );
    }

 return map;
}


} // class
