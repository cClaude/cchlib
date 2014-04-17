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

@SuppressWarnings("boxing")
public class SystemEnvironmentVarTest
{
    private static final Logger LOGGER = Logger.getLogger( SystemEnvironmentVarTest.class );
    private SystemEnvironmentVar javaSysEnv;

    private static void logStrings( final Iterable<String> iter )
    {
        for( final String name : iter ) {
            LOGGER.info( "name = " + name );
            }
    }

    private static void logSerializable( final Iterable<Serializable> iter )
    {
        for( final Serializable serializable : iter ) {
            LOGGER.info( "serializable = " + serializable.toString() + " = " + serializable );
            }
     }

    private int count( final Iterable<?> vars )
    {
        int count = 0;

        for( final Iterator<?> iter = vars.iterator(); iter.hasNext(); iter.next() ) {
            count++;
            }

        return count;
    }

    @Test
    public void test_getVarNames()
    {
        final Iterable<String> iter = javaSysEnv.getVarNames();

        logStrings( iter );

        Assert.assertTrue( true ); // No exception !
        LOGGER.info( "done" );
    }

    @Test
    public void test_setVar_getVar_deleteVar()
    {
        final String name   = "nameForTest";
        final String value  = "value";

        final int count0 = count( javaSysEnv.getVarNames() );

        // ADD
        javaSysEnv.setVar( name, value );
        Assertions.assertThat( javaSysEnv.getVarNames() ).contains( name );
        Assertions.assertThat( javaSysEnv.getVarObjectKeys() ).contains( name );

        final int count1 = count( javaSysEnv.getVarNames() );
        Assertions.assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        final String nValue = javaSysEnv.getVar( name );
        Assertions.assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        final int count2 = count( javaSysEnv.getVarNames() );
        Assertions.assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        javaSysEnv.deleteVar( name );

        final int count3 = count( javaSysEnv.getVarNames() );
        Assertions.assertThat( count3 ).isLessThan( count2 );

        final String nValue2 = javaSysEnv.getVar( name );
        Assertions.assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }

    @Test
    public void test_getVarObjectKeys()
    {
        final Iterable<Serializable> iter = javaSysEnv.getVarObjectKeys();

        logSerializable( iter );

        Assert.assertTrue( true ); // No exception !
        LOGGER.info( "done" );
    }

    @Test
    public void test_setVarObject_getVarObject_deleteVarObject_forString()
    {
        final String  key   = "nameForTest";
        final Integer value = Integer.MAX_VALUE;

        final int count0 = count( javaSysEnv.getVarObjectKeys() );

        // ADD
        javaSysEnv.setVarObject( key, value );
        Assertions.assertThat( javaSysEnv.getVarNames() ).contains( key );
        Assertions.assertThat( javaSysEnv.getVarObjectKeys() ).contains( key );

        final int count1 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        final Serializable nValue = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        final int count2 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        javaSysEnv.deleteVarObject( key );

        final int count3 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count3 ).isLessThan( count2 );

        final Serializable nValue2 = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }

    @Test
    public void test_setVarObject_getVarObject_deleteVarObject()
    {
        final File     key   = new File( "TEST" );
        final TestBean value = new TestBean( 1, "MyTest" );

        final int count0 = count( javaSysEnv.getVarObjectKeys() );

        // ADD
        javaSysEnv.setVarObject( key, value );
        Assertions.assertThat( javaSysEnv.getVarObjectKeys() ).contains( key );

        final int count1 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        final Serializable nValue = javaSysEnv.getVarObject( key );
        Assertions.assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        final int count2 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        javaSysEnv.deleteVarObject( key );

        final int count3 = count( javaSysEnv.getVarObjectKeys() );
        Assertions.assertThat( count3 ).isLessThan( count2 );

        final Serializable nValue2 = javaSysEnv.getVarObject( key );
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
    public static void main(final String[] args)
    {
        new org.junit.runner.JUnitCore().run(SystemEnvironmentVarTest.class);
    }
}
