/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ExternalAppException.java
** Description   :
** Encodage      : ANSI
**
**  1.49.023 2004.06.09 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ExternalAppException
**
*/
package cx.ath.choisnet.util;

/**
**
** @author Claude CHOISNET
*/
public class ExternalAppException
    extends java.io.IOException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public ExternalAppException( String msg ) // ------------------------------
{
 super( msg );
}

/**
**
*/
public ExternalAppException( String msg, Throwable cause ) // -------------
{
 super( msg );

 initCause( cause );
}

/**
**
*/
public static String getStackTraceString( final Throwable e ) // ----------
{
 final java.io.StringWriter sw = new java.io.StringWriter();

 e.printStackTrace( new java.io.PrintWriter( sw ) );

 return sw.toString();
}

/**
**
*/
public String getStackTraceString() // ------------------------------------
{
 return getStackTraceString( this );
}


} // class
