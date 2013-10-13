/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/SimpleServletRequest.java
** Description   :
** Encodage      : ANSI
**
**  2.01.030 2005.11.10 Claude CHOISNET
**  3.01.018 2006.04.11 Claude CHOISNET
**                      La méthode getUserAgent() est deprecated,
**                      Ajout de getUserAgentDetails()
**  3.01.033 2006.04.11 Claude CHOISNET
**                      Ajout de getCookie(String)
**  3.02.036 2006.08.04 Claude CHOISNET
**                      Suppression de: getUserAgentDetails()
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.SimpleServletRequest
**
*/
package cx.ath.choisnet.servlet;

import java.util.EnumSet;
import javax.servlet.http.Cookie;

/**
** <p>
**  Outils permettant d'exploiter plus simplement les informations
**  de la classe {@link javax.servlet.http.HttpServletRequest}
** </p>
**
** @author Claude CHOISNET
** @since   2.01.030
** @version 3.02.036
**
** @see javax.servlet.http.HttpServletRequest
** @see cx.ath.choisnet.servlet.impl.SimpleServletRequestImpl
*/
public interface SimpleServletRequest
{

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved
**
** @return a valid {@link ParameterValue} (never null).
*/
public ParameterValue getParameter( String paramName ); // ----------------

/**
** Return informations about client browser and operating system.
**
** @return an {@link EnumSet} of {@link UserAgent}.
*/
public EnumSet<UserAgent> getUserAgentDetails(); // -----------------------

/**
** Return value of the specified cookie.
**
** @param cookieName Name of the cookie to retrieved
**
** @return a {@link Cookie} or null if the cookie does not exist.
**
** @since 3.01.033
*/
public Cookie getCookie( String cookieName ); // --------------------------

} // class

/**
** @see #getUserAgentDetails()
** @deprecated use getUserAgentDetails()
@Deprecated
public UserAgent getUserAgent(); // ---------------------------------------
*/

