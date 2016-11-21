/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/InputStreamTee.java
** Description   :
** Encodage      : ANSI
**
**  3.02.023 2006.07.05 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.InputStreamTee
**
*/
package cx.ath.choisnet.io;

import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
** Classe permettant d'espionner un flux de type InputStream
**
** @author Claude CHOISNET
** @since   3.02.023
** @version 3.02.023
*/
public class InputStreamTee
    extends InputStream
{
/** */
private final InputStream inputStream;

/** */
private final PipedOutputStream pipeOut;

/** */
private final PipedInputStream pipeIn;

/**
**
*/
public InputStreamTee( final InputStream inputStream ) // -----------------
    throws java.io.IOException
{
 this.inputStream   = inputStream;
 this.pipeOut       = new PipedOutputStream();
 this.pipeIn        = new PipedInputStream( pipeOut );
}

/**
**
*/
@Override
public int available() // -------------------------------------------------
    throws java.io.IOException
{
 return this.inputStream.available();
}

/**
**
*/
@Override
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 this.inputStream.close();
 this.pipeOut.close();
}

/**
**
*/
@Override
public void mark( final int readlimit ) // --------------------------------
{
 this.inputStream.mark( readlimit );
}

/**
**
*/
@Override
public boolean markSupported() // -----------------------------------------
{
 return this.inputStream.markSupported();
}

/**
**
*/
@Override
public int read() // ------------------------------------------------------
    throws java.io.IOException
{
 final int c = this.inputStream.read();

 this.pipeOut.write( c );

 return c;
}

/**
**
*/
@Override
public int read( final byte[] b ) // --------------------------------------
    throws java.io.IOException
{
 return read( b, 0, b.length );
}

/**
**
*/
@Override
public int read( final byte[] b, final int off, final int len ) // --------
    throws java.io.IOException
{
 final int bytesLen = this.inputStream.read( b, off, len );

 if( bytesLen == -1 ) {
    return -1; // EOF
    }
 else {
    this.pipeOut.write( b, off, bytesLen );

    return bytesLen;
    }
}

/**
**
*/
@Override
public void reset() // ----------------------------------------------------
    throws java.io.IOException
{
 this.inputStream.reset();
}

/**
**
*/
@Override
public long skip( final long n ) // ---------------------------------------
    throws java.io.IOException
{
 return this.inputStream.skip( n );
}

/**
**
*/
public InputStream getInputStream() //-------------------------------------
{
 return pipeIn;
}

/**
** .java cx.ath.choisnet.io.InputStreamTee
public final static void main( final String[] args ) //--------------------
    throws
        java.io.FileNotFoundException,
        java.io.IOException
{
 final InputStreamTee inputClone = new InputStreamTee( new java.io.FileInputStream( args[ 0 ] ) );

 System.out.println( "Reading = " + args[ 0 ] );

 int c;

 System.out.println( "inputClone -----------------" );

 while( (c = inputClone.read()) != -1 ) {
    System.out.print( "." );
    }

 System.out.println();

 inputClone.close();

 final InputStream inputTee = inputClone.getInputStream();

 System.out.println( "inputTee -----------------" );

 while( (c = inputTee.read()) != -1 ) {
    System.out.print( "." );
    }

 System.out.println();

// System.err.println(
//    InputStreamHelper.toString( )
//    );
}
*/

} // class
