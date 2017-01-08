package com.googlecode.cchlib.swing;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import java.awt.Window;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.lang.Threads;

public class DialogHelperTest
{
    private static class FakeException extends Exception
    {
        private static final long serialVersionUID = 1L;

        public FakeException( final String message )
        {
            super( message );
        }

        public FakeException( final String message, final Throwable cause )
        {
            super( message, cause );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( DialogHelperTest.class );
    private static final int SECONDS_TO_WAIT = 20;

    @Test
    public void test_showMessageExceptionDialog()
        throws InterruptedException, ExecutionException
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        LOGGER.info( "test_showMessageExceptionDialog() - Wait: " + SECONDS_TO_WAIT );

        final boolean result = Threads.startAndWait(
                () -> showMessageExceptionDialog(),
                SECONDS_TO_WAIT,
                TimeUnit.SECONDS
                );

        assertThat( result ).isTrue();
    }

    @Test
    public void test_showMessageExceptionDialog_with_extra_button()
        throws InterruptedException, ExecutionException
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        LOGGER.info( "test_showMessageExceptionDialog_with_extra_button()" );

        final int result = Threads.startAndWait(
                () -> showMessageExceptionDialog_with_extra_button(),
                SECONDS_TO_WAIT,
                TimeUnit.SECONDS
                );

        LOGGER.info( "selected button index : " + result );

        assertThat( result ).isGreaterThan( -1 );
    }

    private static void crashLoop( final int i ) throws FakeException
    {
        if( i < 11 ) {
            crashLoop( i+1 );
            }
        else {
            throw new FakeException( "Fake Level 1" );
            }
    }

    private static void crashTest() throws FakeException
    {
        try {
            crashLoop( 0 );
            }
        catch( final FakeException e ) {
            throw new FakeException( "Fake  Level 2", e );
            }
    }

    private final static boolean showMessageExceptionDialog()
    {
        return showMessageExceptionDialog( createTitle() );
    }

    private static boolean showMessageExceptionDialog( final String title )
    {
        try {
            crashTest();

            return false;
            }
        catch( final FakeException cause ) {
            DialogHelper.showMessageExceptionDialog( title, cause );

            return true;
        }
    }

    private static String createTitle()
    {
        return "Just for testing: (closing in " + SECONDS_TO_WAIT + " seconds)";
    }

    private final static int showMessageExceptionDialog_with_extra_button()
    {
        try {
            crashTest();

            return -1;
            }
        catch( final FakeException cause ) {
            final AbstractButton[] buttons = new AbstractButton[] {
                    new JButton( "Test A" ),
                    new JButton( "Test B" ),
                    new JButton( "Test C" ),
                    new JButton( "Test D" ),
            };
            return DialogHelper.showMessageExceptionDialog(
                   (Window)null,
                   createTitle(),
                   cause,
                   buttons );
        }
    }

    /**
     * Test result directly from CLI to validate "copy" menu
     *
     * @param args Nothing
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public final static void main( final String[] args ) throws Exception
    {
        showMessageExceptionDialog( "Close me" );

        final DialogHelperTest instance = new DialogHelperTest();

        instance.test_showMessageExceptionDialog();
        instance.test_showMessageExceptionDialog_with_extra_button();

        LOGGER.info( "Done..." );

        System.exit( 0 );
    }
}
