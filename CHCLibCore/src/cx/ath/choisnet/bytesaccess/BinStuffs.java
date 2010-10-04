/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.bytesaccess;

/**
 * DO NOT USE !
 */
@Deprecated
class BinStuffs 
{
    /**
     * @param bytes array of byte
     * @param from  first offset to convert
     * @param to    last offset to convert
     * @return a binary String formatted
     */
    public static final String toBinaryString( final byte[] bytes, final int from, final int to )
    {
        final StringBuilder sb = new StringBuilder();

        for( int i = from; i<=to;) {
            int intValue = 0x0000FF00 | (0x000000FF & bytes[ i++ ] );

            sb.append( Integer.toBinaryString( intValue ).substring( 8 ) );

            if( i<=to ) {
                    sb.append( ' ' );
            }
        }

        return sb.toString();
    }

    /**
     * @param b byte to convert
     * @return a binary String formatted
     */
    public static final String toBinaryString( final byte b )
    {
        return Integer.toBinaryString( 0x0000FF00 | (0x000000FF & b) ).substring( 8 );
    }


    /**
     * @param bytes source byte array
     * @return a hexa String formatted
     * @see #toHexString(byte[], int, int, String)
     */
    public static final String toHexString( final byte[] bytes )
    {
        return toHexString( bytes, 0, bytes.length - 1, null );
    }
    
    /**
     * @param bytes
     * @param separator string append between values (could be null)
     * @return a hexa String formatted
     * @see #toHexString(byte[], int, int, String)
     */
    public static final String toHexString( final byte[] bytes, final String separator )
    {
        return toHexString( bytes, 0, bytes.length - 1, separator );
    }

    /**
     * @param bytes source byte array
     * @param from first index, should be grater than 0
     * @param to last index (this value will be in the result), should be grater than 'from' value
     * @param separator string append between values (could be null)
     * @return a hexa String formatted
     */
    public static final String toHexString( final byte[] bytes, final int from, final int to, final String separator )
    {
        final StringBuilder sb = new StringBuilder();

        for( int i = from; i<=to;) {
            int intValue = 0x0000FF00 | (0x000000FF & bytes[ i++ ] );

            sb.append( Integer.toHexString( intValue ).substring( 2 ) );

            if( i<=to ) {
                if( separator != null ) {
                    sb.append( separator );
                }
            }
        }
        return sb.toString().toUpperCase();
    }

     /**
     * @param ubyte
     * @return a hex String formatted from a int (but only lower 8 bytes are read has an ubyte)
     */

    public static final String ubyteToHexString( final int ubyte )
    {
        return Integer.toHexString(  0x0000FF00 | (0x000000FF & ubyte ) ).substring( 2 ).toUpperCase();
    }

//    /**
//     * @param b a boolean
//     * @return the String "T" if b is true, "F" otherwise
//     */
//    @Deprecated
//    public static final String toString( boolean b )
//    {
//        if( b ) { return "T"; }
//        else    { return "F"; }
//    }

    /**
     * @param b a boolean
     * @return the String "1" if b is true, "0" otherwise
     */
    public static final String toIntegerString( boolean b )
    {
        if( b ) { return "1"; }
        else    { return "0"; }
    }
    
    /**
     * NOTE: Does not handle separator !
     * 
     * @param hexString
     * @return a byte array
     * @throws IllegalArgumentException
     * @throws NumberFormatException 
     * @see #toHexString(byte[])
     * @see #toHexString(byte[], int, int, String)
     */
    public static final byte[] hexStringToByteArray(
            final String hexString
            ) throws IllegalArgumentException, NumberFormatException
    {
        if( hexString.length() % 2 != 0 ) {
            throw new IllegalArgumentException();
        }
        
        final byte[] bytes = new byte[ hexString.length() / 2 ];
        
        for(int i=0; i<bytes.length; i++ ) {
            final int beginIndex = i*2;
            String digit = hexString.substring( beginIndex, beginIndex + 2 );
            int    v     = Integer.parseInt( digit, 16 );
            bytes[ i ]   = (byte)v;
        }
        
        return bytes;
    }

    public static byte[] intToByteArray( int aInt )
    {
        final byte[] bytes = new byte[4];
        
        for( int i = bytes.length - 1;i>=0; i--) {
            bytes[ i ] = (byte)(0x000000FF & aInt);
            aInt>>=8;
         }
        
        return bytes;
    }
    
}
