package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.io.FileIterable;
import com.googlecode.cchlib.util.CancelRequestException;

public class DefaultDigestFileCollectorTest
{
    private static final Logger LOGGER = Logger.getLogger( DefaultDigestFileCollectorTest.class );

    private static final int MAX_FILES_COUNT = 150;
    private static final long FILE_MAX_SIZE = 5 * 1024 * 1024;

    @Test
    public void test_Base()
        throws  NoSuchAlgorithmException,
                FileNotFoundException,
                IOException
    {
        final DefaultDigestFileCollector instance = new DefaultDigestFileCollector();

        instance.addDigestEventListener( getDigestEventListener() );

        final File            root  = FileHelper.getUserHomeDirFile();
        final Iterable<File>  files = new FileIterable(
                root, (final File f) -> {
                    if( f.isFile() && f.length() > 0  && f.length() < FILE_MAX_SIZE ) {
                        return true;
                    }
                    return false;
        });

        LOGGER.info( "adding... : " + root );

        try {
            instance.add( files );
            }
        catch( final CancelRequestException e ) {
            LOGGER.info( "CancelRequestException: " + e /* Not a error, e */ );
            }

        final int dsc = instance.getDuplicateSetsCount();
        final int dfc = instance.getDuplicateFilesCount();

        LOGGER.info("getDuplicateSetsCount: "+dsc);
        LOGGER.info("getDuplicateFilesCount: "+dfc);

        LOGGER.info("compute duplicate count");
        instance.computeDuplicateCount();

        LOGGER.info("getDuplicateSetsCount: "+instance.getDuplicateSetsCount());
        LOGGER.info("getDuplicateFilesCount: "+instance.getDuplicateFilesCount());

        Assert.assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        Assert.assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        LOGGER.info("remove non duplicate");
        instance.removeNonDuplicate();

        Assert.assertEquals("getDuplicateSetsCount:",dsc,instance.getDuplicateSetsCount());
        Assert.assertEquals("getDuplicateFilesCount:",dfc,instance.getDuplicateFilesCount());

        final Map<String, Set<File>> map = instance.getFiles();

        for(final Map.Entry<String,Set<File>> entry:map.entrySet()) {
            final String      k = entry.getKey();
            final Set<File>   s = entry.getValue();

            LOGGER.info( k + " : " + s.size() );
            for( final File f:s ) {
                LOGGER.info( f );
                }
            }

        LOGGER.info( "done." );
    }

    private static DigestEventListener getDigestEventListener()
    {
        return new DigestEventListener()
        {
            private static final long serialVersionUID = 1L;
            int countFile = 0;
            @Override
            public void computeDigest( final File file )
            {
                LOGGER.info( "computeDigest[" + file + "]" );
                countFile++;
            }
            @Override
            public void computeDigest( final File file, final long length )
            {//Partial compute
            }
            @Override
            public void ioError( final IOException e, final File file )
            {
                LOGGER.warn( "ioError file=" + file + " OK[" + e + "]" /*, e OK JUST WARN */ );
            }
            @Override
            public boolean isCancel()
            {
                return countFile > MAX_FILES_COUNT;
            }
            @Override
            public void hashString( final File file, final String hashString )
            {
                LOGGER.info( "hashString[" + file + "] => " + hashString );
            }
        };
    }
}
