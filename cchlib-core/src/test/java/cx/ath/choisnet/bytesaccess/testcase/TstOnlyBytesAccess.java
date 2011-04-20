/**
 * 
 */
package cx.ath.choisnet.bytesaccess.testcase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import cx.ath.choisnet.bytesaccess.BytesAccess;
import cx.ath.choisnet.bytesaccess.BytesAccessException;

/**
 * @author CC
 */
class TstOnlyBytesAccess extends BytesAccess 
{
    public TstOnlyBytesAccess( int bytesLength )
    {
        super( bytesLength );
    }

    public TstOnlyBytesAccess( InputStream is, int length )
            throws NullPointerException,
            BytesAccessException,
            IOException
    {
        super( is, length );
    }
    
    public TstOnlyBytesAccess( byte[] bytes, final int offset, final int length )
        throws IllegalArgumentException
    {
        super(bytes,offset,length);
    }

    public TstOnlyBytesAccess( File file, int length )
            throws NullPointerException,
            BytesAccessException,
            FileNotFoundException,
            IOException
    {
        super( file, length );
    }

    public TstOnlyBytesAccess( BytesAccess anOtherInstance )
    {
        super( anOtherInstance );
    }

}
