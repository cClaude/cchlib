package com.googlecode.cchlib.swing;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 *
 */
public class DialogHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( DialogHelperTest.class );

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
        //try { Thread.sleep( 5_000 ); } catch( InterruptedException ignore ) {}
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void test_showMessageExceptionDialog1()
    {
        runtest_showMessageExceptionDialog( e -> DialogHelper.showMessageExceptionDialog( "Just for testing", e ));
    }

    @Test
    public void test_showMessageExceptionDialog2()
    {
        runtest_showMessageExceptionDialog( e -> {
            final AbstractButton[] buttons = new AbstractButton[5];

            for( int i=0; i<buttons.length; i++ ) {
                buttons[ i ] = new JButton( "Button " + i );
                }

            final int selectedIndex = DialogHelper.showMessageExceptionDialog(
                null,
                "Just for testing",
                e,
                buttons
                );

            LOGGER.info( "selected index: " + selectedIndex );
        });
    }

    private interface OpenExceptionDialog
    {
        void showMessageExceptionDialog( Throwable e );
    }

    private void runtest_showMessageExceptionDialog(
        final OpenExceptionDialog openExceptionDialog
        )
    {
        try { crashTest(); } catch( final FakeException e ) { // $codepro.audit.disable logExceptions
            final Exception exception = e;
            new Thread( (Runnable)( ) -> {
                try {
                    openExceptionDialog.showMessageExceptionDialog( exception );
                    }
                catch( final Exception e1 ) {
                    LOGGER.fatal( "showMessageExceptionDialog Real ERROR!!!", e1 );
                    }
            },"test").start();
            }
    }

    private static class FakeException extends Exception {
        public FakeException( final String message )
        {
            super( message );
        }
        public FakeException( final String message, final Throwable cause )
        {
            super( message, cause );
        }
        private static final long serialVersionUID = 1L;
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
}
