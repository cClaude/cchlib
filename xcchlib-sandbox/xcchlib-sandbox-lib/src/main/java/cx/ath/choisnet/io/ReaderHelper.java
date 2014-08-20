/*
** ------------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/ReaderHelper.java
** Description   :
** Encodage      : ANSI
**
**  2.01.033 2005.11.18 Claude CHOISNET - Version initial
**                      Code issu de cx.ath.choisnet.io.IOTools
** ------------------------------------------------------------------------
**
** cx.ath.choisnet.io.ReaderHelper
**
*/
package cx.ath.choisnet.io;

import java.io.Reader;
import java.io.Writer;

/**
**
**
**
**
** @author Claude CHOISNET
** @since   2.01.033
** @version 2.01.033
**
** @see InputStreamHelper
*/
public class ReaderHelper
{

/**
** Transforme le contenu du flux en chaene, puis ferme le flux.
**
** @throws java.io.IOException en cas d'erreur
*/
public static String toString( Reader input ) // ---------------------------
    throws java.io.IOException
{
 final StringBuilder    sb      = new StringBuilder();
 final char[]           buffer  = new char[ 2048 ];
 int                    len;

 try {
    while( (len = input.read( buffer )) != -1 ) {
        sb.append( buffer, 0, len );
        }
    }
 finally {
    input.close();
    }

 return sb.toString();
}

/**
** <p>
** Copie le contenu du flux 'input' vers le flux 'output'.
** </p>
** <br />
** <b>Les flux ne sont pas fermes</b>
** <br />
**
** @throws java.io.IOException en cas d'erreur
*/
public static void copy( // -----------------------------------------------
    Reader      input,
    Writer      output,
    final int   bufferSize
    )
    throws java.io.IOException
{
 final char[]   buffer  = new char[ bufferSize ];
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
** <b>Les flux ne sont pas fermes</b>
** <br />
**
** @throws java.io.IOException en cas d'erreur
*/
public static void copy( Reader input, Writer output ) // -----------------
    throws java.io.IOException
{
 copy( input, output, 2048 );
}

/**
**
**
*/
public static Reader concat( // -------------------------------------------
    final Reader reader1,
    final Reader reader2
    )
{
 final Reader[] readers = { reader1, reader2 };

 return concat( readers );
}

/**
**
**
*/
public static Reader concat( // -------------------------------------------
    final Reader[] readers
    )
{
 return new Reader()
    {
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        int index = 0;
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public void close() //- - - - - - - - - - - - - - - - - - - - - - -
            throws java.io.IOException
        {
            java.io.IOException anIOE = null;

            for( int i = 0; i < readers.length; i++ ) {
                try {
                    readers[ i ].close();
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
        @Override
        public boolean markSupported() { return false; } // - - - - - - - -
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public int read() //- - - - - - - - - - - - - - - - - - - - - - - -
            throws java.io.IOException
        {
            for( ; index < readers.length; index++ ) {
                int r = readers[ index ].read();

                if( r != -1 ) {
                    return r;
                    }
                }

            return -1;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        @Override
        public int read( char[] cbuf, int off, int len ) // - - - - - - - -
            throws java.io.IOException
        {
            for( ; index < readers.length; index++ ) {
                int rlen = readers[ index ].read( cbuf, off, len );

                if( rlen != -1 ) {
                    return rlen ;
                    }
                }

            return -1;
        }
        //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    };
}

} // class

