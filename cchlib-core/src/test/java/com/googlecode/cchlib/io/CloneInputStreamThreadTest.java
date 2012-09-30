package com.googlecode.cchlib.io;

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
    final private static Logger logger = Logger.getLogger( CloneInputStreamThreadTest.class );
    
    @Before
    public void setup() throws FileNotFoundException
    {
    }
    
    @Test
    public void testCloneInputStreamThread() throws IOException
    {
        CloneInputStreamThread isThread = new CloneInputStreamThread(
            getClass().getName(), 
            IO.getPNGFile(), 
            16, 
            createExceptionHandlers()
            );

        InputStream[] copies = new InputStream[ isThread.getSize() ];
        
        for( int i = 0; i<copies.length; i++ ) {
            copies[ i ] = isThread.getInputStream( i );
            }
        
        isThread.start();
        
        for( int i = 0; i<copies.length; i++ ) {
            InputStream source  = IO.getPNGFile();
            boolean     r       = IOHelper.isEquals( source, copies[ i ]  );

            Assert.assertNotNull( r );
            
            source.close();
            copies[ i ].close();
            }
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
                logger.warn( "handleReadingIOException", e );
            }
            @Override
            public void handleWritingIOException( IOException e )
            {
                logger.warn( "handleWritingIOException", e );
            }
        };
    }

}
