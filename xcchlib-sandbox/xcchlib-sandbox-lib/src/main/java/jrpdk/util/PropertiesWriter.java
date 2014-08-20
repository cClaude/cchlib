/*
** $VER: PropertiesWriter.java
*/
package jrpdk.util;

import java.io.PrintWriter;
import java.io.OutputStream;

/**
** Classe permettant de construire des fichiers properties simplement.
**
** @author Claude CHOISNET
** @version 1.00 24/03/2001
*/
public class PropertiesWriter
{
private       PrintWriter   output;
private final boolean       doNotSaveNullValue;

/**
** Construction e partir d'objet PrintWriter<P>
**
** @param properties    PrintWriter valide dans lequel sera construit
**                      le fichier properties.
** @param hideNullValue Si true les couples (cle,objets) pour lesquels
**                      l'objet est null ne sont pas incrits dans le
**                      fichier properties, si false seule la cle
**                      est incrite.
*/
public PropertiesWriter( // -----------------------------------------------
    PrintWriter properties,
    boolean     hideNullValue
    )
{
 this.output                = properties;
 this.doNotSaveNullValue    = hideNullValue;
}

/**
** Construction e partir d'objet PrintWriter<P>
**
** Par default les couples (cle,objets) pour lesquels l'objet est null ne
** sont pas incrits dans le fichier properties, si false seule la cle
** est incrite.
*/
public PropertiesWriter( PrintWriter properties ) // ----------------------
{
 this( properties, true );
}

/**
** Construction e partir d'objet OutputStream
**
** @param properties    OutputStream valide dans lequel sera construit
**                      le fichier properties.
** @param hideNullValue Si true les couples (cle,objets) pour lesquels
**                      l'objet est null ne sont pas incrits dans le
**                      fichier properties, si false seule la cle
**                      est incrite.
*/
public PropertiesWriter( // -----------------------------------------------
    OutputStream    properties,
    boolean         hideNullValue
    )
{
 this( new PrintWriter( properties ), hideNullValue );
}

/**
** Construction e partir d'objet OutputStream<P>
**
** Par default les couples (cle,objets) pour lesquels l'objet est null ne
** sont pas incrits dans le fichier properties, si false seule la cle
** est incrite.
*/
public PropertiesWriter( OutputStream properties ) // ---------------------
{
 this( new PrintWriter( properties ), true );
}

@Override
protected void finalize() throws Throwable // -----------------------------
{
 output.flush();
 output.close();

 super.finalize();
}

/**
** Ajout d'une ligne dans le fichier properties. La ligne est ajoutee
** sans aucun traitement specifique.
*/
public void putLine( String line ) // -------------------------------------
{
 /* System.out.println( "putLine(" + line + ")" ); /* DEBUG */

 output.println( line );
}

/**
** Ajout d'une ligne de commentaire dans le fichier properties.
*/
public void putComment( String comment ) // -------------------------------
{
 putLine( "# " + comment );
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
**
** @param key       Cle, String non nulle
** @param value     Valeur, String non nulle
*/
private void private_set( String key, String value ) // -------------------
{
 putLine( key + "\t=" + value );
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
*/
public void set( String key, String value ) // ----------------------------
{
 /* System.out.println( "Set( " + key + " String[" + value + "])" ); /* DEBUG */

 if( value == null ) {
    if( doNotSaveNullValue == false ) {
        private_set( key, "" );
        }
    }
 else {
    private_set( key, value );
    }
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
*/
public void set( String key, boolean value ) // ---------------------------
{
 private_set( key, String.valueOf( value ) );
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
*/
public void set( String key, Object value ) // ----------------------------
{
 /* System.out.println( "Set( " + key + " Object[" + value + "])" ); /* DEBUG */

 if( value == null ) {
    if( doNotSaveNullValue == false ) {
        private_set( key, "" );
        }
    }
 else {
    private_set( key, value.toString() );
    }
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
*/
public void set( String key, int value ) // -------------------------------
{
 private_set( key, String.valueOf( value ) );
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
*/
public void set( String key, long value ) // ------------------------------
{
 private_set( key, String.valueOf( value ) );
}

/**
** Ajout d'un couple cle, valeur dans le fichier properties.
*/
public void set( String key, float value ) // -----------------------------
{
 private_set( key, String.valueOf( value ) );
}

} // class
