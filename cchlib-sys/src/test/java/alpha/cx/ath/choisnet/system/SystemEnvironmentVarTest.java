package alpha.cx.ath.choisnet.system;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import org.apache.log4j.Logger;
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
        final Iterable<String> iter = this.javaSysEnv.getVarNames();

        logStrings( iter );

        Assert.assertTrue( true ); // No exception !
        LOGGER.info( "done" );
    }

    @Test
    public void test_setVar_getVar_deleteVar()
    {
        final String name   = "nameForTest";
        final String value  = "value";

        final int count0 = count( this.javaSysEnv.getVarNames() );

        // ADD
        this.javaSysEnv.setVar( name, value );
        assertThat( this.javaSysEnv.getVarNames() ).contains( name );
        assertThat( this.javaSysEnv.getVarObjectKeys() ).contains( name );

        final int count1 = count( this.javaSysEnv.getVarNames() );
        assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        final String nValue = this.javaSysEnv.getVar( name );
        assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        final int count2 = count( this.javaSysEnv.getVarNames() );
        assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        this.javaSysEnv.deleteVar( name );

        final int count3 = count( this.javaSysEnv.getVarNames() );
        assertThat( count3 ).isLessThan( count2 );

        final String nValue2 = this.javaSysEnv.getVar( name );
        assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }

    @Test
    public void test_getVarObjectKeys()
    {
        final Iterable<Serializable> iter = this.javaSysEnv.getVarObjectKeys();

        logSerializable( iter );

        Assert.assertTrue( true ); // No exception !
        LOGGER.info( "done" );
    }

    @Test
    public void test_setVarObject_getVarObject_deleteVarObject_forString()
    {
        final String  key   = "nameForTest";
        final Integer value = Integer.MAX_VALUE;

        final int count0 = count( this.javaSysEnv.getVarObjectKeys() );

        // ADD
        this.javaSysEnv.setVarObject( key, value );
        assertThat( this.javaSysEnv.getVarNames() ).contains( key );
        assertThat( this.javaSysEnv.getVarObjectKeys() ).contains( key );

        final int count1 = count( this.javaSysEnv.getVarObjectKeys() );
        assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        final Serializable nValue = this.javaSysEnv.getVarObject( key );
        assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        final int count2 = count( this.javaSysEnv.getVarObjectKeys() );
        assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        this.javaSysEnv.deleteVarObject( key );

        final int count3 = count( this.javaSysEnv.getVarObjectKeys() );
        assertThat( count3 ).isLessThan( count2 );

        final Serializable nValue2 = this.javaSysEnv.getVarObject( key );
        assertThat( nValue2 ).isNull();

        LOGGER.info( "done" );
    }

    @Test
    public void test_setVarObject_getVarObject_deleteVarObject()
    {
        final File     key   = new File( "TEST" );
        final TestBean value = new TestBean( 1, "MyTest" );

        final int count0 = count( this.javaSysEnv.getVarObjectKeys() );

        // ADD
        this.javaSysEnv.setVarObject( key, value );
        assertThat( this.javaSysEnv.getVarObjectKeys() ).contains( key );

        final int count1 = count( this.javaSysEnv.getVarObjectKeys() );
        assertThat( count1 ).isGreaterThanOrEqualTo( count0 );

        // GET
        final Serializable nValue = this.javaSysEnv.getVarObject( key );
        assertThat( nValue ).isEqualTo( value ).isNotSameAs( value );

        final int count2 = count( this.javaSysEnv.getVarObjectKeys() );
        assertThat( count2 ).isGreaterThanOrEqualTo( count1 );

        // DELETE
        this.javaSysEnv.deleteVarObject( key );

        final int count3 = count( this.javaSysEnv.getVarObjectKeys() );
        assertThat( count3 ).isLessThan( count2 );

        final Serializable nValue2 = this.javaSysEnv.getVarObject( key );
        assertThat( nValue2 ).isNull();

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
        this.javaSysEnv = SystemEnvironmentVarFactory.getJavaSystemEnvironmentVar();
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
