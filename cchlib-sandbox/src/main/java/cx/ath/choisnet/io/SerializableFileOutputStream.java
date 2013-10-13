/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/SerializableFileOutputStream.java
** Description   :
** Encodage      : ANSI
**
**  2.01.010 2005.10.07 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.SerializableFileOutputStream
**
*/
package cx.ath.choisnet.io;

import java.io.File;
import java.io.OutputStream;

/**
** <p>Objet équivalent à la classe {@link java.io.FileOutputStream}, et implémentant
** l'interface {@link java.io.Serializable}.</p>
** <br />
** Le principe de la "sérialisation" de cet objet fonctionne comme suit:
** <br />
** <ul>
**  <li>Lorsque l'objet est "sérialiser", le flux de support est fermé
**      proprement, et les références ayant permis de le construire est
**      sauvegarder dans le flux de "sérialisation".</li>
**  <li>Lorsque l'objet est recontruit, le flux de support (fichier) est
**      recréé sur lequel on viendra ajouter les données.</li>
** </ul>
** <br/>
** <b>La solution proposée par cette sérialisation n'est pas utilisable
** pour une sérialisation à travers un réseau</b>.
**
** @author Claude CHOISNET
** @version 2.01.010
** @since   2.01.010
**
** @see java.io.FileOutputStream
** @see java.io.Serializable
** @see SerializableFileWriter
*/
public class SerializableFileOutputStream
    extends OutputStream
        implements java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private File file;

/** */
private transient OutputStream output;

/**
** <p>Constructs a SerializableFileOutputStream object given a File object.</>
**
*/
public SerializableFileOutputStream( // -----------------------------------
    File file
    )
    throws
        java.io.FileNotFoundException
{
 this( file, false );
}

/**
** <p>Constructs a SerializableFileOutputStream object given a File object.</>
*/
public SerializableFileOutputStream( // -----------------------------------
    File    file,
    boolean append
    )
    throws
        java.io.FileNotFoundException
{
 this.file = file;

 this.open( append );
}

/**
** par la réprise (sérialisation) il faut mettre le paramètre 'append' à
** la valeur 'true'
*/
private void open( boolean append ) // ------------------------------------
    throws java.io.FileNotFoundException
{
 this.output = new java.io.FileOutputStream( this.file, append );
}

/**
**
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 output.close();
}

/**
**
*/
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 output.flush();
}

/**
**
*/
public void write( int b ) // ---------------------------------------------
    throws java.io.IOException
{
 output.write( b );
}

/**
** java.io.Serializable
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
    throws java.io.IOException
{
 output.flush();
 output.close();

 stream.defaultWriteObject();
}

/**
** java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws java.io.IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 //
 // Réinitialisation des champs non sauvegardés
 //
 this.open( true );
}

} // class


