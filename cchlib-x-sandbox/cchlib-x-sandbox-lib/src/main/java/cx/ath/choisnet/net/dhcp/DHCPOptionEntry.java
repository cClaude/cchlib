/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/net/dhcp/DHCPOptionEntry.java
** Description   :
**
**  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
**                      Adapt� du code de Jason Goldschmidt and Nick Stone
**                      et bas� sur les RFCs 1700, 2131 et 2132
**  3.02.015 2006.06.22 Claude CHOISNET
**                      implemente java.io.Serializable
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.net.dhcp.DHCPOptions
**
*/
package cx.ath.choisnet.net.dhcp;

/**
** This class represents a linked list of options for a DHCP message.
** Its purpose is to ease option handling such as add, remove, or change.
**
** @author Claude CHOISNET
** @version 3.02.015
*/
public class DHCPOptionEntry
    implements
        java.io.Serializable
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
private transient byte[] value;

/**
**
*/
public DHCPOptionEntry( // ------------------------------------------------
    final byte[]    b,
    final int       off,
    final int       len
    )
{
    this.value = new byte[ len ];

    System.arraycopy( b, off, this.value, 0, len );
}

/**
**
*/
public DHCPOptionEntry( final byte[] value ) // ---------------------------
{
    this( value, 0, value.length );
}

/**
**
*/
public DHCPOptionEntry( final byte value ) // -----------------------------
{
    this.value = new byte[ 1 ];
    this.value[ 0 ] = value;
}

/**
**
*/
public byte[] getOptionValue() // -----------------------------------------
{
    return this.value;
}

/**
** @return true if all bytes are eguals to 0, false otherwise
*/
public boolean isNull() // ------------------------------------------------
{
 for( byte b : this.value ) {
    if( b != 0 ) {
        return false;
        }
    }

 return true;
}


/**
**
*/
@Override
public String toString() // -----------------------------------------------
{
    return "("
            + value.length
            + "/"
            + DHCPParameters.toHexString( value )
            + ")";
}

/**
**
*/
public DHCPOptionEntry getClone() // --------------------------------------
{
 return new DHCPOptionEntry( getOptionValue() );
}

/**
** interface java.io.Serializable
*/
private void writeObject( java.io.ObjectOutputStream stream ) // ----------
     throws java.io.IOException
{
 stream.defaultWriteObject();

 stream.writeInt( this.value.length );

 stream.write( this.value );
}

/**
** interface java.io.Serializable
*/
private void readObject( java.io.ObjectInputStream stream ) // ------------
     throws
        java.io.IOException,
        ClassNotFoundException
{
 stream.defaultReadObject();

 this.value = new byte[ stream.readInt() ];

 stream.readFully( this.value );
}

} // class
