package cx.ath.choisnet.tools.smstools.myphoneexplorer.thunderbird;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.InvalidMarkException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Claude CHOISNET
 */
public class MessageInputStream extends InputStream
{
    private static final Pattern FROM__LINE_PATTERN = Pattern.compile("(\\A|\\n{2}|(\\r\\n){2})^From .*$\\s*^", 8);
    private CharsetDecoder decoder;
    private ByteBuffer buffer;

    public MessageInputStream(ByteBuffer b, Charset charset)
        throws CharacterCodingException
    {
        this.buffer = b;
        this.decoder = charset.newDecoder();

        Matcher matcher = FROM__LINE_PATTERN.matcher(decoder.decode(buffer));

        if(matcher.find()) {
            buffer.rewind();
            buffer.position(buffer.position() + matcher.end());
            buffer.mark();
        }

        try {
            buffer.reset();
        }
        catch(InvalidMarkException ime) {
            buffer.rewind();
        }
    }

    @Override
    public final int read()
        throws java.io.IOException
    {
        if(!buffer.hasRemaining()) {
            return -1;
        }
        else {
            return buffer.get();
        }
    }

    @Override
    public final int read(byte[] bytes, int offset, int length)
        throws IOException
    {
        if(!buffer.hasRemaining()) {
            return -1;
        }
        else {
            int read = Math.min(length, buffer.remaining());

            buffer.get(bytes, offset, read);

            return read;
        }
    }
}
