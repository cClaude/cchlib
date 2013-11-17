/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx\ath\choisnet\tools\servlets\InitServlet.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.00.000 2004.12.01 Claude CHOISNET
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.tools.servlets.InitServlet
 **
 */
package cx.ath.choisnet.tools.servlets;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;

/**
 ** <P>
 * <B>Servlet :</B> Servlet d'initialisation du DRS.
 * </P>
 **
 **
 ** @author Claude CHOISNET
 ** @version 1.00.000
 */
public class InitServlet extends HttpServlet {
    private static final long              serialVersionUID = 1L;
    private static final Logger            LOGGER           = Logger.getLogger( InitServlet.class );

    /** Parametre de la servlet */
    public final static String             DELAY            = "DELAY";

    /** Parametre de la servlet */
    public final static String             PERIOD           = "PERIOD";

    /** Param�tre de la servlet */
    public final static String             TASKLIST         = "TASK-LIST";

    /**
     **
     ** @see #TASKLIST
     */
    private List<InitServletTaskInterface> taskList         = new LinkedList<InitServletTaskInterface>();

    /**
     ** Delais avant la première execution de la liste des tâches.
     **
     ** @see #DELAY
     */
    private long                           delay;

    /**
     ** Delais en (ms) entre chaques executions de la liste des taches. <BR>
     ** Si cette valeur est égale à zéro ou négative, les taches différées ne
     * seront pas lancées.
     **
     ** @see #PERIOD
     */
    private long                           period;

    /** */
    private Timer                          timer            = null;

    /** */
    private boolean                        cancelTimer      = false;

    /**
     ** Initialisation des param�tres de la servlet
     */
    @Override
    public void init( // ------------------------------------------------------
            ServletConfig servletConfig ) throws ServletException
    {
        super.init( servletConfig );

        //
        // TASKLIST
        //
        String value = servletConfig.getInitParameter( TASKLIST );
        StringTokenizer classST = new StringTokenizer( value, ";, \t\n\r" );

        while( classST.hasMoreTokens() ) {
            String classNameBase = classST.nextToken() + "()";
            int end1 = classNameBase.indexOf( '(' );
            String className = classNameBase.substring( 0, end1 );
            int end2 = classNameBase.indexOf( ')' );
            String param = classNameBase.substring( end1 + 1, end2 );

            System.out.println( "param [" + param + "]" );

            try {
                InitServletTaskInterface instance;
                this.getClass();
                Class<? extends InitServletTaskInterface> aClass = Class
                        .forName( className ).asSubclass(
                                InitServletTaskInterface.class );

                if( param.length() > 0 ) {
                    Constructor<? extends InitServletTaskInterface> constructor = aClass
                            .getConstructor( String.class );

                    instance = constructor.newInstance( param );
                } else {
                    instance = aClass.newInstance();
                }

                instance.init( servletConfig );
                taskList.add( instance );
            }
            catch( ServletException e ) {
                throw e;
            }
            catch( Exception e ) {
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
        this.delay = getInitParameterAsLong( servletConfig, DELAY );

        if( this.delay < 0 ) {
            this.delay = 0;
        }

        //
        // PERIOD
        //
        this.period = getInitParameterAsLong( servletConfig, PERIOD );

        LOGGER.info( "delay      = " + this.delay );
        LOGGER.info( "delay sec  = " + (this.delay / 1000) );
        LOGGER.info( "period     = " + this.period );
        LOGGER.info( "period sec = " + (this.period / 1000) );

        if( period > 0 ) {
            this.timer = new Timer( true /* run as a daemon */);
            // this.timer = new Timer();

            final TimerTask aTimerTask = new TimerTask() {
                @Override
                public void run() // ------------------------------------------
                {
                    LOGGER.info( "run tasks BEGIN " + this.hashCode() );

                    for( Iterator<InitServletTaskInterface> iterator = taskList
                            .iterator(); iterator.hasNext(); ) {
                        InitServletTaskInterface taskInstance = iterator.next();

                        LOGGER.debug( "run tasks: "
                                + taskInstance.getTaskName() + " "
                                + this.hashCode() );
                        LOGGER.debug( "run tasks: @" + taskInstance.hashCode() );

                        if( cancelTimer ) {
                            break;
                        }

                        new Thread( taskInstance ).start();

                        if( !taskInstance.continueRunning() ) {
                            iterator.remove();
                        }
                    }

                    LOGGER.info( "run tasks END " + this.hashCode() );
                }
            };

            this.timer.schedule( aTimerTask, delay, period );
        }

        LOGGER.info( "init() - EXIT" );
    }

    @Override
    public void destroy() // --------------------------------------------------
    {
        LOGGER.info( "destroy() - BEGIN" );
        LOGGER.info( "destroy() - BEGIN" );
        LOGGER.info( "destroy() - BEGIN" );

        this.timer.cancel();
        this.cancelTimer = true;

        LOGGER.info( "destroy() - EXIT" );
        LOGGER.info( "destroy() - EXIT" );
        LOGGER.info( "destroy() - EXIT" );
    }

    public long getInitParameterAsLong( // ------------------------------------
            final ServletConfig servletConfig, final String paramName )
            throws ServletException
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
            catch( NumberFormatException e ) {
                final String msg = "parameter '" + paramName + "' not valid";

                LOGGER.fatal( "*** " + msg, e );

                throw new ServletException( msg, e );
            }
        }
    }
}

