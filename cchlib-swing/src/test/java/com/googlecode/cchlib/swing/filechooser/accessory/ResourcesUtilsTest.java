package com.googlecode.cchlib.swing.filechooser.accessory;

import static org.junit.Assume.assumeTrue;
import java.awt.FlowLayout;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.lang.Threads;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class ResourcesUtilsTest
{
    private static final Logger LOGGER = Logger.getLogger( ResourcesUtilsTest.class );

    @Test
    public void myTestJButton()
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final ResourcesUtils  resourcesUtils = new ResourcesUtils();
        final JFrame          frame = new JFrame();

        frame.setLayout( new FlowLayout() );

        for( final ResourcesUtils.ID id : ResourcesUtils.ID.values() ) {
            final JButton jButton = resourcesUtils.getJButton( id );
            frame.add( jButton );
            }

        frame.setSize(200,200);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LOGGER.info( "myTestJButton() done" );

        Threads.sleep( 3, TimeUnit.SECONDS );
    }

    @Test
    public void testText()
    {
        testText( null );
        testText( Locale.ENGLISH );
        testText( Locale.US );
        testText( Locale.FRENCH );
        testText( Locale.FRANCE );
    }

    private void testText( final Locale locale )
    {
        final ResourcesUtils resourcesUtils = new ResourcesUtils( locale );

        for( final ResourcesUtils.ID id : ResourcesUtils.ID.values() ) {
            final String txt = resourcesUtils.getText( id );

            LOGGER.info( "Text for: " + id + " is [" + txt + "]" );
            }

        LOGGER.info( "testText() done for " + locale );
    }
}
