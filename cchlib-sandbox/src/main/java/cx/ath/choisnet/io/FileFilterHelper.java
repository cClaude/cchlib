/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/FileFilterHelper.java
** Description   :
** Encodage      : ANSI
**
**  2.01.031 2005.11.18 Claude CHOISNET - Version initial
**                      Code issu de cx.ath.choisnet.io.IOTools
**  2.01.042 2006.01.09 Claude CHOISNET
**                      Ajout de l'interface SerializableFileFilter
**                      Ajout de: trueFileFilter()
**  3.01.031 2006.04.25 Claude CHOISNET - Version initial
**                      La méthode nameMatchesFileFilter(String) est
**                      deprecated au profit de PatternFileFilter
**  3.01.041 2006.05.24 Claude CHOISNET - Version initial
**                      La méthode not(FileFilter) retourne maintenant un
**                      objet SerializableFileFilter
**                      La méthode not(FileFilter) retourne maintenant un
**                      objet SerializableFileFilter
**                      La méthode and(FileFilter,FileFilter) retourne
**                      maintenant un objet SerializableFileFilter
**                      La méthode and(FileFilter[]) retourne
**                      maintenant un objet SerializableFileFilter
**                      La méthode or(FileFilter,FileFilter) retourne
**                      maintenant un objet SerializableFileFilter
**                      La méthode or(FileFilter[]) retourne
**                      maintenant un objet SerializableFileFilter
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.FileFilterHelper
**
*/
package cx.ath.choisnet.io;

import java.io.FileFilter;
import java.io.File;

/**
** <p>
** Permet de construire des {@link java.io.FileFilter} classiques.
** </p>
**
**
** @author Claude CHOISNET
** @since   2.01.031
** @version 3.01.041
**
** @see FileFilter
** @see PatternFileFilter
*/
public class FileFilterHelper
{

//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
    ** Permet de construire des objects {@link java.io.FileFilter}
    ** {@link java.io.Serializable}
    */
    public static interface SerializableFileFilter
        extends
            java.io.FileFilter,
            java.io.Serializable
    {
    }
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

/**
** <p>
** Filtre n'acceptant que les dossiers
** </p>
**
** @return un object {@link FileFilter} {@link java.io.Serializable} limitant
**         le filtre aux dossiers.
**
** @see DirectoryIterator
*/
public static SerializableFileFilter directoryFileFilter() // -------------
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File file )
        {
            return file.isDirectory();
        }
    };
}

/**
** <p>
** Filtre retournant toujour vrai (true).
** </p>
**
** @return un object {@link FileFilter} {@link java.io.Serializable} acceptant
**         tous les objets File.
**
*/
public static SerializableFileFilter trueFileFilter() // ------------------
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File file )
        {
            return true;
        }
    };
}

/**
** <p>
**
** </p>
**
*/
public static SerializableFileFilter not( // ------------------------------
    final FileFilter aFileFilter
    )
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File file )
        {
            return !aFileFilter.accept( file );
        }
    };
}

/**
** <p>
**
** </p>
**
*/
public static SerializableFileFilter and( // ------------------------------------------
    final FileFilter firstFileFilter,
    final FileFilter secondFileFilter
    )
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File file )
        {
            if( firstFileFilter.accept( file ) ) {
                return secondFileFilter.accept( file );
                }

            return false;
        }
    };
}

/**
** <p>
**
** </p>
**
*/
public static SerializableFileFilter and( // ------------------------------
    final FileFilter ... fileFilters
    )
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File file )
        {
            for( FileFilter ff : fileFilters ) {
                if( ! ff.accept( file ) ) {
                    return false;
                    }
                }

            return true;
        }
    };
}

/**
** <p>
**
** </p>
**
*/
public static SerializableFileFilter or( // -------------------------------
    final FileFilter    firstFileFilter,
    final FileFilter    secondFileFilter
    )
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File pathname )
        {
            if( firstFileFilter.accept( pathname ) ) {
                return true;
                }

            return secondFileFilter.accept( pathname );
        }
    };
}

/**
** <p>
**
** </p>
**
*/
public static SerializableFileFilter or( // -------------------------------
    final FileFilter ... fileFilters
    )
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File pathname )
        {
            for( FileFilter ff : fileFilters ) {
                if( ff.accept( pathname ) ) {
                    return true;
                    }
                }

            return false;
        }
    };
}

/**
** <p>
** Permet de filtrer les fichiers vides.
** </p>
**
** @since 3.01.041
*/
public static SerializableFileFilter zeroLengthFileFilter() // ------------
{
 return new SerializableFileFilter()
    {
        private static final long serialVersionUID = 1L;

        public boolean accept( final File file )
        {
            return file.length() == 0;
        }
    };
}

/**
** <p>
** Construit un FileFilter basé sur une expression régulière.
** </p>
**
** @deprecated use {@link PatternFileFilter} instead
*/
@Deprecated
public static FileFilter nameMatchesFileFilter( final String regex ) // ---
{
// final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile( regex );
//
// return new FileFilter()
//    {
//        private static final long serialVersionUID = 1L;
//
//        public boolean accept( File pathname )
//        {
//            return pattern.matcher( pathname.getName() ).matches();
//        }
//    };
 return new PatternFileFilter( regex );
}

} // class
