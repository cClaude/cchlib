/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/servlet/sql/LogServlet.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.00 2005.03.24 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.servlet.sql.LogServlet
 **
 */
package cx.ath.choisnet.servlet.sql;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import cx.ath.choisnet.sql.DataSourceFactory;
import cx.ath.choisnet.sql.SimpleUpdate;
import cx.ath.choisnet.sql.mysql.MySQLTools;

/**
 ** <P>
 * <B>Servlet :</B> Ajoute une entre dans la table des logs.
 * </P>
 **
 **
 ** <B>Configuration :</B><BR>
 **
 ** <XMP> </XMP>
 **
 **
 ** @author Claude CHOISNET
 ** @version 1.00
 */
@Deprecated
public class LogServlet extends HttpServlet
{
    /** serialVersionUID */
    private static final long serialVersionUID = 3L;

    /** base acces */
    private DataSource dataSource;

    @Override
    public void init( final ServletConfig servletConfig ) // ------------------------
            throws ServletException
    {
        super.init( servletConfig );

        try {
            this.dataSource = DataSourceFactory.buildMySQLDataSource(
                    "127.0.0.1", "tools", "tomcat", "tomcat" );
        }
        catch( final Exception e ) {
            throw new ServletException( "Can't init database access", e );
        }
    }

    @Override
    public void service( // ---------------------------------------------------
            final HttpServletRequest request, final HttpServletResponse response )
            throws javax.servlet.ServletException, java.io.IOException
    {
        final String col_AutoIpAddress = request.getRemoteAddr();
        final String col_AutoComputerName = request.getRemoteHost();

        final String col_ComputerName = MySQLTools.parseFieldValue(
                getParameter( request, "CN", "COMPUTERNAME", "ComputerName" ),
                16 );
        final String col_LogDate = MySQLTools.parseFieldValue(
                getParameter( request, "DATE", "LOGDATE", "LogDate" ), 24 );
        final String col_AppName = MySQLTools.parseFieldValue(
                getParameter( request, "AN", "APPNAME", "AppName" ), 64 );
        final String col_Message = MySQLTools.parseFieldValue(
                getParameter( request, "MSG", "MESSAGE", "Message" ), 255 );

        final String nextURL = request.getParameter( "X_NEXTURL" );

        if( nextURL == null ) {
            throw new ServletException( "parameter 'X_NEXTURL' not found." );
        }

        final String query = ("INSERT INTO `Logger` (`AutoLogDate`,`AutoIpAddress`,`AutoComputerName`, "
                + "`ComputerName`, `LogDate`, `AppName`, `Message`) "
                + "VALUES ( NOW(),'")
                + col_AutoIpAddress
                + "','"
                + col_AutoComputerName
                + "', "
                + "'"
                + col_ComputerName
                + "', '"
                + col_LogDate
                + "', "
                + "'"
                + col_AppName
                + "', '"
                + col_Message + "');";

        int result;
        Throwable exception;

        try(SimpleUpdate simpleUpdate = new SimpleUpdate( this.dataSource )) {
            result = simpleUpdate.doUpdate( query );
            exception = null;
        }
        catch( final Exception e ) {
            getLogger().warn( "Error while add in log : " + query, e );

            result = -1;
            exception = e;
        }

        final HttpSession session = request.getSession();

        session.setAttribute( "RESULT", Integer.toString( result ) );
        session.setAttribute( "EXCEPTION", exception );

        this.getServletContext().getRequestDispatcher( nextURL )
                .forward( request, response );
    }

    private static final String getParameter(
            // ------------------------------
            final HttpServletRequest request, final String paramName1, final String paramName2,
            final String paramName3 )
    {
        String value = request.getParameter( paramName1 );

        if( value == null ) {
            value = request.getParameter( paramName2 );

            if( value == null ) {
                value = request.getParameter( paramName3 );

                if( value == null ) {
                    value = "*";
                }
            }
        }

        return value;
    }

    /**
     ** Gestion des traces
     */
    protected final org.apache.log4j.Logger getLogger() // --------------------
    {
        return org.apache.log4j.Logger.getLogger( this.getClass() );
    }

}
