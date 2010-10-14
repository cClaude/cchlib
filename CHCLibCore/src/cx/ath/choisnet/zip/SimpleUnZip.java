package cx.ath.choisnet.zip;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleUnZip 
    implements  Closeable, 
                ZipListener
{
    private List<ZipEventListener> postProcessingListeners 
            = new ArrayList<ZipEventListener>();
    private List<ZipEventListener> processingListeners 
            = new ArrayList<ZipEventListener>();
    private ZipInputStream zis;
    private byte[] buffer;
    private int fileCount;

    /**
     * 
     * @param input
     * @throws java.io.IOException
     */
    public SimpleUnZip(InputStream input)
        throws java.io.IOException
    {
        this(input, 4096);
    }

    /**
     * 
     * @param input
     * @param bufferSize
     * @throws java.io.IOException
     */
    public SimpleUnZip(InputStream input, int bufferSize)
        throws java.io.IOException
    {
        zis = new ZipInputStream(input);
        buffer = new byte[bufferSize];
        fileCount = 0;
    }

    @Override
    public void close()
        throws java.io.IOException
    {
        zis.close();
    }

    @Override
    protected void finalize() throws Throwable
    {
        close();
        super.finalize();
    }

    /**
     * 
     * @param folderFile
     * @return
     * @throws java.io.IOException
     */
    public File saveNextEntry(File folderFile)
        throws java.io.IOException
    {
        ZipEntry zipEntry = zis.getNextEntry();

        if(zipEntry == null) {
            return null;
        }

        for(ZipEventListener l:postProcessingListeners) {
            l.newFile( zipEntry );
        }

        File file = new File(folderFile, zipEntry.getName());

        file.setLastModified(zipEntry.getTime());

        File parent = file.getParentFile();

        OutputStream output;

        if(zipEntry.isDirectory()) {
            output = null;
            file.mkdirs();
            }
        else {
            if(!parent.isDirectory()) {
                parent.mkdirs();
                }
            output = new BufferedOutputStream(
                        new FileOutputStream( file )
                        );
        }

        int len;

        while((len = zis.read(buffer, 0, buffer.length)) != -1) {
            output.write(buffer, 0, len);
        }

        if(output != null) {
            output.close();
        }

        this.fileCount++;

        for(ZipEventListener l:processingListeners) {
            l.newFile( zipEntry );
        }
        
        return file;
    }

    /**
     * 
     * @param folderFile
     * @throws java.io.IOException
     */
    public void saveAll(File folderFile)
        throws java.io.IOException
    {
        while(saveNextEntry(folderFile) != null);
    }

    /**
     * Returns count files extracted
     * @return count files extracted
     */
    public int getFileCount()
    {
        return fileCount;
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
