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
import com.googlecode.cchlib.io.FileIterator;

/**
 *
 */
public class DuplicateFileCollectorTest
{
    private static final transient Logger logger = Logger.getLogger( DuplicateFileCollectorTest.class );
    protected static final int MAX_FILES_COUNT = 50;

    @Test
    public void test_Base()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        MessageDigestFile       messageDigestFile = new MessageDigestFile("MD5");
        DuplicateFileCollector  instance          = new DuplicateFileCollector( messageDigestFile, true );
        File                    root              = FileHelper.getUserHomeDirFile();
        Iterable<File>          files = new FileIterator(
                root,
                new java.io.FileFilter()
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() ) {
                            //System.out.println(f);
                            return true;
                        }
                        return false;
                    }
                }
                );

        instance.addDigestEventListener(
                new DigestEventListener()
                {
                    long currentFileLength = 0;
                    long cumul = 0;
                    int countFile = 0;
                    boolean canNotCheckCumulSinceALeastOneFileLocked = false;

                    @Override
                    public void computeDigest( File file )
                    {
                        if( ! canNotCheckCumulSinceALeastOneFileLocked ) {
                            assertEquals("Bad cumul size!",currentFileLength,cumul);
                            }

                        logger.info( "ComputeD:" + file );
                        currentFileLength = file.length();
                        cumul = 0;
                    }
                    @Override
                    public void ioError( IOException e, File file )
                    {
                        logger.warn( "IOException "+file+" : "+e/*,e JUST A WARNING*/);
                        canNotCheckCumulSinceALeastOneFileLocked = true;
                    }
                    @Override
                    public void computeDigest( File file, long length )
                    {
                        //System.out.printf("in:%s - reading %d bytes\n",file,length);
                        cumul += length;
                    }
                    @Override
                    public boolean isCancel()
                    {
                        //return false;
                        return (countFile++) > MAX_FILES_COUNT;
                    }
                });

        logger.info( "adding... : "+root );
        instance.pass1Add( files );
        instance.pass2();

        int dsc = instance.getDuplicateSetsCount();
        int dfc = instance.getDuplicateFilesCount();

        logger.info("getDuplicateSetsCount: "+dsc);
        logger.info("getDuplicateFilesCount: "+dfc);

        logger.info("compute duplicate count");
        instance.computeDuplicateCount();

        logger.info("getDuplicateSetsCount: "+instance.getDuplicateSetsCount());
        logger.info("getDuplicateFilesCount: "+instance.getDuplicateFilesCount());

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        logger.info("remove non duplicate");
        instance.removeNonDuplicate();

        assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        Map<String, Set<File>> map = instance.getFiles();

        for(Map.Entry<String,Set<File>> entry:map.entrySet()) {
            String      k = entry.getKey();
            Set<File>   s = entry.getValue();

            logger.info( "'"+k+" : "+ s.size() );
            
            for(File f:s) {
                logger.info( f );
                }
            }
    }
}
