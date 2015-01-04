/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/util/duplicate/impl/AbstractMD5FileCollection.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  3.01.042 2006.05.24 Claude CHOISNET - version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.util.duplicate.impl.AbstractMD5FileCollection
 **
 */
package cx.ath.choisnet.util.duplicate.impl;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import cx.ath.choisnet.util.duplicate.MD5CollectionHelper;
import cx.ath.choisnet.util.duplicate.MD5FileCollection;

/**
 ** Support pour les objets de type {@link MD5FileCollection}
 **
 ** @author Claude CHOISNET
 ** @since 3.01.042
 ** @version 3.01.042
 **
 ** @see MD5FileCollection
 */
public abstract class AbstractMD5FileCollection implements MD5FileCollection {
    private static final long                          serialVersionUID      = 1L;

    /**
     ** Liste des objets File correspondant aux dossiers
     */
    protected SortedSet<File>                          dbFoldersFiles;

    /**
     ** Couple (empreinte, liste de File fichiers)
     */
    protected SortedMap<MD5TreeEntry, SortedSet<File>> dbMessageDigestFiles;

    /**
     ** Mise en cache du nombre de fichiers.
     */
    private transient int                              _transient_filesCount = 0;

    /**
     ** Construction d'un objet MD5CollectionImpl vide
     */
    protected AbstractMD5FileCollection() // ----------------------------------
    {
        this.dbFoldersFiles = new TreeSet<File>();
        this.dbMessageDigestFiles = new TreeMap<MD5TreeEntry, SortedSet<File>>();
    }

    /**
     ** Retourne un ensemble, non modifiable, de chaines representant le le nom relatif de l'ensemble des dossiers connus
     * de cette collection.
     **
     ** @return un objet Set<String> non null, mais eventuellement vide.
     */
    @Override
    final public Set<File> getFolderFiles() // --------------------------------------
    {
        return this.dbFoldersFiles;
    }

    /**
     ** Retourne un dictionnaire, non modifiable, des fichiers sous forme d'un object Map contenant un couple forme de
     * l'empreinte du fichier {@link MD5TreeEntry} et d'un ensemble de chaene representant le nom relatif de chacune des
     * instances de ce fichier (il doit y avoir au moins une entree).
     **
     ** @return un Map<MD5TreeEntry,? extends Set<String>> non null, et non vide.
     */
    @Override
    final public Map<MD5TreeEntry, ? extends Set<File>> getEntryFiles() // -----------
    {
        return this.dbMessageDigestFiles;
    }

    /**
     ** Retourne l'ensemble des noms de fichier relatifs correspondant e l'empreinte donnee.
     **
     ** @param md5
     *            Empreinte recherchee
     **
     ** @return un Set<String> si au moins un fichier correspond au MD5TreeEntry donne, retourne null autrement.
     */
    @Override
    final public Set<File> getEntryFiles( final MD5TreeEntry md5 ) // ---------------
    {
        return this.dbMessageDigestFiles.get( md5 );
    }

    /**
     ** Calcul ou recalul le nombre de fichiers contenu dans la collection.
     **
     ** @see #getEntryCount()
     */
    final public void computeEntryCount() // ----------------------------------------
    {
        this._transient_filesCount = MD5CollectionHelper.getEntryCount( this );
    }

    /**
     ** Retourne le nombre de fichiers contenu dans la collection. Cette valeur etant cachee, si la collection est
     * modifiee, elle devrait etre reinitialisee {@link #computeEntryCount()}.
     **
     ** @return un int correspondant au nombre de fichiers (hors dossier) etant defini dans la collection.
     **
     ** @see #computeEntryCount()
     */
    @Override
    final public int getEntryCount() // ---------------------------------------------
    {
        if( this._transient_filesCount == 0 ) {
            computeEntryCount();
        }

        return _transient_filesCount;
    }

    /**
     ** Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    final public int compareTo( final MD5FileCollection anOtherCollection ) // ------
    {
        if( super.equals( anOtherCollection ) ) {
            return 0; // on s'arrete le, c'est le meme objet !
        }

        return MD5CollectionHelper.compare( this, anOtherCollection );
    }

    /**
     ** Indicates whether some other {@link MD5FileCollection} is "equal to" this one.
     **
     */
    final public boolean equals( final MD5FileCollection anOtherCollection ) // -----
    {
        if( super.equals( anOtherCollection ) ) {
            return true; // on s'arrete le, c'est le meme objet !
        }

        return MD5CollectionHelper.compare( this, anOtherCollection ) == 0;
    }

    @Override
    final public String toString() // -----------------------------------------------
    {
        return MD5CollectionHelper.toString( this );
    }
} // class