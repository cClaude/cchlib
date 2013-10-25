/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/MD5CollectionHelper.java
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
** cx.ath.choisnet.util.duplicate.MD5CollectionHelper
**
*/
package cx.ath.choisnet.util.duplicate;

import cx.ath.choisnet.util.checksum.MD5TreeEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
** <p>
** Classe d'aide � l'impl�mentation de {@link MD5Collection} et
** de {@link MD5FileCollection}
** </p>
**
** @author Claude CHOISNET
** @since   3.01.042
** @version 3.01.042
**
*/
public class MD5CollectionHelper
{

/**
** Retourne le nombre de fichiers connus dans l'objet
** {@link MD5Collection} donn�
*/
public static int getEntryCount( final MD5Collection aCollection ) // -----
{
 return getEntryCount( aCollection.getEntryFilenames() );
}

/**
** Retourne le nombre de fichiers connus dans l'objet
** {@link MD5FileCollection} donn�
*/
public static int getEntryCount( // ---------------------------------------
    final MD5FileCollection aCollection
    )
{
 return getEntryCount( aCollection.getEntryFiles() );
}

/**
** Retourne le nombre d'�l�ments g�n�riques connus.
*/
public static <T> int getEntryCount( // -----------------------------------
    final Map<MD5TreeEntry,? extends Set<T>> map
    )
{
 int count = 0;

 for( Collection<T> c : map.values() ) {
    count += c.size();
    }

 return count;
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
public static int compare( // ---------------------------------------------
    final MD5Collection o1,
    final MD5Collection o2
    )
{
 int cmp = compare(
            o1.getFolderFilenames(),
            o1.getEntryFilenames(),
            o2.getFolderFilenames(),
            o2.getEntryFilenames()
            );

 if( cmp == 0 ) {
    //
    // A priori pas de diff�rences
    //
    if( o1.getEntryCount() != o2.getEntryCount() ) {
        throw new RuntimeException( "Inconsistence compareTo() : diff. filesCount() " );
        }
    }

 return 0;
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
public static int compare( // ---------------------------------------------
    final MD5FileCollection o1,
    final MD5FileCollection o2
    )
{
 int cmp = compare(
            o1.getFolderFiles(),
            o1.getEntryFiles(),
            o2.getFolderFiles(),
            o2.getEntryFiles()
            );

 if( cmp == 0 ) {
    //
    // A priori pas de diff�rences
    //
    if( o1.getEntryCount() != o2.getEntryCount() ) {
        throw new RuntimeException( "Inconsistence compareTo() : diff. filesCount() " );
        }
    }

 return 0;
}

/**
** Compares this object with the specified object for order. Returns a
** negative integer, zero, or a positive integer as this object is less
** than, equal to, or greater than the specified object.
*/
public static <T extends Comparable<T>> int compare( // -------------------
    final Set<T>                                o1folders,
    final Map<MD5TreeEntry,? extends Set<T>>    o1files,
    final Set<T>                                o2folders,
    final Map<MD5TreeEntry,? extends Set<T>>    o2files
    )
{
 //
 // Analyse des dossiers
 //
 final int cmpFolders = MD5CollectionHelper.compareTo(
                            o1folders,
                            o2folders
                            );

 if( cmpFolders != 0 ) {
    return cmpFolders;
    }

    //
    // Analyse des fichiers
    //
    {
        Iterator<? extends Map.Entry<MD5TreeEntry,? extends Set<T>>> iter0
                = o1files.entrySet().iterator();

        Iterator<? extends Map.Entry<MD5TreeEntry,? extends Set<T>>> iter1
                = o2files.entrySet().iterator();

        while( iter0.hasNext() ) {

            if( iter1.hasNext() ) {
                Map.Entry<MD5TreeEntry,? extends Set<T>> obj0 = iter0.next();
                Map.Entry<MD5TreeEntry,? extends Set<T>> obj1 = iter1.next();

                int resKey = obj0.getKey().compareTo( obj1.getKey() );

                if( resKey != 0 ) {
                    return resKey; // Cl�s diff�rentes
                    }

                int cmpFiles = MD5CollectionHelper.compareTo( obj0.getValue(), obj1.getValue() );

                if( cmpFiles != 0 ) {
                    return cmpFiles; // Valeur diff�rentes
                    }
                }
            else {
                return -1; // Pas le m�me nombre d'entr�es
                }
            }

        if( iter1.hasNext() ) {
            return 1; // Pas le m�me nombre d'entr�es
            }

    }

 return 0;
}

/**
**
*/
private final static <T extends Comparable<T>> int compareTo( // ----------
    final Iterable<T> iterable0,
    final Iterable<T> iterable1
    )
{
 final Iterator<T>  iter0   = iterable0.iterator();
 final Iterator<T>  iter1   = iterable1.iterator();

 while( iter0.hasNext() ) {

    if( iter1.hasNext() ) {
        int res = iter0.next().compareTo( iter1.next() );

        if( res != 0 ) {
            return res; // Valeur diff�rentes
            }
        }
    else {
        return -1; // Pas le m�me nombre d'entr�es
        }
    }

 if( iter1.hasNext() ) {
    return 1; // Pas le m�me nombre d'entr�es
    }

 return 0;
}

/**
**
*/
public static String toString( final MD5Collection o ) // -----------------
{
 return MD5CollectionHelper.toString(
                    o.getFolderFilenames(),
                    o.getEntryFilenames()
                    );
}

/**
**
*/
public static String toString( final MD5FileCollection o ) // -------------
{
 return MD5CollectionHelper.toString(
                    o.getFolderFiles(),
                    o.getEntryFiles()
                    );
}

/**
**
*/
public static <T> String toString( // -------------------------------------
    final Set<T>                                folders,
    final Map<MD5TreeEntry,? extends Set<T>>    files
    )
{
 final StringBuilder sb = new StringBuilder();

/*
 for( T folder : folders ) {
    sb.append( "D:" + folder + "\n" );
    }

 for( Map.Entry<MD5TreeEntry,? extends Set<T>> entry : files.entrySet() ) {
    sb.append( "F:" + entry.getKey() + "\n" );
    }
*/
 sb.append( "D=" + folders.size() + "/F=" + getEntryCount( files ) );

 return sb.toString();
}

/**
**
*/
private final static String toXML( final String datas ) // ----------------
{
 return datas.replaceAll( "&", "&amp;" );
}

/**
**
*/
public static <T> void toXML( // ------------------------------------------
    final String                                rootElementName,
    final Set<T>                                folders,
    final Map<MD5TreeEntry,? extends Set<T>>    files,
    final Appendable                            output
    )
    throws java.io.IOException
{
 output.append( "<" + rootElementName + ">\n" );
 output.append( " <folders>\n" );

 for( T folder : folders ) {
    output.append( "  <folder>" + toXML( folder.toString() ) + "</folder>\n" );
    }

 output.append( " </folders>\n" );
 output.append( " <files>\n" );

 for( Map.Entry<MD5TreeEntry,? extends Set<T>> entry : files.entrySet() ) {
    output.append( "  <entry id=\"" + entry.getKey() + "\">\n" );

    for( T file : entry.getValue() ) {
        output.append( "    <file>" + toXML( file.toString() ) + "</file>\n" );
        }

    output.append( "  </entry>\n" );
    }

 output.append( " </files>\n" );
 output.append( "</" + rootElementName + ">\n" );
}

/**
** Transformation d'un objet {@link MD5Collection} en un flux XML.
**
** @param rootElementName   Nom de l'�l�ment XML racine.
** @param aMD5Collection    Objet {@link MD5Collection} � transformer en XML.
** @param output            Objet {@link Appendable} destin� � recevoir le flux XML.
**
** @throws java.io.IOException en cas de probl�me lors de l'ajout dans le flux.
*/
public static void toXML( // ----------------------------------------------
    final String        rootElementName,
    final MD5Collection aMD5Collection,
    final Appendable    output
    )
    throws java.io.IOException
{
 MD5CollectionHelper.toXML(
    rootElementName,
    aMD5Collection.getFolderFilenames(),
    aMD5Collection.getEntryFilenames(),
    output
    );
}

/**
** Transformation d'un objet {@link MD5FileCollection} en un flux XML.
**
** @param rootElementName       Nom de l'�l�ment XML racine.
** @param aMD5FileCollection    Objet {@link MD5Collection} � transformer en XML.
** @param output                Objet {@link Appendable} destin� � recevoir le flux XML.
**
** @throws java.io.IOException en cas de probl�me lors de l'ajout dans le flux.
*/
public static void toXML( // ----------------------------------------------
    final String            rootElementName,
    final MD5FileCollection aMD5FileCollection,
    final Appendable        output
    )
    throws java.io.IOException
{
 MD5CollectionHelper.toXML(
    rootElementName,
    aMD5FileCollection.getFolderFiles(),
    aMD5FileCollection.getEntryFiles(),
    output
    );
}

} // class

