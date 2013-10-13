/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/EmptyReader.java
** Description   :
** Encodage      : ANSI
**
**  1.00.001 2004.06.09 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.EmptyReader
**
*/
package cx.ath.choisnet.io;

/**
** <P>
** Construction d'un {@link java.io.Reader} vide.  (cas limite)
** </P>
**
** @author Claude CHOISNET
*/
public class EmptyReader
    extends java.io.Reader
{

/**
**
*/
public EmptyReader() // ---------------------------------------------------
{
 // Empty
}

/**
** @see java.io.Reader#close()
*/
public void close() // ----------------------------------------------------
    throws java.io.IOException
{
 // Empty
}

/**
** @see java.io.Reader#read(char[],int,int)
*/
public int read( char[] cbuf, int off, int len ) // -----------------------
    throws java.io.IOException
{
 return -1;
}

} // class
