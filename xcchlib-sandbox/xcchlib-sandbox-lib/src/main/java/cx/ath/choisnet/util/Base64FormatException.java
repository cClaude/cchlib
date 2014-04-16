// Base64FormatException.java
// $Id: Base64FormatException.java,v 1.4 2000/08/16 21:37:48 ylafon Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
//
// package org.w3c.tools.codec ;
//
package cx.ath.choisnet.util;

/**
** Exception for invalid BASE64 streams.
*/
public class Base64FormatException
    extends java.io.IOException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
** Create that kind of exception
**
** @param msg The associated error message
*/
public Base64FormatException( String msg ) // -----------------------------
{
 super( msg ) ;
}

/**
** Create that kind of exception
**
** @param msg   The associated error message
** @param cause Exception who generate this exception.
*/
public Base64FormatException( String msg, Throwable cause ) // ------------
{
 super( msg );

 initCause( cause ) ;
}

}