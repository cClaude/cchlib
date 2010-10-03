package cx.ath.choisnet.zip;

import cx.ath.choisnet.io.FileIterator;
import cx.ath.choisnet.util.Wrappable;
import cx.ath.choisnet.util.iterator.IteratorWrapper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.EventListener;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SimpleZip
    implements java.io.Closeable
{
    static interface AppenerListener extends EventListener
    {
        public abstract void fileAppend(ZipEntry zipentry);
    }

    private ZipOutputStream zos;
    private byte[] buffer;

    public SimpleZip(OutputStream output)
        throws java.io.IOException
    {
        this(output, 4096);
    }

    public SimpleZip(OutputStream output, int bufferSize)
        throws java.io.IOException
    {
        zos = new ZipOutputStream(output);
        buffer = new byte[bufferSize];
        setMethod(8);
    }

    public void close()
        throws java.io.IOException
    {
        zos.flush();
        zos.finish();
        zos.close();
        //zos.close();
    }

    public void setComment(String comment)
    {
        zos.setComment(comment);
    }

    public void setLevel(int level)
    {
        zos.setLevel(level);
    }

    public void setMethod(int method)
    {
        zos.setMethod(method);
    }

    public void add(SimpleZipEntry simpleZipEntry)
        throws java.io.IOException
    {
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
    }

    public void addAll( Iterable<SimpleZipEntry> c )
        throws java.io.IOException
    {
        for( SimpleZipEntry entry : c ) {
            add( entry );
        }
    }

    public void addFolder(File folderFile, Wrappable<File,SimpleZipEntry> wrapper)
        throws java.io.IOException
    {
        addAll(
                new IteratorWrapper<File, SimpleZipEntry>(
                        new FileIterator(folderFile),
                        wrapper
                        )
                );
    }
}
