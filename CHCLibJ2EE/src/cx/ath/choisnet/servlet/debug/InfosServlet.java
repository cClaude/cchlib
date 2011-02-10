package cx.ath.choisnet.servlet.debug;

import cx.ath.choisnet.servlet.debug.impl.InfosServletDisplayerImpl;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Display informations from {@link HttpServlet} to {@link HttpServlet#log(String)},
 * {@link System#out}, {@link System#err}, {@link HttpServletResponse#getWriter()}.
 * <br/>
 * 
 * 
 * <p>
 *  Configuration:
 * </p>
 * <pre>
 * </pre>
 * 
 * @author Claude CHOISNET
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
        InfosServletDisplayer infos = new InfosServletDisplayerImpl(this, request, response);

        response.setContentType("text/html");

        infos.appendHTML( response.getWriter() );
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
}
