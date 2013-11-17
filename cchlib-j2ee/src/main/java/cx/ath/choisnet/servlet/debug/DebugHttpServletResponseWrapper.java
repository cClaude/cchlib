package cx.ath.choisnet.servlet.debug;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This class work like {@link javax.servlet.http.HttpServletResponseWrapper} but
 * add debug traces (using log4j) of each call.
 * <br/>
 * Provides a convenient implementation of the ServletResponse interface
 * that can be subclassed by developers wishing to adapt the response from
 * a Servlet. This class implements the Wrapper or Decorator pattern.
 * Methods default to calling through to the wrapped response object.
 *
 * @see javax.servlet.http.HttpServletResponseWrapper
 */
public class DebugHttpServletResponseWrapper
    extends DebugServletResponseWrapper
        implements HttpServletResponse
{
    private final static Logger LOGGER = Logger.getLogger( DebugHttpServletResponseWrapper.class );
    private final HttpServletResponse response;

    /**
     * Creates a ServletResponse adaptor wrapping the given response object.
     * @param response
     */
    public DebugHttpServletResponseWrapper( HttpServletResponse response )
    {
        super( response );
        this.response = response;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
     */
    @Override
    public void addCookie( Cookie cookie )
    {
        LOGGER.debug( "addCookie(" + cookie + ")" );
        this.response.addCookie(cookie);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
     */
    @Override
    public void addDateHeader( String name, long date )
    {
        LOGGER.debug( "addDateHeader(" + name + "," + date + ")" );
        this.response.addDateHeader(name,date);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void addHeader( String name, String value )
    {
        LOGGER.debug( "addHeader(" + name + "," + value + ")" );
        this.response.addHeader(name,value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
     */
    @Override
    public void addIntHeader( String name, int value )
    {
        LOGGER.debug( "addIntHeader(" + name + "," + value + ")" );
        this.response.addIntHeader(name,value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
     */
    @Override
    public boolean containsHeader( String name )
    {
        LOGGER.debug( "containsHeader(" + name + ")" );
        return this.response.containsHeader(name);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String)
     */
    @Override
    public String encodeRedirectURL( String url )
    {
        LOGGER.debug( "encodeRedirectURL(" + url + ")" );
        return this.response.encodeRedirectURL(url);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String)
     */
    @Override
    @Deprecated
    public String encodeRedirectUrl( String url )
    {
        LOGGER.debug( "encodeRedirectUrl(" + url + ")" );
        return this.response.encodeRedirectURL(url);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
     */
    @Override
    public String encodeURL( String url )
    {
        LOGGER.debug( "encodeURL(" + url + ")" );
        return this.response.encodeURL(url);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
     */
    @Override
    @Deprecated
    public String encodeUrl( String url )
    {
        LOGGER.debug( "encodeUrl(" + url + ")" );
        return this.response.encodeURL(url);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#getHeader(java.lang.String)
     */
    @Override
    public String getHeader( String name )
    {
        LOGGER.debug( "getHeader(" + name + ")" );
        return this.response.getHeader(name);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#getHeaderNames()
     */
    @Override
    public Collection<String> getHeaderNames()
    {
        LOGGER.debug( "getHeaderNames()" );
        return this.response.getHeaderNames();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#getHeaders(java.lang.String)
     */
    @Override
    public Collection<String> getHeaders( String name )
    {
        LOGGER.debug( "getHeaders(" + name + ")" );
        return this.response.getHeaders(name);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#getStatus()
     */
    @Override
    public int getStatus()
    {
        LOGGER.debug( "getStatus()" );
        return this.response.getStatus();
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#sendError(int)
     */
    @Override
    public void sendError( int sc ) throws IOException
    {
        LOGGER.debug( "sendError(" + sc + ")" );
        this.response.sendError(sc);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
     */
    @Override
    public void sendError( int sc, String msg ) throws IOException
    {
        LOGGER.debug( "sendError(" + sc + ","  + msg + ")" );
        this.response.sendError(sc,msg);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
     */
    @Override
    public void sendRedirect( String location ) throws IOException
    {
        LOGGER.debug( "sendRedirect(" + location + ")" );
        this.response.sendRedirect(location);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
     */
    @Override
    public void setDateHeader( String name, long date )
    {
        LOGGER.debug( "setDateHeader(" + name + ","  + date + ")" );
        this.response.setDateHeader(name,date);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void setHeader( String name, String value )
    {
        LOGGER.debug( "setHeader(" + name + ","  + value + ")" );
        this.response.setHeader(name,value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
     */
    @Override
    public void setIntHeader( String name, int value )
    {
        LOGGER.debug( "setIntHeader(" + name + ","  + value + ")" );
        this.response.setIntHeader(name,value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setStatus(int)
     */
    @Override
    public void setStatus( int sc )
    {
        LOGGER.debug( "setStatus(" + sc + ")" );
        this.response.setStatus(sc);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
     */
    @Override
    @Deprecated
    public void setStatus( int sc, String sm )
    {
        LOGGER.debug( "setStatus(" + sc + ","  + sm + ")" );
        this.response.setStatus(sc,sm);
    }
}
