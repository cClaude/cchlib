/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.bytesaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * <P>
 * Unlike {@link java.util.BitSet} this class work directly on bits
 * and offer some basic tools to access and modify a custom structure
 * does not come from Java (typically C).
 * </P>
 * <BR/>
 * Supported type:
 * <pre>
 * - boolean : 1 bit
 * - UInteger: 8 bits
 * - String: depends of encoding
 * </pre>
 * @author Claude CHOISNET
 * @see BytesAccessComparator
 */
public abstract class BytesAccess implements Cloneable
{
    /**
     * Internal buffer
     */
    private byte[] bytes;

    /**
     * Build an array of bytes, all values will be set to 0
     *
     * @param bytesLength length of byte array
     */
    public BytesAccess( int bytesLength )
    {
        this.bytes = new byte[ bytesLength ];

        Arrays.fill( this.bytes, (byte)0 );
    }

    /**
     * Build a *new* array of bytes from a given bytes
     * 
     * @param bytes  byte array to copy
     * @param offset first offset to copy
     * @param length number of bytes to copy
     * @throws IllegalArgumentException if offset is negative
     */
    public BytesAccess( byte[] bytes, final int offset, final int length )
        throws IllegalArgumentException
    {
        if( offset < 0 ) {
            throw new IllegalArgumentException("offset can't be negative");
        }
        this.bytes = new byte[ length ];
        
        for( int i = 0; i<length; i++ ) {
            this.bytes[ i ] = bytes[ offset + i ];
        }
    }
    
    /**
     ** Load bytes from an InputStream
     * @param is a valid InputStream
     * @param length
     * @throws IOException if an error occur while reading stream
     * @throws NullPointerException if "is" is null
     * @throws BytesAccessException
     */
    public BytesAccess( final InputStream is, final int length )
        throws NullPointerException, BytesAccessException, IOException
    {
        this( length );

        int len = is.read( bytes );

        if( len != bytes.length ) {
            throw new BytesAccessException( "Can't read " + length + " bytes, only found " + len );
        }
    }

    /**
     ** Load bytes from a File
     * @param file a valid File object
     * @param length
     * @throws IOException if an error occur while reading stream
     * @throws NullPointerException if "is" is null
     * @throws BytesAccessException
     * @throws FileNotFoundException
     */
    public BytesAccess( final File file, final int length )
        throws NullPointerException, BytesAccessException, FileNotFoundException, IOException
    {
        this( length );

        InputStream is = new FileInputStream( file );

        int len = is.read( bytes );

        is.close();

        if( len != bytes.length ) {
            throw new BytesAccessException( "Can't read " + length + " bytes, only found " + len );
        }
    }

   /**
    * Build an BytesAcces using an other one
    * @param anOtherInstance
    * @see Cloneable
    */
    public BytesAccess( final BytesAccess anOtherInstance )
    {
        this( anOtherInstance.bytes.length );
//        for( int i = 0; i< this.bytes.length; i++ ) {
//            this.bytes[ i ] = anOtherInstance.bytes[ i ];
//        }
        System.arraycopy(anOtherInstance.bytes, 0, this.bytes, 0, this.bytes.length);
    }

    /**
     * @return length of internal byte buffer
     */
    public int length()
    {
        return this.bytes.length;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Arrays.hashCode(bytes);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        try {
            return compare( bytes, BytesAccess.class.cast( obj ).bytes ) == 0;
        }
        catch( IllegalArgumentException e ) {
            return false;
        }
    }

    /**
     * @see #compare(byte[], byte[])
     */
    public  final static long CMP_MASK_OFFSET     = 0xFFFFFFFFFFFF0000L;
    private final static long CMP_MASK_OFFSET_LOW = 0x0000FFFFFFFFFFFFL;
    /**
     * @see #compare(byte[], byte[])
     */
    public final static int  CMP_MASK_ROT_OFFSET = 16; //2*8;

    /**
     * @see #compare(byte[], byte[])
     */
    public final static long CMP_MASK_BYTE0_VALUE = 0x000000000000FF00;
    /**
     * @see #compare(byte[], byte[])
     */
    public final static long CMP_MASK_ROT_BYTE0_VALUE = 8; //1*8

    /**
     * @see #compare(byte[], byte[])
     */
    public final static long CMP_MASK_BYTE1_VALUE = 0x00000000000000FFL;
    /**
     * @see #compare(byte[], byte[])
     */
    public final static int CMP_MASK_ROT_BYTE1_VALUE = 0; //0*8

   /**
    * Could be use to create your own compareTo() method
    *
    * Result when bytes arrays are not equals :
    * <PRE>
    * 0xFFFFFFFFFFFFFF00 : give offset of difference
    * 0x00000000000000F0 : give byte value for parameter bytes0
    * 0x000000000000000F : give byte value for parameter bytes1
    * </PRE>
    * @param bytes0
    * @param bytes1
    * @return 0 if comparison match, otherwise try to give information
    *         on difference found.
    * @throws IllegalArgumentException if byte arrays have different length
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    * @see BytesAccessComparator
    */
    public static long compare( byte[] bytes0, byte[] bytes1 )
        throws IllegalArgumentException
    {
        if( bytes0.length != bytes1.length ) {
            throw new IllegalArgumentException( "bytes arrays not same size (" + bytes0.length + "!=" + bytes1.length + ')' );
        }

        for( int i=0; i<bytes0.length; i++ ) {
            if( bytes0[ i ] != bytes1[ i ] ) {
//Logger slogger = Logger.getLogger(BytesAccess.class);
//i = bytes0.length - 1; 
//slogger.info( String.format("diff found at %1$d (%1$02X)", i) );
                long offset = i<<CMP_MASK_ROT_OFFSET;

                if( (i & CMP_MASK_OFFSET_LOW) != i ) {
                    // Can't store offset
                    // BUT: this can't occur since, 
                    // arrays are limited to Integer.MAX_VALUE
                    // here just to try to deal with all cases
                    // and perhaps some Java evolution ;)
                    offset = CMP_MASK_OFFSET;
                }
                else {
                    offset = i<<CMP_MASK_ROT_OFFSET;
                }
//slogger.info( String.format("offset %1$016X", offset) );
//slogger.info( String.format("CMP_MASK_OFFSET        %1$016X", CMP_MASK_OFFSET) );
////slogger.info( String.format("cmpMask                %1$016X", cmpMask) );
//slogger.info( String.format("CMP_MASK_ROT_OFFSET    %1$d", CMP_MASK_ROT_OFFSET) );
//slogger.info( String.format("i<<CMP_MASK_ROT_OFFSET %1$016X", (i<<CMP_MASK_ROT_OFFSET)) );
//slogger.info( String.format("bytes0[ i ] %1$08X", bytes0[ i ]) );
//slogger.info( String.format("bytes1[ i ] %1$08X", bytes1[ i ]) );

                // 0xFFFFFFFF FFFF0000 - offset of difference
                // 0x00000000 0000FF00 - first  byte value
                // 0x00000000 000000FF - second byte value

                return offset
                    | ( ((bytes0[ i ])<<CMP_MASK_ROT_BYTE0_VALUE) & CMP_MASK_BYTE0_VALUE)
                    | ( ((bytes1[ i ])<<CMP_MASK_ROT_BYTE1_VALUE) & CMP_MASK_BYTE1_VALUE);
            }
        }

        return 0;
    }

    /**
     * @param anOtherInstance
     * @return 0 if byte array have same length and same content
     * @see #compareTo(byte[])
     */
    public int compareTo( final BytesAccess anOtherInstance )
    {
        return compareTo( anOtherInstance.bytes );
    }

    /**
     * Call compare(byte[], byte[]) to build is result,
     * so have a look at compare(byte[], byte[]).
     * <BR/>
     * If you just want to make a basic comparison to 
     * care one return value, just compare it to 0. 
     * It's safe to use result for sorting or building 
     * something like an hash code.
     * <br/>
     * But you can also use result to identify difference
     * in yours arrays. Since compare(byte[], byte[]) 
     * return use a long to store informations, you may
     * loose offset information.
     * 
     * @param someBytes
     * @return 0 if byte array have same length and same content
     * @see #compare(byte[], byte[])
     */
    public int compareTo( byte[] someBytes )
    {
        long l = compare( bytes, someBytes );

        if( (l & 0xFFFFFFFF00000000L) != 0 ) {
            // Greater than what Integer can't store
            // So we can't remember offset.
            
            // Remove bad informations
            l &= (CMP_MASK_BYTE0_VALUE|CMP_MASK_BYTE1_VALUE);
            
            //set offset to 0xFF (255)
            l ^= (0x00FFL<<CMP_MASK_ROT_OFFSET);
        }

        return (int)l;
    }

    /**
     * Compare this Object wise an other one.
     * @param anOtherInstance
     * @return null if both BytesAcces byte[] are identical.
     * @see #advanceCompareTo(byte[])
     */
     public String advanceCompareTo( final BytesAccess anOtherInstance )
     {
         return advanceCompareTo( anOtherInstance.bytes );
     }

   /**
    * TODO: document result String (format and limitations)
    *
    * @param someBytes
    * @return null if both BytesAcces byte[] are identical
    */
    public String advanceCompareTo( final byte[] someBytes )
    {
        for( int i=0; i<this.bytes.length; i++ ) {
            if( this.bytes[ i ] != someBytes[ i ] ) {
                byte  diff  = (byte)(this.bytes[ i ] ^ someBytes[ i ]);

                return "0x"
                    //+ BinStuffs.ubyteToHexString( i )
                    + ubyteToHexString( i )
                    + ':'
                    //+ BinStuffs.toBinaryString( diff )
                    + toBinaryString( diff )
                    + '['
                    //+ BinStuffs.toBinaryString( this.bytes[ i ] )
                    + toBinaryString( this.bytes[ i ] )
                    + '/'
                    //+ BinStuffs.toBinaryString( someBytes[ i ] )
                    + toBinaryString( someBytes[ i ] )
                    + ']';
            }
        }

        return null;
    }

    /**
     * @param ubyte
     * @return a hex String formatted from a int (but only lower 8 bytes are read has an ubyte)
     */
    private static String ubyteToHexString( final int ubyte )
    {
        return Integer.toHexString(  0x0000FF00 | (0x000000FF & ubyte ) ).substring( 2 ).toUpperCase();
    }
    
    /**
     * @param b byte to convert
     * @return a binary String formatted
     */
    private static String toBinaryString( final byte b )
    {
        return Integer.toBinaryString( 0x0000FF00 | (0x000000FF & b) ).substring( 8 );
    }
    
    /* ---------------------------------------------------------------------- */
    /* ------ saving stuffs ------------------------------------------------- */
    /* ---------------------------------------------------------------------- */

   /**
    * Copy internal byte array to a stream
    *
    * @param os
    * @throws IOException
    */
    public void save( final OutputStream os ) throws IOException
    {
        os.write( bytes );
    }

   /**
    * Save BytesAcces has a stream of bytes
    *
    * @param file destination file
    * @throws FileNotFoundException
    * @throws IOException
    */
    public void save( final File file ) throws FileNotFoundException, IOException
    {
        final OutputStream os = new FileOutputStream( file );

        save( os );
        os.close();
    }

    /* ---------------------------------------------------------------------- */
    /* ------ Some binary stuffs -------------------------------------------- */
    /* ---------------------------------------------------------------------- */

    /**
     * Return a copy of internal buffer (read only)
     * @return a new byte array within copy off internal buffer
     */
    public byte[] getBytesCopy()
    {
        byte[] cpy = new byte[ this.bytes.length ];

//        for( int i = 0; i< this.bytes.length; i++ ) {
//            cpy[ i ] = this.bytes[ i ];
//        }
        System.arraycopy(this.bytes, 0, cpy, 0, this.bytes.length);

        return cpy;
    }

    /**
     * @param anOtherInstance
     * @return a byte array witch XOR mask
     * @throws IllegalArgumentException if internal bytes buffers have different length
     * @see #xorOperator(byte[], byte[])
     * @see #xorOperator(byte[])
     * @see #getBytesCopy()
     */
    public byte[] xorOperator( BytesAccess anOtherInstance )
        throws IllegalArgumentException
    {
        return xorOperator( this.bytes, anOtherInstance.bytes );
    }

    /**
     * @param someBytes
     * @return a byte array witch XOR mask
     * @throws IllegalArgumentException if internal bytes buffers have different length
     * @see #xorOperator(byte[], byte[])
     * @see #xorOperator(byte[])
     * @see #getBytesCopy()
     */
    public byte[] xorOperator( byte[] someBytes )
        throws IllegalArgumentException
    {
        return xorOperator( this.bytes, someBytes );
    }

    /**
     * Perform binary XOR (^) operator betting bytes0 and bytes1
     * @param bytes0 first byte array to be compare
     * @param bytes1 second byte array to be compare
     * @return a new byte array
     * @throws IllegalArgumentException if byte arrays have different length
     * @see #xorOperator(BytesAccess)
     * @see #xorOperator(byte[])
     * @see #getBytesCopy()
     */
    public static byte[] xorOperator( final byte[] bytes0, final byte[] bytes1 )
        throws IllegalArgumentException
    {
        if( bytes0.length != bytes1.length ) {
            throw new IllegalArgumentException( "bytes arrays not same size (" + bytes0.length + "!=" + bytes1.length + ')' );
        }

        byte[] mask = new byte[ bytes0.length ];

        for( int i = 0; i< bytes0.length; i++ ) {
            mask[ i ] = (byte)(bytes0[ i ] ^ bytes1[ i ]);;
        }

        return mask;
    }

    /**
     *
     * @param someBytes
     * @return a new byte array
     */
    public byte[] andOperator( byte[] someBytes )
    {
        return andOperator( this.bytes, someBytes );
    }

    /**
     *
     * @param anOtherInstance
     * @return a new byte array
     */
    public byte[] andOperator( BytesAccess anOtherInstance )
    {
        return andOperator( this.bytes, anOtherInstance.bytes );
    }

    /**
     * Perform binary AND (&) operator betting bytes0 and bytes1
     * @param bytes0 first byte array to be compare
     * @param bytes1 second byte array to be compare
     * @return a new byte array
     * @throws IllegalArgumentException if byte arrays have different length
     * @see #xorOperator(BytesAccess)
     * @see #xorOperator(byte[])
     * @see #getBytesCopy()
     */
    public static byte[] andOperator( final byte[] bytes0, final byte[] bytes1 )
        throws IllegalArgumentException
    {
        if( bytes0.length != bytes1.length ) {
            throw new IllegalArgumentException( "bytes arrays not same size (" + bytes0.length + "!=" + bytes1.length + ')' );
        }

        byte[] mask = new byte[ bytes0.length ];

        for( int i = 0; i< bytes0.length; i++ ) {
            mask[ i ] = (byte)(bytes0[ i ] & bytes1[ i ]);;
        }

        return mask;
    }

    /**
     *
     * @param someBytes
     * @return a new byte array
     */
    public byte[] orOperator( byte[] someBytes )
    {
        return orOperator( this.bytes, someBytes );
    }

    /**
     *
     * @param anOtherInstance
     * @return a new byte array
     */
    public byte[] orOperator( BytesAccess anOtherInstance )
    {
        return orOperator( this.bytes, anOtherInstance.bytes );
    }

    /**
     * Perform binary OR (|) operator betting bytes0 and bytes1
     * @param bytes0 first byte array to be compare
     * @param bytes1 second byte array to be compare
     * @return a new byte array
     * @throws IllegalArgumentException if byte arrays have different length
     * @see #xorOperator(BytesAccess)
     * @see #xorOperator(byte[])
     * @see #getBytesCopy()
     */
    public static byte[] orOperator( final byte[] bytes0, final byte[] bytes1 )
        throws IllegalArgumentException
    {
        if( bytes0.length != bytes1.length ) {
            throw new IllegalArgumentException( "bytes arrays not same size (" + bytes0.length + "!=" + bytes1.length + ')' );
        }

        byte[] mask = new byte[ bytes0.length ];

        for( int i = 0; i< bytes0.length; i++ ) {
            mask[ i ] = (byte)(bytes0[ i ] | bytes1[ i ]);;
        }

        return mask;
    }

    /* ---------------------------------------------------------------------- */
    /* ------ Getters stuffs ------------------------------------------------ */
    /* ---------------------------------------------------------------------- */

   /**
    * @param offset
    * @param mask
    * @return boolean value for given offset/mask
    */
    protected boolean getBoolean( final int offset, final byte mask )
    {
        return (mask & this.bytes[ offset ]) != 0;
    }

   /**
    * Return an unsigned integer (i.e., always > 0)
    *
    * @param offset
    * @param mask
    * @param rightRot
    * @return integer value from byte
    */
    public int getUInteger( final int offset, final byte mask, final int rightRot )
    {
        return (    (0x00FF & this.bytes[offset])
                    &
                    (0x0FF & mask)
                )>>rightRot;
    }

   /**
    * Return an unsigned integer (i.e., always > 0)
    *
    * @param offset
    * @param mask0
    * @param leftRot
    * @param mask1
    * @param rightRot
    * @return an integer from 2 bytes [offset and (offset+1)].
    */
    public int getUInteger( final int offset, final byte mask0, final int leftRot, final byte mask1, final int rightRot )
    {
        return (
                    (
                        (0x00FF & this.bytes[offset])
                        &
                        (0x00FF & mask0)
                    )
                    <<
                    leftRot
                )
              + (
                  (
                      (0x00FF & this.bytes[offset + 1])
                      &
                      (0x00FF & mask1)
                      )
                  >>
                  rightRot
                );
    }

  /**
    * build a raw String using encodingCharset from an array of bytes.
    * This String could contain any Character (\00 included)
    *
    * @param from
    * @param to
    * @param encodingCharset
    * @return a String
    */
    protected  final String toString( final int from, final int to, final String encodingCharset )
    {
        try {
            return new String( bytes, from, to - from + 1, encodingCharset );
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }
    }

   /**
    * @param offset
    * @param length
    * @return a byte array copy of internal buffer starting at offset with a len of length
    */
    protected byte[] getBytes( final int offset, final int length )
    {
        byte[] newBytes = new byte[ length ];

        for( int i=0; i<newBytes.length; i++ ) {
            newBytes[ i ] = bytes[ offset + i ];
            }

        return newBytes;
    }

    /* ---------------------------------------------------------------------- */
    /* ------ Setters stuffs ------------------------------------------------ */
    /* ---------------------------------------------------------------------- */

   /**
    *
    * @param offset
    * @param mask
    * @param bool
    */
    protected void setBoolean( final int offset, final byte mask, final boolean bool )
   {
       final int umask = 0x00FF & mask; // be sure only 1 byte
       byte saveValue = (byte) (bytes[ offset ] & ~umask);

       if( bool ) {
           saveValue |= umask;
       }

       bytes[ offset ] = saveValue;
   }


   /**
    * @param offset
    * @param mask
    * @param leftRot
    * @param value
    */
    protected void setUInteger( final int offset, final byte mask, final int leftRot, final int value )
    {
       final int umask = 0x00FF & mask; // be sure only 1 byte
       byte saveValue  = (byte)(bytes[ offset ] & ~umask);
       int  fixValue   = (value<<leftRot) & umask;

       bytes[ offset ] = (byte)(saveValue | fixValue);
   }

   /**
    *
    * @param offset
    * @param mask0
    * @param rightRot
    * @param mask1
    * @param leftRot
    * @param value
    */
    protected void setUInteger( final int offset, final byte mask0, final int rightRot, final byte mask1, final int leftRot, final int value )
   {
       int leftPart  = (value >> rightRot) & mask0;
       setUInteger( offset    , mask0, 0, leftPart );

       int rightPart = (value << leftRot ) & mask1;
       setUInteger( offset + 1, mask1, 0, rightPart );
   }

    /**
     *
     * @param from
     * @param to
     * @param s
     * @param charSet
     */
    protected void setString( final int from, final int to, final String s, final String charSet )
    {
        final byte[] sBytes;

        try {
            sBytes = s.getBytes( charSet );
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException( e );
        }

        final int sBytesLen = sBytes.length - 2;
        final int copyLen = to - from + 1;

        for( int i=0; i<copyLen; i++ ) {
            if( i<sBytesLen ) { bytes[ i + from ] = sBytes[ i + 2 ]; }
            else { bytes[ i + from ] = 0; };
        }
    }

    /**
     *
     * @param offset
     * @param bytes
     */
    protected void setBytes( final int offset, final byte[] bytes )
    {
//        for( int i = 0; i<bytes.length; i++ ) {
//            this.bytes[ offset + i ] = bytes[ i ];
//            }
        System.arraycopy(bytes, 0, this.bytes, offset, bytes.length);
    }

}