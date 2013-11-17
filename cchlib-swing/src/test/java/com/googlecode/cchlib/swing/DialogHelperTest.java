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
    private final static Logger LOGGER = Logger.getLogger( DialogHelperTest.class );

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
        runtest_showMessageExceptionDialog( new OpenExceptionDialog() {
            @Override
            public void showMessageExceptionDialog( Throwable e )
            {
                DialogHelper.showMessageExceptionDialog( "Just for testing", e );
            }
        });
    }

    @Test
    public void test_showMessageExceptionDialog2()
    {
        runtest_showMessageExceptionDialog( new OpenExceptionDialog() {
            @Override
            public void showMessageExceptionDialog( Throwable e )
            {
                AbstractButton[] buttons = new AbstractButton[5];

                for( int i=0; i<buttons.length; i++ ) {
                    buttons[ i ] = new JButton( "Button " + i );
                    }

                int selectedIndex = DialogHelper.showMessageExceptionDialog(
                    null,
                    "Just for testing",
                    e,
                    buttons
                    );

                LOGGER.info( "selected index: " + selectedIndex );
            }
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
        try { crashTest(); } catch( FakeException e ) { // $codepro.audit.disable logExceptions
            final Exception exception = e;
            new Thread( new Runnable() {
                @Override
                public void run()
                {
                    try {
                        openExceptionDialog.showMessageExceptionDialog( exception );
                        }
                    catch( Exception e ) {
                        LOGGER.fatal( "showMessageExceptionDialog Real ERROR!!!", e );
                        }
                }
            },"test").start();
            }
    }

    private static class FakeException extends Exception {
        public FakeException( String message )
        {
            super( message );
        }
        public FakeException( String message, Throwable cause )
        {
            super( message, cause );
        }
        private static final long serialVersionUID = 1L;
    }

    private static void crashLoop( final int i ) throws FakeException
    {
        if( i > 10 ) {
            throw new FakeException( "Fake Level 1" );
            }
        else {
            crashLoop( i+1 );
            }
    }

    private static void crashTest() throws FakeException
    {
        try {
            crashLoop( 0 );
            }
        catch( FakeException e ) {
            throw new FakeException( "Fake  Level 2", e );
            }
    }
}
