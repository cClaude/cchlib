/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/ServletContextParamNotFoundException.java
** Description   :
** Encodage      : ANSI
**
**  3.02.008 2006.06.09 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.ServletContextParamNotFoundException
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
public class ServletContextParamNotFoundException
    extends SimpleServletContextException
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public ServletContextParamNotFoundException( // ---------------------------
    final String message,
    final Throwable cause
    )
{
 super( message, cause );
}

/**
**
*/
public ServletContextParamNotFoundException( // ---------------------------
    final String message
    )
{
 super( message );
}

/**
**
*/
public ServletContextParamNotFoundException( // ---------------------------
    final Throwable cause
    )
{
 super( cause );
}


} // class
