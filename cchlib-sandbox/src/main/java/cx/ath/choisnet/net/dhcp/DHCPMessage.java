/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPMessage.java
** Description   :
**
**  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
**                      Adapt� du code de Jason Goldschmidt and Nick Stone
**                      edu.bucknell.net.JDHCP.DHCPMessage
**                      http://www.eg.bucknell.edu/~jgoldsch/dhcp/
**                      et bas� sur les RFCs 1700, 2131 et 2132
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPMessage
**
*/
package cx.ath.choisnet.net.dhcp;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
** This class represents a DHCP Message.
**
**
**
** @author Jason Goldschmidt and Nick Stone
** @author Claude CHOISNET
** @version 3.02.014
*/
public class DHCPMessage
{
/** */
public static final String BROADCAST_IP_ADDR = "255.255.255.255";

/**
** @see #BROADCAST_IP_ADDR
*/
public static final InetAddress BROADCAST_ADDR;

static {
 try {
    //
    // broadcast address(by default)
    //
    BROADCAST_ADDR = InetAddress.getByName( BROADCAST_IP_ADDR );
    }
 catch( java.net.UnknownHostException e ) {
    throw new RuntimeException( e );
    }
}

/** port of the servername */
private int messagePort;

/** InetAddress of the servername */
private InetAddress messageInetAddress;

/** */
private DHCPParameters dhcpParameters;

/**
** Creates a DHCPMessage object
** <br/>
** initializes the object, sets the host to a specified host name,
** and binds to a specified port.
**
** @param serverInetAddress the host InetAddress
** @param port              the port number
** @param dhcpParameters    A DHCPParameters object
*/
public DHCPMessage( // ----------------------------------------------------
    final InetAddress       serverInetAddress,
    final int               port,
    final DHCPParameters    dhcpParameters
    )
{
 this.messageInetAddress    = serverInetAddress;
 this.messagePort           = port;
 this.dhcpParameters        = dhcpParameters;
}

/**
** Creates a DHCPMessage object
** <br/>
** initializes the object, sets the host to a specified host name,
** and binds to a specified port.
**
** @param serverInetAddress the host InetAddress
** @param port              the port number
*/
public DHCPMessage( // ----------------------------------------------------
    final InetAddress   serverInetAddress,
    final int           port
    )
{
 this( serverInetAddress, port, new DHCPParameters() );
}

/**
** Creates a DHCPMessage object
** <br/>
** initializes the object, sets the host to the broadcast address,
** and binds to a specified port.
**
** @param dhcpParameters    A DHCPParameters object
*/
public DHCPMessage( final DHCPParameters dhcpParameters ) // --------------
{
 this( BROADCAST_ADDR, DHCPSocket.SERVER_PORT, dhcpParameters );
}

/**
** Creates a DHCPMessage object
** <br/>
** initializes the object, sets the host to the broadcast address,
** the local subnet, binds to the default server port.
*/
public DHCPMessage() // ---------------------------------------------------
{
 this( new DHCPParameters() );
}

/**
** Set message destination port.
**
** @param inPortNum port on message destination host
**
** @return current 'this' object
*/
public DHCPMessage setPort( final int inPortNum ) // ----------------------
{
 this.messagePort = inPortNum;

 return this;
}

/**
** Get message destination port
**
** @return an interger representation of the message destination port
*/
public int getPort() // ---------------------------------------------------
{
 return this.messagePort;
}

/**
** Set message InetAddress
**
** @return current 'this' object
*/
public DHCPMessage setInetAddress( final InetAddress messageInetAddress ) // -----
{
 this.messageInetAddress = messageInetAddress;

 return this;
}

/**
** Get message InetAddress
**
** @return a {@link InetAddress} representing the message destination server
*/
public InetAddress getInetAddress() // ------------------------------------
{
 return this.messageInetAddress;
}

/**
**
*/
public DatagramPacket toDatagramPacket() // -------------------------------
{
 final byte[] data = this.getDHCPParameters().toByteArray();

 return new DatagramPacket(
                data,
                data.length,
                this.getInetAddress(),
                this.getPort()
                );
}

/**
** Set message {@link DHCPParameters}
**
** @param dhcpParameters a valid {@link DHCPParameters} object
**
*/
public void setDHCPParameters( final DHCPParameters dhcpParameters ) // ---
{
 this.dhcpParameters = dhcpParameters;
}

/**
** Set message {@link DHCPParameters} from an other {@link DHCPMessage}
**
** @param anOtherDHCPMessage an other {@link DHCPMessage}
**
*/
public void setDHCPParameters( // -----------------------------------------
    final DHCPMessage anOtherDHCPMessage
    )
{
 this.setDHCPParameters(
        anOtherDHCPMessage.getDHCPParameters().getClone()
        );
}

/**
** Get message DHCPParameters
**
** @return a {@link DHCPParameters}
*/
public DHCPParameters getDHCPParameters() // ------------------------------
{
 return this.dhcpParameters;
}

/**
** Find if messages have same xid value
**
** @return a true if boot message have same xid value
*/
public boolean isSameThread( final DHCPMessage aOtherDHCPMessage ) // -----
{
 return this.getDHCPParameters().getXId() == aOtherDHCPMessage.getDHCPParameters().getXId();
}

/**
** Return message type value
**
** @return a byte with in value of message type
*/
public byte getMessageType() // -------------------------------------------
{
 return this.getDHCPParameters().getOptionAsByte( DHCPOptions.MESSAGE_TYPE );
}

/**
**
public DHCPMessage getClone() // ---------------------------------------
{
 final DHCPMessage copy = new DHCPMessage();

 copy.messageInetAddress    = this.messageInetAddress;
 copy.messagePort           = this.messagePort;
 copy.dhcpParameters        = this.dhcpParameters.getClone();

 return copy;
}
*/

/**
**
*/
public String toString() // -----------------------------------------------
{
 final StringBuilder sb = new StringBuilder();

 sb.append( super.toString() + "\n" );
 sb.append( this.getDHCPParameters().toString() );

 return sb.toString();
}

/**
**
*/
public String toHexString() // --------------------------------------------
{
 return this.getDHCPParameters().toHexString();
}

} // class
