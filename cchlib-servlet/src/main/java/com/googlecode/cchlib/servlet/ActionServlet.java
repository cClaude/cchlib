package com.googlecode.cchlib.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.cchlib.servlet.action.ServletAction;

/**
 * Servlet: com.googlecode.cchlib.servlet.ActionServlet
 *
 * <pre>
 *  Parameters:
 *      ACTION:
 *      NEXTURL:
 *      DEFAULTERRORURL:
 * </pre>
 */
@SuppressWarnings({
    "squid:CommentedOutCodeLine",
    "squid:S1135",
    "squid:S1133",
    "squid:S2147",
    "squid:S00100"
    })
public class ActionServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * Default value : {@value}
     */
    public static final String DEFAULT_ACTION_PARAMETER_NAME = "$ACTION";
    /**
     * Default value : {@value}
     */
    public static final String DEFAULT_NEXTURL_PARAMETER_NAME = "$NEXTURL";
    /**
     * Default value : {@value}
     */
    public static final String DEFAULT_ERROR_PARAMETER_NAME = "$ERROR";

    /**
     * Servlet name :
     * @serial
     */
    protected static final String SERVLETNAME = ActionServlet.class.getName();
    /** @serial */
    @SuppressWarnings("squid:S2226") // Servlet parameter
    private String actionParamName;
    /** @serial */
    @SuppressWarnings("squid:S2226") // Servlet parameter
    private String nextParamName;
//    /** @serial */
//    private String faultBackURL; TO DO faultBackURL ???
    /** @serial */
    @SuppressWarnings("squid:S2226") // Servlet parameter
    private String errorParamName;

    @Override
    public void init()
        throws javax.servlet.ServletException
    {
        this.actionParamName = private_getInitParameter( "ACTION", DEFAULT_ACTION_PARAMETER_NAME );
        this.nextParamName   = private_getInitParameter( "NEXTURL", DEFAULT_NEXTURL_PARAMETER_NAME );
        this.errorParamName  = private_getInitParameter( "ERROR", "$ERROR" );
//        this.faultBackURL   = private_getInitParameter( "DEFAULTERRORURL", Const.EMPTY_STRING );
    }

    private String private_getInitParameter( final String name )
    {
        final String value = getInitParameter( name );

        log( SERVLETNAME + " init: [" + name + "] = [" + value + "]" );

        return value;
    }

    private String private_getInitParameter( final String name, final String defaultValue )
    {
        final String value = private_getInitParameter(name);

        if( value == null ) {
            log( SERVLETNAME + " init : [" + name + "] = (novalue) use default=[" + defaultValue + "]" );

            return defaultValue;
        }

        return value;
    }

    @Override
    public void service(
            final HttpServletRequest  request,
            final HttpServletResponse response
            )
        throws ServletException, IOException
    {
        //TO DO: should be customizable !!!!
        //request.setCharacterEncoding( "utf-8" );

        final String action = request.getParameter( getActionParameterName() );

        log( "ACTION["  + getActionParameterName()  + "]=[" + action  + "]" );

        final ServletAction servletAction = getServletAction( request, response, action );

        if( servletAction != null ) {
            final ActionServlet.Action nextAction = getNextAction( request, response, servletAction, action );

            if( nextAction != null ) {
                final String nextURL = request.getParameter( getNextURLParameterName() );

                log( "NEXTURL[" + getNextURLParameterName() + "]=[" + nextURL + "]" );
                //log( "request[" + request + "]" );
                //log( "response[" + response + "]" );
                //log( "getServletContext[" + getServletContext() + "]" );

                handleRequest( request, response, nextAction, nextURL );
            }
        }
    }

    private void handleRequest(
        final HttpServletRequest    request,
        final HttpServletResponse   response,
        final ActionServlet.Action  nextAction,
        final String                nextURL
        )
        throws ServletException, IOException
    {
        if( Action.FORWARD.equals( nextAction ) ) {
            try {
                getServletContext().getRequestDispatcher(nextURL).forward(request, response);
                }
            catch( final IllegalArgumentException e ) {
                handleException( request, response, getNextURLParameterName(), nextURL, e );
                }
            }
        else if( Action.SENDREDIRECT.equals( nextAction ) ) {
            response.sendRedirect(nextURL);
        }
        //else if( Action.DONE.equals( nextAction ) ) {} // Done
    }

    private ActionServlet.Action getNextAction(
            final HttpServletRequest  request,
            final HttpServletResponse response,
            final ServletAction       servletAction,
            final String              action
            ) throws IOException, ServletException
    {
        try {
            return servletAction.doAction( request, response, getServletContext() ) ;
            }
        catch( final Exception e ) {
            log( "doAction[" + action + "]", e );
            handleException( request, response, getNextURLParameterName(), action, e );

            return null;
            }
    }

    private ServletAction getServletAction(
            final HttpServletRequest  request,
            final HttpServletResponse response,
            final String              action
            ) throws IOException, ServletException
    {
        try {
            final Class<?> c = Class.forName( action );
            final Object   o = c.newInstance();

            return ServletAction.class.cast( o );
            }
        catch( final ClassNotFoundException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return null;
            }
        catch( final InstantiationException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return null;
            }
        catch( final IllegalAccessException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return null;
            }
        catch( final ClassCastException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return null;
            }
    }

    @SuppressWarnings("squid:S1172")
    private void handleException(
            final HttpServletRequest    request,
            final HttpServletResponse   response,
            final String                paramName,
            final String                paramValue,
            final Throwable             cause
            )
        throws IOException, ServletException
    {
        final StringBuilder msg = new StringBuilder( cause.getClass().getName() );

        msg.append( ": '" )
           .append( paramName )
           .append( "' = '" )
           .append( paramValue )
           .append( '\'' );

        log( msg.toString(), cause );

        //request.setAttribute( getErrorParameterName(), cause );

        //response.sendRedirect( request.getContextPath() + getFaultBackURL( request ) );
        //getServletContext().getRequestDispatcher( getErrorURL( request ) ).forward(request, response);
        throw new ServletException( msg.toString(), cause );

        //TODO: add parameter to forward (or sendRedirect) for some exceptions
        //add a interface/class
        //   Action handleException( Throwable e, msg? );
    }

//    /**
//     * Returns fault back URL, default implementation return an empty String
//     * @return fault back URL
//     */
//    protected String getErrorURL(HttpServletRequest request)
//    {
//        return Const.EMPTY_STRING;
//    }

    /**
     * NEEDDOC
     *
     * @return NEEDDOC
     */
    protected String getActionParameterName()
    {
        return this.actionParamName;
    }

    /**
     * NEEDDOC
     *
     * @return NEEDDOC
     */
    protected String getNextURLParameterName()
    {
        return this.nextParamName;
    }

    /**
     * NEEDDOC
     *
     * @return NEEDDOC
     */
    protected String getErrorParameterName()
    {
        return this.errorParamName;
    }

    @Override
    public String getServletInfo()
    {
        return "Generic Servlet able to call an Action take on request and redirect to a URL taken on request";
    }

    /**
     * How ActionServlet should handle display
     */
    public enum Action {
        /**
         * Display already done, do nothing (only handle exception)
         */
        DONE,
        /**
         * Forward using NEXTURL parameter
         *
         * @see javax.servlet.RequestDispatcher#forward(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
         */
        FORWARD,
        /**
         * sendRedirect using NEXTURL parameter
         *
         * @see HttpServletResponse#sendRedirect(String)
         */
        SENDREDIRECT,
    }

}
