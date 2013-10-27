/*
** $VER: InfosServlet.java
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/servlet/debug/InfosServlet.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2000.09.26 Claude CHOISNET
**  1.10 2005.03.09 Claude CHOISNET
**  1.50 2005.05.23 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.servlet.debug.InfosServlet
**
*/
package cx.ath.choisnet.servlet.debug;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
** This servlet shows how a servlet is initialized,
** how it can retrieve info from the request and servlet context
** and how it is destroyed.
**
** <xmp>
**
**      <servlet>
**          <servlet-name>cx.ath.choisnet.servlet.debug.InfosServlet</servlet-name>
**          <description>Display all servlet information</description>
**          <servlet-class>cx.ath.choisnet.servlet.debug.InfosServlet</servlet-class>
**
**          <init-param>
**              <param-name>PARAM_NAME1</param-name>
**              <param-value>value1</param-value>
**          </init-param>
**
**          <init-param>
**              <param-name>PARAM_NAME2</param-name>
**              <param-value>value2</param-value>
**          </init-param>
**
**      </servlet>
**
**      ...
**
**      <servlet-mapping>
**          <servlet-name>cx.ath.choisnet.servlet.debug.InfosServlet</servlet-name>
**          <url-pattern>/InfosServlet</url-pattern>
**      </servlet-mapping>
**
** </xmp>
**
** @author Claude CHOISNET
** @version 1.50
*/
public class InfosServlet
    extends HttpServlet
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
protected final static String SERVLETNAME = "InfosServlet";

/**
** This method is called when the servlet is initialized.
** It is used to initialize any resources which are needed by the servlet.
*/
@Override
public void init() throws ServletException // -----------------------------
{
 log( SERVLETNAME + " init() method called." );

 System.out.println( "print on System.out from " + this );
 System.err.println( "print on System.err from " + this );

 // Retrieve and display the init arguments for this servlet
 Enumeration names = getServletConfig().getInitParameterNames();

if( names != null ) {
    while ( names.hasMoreElements() ) {
        String name  = (String)names.nextElement();
        String value = getServletConfig().getInitParameter( name );

        log( SERVLETNAME + " init argument: [" + name + "] = [" + value + "]" );
        }
    }
 else {
    log( SERVLETNAME + " has no init arguments" );
    }
}

/**
**
*/
@Override
public void service( // ---------------------------------------------------
    HttpServletRequest  request,
    HttpServletResponse response
    )
    throws ServletException, IOException
{
 InfosServletDisplayer infos
    = new cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayerImpl( this, request, response );

 response.setContentType( "text/html" );

 PrintWriter out = response.getWriter();

 infos.appendHTML( out );
}


/**
**
*/
@Override
public String getServletInfo() // -----------------------------------------
{
 return SERVLETNAME + " returns info from the request and servlet context.";
}

/**
** This method is called when the servlet is destroyed.
*/
@Override
public void destroy() // --------------------------------------------------
{
 super.destroy();

 log( SERVLETNAME + " destroy() method called." );
}

} // class


