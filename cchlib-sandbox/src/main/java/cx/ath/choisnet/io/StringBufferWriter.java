/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/StringBufferWriter.java
** Description   :
** Encodage      : ANSI
**
**  1.00.___ 2005.05.04 Claude CHOISNET
**  2.01.021 2005.10.20 Claude CHOISNET
**                      Ajout de la méthode : setLength(int)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.StringBufferWriter
**
*/
package cx.ath.choisnet.io;

import java.io.Writer;
import java.io.StringWriter;

/**
**
**
** @author Claude CHOISNET
** @since   1.30
** @version 2.01.021
*/
final public class StringBufferWriter
    extends StringWriter
{

/**
**
*/
public StringBufferWriter() // --------------------------------------------
{
 super();
}

/**
**
*/
public StringBufferWriter( int initialSize ) // ---------------------------
{
 super( initialSize );
}

/**
**
** Retourne le contenu du flux et vide le buffer.
*/
public String cleanBuffer() // --------------------------------------------
{
 final StringBuffer sb      = getBuffer();
 final String       result  = sb.toString();

 sb.setLength( 0 );

 return result;
}

/**
**
**
** @return the length of the sequence of characters currently represented
**         by this string buffer.
*/
public int length() // ----------------------------------------------------
{
 return getBuffer().length();
}

/**
**
**
*/
public void setLength( int len ) // ---------------------------------------
{
 getBuffer().setLength( len );
}

} // class
