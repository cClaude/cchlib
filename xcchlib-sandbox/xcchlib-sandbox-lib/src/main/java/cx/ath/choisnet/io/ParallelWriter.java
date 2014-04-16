/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/ParallelWriter.java
** Description   :
** Encodage      : ANSI
**
**  1.01.___ 2005.05.04 Claude CHOISNET
**  3.00.003 2006.02.14 Claude CHOISNET
**                      Suppression du constructeur avec 3 param�tres
**                      Writer.
**                      Reprise du constructeur ParallelWriter(...)
**                      afin d'�tre compatible avec ce qu'�tait le
**                      constructeur � 3 param�tres (cr�ation d'un tableau
**                      local).
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.ParallelWriter
**
*/
package cx.ath.choisnet.io;

import java.io.Writer;

/**
**
** @author Claude CHOISNET
** @version 3.00.003
*/
final public class ParallelWriter
    extends Writer
{
/** */
private Writer[] writers;

/**
**
*/
public ParallelWriter( // -------------------------------------------------
    final Writer writer1,
    final Writer writer2
    )
{
 this.writers       = new Writer[ 2 ];

 this.writers[ 0 ]  = writer1;
 this.writers[ 1 ]  = writer2;
}

/**
**
*/
public ParallelWriter( // -------------------------------------------------
    Writer ... writerList
    )
{
 this.writers = new Writer[ writerList.length ];

 int i = 0;

 for( Writer w : writerList ) {
    this.writers[ i++ ] = w;
    }
}

/**
** @see Writer#close()
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 java.io.IOException lastException = null;

 for( int i = 0; i < writers.length; i++ ) {
    try {
        this.writers[ i ].close();
        }
    catch( java.io.IOException e ) {
        //
        // Sauvegarde la derni�re exception
        //
        lastException = e;
        }
    }

 if( lastException != null ) {
    throw lastException;
    }
}

/**
** @see Writer#flush()
*/
@Override
public void flush() // ----------------------------------------------------
    throws java.io.IOException
{
 for( int i = 0; i < writers.length; i++ ) {
    this.writers[ i ].flush();
    }
}

/**
** @see Writer#write(char[] cbuf, int off, int len)
*/
@Override
public void write( char[] cbuf, int off, int len ) // ---------------------
    throws java.io.IOException
{
 for( int i = 0; i < writers.length; i++ ) {
    this.writers[ i ].write( cbuf, off, len );
    }
}

} // class


/**
** @deprecated use OutputStreamWriter( stream, encoding )
@Deprecated
public ParallelWriter( // -------------------------------------------------
    Writer          writer,
    OutputStream    stream
    )
{
 this( writer, new OutputStreamWriter( stream ) );
}
*/

/**
**
public ParallelWriter( // -------------------------------------------------
    Writer  writer1,
    Writer  writer2,
    Writer  writer3
    )
{
 this.writers       = new Writer[ 3 ];

 this.writers[ 0 ]  = writer1;
 this.writers[ 1 ]  = writer2;
 this.writers[ 2 ]  = writer3;
}
*/
