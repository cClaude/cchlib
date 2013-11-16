package alpha.cx.ath.choisnet.system;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>SystemEnvironmentVarTest</code> contains tests for the class <code>{@link SystemEnvironmentVar}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class SystemEnvironmentVarTest
{
    private static final Logger LOGGER = Logger.getLogger( SystemEnvironmentVarTest.class );
    private SystemEnvironmentVar javaSysEnv;

    private static void logStrings( Iterable<String> iter )
    {
        for( String name : iter ) {
            LOGGER.info( "name = " + name );
            }
    }

    private static void logSerializable( Iterable<Serializable> iter )
    {
        for( Serializable serializable : iter ) {
            LOGGER.info( "serializable = " + serializable.toString() + " = " + serializable );
            }
     }

    private int count( Iterable<?> vars )
    {
        int count = 0;

        for( Iterator<?> iter = vars.iterator(); iter.hasNext(); iter.next() ) {
            count++;
            }

        return count;
    }

    @Test
    public void test_getVarNames()
    {
        Iterable<String> iter = javaSysEnv.getVarNames();

        logStrings( iter );

        Assert.assertTrue( true ); // No exception !
        LOGGER.info( "done" );
    }

    @Test
    public void test_setVar_getVar_deleteVar()
    {
        final String name   = "nameForTest";
        final String value  = "value";

        int count0 = count( javaSysEnv.getVarNames() );

        // ADD
        javaSysEnv.setVar( name, value );
        Assertions.assertThat( javaSysEnv.getVarNames() ).contains( name );
        Assertions.assertThat( javaSysEnv.getVarObjectKeys() ).contains( name );

        int count1 = count( javaSysEnv.getVarNames() );
        Assertions.assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        String nValue = javaSysEnv.getVar( name );
        Assertions.assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        int count2 = count( javaSysEnv.getVarNames() );
        Assertions.assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        javaSysEnv.deleteVar( name );

        int count3 = count( javaSysEnv.getVarNames() );
        Assertions.assertThat( count3 ).isLessThan( count2 );

        String nValue2 = javaSysEnv.getVar( name );
        Assertions.assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }

    @Test
    public void test_getVarObjectKeys()
    {
        Iterable<Serializable> iter = javaSysEnv.getVarObjectKeys();

        logSerializable( iter );

        Assert.assertTrue( true ); // No exception !
        LOGGER.info( "done" );
    }

    @Test
    public void test_setVarObject_getVarObject_deleteVarObject_forString()
    {
        final String  key   = "nameForTest";
        final Integer value = Integer.MAX_VALUE;

        int count0 = count( javaSysEnv.getVarObjectKeys() );

        // ADD
        javaSysEnv.setVarObject( key, value );
        Assertions.assertThat( javaSysEnv.getVarNames() ).contains( key );
        Assertions.assertThat( javaSysEnv.getVarObjectKeys() ).contains( key );

        int count1 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        Serializable nValue = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        int count2 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        javaSysEnv.deleteVarObject( key );

        int count3 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count3 ).isLessThan( count2 );

        Serializable nValue2 = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }
    
    @Test
    public void test_setVarObject_getVarObject_deleteVarObject()
    {
        final File     key   = new File( "TEST" );
        final TestBean value = new TestBean( 1, "MyTest" );

        int count0 = count( javaSysEnv.getVarObjectKeys() );

        // ADD
        javaSysEnv.setVarObject( key, value );
        Assertions.assertThat( javaSysEnv.getVarObjectKeys() ).contains( key );

        int count1 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        Serializable nValue = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        int count2 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        javaSysEnv.deleteVarObject( key );

        int count3 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count3 ).isLessThan( count2 );

        Serializable nValue2 = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }

    /**
     * Perform pre-test initialization.
     */
    @Before
    public void setUp()
        throws Exception
    {
        // add additional set up code here
        javaSysEnv = SystemEnvironmentVarFactory.getJavaSystemEnvironmentVar();
    }

    /**
     * Perform post-test clean-up.
     */
    @After
    public void tearDown()
        throws Exception
    {
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(SystemEnvironmentVarTest.class);
    }
}
