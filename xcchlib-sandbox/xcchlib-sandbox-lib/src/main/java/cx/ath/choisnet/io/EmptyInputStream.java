/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/EmptyInputStream.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2004.06.09 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.EmptyInputStream
**
*/
package cx.ath.choisnet.io;

/**
**
** @author Claude CHOISNET
*/
public class EmptyInputStream
    extends java.io.InputStream

{

/**
**
*/
public EmptyInputStream() // ----------------------------------------------
{
}

/**
** @see java.io.InputStream#read()
*/
@Override
public int read() // ------------------------------------------------------
    throws java.io.IOException
{
 return -1;
}

} // class
