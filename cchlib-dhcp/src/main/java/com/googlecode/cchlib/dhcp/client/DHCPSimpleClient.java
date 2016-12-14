package com.googlecode.cchlib.dhcp.client;

/*
 ** -----------------------------------------------------------------------
 **  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
 **                      Adapté du code de Jason Goldschmidt and Nick Stone
 **                      et basé sur les RFCs 1700, 2131 et 2132
 **                      http://www.themanualpage.org/dhcp/
 ** -----------------------------------------------------------------------
 */

import com.googlecode.cchlib.dhcp.DHCPParameters;
import com.googlecode.cchlib.dhcp.logger.DHCPLogger;

public class DHCPSimpleClient
{
    private final DHCPSimpleClientRunner clientRunner;

    public DHCPSimpleClient(
        final DHCPSocketBuilder socketBuilder,
        final DHCPParameters    dhcpParameters,
        final DHCPLogger        logger
        )
    {
        this.clientRunner = new DHCPSimpleClientRunner(
                socketBuilder,dhcpParameters,logger
                );
    }

    public void start()
    {
        if( this.clientRunner.isRunning() ) {
            throw new IllegalStateException( "Already running" );
        }

        this.clientRunner.setRunning( true );

        final Thread thread = new Thread( this.clientRunner );
        //thread.setDaemon( isDaemon ); -
        thread.start();
    }
}
