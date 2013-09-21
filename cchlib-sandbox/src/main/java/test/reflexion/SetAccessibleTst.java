package test.reflexion;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 *
 */
public class SetAccessibleTst
{
    private static final Logger logger = Logger.getLogger( SetAccessibleTst.class );

    private static final String CLASSNAME = SetAccessibleTst.class.getName();
    private static final String FIELDNAME = "testAttribute";

    private Integer testAttribute;

    public SetAccessibleTst( final int firstValue )
    {
        this.testAttribute = Integer.valueOf( firstValue );
    }

    private void testLog()
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

        synchronized( f ) {
            testSetAccesible( f );
        }
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

        f.setAccessible( true ); // Try to restore ! (need to handle concurrent access)
        Object value = f.get( this ); // Well, this is a test ;)

        int typedValue = ((Integer)value).intValue();
        f.set( this, Integer.valueOf( typedValue + 1 ) );

        f.setAccessible( accessible ); // Restore previous value
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        SetAccessibleTst instance = new SetAccessibleTst( 0 );
        instance.testLog();
        logger.info( "New value is :" + instance.testAttribute );
    }


}
