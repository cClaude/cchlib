/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5FileCollection.java
** Description   :
** Encodage      : ANSI
**
**  3.01.042 2006.05.24 Claude CHOISNET
**                      Reprise de la classe:
**                          cx.ath.choisnet.util.checksum.MD5Collection
**                      sous le nom:
**                          cx.ath.choisnet.util.duplicate.MD5Collection
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.MD5FileCollection
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import java.io.File;
import java.util.Map;
import java.util.Set;

/**
** <p>
** Vue par empreintes d'un ensemble de fichiers
** <br/>
** <br/>
** Cette classe permet d'avoir une vue d'une arboressance de
** fichiée basée sur une liste d'objet {@link File} de répertoires et sur
** l'empreinte des fichiers ({@link MD5TreeEntry}) auquel est
** attachée une liste de d'objet {@link File} représentant les fichiers.
** <br/>
** <br/>
** Voir également {@link MD5Collection}
** </p>
=**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
** @see cx.ath.choisnet.util.checksum.MD5TreeEntry
** @see DuplicateFileLayer
** @see DuplicateLayer
** @see MD5Collection
** @see MD5FileCollectionCompator
*/
public interface MD5FileCollection
    extends
        Comparable<MD5FileCollection>,
        java.io.Serializable
{

/**
** Retourne un ensemble, non modifiable, d'objet {@link File} représentant le
** le nom complet de l'ensemble des dossiers connus de cette collection.
**
** @return un objet Set<String> non null, mais éventuellement vide.
**
** @see MD5Collection#getFolderFilenames()
*/
public Set<File> getFolderFiles(); // -------------------------------------

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
public Map<MD5TreeEntry,? extends Set<File>> getEntryFiles(); // ----------

/**
** Retourne l'ensemble des noms de fichier complet correspondant é
** l'empreinte donnée.
**
** @param md5 Empreinte recherchée
**
** @return un Set<File> si au moins un fichier correspond au MD5TreeEntry
** donné, retourne null autrement.
**
** @see MD5Collection#getEntryFilenames(MD5TreeEntry)
*/
public Set<File> getEntryFiles( MD5TreeEntry md5 ); // --------------------

/**
** Retourne le nombre de fichiers contenu dans la collection.
**
** @return un int correspondant au nombre de fichiers (hors dossier) étant
**         défini dans la collection.
*/
public int getEntryCount(); // --------------------------------------------


} // class
