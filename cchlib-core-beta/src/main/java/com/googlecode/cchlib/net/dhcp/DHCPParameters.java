package com.googlecode.cchlib.net.dhcp;

import com.googlecode.cchlib.NeedDoc;
import com.googlecode.cchlib.NeedTestCases;

/**
 *
 *
 */
@NeedDoc
@NeedTestCases
public class DHCPParameters
{
    public static final byte OP_OPTION_BOOTREQUEST = 1;
    public static final byte OP_OPTION_BOOTREPLY = 2;
    private byte op;
    private byte htype;
    private byte hlen;
    private byte hops;
    private int xid;
    private short secs;
    private short flags;
    private final byte[] ciaddr = new byte[4];
    private final byte[] yiaddr = new byte[4];
    private final byte[] siaddr = new byte[4];
    private final byte[] giaddr = new byte[4];
    private final byte[] chaddr = new byte[16];
    private final byte[] sname  = new byte[64];
    private final byte[] file   = new byte[128];
//    private AbstractDHCPOptions dhcpOptions;
//    protected /*transient*/ Object lock;
//
//    public DHCPParameters(AbstractDHCPOptions dhcpOptions)
//    {
//        this.lock        = new Object();
//        this.dhcpOptions = dhcpOptions;
//        //dhcpOptions = new DHCPOptions();
//    }
//
//    public byte[] toByteArray()
//    {
//       ByteArrayOutputStream outBStream;
//
//       synchronized( lock ) {
//            outBStream = new ByteArrayOutputStream();
//
//            try {
//                DataOutputStream outStream = new DataOutputStream(outBStream);
//
//                outStream.writeByte(op);
//                outStream.writeByte(htype);
//                outStream.writeByte(hlen);
//                outStream.writeByte(hops);
//                outStream.writeInt(xid);
//                outStream.writeShort(secs);
//                outStream.writeShort(flags);
//                outStream.write(ciaddr);
//                outStream.write(yiaddr);
//                outStream.write(siaddr);
//                outStream.write(giaddr);
//                outStream.write(chaddr);
//                outStream.write(sname);
//                outStream.write(file);
//
//                byte[] options = dhcpOptions.toByteArray();
//
//                outStream.write(options, 0, options.length);
//                }
//            catch( IOException shouldNotOccured ) {
//                throw new RuntimeException( shouldNotOccured );
//                }
//            }
//
//        return outBStream.toByteArray();
//    }
//
//    public static DHCPParameters newInstance(byte[] ibuf)
//    {
//        DHCPParameters message = new DHCPParameters( new DHCPOptionsProperties() ); // FIXME
//
//        message.init(ibuf);
//
//        return message;
//    }
//
//    private void init(byte[] ibuf)
//        throws java.lang.ArrayIndexOutOfBoundsException
//    {
//        synchronized(lock) {
//            ByteArrayInputStream bais = new ByteArrayInputStream(ibuf);
//            DataInputStream      dis  = new DataInputStream(bais);
//
//            try {
//                op = dis.readByte();
//                htype = dis.readByte();
//                hlen = dis.readByte();
//                hops = dis.readByte();
//                xid = dis.readInt();
//                secs = dis.readShort();
//                flags = dis.readShort();
//
//                dis.readFully(ciaddr);
//                dis.readFully(yiaddr);
//                dis.readFully(siaddr);
//                dis.readFully(giaddr);
//                dis.readFully(chaddr);
//                dis.readFully(sname);
//                dis.readFully(file);
//
//                dhcpOptions.init(dis);
//            }
//            catch(java.io.IOException unexpected) {
//                throw new RuntimeException(unexpected);
//            }
//        }
//    }
//
//    private void init(DHCPParameters src) throws ArrayIndexOutOfBoundsException
//    {
//        synchronized(lock) {
//            op = src.op;
//            htype = src.htype;
//            hlen = src.hlen;
//            hops = src.hops;
//            xid = src.xid;
//            secs = src.secs;
//            flags = src.flags;
//
//            DHCPParameters.set(src.ciaddr, ciaddr, "ciaddr");
//            DHCPParameters.set(src.yiaddr, yiaddr, "yiaddr");
//            DHCPParameters.set(src.siaddr, siaddr, "siaddr");
//            DHCPParameters.set(src.giaddr, giaddr, "giaddr");
//            DHCPParameters.set(src.chaddr, chaddr, "chaddr");
//            DHCPParameters.set(src.sname, sname, "sname");
//            DHCPParameters.set(src.file, file, "file");
//
//            dhcpOptions = src.dhcpOptions.getClone();
//        }
//
//    }
//
//    public void setOp(byte inOp)
//    {
//        op = inOp;
//    }
//
//    public byte getOp()
//    {
//        return op;
//    }
//
//    public void setHType(byte htype)
//    {
//        this.htype = htype;
//    }
//
//    public byte getHType()
//    {
//        return htype;
//    }
//
//    public void setHLen(byte hlen)
//    {
//        this.hlen = hlen;
//    }
//
//    public byte getHLen()
//    {
//        return hlen;
//    }
//
//    public void setHOps(byte hops)
//    {
//        this.hops = hops;
//    }
//
//    public byte getHOps()
//    {
//        return hops;
//    }
//
//    public void setXId(int xid)
//    {
//        this.xid = xid;
//    }
//
//    public int getXId()
//    {
//        return xid;
//    }
//
//    public void setSecs(short secs)
//    {
//        this.secs = secs;
//    }
//
//    public short getSecs()
//    {
//        return secs;
//    }
//
//    public void setFlags(short flags)
//    {
//        this.flags = flags;
//    }
//
//    public short getFlags()
//    {
//        return flags;
//    }
//
//    public void setCIAddr(byte[] ciaddr)
//    {
//        DHCPParameters.set(ciaddr, this.ciaddr, "ciaddr");
//    }
//
//    public byte[] getCIAddr()
//    {
//        return ciaddr;
//    }
//
//    public void setYIAddr(byte[] yiaddr)
//    {
//        DHCPParameters.set(yiaddr, this.yiaddr, "yiaddr");
//    }
//
//    public byte[] getYIAddr()
//    {
//        return yiaddr;
//    }
//
//    public void setSIAddr(byte[] siaddr)
//    {
//        DHCPParameters.set(siaddr, this.siaddr, "siaddr");
//    }
//
//    public byte[] getSIAddr()
//    {
//        return siaddr;
//    }
//
//    public void setGIAddr(byte[] giaddr)
//    {
//        DHCPParameters.set(giaddr, this.giaddr, "siaddr");
//    }
//
//    public byte[] getGIAddr()
//    {
//        return giaddr;
//    }
//
//    public void setChaddr(byte[] chaddr)
//    {
//        DHCPParameters.set(chaddr, this.chaddr, "chaddr");
//    }
//
//    public byte[] getCHAddr()
//    {
//        return chaddr;
//    }
//
//    public void setSName(byte[] sname)
//    {
//        DHCPParameters.set(sname, this.sname, "sname", true);
//    }
//
//    public byte[] getSName()
//    {
//        return sname;
//    }
//
//    public void setFile(byte[] file)
//    {
//        DHCPParameters.set(file, this.file, "file", true);
//    }
//
//    public byte[] getFile()
//    {
//        return file;
//    }
//
//    public void setFilename(String filename, String charsetName)
//        throws java.io.UnsupportedEncodingException
//    {
//        setFile(filename.getBytes(charsetName));
//    }
//
//    public void setFilename(String filename)
//    {
//        setFile(filename.getBytes());
//    }
//
//    public String getFilename()
//    {
//        return DHCPParameters.toString(getFile());
//    }
//
//    public void setOption(byte optNum, byte[] optionDataArray)
//    {
//        dhcpOptions.setOption(optNum, optionDataArray);
//    }
//
//    public void setOption(byte optNum, byte optionData)
//    {
//        dhcpOptions.setOption(optNum, optionData);
//    }
//
//    public void setOption(byte optNum, String optionData)
//    {
//        dhcpOptions.setOption(optNum, optionData.getBytes());
//    }
//
//    public void setOptions(DHCPParameters anOtherDHCPParameters)
//    {
//        dhcpOptions.setOptions(anOtherDHCPParameters.dhcpOptions);
//    }
//
//    public byte[] getOption(byte optNum)
//    {
//        return dhcpOptions.getOption(optNum);
//    }
//
//    public byte getOptionAsByte(byte optNum)
//        throws java.lang.IllegalArgumentException, java.lang.NullPointerException
//    {
//        byte[] datas = getOption(optNum);
//
//        if(datas.length != 1) {
//            throw new IllegalArgumentException(Byte.toString(optNum));
//        }
//        else {
//            return datas[0];
//        }
//    }
//
//    private static final void set(byte[] src, byte[] dest, String fieldname, boolean autoAlign)
//    {
//        if(autoAlign && src.length < dest.length) {
//            Arrays.fill(dest, (byte)0);
//
//            for(int i = 0; i < src.length; i++) {
//                dest[i] = src[i];
//            }
//        }
//        else {
//            DHCPParameters.set(src, dest, fieldname);
//        }
//    }
//
//    private static final void set(byte[] src, byte[] dest, String fieldname)
//    {
//        if(src.length != dest.length) {
//            throw new IllegalArgumentException(fieldname);
//        }
//
//        for(int i = 0; i < dest.length; i++) {
//            dest[i] = src[i];
//        }
//    }
//
//    @Override
//    public String toString()
//    {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append( super.toString() )
//            .append('\n')
//            .append("op      =[").append(op).append("]\n")
//            .append("htype   =[").append(htype).append("]\n")
//            .append("hlen    =[").append(hlen).append("]\n")
//            .append("hops    =[").append(hops).append("]\n")
//            .append("xid     =[0x").append(DHCPParameters.toHexString(xid)).append("]\n")
//            .append("secs    =[").append(secs).append("]\n")
//            .append("flags   =[0x").append(DHCPParameters.toHexString(flags)).append("]\n")
//            .append("ciaddr  =[").append(DHCPParameters.ip4AddrToString(ciaddr)).append("] Client IP address (without ARP)\n")
//            .append("yiaddr  =[").append(DHCPParameters.ip4AddrToString(yiaddr)).append("] Your 'Client' IP address\n")
//            .append("siaddr  =[").append(DHCPParameters.ip4AddrToString(siaddr)).append("] Server IP address\n")
//            .append("giaddr  =[").append(DHCPParameters.ip4AddrToString(giaddr)).append("] Relay agent IP address\n")
//            .append("chaddr  =[").append(DHCPParameters.toHexString(chaddr, false)).append("] MAC address\n")
//            .append("sname   =[").append(DHCPParameters.toString(sname)).append("]\n")
//            .append("file    =[").append(getFilename()).append("]\n")
//            .append(dhcpOptions);
//
//        return sb.toString();
//    }
//
////    public DHCPParameters createClone()
////    {
////        DHCPParameters copy = new DHCPParameters();
////
////        copy.init(this);
////
////        return copy;
////    }
//
//    private void writeObject(ObjectOutputStream stream)
//        throws java.io.IOException
//    {
//        stream.defaultWriteObject();
//    }
//
//    private void readObject(ObjectInputStream stream)
//        throws java.io.IOException, ClassNotFoundException
//    {
//        stream.defaultReadObject();
//
//        lock = new Object();
//    }
//
//    public String toHexString()
//    {
//        return DHCPParameters.toHexString( toByteArray() );
//    }
//
//    static final String toString(byte[] bytes)
//    {
//        String str = new String(bytes);
//
//        int nulIndex = str.indexOf('\0');
//
//        if(nulIndex < 0) {
//            return str;
//        }
//        else {
//            return str.substring(0, nulIndex);
//        }
//    }
//
//    public static final String toHexString(int value)
//    {
//        String str = (new StringBuilder()).append("0000000").append(Integer.toHexString(value).toUpperCase()).toString();
//
//        return str.substring(str.length() - 8);
//    }
//
//    public static final String toHexString(byte[] values)
//    {
//        return DHCPParameters.toHexString(values, true);
//    }
//
//    public static final String toHexString(byte[] values, boolean doLayout)
//    {
//        StringBuilder sb = new StringBuilder();
//
//        for(int i = 0; i < values.length; i++) {
//            sb.append( DHCPParameters.toHexString(values[i]) );
//
//            if(!doLayout || (i + 1) % 4 != 0) {
//                continue;
//            }
//
//            if((i + 1) % 32 == 0) {
//                sb.append('\n');
//            }
//            else {
//                sb.append(' ');
//            }
//        }
//
//        return sb.toString();
//    }
//
//    public static final String toHexString(byte value)
//    {
//        int intValue = 0xffff0000 | 0xffff & value;
//
//        return Integer.toHexString(intValue).substring(6).toUpperCase();
//    }
//
//    public static final String ip4AddrToString(byte[] bytes)
//    {
//        StringBuilder sb = new StringBuilder();
//        boolean first = true;
//
//        for(int i = 0; i < bytes.length; i++) {
//            if(first) {
//                first = false;
//            }
//            else {
//                sb.append('.');
//            }
//
//            sb.append(bytes[i] & 0xff);
//        }
//
//        return sb.toString();
//    }
//
//    public static final long byteToLong(byte[] bytes)
//    {
//        long lValue = 0L;
//
//        for(int i = 0; i < bytes.length; i++) {
//            lValue <<= 8;
//            lValue += 0xff & bytes[i];
//        }
//
//        return lValue;
//    }
}
