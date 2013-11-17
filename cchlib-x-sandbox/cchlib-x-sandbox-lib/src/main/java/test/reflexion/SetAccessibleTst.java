package test.reflexion;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 *
 */
public class SetAccessibleTst
{
    private static final Logger LOGGER = Logger.getLogger( SetAccessibleTst.class );

    private SetAccessibleTst() {}

    public static void main( String[] args )
    {
        {
            TstRunnable instance0 = new TstRunnable( 0, 0 );
            instance0.testLog();
            LOGGER.info( "New value is :" + instance0.testAttribute );
        }

        new Thread( new TstRunnable(     0,   50 ) ).start();
        new Thread( new TstRunnable( 10000, 1000 ) ).start();
    }

    static class TstRunnable implements Runnable
    {
        private static final String CLASSNAME = TstRunnable.class.getName();
        private static final String FIELDNAME = "testAttribute";

        private Integer testAttribute;
        private long delay;

        public TstRunnable(
            final int  firstValue,
            final long delay
            )
        {
            this.testAttribute = Integer.valueOf( firstValue );
            this.delay         = delay;
        }

        @Override
        public void run()
        {
            for( int i=0; i<100; i++ ) {
                testLog();

                LOGGER.info( "New value is :" + testAttribute );
                }
        }

        public void testLog()
        {
            try {
                testSetAccesible();
                }
            catch( Exception e ) {
                e.printStackTrace();
                }
        }

        public void testSetAccesible() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
        {
            Field f = getNotPublicField();

            //synchronized( f ) {
                testSetAccesible( f );
            //}
        }

        private Field getNotPublicField() throws ClassNotFoundException, NoSuchFieldException, SecurityException
        {
            Class<?> clazz = Class.forName( CLASSNAME );

            try {
                clazz.getField( FIELDNAME );

                throw new IllegalStateException( "Field must not be public");
                }
            catch( NoSuchFieldException exceptedException ) {
                // All ok
                }

            return clazz.getDeclaredField( FIELDNAME );
        }

        private void testSetAccesible( Field f ) throws IllegalArgumentException, IllegalAccessException
        {
            boolean accessible = f.isAccessible();

            assert accessible == false;
            f.setAccessible( true ); // Try to restore ! (need to handle concurrent access)
            LOGGER.info( "F->T" );
            Object value = f.get( this ); // Well, this is a test ;)

            int typedValue = ((Integer)value).intValue();
            f.set( this, Integer.valueOf( typedValue + 1 ) );

            try {
                Thread.sleep( delay );
                }
            catch( InterruptedException ignore ) {
                // ignore
                }

            assert accessible == true;
            f.setAccessible( accessible ); // Restore previous value
            LOGGER.info( "T->F" );
        }
    }
}
