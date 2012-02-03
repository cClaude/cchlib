package alpha.com.googlecode.cchlib.swing;

import java.awt.Component;
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
            crashLoop( 0 );
            }
        catch( IOException e ) {
            Component c = null; // Main Frame
            DialogHelper.showMessageExceptionDialog( c , "a title", e );
            }

        try {
            Thread.sleep( 5_000 );
            }
        catch( InterruptedException ignore ) {}
    }


    public static void crashLoop( int i ) throws IOException
    {
        if( i > 10 ) {
            throw new IOException( "Fake IOException" );
            }
        else {
            crashLoop( i+1 );
            }
    }
}
