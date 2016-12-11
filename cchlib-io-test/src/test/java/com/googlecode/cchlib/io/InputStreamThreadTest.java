package com.googlecode.cchlib.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("resource")
public class InputStreamThreadTest
{
    final private static Logger LOGGER = Logger.getLogger( InputStreamThreadTest.class );

    @Test
    public void testInputStreamThread() throws IOException
    {
        try( InputStream sourceIS0 = IOTestHelper.createPNGInputStream() ) {
            final InputStreamThread isThread = new InputStreamThread(
                    getClass().getName(),
                    sourceIS0,
                    16,
                    getExceptionHandler()
            );

            final InputStream copy = isThread.getInputStream();

            isThread.start();

            final InputStream sourceIS1  = IOTestHelper.createPNGInputStream();
            final boolean     r          = IOHelper.isEquals( sourceIS1, copy );

            Assert.assertTrue( r );

            sourceIS1.close();
            copy.close();
        }

        LOGGER.info( "Done" );
    }

    private InputStreamThreadExceptionHandler getExceptionHandler()
    {
        return new InputStreamThreadExceptionHandler()
        {
            @Override
            public void handleReadingIOException( final IOException e )
            {
                LOGGER.warn( "handleReadingIOException", e );
            }
            @Override
            public void handleWritingIOException( final IOException e )
            {
                LOGGER.warn( "handleWritingIOException", e );
            }
        };
    }
}
