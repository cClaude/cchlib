/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/InputStreamHelper.java
** Description   :
** Encodage      : ANSI
**
**  2.01.033 2005.11.18 Claude CHOISNET - Version initial
**                      Code issu de cx.ath.choisnet.io.IOTools
**  3.00.002 2006.02.07 Claude CHOISNET
**                      Correction d'un bug Majeur dans copy(InputStream,OutputStream,int)
**                      qui ne faisait pas de OutputStream.flush() en fin
**                      copie.
**                      Correction d'un bug mineur dans copy(File,File)
**                      la fermeture du flux de destination ne génerait pas
**                      d'exception.
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.InputStreamHelper
**
*/
package cx.ath.choisnet.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
**
** Classe offrant des méthodes autour de {@link InputStream}, en
** particulier la copie de flux (ou de fichier), ainsi que la
** concaténation de flux.
**
**
**
** @author Claude CHOISNET
** @since   2.01.033
** @version 3.00.002
**
** @see ConcateInputStream
** @see InputStream
** @see ReaderHelper
*/
public class InputStreamHelper
{

/**
** Transforme le contenu du flux en chaîne, puis ferme le flux.
**
** @throws java.io.IOException en cas d'erreur
*/
public static String toString( InputStream is ) // ------------------------
    throws java.io.IOException
{
 final StringBuilder    sb      = new StringBuilder();
 final byte[]           buffer  = new byte[ 2048 ];
 int                    len;

 try {
    while( (len = is.read( buffer )) != -1 ) {
        sb.append( new String( buffer, 0, len ) );
        }
    }
 finally {
    is.close();
    }

 return sb.toString();
}

/**
** <p>
** Copie le contenu du flux 'input' vers le flux 'output'.
** </p>
** <br />
** <b>Les flux ne sont pas fermés</b>
** <br />
**
** @throws java.io.IOException en cas d'erreur
*/
public static void copy( // -----------------------------------------------
    InputStream     input,
    OutputStream    output,
    final int       bufferSize
    )
    throws java.io.IOException
{
 final byte[]   buffer  = new byte[ bufferSize ];
 int            len;

 while( (len = input.read( buffer )) != -1 ) {
    output.write( buffer, 0, len );
    }

 output.flush();
}

/**
** <p>
** Copie le contenu du flux 'input' vers le flux 'output'.
** </p>
** <br />
** <b>Les flux ne sont pas fermés</b>
** <br />
**
** @throws java.io.IOException en cas d'erreur
*/
public static void copy( InputStream input, OutputStream output ) // ------
    throws java.io.IOException
{
 copy( input, output, 2048 );
}

/**
** <p>
** Copie le contenu du flux 'input' vers le flux 'output'.
** </p>
** <br />
** <b>Les flux ne sont pas fermés</b>
** <br />
**
** @throws java.io.IOException en cas d'erreur
*/
public static void copy( File inputFile, File outputFile ) // -------------
    throws java.io.IOException
{
 InputStream  input  = new BufferedInputStream( new FileInputStream( inputFile ) );
 OutputStream output = new BufferedOutputStream( new FileOutputStream( outputFile ) );

 try {
    copy( input, output, 4096 );
    }
 finally {
    try {  input.close(); } catch( Exception ignore ) {}

    output.close(); // close avec génération d'exception....
    }
}

/**
**
**
**
*/
public static InputStream concat( // --------------------------------------
    final InputStream is1,
    final InputStream is2
    )
{
 final InputStream[] is = { is1, is2 };

 return concat( is );
}

/**
**
**
**
*/
public static InputStream concat( // --------------------------------------
    final InputStream[] is
    )
{
 return new InputStream()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        int index = 0;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public int available() // - - - - - - - - - - - - - - - - - - - - -
            throws java.io.IOException
        {
            return is[ index ].available();
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public void close() //- - - - - - - - - - - - - - - - - - - - - - -
            throws java.io.IOException
        {
            java.io.IOException anIOE = null;

            for( int i = 0; i < is.length; i++ ) {
                try {
                    is[ i ].close();
                    }
                catch( java.io.IOException e ) {
                    anIOE = e;
                    }
                }

            if( anIOE != null ) {
                throw anIOE;
                }
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public boolean markSupported() { return false; } // - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        public int read() //- - - - - - - - - - - - - - - - - - - - - - - -
            throws java.io.IOException
        {
            for( ; index < is.length; index++ ) {
                int r = is[ index ].read();

                if( r != -1 ) {
                    return r;
                    }
                }

            return -1;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

} // class

