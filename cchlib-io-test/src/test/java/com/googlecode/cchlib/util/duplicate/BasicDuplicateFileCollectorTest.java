package com.googlecode.cchlib.util.duplicate;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;

/**
 *
 */
public class BasicDuplicateFileCollectorTest
{
    private static final Logger LOGGER = Logger.getLogger( BasicDuplicateFileCollectorTest.class );

    private static final int MAX_FILES_COUNT = 50;
    private static final long FILE_MAX_LENGTH = 2 * 1024 * 1024;

    @Test
    public void test_Base()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        final MessageDigestFile             messageDigestFile = new MessageDigestFile("MD5");
        final BasicDuplicateFileCollector   instance          = new BasicDuplicateFileCollector( messageDigestFile, true );
        final File                          root              = FileHelper.getUserHomeDirFile();
        final Iterable<File>                files             = FileIteratorBuilder.createFileIterator(root, FILE_MAX_LENGTH, MAX_FILES_COUNT * 2);

        instance.addDigestEventListener(
                new DigestEventListener()
                {
                    private static final long serialVersionUID = 1L;
                    long currentFileLength = 0;
                    long cumul = 0;
                    int fileCount = 0;
                    boolean canNotCheckCumulSinceALeastOneFileLocked = false;

                    @Override
                    public void computeDigest( final File file )
                    {
                        if( ! this.canNotCheckCumulSinceALeastOneFileLocked ) {
                            assertEquals("Bad cumul size!",this.currentFileLength,this.cumul);
                            }

                        LOGGER.info( "ComputeD:" + file );
                        this.currentFileLength = file.length();
                        this.cumul = 0;
                        this.fileCount++;
                    }
                    @Override
                    public void ioError( final IOException e, final File file )
                    {
                        LOGGER.warn( "IOException "+file+" : "+e/*,e JUST A WARNING*/);
                        this.canNotCheckCumulSinceALeastOneFileLocked = true;
                    }
                    @Override
                    public void computeDigest( final File file, final long length )
                    {
                        //System.out.printf("in:%s - reading %d bytes\n",file,length);
                        this.cumul += length;
                    }
                    @Override
                    public boolean isCancel()
                    {
                        //return false;
                        return this.fileCount > MAX_FILES_COUNT;
                    }
                    @Override
                    public void hashString( final File file, final String hashString )
                    {
                        LOGGER.info( "hashString[" + file + "] => " + hashString );
                    }
                });

        LOGGER.info( "adding... : " + root );
        LOGGER.info( "Pass 1" );
        instance.pass1Add( files );
        LOGGER.info( "Pass 2" );
        instance.pass2();

        final int dsc = instance.getDuplicateSetsCount();
        final int dfc = instance.getDuplicateFilesCount();

        LOGGER.info("getDuplicateSetsCount: "+dsc);
        LOGGER.info("getDuplicateFilesCount: "+dfc);

        LOGGER.info("compute duplicate count");
        instance.computeDuplicateCount();

        LOGGER.info("getDuplicateSetsCount: "+instance.getDuplicateSetsCount());
        LOGGER.info("getDuplicateFilesCount: "+instance.getDuplicateFilesCount());

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        LOGGER.info("remove non duplicate");
        instance.removeNonDuplicate();

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        final Map<String, Set<File>> map = instance.getFiles();

        for(final Map.Entry<String,Set<File>> entry:map.entrySet()) {
            final String      k = entry.getKey();
            final Set<File>   s = entry.getValue();

            LOGGER.info( "'"+k+" : "+ s.size() );

            for(final File f:s) {
                LOGGER.info( f );
                }
            }

        LOGGER.info( "Done." );
    }
}
