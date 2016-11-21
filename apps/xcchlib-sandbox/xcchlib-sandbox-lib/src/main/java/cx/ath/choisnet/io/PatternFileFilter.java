/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/PatternFileFilter.java
** Description   :
** Encodage      : ANSI
**
**  3.01.031 2006.04.25 Claude CHOISNET - Version initial
**  3.02.042 2007.01.08 Claude CHOISNET
**                      Ajout de: asFileFilter()
**                      Ajout de: asFilenameFilter()
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.PatternFileFilter
**
*/
package cx.ath.choisnet.io;

import java.io.File;
import java.util.regex.Pattern;

/**
** <p>
** Permet de construire un {@link java.io.FileFilter} en utilisant
** les expressions regulieres.
** </p>
** La vue {@link java.io.FileFilter} permet de faire une recherche sur le
** nom complet du fichier ({@link #accept(File)}), alors que la vue
** {@link java.io.FilenameFilter} permet de faire une recherche uniquement
** basee sur le nom du fichier ({@link #accept(File,String)}).
**
** @author Claude CHOISNET
** @since   3.01.031
** @version 3.02.042
**
** @see FileFilterHelper
** @see Pattern
** @see File#getPath()
*/
public class PatternFileFilter
    implements
        java.io.FileFilter,
        java.io.FilenameFilter,
        java.io.Serializable
{
/** */
private static final long serialVersionUID = 1L;

/** */
private Pattern pattern;

/**
**
*/
public PatternFileFilter( // ----------------------------------------------
    final Pattern pattern
    )
{
 this.pattern = pattern;
}

/**
**
** @see Pattern#compile(String)
*/
public PatternFileFilter( // ----------------------------------------------
    final String regex
    )
{
 this( Pattern.compile( regex ) );
}

/**
** Retourne true si le chemin complet du fichier correspont e l'expression
** reguliere.
**
** @param file  Fichier e tester
**
** @see java.io.FileFilter#accept(File)
** @see File#getPath()
*/
@Override
public boolean accept( final File file ) // -------------------------------
{
 return this.pattern.matcher( file.getPath() ).matches();
}

/**
** Retourne true si le nom du fichier correspont e l'expression
** reguliere.
**
** @param dir   Objet File du dossier courant (ignore)
** @param name  Nom du fichier e tester.
**
** @see java.io.FilenameFilter#accept(File,String)
*/
@Override
public boolean accept( final File dir, final String name ) // -------------
{
 return this.pattern.matcher( name ).matches();
}

/**
** Retourne un object {@link java.io.FileFilter}, cette methode est
** destinee e lever les ambiguitees.
**
** @see #asFilenameFilter()
**
** @since 3.02.042
*/
public java.io.FileFilter asFileFilter() // -------------------------------
{
 return this;
}

/**
** Retourne un object {@link java.io.FilenameFilter}, cette methode est
** destinee e lever les ambiguitees.
**
** @see #asFileFilter()
**
** @since 3.02.042
*/
public java.io.FilenameFilter asFilenameFilter() // -----------------------
{
 return this;
}

} // class

