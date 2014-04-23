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
    final private static Logger LOGGER = Logger.getLogger( CloneInputStreamThreadTest.class );

    @Before
    public void setup() throws FileNotFoundException
    {
    }

    @Test
    public void testCloneInputStreamThread() throws IOException
    {
        InputStream             sourceIS0   = IO.createPNGInputStream();
        CloneInputStreamThread  threadIS    = new CloneInputStreamThread(
            getClass().getName(),
            sourceIS0,
            16,
            createExceptionHandlers()
            );

        //InputStream[] copies = new InputStream[ threadIS.getSize() ];
        TestRunnable[] runs = new TestRunnable[ threadIS.getSize() ];

        for( int i = 0; i<runs.length; i++ ) {
            final InputStream is = threadIS.getInputStream( i );

            runs[ i ]= new TestRunner( is );

            LOGGER.info( "start( " + i + " )" );
            new Thread( runs[ i ], "testCloneInputStreamThread" ).start();
            }

        threadIS.start();
        LOGGER.info( "start()" );

        final byte[] source = IO.createPNG();

        for( int i = 0; i<runs.length; i++ ) {
            byte[] bytes = runs[ i ].getInputStreamAsBytes();

            Assert.assertArrayEquals( source, bytes );
            }

        sourceIS0.close();

        LOGGER.info( "done" );
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
            public void handleReadingIOException( IOException e )
            {
                LOGGER.warn( "handleReadingIOException", e );
            }
            @Override
            public void handleWritingIOException( IOException e )
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
                bytes = convertInputStreamAsBytes();
                }
            catch( IOException e ) { // $codepro.audit.disable logExceptions
                ioException = e;
                }
            isReady = true;
        }

        @Override
        public byte[] getInputStreamAsBytes() throws IOException
        {
            while( ! isReady() ) {
                try { Thread.sleep( 500 ); }
                catch( InterruptedException ignore ) {} // $codepro.audit.disable emptyCatchClause, logExceptions
                }

            return bytes;
        }
        private byte[] convertInputStreamAsBytes() throws IOException
        {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();

            try {
                try {
                    IOHelper.copy( is, os );
                    }
                finally {
                    os.close();
                    }
                }
            finally {
                is.close();
                }

            return os.toByteArray();
        }
        @Override
        public IOException IOException()
        {
            return ioException;
        }
    }
}
