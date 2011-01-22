/**
 * 
 */
package alpha.cx.ath.choisnet.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import cx.ath.choisnet.ToDo;

/**
 * Try to solve Ping in JAVA.
 * 
 * @author Claude CHOISNET
 */
//TODO: Not yet finish :(
//To request a Windows System you much install "Simple TCP/IP Services Service DLL" to
//on the requested computer 
@ToDo
public class Pinger extends Thread 
{
    /**
     * TODO: not yet implemented
     */
    public enum Method {
        /**
         * Use all methods in sequence if previous did
         * not work. (TODO)
         */
        AllInSequence,
        /**
         * Use {@link InetAddress#isReachable()}
         */
        InetAddress_isReachable,
    }
    /**
     * Default port number for {@link #helloPing(String)} : value = {@value}
     */
    public static final int DEFAULT_PORT = 7;
    /**
     * Default time out for requests (ms)
     */
    public static final int DEFAULT_TIMEOUT = 1500;
    
    private boolean pingResultReady = false;
    private boolean pingResultOk = false;

    private String host;
    private int timeout;

    public Pinger( String host )
    {
        this( host, DEFAULT_TIMEOUT );
    }
    
    public Pinger( String host, int timeout )
    {
        this.host    = host;
        this.timeout = timeout;
    }

    /**
     * 
     */
    public void run()
    {
        pingResultReady = false;

        try {
            pingResultOk = ping( host, timeout );
            } 
        catch( IOException e ) {
            e.printStackTrace();
        }

        this.pingResultReady = true;
    }

    /**
     * 
     * @return
     */
    public boolean isPingResultReady()
    {
        return this.pingResultReady;
    }
    
    /**
     * 
     * @return
     */
    public boolean isPingResultOk()
    {
        return this.pingResultOk;
    }

    /**
     * 
     * @param host
     * @param timeout
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    public static boolean ping(String host, int timeout )
        throws UnknownHostException, IOException
    {
        return ping( InetAddress.getByName( host ), timeout );
    }

    /**
     * 
     * @param inetAddress 
     * @param timeout the time, in milliseconds, before the call aborts 
     * @return a boolean indicating if the address is reachable.
     * @throws IOException 
     */
    public static boolean ping(InetAddress inetAddress, int timeout)
        throws IOException
    {
        return inetAddress.isReachable( timeout );
    }
    
    /**
     * 
     * @param host
     * @return
     * @throws PingerException 
     */
    public static boolean helloPing(String host)
        throws PingerException
    {
        return helloPing( host, DEFAULT_PORT );
    }
    
    /**
     * 
     * @param host
     * @param port
     * @return
     * @throws PingerException 
     */
    public static boolean helloPing(String host, int port) throws PingerException
    {
        InetAddress inetAddress;
        
        try {
            inetAddress = InetAddress.getByName( host );
        }
        catch( UnknownHostException e ) {
            throw new PingerHostException(
                    e.getMessage(),
                    e,
                    host
                    );
        }
        
        try {
            String str = sendUDPRequest(inetAddress, port, "Hello\n\r".getBytes() );

            if(str.equals("Hello")) {
                //System.out.println("Alive!");
                return true;
                }
            //System.out.println("Dead or echo port not responding");

            return false;
            }
        catch(java.io.IOException e) {
            throw new PingerIOException(
                    e.getMessage(),
                    e,
                    inetAddress
                    );
        }
        //return false;
    }

//    /**
//     * 
//     * @param host
//     * @param port
//     * @param bytes
//     * @return
//     * @throws UnknownHostException
//     * @throws IOException
//     */
//    public static String sendRequest(String host, int port, byte[] bytes)
//        throws UnknownHostException, IOException
//    {
//        return sendRequest( InetAddress.getByName( host ), port, bytes );
//    }
    
    /**
     * 
     * @param inetAddress
     * @param port
     * @param bytes
     * @return
     * @throws java.io.IOException
     */
    public static String sendTCPRequest(
            final InetAddress   inetAddress,
            final int           port, 
            final byte[]        sendData
            )
        throws java.io.IOException
    {
        Socket        socket = null;
        StringBuilder result = new StringBuilder();;

        try {
            socket = new Socket(inetAddress, port);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            bos.write(sendData, 0, sendData.length);

            String line;

            while((line = br.readLine()) != null) {
                result.append(line);
                }

            try {
                br.close();
                }
            catch( IOException ignore) {
                // Ignore
                }
            try {
                bos.close();
                }
            catch( IOException ignore) {
                // Ignore
                }
            }
        finally {
            if( socket != null ) {
                socket.close();
                }
            }

        return result.toString();
    }

    /**
     * 
     * @param inetAddress
     * @param port
     * @param bytes
     * @return
     * @throws java.io.IOException
     */
    public static String sendUDPRequest(
            final InetAddress   inetAddress,
            final int           port, 
            final byte[]        sendData
            )
        throws java.io.IOException
    {
        DatagramSocket  socket = null;
        StringBuilder   result = new StringBuilder();;

        try {
            //byte[] sendData     = new byte[1024];
            byte[] receiveData  = new byte[1024];

            //String sentence = inFromUser.readLine();
            //sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket( sendData, sendData.length, inetAddress, port );
            socket = new DatagramSocket( port, inetAddress );
            socket.send( sendPacket );

//            InetAddress serveur = InetAddress.getByName(argv[0]);
//            int length = argv[1].length();
//            byte buffer[] = argv[1].getBytes();
//            DatagramPacket dataSent = new DatagramPacket(buffer,length,serveur,ServeurEcho.port);
//            DatagramSocket socket = new DatagramSocket();
//            socket.send(dataSent);

            DatagramPacket receivePacket = new DatagramPacket( receiveData, receiveData.length );
            socket.receive(receivePacket);
            System.out.println("Data recieved : " + new String( receivePacket.getData()) );
            System.out.println("From : " + receivePacket.getAddress() + ":" + receivePacket.getPort() );

/*            
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive( receivePacket );
            
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);
//            clientSocket.close();
            /*
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            bos.write(bytes, 0, bytes.length);

            String line;

            while((line = br.readLine()) != null) {
                result.append(line);
                }

            try {
                br.close();
                }
            catch( IOException ignore) {
                // Ignore
                }
            try {
                bos.close();
                }
            catch( IOException ignore) {
                // Ignore
                }
*/                
            }
        finally {
            if( socket != null ) {
                socket.close();
                }
            }

        return result.toString();
    }
    
//    public static void main(String[] args) throws UnknownHostException, IOException
//    {
//        Pinger.helloPing( "127.0.0.1" );
//
//        Pinger.helloPing( "google.com" );
//        
//        Pinger.ping( "localhost", Pinger.DEFAULT_TIMEOUT );
//        Pinger.ping( "google.com", Pinger.DEFAULT_TIMEOUT );
//
//    }
//    public static void main(String[] argv) throws Exception
//    {
//        for( int i=0; i< 200; i++ ) {
//            System.out.println("reachable? "+i+" -> "+java.net.InetAddress.getByName("www.free.fr").isReachable(i));
//        }
//    }
}
