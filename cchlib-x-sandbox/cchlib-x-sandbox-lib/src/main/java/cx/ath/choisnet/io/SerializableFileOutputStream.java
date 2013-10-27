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
** <p>Objet �quivalent � la classe {@link java.io.FileOutputStream}, et impl�mentant
** l'interface {@link java.io.Serializable}.</p>
** <br />
** Le principe de la "s�rialisation" de cet objet fonctionne comme suit:
** <br />
** <ul>
**  <li>Lorsque l'objet est "s�rialiser", le flux de support est ferm�
**      proprement, et les r�f�rences ayant permis de le construire est
**      sauvegarder dans le flux de "s�rialisation".</li>
**  <li>Lorsque l'objet est recontruit, le flux de support (fichier) est
**      recr�� sur lequel on viendra ajouter les donn�es.</li>
** </ul>
** <br/>
** <b>La solution propos�e par cette s�rialisation n'est pas utilisable
** pour une s�rialisation � travers un r�seau</b>.
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
** par la r�prise (s�rialisation) il faut mettre le param�tre 'append' �
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
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 output.close();
}

/**
**
*/
@Override
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 output.flush();
}

/**
**
*/
@Override
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
 // R�initialisation des champs non sauvegard�s
 //
 this.open( true );
}

} // class


