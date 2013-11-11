/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/SerializableFileWriter.java
** Description   :
** Encodage      : ANSI
**
**  2.01.010 2005.10.07 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.SerializableFileWriter
**
*/
package cx.ath.choisnet.io;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
** <p>Objet �quivalent � la classe {@link java.io.FileWriter}, et impl�mentant
** l'interface {@link java.io.Serializable}.</p>
** <br />
** <p>Le principe de la "s�rialisation" de cet objet s'appuit sur la
** classe {@link SerializableFileOutputStream}</p>
** <br />
**
** @author Claude CHOISNET
** @version 2.01.010
** @since   2.01.010
**
** @see java.io.FileWriter
** @see java.io.Serializable
** @see SerializableFileOutputStream
*/
public class SerializableFileWriter
    extends Writer
        implements
            java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private SerializableFileOutputStream serOutput;

/** */
private String encoding;

/** */
private transient Writer output;

/**
** <p>Constructs a SerializableFileWriter object given a File object.</>
**
** @see java.nio.charset.Charset
** @see javax.servlet.ServletResponse#getCharacterEncoding()
*/
public SerializableFileWriter( // -----------------------------------------
    File    file,
    String  encoding
    )
    throws
        java.io.FileNotFoundException,
        java.io.UnsupportedEncodingException,
        IOException
{
 this( file, encoding, false );
}

/**
** <p>Constructs a SerializableFileWriter object given a File object.</>
**
** @see java.nio.charset.Charset
** @see javax.servlet.ServletResponse#getCharacterEncoding()
*/
public SerializableFileWriter( // -----------------------------------------
    File    file,
    String  encoding,
    boolean append
    )
    throws
        java.io.FileNotFoundException,
        java.io.UnsupportedEncodingException,
        IOException
{
 this.serOutput = new SerializableFileOutputStream( file, append );
 this.encoding  = encoding;

 this.open();
}

/**
** par la r�prise (s�rialisation) il faut mettre le param�tre 'append' �
** la valeur 'true'
*/
private void open() // ----------------------------------------------------
{
 this.output = new java.io.OutputStreamWriter( this.serOutput );
}

/**
**
*/
@Override
public void close() // ----------------------------------------------------
    throws IOException
{
 output.close();
}

/**
**
*/
@Override
public void flush() // ----------------------------------------------------
    throws IOException
{
 output.flush();
}

/**
**
*/
@Override
public void write( char[] array, int offset, int len ) // -----------------
    throws IOException
{
 output.write( array, offset, len );
}

/**
** java.io.Serializable
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
    throws IOException
{
 output.flush();
 output.close();

 stream.defaultWriteObject();
}

/**
** java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
    throws IOException, ClassNotFoundException
{
 stream.defaultReadObject();

 //
 // R�initialisation des champs non sauvegard�s
 //
 this.open();
}

} // class


