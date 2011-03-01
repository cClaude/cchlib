/**
 * 
 */
package cx.ath.choisnet.bytesaccess.testcase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import cx.ath.choisnet.bytesaccess.BytesAccess;
import cx.ath.choisnet.bytesaccess.BytesAccessDebug;
import cx.ath.choisnet.bytesaccess.BytesAccessException;

/**
 * @author CC
 */
class TstOnlyBytesAccessDebug extends BytesAccessDebug 
{
    public TstOnlyBytesAccessDebug( int bytesLength )
    {
        super( bytesLength );
    }
    
    public TstOnlyBytesAccessDebug( byte[] bytes, final int offset, final int length )
        throws IllegalArgumentException
    {
        super(bytes,offset,length);
    }

    public TstOnlyBytesAccessDebug( InputStream is, int length )
            throws NullPointerException,
            BytesAccessException,
            IOException
    {
        super( is, length );
    }

    public TstOnlyBytesAccessDebug( File file, int length )
            throws NullPointerException,
            BytesAccessException,
            FileNotFoundException,
            IOException
    {
        super( file, length );
    }

    public TstOnlyBytesAccessDebug( BytesAccess anOtherInstance )
    {
        super( anOtherInstance );
    }

}
