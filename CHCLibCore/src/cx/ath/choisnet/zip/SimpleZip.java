package cx.ath.choisnet.zip;

import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.util.Wrappable;
import cx.ath.choisnet.util.iterator.IteratorWrapper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleZip
    implements  java.io.Closeable,
                ZipListener
{
    private List<ZipEventListener> postProcessingListeners 
            = new ArrayList<ZipEventListener>();
    private List<ZipEventListener> processingListeners 
            = new ArrayList<ZipEventListener>();
    private ZipOutputStream zos;
    private byte[] buffer;

    /**
     * 
     * @param output
     * @throws java.io.IOException
     */
    public SimpleZip(OutputStream output)
        throws java.io.IOException
    {
        this(output, 4096);
    }

    /**
     * 
     * @param output
     * @param bufferSize
     * @throws java.io.IOException
     */
    public SimpleZip(
            OutputStream    output, 
            int             bufferSize
            )
        throws java.io.IOException
    {
        zos    = new ZipOutputStream(output);
        buffer = new byte[bufferSize];
        setMethod( ZipEntry.DEFLATED );
    }

    /**
     * 
     */
    public void close()
        throws java.io.IOException
    {
        zos.flush();
        zos.finish();
        zos.close();
    }

    /**
     * 
     * @param comment
     */
    public void setComment(String comment)
    {
        zos.setComment(comment);
    }

    /**
     * 
     * @param level
     */
    public void setLevel(int level)
    {
        zos.setLevel(level);
    }

    /**
     * 
     * @param method
     * @throws IllegalArgumentException 
     * @see ZipEntry#DEFLATED
     * @see ZipEntry#STORED
     */
    public void setMethod(int method)
        throws IllegalArgumentException
    {
        zos.setMethod(method);
    }

    /**
     * 
     * @param simpleZipEntry
     * @throws java.io.IOException
     */
    public void add(SimpleZipEntry simpleZipEntry)
        throws java.io.IOException
    {
        for(ZipEventListener l:postProcessingListeners) {
            l.newFile( simpleZipEntry.getZipEntry() );
        }

        zos.putNextEntry(simpleZipEntry.getZipEntry());

        if(!simpleZipEntry.getZipEntry().isDirectory()) {
            BufferedInputStream bis = new BufferedInputStream(simpleZipEntry.getInputStream());
            int len;

            while((len = bis.read(buffer, 0, buffer.length)) != -1) {
                zos.write(buffer, 0, len);
            }

            bis.close();
        }
        zos.flush();
        zos.closeEntry();

        for(ZipEventListener l:processingListeners) {
            l.newFile( simpleZipEntry.getZipEntry() );
        }
    }

    /**
     * 
     * @param c
     * @throws java.io.IOException
     */
    public void addAll( Iterable<SimpleZipEntry> c )
        throws java.io.IOException
    {
        for( SimpleZipEntry entry : c ) {
            add( entry );
        }
    }

    /**
     * 
     * @param folderFile
     * @param wrapper
     * @throws java.io.IOException
     */
    public void addFolder(
            File                            folderFile, 
            Wrappable<File,SimpleZipEntry>  wrapper
            )
        throws java.io.IOException
    {
        addAll(
                new IteratorWrapper<File, SimpleZipEntry>(
                        new FileIterator(folderFile),
                        wrapper
                        )
                );
    }

    @Override
    public void addPostProcessingListener( ZipEventListener listener )
    {
        this.postProcessingListeners.add(listener);
    }
    @Override
    public void removePostProcessingListener( ZipEventListener listener )
    {
        this.postProcessingListeners.remove(listener);
    }
    @Override
    public void addProcessingListener( ZipEventListener listener )
    {
        this.processingListeners.add( listener );
    }
    @Override
    public void removeProcessingListener( ZipEventListener listener )
    {
        this.processingListeners.remove( listener );
    }
}
