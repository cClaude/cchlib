/*
** $VER: HttpSessionDebug.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/HttpSessionDebug.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2001.05.10 Claude CHOISNET
**  1.50 2005.05.23 Claude CHOISNET
**  2.01.031 2005.11.21 Claude CHOISNET - DEPRECATED !
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.HttpSessionDebug
**
*/
package cx.ath.choisnet.servlet.debug;


import java.util.Enumeration;
import javax.servlet.http.HttpSession;

/**
** For debugging HttpSession attributes...
**
** @author Claude CHOISNET 2001.05.10
** @version 1.50
** @deprecated NO REMPLACEMENT
*/
@Deprecated
public class HttpSessionDebug
{
/** No constructor */
private HttpSessionDebug() {} // ------------------------------------------

/**
** Build a StringBuffer with all attributes found in the session object.<P>
** For each attribute use the following syntaxe :
** <PRE>
** Attribute( "<B><I>ATTRIBUTE_NAME</I></B>" : <B><I>ATTRIBUTE_CLASS_NAME</I></B> ) = [<B><I>ATTRIBUTE_STRING_VALUE</I></B>]
** </PRE>
**
** @param session a valid HttpSession object
**
** @return a StringBuffer with all session attributes descriptions.
**
** @deprecated NO REMPLACEMENT
*/
@Deprecated
public static StringBuffer buildDisplay( HttpSession session ) // ---------
{
 StringBuffer buffer = new StringBuffer();

 try {
    @SuppressWarnings("unchecked")
    Enumeration<String> enum0 = session.getAttributeNames();

    if( enum0 != null ) {
        while( enum0.hasMoreElements() ) {
            String attributeName      = enum0.nextElement();
            Object attributeValue     = session.getAttribute( attributeName );
            String attributeClassName;

            try {
                attributeClassName = attributeValue.getClass().getName();
                }
            catch( Exception ignore ) {
                attributeClassName = "Not Found";
                }

            buffer.append( "Attribute( \"" );
            buffer.append( attributeName );
            buffer.append( "\" : " );
            buffer.append( attributeClassName );
            buffer.append( " ) = [" );
            buffer.append( attributeValue );
            buffer.append( "]\n" );
            } // while
        }
    else {
        buffer.append( "No attribute !\n" );
        }
    }
 catch( IllegalStateException e ) {
    buffer.append( "Warning:" );
    buffer.append( e.getMessage() );
    buffer.append( "\n" );
    }
 catch( Exception e ) {
    buffer.append( "Fail:" );
    buffer.append( e.getMessage() );
    buffer.append( "\n" );
    }

 return buffer;
}

} // HttpSessionDebug


/**
** for OLD Version of JSDK
*/
// private static Enumeration getSessionAttributeNames( HttpSession session ) // ----
// {
//  String[]         name  = session.getValueNames();
//  java.util.Vector v     = new java.util.Vector( name.length );
//
// return v.elements();
// }

/*
*
** Exemple:<P>
**  HttpSessionDebug.displayHttpSessionAttributes( session, this, Trace.DEBUG_INFO );
**
** @see #buildDisplay
** @see Trace#println( String, Object, int )
*
/
public static void displayHttpSessionAttributes( // ----------------------------
  HttpSession session,
  Object      object,
  int         traceLevel
  )
{
 StringBuffer buffer = new StringBuffer();

 buffer.append( "display HttpSession Attributes\n" );
 buffer.append( "---- BEGIN ----\n" );
 buffer.append( buildDisplay( session ).toString() );
 buffer.append( "---- END ----\n" );

 try {
    Trace.println( buffer.toString(), object, traceLevel );
    }
 catch( Exception ignore ) {
    }
}
*/
