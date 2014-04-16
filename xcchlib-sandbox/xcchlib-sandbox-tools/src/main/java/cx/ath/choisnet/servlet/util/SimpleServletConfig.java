/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/util/SimpleServletConfig.java
** Description   :
** Encodage      : ANSI
**
**  1.00.2005.03.17 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.util.SimpleServletConfig
**
*/
package cx.ath.choisnet.servlet.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.File;

/**
** <P>Outils permettant d'exploiter plus simplement les informations
**  de la classe javax.servlet.http.HttpServletRequest</P>
**
** @see javax.servlet.http.HttpServletRequest
**
** @author Claude CHOISNET
** @version 1.00
** @since 1.00
*/
public class SimpleServletConfig
{

/** */
private final HttpServlet httpServlet;

/** */
private final ServletContext servletContext;

/**
**
*/
public SimpleServletConfig( // --------------------------------------------
    final HttpServlet httpServlet
    )
{
 this.httpServlet       = httpServlet;
 this.servletContext    = httpServlet.getServletContext();
}

/**
** Recherche le paramétre d'abord dans les paramétres de la servlet, ensuite
** dans le context de l'application courante.
**
*/
public String getRequiredParameter( final String name ) // ----------------
    throws javax.servlet.ServletException
{
 String value = httpServlet.getInitParameter( name );

 if( value == null ) {
    value = servletContext.getInitParameter( name );
    }

 if( value == null ) {
    throw new javax.servlet.ServletException( "parameter '" + name + "' not found" );
    }

 return value;
}

/**
** Recherche le paramétre d'abord dans les paramétres de la servlet, ensuite
** dans le context de l'application courante.
**
*/
public int getRequiredParameterAsInt( final String name ) // --------------
    throws javax.servlet.ServletException
{
 String value = getRequiredParameter( name );

 try {
    return Integer.parseInt( value );
    }
  catch( Exception e) {
    throw new javax.servlet.ServletException(
        "parameter '" + name + "' not valid : '" + value
            + "' - while expecting integer !"
        );
    }
}

/**
** Recherche le paramétre d'abord dans les paramétres de la servlet, ensuite
** dans le context de l'application courante.
*/
public File getRequiredParameterAsFile( final String name ) // ------------
    throws javax.servlet.ServletException
{
 String value = getRequiredParameter( name );

 try {
    return new File( value );
    }
  catch( Exception e) {
    throw new javax.servlet.ServletException(
        "parameter '" + name + "' not valid : '" + value
            + "' - while expecting File !"
        );
    }
}

} // class
