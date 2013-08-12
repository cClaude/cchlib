package alpha.dhcp;
//
//import java.net.BindException;
//import java.net.SocketException;
//import com.googlecode.cchlib.net.dhcp.DHCPParameters;
//import com.googlecode.cchlib.net.dhcp.DHCPSimpleClient;
//import com.googlecode.cchlib.net.dhcp.DHCPSocket;
//
///**
// *
// */
//public class DHCPClientSample
//{
//    private DHCPClientSample()
//    {
//    }
//
//    public static void main(String...args)
//    {
//        if( args.length == 0 ) {
//            System.out.println("Usage: dhcpclient <ethernet_addresss>\n\tie. dhcpclient 12:34:56:76:89:AB");
//            System.exit(1);
//            }
//
//        String hwaddr = args[0];
//
//        try {
//            @SuppressWarnings("resource")
//            DHCPSocket          mySocket = new DHCPSocket(68);
//            DHCPParameters      params   = DHCPClientSample.getDefaultDHCPParameters( DHCPSimpleClient.addrToByte(hwaddr) );
//            DHCPSimpleClient    aClient  = new DHCPSimpleClient( mySocket, params, hwaddr, System.out );
//
//            aClient.start();
//            }
//        catch( BindException e ) {
//            System.err.println("Socket Bind Error: ");
//            System.err.print("Another process is bound to this port\n");
//            System.err.print("or you do not have access to bind a process ");
//            System.err.println("to this port");
//            }
//        catch( SocketException e) {
//            System.err.println( "SocketException: " + e );
//            e.printStackTrace();
//            }
//    }
//
//    private static DHCPParameters getDefaultDHCPParameters(byte hwaddr[])
//    {
//        DHCPParameters dhcpParameters = new DHCPParameters();
//        dhcpParameters.setOp((byte)0);
//        dhcpParameters.setHType((byte)6);
//        dhcpParameters.setHLen((byte)6);
//        dhcpParameters.setHOps((byte)0);
//        dhcpParameters.setXId(0);
//        dhcpParameters.setSecs((short)0);
//        dhcpParameters.setFlags((short)0);
//        dhcpParameters.setChaddr(hwaddr);
//        dhcpParameters.setOption((byte)53, (byte)1);
//        dhcpParameters.setOption((byte)60, "usine");
//        dhcpParameters.setOption((byte)61, "usine");
//
//        return dhcpParameters;
//    }
//}
