package com.googlecode.cchlib.net.dhcp;
//
//import java.io.IOException;
//import java.io.PrintStream;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Random;
//import java.util.StringTokenizer;
//
///**
// * TODOC
// *
// */
//public class DHCPSimpleClient extends Thread
//{
//    private final DHCPSocket bindDHCPSocket;
//    private final Random ranXid = new Random();
//    private boolean running;
//    private final DHCPParameters dhcpParameters;
//    private PrintStream logger;
//
//    /**
//     * 
//     * @param dhcpSocket
//     * @param dhcpParameters
//     * @param threadName
//     * @param psLogger
//     */
//    public DHCPSimpleClient(
//        final DHCPSocket        dhcpSocket, 
//        final DHCPParameters    dhcpParameters, 
//        final String            threadName,
//        final PrintStream       psLogger
//        )
//    {
//        this.bindDHCPSocket = dhcpSocket;
//        this.dhcpParameters = dhcpParameters;
//        this.logger = psLogger;
//
//        super.setName(threadName);
//
//        this.running = true;
//    }
//
//    @Override
//    public void run()
//    {
//        try {
//            DHCPMessage receivedMessage = sendDiscover();
//
//            do {
//                if(!running) {
//                    break;
//                }
//
//                DHCPParameters params      = receivedMessage.getDHCPParameters();
//                byte           messageType = params.getOptionAsByte((byte)53);
//
//                this.trace("<< receving <<", receivedMessage);
//
//                switch(messageType) {
//                case 2:
//                {
//                    String ip = DHCPParameters.ip4AddrToString(params.getYIAddr());
//                    logger.println(
//                            (new StringBuilder())
//                                .append(getName())
//                                .append(" received a DHCPOFFER for ")
//                                .append(ip)
//                                .toString()
//                                );
//
//                    receivedMessage = sendRequest(receivedMessage);
//                    break;
//                }
//                case 5:
//                {
//                    long    t1 = DHCPParameters.byteToLong(params.getOption((byte)58));
//                    long    t2 = DHCPParameters.byteToLong(params.getOption((byte)59));
//                    String  ip = DHCPParameters.ip4AddrToString(params.getYIAddr());
//
//                    logger.println(
//                            (new StringBuilder())
//                                .append(getName())
//                                .append(" received an DHCPACK and a leasetime.")
//                                .toString()
//                                );
//                    logger.println(
//                            (new StringBuilder())
//                            .append("Binding to IP address: ")
//                            .append(ip)
//                            .toString()
//                            );
//                    logger.println(
//                            (new StringBuilder())
//                                .append("Goodnight for ")
//                                .append(t1)
//                                .append(" seconds (t1/t2)=(")
//                                .append(t1)
//                                .append('/')
//                                .append(t2)
//                                .append(')')
//                                .toString()
//                                );
//
//                    t1 = 15L;
//                    mySleepInSec(t1);
//
//                    logger.println(
//                            (new StringBuilder())
//                                .append(getName())
//                                .append(" sending ReNew Message to server...")
//                                .toString()
//                                );
//
//                    receivedMessage = reNew(receivedMessage);
//                    break;
//                }
//                case 6:
//                {
//                    logger.println(
//                            (new StringBuilder())
//                                .append(getName())
//                                .append(" Revieded DHCPNAK... ")
//                                .toString()
//                                );
//
//                    receivedMessage = sendDiscover();
//                    break;
//                }
//                }
//            } while(true);
//        }
//        catch( IOException e) {
//            logger.println(e);
//
//            e.printStackTrace( logger );
//        }
//    }
//
//    private void mySleepInSec(long seconds)
//    {
//        try {
////            DHCPSimpleClient _tmp = this;
//            DHCPSimpleClient.sleep(1000L * seconds);
//        }
//        catch(InterruptedException ignore) {
//
//        }
//    }
//
//    private DHCPMessage sendDiscover()
//        throws IOException
//    {
//        DHCPMessage    messageOut = new DHCPMessage(dhcpParameters.createClone());
//        DHCPParameters params     = messageOut.getDHCPParameters();
//
//        params.setOp((byte)1);
//        params.setXId(ranXid.nextInt());
//
//        this.trace(">> Sending >> DHCPDISCOVER", messageOut);
//
//        bindDHCPSocket.send(messageOut);
//
//        DHCPMessage messageIn = new DHCPMessage();
//
//        for(boolean sentinal = true; sentinal;) {
//            if(bindDHCPSocket.receive(messageIn)) {
//                if(messageOut.isSameThread(messageIn)) {
//                    sentinal = false;
//                }
//                else {
//                    this.trace("<< receving ERROR << DHCPDISCOVER << ERROR1 <<", messageIn);
//
//                    bindDHCPSocket.send(messageOut);
//                }
//            }
//            else  {
//                bindDHCPSocket.send(messageOut);
//            }
//        }
//
//        return messageIn;
//    }
//
//    private DHCPMessage sendRequest(DHCPMessage lastReceivedDHCPMessage)
//        throws UnknownHostException, IOException
//    {
//        DHCPMessage    newDHCPMessage = lastReceivedDHCPMessage.createClone();
//        DHCPParameters params         = dhcpParameters.createClone();
//        DHCPParameters params         = dhcpParameters.createClone();
//        DHCPOptionsProperties()
//        params.setOptions(newDHCPMessage.getDHCPParameters());
//        newDHCPMessage.setDHCPParameters(params);
//
//        params.setOp((byte)1);
//        params.setOption((byte)53, (byte)3);
//        params.setOption((byte)50, params.getYIAddr());
//
//        this.trace(">> DHCPOFFER >>", newDHCPMessage);
//
//        bindDHCPSocket.send(newDHCPMessage);
//
//        logger.print(
//                (new StringBuilder())
//                    .append(getName())
//                    .append(" sending DHCPREQUEST for ")
//                    .append(DHCPParameters.ip4AddrToString(params.getOption((byte)50)))
//                    .toString()
//                    );
//
//        DHCPMessage messageIn = new DHCPMessage();
//
//        for(boolean sentinal = true; sentinal;) {
//            if(bindDHCPSocket.receive(messageIn)) {
//                if(params.getXId() == messageIn.getDHCPParameters().getXId()) {
//                    sentinal = false;
//                    }
//                else {
//                    bindDHCPSocket.send(newDHCPMessage);
//                    }
//                }
//            else {
//                bindDHCPSocket.send(newDHCPMessage);
//                }
//            }
//
//        return messageIn;
//    }
//
//    private DHCPMessage reNew(final DHCPMessage offerMessageIn)
//        throws UnknownHostException, SocketException, IOException
//    {
//        DHCPParameters params   = offerMessageIn.getDHCPParameters();
//        String         serverIP = DHCPParameters.ip4AddrToString(params.getSIAddr());
//
//        params.setOp((byte)1);
//        params.setOption((byte)53, (byte)3);
//        params.setCIAddr(params.getYIAddr());
//
//        int  soTimeout = bindDHCPSocket.getSoTimeout() / 1000;
//        long t1        = DHCPParameters.byteToLong(params.getOption((byte)58));
//        long t2        = DHCPParameters.byteToLong(params.getOption((byte)59));
//        int  elpstime  = 1;
//
//        t2 = 15L;
//
//        bindDHCPSocket.send(offerMessageIn);
//
//        DHCPMessage messageIn = new DHCPMessage(InetAddress.getByName(serverIP), 67);
//
//        boolean sentinal = true;
//
//        do {
//            if(!sentinal) {
//                break;
//            }
//            if((long)(elpstime * soTimeout) + t1 >= t2) {
//                logger.print(getName());
//                logger.println(" rebinding, T1 has ran out...");
//
//                messageIn = reBind(offerMessageIn);
//                break;
//            }
//
//            if(bindDHCPSocket.receive(messageIn)) {
//                sentinal = false;
//                break;
//            }
//
//            bindDHCPSocket.send(offerMessageIn);
//            elpstime++;
//        } while(true);
//
//        return messageIn;
//    }
//
//    private DHCPMessage reBind(DHCPMessage offerMessageIn)
//        throws SocketException, IOException
//    {
//        DHCPParameters params = offerMessageIn.getDHCPParameters();
//
//        params.setOp((byte)1);
//        params.setOption((byte)53, (byte)3);
//        params.setCIAddr(params.getYIAddr());
//
//        long leaseTime = DHCPParameters.byteToLong(params.getOption((byte)51));
//        long t2        = DHCPParameters.byteToLong(params.getOption((byte)59));
//        int  so_timeout = bindDHCPSocket.getSoTimeout() / 1000;
//        int  elpstime = 1;
//
//        bindDHCPSocket.send(offerMessageIn);
//
//        DHCPMessage messageIn = new DHCPMessage();
//        boolean     sentinal  = true;
//
//        do {
//            if(!sentinal) {
//                break;
//            }
//
//            if((long)(elpstime * so_timeout) + t2 >= leaseTime)  {
//                logger.print(getName());
//                logger.print(" is sending DHCPRELEASE, T2 has ran out ");
//                logger.println("shuttingdown.");
//
//                sendRelease(offerMessageIn);
//
//                break;
//            }
//
//            if(bindDHCPSocket.receive(messageIn)) {
//                if(offerMessageIn.isSameThread(messageIn)) {
//                    sentinal = false;
//                }
//                else {
//                    bindDHCPSocket.send(offerMessageIn);
//                    elpstime++;
//                }
//            }
//            else {
//                bindDHCPSocket.send(offerMessageIn);
//                elpstime++;
//            }
//        } while(true);
//
//        return messageIn;
//    }
//
//    private void sendRelease(DHCPMessage inOfferMessage)
//        throws IOException
//    {
//        DHCPParameters params = inOfferMessage.getDHCPParameters();
//
//        params.setOp((byte)1);
//        params.setOption((byte)53, (byte)7);
//
//        bindDHCPSocket.send(inOfferMessage);
//        running = false;
//    }
//
//    public /*static*/ final void trace(
//        String      messageName, 
//        DHCPMessage dhcpMessage
//        )
//    {
//        logger.println(
//            (new StringBuilder())
//                .append(" --- ")
//                .append(messageName)
//                .append(" : --- BEGIN ---")
//                .toString()
//                );
//        logger.println(dhcpMessage.toString());
//        logger.println(
//             (new StringBuilder())
//                 .append(" --- ")
//                 .append(messageName)
//                 .append(" : --- E N D ---")
//                 .toString()
//                 );
//    }
//
//    public static final byte[] addrToByte(String addr)
//    {
//        StringTokenizer token     = new StringTokenizer(addr, ":-");
//        byte[]          outHwaddr = new byte[16];
//
//        for(int i = 0; i < 6;) {
//            int temp = Integer.parseInt(token.nextToken(), 16);
//
//            outHwaddr[i++] = (byte)temp;
//        }
//
//        return outHwaddr;
//    }
//}
