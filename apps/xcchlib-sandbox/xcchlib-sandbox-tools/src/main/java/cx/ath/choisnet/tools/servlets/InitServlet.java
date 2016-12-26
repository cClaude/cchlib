package cx.ath.choisnet.tools.servlets;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

/**
 *
 *
 * @since 1.00
 */
public class InitServlet extends HttpServlet
{
    private final class ServletTimerTask extends TimerTask
    {
        @Override
        public void run()
        {
            LOGGER.info( "run tasks BEGIN " + this.hashCode() );

            for( final Iterator<InitServletTaskInterface> iterator = InitServlet.this.tasks
                    .iterator(); iterator.hasNext(); ) {
                final InitServletTaskInterface taskInstance = iterator.next();

                if( LOGGER.isDebugEnabled() ) {
                    LOGGER.debug( "run tasks: "
                            + taskInstance.getTaskName() + " "
                            + this.hashCode() );
                    LOGGER.debug( "run tasks: @" + taskInstance.hashCode() );
                }

                if( InitServlet.this.cancelTimer ) {
                    break;
                }

                new Thread( taskInstance ).start();

                if( !taskInstance.continueRunning() ) {
                    iterator.remove();
                }
            }

            LOGGER.info( "run tasks END " + this.hashCode() );
        }
    }

    private static final long              serialVersionUID = 1L;
    private static final Logger            LOGGER           = Logger.getLogger( InitServlet.class );

    /** Servlet parameter */
    public static final String             DELAY            = "DELAY";

    /** Servlet parameter */
    public static final String             PERIOD           = "PERIOD";

    /** Servlet parameter */
    public static final String             TASKLIST         = "TASK-LIST";

    private final LinkedList<InitServletTaskInterface> tasks = new LinkedList<>();

    /**
     * Delais avant la première execution de la liste des tâches.
     *
     * @see #DELAY
     */
    private long                           initialDelay;

    /**
     * Delais en (ms) entre chaques executions de la liste des taches.
     * <p>
     * Si cette valeur est égale à zéro ou négative, les taches différées ne
     * seront pas lancées.
     *
     * @see #PERIOD
     */
    private long                           period;

    private Timer                          timer            = null;
    private boolean                        cancelTimer      = false;

    @Override
    public void init( final ServletConfig servletConfig ) throws ServletException
    {
        super.init( servletConfig );

        //
        // TASKLIST
        //
        final String          value   = servletConfig.getInitParameter( TASKLIST );
        final StringTokenizer classST = new StringTokenizer( value, ";, \t\n\r" );

        while( classST.hasMoreTokens() ) {
            final String classNameBase = classST.nextToken() + "()";
            final int    end1          = classNameBase.indexOf( '(' );
            final String className     = classNameBase.substring( 0, end1 );
            final int    end2          = classNameBase.indexOf( ')' );
            final String param         = classNameBase.substring( end1 + 1, end2 );

            if( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "param [" + param + "]" );
            }

            try {
                InitServletTaskInterface instance;

                final Class<? extends InitServletTaskInterface> aClass = Class
                        .forName( className ).asSubclass(
                                InitServletTaskInterface.class );

                if( param.length() > 0 ) {
                    final Constructor<? extends InitServletTaskInterface> constructor = aClass
                            .getConstructor( String.class );

                    instance = constructor.newInstance( param );
                } else {
                    instance = aClass.newInstance();
                }

                instance.init( servletConfig );
                this.tasks.add( instance );
            }
            catch( final ServletException e ) {
                throw e;
            }
            catch( final Exception e ) {
                //
                // InstantiationException
                // ClassNotFoundException
                // IllegalAccessException
                // ClassCastException
                // ....
                //
                LOGGER.fatal( "*** FAIL '" + className + "' ***", e );
            }
        }

        //
        // DELAY
        //
        this.initialDelay = getInitParameterAsLong( servletConfig, DELAY );

        if( this.initialDelay < 0 ) {
            this.initialDelay = 0;
        }

        //
        // PERIOD
        //
        this.period = getInitParameterAsLong( servletConfig, PERIOD );

        LOGGER.info( "delay      = " + this.initialDelay );
        LOGGER.info( "delay sec  = " + (this.initialDelay / 1000) );
        LOGGER.info( "period     = " + this.period );
        LOGGER.info( "period sec = " + (this.period / 1000) );

        if( this.period > 0 ) {
            this.timer = new Timer( true /* run as a daemon */);

            final TimerTask aTimerTask = new ServletTimerTask();

            this.timer.schedule( aTimerTask, this.initialDelay, this.period );
        }

        LOGGER.info( "init() - EXIT" );
    }

    @Override
    public void destroy()
    {
        LOGGER.info( "destroy() - Prepare : " + this );

        this.timer.cancel();
        this.cancelTimer = true;

        LOGGER.info( "destroy() - Exit: " + this );
    }

    private long getInitParameterAsLong(
        final ServletConfig servletConfig,
        final String        paramName
        ) throws ServletException
    {
        final String value = servletConfig.getInitParameter( paramName );

        if( value == null ) {
            final String msg = "parameter '" + paramName + "' not found.";

            LOGGER.fatal( "*** " + msg );

            throw new ServletException( msg );
        } else {
            try {
                return Long.parseLong( value );
            }
            catch( final NumberFormatException e ) {
                final String msg = "parameter '" + paramName + "' not valid";

                LOGGER.fatal( "*** " + msg, e );

                throw new ServletException( msg, e );
            }
        }
    }
}

