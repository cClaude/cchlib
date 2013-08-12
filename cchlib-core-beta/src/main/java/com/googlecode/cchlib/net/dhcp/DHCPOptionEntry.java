package com.googlecode.cchlib.net.dhcp;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//
///**
// *
// *
// */
//public class DHCPOptionEntry implements Serializable
//{
//    private static final long serialVersionUID = 1L;
//    private transient byte[] value;
//
//    public DHCPOptionEntry( byte[] b, int off, int len )
//    {
//        value = new byte[ len ];
//
//        System.arraycopy(b, off, value, 0, len);
//    }
//
//    public DHCPOptionEntry( byte[] value )
//    {
//        this(value, 0, value.length);
//    }
//
//    public DHCPOptionEntry( DHCPOptionEntry other )
//    {
//        this( other.getOptionValue() );
//    }
//
//    public DHCPOptionEntry( byte value )
//    {
//        this.value    = new byte[1];
//        this.value[0] = value;
//    }
//
//    public byte[] getOptionValue()
//    {
//        return value;
//    }
//
//    @Override
//    public String toString()
//    {
//        return '(' + value.length + '/' + DHCPParameters.toHexString( value ) + ')';
//    }
//
//    private void writeObject(ObjectOutputStream stream) throws IOException
//    {
//        stream.defaultWriteObject();
//
//        stream.writeInt(value.length);
//        stream.write(value);
//    }
//
//    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
//    {
//        stream.defaultReadObject();
//
//        value = new byte[ stream.readInt() ];
//        stream.readFully( value );
//    }
//}
