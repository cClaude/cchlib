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
 *
 */
public class ActionServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * Default value : {@value}
     */
    public final static String DEFAULT_ACTION_PARAMETER_NAME = "$ACTION";
    /**
     * Default value : {@value}
     */
    public final static String DEFAULT_NEXTURL_PARAMETER_NAME = "$NEXTURL";
    /**
     * Default value : {@value}
     */
    public final static String DEFAULT_ERROR_PARAMETER_NAME = "$ERROR";

    /**
     * Servlet name :
     * @serial
     */
    protected static final String SERVLETNAME = ActionServlet.class.getName();
    /** @serial */
    private String actionParamName;
    /** @serial */
    private String nextParamName;
//    /** @serial */
//    private String faultBackURL; TO DO faultBackURL ???
    /** @serial */
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

        String action  = request.getParameter( getActionParameterName() );

        log( "ACTION["  + getActionParameterName()  + "]=[" + action  + "]" );

        ServletAction servletAction;

        try {
            final Class<?> c = Class.forName( action );
            final Object   o = c.newInstance();

            servletAction = ServletAction.class.cast( o );
            }
        catch( ClassNotFoundException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return;
            }
        catch( InstantiationException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return;
            }
        catch( IllegalAccessException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return;
            }
        catch( ClassCastException e ) {
            handleException( request, response, getActionParameterName(), action, e );
            return;
            }

        ActionServlet.Action nextAction;

        try {
            nextAction = servletAction.doAction( request, response, getServletContext() ) ;
            }
        catch( Exception e ) {
            log( "doAction[" + action + "]", e );
            handleException( request, response, getNextURLParameterName(), action, e );
            return;
            }

        String nextURL = request.getParameter( getNextURLParameterName() );

        log( "NEXTURL[" + getNextURLParameterName() + "]=[" + nextURL + "]" );
        //log( "request[" + request + "]" );
        //log( "response[" + response + "]" );
        //log( "getServletContext[" + getServletContext() + "]" );

        if( Action.FORWARD.equals( nextAction ) ) {
            try {
                getServletContext().getRequestDispatcher(nextURL).forward(request, response);
                }
            catch( IllegalArgumentException e ) {
                handleException( request, response, getNextURLParameterName(), nextURL, e );
                return;
                }
            }
        else if( Action.SENDREDIRECT.equals( nextAction ) ) {
            response.sendRedirect(nextURL);
        }
        //else if( Action.DONE.equals( nextAction ) ) {} // Done

    }

    private void handleException(
            final HttpServletRequest    request,
            final HttpServletResponse   response,
            final String                paramName,
            final String                paramValue,
            final Throwable             cause
            )
        throws IOException, ServletException
    {
        StringBuilder msg = new StringBuilder( cause.getClass().getName() );

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
     * TODOC
     * 
     * @return TODOC
     */
    protected String getActionParameterName()
    {
        return this.actionParamName;
    }

    /**
     * TODOC
     * 
     * @return TODOC
     */
    protected String getNextURLParameterName()
    {
        return this.nextParamName;
    }

    /**
     * TODOC
     * 
     * @return TODOC
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

//    @Override
//    public void destroy()
//    {
//        super.destroy();
//    }

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
