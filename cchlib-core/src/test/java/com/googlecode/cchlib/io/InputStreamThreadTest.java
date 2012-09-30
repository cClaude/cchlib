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
public class InputStreamThreadTest 
{
    final private static Logger logger = Logger.getLogger( InputStreamThreadTest.class );
    
    @Before
    public void setup() throws FileNotFoundException
    {
    }
    
    @Test
    public void testInputStreamThread() throws IOException
    {
        InputStreamThread isThread = new InputStreamThread(
            getClass().getName(), 
            IO.getPNGFile(), 
            16, 
            getExceptionHandler()
            );
        
        InputStream copy = isThread.getInputStream();
        
        isThread.start();
        
        InputStream source  = IO.getPNGFile();
        boolean     r       = IOHelper.isEquals( source, copy );

        Assert.assertNotNull( r );

        source.close();
        copy.close();
    }

    private InputStreamThreadExceptionHandler getExceptionHandler()
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
