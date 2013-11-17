package oldies.tools.smstools.myphoneexplorer.thunderbird;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;
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
//import net.fortuna.mstor.util.CapabilityHints;
//import net.fortuna.mstor.util.Configurator;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 * 
 */
public class MboxFile
    implements Closeable
{
    private final static Logger LOGGER = Logger.getLogger( MboxFile.class );
    


    public static final String READ_ONLY = "r";
    public static final String READ_WRITE = "rw";
//    private static final String TEMP_FILE_EXTENSION = ".tmp";
    public static final String FROM__PREFIX = "From ";
    private static final Pattern VALID_MBOX_PATTERN = Pattern.compile("^From .*", 32);
    private static final Pattern FROM__LINE_PATTERN = Pattern.compile("(\\A|\\n{2}|(\\r\\n){2})^From .*$", 8);
//notuse    private static final Pattern RELAXED_FROM__LINE_PATTERN = Pattern.compile("^From .*$", 8);
    private static final int DEFAULT_BUFFER_SIZE = 8192;
//    private static java.nio.charset.Charset charset = java.nio.charset.Charset.forName(net.fortuna.mstor.util.Configurator.getProperty("mstor.mbox.encoding", "ISO-8859-1"));
    private Charset charset; //= new Charset("ISO-8859-1" );
//    private org.apache.commons.logging.Log log;
    private CharsetDecoder decoder;
    private CharsetEncoder encoder;
    private File file;
    private String mode;
    private RandomAccessFile raf;
    private FileChannel channel;
    private Long[] messagePositions;

    public MboxFile(File file, Charset charset)
        throws java.io.FileNotFoundException
    {
        this(file, charset, "r");
    }

    public MboxFile(File file, Charset charset, String mode)
    {
    	this.charset = charset;

        decoder = charset.newDecoder();
        encoder = charset.newEncoder();
        encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        this.file = file;
        this.mode = mode;
    }

    private RandomAccessFile getRaf()
        throws java.io.FileNotFoundException
    {
        if(raf == null) {
            raf = new RandomAccessFile(file, mode);
        }
        return raf;
    }

    private FileChannel getChannel()
        throws java.io.FileNotFoundException
    {
        if(channel == null) {
            channel = getRaf().getChannel();
        }
        return channel;
    }

    private ByteBuffer read(long position, int size)
        throws java.io.IOException
    {
        ByteBuffer buffer = null;
        
        try {
//            net.fortuna.mstor.data.BufferStrategy bufferStrategy = null;
//            if(net.fortuna.mstor.util.Configurator.getProperty("mstor.mbox.bufferStrategy") != null) {
//                bufferStrategy = net.fortuna.mstor.data.BufferStrategy.valueOf(net.fortuna.mstor.util.Configurator.getProperty("mstor.mbox.bufferStrategy").toUpperCase());
//            }
//            if(net.fortuna.mstor.data.BufferStrategy.MAPPED.equals(bufferStrategy)) {
//                buffer = getChannel().map(java.nio.channels.FileChannel.MapMode.READ_ONLY, position, size);
//            } 
//            else {
//                if(net.fortuna.mstor.data.BufferStrategy.DIRECT.equals(bufferStrategy)) {
                    buffer = java.nio.ByteBuffer.allocateDirect(size);
//                } 
//                else if(net.fortuna.mstor.data.BufferStrategy.DEFAULT.equals(bufferStrategy) || bufferStrategy == null) {
//                    buffer = java.nio.ByteBuffer.allocate(size);
//                }
//                else {
//                    throw new IllegalArgumentException((new StringBuilder()).append("Unrecognised buffer strategy: ").append(net.fortuna.mstor.util.Configurator.getProperty("mstor.mbox.bufferStrategy")).toString());
//                }

                getChannel().position(position);
                getChannel().read(buffer);

                buffer.flip();
//            }
        }
        catch(java.io.IOException ioe) {
            LOGGER.warn("Error reading bytes using nio", ioe);

            getRaf().seek(position);
            
            byte[] buf = new byte[size];

            getRaf().read(buf);

            buffer = ByteBuffer.wrap(buf);
        }
 
        return buffer;
    }

    private Long[] getMessagePositions()
        throws java.io.IOException
    {
        if(messagePositions == null) {
            List<Long> posList = new ArrayList<Long>();
            
            LOGGER.debug( "Channel size [" + getChannel().size() + "] bytes" );

            int bufferSize = (int)Math.min(getChannel().size(), DEFAULT_BUFFER_SIZE);

            CharSequence cs = null;
            
            ByteBuffer buffer = read(0L, bufferSize);

            cs = decoder.decode(buffer);

            LOGGER.debug( "Buffer [" + cs + "]" );

            long offset = 0L;

            do {
                Matcher matcher = null;
//                if(net.fortuna.mstor.util.CapabilityHints.isHintEnabled("mstor.mbox.parsing.relaxed")) {
//                    matcher = RELAXED_FROM__LINE_PATTERN.matcher(cs);
//                }
//                else {
                    matcher = FROM__LINE_PATTERN.matcher(cs);
//                }

                for(; matcher.find(); posList.add(new Long(offset + (long)matcher.start()))) {
                    LOGGER.debug( "Found match at [" + (offset + (long)matcher.start()) + "]" );
                }

                if(offset + (long)bufferSize >= getChannel().size()) {
                    break;
                }

                offset += bufferSize - FROM__PREFIX.length() - 2;
                bufferSize = (int)Math.min(getChannel().size() - offset, DEFAULT_BUFFER_SIZE);
                buffer = read(offset, bufferSize);

                cs = decoder.decode(buffer);

            } while(true);

            messagePositions = /*(Long[])*/posList.toArray(new Long[posList.size()]);
        }
        return messagePositions;
    }

    public final int getMessageCount()
        throws java.io.IOException
    {
        return getMessagePositions().length;
    }

//    private net.sf.ehcache.Cache getMessageCache()
//    {
//        net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.create();
//        java.lang.String cacheName = (new StringBuilder()).append("mstor.mbox.").append(file.getAbsolutePath().hashCode()).toString();
//        if(manager.getCache(cacheName) == null)
//        {
//            manager.addCache(cacheName);
//
//        }
//        return manager.getCache(cacheName);
//    }

    public final InputStream getMessageAsStream(int index)
        throws java.io.IOException
    {
        ByteBuffer buffer = null;
        
//        if(net.fortuna.mstor.util.CapabilityHints.isHintEnabled("mstor.mbox.cacheBuffers")) {
//            net.sf.ehcache.Element cacheElement = getMessageCache().get(new Integer(index));
//            
//            if(cacheElement != null) {
//                buffer = (java.nio.ByteBuffer)cacheElement.getObjectValue();
//            }
//        }
        
        if(buffer == null) {
            long position = getMessagePositions()[index].longValue();
            long size;
            
            if(index < getMessagePositions().length - 1) {
                size = getMessagePositions()[index + 1].longValue() - getMessagePositions()[index].longValue();
            } 
            else {
                size = getChannel().size() - getMessagePositions()[index].longValue();
            }
            
            buffer = read(position, (int)size);
            
//            if(net.fortuna.mstor.util.CapabilityHints.isHintEnabled("mstor.mbox.cacheBuffers")) {
//                getMessageCache().put(new Element(new Integer(index), buffer));
//            }
        }

        return new MessageInputStream(buffer, charset);
    }

    public final byte[] getMessage(int index)
        throws java.io.IOException
    {
        InputStream in = getMessageAsStream(index);
        
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            int read;
            
            while((read = in.read()) != -1) {
                bout.write(read);
            }

            return bout.toByteArray();
            }
        finally {
            in.close();
            }
    }

//    public final void appendMessage(byte[] message)
//        throws java.io.IOException
//    {
//        synchronized(file) {
//            net.fortuna.mstor.data.MessageAppender appender = new MessageAppender(getChannel());
//            long newMessagePosition = appender.appendMessage(message);
//
//            if(messagePositions != null) {
//                Long[] newMessagePositions = new Long[messagePositions.length + 1];
//
//                java.lang.System.arraycopy(messagePositions, 0, newMessagePositions, 0, messagePositions.length);
//
//                newMessagePositions[newMessagePositions.length - 1] = java.lang.Long.valueOf(newMessagePosition);
//                messagePositions = newMessagePositions;
//
//            }
//        }
//        getMessageCache().removeAll();
//    }

//    public final void purge(int[] msgnums)
//        throws java.io.IOException
//    {
//        File newFile = new File(System.getProperty("java.io.tmpdir"), file.getName() + TEMP_FILE_EXTENSION);
//        FileOutputStream newOut = new FileOutputStream(newFile);
//        FileChannel newChannel = newOut.getChannel();
//
//        net.fortuna.mstor.data.MessageAppender appender = new MessageAppender(newChannel);
//
//        synchronized(file) {
//label0:
//            for(int i = 0; i < getMessagePositions().length; i++) {
//                for(int j = 0; j < msgnums.length; j++) {
//                    if(msgnums[j] == i) {
//                        continue label0;
//                    }
//                }
//
//                appender.appendMessage(getMessage(i));
//            }
//
//            newOut.close();
//            close();
//            java.io.File tempFile = new File(java.lang.System.getProperty("java.io.tmpdir"), (new StringBuilder()).append(file.getName()).append(".").append(java.lang.System.currentTimeMillis()).toString());
//            if(!renameTo(file, tempFile))
//            {
//                throw new IOException("Unable to rename existing file");
//
//            }
//            tempFile.deleteOnExit();
//            renameTo(newFile, file);
//        }
//    }

//    private boolean renameTo(java.io.File source, java.io.File dest)
//    {
//        if(log.isDebugEnabled())
//        {
//            log.debug((new StringBuilder()).append("Renaming [").append(source).append("] to [").append(dest).append("]").toString());
//
//        }
//        if(dest.exists())
//
//        {
//            dest.delete();
//
//        }
//        boolean success = source.renameTo(dest);
//        if(!success)
//        {
//            try
//            {
//                java.io.InputStream in = new FileInputStream(source);
//
//                java.io.OutputStream out = new FileOutputStream(dest);
//
//                byte buffer[] = new byte[8192];
//
//                int length;
//                while((length = in.read(buffer)) >= 0) {
//                    out.write(buffer, 0, length);
//                }
//
//                in.close();
//                out.close();
    
//                try {
//                    success = source.delete();
//                }
//                catch(java.lang.Exception e) {
//                    log.warn("Error cleaning up", e);
//                }
//            }
//            catch(java.io.IOException ioe) {
//                log.error((new StringBuilder()).append("Failed to rename [").append(source).append("] to [").append(dest).append("]").toString(), ioe);
//            }
//        }
//        return success;
//    }

    @Override
    public final void close()
        throws java.io.IOException
    {
        if(messagePositions != null) {
            messagePositions = null;
        }

        if(raf != null) {
            raf.close();
            raf = null;
            channel = null;
        }
    }

    @SuppressWarnings("resource")
    public static boolean isValid(final File file)
    {
        BufferedReader  reader;
        String          line;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            line   = reader.readLine();
        }
        catch( IOException e ) {
            LOGGER.info(
                "Not a valid mbox file [" + file + "]",
                e
                );
            return false;
        }
        boolean flag = line == null || VALID_MBOX_PATTERN.matcher(line).matches();

        if(reader != null) {
            try {
                reader.close();
            }
            catch(java.io.IOException ioe) {
                LOGGER.info(
                    "Error closing stream ["
                        + file 
                        + "]", 
                        ioe
                        );
            }
        }
        return flag;
    }
}
