package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.log4j.Logger;

/**
 * This class work like {@link javax.servlet.http.HttpServletResponseWrapper}
 * but add debug traces (using log4j) of each call.
 * <p>
 * Provides a convenient implementation of the ServletResponse interface
 * that can be subclassed by developers wishing to adapt the response from
 * a Servlet. This class implements the Wrapper or Decorator pattern. Methods
 * default to calling through to the wrapped response object.
 *
 * @see HttpServletResponseWrapper
 */
public class DebugHttpServletResponseWrapper
    extends DebugServletResponseWrapper
        implements HttpServletResponse
{
    private static final Logger LOGGER = Logger.getLogger( DebugHttpServletResponseWrapper.class );

    private final HttpServletResponse response;

    /**
     * Creates a ServletResponse adaptor wrapping the given response object.
     *
     * @param response
     *            Current {@link HttpServletResponse}
     */
    public DebugHttpServletResponseWrapper( final HttpServletResponse response )
    {
        super( response );

        this.response = response;
    }

    @Override
    public void addCookie( final Cookie cookie )
    {
        LOGGER.debug( "addCookie(" + cookie + ")" );

        this.response.addCookie(cookie);
    }

    @Override
    public void addDateHeader( final String name, final long date )
    {
        LOGGER.debug( "addDateHeader(" + name + "," + date + ")" );

        this.response.addDateHeader(name,date);
    }

    @Override
    public void addHeader( final String name, final String value )
    {
        LOGGER.debug( "addHeader(" + name + "," + value + ")" );

        this.response.addHeader(name,value);
    }

    @Override
    public void addIntHeader( final String name, final int value )
    {
        LOGGER.debug( "addIntHeader(" + name + "," + value + ")" );

        this.response.addIntHeader(name,value);
    }

    @Override
    public boolean containsHeader( final String name )
    {
        LOGGER.debug( "containsHeader(" + name + ")" );

        return this.response.containsHeader(name);
    }

    @Override
    public String encodeRedirectURL( final String url )
    {
        LOGGER.debug( "encodeRedirectURL(" + url + ")" );

        return this.response.encodeRedirectURL(url);
    }

    @Override
    @Deprecated
    @SuppressWarnings({"squid:S1133","squid:MissingDeprecatedCheck"}) // Deprecated by API
    public String encodeRedirectUrl( final String url )
    {
        LOGGER.debug( "encodeRedirectUrl(" + url + ")" );

        return this.response.encodeRedirectURL(url);
    }

    @Override
    public String encodeURL( final String url )
    {
        LOGGER.debug( "encodeURL(" + url + ")" );

        return this.response.encodeURL(url);
    }

    @Override
    @Deprecated
    @SuppressWarnings({"squid:S1133","squid:MissingDeprecatedCheck"}) // Deprecated by API
    public String encodeUrl( final String url )
    {
        LOGGER.debug( "encodeUrl(" + url + ")" );

        return this.response.encodeURL(url);
    }

    @Override
    public String getHeader( final String name )
    {
        LOGGER.debug( "getHeader(" + name + ")" );

        return this.response.getHeader(name);
    }

    @Override
    public Collection<String> getHeaderNames()
    {
        LOGGER.debug( "getHeaderNames()" );

        return this.response.getHeaderNames();
    }

    @Override
    public Collection<String> getHeaders( final String name )
    {
        LOGGER.debug( "getHeaders(" + name + ")" );

        return this.response.getHeaders(name);
    }

    @Override
    public int getStatus()
    {
        LOGGER.debug( "getStatus()" );

        return this.response.getStatus();
    }

    @Override
    public void sendError( final int sc ) throws IOException
    {
        LOGGER.debug( "sendError(" + sc + ")" );

        this.response.sendError(sc);
    }

    @Override
    public void sendError( final int sc, final String msg ) throws IOException
    {
        LOGGER.debug( "sendError(" + sc + ","  + msg + ")" );

        this.response.sendError(sc,msg);
    }

    @Override
    public void sendRedirect( final String location ) throws IOException
    {
        LOGGER.debug( "sendRedirect(" + location + ")" );

        this.response.sendRedirect(location);
    }

    @Override
    public void setDateHeader( final String name, final long date )
    {
        LOGGER.debug( "setDateHeader(" + name + ","  + date + ")" );

        this.response.setDateHeader(name,date);
    }

    @Override
    public void setHeader( final String name, final String value )
    {
        LOGGER.debug( "setHeader(" + name + ","  + value + ")" );

        this.response.setHeader(name,value);
    }

    @Override
    public void setIntHeader( final String name, final int value )
    {
        LOGGER.debug( "setIntHeader(" + name + ","  + value + ")" );

        this.response.setIntHeader(name,value);
    }

    @Override
    public void setStatus( final int sc )
    {
        LOGGER.debug( "setStatus(" + sc + ")" );

        this.response.setStatus(sc);
    }

    @Override
    @Deprecated
    @SuppressWarnings({"squid:S1133","squid:MissingDeprecatedCheck"}) // Deprecated by API
    public void setStatus( final int sc, final String sm )
    {
        LOGGER.debug( "setStatus(" + sc + ","  + sm + ")" );

        this.response.setStatus(sc,sm);
    }
}
