package com.googlecode.cchlib.net.dhcp.util0;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DHCPUtility {
    private DHCPUtility(){}

    /**
     *
     * @return Returns the MAC Address for the current host's network interface
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static byte[] getMacAddress() throws UnknownHostException, SocketException
    {
        byte[] mac = null;

        InetAddress address = InetAddress.getLocalHost();

        /*
         * Get NetworkInterface for the current host and then read the
         * hardware address.
         */
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        mac = ni.getHardwareAddress();

        return mac;
    }

    public static void printMacAddress()
    {
        printMacAddress( System.out );
    }
    public static void printMacAddress( PrintStream out )
    {
        try {
            InetAddress address = InetAddress.getLocalHost();

            /*
             * Get NetworkInterface for the current host and then read the
             * hardware address.
             */
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();

            out.print("Hardware Address for current adapter: ");

            /*
             * Extract each array of mac address and convert it to hexa with the
             * . * following format 08-00-27-DC-4A-9E.
             */
            for (int i = 0; i < mac.length; i++) {
                out.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
            }

            out.println();
        } catch (UnknownHostException e) {
            e.printStackTrace( out );
        } catch (SocketException e) {
            e.printStackTrace( out );
        }
    }

    /**
     * Converts a byte array to a BitSet
     * @param byteArray - the array of bytes to convert
     * @return the BitSet representation of a byte array
     */
    public static BitSet bytes2Bits(byte[] byteArray) {
        BitSet bits = new BitSet(8*byteArray.length);

        for (int i=0; i < byteArray.length; i++) {
                int temp = (byteArray[i] < 0 ? byteArray[i] + 256 : byteArray[i]);
                for (int j=7; j >= 0; j--) {
                        if (temp - Math.pow(2,j) >= 0) {
                                bits.flip(i*8+7-j);
                                /*System.out.println("flipping bit " + j + "("
                                                + Math.pow(2,j) +")"+ "from array element " + i
                                                + "(" + temp +")");*/
                                temp -= Math.pow(2,j);
                        }
                }
        }


        return bits;
    }

    public static String byteToHex( byte b ) {
        return Integer.toHexString( (int)(b & 0xff) );
    }

    //only works for 4 bytes
    public static byte[] inttobytes(int i){
        byte[] dword = new byte[4];
        dword[0] = (byte) ((i >> 24) & 0x000000FF);
        dword[1] = (byte) ((i >> 16) & 0x000000FF);
        dword[2] = (byte) ((i >> 8) & 0x000000FF);
        dword[3] = (byte) (i & 0x00FF);
        return dword;
    }

    public static int bytestoint(byte[] ba){
        int integer = 0;
        for (int i=0; i < ba.length; i++) {
                //System.out.printf("byte" + i + ": "+ (ba[i] & 0xff) + " ");
                integer += (ba[i] & 0xff) * Math.pow(2, 8*i);
        }
        //System.out.println("integer convesion: " + integer);
        assert(integer >= 0);
        return integer;
    }

    public static byte[] shorttobytes(short i){
        byte[] b = new byte[2];
        b[0] = (byte) ((i >> 8) & 0x000000FF);
        b[1] = (byte) (i & 0x00FF);
        return b;
    }

    /**
     * Converts the first 4 byte values of a byte array to an ip string.
     * @param ba - a byte array of 4 bytes
     * @return - IP String representation
     */
    public static String ip4ToString(byte[] ba) {
        assert ba.length == 4;
        return ip4ToString(ba[0], ba[1], ba[2], ba[3]);
    }

    /**
     * Converts 4 byte values to an ip string
     * @param a - 1st byte value
     * @param b - 2nd byte value
     * @param c - 3rd byte value
     * @param d - 4th byte value
     * @return - IP String representation
     */
    public static String ip4ToString(byte a, byte b, byte c, byte d) {
            return (a & 0xff) + "." + (b & 0xff) + "." + (c & 0xff) + "." + (d & 0xff);
    }

    public static String printString(byte[] ba) {
        StringBuilder str = new StringBuilder();
        
        for (int i=0; i < ba.length; i++) {
            if (ba[i] != 0) {
                str.append( (char) ba[i] );
            }
        }
        return str.toString();
    }

    public static byte[] stringToBytes(String str) {
            return str.getBytes();
    }

    public static String macToString(byte a, byte b, byte c, byte d, byte e, byte f) {
        //Ethernet MAC Address?
        return    DHCPUtility.byteToHex(a) + "-" +
                  DHCPUtility.byteToHex(b) + "-" +
                  DHCPUtility.byteToHex(c) + "-" +
                  DHCPUtility.byteToHex(d) + "-" +
                  DHCPUtility.byteToHex(e) + "-" +
                  DHCPUtility.byteToHex(f);
    }

    public static String macToString(byte[] ba) {
            assert ba.length == 6;
            return macToString(ba[0], ba[1], ba[2], ba[3], ba[4], ba[5]);
    }

    public static boolean isEqual(byte[] a, byte[] b) {
        boolean isEqual = true;
        for (int i=0; i < Math.min(a.length, b.length); i++) {
            if (a[i] != b[i]) {
                isEqual = false;
                break;
            }
        }
        return isEqual;
    }

    public static byte[] strToIP(String str) {
        Pattern regex =  Pattern.compile(".*?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}).*");
        Matcher m = regex.matcher(str);
        
        if( m.matches() ) {
            String[] ip   = m.group(1).split("\\.");
            byte[]   baIP = new byte[4];
            
            baIP[0] = new Byte((byte) Integer.parseInt(ip[0]));
            baIP[1] = new Byte((byte) Integer.parseInt(ip[1]));
            baIP[2] = new Byte((byte) Integer.parseInt(ip[2]));
            baIP[3] = new Byte((byte) Integer.parseInt(ip[3]));
            assert(DHCPUtility.ip4ToString(baIP).compareTo(m.group(1)) == 0) :
                    "IP conversion incorrect: " + m.group(1) +
                    " conversion: " + DHCPUtility.ip4ToString(baIP);
            return baIP;
            }
        else {
            return null;
            }
    }


}
