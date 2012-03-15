package com.googlecode.cchlib.net.dhcp;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 *
 */
public class DHCPOptions
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    public static final byte MAGIC_COOKIE[] = {
        99, -126, 83, 99
    };

    public static final byte REQUESTED_IP = 50;
    public static final byte LEASE_TIME = 51;
    public static final byte MESSAGE_TYPE = 53;
    public static final byte T1_TIME = 58;
    public static final byte T2_TIME = 59;
    public static final byte CLASS_ID = 60;
    public static final byte CLIENT_ID = 61;
    public static final byte END_OPTION = -1;
    public static final byte MESSAGE_TYPE_DHCPDISCOVER = 1;
    public static final byte MESSAGE_TYPE_DHCPOFFER = 2;
    public static final byte MESSAGE_TYPE_DHCPREQUEST = 3;
    public static final byte MESSAGE_TYPE_DHCPDECLINE = 4;
    public static final byte MESSAGE_TYPE_DHCPACK = 5;
    public static final byte MESSAGE_TYPE_DHCPNAK = 6;
    public static final byte MESSAGE_TYPE_DHCPRELEASE = 7;
    public static final byte MESSAGE_TYPE_DHCPINFORM = 8;
    private /*Map*/HashMap<Byte,DHCPOptionEntry> optionsTable;
    private static transient Properties prop;

    public DHCPOptions()
    {
        optionsTable = new HashMap<Byte,DHCPOptionEntry>();
    }

    public DHCPOptions setOption(byte option, byte[] value)
    {
        setOption(option, new DHCPOptionEntry(value));

        return this;
    }

    public DHCPOptions setOption(byte option, byte value)
    {
        setOption(option, new DHCPOptionEntry(value));

        return this;
    }

    public DHCPOptions setOption(byte option, DHCPOptionEntry value)
    {
        optionsTable.put( Byte.valueOf( option ), value);

        return this;
    }

    public DHCPOptions setOptions(DHCPOptions anOtherDHCPOptions)
    {
        setOptions( anOtherDHCPOptions.optionsTable.entrySet() );

        return this;
    }

    public DHCPOptions setOptions(Set<Map.Entry<Byte, DHCPOptionEntry>> aCollection)
    {
        for( Map.Entry<Byte, DHCPOptionEntry> entry : aCollection ) {
            setOption(
                    entry.getKey().byteValue(),
                    entry.getValue().getClone()
                    );
        }

        /*
        java.util.Map.Entry entry;

        for(Iterator i$ = aCollection.iterator(); i$.hasNext();
        setOption(((java.lang.Byte)entry.getKey()).byteValue(), ((cx.ath.choisnet.net.dhcp.DHCPOptionEntry)entry.getValue()).getClone())) {
            entry = (java.util.Map.Entry)i$.next();
        }*/

        return this;
    }

    public void clear()
    {
        optionsTable.clear();
    }

    public void removeOption(Byte code)
    {
        optionsTable.remove(code);
    }

    public void removeOption(byte code)
    {
        removeOption( Byte.valueOf( code ) );
    }

    public DHCPOptionEntry getDHCPOptionEntry(byte code)
    {
        return optionsTable.get( Byte.valueOf( code ) );
    }

    public byte[] getOption(byte code)
    {
        DHCPOptionEntry entry = getDHCPOptionEntry(code);

        if(entry != null) {
            return entry.getOptionValue();

        }
        else {
            return null;
        }
    }

    public void init(DataInputStream dis)
        throws java.io.IOException
    {
        clear();
        dis.readByte();
        dis.readByte();
        dis.readByte();
        dis.readByte();

        byte code;

        while((code = dis.readByte()) != -1) {
            byte   length = dis.readByte();
            byte[] datas  = new byte[length];

            dis.readFully(datas);

            setOption(code, new DHCPOptionEntry(datas));
        }
    }

    public byte[] toByteArray()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for(int i = 0; i < MAGIC_COOKIE.length; i++) {
            baos.write(MAGIC_COOKIE[i]);
        }

        byte[] content;

        for(Iterator<Map.Entry<Byte,DHCPOptionEntry>> i$ = getOptionSet().iterator(); i$.hasNext(); baos.write(content, 0, content.length)) {
            Map.Entry<Byte,DHCPOptionEntry> entry = i$.next();

            baos.write( entry.getKey().byteValue() );
            content = entry.getValue().getOptionValue();

            baos.write( (byte)content.length );
        }

        baos.write(-1);

        return baos.toByteArray();
    }

    public Set<Map.Entry<Byte, DHCPOptionEntry>> getOptionSet()
    {
        return Collections.unmodifiableSet((new TreeMap<Byte, DHCPOptionEntry>(optionsTable)).entrySet());
    }

    public String toHexString()
    {
        return  DHCPParameters.toHexString(toByteArray());
    }

    public DHCPOptions getClone()
    {
        DHCPOptions copy = new DHCPOptions();

        Map.Entry<Byte,DHCPOptionEntry> entry;

        for(
                Iterator<Map.Entry<Byte,DHCPOptionEntry>> i$ = optionsTable.entrySet().iterator();
                i$.hasNext();
                copy.setOption(
                        entry.getKey().byteValue(),
                        entry.getValue()).getClone()
                        ) {
            entry = i$.next();
        }

        return copy;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(
                Iterator<Map.Entry<Byte,DHCPOptionEntry>> i$ = getOptionSet().iterator();
                i$.hasNext();
                sb.append("\n")
                ) {
            Map.Entry<Byte,DHCPOptionEntry> entry = i$.next();

            String comment = getOptionComment( entry.getKey().byteValue() );

            char type = comment.charAt(2);
            String displayValue;

            switch(type) {
            case 65:
                displayValue = DHCPParameters.ip4AddrToString(entry.getValue().getOptionValue());
                break;
            case 67:
                displayValue = getSubComment( entry.getKey().byteValue(), entry.getValue().getOptionValue());

                break;
            case 83:
                displayValue = DHCPParameters.toString( entry.getValue().getOptionValue() );
                break;
            default:
                displayValue = entry.getValue().toString();
                break;
            }

            sb.append(entry.getKey());
            sb.append("\t=");
            sb.append(displayValue);
            sb.append(" # ");
            sb.append(comment);
        }

        return sb.toString();
    }

    public String getProperty(String name)
    {
        //DHCPOptions _tmp = this;

        if(prop == null) {
            String ressourceName = "DHCPOptions.properties";
            //DHCPOptions _tmp1 = this;
            prop = new Properties();

            InputStream is = getClass().getResourceAsStream(ressourceName);

            try {
                //DHCPOptions _tmp2 = this;
                prop.load(is);

                is.close();
            }
            catch(java.io.IOException e) {
                throw new RuntimeException("Can't read :DHCPOptions.properties", e);
            }
            catch(java.lang.NullPointerException e) {
                throw new RuntimeException("Can't find :DHCPOptions.properties", e);
            }
        }
        //DHCPOptions _tmp3 = this;
        return prop.getProperty(name);
    }

    public String getOptionComment(byte option)
    {
        String value = getProperty((new StringBuilder()).append("OPTION_NUM.").append(option).toString());

        if(value != null) {
            return value;
        }
        else {
            return (new StringBuilder()).append("Unkown option ").append(option).toString();
        }
    }

    public String getSubComment(byte option, byte[] code)
    {
        if(code.length != 1) {
            return (new StringBuilder()).append("Bad size for data expected 1, found ").append(code.length).toString();
        }
        else {
            return getSubComment(option, code[0]);
        }
    }

    public String getSubComment(byte option, byte code)
    {
        StringBuilder   sb      = new StringBuilder();
        String          value   = getProperty(
                        sb.append(option)
                            .append('.')
                            .append(code)
                            .toString()
                    );

        if(value != null) {
            return value;
        }
        else {
            sb.setLength( 0 );
            return sb.append("Unkown option.code ")
                        .append(option)
                        .append('.')
                        .append(code)
                        .toString();
        }
    }

}
