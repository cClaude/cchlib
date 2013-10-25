/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/impl/SimpleServletContextImpl.java
** Description   :
** Encodage      : ANSI
**
**  3.02.008 2006.06.09 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.impl.SimpleServletContextImpl
**
*/
package cx.ath.choisnet.servlet.impl;

import  java.util.Collections;
import  java.util.Enumeration;
import  java.util.HashMap;
import  java.util.Map;
import cx.ath.choisnet.servlet.ServletContextParamNotFoundException;
import cx.ath.choisnet.servlet.SimpleServletContext;
import java.util.Iterator;
import javax.servlet.ServletContext;

/**
** <P>
** Outils permettant d'exploiter plus simplement les informations
** de la classe {@link javax.servlet.http.HttpServletRequest}
** </P>
**
** @author Claude CHOISNET
** @since   3.02.008
** @version 3.02.008
**
** @see javax.servlet.ServletContext
*/
public class SimpleServletContextImpl
    implements SimpleServletContext
{
/**
**
*/
private final ServletContext servletContext;

/**
**
*/
private transient Map<String,String> initParametersMap;

/**
**
*/
public SimpleServletContextImpl( // ---------------------------------------
    final ServletContext servletContext
    )
{
 this.servletContext    = servletContext;
 this.initParametersMap = null;
}

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved from
*/
public String getInitParameter( final String paramName ) // ---------------
    throws ServletContextParamNotFoundException
{
 try {
    return this.servletContext.getInitParameter( paramName ).toString();
    }
 catch( Exception e ) {
    throw new ServletContextParamNotFoundException( paramName );
    }
}

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved from
*/
public String getInitParameter( // ----------------------------------------
    final String paramName,
    final String defaultValue
    )
{
 try {
    return getInitParameter( paramName );
    }
 catch( ServletContextParamNotFoundException e ) {
    return defaultValue;
    }
}


/**
**
*/
public Map<String,String> getInitParameters() // --------------------------
{
 if( this.initParametersMap == null ) {

    synchronized( this ) {
        final Map<String,String> map = new HashMap<String,String>();
        final Enumeration        en  = this.servletContext.getInitParameterNames();

        while( en.hasMoreElements() ) {
            final String name = en.nextElement().toString();

            map.put( name, this.servletContext.getInitParameter( name ) );
            }

        this.initParametersMap = Collections.unmodifiableMap( map );
        }
    }

 return this.initParametersMap;
}

/**
**
*/
public Iterator<String> getInitParameterNames() // ------------------------
{
 return getInitParameters().keySet().iterator();
}

/**
** Return value of the specified parameter.
**
** @param paramName Name of the parameter to retrieved
**
** @return a valid {@link ParameterValue} (never null).
public ParameterValue getParameter( String paramName ); // ----------------
*/


} // class

//import cx.ath.choisnet.util.WrapperHelper;
