/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPMessageFactory.java
** Description   :
**
**  3.02.040 2006.09.05 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPMessageFactory
**
*/
package cx.ath.choisnet.net.dhcp;

import java.net.DatagramPacket;

/**
**
**
**
** @author Claude CHOISNET
** @since   3.02.040
** @version 3.02.040
*/
public class DHCPMessageFactory
{

/**
**
*/
public final static DHCPMessage newInstance( // ---------------------------
    final DatagramPacket data
    )
{
 DHCPParameters params  = DHCPParameters.newInstance( data.getData() );
 DHCPMessage    msg     = new DHCPMessage( params );

 return msg;
}

} // class

