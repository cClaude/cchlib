/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/impl/AbstractMD5Collection.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET - version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.impl.AbstractMD5Collection
**
*/
package cx.ath.choisnet.util.duplicate.impl;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.MD5Collection;
import cx.ath.choisnet.util.duplicate.MD5CollectionHelper;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
** Support pour les objets de type {@link MD5Collection}
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see MD5Collection
*/
public abstract class AbstractMD5Collection
    implements MD5Collection
{
/** serialVersionUID */
// private static final long serialVersionUID = 1L;

/**
** Liste des objets File correspondant aux dossiers
*/
protected SortedSet<String> dbFoldersFilenames;

/**
** Couple (empreinte, liste de File fichiers)
*/
protected SortedMap<MD5TreeEntry,SortedSet<String>> dbMessageDigestFilenames;

/**
** Mise en cache du nombre de fichiers.
*/
private transient int _transient_filesCount = 0;

/**
** Construction d'un objet MD5CollectionImpl vide
*/
protected AbstractMD5Collection() // --------------------------------------
{
 this.dbFoldersFilenames        = new TreeSet<String>();
 this.dbMessageDigestFilenames  = new TreeMap<MD5TreeEntry,SortedSet<String>>();
}

/**
** Retourne un ensemble, non modifiable, de chaines repr�sentant le
** le nom relatif de l'ensemble des dossiers connus de cette collection.
**
** @return un objet Set<String> non null, mais �ventuellement vide.
*/
final
public Set<String> getFolderFilenames() // --------------------------------
//public Set<String> folderNames() // ---------------------------------------
{
 return this.dbFoldersFilenames;
}

/**
** Retourne un dictionnaire, non modifiable, des fichiers sous forme d'un
** object Map contenant un couple form� de l'empreinte du fichier {@link MD5TreeEntry}
** et d'un ensemble de cha�ne repr�sentant le nom relatif de chacune des
** instances de ce fichier (il doit y avoir au moins une entr�e).
**
** @return un Map<MD5TreeEntry,? extends Set<String>> non null, et non vide.
*/
final
public Map<MD5TreeEntry,? extends Set<String>> getEntryFilenames() // -----
//public Map<MD5TreeEntry,? extends Set<String>> files() // -----------------
{
 return this.dbMessageDigestFilenames;
}

/**
** Retourne l'ensemble des noms de fichier relatifs correspondant �
** l'empreinte donn�e.
**
** @param md5 Empreinte recherch�e
**
** @return un Set<String> si au moins un fichier correspond au MD5TreeEntry
** donn�, retourne null autrement.
*/
final
public Set<String> getEntryFilenames( final MD5TreeEntry md5 ) // ---------
//public Set<String> getFileNames( final MD5TreeEntry md5 ) // --------------
{
 return this.dbMessageDigestFilenames.get( md5 );
}

/**
** Calcul ou recalul le nombre de fichiers contenu dans la collection.
**
** @see #getEntryCount()
*/
final
public void computeEntryCount() // ----------------------------------------
{
 this._transient_filesCount = MD5CollectionHelper.getEntryCount( this );
}

/**
** Retourne le nombre de fichiers contenu dans la collection. Cette valeur
** �tant cach�e, si la collection est modifi�e, elle devrait �tre
** r�initialis�e {@link #computeEntryCount()}.
**
** @return un int correspondant au nombre de fichiers (hors dossier) �tant
**         d�fini dans la collection.
**
** @see #computeEntryCount()
*/
final
public int getEntryCount() // ---------------------------------------------
{
 if( this._transient_filesCount == 0 ) {
    computeEntryCount();
    }

 return _transient_filesCount;
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
final
public int compareTo( final MD5Collection anOtherMD5Collection ) // -------
{
 if( super.equals( anOtherMD5Collection ) ) {
    return 0; // on s'arr�te l�, c'est le m�me objet !
    }

 return MD5CollectionHelper.compare( this, anOtherMD5Collection );
}

/**
** Indicates whether some other {@link MD5Collection} is "equal to" this one.
**
*/
final
public boolean equals( final MD5Collection anOtherMD5Collection ) // ------
{
 if( super.equals( anOtherMD5Collection ) ) {
    return true; // on s'arr�te l�, c'est le m�me objet !
    }

 return MD5CollectionHelper.compare( this, anOtherMD5Collection ) == 0;
}

/**
**
*/
final
public String toString() // -----------------------------------------------
{
 return MD5CollectionHelper.toString( this );
}

/**
**
private void writeObject( final java.io.ObjectOutputStream stream ) // ----
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeInt( this.baseFolderFilePathLen  );
}
*/

/**
**
private void readObject( final java.io.ObjectInputStream stream ) // ------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.baseFolderFilePathLen  = stream.readInt();
}
*/

} // class
