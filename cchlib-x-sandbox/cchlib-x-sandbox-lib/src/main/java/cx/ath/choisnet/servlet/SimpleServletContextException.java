/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/SimpleServletContextException.java
** Description   :
** Encodage      : ANSI
**
**  3.02.008 2006.06.09 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.SimpleServletContextException
**
*/
package cx.ath.choisnet.servlet;

/**
** <P>Exception déclanchées par la classe {@link SimpleServletContext}</P>
**
** @author Claude CHOISNET
** @since   3.02.008
** @version 3.02.008
*/
public class SimpleServletContextException
    extends javax.servlet.ServletException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public SimpleServletContextException( // ----------------------------------
    final String message,
    final Throwable cause
    )
{
 super( message, cause );
}

/**
**
*/
public SimpleServletContextException( // ----------------------------------
    final String message
    )
{
 super( message );
}

/**
**
*/
public SimpleServletContextException( // ----------------------------------
    final Throwable cause
    )
{
 super( cause );
}


} // class
