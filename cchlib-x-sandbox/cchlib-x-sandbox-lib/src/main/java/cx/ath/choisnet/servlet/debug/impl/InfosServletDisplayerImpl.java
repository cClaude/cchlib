/*
** $VER: InfosServletDisplayer.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/impl/InfosServletDisplayerImpl.java
** Description   :
** Encodage      : ANSI
**
**  2.01.032 2005.11.21 Claude CHOISNET - Version initiale
**                      inspir�e de :
**                          cx.ath.choisnet.servlet.debug.DisplayServletInfos
**                      et bas� sur :
**                          cx.ath.choisnet.lang.reflect.MappableHelper
**  2.02.007 2005.12.05 Claude CHOISNET
**                      Ajout de l'affichage du contenu de l'objet HttpSession
**  2.02.042 2006.01.09 Claude CHOISNET
**                      Ajout d'un r�sum� dans l'affichage.
**  3.03.002 29.05.2007 Claude CHOISNET
**                      Traitement de l'affichage des informations de la
**                      JVM (runtime) � l'aide de r�flexion. Permet de
**                      d'appeller des m�thodes pas n�cessairement disponible
**                      sur toutes les versions de JVM.
**  3.03.003 05.06.2007 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayerImpl
**
*/
package cx.ath.choisnet.servlet.debug.impl;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Enumeration;
import cx.ath.choisnet.util.EnumerationBuilder;
import cx.ath.choisnet.servlet.debug.InfosServletDisplay;
import cx.ath.choisnet.servlet.debug.InfosServletDisplay.Anchor;
import cx.ath.choisnet.lang.reflect.MappableHelperFactory;
import cx.ath.choisnet.lang.reflect.MappableHelper;

/**
**
** This class shows how a servlet is initialized,
** how it can retrieve info from the request and servlet context
** and how it is destroyed.
**
**
** @author Claude CHOISNET
** @since   2.01.032
** @version 3.03.003
*/
public class InfosServletDisplayerImpl
    implements cx.ath.choisnet.servlet.debug.InfosServletDisplayer
{
protected HttpServlet           servlet;
protected HttpServletRequest    request;
protected HttpServletResponse   response;
protected HttpSession           httpSession;
protected ServletContext        servletContext;

/**
**
*/
public InfosServletDisplayerImpl( // --------------------------------------
    HttpServlet         servlet,
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws java.io.IOException
{
 this( servlet, request, response, request.getSession( false ) );
}

/**
**
*/
public InfosServletDisplayerImpl( // --------------------------------------
    HttpServlet         servlet,
    HttpServletRequest  request,
    HttpServletResponse response,
    HttpSession         httpSession
    )
    throws java.io.IOException
{
 this.servlet           = servlet;
 this.request           = request;
 this.response          = response;
 this.httpSession       = httpSession;
 this.servletContext    = servlet.getServletContext();
}

/**
**
*/
@Override
public void appendHTML( Appendable out ) // -------------------------------
{
 try {
    out.append( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" );
    out.append( "\t\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" );

    out.append( "<html>\n<head><title>ServletInfos Output</title></head>\n" );
    out.append( "<body>\n<center>\n" );

    out.append( "<h1>Summary</h1>\n" );

    out.append( "<table class=\"summary\">\n" );

    out.append( "<tr><td>getServerInfo()</td><td>");
    out.append( this.servletContext.getServerInfo() );
    out.append( "</td></tr>\n" );

    out.append( "<tr><td>java.runtime.version</td><td>");
    out.append( System.getProperties().getProperty( "java.runtime.version" ) );
    out.append( "</td></tr>\n" );

    out.append( "</table\n" );

    out.append( "<h1>ServletInfos Output</h1>\n" );

    final List<InfosServletDisplay> displayer
        = new LinkedList<InfosServletDisplay>();

    displayer.add( getHttpServlet() );
    displayer.add( getHttpServletRequest() );
    displayer.add( getRequest_getHeaderNames() );
    displayer.add( getRequest_getParameterNames() );
    displayer.add( getRequest_getAttributeNames() );
    displayer.add( getCookies() );
    displayer.add( getConfig() );
    displayer.add( getConfig_getInitParameterNames() );
    displayer.add( getContext() );
    displayer.add( getContext_getAttributeNames() );
    displayer.add( getContext_getInitParameterNames() );
    displayer.add( getHttpSession() );
    displayer.add( getHttpSession_getAttributeNames() );

    //
    // display Virtual Machine Infos
    //
    displayer.add( getJVM_Memory() );
    displayer.add( getJVM_SystemObject() );
    displayer.add( getJVM_SystemProperties() );

    out.append( "<table class=\"menu\">\n" );

    for( InfosServletDisplay display : displayer ) {
        Anchor anchor = display.getAnchor();

        out.append( "<tr><td><a href=\"#" + anchor.getHTMLName() + "\">" + anchor.getDisplay() + "</a></td></tr>\n" );
        }

    out.append( "</table><br />\n" );

    for( InfosServletDisplay display : displayer ) {
        display.appendHTML( out );
        out.append( "\n" );
        }

    out.append( "</center>\n</body>\n</html>\n" );
    }
 catch( java.io.IOException hideException ) {
    throw new RuntimeException( hideException );
    }
}

/**
** Display informations from the HttpServletRequest
*/
private InfosServletDisplay getHttpServlet() // ---------------------------
{
 return new InfosServletDisplayImpl2<HttpServlet>(
                "Here is the javax.servlet.http.HttpServlet object<br />" + getObjectInfo( servlet ),
                "HttpServlet",
                servlet
                ).put(
                    "servlet.getClass().getName()", servlet.getClass().getName()
                );
// values.add(
//      "servlet.getClass().getSuperclass()",
//      servlet.getClass().getSuperclass().getName()
//      );
}

/**
** Display informations from the HttpServletRequest
*/
private InfosServletDisplay getHttpServletRequest() // --------------------
{
 return new InfosServletDisplayImpl2<HttpServletRequest>(
                "Here is the HttpServletRequest object<br />" + getObjectInfo( request ),
                "HttpServletRequest",
                request
                );
}

/**
** Display the HttpServletRequest header parameters
*/
private InfosServletDisplay getRequest_getHeaderNames() // ----------------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 final StringBuilder        value   = new StringBuilder();
 Enumeration<String>        enum0   = EnumerationBuilder.toEnumerationString(
                                            request.getHeaderNames()
                                            );

 while( enum0.hasMoreElements() ) {
    String         name    = enum0.nextElement();
    Enumeration<?> enum2   = request.getHeaders( name );

    value.setLength( 0 );

    while( enum2.hasMoreElements() ) {
        value.append( enum2.nextElement() + "<br/>" );
        }

    map.put( name, value.toString() );
    }

 return new InfosServletDisplayImpl(
        "Here are the request header parameters<br/>request.getHeaderNames()",
        "HttpServletRequest.getHeaderNames()", // LINK
        map,
        "There are no request header parameters."
        );
}

/**
** Display the HTTP HttpServletRequest parameters
*/
private InfosServletDisplay getRequest_getParameterNames() // -------------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 final StringBuilder        value   = new StringBuilder();
 Enumeration<String>        enum0   = EnumerationBuilder.toEnumerationString(
                                            request.getParameterNames()
                                            );

 while( enum0.hasMoreElements() ) {
    String      name    = enum0.nextElement();
    String[]    values  = request.getParameterValues( name );

    value.setLength( 0 );

    for( String v : values ) {
        value.append( v + "<br/>" );
        }

    map.put( name, value.toString() );
    }

 return new InfosServletDisplayImpl(
        "Here are the request url/form parameters<br />request.getParameterNames()",
        "request.getParameterNames()",
        map,
        "There are no request url/form parameters."
        );
}

/**
** Display the HTTP HttpServletRequest attributes
*/
private InfosServletDisplay getRequest_getAttributeNames() // -------------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 Enumeration<String>        enum0   = EnumerationBuilder.toEnumerationString(
                                            request.getAttributeNames()
                                            );

 while( enum0.hasMoreElements() ) {
    String  name    = enum0.nextElement();
    Object  value   = request.getAttribute( name );

    map.put( name, toString( value ) );
    }

 return new InfosServletDisplayImpl(
        "Here are the request attributes<br />request.getAttributeNames()",
        "request.getAttributeNames()",
        map,
        "There are no request attributes."
        );
}

/**
** Display the Cookies
** <P>
** TomCat 4.0 return an null object when there is cookie define.
** </P>
*/
private InfosServletDisplay getCookies() // --------------------------------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 final Enumeration<Cookie>  cookies = EnumerationBuilder.toEnumeration( request.getCookies() );

 while( cookies.hasMoreElements() ) {
    final Cookie cookie = cookies.nextElement();

    map.put( cookie.getName(), cookie.getValue() );
    }

 return new InfosServletDisplayImpl(
        "Here is the Cookies information",
        "request.getCookies()",
        map,
        "NO cookies !"
        );
}

/**
** Display the ServletConfig methods
**
** @since 3.03.003
*/
private InfosServletDisplay getConfig() // --------------------------------
{
 final ServletConfig servletConfig = servlet.getServletConfig();

 return new InfosServletDisplayImpl2<ServletConfig>(
        "Here is information from the ServletConfig<br />" + getObjectInfo( servletConfig ),
        "ServletConfig",
        servletConfig
        );
}

/**
** Display the ServletConfig init attributes
*/
private InfosServletDisplay getConfig_getInitParameterNames() // ----------
{
 final ServletConfig        servletConfig   = servlet.getServletConfig();
 final Map<String,String>   map             = new TreeMap<String,String>();
 Enumeration<String>        enum0           = EnumerationBuilder.toEnumerationString(
                                                servletConfig.getInitParameterNames()
                                                );

 while( enum0.hasMoreElements() ) {
    String name = enum0.nextElement();

    map.put( name, servletConfig.getInitParameter( name ) );
    }

 return new InfosServletDisplayImpl(
        "Here are the ServletConfig init attributes<br />(servlet name  = '"
            + servletConfig.getServletName()
            + "')<br/>servletConfig.getInitParameterNames()",
        "servletConfig.getInitParameterNames()",
        map,
        "There are no Servlet Config attributes."
        );
}

/**
** Display the ServletContext object
*/
private InfosServletDisplay getContext() // -------------------------------
{
 final StringBuilder valueOfgetResource  = new StringBuilder();

 try {
    valueOfgetResource.append( servletContext.getResource( "/" ) );
    }
  catch( java.net.MalformedURLException e ) {
    valueOfgetResource.append( e );
    }

 return new InfosServletDisplayImpl2<ServletContext>(
        "Here is information from the ServletContext<br />" + getObjectInfo( servletContext ),
        "ServletContext",
        servletContext
        ).put(
            "getContext( \"/\" )", toString( servletContext.getContext( "/" ) )
        ).put(
            "getResource( \"/\" )", valueOfgetResource.toString()
        ).put(
            "getRealPath( \"/\" )", servletContext.getRealPath( "/" )
        ).put(
            "getRealPath( \"/WEB-INF\" )", servletContext.getRealPath( "/WEB-INF" )
        ).put(
            "getRequestDispatcher( \"/\" )", toString( servletContext.getRequestDispatcher( "/" ) )
        ).put(
            "getMimeType( \"file.hqx\" )", servletContext.getMimeType( "file.hqx" )
        ).put(
            "getMimeType( \"toto.png\" )", servletContext.getMimeType( "toto.png" )
        );
}

/**
** Display the ServletContext attributes
*/
private InfosServletDisplay getContext_getAttributeNames() // -------------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 Enumeration<String>        enum0   = EnumerationBuilder.toEnumerationString(
                                                servletContext.getAttributeNames()
                                                );

 while( enum0.hasMoreElements() ) {
    String name = enum0.nextElement();

    map.put( name, toString( servletContext.getAttribute( name ) ) );
    }

 return new InfosServletDisplayImpl(
        "Here are attributes from the ServletContext: servletContext.getAttributeNames()",
        "servletContext.getAttributeNames()",
        map,
        "There is no attributes on the ServletContext"
        );
}

/**
** Display the ServletContext attributes
*/
private InfosServletDisplay getContext_getInitParameterNames() // ---------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 Enumeration<String>        enum0   = EnumerationBuilder.toEnumerationString(
                                                servletContext.getInitParameterNames()
                                                );

 while( enum0.hasMoreElements() ) {
    String name = enum0.nextElement();

    map.put( name, servletContext.getInitParameter( name ) );
    }

 return new InfosServletDisplayImpl(
        "Here are  init attributs from the ServletContext<br/>servletContext.getInitParameterNames()",
        "servletContext.getInitParameterNames()",
        map,
        "There is no init attributs from the ServletContext"
        );
}

/**
** Display the HttpSession object
*/
private InfosServletDisplay getHttpSession() // ---------------------------
{
 return new InfosServletDisplayImpl2<HttpSession>(
        "Here is information from the HttpSession<br />" + getObjectInfo( httpSession ),
        "HttpSession",
        httpSession
        );
}

/**
** Display the HttpSession attributes
*/
private InfosServletDisplay getHttpSession_getAttributeNames() // ---------
{
 final Map<String,String>   map     = new TreeMap<String,String>();
 StringBuilder              title   = new StringBuilder( "Here are the HttpSession : " );
 Enumeration<String>        enum0;
 String                     noDataMsg;

 if( httpSession == null ) {
    title.append( "NOT FOUND" );

    enum0       = EnumerationBuilder.toEnumeration();
    noDataMsg   = "There are no HttpSession !";
    }
 else {
    title.append( "ID=" + httpSession.getId() );

    enum0       = EnumerationBuilder.toEnumerationString( httpSession.getAttributeNames() );
    noDataMsg   = "There are no object on the current HttpSession.";
    }


 while( enum0.hasMoreElements() ) {
    String name = enum0.nextElement();

    map.put( name, toString( httpSession.getAttribute( name ) ) );
    }

 return new InfosServletDisplayImpl(
        title.toString(),
        "HttpSession.getAttributeNames()",
        map,
        noDataMsg
        );
}

/**
** Display the memory informations, with an extra message...
*/
private InfosServletDisplay getJVM_Memory() // ----------------------------
{

 MappableHelper mapHelper = new MappableHelperFactory().setMethodesNamePattern(
                                    "(freeMemory|totalMemory|maxMemory|availableProcessors)"
            ).addClasses(
                Object.class
                ).addAttributes(
                    MappableHelper.Attributes.ALL_PRIMITIVE_TYPE
                    ).getInstance();

 Runtime                    thisRuntime = Runtime.getRuntime();
 long                       freeMemory  = thisRuntime.freeMemory();
 long                       totalMemory = thisRuntime.totalMemory();
 final Map<String,String>   map         = mapHelper.toMap( thisRuntime );

/*
 final Map<String,String> map = new TreeMap<String,String>();

 map.put( "freeMemory()"                    , Long.toString( freeMemory   ) );
 map.put( "totalMemory()"                   , Long.toString( totalMemory  ) );
*/

 map.put( "totalMemory() - freeMemory()"    , Long.toString( totalMemory - freeMemory ) );

 return new InfosServletDisplayImpl(
        "JVM memory informations (Runtime)",
        "JVM_Memory",
        map
        );
}

/**
** Display the memory informations
*/
private InfosServletDisplay getJVM_SystemProperties() // ------------------
{
 final java.util.Properties prop    =  System.getProperties();
 final Map<String,String>   map     = new TreeMap<String,String>();
 Enumeration<String>        enum0   = EnumerationBuilder.toEnumerationString(
                                                prop.propertyNames()
                                                );

 while( enum0.hasMoreElements() ) {
    String name = enum0.nextElement();

    map.put( name, prop.getProperty( name ) );
    }

 return new InfosServletDisplayImpl(
        "Here is the JVM System.getProperties() informations : ",
        "System.getProperties()",
        map,
        "There is no init attributs from the ServletContext"
        );
}

/**
**
*/
private InfosServletDisplay getJVM_SystemObject() // ----------------------
{
 final Map<String,String> map = new TreeMap<String,String>();

 map.put( "System.out", toString( System.out ) );
 map.put( "System.err", toString( System.err ) );
 map.put( "System.in", toString( System.in ) );

 return new InfosServletDisplayImpl(
                "Here is the JVM System Object informations",
                "System",
                map
                );
}

/**
**
*/
private final String toString( final Object o ) // ------------------------
{
// return cx.ath.choisnet.lang.reflect.MappableHelper.toString(
//            cx.ath.choisnet.lang.reflect.MappableHelper.SHOW_ALL,
//            o
//            );
 if( o != null ) {
    return o.toString();
    }

 return "Object is null";
}

/**
**
*/
private final static String getObjectInfo( final Object o ) // ------------
{
 if( o != null ) {
    return "ClassName:" + o.getClass().getName() + "<br/>" + o.toString();
    }
 else {
    return "NULL";
    }
}

} // class

