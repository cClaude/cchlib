package com.googlecode.cchlib.swing;

import java.io.IOException;
import org.junit.Test;

/**
 *
 *
 */
public class DialogHelperTest
{
    @Test
    public void test_showMessageExceptionDialog()
    {
        try {
            crashTest();
            }
        catch( ClassNotFoundException e ) {
            DialogHelper.showMessageExceptionDialog( "a title", e );
            }

        try {
            Thread.sleep( 5_000 );
            }
        catch( InterruptedException ignore ) {}
    }

    private static void crashLoop( final int i ) throws IOException
    {
        if( i > 10 ) {
            throw new IOException( "Fake IOException" );
            }
        else {
            crashLoop( i+1 );
            }
    }
    
    private static void crashTest() throws ClassNotFoundException
    {
        try {
            crashLoop( 0 );
            }
        catch( IOException e ) {
            throw new ClassNotFoundException( "Fake ClassNotFoundException", e );
            }
    }
}
