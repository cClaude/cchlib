package cx.ath.choisnet.util.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class MD5FileCompute extends MD5
{
    private final byte[] bytesBuffer;

    public MD5FileCompute(int bufferSize) throws NoSuchAlgorithmException
    {
        super();

        bytesBuffer = new byte[bufferSize];
    }

    public MD5FileCompute() throws NoSuchAlgorithmException
    {
        this(8192);
    }

    public void compute(File file)
        throws java.io.FileNotFoundException,
               java.io.IOException
    {
        reset();

        FileInputStream fis = new FileInputStream(file);

        FileChannel fchannel = fis.getChannel();

        for(
            ByteBuffer buffer = ByteBuffer.wrap(bytesBuffer);
            fchannel.read(buffer) != -1 || buffer.position() > 0;
            buffer.compact()
            ) {
            buffer.flip();

            update(buffer);
        }

        try {
            fchannel.close();
        }
        catch(Exception ignore) {
            }

        try {
            fis.close();
        }
        catch(Exception ignore) {
            }

        //return new MD5File(file, getValue());
    }
}
