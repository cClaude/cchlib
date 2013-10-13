/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPParameters.java
** Description   :
**
**  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
**                      Adapté du code de Jason Goldschmidt and Nick Stone
**                      et basé sur les RFCs 1700, 2131 et 2132
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPParameters
**
*/
package cx.ath.choisnet.net.dhcp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

/**
** This class represents a DHCP Message.
**
**
**
** @author Jason Goldschmidt and Nick Stone
** @author Claude CHOISNET
** @version 3.02.014
*/
public class DHCPParameters
{
/**
**
*/
public static final byte OP_OPTION_BOOTREQUEST = 1;

/**
**
*/
public static final byte OP_OPTION_BOOTREPLY = 2;

private byte            op;     // Code opération du message
private byte            htype;  // HW: Adresse matérielle
private byte            hlen;   // hardware address length
private byte            hops;   // Hw options
private int             xid;    // transaction id
private short           secs;   // elapsed time from trying to boot
private short           flags;  // flags
private final byte[]    ciaddr  = new byte[   4 ];  // client IP
private final byte[]    yiaddr  = new byte[   4 ];  // your client IP
private final byte[]    siaddr  = new byte[   4 ];  // Server IP
private final byte[]    giaddr  = new byte[   4 ];  // relay agent IP
private final byte[]    chaddr  = new byte[  16 ];  // Client HW address
private final byte[]    sname   = new byte[  64 ];  // Optional server host name
private final byte[]    file    = new byte[ 128 ];  // Boot file name

/** internal representaton of DHCP Options */
private DHCPOptions dhcpOptions;

/** Lock Object  */
protected transient Object lock = new Object();

/**
** Creates a empty DHCPParameters
*/
public DHCPParameters() // ------------------------------------------------
{
 this.dhcpOptions = new DHCPOptions();
}

/**
** Converts current DHCPParameters object to a byte array.
**
** @return a byte array with information from DHCPParameters object.
*/
public byte[] toByteArray() // --------------------------------------------
{
 synchronized( lock ) {

    try {
        final ByteArrayOutputStream outBStream  = new ByteArrayOutputStream();
        final DataOutputStream      outStream   = new DataOutputStream( outBStream );

        outStream.writeByte( op );
        outStream.writeByte( htype );
        outStream.writeByte( hlen );
        outStream.writeByte( hops );
        outStream.writeInt( xid );
        outStream.writeShort( secs );
        outStream.writeShort( flags );
        outStream.write( this.ciaddr ); //   4
        outStream.write( this.yiaddr ); //   4
        outStream.write( this.siaddr ); //   4
        outStream.write( this.giaddr ); //   4
        outStream.write( this.chaddr ); //  16
        outStream.write( this.sname  ); //  64
        outStream.write( this.file   ); // 128

        final byte[] options = dhcpOptions.toByteArray();

        outStream.write( options, 0, options.length );

        return outBStream.toByteArray();
        }
    catch( java.io.IOException unexpected ) {
        throw new RuntimeException( unexpected );
        }

    }
}

/**
**
*/
public static DHCPParameters newInstance( final byte[] ibuf ) // -------------
{
 final DHCPParameters message = new DHCPParameters();

 message.init( ibuf );

 return message;
}

/**
** Convert a specified byte array containing a DHCP message into a
** DHCPParameters object.
**
** @param ibuf  byte array to convert to a DHCPParameters object
**
** @return a DHCPParameters object with information from byte array.
*/
private void init( final byte[] ibuf ) // ---------------------------------
    throws ArrayIndexOutOfBoundsException
{
 synchronized( lock ) {
    final ByteArrayInputStream  bais    = new ByteArrayInputStream( ibuf );
    final DataInputStream       dis     = new DataInputStream( bais );

    try {
        this.op     = dis.readByte();
        this.htype  = dis.readByte();
        this.hlen   = dis.readByte();
        this.hops   = dis.readByte();
        this.xid    = dis.readInt();
        this.secs   = dis.readShort();
        this.flags  = dis.readShort();

        dis.readFully( this.ciaddr ); //   4
        dis.readFully( this.yiaddr ); //   4
        dis.readFully( this.siaddr ); //   4
        dis.readFully( this.giaddr ); //   4
        dis.readFully( this.chaddr ); //  16
        dis.readFully( this.sname  ); //  64
        dis.readFully( this.file   ); // 128

        this.dhcpOptions.init( dis );
        }
    catch( java.io.IOException unexpected ) {
        throw new RuntimeException( unexpected );
        }
    }
}

private void init( final DHCPParameters src ) // --------------------------
    throws ArrayIndexOutOfBoundsException
{
 synchronized( lock ) {
    this.op     = src.op;
    this.htype  = src.htype;
    this.hlen   = src.hlen;
    this.hops   = src.hops;
    this.xid    = src.xid;
    this.secs   = src.secs;
    this.flags  = src.flags;

    set( src.ciaddr, this.ciaddr, "ciaddr" );
    set( src.yiaddr, this.yiaddr, "yiaddr" );
    set( src.siaddr, this.siaddr, "siaddr" );
    set( src.giaddr, this.giaddr, "giaddr" );
    set( src.chaddr, this.chaddr, "chaddr" );
    set( src.sname , this.sname , "sname"  );
    set( src.file  , this.file  , "file"   );

    this.dhcpOptions = src.dhcpOptions.getClone();
    }
}

/**
** Set message Op code / message type.
**
** @param inOp  message Op code / message type
**
** @see #getOp()
** @see #OP_OPTION_BOOTREQUEST
** @see #OP_OPTION_BOOTREPLY
*/
public void setOp( final byte inOp ) // -----------------------------------
{
 this.op = inOp;
}

/**
** Get message Op code / message type.
**
** @see #setOp(byte)
*/
public byte getOp() // ----------------------------------------------------
{
 return this.op;
}

/**
** Set hardware address type.
**
** @param htype hardware address type
**
** @see #getHType()
*/
public void setHType( final byte htype )  // ------------------------------
{
 this.htype = htype;
}

/**
** Get hardware address type.
**
** @see #setHType(byte)
*/
public byte getHType() // -------------------------------------------------
{
 return this.htype;
}

/**
** Set hardware address length.
**
** @param hlen  hardware address length
**
** @see #getHLen()
*/
public void setHLen( final byte hlen ) // ---------------------------------
{
 this.hlen = hlen;
}

/**
** Get hardware address length.
**
** @see #setHLen(byte)
*/
public byte getHLen() // --------------------------------------------------
{
 return this.hlen ;
}

/**
** Set hops field.
**
** @param hops hops field
**
** @see #getHOps()
*/
public void setHOps( final byte hops ) // ---------------------------------
{
 this.hops = hops;
}

/**
** Get hops field.
**
** @see #setHOps(byte)
*/
public byte getHOps() // --------------------------------------------------
{
 return this.hops;
}

/**
** Set transaction ID.
**
** @param xid  transactionID
**
** @see #getXId()
*/
public void setXId( final int xid ) // ----------------------------------
{
 this.xid = xid;
}

/**
** Get transaction ID.
**
** @see #setXId(int)
*/
public int getXId() // ----------------------------------------------------
{
 return this.xid;
}

/**
** Set seconds elapsed since client began address acquisition or
** renewal process.
**
** @param secs  seconds elapsed since client began address acquisition
**              or renewal process
**
** @see #getSecs()
*/
public void setSecs( final short secs ) // --------------------------------
{
 this.secs = secs;
}

/**
** Get seconds elapsed since client began address acquisition or
** renewal process.
**
** @see #setSecs(short)
*/
public short getSecs() // -------------------------------------------------
{
return this.secs;
}

/**
** Set flags field.
**
** @param flags flags field
**
** @see #getFlags()
*/
public void setFlags( final short flags ) // ------------------------------
{
 this.flags = flags;
}

/**
** Get flags field.
**
** @see #setFlags(short)
*/
public short getFlags() // ------------------------------------------------
{
 return this.flags;
}

/**
** Set client IP address.
**
** @param ciaddr client IP address
**
** @see #getCIAddr()
*/
public void setCIAddr( final byte[] ciaddr ) // ---------------------------
{
 set( ciaddr, this.ciaddr, "ciaddr" );
}

/**
** Get client IP address.
**
** @see #setCIAddr(byte[])
*/
public byte[] getCIAddr() // ----------------------------------------------
{
 return this.ciaddr;
}

/**
** Set 'your' (client) IP address.
**
** @param yiaddr 'your' (client) IP address
**
** @see #getYIAddr()
*/
public void setYIAddr( final byte[] yiaddr ) // ---------------------------
{
 set( yiaddr, this.yiaddr, "yiaddr" );
}

/**
** Get 'your' (client) IP address.
**
** @see #setYIAddr(byte[])
*/
public byte[] getYIAddr() // ----------------------------------------------
{
 return this.yiaddr;
}

/**
** Set address of next server to use in bootstrap.
**
** @param siaddr address of next server to use in bootstrap
**
** @see #getSIAddr()
*/
public void setSIAddr( final byte[] siaddr ) // ---------------------------
{
 set( siaddr, this.siaddr, "siaddr" );
}

/**
** Get address of next server to use in bootstrap.
**
** @see #setSIAddr(byte[])
*/
public byte[] getSIAddr() // ----------------------------------------------
{
 return this.siaddr;
}

/**
** Set relay agent IP address.
**
** @param giaddr relay agent IP address
**
** @see #getGIAddr()
*/
public void setGIAddr( final byte[] giaddr ) // ---------------------------
{
 set( giaddr, this.giaddr, "siaddr" );
}

/**
** Get relay agent IP address.
**
** @see #setGIAddr(byte[])
*/
public byte[] getGIAddr() // ----------------------------------------------
{
 return this.giaddr;
}

/**
** Set client harware address.
**
** @param chaddr client hardware address
**
** @see #getCHAddr()
*/
public void setChaddr( final byte[] chaddr ) // ---------------------------
{
 set( chaddr, this.chaddr, "chaddr" );
}

/**
** Get client harware address.
**
** @see #setChaddr(byte[])
*/
public byte[] getCHAddr() // ----------------------------------------------
{
 return this.chaddr;
}

/**
** Set optional server host name.
**
** @param sname server host name
**
** @see #getSName()
*/
public void setSName( final byte[] sname ) // -----------------------------
{
 set( sname, this.sname, "sname", true );
}

/**
** Get optional server host name.
**
** @see #setSName(byte[])
*/
public byte[] getSName() // -----------------------------------------------
{
 return this.sname;
}

/**
** Set boot file name.
**
** @param file boot file name
**
** @see #getFile()
*/
public void setFile( final byte[] file ) // -------------------------------
{
 set( file, this.file, "file", true );
}

/**
** Get boot file name.
**
** @see #setFile(byte[])
*/
public byte[] getFile() // ------------------------------------------------
{
 return this.file;
}

/**
** Set boot file name.
**
** @param filename      boot file name
** @param charsetName   the name of a supported charset
**
** @see #setFile(byte[])
** @see #setFilename(String)
** @see #getFilename()
*/
public void setFilename( // -----------------------------------------------
    final String filename,
    final String charsetName
    )
    throws java.io.UnsupportedEncodingException
{
 setFile( filename.getBytes( charsetName ) );
}

/**
** Set boot file name.
**
** @param filename      boot file name
**
** @see #setFile(byte[])
** @see #setFilename(String,String)
** @see #getFilename()
** @see String#getBytes()
*/
public void setFilename( // -----------------------------------------------
    final String filename
    )
{
 setFile( filename.getBytes() );
}

/**
** Set boot file name.
**
** @see #setFile(byte[])
** @see #setFilename(String,String)
** @see #setFilename(String)
*/
public String getFilename() // --------------------------------------------
{
 return DHCPParameters.toString( getFile() );
}

/**
** Sets DHCP options in DHCPParameters. If option already exists then remove
** old option and insert a new one.
**
** @param optNum            option number
** @param optionDataArray   option data
*/
public void setOption( // -------------------------------------------------
    final byte      optNum,
    final byte[]    optionDataArray
    )
{
 this.dhcpOptions.setOption( optNum, optionDataArray );
}

/**
** Sets DHCP options in DHCPParameters. If option already exists then remove
** old option and insert a new one.
**
** @param optNum        option number
** @param optionData    option data (1 byte)
*/
public void setOption( // -------------------------------------------------
    final byte  optNum,
    final byte  optionData
    )
{
 this.dhcpOptions.setOption( optNum, optionData );
}

/**
** Sets DHCP options in DHCPParameters. If option already exists then remove
** old option and insert a new one.
**
** @param optNum        option number
** @param optionData    option data (use default charset)
**
** @see String#getBytes()
*/
public void setOption( // -------------------------------------------------
    final byte      optNum,
    final String    optionData
    )
{
 this.dhcpOptions.setOption( optNum, optionData.getBytes() );
}

/**
** @param anOtherDHCPParameters xxx
**
** @see DHCPOptions#setOptions(DHCPOptions)
*/
public void setOptions( // ------------------------------------------------
    final DHCPParameters anOtherDHCPParameters
    )
{
 this.dhcpOptions.setOptions( anOtherDHCPParameters.dhcpOptions );
}

/**
** Returns specified DHCP option that matches the input code. Null is
**  returned if option is not set.
**
** @param optNum  option number
*/
public byte[] getOption( final byte optNum ) // ---------------------------
{
 return this.dhcpOptions.getOption( optNum );
}

/**
** Returns specified DHCP option that matches the input code. Null is
**  returned if option is not set.
**
** @param optNum  option number
**
** @throws IllegalArgumentException if field is not exactly 1 byte long
** @throws NullPointerException     if option is not defined
*/
public byte getOptionAsByte( final byte optNum ) // -----------------------
    throws IllegalArgumentException, NullPointerException
{
 final byte[] datas = getOption( optNum );

 if( datas.length != 1 ) {
    throw new IllegalArgumentException( Byte.toString( optNum ) );
    }

 return datas[ 0 ];
}


/**
** Set value form src to dest
*/
private final static void set( // -----------------------------------------
    final byte[]    src,
    final byte[]    dest,
    final String    fieldname,
    final boolean   autoAlign
    )
{
 if( autoAlign && src.length < dest.length ) {
    Arrays.fill( dest, (byte)0 );

    for( int i = 0; i<src.length; i++ ) {
        dest[ i ] = src[ i ];
        }
    }
 else {
    set( src, dest, fieldname );
    }
}

/**
** Set value form src to dest
*/
private final static void set( // -----------------------------------------
    final byte[]    src,
    final byte[]    dest,
    final String    fieldname
    )
{
 if( src.length != dest.length ) {
    throw new IllegalArgumentException( fieldname );
    }

 for( int i = 0; i<dest.length; i++ ) {
    dest[ i ] = src[ i ];
    }
}


/**
**
*/
public String toString() // -----------------------------------------------
{
 final StringBuilder sb = new StringBuilder();

 sb.append( super.toString() + "\n" );
 sb.append( "op      =[" + op    + "]\n" );
 sb.append( "htype   =[" + htype + "]\n" );
 sb.append( "hlen    =[" + hlen  + "]\n" );
 sb.append( "hops    =[" + hops  + "]\n" );
 if( xid != 0 ) {
    sb.append( "xid     =[0x" + DHCPParameters.toHexString( xid ) + "]\n" );
    }
 if( secs != 0 ) {
    sb.append( "secs    =[" + secs  + "]\n" );
    }
 if( flags != 0 ) {
    sb.append( "flags   =[0x" + DHCPParameters.toHexString( flags ) + "]\n" );
    }

 //
 // Address
 //
 if( ! isNull( ciaddr ) ) {
    sb.append( "ciaddr  =[" + ip4AddrToString( ciaddr ) + "] Client IP address (without ARP)\n" );
    }
 if( ! isNull( yiaddr ) ) {
    sb.append( "yiaddr  =[" + ip4AddrToString( yiaddr ) + "] Your 'Client' IP address\n" );
    }
 if( ! isNull( siaddr ) ) {
    sb.append( "siaddr  =[" + ip4AddrToString( siaddr ) + "] Server IP address\n" );
    }
 if( ! isNull( giaddr ) ) {
    sb.append( "giaddr  =[" + ip4AddrToString( giaddr ) + "] Relay agent IP address\n" );
    }

 sb.append( "chaddr  =[" + DHCPParameters.toHexString( chaddr, false ) + "] MAC address\n" );

 if( ! isNull( sname ) ) {
    sb.append( "sname   =[" + DHCPParameters.toString( sname ) + "]\n" );
    }
 if( ! isNull( file ) ) {
    sb.append( "file    =[" + getFilename()   + "]\n" );
    }

 sb.append( this.dhcpOptions.toString() );

 return sb.toString();
}

/**
**
*/
public DHCPParameters getClone() // ---------------------------------------
{
 final DHCPParameters copy = new DHCPParameters();

 copy.init( this );

 return copy;
}

/**
** interface java.io.Serializable ($$$ A FINIR$$$)
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
     throws java.io.IOException
{
 stream.defaultWriteObject();

// stream.writeInt( this.value.length );
// stream.write( this.value );
}

/**
** interface java.io.Serializable ($$$ A FINIR$$$)
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 //
 // transient fields
 //
 this.lock = new Object();

// this.value = new byte[ stream.readInt() ];
// stream.readFully( this.value );
}

/**
**
*/
public String toHexString() // --------------------------------------------
{
 final StringBuilder sb = new StringBuilder();

 sb.append( DHCPParameters.toHexString( this.toByteArray() ) );

 return sb.toString();
}

/**
** Converts byte[] end with '0' to a new String
*/
public static final String toString( final byte[] bytes ) // --------
{
 final String   str         = new String( bytes );
 final int      nulIndex    = str.indexOf( 0 );

 if( nulIndex < 0 ) {
    return str;
    }
 else {
    return str.substring( 0, nulIndex );
    }
}

/**
**
*/
public final static String toHexString( final int value ) // --------------
{
 final String str = "0000000" + Integer.toHexString( value ).toUpperCase();

 return str.substring( str.length() - 8 );
}

/**
**
*/
public final static String toHexString( final byte[] values ) // ----------
{
 return toHexString( values, true );
}

/**
**
*/
public final static String toHexString( // --------------------------------
    final byte[]    values,
    final boolean   doLayout
    )
{
 final StringBuilder sb = new StringBuilder();

 for( int i = 0; i < values.length; i++ ) {
    sb.append( DHCPParameters.toHexString( values[ i ] ) );

    if( doLayout && ((i+1)%4 == 0) ) {
        if( (i+1)%32 == 0 ) {
            sb.append( '\n' );
            }
        else {
            sb.append( ' ' );
            }
        }
    }

 return sb.toString();
}

/**
**
*/
public final static String toHexString( final byte value ) // -------------
{
 int intValue = 0xFFFF0000 | (0x0000FFFF & value);

 return Integer.toHexString( intValue ).substring( 6 ).toUpperCase();
}

/**
** Converts byte[] => Strings
*/
public static final String ip4AddrToString( final byte[] bytes ) // -------
{
 final StringBuilder    sb      = new StringBuilder();
 boolean                first   = true;

 for( int i = 0; i < bytes.length; i++ ) {
    if( first ) {
        first = false;
        }
    else {
        sb.append( '.' );
        }

    sb.append( bytes[ i ] & 0x000000FF );
    }

 return sb.toString();
}

/**
** Converts byte[] => long
*/
public final static long byteToLong( final byte[] bytes ) // --------------
{
 long lValue = 0;

 for( int i = 0; i<bytes.length; i++ ) {
    lValue <<= 8;
    lValue  += (0x000000FF & bytes[ i ]);
    }

 return lValue;
}

/**
**
*/
public static final boolean isNull( final byte[] values ) // --------------
{
 for( byte b : values ) {
    if( b != 0 ) {
        return false;
        }
    }

 return true;
}

} // class

