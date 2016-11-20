/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/SimpleServletContext.java
** Description   :
** Encodage      : ANSI
**
**  3.02.008 2006.06.09 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.SimpleServletContext
**
*/
package cx.ath.choisnet.servlet;

/**
** <P>Outils permettant d'exploiter plus simplement les informations
**  de la classe {@link javax.servlet.http.HttpServletRequest}</P>
**
** @author Claude CHOISNET
** @since   3.02.008
** @version 3.02.008
**
** @see javax.servlet.ServletContext
** @see SimpleServletRequest
** @see cx.ath.choisnet.servlet.impl.SimpleServletContextImpl
*/
public interface SimpleServletContext
{

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved from
*/
public String getInitParameter( String paramName ) // ---------------------
    throws ServletContextParamNotFoundException;

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved from
*/
public String getInitParameter( // ----------------------------------------
    String paramName,
    String defaultValue
    );

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved
**
** @return a valid {@link ParameterValue} (never null).
public ParameterValue getParameter( String paramName ); // ----------------
*/


} // class
