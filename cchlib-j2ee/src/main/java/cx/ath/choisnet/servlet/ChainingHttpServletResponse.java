package cx.ath.choisnet.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * Add possibility to HttpServletResponse to commit HttpServletResponse
 * after filter chaining
 *
 */
public interface ChainingHttpServletResponse extends HttpServletResponse
{
    /**
     * Finish a response.
     * @throws IOException
     */
    public void finishResponse() throws IOException;
}
