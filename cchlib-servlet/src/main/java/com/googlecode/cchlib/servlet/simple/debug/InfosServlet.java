package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import com.googlecode.cchlib.servlet.simple.debug.impl.InfosServletDisplayerImpl;

/**
 * Display informations from {@link HttpServlet} to {@link HttpServlet#log(String)},
 * {@link System#out}, {@link System#err}, {@link HttpServletResponse#getWriter()}.
 * <BR>
 * <p>
 *  Configuration:
 * </p>
 * <pre>
 * </pre>
 *
 */
public class InfosServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    // TODO: add (re)loaded time - System.getMillisec() - loaded time of servlet

    /**
     * Servlet name : "{@value}"
     * @serial
     */
    protected static final String SERVLETNAME = "InfosServlet";

    @Override
    public void init()
        throws javax.servlet.ServletException
    {
        log("InfosServlet init() method called.");

        System.out.print( "print on System.out from " );
        System.out.println( this );

        System.err.print( "print on System.err from " );
        System.out.println( this );

        final Enumeration<?> names = getServletConfig().getInitParameterNames();

        if( names != null ) {
            String name;
            String value;

            while( names.hasMoreElements() ) {
                name  = (String)names.nextElement();
                value = getServletConfig().getInitParameter(name);

                log( "InfosServlet init argument: [" + name + "] = [" + value + "]" );
            }

        }
        else {
            log("InfosServlet has no init arguments");
        }

    }

    @Override
    public void service(
            final HttpServletRequest request,
            final HttpServletResponse response
            )
        throws ServletException, IOException
    {
        response.setContentType("text/html");

       appendHTML(
           response.getWriter(),
           this,
           request,
           response
           );
   }

    @Override
    public String getServletInfo()
    {
        return "InfosServlet returns info from the request and servlet context.";
    }

    @Override
    public void destroy()
    {
        super.destroy();

        log("InfosServlet destroy() method called.");
    }

    /**
     * This method could be use for debugging your servlets and your
     * or your JSPs
     * <BR>
     * Include this :
     * cx.ath.choisnet.servlet.debug.InfosServlet.appendHTML( out, this, request, response );
     *
     * @param output
     * @param servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public static void appendHTML(
            final Appendable          output,
            final HttpServlet         servlet,
            final HttpServletRequest  request,
            final HttpServletResponse response
            )
        throws IOException
    {
        new InfosServletDisplayerImpl(servlet, request, response).appendHTML( output );
    }

    /**
     * This method could be use for debugging your JSPs
     * <BR>
     * Include this :
     * cx.ath.choisnet.servlet.debug.InfosServlet.appendHTML( out, this, pageContext );
     *
     * @param output
     * @param servlet
     * @param pageContext
     * @throws ServletException
     * @throws IOException
     */
    public static void appendHTML(
            final Appendable     output,
            final HttpServlet    servlet,
            final PageContext    pageContext
            )
        throws IOException
    {
        new InfosServletDisplayerImpl(servlet, pageContext).appendHTML( output );
    }
}
