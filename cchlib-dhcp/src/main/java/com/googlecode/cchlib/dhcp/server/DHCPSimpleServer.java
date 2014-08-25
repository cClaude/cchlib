package com.googlecode.cchlib.dhcp.server;

/*
 ** -----------------------------------------------------------------------
 **  3.02.040 2006.09.05 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 */

import com.googlecode.cchlib.dhcp.DHCPSocket;
import com.googlecode.cchlib.dhcp.logger.DHCPLogger;

/**
 **
 ** @author Claude CHOISNET
 ** @since 3.02.040
 */
public class DHCPSimpleServer {
    private final DHCPServerRunner  runner;
    private final String            threadName;
    private final boolean           isDaemon;

    public DHCPSimpleServer( // -----------------------------------------------
            final String        threadName,
            final DHCPLogger    logger,
            final boolean       isDaemon,
            final boolean       replyToClients
            )
    {
        this( threadName, DHCPSocket.SERVER_PORT, logger, isDaemon, replyToClients );
    }

    public DHCPSimpleServer( // -----------------------------------------------
            final String        threadName,
            final int           port,
            final DHCPLogger    logger,
            final boolean       isDaemon,
            final boolean       replyToClients
            )
    {
        this( new DHCPServerRunner( port, logger, replyToClients ), threadName, isDaemon );
    }

    public DHCPSimpleServer( // -----------------------------------------------
            final DHCPServerRunner  runner,
            final String            threadName,
            final boolean           isDaemon
            )
    {
        this.runner     = runner;
        this.threadName = threadName;
        this.isDaemon   = isDaemon;
    }

    public void start()
    {
        if( runner.isRunning() ) {
            throw new IllegalStateException( "Already running" );
        }

        runner.setRunning( true );

        final Thread thread = new Thread( this.runner, this.threadName );
        thread.setDaemon( isDaemon );
        thread.start();
    }

    public void stop()
    {
        if( ! runner.isRunning() ) {
            throw new IllegalStateException( "Not running" );
        }

        runner.setRunning( false );
    }
}
