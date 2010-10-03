package cx.ath.choisnet.zip;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleUnZip implements Closeable
{
    private ZipInputStream zis;
    private byte[] buffer;
    private int fileCount;

    public SimpleUnZip(InputStream input)
        throws java.io.IOException
    {
        this(input, 4096);
    }

    public SimpleUnZip(InputStream input, int bufferSize)
        throws java.io.IOException
    {
        zis = new ZipInputStream(input);
        buffer = new byte[bufferSize];
        fileCount = 0;
    }

    public void close()
        throws java.io.IOException
    {
        zis.close();
    }

    protected void finalize() throws Throwable
    {
        close();

        super.finalize();
    }

    public File saveNextEntry(File folderFile)
        throws java.io.IOException
    {
        ZipEntry zipEntry = zis.getNextEntry();

        if(zipEntry == null) {
            return null;
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

        return file;
    }

    public void saveAll(File folderFile)
        throws java.io.IOException
    {
        while(saveNextEntry(folderFile) != null);
    }

    public int getFileCount()
    {
        return fileCount;
    }

}
