package cx.ath.choisnet.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import cx.ath.choisnet.ToDo;
import cx.ath.choisnet.util.iterator.ArrayIterator;
import cx.ath.choisnet.util.iterator.IteratorFilter;


/**
 * @author Claude CHOISNET
 * @see StreamHelper
 * @see ReaderHelper
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public final class FileHelper
{
    private final static int DEFAULT_BUFFER_SIZE = 4096;

    private FileHelper()
    {
        //All static
    }

    /**
     *
     * @param inputFile
     * @param outputFile
     * @throws java.io.IOException
     */
    public static void copy(File inputFile, File outputFile)
        throws java.io.IOException
    {
        InputStream  input  = new BufferedInputStream(new FileInputStream(inputFile));
        OutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            StreamHelper.copy(input, output, DEFAULT_BUFFER_SIZE);
        }
        finally {
            try { input.close(); } catch(Exception ignore) {}
            try { output.close(); } catch(Exception ignore) { }
        }
    }

    /**
     *
     * @param file
     * @param s
     * @throws IOException
     */
    public static void toFile( File file, String s ) throws IOException
    {
        FileWriter fos = new FileWriter( file );

        try {
            fos.write( s );
        }
        finally {
            fos.close();
        }
    }

    /**
     * Delete all files and folders giving folder
     *
     * @param rootDirFile a valid File object (could be a File or a Directory)
     * @throws FileDeleteException if any error occur during delete process
     */
    public static void deleteTree( File rootDirFile )
        throws FileDeleteException
    {
        if( !rootDirFile.exists() ) {
            return;
        }

        File[] files = rootDirFile.listFiles();

        if( files != null ) {
            for(File f : files) {
                if( f.isFile() ) {
                    boolean res = f.delete();

                    if( !res ) {
                        throw new FileDeleteException(f);
                    }
                }
                else if( f.isDirectory() ) {
                    deleteTree( f );
                }
            }
        }

        boolean res = rootDirFile.delete();
        if( !res ) {
            throw new FileDeleteException(rootDirFile);
        }
    }

    /**
     * Wrap an Iterator on a File array and apply a filter
     *
     * @param files
     * @param fileFilter
     * @return return a file Iterator
     */
    public static Iterator<File> toIterator(
            File[]     files,
            FileFilter fileFilter
            )
    {
        return new IteratorFilter<File>(
                new ArrayIterator<File>(files),
                IteratorFilter.wrappe(fileFilter)
                );
    }
}
