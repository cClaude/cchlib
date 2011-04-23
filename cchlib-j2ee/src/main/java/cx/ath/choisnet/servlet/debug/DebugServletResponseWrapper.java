package cx.ath.choisnet.servlet.debug;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 * This class work like {@link javax.servlet.ServletResponseWrapper} but
 * add debug traces (using log4j) of each call.
 * <br/>
 * Provides a convenient implementation of the ServletResponse interface
 * that can be subclassed by developers wishing to adapt the response from
 * a Servlet. This class implements the Wrapper or Decorator pattern.
 * Methods default to calling through to the wrapped response object.
 *
 * @author Claude CHOISNET
 * @see javax.servlet.ServletResponseWrapper
 */
public class DebugServletResponseWrapper implements ServletResponse
{
    private final static Logger logger = Logger.getLogger( DebugServletResponseWrapper.class );
    private final ServletResponse response;

    /**
     * Creates a ServletResponse adaptor wrapping the given response object.
     * @param response
     */
    public DebugServletResponseWrapper( ServletResponse response )
    {
        this.response = response;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#flushBuffer()
     */
    @Override
    public void flushBuffer() throws IOException
    {
        logger.debug( "flushBuffer()" );
        this.response.flushBuffer();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getBufferSize()
     */
    @Override
    public int getBufferSize()
    {
        logger.debug( "getBufferSize()" );
        return this.response.getBufferSize();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getCharacterEncoding()
     */
    @Override
    public String getCharacterEncoding()
    {
        logger.debug( "getCharacterEncoding()" );
        return this.response.getCharacterEncoding();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getContentType()
     */
    @Override
    public String getContentType()
    {
        logger.debug( "getContentType()" );
        return this.response.getContentType();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getLocale()
     */
    @Override
    public Locale getLocale()
    {
        logger.debug( "getLocale()" );
        return this.response.getLocale();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getOutputStream()
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        logger.debug( "getOutputStream()" );
        return this.response.getOutputStream();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#getWriter()
     */
    @Override
    public PrintWriter getWriter() throws IOException
    {
        logger.debug( "getWriter()" );
        return this.response.getWriter();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#isCommitted()
     */
    @Override
    public boolean isCommitted()
    {
        logger.debug( "isCommitted()" );
        return this.response.isCommitted();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#reset()
     */
    @Override
    public void reset()
    {
        logger.debug( "reset()" );
        this.response.reset();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#resetBuffer()
     */
    @Override
    public void resetBuffer()
    {
        logger.debug( "resetBuffer()" );
        this.response.resetBuffer();
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setBufferSize(int)
     */
    @Override
    public void setBufferSize( int size )
    {
        logger.debug( "setBufferSize(" + size + ")" );
        this.response.setBufferSize(size);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setCharacterEncoding(java.lang.String)
     */
    @Override
    public void setCharacterEncoding( String charset )
    {
        logger.debug( "setCharacterEncoding(" + charset + ")" );
        this.response.setCharacterEncoding(charset);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setContentLength(int)
     */
    @Override
    public void setContentLength( int len )
    {
        logger.debug( "setContentLength(" + len + ")" );
        this.response.setContentLength(len);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setContentType(java.lang.String)
     */
    @Override
    public void setContentType( String type )
    {
        logger.debug( "setContentType(" + type + ")" );
        this.response.setContentType(type);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
     */
    @Override
    public void setLocale( Locale locale )
    {
        logger.debug( "setLocale(" + locale + ")" );
        this.response.setLocale(locale);
    }
}
