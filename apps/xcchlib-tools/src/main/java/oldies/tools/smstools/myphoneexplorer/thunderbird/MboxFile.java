package oldies.tools.smstools.myphoneexplorer.thunderbird;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class MboxFile implements Closeable
{
    private static final Logger LOGGER = Logger.getLogger( MboxFile.class );

    public static final String READ_ONLY = "r";
    public static final String READ_WRITE = "rw";

    public static final String FROM_PREFIX = "From ";
    private static final Pattern VALID_MBOX_PATTERN = Pattern.compile("^From .*", 32);
    private static final Pattern FROM__LINE_PATTERN = Pattern.compile("(\\A|\\n{2}|(\\r\\n){2})^From .*$", 8);

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private final Charset        charset;
    private final CharsetDecoder decoder;
    private final CharsetEncoder encoder;
    private final File           file;
    private final String         mode;

    private RandomAccessFile raf;
    private FileChannel      channel;
    private Long[]           messagePositions;

    public MboxFile( final File file, final Charset charset)
    {
        this( file, charset, "r" );
    }

    public MboxFile( final File file, final Charset charset, final String mode )
    {
        this.charset = charset;
        this.decoder = charset.newDecoder();
        this.encoder = charset.newEncoder();
        this.encoder.onUnmappableCharacter( CodingErrorAction.REPLACE );

        this.file = file;
        this.mode = mode;
    }

    private RandomAccessFile getRaf() throws FileNotFoundException
    {
        if(this.raf == null) {
            this.raf = new RandomAccessFile(this.file, this.mode);
        }

        return this.raf;
    }

    private FileChannel getChannel() throws FileNotFoundException
    {
        if(this.channel == null) {
            this.channel = getRaf().getChannel();
        }
        return this.channel;
    }

    private ByteBuffer read( final long position, final int size ) throws IOException
    {
        ByteBuffer buffer = null;

        try {
            buffer = ByteBuffer.allocateDirect( size );

            getChannel().position(position);
            getChannel().read(buffer);

            buffer.flip();
        }
        catch( final IOException ioe ) {
            LOGGER.warn("Error reading bytes using nio", ioe);

            getRaf().seek(position);

            final byte[] buf = new byte[ size ];

            getRaf().read( buf );

            buffer = ByteBuffer.wrap( buf );
        }

        return buffer;
    }

    private Long[] getMessagePositions() throws IOException
    {
        if(this.messagePositions == null) {
            final List<Long> posList = new ArrayList<>();

            LOGGER.debug( "Channel size [" + getChannel().size() + "] bytes" );

            int bufferSize = (int)Math.min(getChannel().size(), DEFAULT_BUFFER_SIZE);


            ByteBuffer   buffer = read( 0L, bufferSize );
            CharSequence cs     = this.decoder.decode( buffer );

            LOGGER.debug( "Buffer [" + cs + "]" );

            long offset = 0L;

            do {
                final Matcher matcher = FROM__LINE_PATTERN.matcher(cs);

                for(; matcher.find(); posList.add( Long.valueOf(offset + matcher.start())) ) {
                    LOGGER.debug( "Found match at [" + (offset + matcher.start()) + "]" );
                }

                if((offset + bufferSize) >= getChannel().size()) {
                    break;
                }

                offset += bufferSize - FROM_PREFIX.length() - 2;
                bufferSize = (int)Math.min(getChannel().size() - offset, DEFAULT_BUFFER_SIZE);
                buffer = read(offset, bufferSize);

                cs = this.decoder.decode(buffer);

            } while(true);

            this.messagePositions = /*(Long[])*/posList.toArray(new Long[posList.size()]);
        }
        return this.messagePositions;
    }

    public final int getMessageCount() throws IOException
    {
        return getMessagePositions().length;
    }

    public final InputStream getMessageAsStream(final int index)
        throws java.io.IOException
    {
        final long position = getMessagePositions()[index].longValue();
        long size;

        if(index < (getMessagePositions().length - 1)) {
            size = getMessagePositions()[index + 1].longValue() - getMessagePositions()[index].longValue();
        }
        else {
            size = getChannel().size() - getMessagePositions()[index].longValue();
        }

        final ByteBuffer buffer = read(position, (int)size);

        return new MessageInputStream(buffer, this.charset);
    }

    public final byte[] getMessage(final int index)
        throws java.io.IOException
    {
        try( InputStream in = getMessageAsStream( index ) ) {
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int read;

            while((read = in.read()) != -1) {
                bout.write(read);
            }

            return bout.toByteArray();
            }
    }

    @Override
    public final void close() throws IOException
    {
        if(this.messagePositions != null) {
            this.messagePositions = null;
        }

        if(this.raf != null) {
            this.raf.close();
            this.raf = null;
            this.channel = null;
        }
    }

    @SuppressWarnings("resource")
    public static boolean isValid(final File file)
    {
        String line;

        try( BufferedReader reader = new BufferedReader( new FileReader( file ) ) ) {
            line   = reader.readLine();
        }
        catch( final IOException e ) {
            final String message = "Not a valid mbox file [" + file + "]";

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( message, e );
            } else {
                LOGGER.warn( message + " - " + e.getMessage() );
            }
            return false;
        }

        return (line == null) || VALID_MBOX_PATTERN.matcher(line).matches();
    }
}
