package com.googlecode.cchlib.servlet.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.servlet.ActionServlet;
import com.googlecode.cchlib.servlet.Tools;
import com.googlecode.cchlib.servlet.exception.RequestParameterNotFoundException;
import com.googlecode.cchlib.servlet.exception.RequestParameterNumberFormatException;
import com.googlecode.cchlib.servlet.exception.ServletActionAssertException;
import com.googlecode.cchlib.servlet.exception.ServletActionException;
import com.googlecode.cchlib.sql.SQLTools;

/**
 * NEEDDOC
 */
public abstract class AbstractServletAction implements ServletAction
{
    private static final Logger LOGGER = Logger.getLogger( AbstractServletAction.class );

    private HttpServletRequest  request;
    private HttpServletResponse response;
    private ServletContext      servletContext;

    /**
     * Action to do
     *
     * @return value is handle like {@link ServletAction#doAction(HttpServletRequest, HttpServletResponse, ServletContext)}
     * @see ServletAction#doAction(HttpServletRequest, HttpServletResponse, ServletContext)
     * @see #getRequest()
     * @see #getResponse()
     * @see #getServletContext()
     * @throws ServletActionException if any error occur
     */
    public abstract ActionServlet.Action doAction()
        throws ServletActionException;

    /**
     * Initialize environment and call {@link #doAction()}
     */
    @Override
    public ActionServlet.Action doAction(
            final HttpServletRequest    request,
            final HttpServletResponse   response,
            final ServletContext        servletContext
            )
        throws ServletActionException
    {
        init(request,response,servletContext);

        return doAction();
    }

    /**
     * Initialize AbstractServletAction
     */
    private void init(
            final HttpServletRequest    request,
            final HttpServletResponse   response,
            final ServletContext        servletContext
            )
    {
        if( request == null ) {
            throw new NullPointerException( "HttpServletRequest not valid" );
            }
        if( response == null ) {
            throw new NullPointerException( "HttpServletResponse not valid" );
            }
        if( servletContext == null ) {
            throw new NullPointerException( "ServletContext not valid" );
            }

        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
    }

    /**
     * @return the HttpServletRequest
     */
    public HttpServletRequest getRequest()
    {
        return this.request;
    }

    /**
     * @return the HttpServletResponse
     */
    public HttpServletResponse getResponse()
    {
        return this.response;
    }

    /**
     * @return the ServletContext
     */
    public ServletContext getServletContext()
    {
        return this.servletContext;
    }

    /**
     * Returns the value of a request parameter as a String.
     * <BR>
     * You should only use this method when you are sure the parameter
     * has only one value. If the parameter might have more than one value,
     * use getParameterValues(java.lang.String).
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return a String representing the single value of the parameter
     * @throws RequestParameterNotFoundException  if the parameter does not exist.
     * @see HttpServletRequest#getParameter(String)
     * @see Tools#getParameterValues(HttpServletRequest, String)
     */
    public String getParameter(
            final String name
            )
        throws RequestParameterNotFoundException
    {
        return Tools.getParameter( this.request, name );
    }

    /**
     * Returns the value of a request parameter as an integer.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return a String representing the single value of the parameter
     * @throws RequestParameterNotFoundException  if the parameter does not exist.
     * @throws RequestParameterNumberFormatException if the value is not an integer
     */
    @SuppressWarnings({"squid:S1160"})
    public int getIntParameter(
            final String name
            )
        throws  RequestParameterNotFoundException,
                RequestParameterNumberFormatException
    {
        return Tools.getIntParameter( this.request, name );
    }

    /**
     * Returns the value of a request parameter as a String. Result String
     * is safe to use as a SQL field parameter.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return a String representing the single value of the parameter,
     *         safe to use as a SQL field parameter
     * @throws RequestParameterNotFoundException  if the parameter does not exist.
     * @see #getParameter(String)
     * @see SQLTools
     */
    public String getSQLParameter(
            final String name
            )
        throws RequestParameterNotFoundException
    {
        return SQLTools.parseFieldValue( getParameter( name ) );
    }

    /**
     * Returns an array of String objects containing all of the
     * values the given request parameter has.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return an array of String objects containing the parameter's values
     * @throws RequestParameterNotFoundException if the parameter does not exist.
     * @see HttpServletRequest#getParameter(String)
     * @see Tools#getParameter(HttpServletRequest, String)
     */
    public String[] getParameterValues(
            final String name
            )
        throws RequestParameterNotFoundException
    {
        return Tools.getParameterValues( this.request, name );
    }

    /**
     * Returns an array of int containing all of the
     * values the given request parameter has.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return a String representing the single value of the parameter
     * @throws RequestParameterNotFoundException  if the parameter does not exist.
     * @throws RequestParameterNumberFormatException if at least one value is not an integer
     */
    @SuppressWarnings({"squid:S1160"})
    public int[] getIntParameterValues(
            final String name
            )
        throws  RequestParameterNotFoundException,
                RequestParameterNumberFormatException
    {
        return Tools.getIntParameterValues( this.request, name );
    }

    /**
     * Returns an array of String objects containing all of the
     * values the given request parameter has. Result String
     * are safe to use as a SQL field parameters.
     *
     * @param name a String containing the name of the parameter whose value is requested
     * @return an array of String objects containing the parameter's values
     * @throws RequestParameterNotFoundException if the parameter does not exist.
     * @see #getSQLParameter(String)
     * @see #getParameterValues(String)
     */
    public String[] getSQLParameterValues(
            final String name
            )
        throws RequestParameterNotFoundException
    {
        final String[] s = Tools.getParameterValues( this.request, name );
        final String[] r = new String[ s.length ];

        for(int i=0; i<s.length; i++) {
            r[ i ] = SQLTools.parseFieldValue( s[ i ] );
            }

        return r;
    }

    /**
     * Writes the specified message to a servlet log file, usually an event log.
     * @param message a String that describes the error or exception
     */
    public void log( final String message )
    {
        if( getServletContext() == null ) {
            throw new NullPointerException( "ServletContext not initilialized" );
            }

        getServletContext().log( message );
        LOGGER.info( message );
    }

    /**
     * Writes the specified message to a servlet log file, usually an event log.
     * @param message a String that describes the error or exception
     * @param throwable the Throwable error or exception
     */
    public void log( final String message, final Throwable throwable )
    {
        if( getServletContext() == null ) {
            throw new NullPointerException( "ServletContext not initilialized" );
            }

        getServletContext().log( message, throwable );
        LOGGER.info( message, throwable );
    }

    /**
     * Debug/Check helper
     *
     * @param o
     * @throws ServletActionAssertException
     */
    public static void assertNull( final Object o )
        throws ServletActionAssertException
    {
        assertNull( null, o );
    }

    /**
     * Debug/Check helper
     *
     * @param o
     * @throws ServletActionAssertException
     */
    public static void assertNull(
            final String message,
            final Object o
            )
        throws ServletActionAssertException
    {
        if( o != null ) {
            final StringBuilder sb = new StringBuilder();

            sb.append( "NOT NULL: " )
              .append( o );

            if( message != null ) {
                sb.append( " : " ).append( message );
            }

            throw new ServletActionAssertException( sb.toString() );
        }
    }

    /**
     * Debug/Check helper
     *
     * @param o
     * @throws ServletActionAssertException
     */
    public static void assertNotNull( final Object o )
        throws ServletActionAssertException
    {
        assertNotNull( null, o );
    }

    /**
     * Debug/Check helper
     *
     * @param message
     * @param o
     * @throws ServletActionAssertException
     */
    public static void assertNotNull(
            final String message,
            final Object o
            )
        throws ServletActionAssertException
    {
        if( o == null ) {
            throw new ServletActionAssertException( "NULL" );
            }
        else {
            final StringBuilder sb = new StringBuilder();

            sb.append( "NULL" );

            if( message != null ) {
                sb.append( " : " ).append( message );
                }

            throw new ServletActionAssertException( sb.toString() );
            }
    }
}

