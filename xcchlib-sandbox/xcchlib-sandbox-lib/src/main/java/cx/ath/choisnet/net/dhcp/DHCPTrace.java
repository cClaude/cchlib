/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPTrace.java
** Description   :
**
**  3.02.040 2006.09.05 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPTrace
**
*/
package cx.ath.choisnet.net.dhcp;

/**
**
** @author Claude CHOISNET
** @since   3.02.040
** @version 3.02.040
*/
@Deprecated // TODO REMOVE THIS
public class DHCPTrace
{

/**
**
*/
public final static void println( // --------------------------------------
    final String message
    )
{
 System.out.println( message );
}

/**
**
*/
public final static void println( // --------------------------------------
    final String        messageName,
    final DHCPMessage   dhcpMessage
    )
{
 System.out.println( " --- " + messageName + " : --- BEGIN ---" );
 System.out.println( dhcpMessage.toString() );
// System.out.println( " --- " + messageName + " : --- HEXA! ---" );
// System.out.println( dhcpMessage.toHexString() );
 System.out.println( " --- " + messageName + " : --- E N D ---" );
}

} // class
