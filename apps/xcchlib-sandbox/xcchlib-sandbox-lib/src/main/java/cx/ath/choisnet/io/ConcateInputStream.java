/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/ConcateInputStream.java
** Description   :
** Encodage      : ANSI
**
**  1.00.000 2005.03.17 Claude CHOISNET
**  3.01.025 2006.04.18 Claude CHOISNET
**                      Correction d'un bug sur la méthode: available()
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.ConcateInputStream
**
*/
package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
**
** @author Claude CHOISNET
** @since   1.0
** @version 3.01.025
**
** @see InputStreamHelper
*/
public class ConcateInputStream
    extends java.io.InputStream
{

/** Index of current stream */
private int currentStream;

/** Array of streams */
private final InputStream[] inputStreamArray;

/**
**
*/
public ConcateInputStream( // -------------------------------------------
    final InputStream firstInputStream,
    final InputStream secondInputStream
    )
{
 this.currentStream             = 0;
 this.inputStreamArray          = new InputStream[ 2 ];
 this.inputStreamArray[ 0 ]     = firstInputStream;
 this.inputStreamArray[ 1 ]     = secondInputStream;

 check();
}

/**
**
*/
public ConcateInputStream( // -------------------------------------------
    final InputStream   inputStream,
    final String        datas
    )
{
 this( inputStream, new ByteArrayInputStream( datas.getBytes() ) );
}

/**
**
*/
public ConcateInputStream( // -------------------------------------------
    final String        datas,
    final InputStream   inputStream
    )
{
 this( new ByteArrayInputStream( datas.getBytes() ), inputStream );
}

/**
**
**
** @see InputStream#available()
*/
@Override
public int available() // -------------------------------------------------
    throws java.io.IOException
{
 if( this.currentStream < this.inputStreamArray.length ) {
    return this.inputStreamArray[ this.currentStream ].available();
    }
 else {
    return 0;
    }
}

/**
** @see InputStream#close()
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 for( int i = 0; i < this.inputStreamArray.length; i++ ) {
     this.inputStreamArray[ i ].close();
    }
}

/**
** @see InputStream#read()
*/
@Override
public int read() // ------------------------------------------------------
    throws java.io.IOException
{
 int result = -1;

 while( this.currentStream < this.inputStreamArray.length ) {
    result = this.inputStreamArray[ this.currentStream ].read();

    if( result != -1 ) {
        //
        // Retourne la valeur pour le flux courant
        //
        return result;
        }
    //
    // On passe au flux suivant
    //
    this.currentStream++;
    }

 return result;
}

/**
** @see InputStream#markSupported()
*/
@Override
public boolean markSupported() // -----------------------------------------
{
 return false;
}

/**
** @see InputStream#mark(int)
*/
@Override
public void mark( int readlimit ) // --------------------------------------
{
 // empty
}

/**
** @see InputStream#reset()
*/
@Override
public void reset() // ----------------------------------------------------
{
 // empty
}

/**
**
*/
private void check() // ---------------------------------------------------
    throws RuntimeException
{
 if( this.inputStreamArray == null ) {
    throw new RuntimeException( "inputStreamArray is null." );
    }

 final int length = inputStreamArray.length;

// if( length != this.inputStreamArrayLength ) {
//    throw new RuntimeException( "internal error : length != this.inputStreamArrayLength." );
//    }

 for( int i = 0; i<length; i++ ) {
    if( this.inputStreamArray[ i ] == null ) {
        throw new RuntimeException( "one or more InputStream is null." );
        }
    }
}

/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
 final StringBuilder sb = new StringBuilder( "[" );

 sb.append( this.getClass().getName() );
 sb.append( '[' );
 sb.append( this.currentStream );
 sb.append( '/' );
 sb.append( this.inputStreamArray.length );
 sb.append( ']' );
 sb.append( inputStreamArray[0].toString() );

 for( int i = 1; i<this.inputStreamArray.length; i++ ) {
    sb.append( ',' );
    sb.append( inputStreamArray[i].toString() );
    }

 sb.append( ']' );

 return sb.toString();
}

} // class


