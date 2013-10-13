/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPSimpleClient.java
** Description   :
**
**  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
**                      Adapté du code de Jason Goldschmidt and Nick Stone
**                      et basé sur les RFCs 1700, 2131 et 2132
**                      http://www.themanualpage.org/dhcp/
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPSimpleClient
**
*/
package cx.ath.choisnet.net.dhcp;

import java.net.InetAddress;
import java.util.Random;
import java.util.StringTokenizer;

/**
**
** @author Claude CHOISNET
** @version 3.02.014
*/
public class DHCPSimpleClient
    extends Thread
{
/**
**
*/
private final DHCPSocket bindDHCPSocket;

/**
**
*/
private final Random ranXid;

/**
**
*/
private boolean running;

/**
** Paramètres initiaux
*/
private final DHCPParameters dhcpParameters;

/**
** Dernier message reçu.
*/
private DHCPMessage dhcpMessageReceived;

/**
**
*/
public DHCPSimpleClient( // -----------------------------------------------
    final DHCPSocket        dhcpSocket,
    final DHCPParameters    dhcpParameters,
    final String            threadName
    )
{
 this.bindDHCPSocket    = dhcpSocket;
 this.dhcpParameters    = dhcpParameters;
 this.ranXid            = new Random();
 this.running           = true;

 // set thread name
 super.setName( threadName );
}

/**
**
*/
public void run() // ------------------------------------------------------
{
 this.dhcpMessageReceived = new DHCPMessage();

 try {
    sendDHCPDISCOVER();

    while( this.running ) {
        final byte messageType = this.dhcpMessageReceived.getMessageType();

        DHCPTrace.println( "<< receving <<", this.dhcpMessageReceived );

        switch( messageType ) {

            case DHCPOptions.MESSAGE_TYPE_DHCPOFFER:
                {
                String ip = DHCPParameters.ip4AddrToString( this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

                DHCPTrace.println( getName() + " received a DHCPOFFER for " + ip );

                sendRequest();
                }
                break;

            case DHCPOptions.MESSAGE_TYPE_DHCPACK:
                {
                long    t1 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T1_TIME ) );
                long    t2 = DHCPParameters.byteToLong( this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T2_TIME ) );
                String  ip = DHCPParameters.ip4AddrToString( this.dhcpMessageReceived.getDHCPParameters().getYIAddr() );

                DHCPTrace.println( getName() + " received an DHCPACK and a leasetime." );
                DHCPTrace.println( "Binding to IP address: " + ip );

                DHCPTrace.println(
                        "Goodnight for "
                            + t1
                            + " seconds (t1/t2)=(" + t1 + "/" + t2 + ")"
                            );

                t1 = 15; // *******************

                sleepFor( t1 );

                DHCPTrace.println( getName() + " sending ReNew Message to server..." );

                reNew();
                }
                break;

            case DHCPOptions.MESSAGE_TYPE_DHCPNAK:
                DHCPTrace.println( getName() + " Revieded DHCPNAK... " );

                sendDHCPDISCOVER();
                break;

            default:
                break;
            }
        }
    }
 catch( java.io.IOException e ) {
    System.err.println( e );

    e.printStackTrace();
    }

}

/**
**
*/
protected void sleepFor( final long seconds ) // --------------------------
{
 try {
    super.sleep( 1000 * seconds );
    }
 catch( java.lang.InterruptedException ignore ) {
    // ignore : continue
    }
}

/**
**
*/
protected DHCPParameters getDHCPParameters() // ---------------------------
{
 return this.dhcpParameters;
}

/**
** <p>dhcpclient message send/recieve functions</p>
**
** Sends DHCP Message
**
*/
public void send( // ------------------------------------------------------
    final DHCPMessage dhcpMessageSend
    )
    throws java.io.IOException
{
 boolean sentinal = true;

 this.bindDHCPSocket.send( dhcpMessageSend );

 while( sentinal ) {
     if( this.bindDHCPSocket.receive( this.dhcpMessageReceived ) ) {

        if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
            //
            // C'est bien la réponse à notre message...
            //
            sentinal = false;
            }
        else {
            DHCPTrace.println( "<< receving ERROR <<", this.dhcpMessageReceived );

            this.bindDHCPSocket.send( dhcpMessageSend );
            }
        }
    else {
        //
        // Time out
        //
        bindDHCPSocket.send( dhcpMessageSend );
        }

    } // while
}

/**
** <p>dhcpclient message send/recieve functions</p>
**
** Sends DHCP Message
**
*/
public boolean send( // ---------------------------------------------------
    final DHCPMessage   dhcpMessageSend,
    final long          t1,
    final long          t2
    )
    throws java.io.IOException
{
 final int  soTimeout   = this.bindDHCPSocket.getSoTimeout() / 1000;
 int        elpstime    = 1;
 boolean    sentinal    = true;

 // send DHCPREQUEST
 this.bindDHCPSocket.send( dhcpMessageSend );

 while( sentinal ) {
    if( ((elpstime * soTimeout) + t1)  >=  t2 ) {
        return false; // TIME OUT for anwser
        }

    if( this.bindDHCPSocket.receive( this.dhcpMessageReceived ) ) {

        if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
            sentinal = false;
            }
        else {
            this.bindDHCPSocket.send( dhcpMessageSend );
            elpstime++;
            }

        }
    else {
        //
        // Time out
        //
        this.bindDHCPSocket.send( dhcpMessageSend );

        elpstime++;
        }
    }

 return true;
}


/**
** <p>dhcpclient message send/recieve functions</p>
**
** Sends DHCP Discover Message and returns the recieved Offer Message
**
**
*/
protected void sendDHCPDISCOVER() // ------------------------------------------
    throws java.io.IOException
{
 //
 // Create a new DHCPMessage (form default parameters)
 //
 final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

 // setOp Method being used
 dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

 // should be a random int
 dhcpMessageSend.getDHCPParameters().setXId( ranXid.nextInt() );

 DHCPTrace.println( ">> Sending >> DHCPDISCOVER", dhcpMessageSend );

 // send DHCPDISCOVER
 send( dhcpMessageSend );

// this.bindDHCPSocket.send( dhcpMessageSend );
//
// boolean sentinal = true;
//
// while( sentinal ) {
//     if( this.bindDHCPSocket.receive( this.dhcpMessageReceived ) ) {
//
//        if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
//            //
//            // C'est bien la réponse à notre message...
//            //
//            sentinal = false;
//            }
//        else {
//            DHCPTrace.println( "<< receving ERROR << DHCPDISCOVER << ERROR1 <<", this.dhcpMessageReceived );
//
//            this.bindDHCPSocket.send( dhcpMessageSend );
//            }
//        }
//    else {
//        //
//        // Time out
//        //
//        bindDHCPSocket.send( dhcpMessageSend );
//        }
//    } // while
}

/**
** Sends DHCPREQUEST Message and returns server message
*/
protected void sendRequest() // -------------------------------------------
    throws
        java.net.UnknownHostException,
        java.io.IOException
{
 //
 // Create a new DHCPMessage (from default parameters)
 //
 final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

 // setup message to send a DCHPREQUEST
 dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST ); // 1

 // change message type
 dhcpMessageSend.getDHCPParameters().setOption(
                    DHCPOptions.MESSAGE_TYPE,
                    DHCPOptions.MESSAGE_TYPE_DHCPREQUEST
                    );

 // set new IP value
 dhcpMessageSend.getDHCPParameters().setOption(
                        DHCPOptions.REQUESTED_IP,
                        this.dhcpMessageReceived.getDHCPParameters().getYIAddr()
                        );

 DHCPTrace.println( ">> DHCPOFFER >>", dhcpMessageSend );

 System.out.print(
        this.getName()
            + " sending DHCPREQUEST for "
            + DHCPParameters.ip4AddrToString(
                    dhcpMessageSend.getDHCPParameters().getOption( DHCPOptions.REQUESTED_IP )
                    )
        );

 send( dhcpMessageSend );

// this.bindDHCPSocket.send( dhcpMessageSend );
//
// boolean sentinal = true;
//
// while( sentinal ) {
//    if( this.bindDHCPSocket.receive( this.dhcpMessageReceived ) ) {
//
//        if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
//            //
//            // C'est bien la réponse à notre message...
//            //
//            sentinal = false;
//            }
//        else {
//            DHCPTrace.println( "<< receving ERROR << DHCPOFFER << ERROR1 <<", this.dhcpMessageReceived );
//
//            this.bindDHCPSocket.send( dhcpMessageSend );
//            }
//        }
//    else {
//        //
//        // Time out
//        //
//        bindDHCPSocket.send( dhcpMessageSend );
//        }
//    }
}

/**
** Sends DHCPRENEW message and returns server message
*/
protected void reNew() // -------------------------------------------------
    throws
        java.net.UnknownHostException,
        java.net.SocketException,
        java.io.IOException
{
 //
 // Create a new DHCPMessage (from default parameters)
 //
 final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

 // setup message to send a DCHPREQUEST
 dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

 // change message type
 dhcpMessageSend.getDHCPParameters().setOption(
            DHCPOptions.MESSAGE_TYPE,
            DHCPOptions.MESSAGE_TYPE_DHCPREQUEST
            );

 // must set ciaddr
 dhcpMessageSend.getDHCPParameters().setCIAddr(
            this.dhcpMessageReceived.getDHCPParameters().getYIAddr()
            );

 final long t1  = DHCPParameters.byteToLong(
                            this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T1_TIME )
                            );
 final long t2  = DHCPParameters.byteToLong(
                            this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T2_TIME )
                            );

 final String dhcpServer = DHCPParameters.ip4AddrToString(
                            this.dhcpMessageReceived.getDHCPParameters().getSIAddr()
                            );

// int    soTimeout   = this.bindDHCPSocket.getSoTimeout() / 1000;
// int    elpstime    = 1;

 dhcpMessageSend.setInetAddress( InetAddress.getByName( dhcpServer ) );

 if( !send( dhcpMessageSend, t1, 15 /*t2*/ ) ) {
    DHCPTrace.println( this.getName() + " rebinding, T1 has ran out... " + dhcpServer );

    reBind();
    }
//
// // send DHCPREQUEST
// this.bindDHCPSocket.send( dhcpMessageSend );
//
// boolean sentinal = true;
//
// while( sentinal ) {
//    if( ((elpstime * soTimeout) + t1)  >=  t2 ) {
//        DHCPTrace.println( this.getName() + " rebinding, T1 has ran out... " + dhcpServer );
//
//        reBind();
//        break;
//        }
//
//    if( this.bindDHCPSocket.receive( this.dhcpMessageReceived ) ) {
//        sentinal = false;
//        break;
//        }
//    else {
//        //
//        // Time out
//        //
//        this.bindDHCPSocket.send( dhcpMessageSend );
//        elpstime++;
//        }
//    }
}

/**
** Sends DHCPREBIND message, returns server message
*/
protected void reBind() // ------------------------------------------------
    throws
        java.net.SocketException,
        java.io.IOException
{
 //
 // Create a new DHCPMessage (form default parameters)
 //
 final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

 // setOp Method being used
 dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

 // change message type
 dhcpMessageSend.getDHCPParameters().setOption(
                    DHCPOptions.MESSAGE_TYPE,
                    DHCPOptions.MESSAGE_TYPE_DHCPREQUEST
                    );

 // must set ciaddr
 dhcpMessageSend.getDHCPParameters().setCIAddr(
            this.dhcpMessageReceived.getDHCPParameters().getYIAddr()
            );

 final long leaseTime   = DHCPParameters.byteToLong(
                            this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.LEASE_TIME )
                            );
 final long t2          = DHCPParameters.byteToLong(
                            this.dhcpMessageReceived.getDHCPParameters().getOption( DHCPOptions.T2_TIME )
                            );
// int    so_timeout  = this.bindDHCPSocket.getSoTimeout() / 1000;
// int    elpstime    = 1;

 if( !send( dhcpMessageSend, t2, /*leaseTime*/ 20 ) ) {
        DHCPTrace.println( this.getName() + " is sending DHCPRELEASE, T2 has ran out " );
        DHCPTrace.println( "shuttingdown." );

        sendRelease();
    }
// // send DHCPREQUEST
// this.bindDHCPSocket.send( dhcpMessageSend );
//
// boolean sentinal = true;
//
// while( sentinal ) {
//    if( ((elpstime * so_timeout) + t2)  >=  leaseTime ) {
//
//        DHCPTrace.println( this.getName() + " is sending DHCPRELEASE, T2 has ran out " );
//        DHCPTrace.println( "shuttingdown." );
//
//        sendRelease();
//        break;
//        }

//    if( this.bindDHCPSocket.receive( this.dhcpMessageReceived ) ) {
//
//        if( dhcpMessageSend.isSameThread( this.dhcpMessageReceived ) ) {
//            sentinal = false;
//            }
//        else {
//            this.bindDHCPSocket.send( dhcpMessageSend );
//            elpstime++;
//            }
//        }
//    else {
//        this.bindDHCPSocket.send( dhcpMessageSend );
//        elpstime++;
//        }
//
//    }
}

/**
** Sends DHCPRELEASE message, returns nothing
*/
protected void sendRelease() // -------------------------------------------
    throws java.io.IOException
{
 //
 // Create a new DHCPMessage (form default parameters)
 //
 final DHCPMessage dhcpMessageSend = new DHCPMessage( this.dhcpParameters.getClone() );

 // setup message to send a DCHPREQUEST
 dhcpMessageSend.getDHCPParameters().setOp( DHCPParameters.OP_OPTION_BOOTREQUEST );

 // change message type
 dhcpMessageSend.getDHCPParameters().setOption(
                    DHCPOptions.MESSAGE_TYPE,
                    DHCPOptions.MESSAGE_TYPE_DHCPRELEASE
                    );

 // send DHCPREQUEST
 this.bindDHCPSocket.send( dhcpMessageSend );

 this.running = false;
}


/**
** Converts the Chaddr String => byte[]
*/
public static final byte[] addrToByte( final String addr ) // -------------
{
 final StringTokenizer  token       = new StringTokenizer( addr, ":-" );
 final byte[]           outHwaddr   = new byte[ 16 ];

 int i = 0;

 while( i < 6 ) {
    int temp = Integer.parseInt( token.nextToken(), 16 );

    outHwaddr[ i++ ] = (byte) temp;
    }

 return outHwaddr;
}

} // class Client


