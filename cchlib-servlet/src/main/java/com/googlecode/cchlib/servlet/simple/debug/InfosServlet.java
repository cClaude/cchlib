package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.servlet.simple.debug.impl.InfosServletDisplayerImpl;

/**
 * Display informations from {@link HttpServlet} to {@link HttpServlet#log(String)},
 * {@link System#out}, {@link System#err}, {@link HttpServletResponse#getWriter()}.
 *
 * <p>Configuration:
 * <br>
 * NEEDDOC
 */
public class InfosServlet extends HttpServlet
{
    private static final long   serialVersionUID = 1L;
    private static final Logger LOGGER           = Logger.getLogger( InfosServlet.class );

    /**
     * Servlet name : "{@value}"
     * @serial
     */
    protected static final String SERVLETNAME = "InfosServlet";

    @Override
    @SuppressWarnings("squid:S106")
    public void init() throws ServletException
    {
        // Use standard logger
        log("InfosServlet init() method called.");

        // Log to stdout
        final PrintStream stdout = System.out;

        stdout.print( "print on System.out from " );
        stdout.println( this );

        // Log to stderr
        final PrintStream stderr = System.err;

        stderr.print( "print on System.err from " );
        stderr.println( this );

        final Enumeration<?> names = getServletConfig().getInitParameterNames();

        if( names != null ) {
            while( names.hasMoreElements() ) {
                final String name  = (String)names.nextElement();
                final String value = getServletConfig().getInitParameter(name);

                log( "InfosServlet init argument: [" + name + "] = [" + value + "]" );
            }

        }
        else {
            log("InfosServlet has no init arguments");
        }

    }

    @Override
    public void service(
            final HttpServletRequest  request,
            final HttpServletResponse response
            )
        throws ServletException, IOException
    {
        response.setContentType("text/html");

        try {
            appendHTML(
                    response.getWriter(),
                    this,
                    request,
                    response
                    );
        } catch( final Exception e ) {
            LOGGER.fatal( InfosServlet.class, e );
        }
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
     * @param output NEEDDOC
     * @param servlet NEEDDOC
     * @param request NEEDDOC
     * @param response NEEDDOC
     * @throws IOException NEEDDOC
     */
    public static void appendHTML(
            final Appendable          output,
            final HttpServlet         servlet,
            final HttpServletRequest  request,
            final HttpServletResponse response
            )
        throws IOException
    {
        new InfosServletDisplayerImpl(servlet, request, response)
            .appendHTML( output );
    }

    /**
     * This method could be use for debugging your JSPs
     * <BR>
     * Include this :
     * cx.ath.choisnet.servlet.debug.InfosServlet.appendHTML( out, this, pageContext );
     *
     * @param output NEEDDOC
     * @param servlet NEEDDOC
     * @param pageContext NEEDDOC
     * @throws IOException NEEDDOC
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
