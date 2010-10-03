package alpha.cx.ath.choisnet.net.dhcp;

import java.net.InetAddress;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class DHCPSimpleClient extends Thread
{

    private final DHCPSocket bindDHCPSocket;
    private final Random ranXid = new Random();
    private boolean running;
    private final DHCPParameters dhcpParameters;

    public DHCPSimpleClient(DHCPSocket dhcpSocket, DHCPParameters dhcpParameters, String threadName)
    {
        bindDHCPSocket = dhcpSocket;

        this.dhcpParameters = dhcpParameters;

        running = true;

        super.setName(threadName);
    }

    public void run()
    {
        try {
            DHCPMessage receivedMessage = sendDiscover();

            do {
                if(!running) {
                    break;
                }

                DHCPParameters params      = receivedMessage.getDHCPParameters();
                byte           messageType = params.getOptionAsByte((byte)53);

                DHCPSimpleClient.trace("<< receving <<", receivedMessage);

                switch(messageType) {
                case 2:
                {
                    String ip = DHCPParameters.ip4AddrToString(params.getYIAddr());
                    System.out.println(
                            (new StringBuilder())
                                .append(getName())
                                .append(" received a DHCPOFFER for ")
                                .append(ip)
                                .toString()
                                );

                    receivedMessage = sendRequest(receivedMessage);
                    break;
                }
                case 5:
                {
                    long    t1 = DHCPParameters.byteToLong(params.getOption((byte)58));
                    long    t2 = DHCPParameters.byteToLong(params.getOption((byte)59));
                    String  ip = DHCPParameters.ip4AddrToString(params.getYIAddr());

                    System.out.println(
                            (new StringBuilder())
                                .append(getName())
                                .append(" received an DHCPACK and a leasetime.")
                                .toString()
                                );
                    System.out.println(
                            (new StringBuilder())
                            .append("Binding to IP address: ")
                            .append(ip)
                            .toString()
                            );
                    System.out.println(
                            (new StringBuilder())
                                .append("Goodnight for ")
                                .append(t1)
                                .append(" seconds (t1/t2)=(")
                                .append(t1)
                                .append("/")
                                .append(t2)
                                .append(")")
                                .toString()
                                );

                    t1 = 15L;
                    mySleepInSec(t1);

                    System.out.println(
                            (new StringBuilder())
                                .append(getName())
                                .append(" sending ReNew Message to server...")
                                .toString()
                                );

                    receivedMessage = reNew(receivedMessage);
                    break;
                }
                case 6:
                {
                    System.out.println(
                            (new StringBuilder())
                                .append(getName())
                                .append(" Revieded DHCPNAK... ")
                                .toString()
                                );

                    receivedMessage = sendDiscover();
                    break;
                }
                }
            } while(true);
        }
        catch(java.io.IOException e) {
            System.err.println(e);

            e.printStackTrace();
        }
    }

    private void mySleepInSec(long seconds)
    {
        try {
//            DHCPSimpleClient _tmp = this;
            DHCPSimpleClient.sleep(1000L * seconds);
        }
        catch(InterruptedException ignore) {

        }
    }

    private DHCPMessage sendDiscover()
        throws java.io.IOException
    {
        DHCPMessage    messageOut = new DHCPMessage(dhcpParameters.getClone());
        DHCPParameters params     = messageOut.getDHCPParameters();

        params.setOp((byte)1);
        params.setXId(ranXid.nextInt());

        DHCPSimpleClient.trace(">> Sending >> DHCPDISCOVER", messageOut);

        bindDHCPSocket.send(messageOut);

        DHCPMessage messageIn = new DHCPMessage();

        for(boolean sentinal = true; sentinal;) {
            if(bindDHCPSocket.receive(messageIn)) {
                if(messageOut.isSameThread(messageIn)) {
                    sentinal = false;
                }
                else {
                    DHCPSimpleClient.trace("<< receving ERROR << DHCPDISCOVER << ERROR1 <<", messageIn);

                    bindDHCPSocket.send(messageOut);
                }
            }
            else  {
                bindDHCPSocket.send(messageOut);
            }
        }

        return messageIn;
    }

    private DHCPMessage sendRequest(DHCPMessage lastReceivedDHCPMessage)
        throws java.net.UnknownHostException, java.io.IOException
    {
        DHCPMessage    newDHCPMessage = lastReceivedDHCPMessage.getClone();
        DHCPParameters params         = dhcpParameters.getClone();

        params.setOptions(newDHCPMessage.getDHCPParameters());
        newDHCPMessage.setDHCPParameters(params);

        params.setOp((byte)1);
        params.setOption((byte)53, (byte)3);
        params.setOption((byte)50, params.getYIAddr());

        DHCPSimpleClient.trace(">> DHCPOFFER >>", newDHCPMessage);

        bindDHCPSocket.send(newDHCPMessage);

        System.out.print(
                (new StringBuilder())
                    .append(getName())
                    .append(" sending DHCPREQUEST for ")
                    .append(DHCPParameters.ip4AddrToString(params.getOption((byte)50)))
                    .toString()
                    );

        DHCPMessage messageIn = new DHCPMessage();

        for(boolean sentinal = true; sentinal;) {
            if(bindDHCPSocket.receive(messageIn)) {
                if(params.getXId() == messageIn.getDHCPParameters().getXId()) {
                    sentinal = false;
                }
                else {
                    bindDHCPSocket.send(newDHCPMessage);
                }
            }
            else {
                bindDHCPSocket.send(newDHCPMessage);
            }
        }

        return messageIn;
    }

    private DHCPMessage reNew(DHCPMessage offerMessageIn)
        throws java.net.UnknownHostException, java.net.SocketException, java.io.IOException
    {
        DHCPParameters params   = offerMessageIn.getDHCPParameters();
        String         serverIP = DHCPParameters.ip4AddrToString(params.getSIAddr());

        params.setOp((byte)1);
        params.setOption((byte)53, (byte)3);
        params.setCIAddr(params.getYIAddr());

        int  soTimeout = bindDHCPSocket.getSoTimeout() / 1000;
        long t1        = DHCPParameters.byteToLong(params.getOption((byte)58));
        long t2        = DHCPParameters.byteToLong(params.getOption((byte)59));
        int  elpstime  = 1;

        t2 = 15L;

        bindDHCPSocket.send(offerMessageIn);

        DHCPMessage messageIn = new DHCPMessage(InetAddress.getByName(serverIP), 67);

        boolean sentinal = true;

        do {
            if(!sentinal) {
                break;
            }
            if((long)(elpstime * soTimeout) + t1 >= t2) {
                System.out.print(getName());
                System.out.println(" rebinding, T1 has ran out...");

                messageIn = reBind(offerMessageIn);
                break;
            }

            if(bindDHCPSocket.receive(messageIn)) {
                sentinal = false;
                break;
            }

            bindDHCPSocket.send(offerMessageIn);
            elpstime++;
        } while(true);

        return messageIn;
    }

    private DHCPMessage reBind(DHCPMessage offerMessageIn)
        throws java.net.SocketException, java.io.IOException
    {
        DHCPParameters params = offerMessageIn.getDHCPParameters();

        params.setOp((byte)1);
        params.setOption((byte)53, (byte)3);
        params.setCIAddr(params.getYIAddr());

        long leaseTime = DHCPParameters.byteToLong(params.getOption((byte)51));
        long t2        = DHCPParameters.byteToLong(params.getOption((byte)59));
        int  so_timeout = bindDHCPSocket.getSoTimeout() / 1000;
        int  elpstime = 1;

        bindDHCPSocket.send(offerMessageIn);

        DHCPMessage messageIn = new DHCPMessage();
        boolean     sentinal  = true;

        do {
            if(!sentinal) {
                break;
            }

            if((long)(elpstime * so_timeout) + t2 >= leaseTime)  {
                System.out.print(getName());
                System.out.print(" is sending DHCPRELEASE, T2 has ran out ");
                System.out.println("shuttingdown.");

                sendRelease(offerMessageIn);

                break;
            }

            if(bindDHCPSocket.receive(messageIn)) {
                if(offerMessageIn.isSameThread(messageIn)) {
                    sentinal = false;
                }
                else {
                    bindDHCPSocket.send(offerMessageIn);
                    elpstime++;
                }
            }
            else {
                bindDHCPSocket.send(offerMessageIn);
                elpstime++;
            }
        } while(true);

        return messageIn;
    }

    private void sendRelease(DHCPMessage inOfferMessage)
        throws java.io.IOException
    {
        DHCPParameters params = inOfferMessage.getDHCPParameters();

        params.setOp((byte)1);
        params.setOption((byte)53, (byte)7);

        bindDHCPSocket.send(inOfferMessage);
        running = false;
    }

    public static final void trace(String messageName, DHCPMessage dhcpMessage)
    {
        System.out.println(
            (new StringBuilder())
                .append(" --- ")
                .append(messageName)
                .append(" : --- BEGIN ---")
                .toString()
                );
        System.out.println(dhcpMessage.toString());
        System.out.println(
             (new StringBuilder())
                 .append(" --- ")
                 .append(messageName)
                 .append(" : --- E N D ---")
                 .toString()
                 );
    }

    public static final byte[] addrToByte(String addr)
    {
        StringTokenizer token     = new StringTokenizer(addr, ":-");
        byte[]          outHwaddr = new byte[16];

        for(int i = 0; i < 6;) {
            int temp = Integer.parseInt(token.nextToken(), 16);

            outHwaddr[i++] = (byte)temp;
        }

        return outHwaddr;
    }
}
