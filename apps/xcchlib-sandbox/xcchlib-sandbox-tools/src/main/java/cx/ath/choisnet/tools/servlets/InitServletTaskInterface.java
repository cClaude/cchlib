package cx.ath.choisnet.tools.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 *
 * @since 1.00
 */
public interface InitServletTaskInterface extends Runnable
{

    public void init( ServletConfig servletConfig ) throws ServletException;

    public String getTaskName();
    public boolean continueRunning();
    public void log( String message );
}
