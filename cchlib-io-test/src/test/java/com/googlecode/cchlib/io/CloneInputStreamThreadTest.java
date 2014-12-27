package com.googlecode.cchlib.io;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class CloneInputStreamThreadTest
{
    private static final int BUFFER_SIZE = 16;
    private static final Logger LOGGER = Logger.getLogger( CloneInputStreamThreadTest.class );

    @Before
    public void setup() throws FileNotFoundException
    {
    }

    @Test
    public void testCloneInputStreamThread() throws IOException
    {
        try (InputStream sourceIS0 = IO.createPNGInputStream()) {
            final CloneInputStreamThread  threadIS    = new CloneInputStreamThread(
                    getClass().getName(),
                    sourceIS0,
                    BUFFER_SIZE,
                    createExceptionHandlers()
            );
            //InputStream[] copies = new InputStream[ threadIS.getSize() ];
            final TestRunnable[] runs = new TestRunnable[ threadIS.getSize() ];
            for( int i = 0; i<runs.length; i++ ) {
                final InputStream is = threadIS.getInputStream( i );

                runs[ i ]= new TestRunner( is ); // $codepro.audit.disable avoidInstantiationInLoops

                LOGGER.info( "start( " + i + " )" );
                launchTask( runs[ i ] );
            }
            threadIS.start();
            LOGGER.info( "start()" );
            final byte[] source = IO.createPNG();
            for (final TestRunnable run : runs) {
                final byte[] bytes = run.getInputStreamAsBytes();
                Assert.assertArrayEquals( source, bytes );
            }
        }

        LOGGER.info( "done" );
    }

    private void launchTask( final TestRunnable task )
    {
        new Thread( task, "testCloneInputStreamThread" ).start();
    }

    private InputStreamThreadExceptionHandler[] createExceptionHandlers()
    {
        final InputStreamThreadExceptionHandler[] handlers
        = new InputStreamThreadExceptionHandler[ 5 ];

        for( int i = 0; i<handlers.length; i++ ) {
            handlers[ i ] = createExceptionHandler();
            }

        return handlers;
    }

    private InputStreamThreadExceptionHandler createExceptionHandler()
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

    interface TestRunnable extends Runnable
    {
        boolean isReady();
        byte[] getInputStreamAsBytes() throws IOException;
        IOException IOException();
    }

    static class TestRunner implements TestRunnable
    {
        private final InputStream   is;
        private byte[]              bytes;
        private boolean             isReady;
        private IOException         ioException;

        public TestRunner( final InputStream is )
        {
            this.is      = is;
            this.isReady = false;
        }
        @Override
        public boolean isReady()
        {
            return this.isReady;
        }
        @Override
        public void run()
        {
            try {
                this.bytes = convertInputStreamAsBytes();
                }
            catch( final IOException e ) { // $codepro.audit.disable logExceptions
                this.ioException = e;
                }
            this.isReady = true;
        }

        @Override
        public byte[] getInputStreamAsBytes() throws IOException
        {
            while( ! isReady() ) {
                try { Thread.sleep( 500 ); }
                catch( final InterruptedException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
                }

            return this.bytes;
        }
        private byte[] convertInputStreamAsBytes() throws IOException
        {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();

            try {
                try {
                    IOHelper.copy( this.is, os );
                    }
                finally {
                    os.close();
                    }
                }
            finally {
                this.is.close();
                }

            return os.toByteArray();
        }
        @Override
        public IOException IOException()
        {
            return this.ioException;
        }
    }
}
