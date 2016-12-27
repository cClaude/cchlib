package cx.ath.choisnet.bytesaccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Perform extra check for BytesAcces. This class is design to
 * be use <b>only</b> during development process.
 *
 * @see BytesAccess
 */
public abstract class BytesAccessDebug extends BytesAccess
{
    public BytesAccessDebug( final int bytesLength )
    {
        super( bytesLength );
    }

    public BytesAccessDebug( final byte[] bytes, final int offset, final int length )
    {
        super( bytes, offset, length );
    }

    public BytesAccessDebug( final File file, final int bytesLength )
        throws IOException
    {
        super( file, bytesLength );
    }

    public BytesAccessDebug( final InputStream is, final int length )
        throws IOException
    {
        super( is, length );
    }

    public BytesAccessDebug( final BytesAccess anOtherBytesAccess )
    {
        super( anOtherBytesAccess );
    }

    /* ---------------------------------------------------------------------- */
    /* ------ Getters stuffs ------------------------------------------------ */
    /* ---------------------------------------------------------------------- */

    /* (non-Javadoc)
     * @see utils.bytes.BytesAcces#getBoolean(int, byte)
     */
    @Override
    protected boolean getBoolean( final int offset, final byte mask )
    {
        // Verify mask has only 1 bit
        final int countBit= countBit( mask );

        if( countBit != 1 ) {
            throw new BytesAccessDebugRuntimeException(
                "getBoolean() mask should had (only) 1 bit: found " + countBit
                    + " for (" + offset + ':' + mask +  ')'
                );
        }
        return super.getBoolean( offset, mask );
    }

    @Override
    public int getUInteger( final int offset, final byte mask, final int rightRot )
    {
        if( ! isMaskLinear( mask ) ) {
            throw new BytesAccessDebugRuntimeException(
                "getUInteger() mask look bad (like 0x0011011) " + mask
                    + " for (" + offset + ':' + mask + '/' + rightRot + ')'
                );
        }
        // TODO: (Verify mask has at least 2 bits???)

        final int v = super.getUInteger(offset,mask,rightRot);

        if( v < 0 ) {
            throw new BytesAccessDebugRuntimeException(
                "getUInteger() 1byte - return: " + v
                    + " for (" + offset + ':' + mask + '/' + rightRot + ')'
                );
        }

        return v;
    }

    @Override
    public int getUInteger(
        final int  offset,
        final byte mask0,
        final int  leftRot,
        final byte mask1,
        final int  rightRot
        )
    {
        if( ! isMaskLinear( mask0 ) ) {
            throw new BytesAccessDebugRuntimeException(
                "getUInteger() mask0 look bad (like 0x0011011) " + mask0
                    + " for (" + offset + ':' + mask0 + '/' + rightRot + ':' + mask1 + '/' + leftRot + ')'
                );
        }
        if( ! isMaskLinear( mask1 ) ) {
            throw new BytesAccessDebugRuntimeException(
                "getUInteger() mask1 look bad (like 0x0011011) " + mask1
                    + " for (" + offset + ':' + mask0 + '/' + rightRot + ':' + mask1 + '/' + leftRot + ')'
                );
        }
        // TODO: Verify mask0 is right align + Verify mask1 is left align

        final int v = super.getUInteger( offset, mask0, leftRot, mask1, rightRot );

        if( v < 0 ) {
            throw new BytesAccessDebugRuntimeException(
                "getUInteger() 2bytes - return: " + v
                    + " for (" + offset + ':' + mask0 + '/' + leftRot + ',' + mask1 + '/' + rightRot + ')'
                );
        }

        return v;
    }

    /* ---------------------------------------------------------------------- */
    /* ------ Setters stuffs ------------------------------------------------ */
    /* ---------------------------------------------------------------------- */

    @Override
    protected void setBoolean( final int offset, final byte mask, final boolean bool )
    {
        // Verify mask has only 1 bit
        final int countBit= countBit( mask );

        if( countBit != 1 ) {
            throw new BytesAccessDebugRuntimeException(
                "getBoolean() mask should had (only) 1 bit: found " + countBit
                    + " for (" + offset + ':' + mask +  ')'
                );
        }

        super.setBoolean( offset, mask, bool );
    }

    @Override
    protected void setUInteger(
        final int  offset,
        final byte mask,
        final int  leftRot,
        final int  value
        )
    {
        if( ( value < 0 ) && (value > 255) ) {
            throw new BytesAccessDebugRuntimeException(
                "setInteger() value should be in [0..255] " + value
                    + " for (" + offset + ':' + mask + '/' + leftRot + ')'
                    );
        }
        if( ! isMaskLinear( mask ) ) {
            throw new BytesAccessDebugRuntimeException(
                "setInteger() mask look bad (like 0x0011011) " + mask
                    + " for (" + offset + ':' + mask + '/' + leftRot + ')'
                );
        }
        // TODO: (Verify mask has at least 2 bits???) + check value according to mask !

        super.setUInteger( offset, mask, leftRot, value );
    }

    @Override
    protected void setUInteger(
        final int  offset,
        final byte mask0,
        final int  rightRot,
        final byte mask1,
        final int  leftRot,
        final int  value
        )
    {
        if( ! isMaskLinear( mask0 ) ) {
            throw new BytesAccessDebugRuntimeException(
                "setInteger() mask0 look bad (like 0x0011011) " + mask0
                    + " for (" + offset + ':' + mask0 + '/' + rightRot + ':' + mask1 + '/' + leftRot + ')'
                );
            }

        if( ! isMaskLinear( mask1 ) ) {
            throw new BytesAccessDebugRuntimeException(
                "setInteger() mask1 look bad (like 0x0011011) " + mask1
                    + " for (" + offset + ':' + mask0 + '/' + rightRot + ':' + mask1 + '/' + leftRot + ')'
                );
            }
        // TODO: Verify mask0 is right align + Verify mask1 is left align + check value according to mask !

        super.setUInteger( offset, mask0, rightRot, mask1, leftRot, value );
   }

    private static int countBit( final byte byteValue )
    {
        int  count = 0;
        byte b     = 0x01;

        for( int i = 0; i < 8; i++ ) {
            if( ( byteValue & b ) != 0 ) {
                count++;
            }

            b <<= 1;
        }

        return count;
    }

    /**
     * Check mask does not contain 0 between first 1 and last 1
     * @param mask The binary mask
     * @return true if there is no 0 bit between fist 1 and last 1,
     *         and if mask is not empty !
     */
    protected boolean isMaskLinear( final byte mask )
    {// TODO: Test case !
        if( mask == 0 ) {
            return false;
            }

        byte    iMask = 0x70;
        int     i     = 0;

        for( ; i<8 ; i++ ) { // looking for first left 1 bit value
            if( (iMask & mask) == iMask ) {
                break;
                }

            iMask >>= 1;
            }
        for( ; i<8; i++ ) { // looking next 0
            if( (iMask & mask) == 0 ) {
                break;
                }

            iMask >>= 1;
            }
        for(; i<8; i++ ) { // looking for next 1
            if( (iMask & mask) == iMask ) {
                return false;
                }

            iMask >>= 1;
            }

        return true;
    }
}
