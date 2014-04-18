package cx.ath.choisnet.io;

//import cx.ath.choisnet.io.SerializableHelper;
import com.googlecode.cchlib.io.SerializableHelper;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class SerializableTestCaseHelperTest
{
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of clone method, of class SerializableHelper.
     */
    @Test
    public void testClone() throws IOException, ClassNotFoundException
    {
        final MyImpl                    imp1  = new MyImpl( 1, "Str1" );
        final MyImpl                    imp2  = new MyImpl( 2, "Str2" );
        final MyInterfaceSerializable   imp3  = new MyImpl( 3, "Str3" );

        // Pas de problème dans ce cas (on trouve la classe support tout seul).
        MyImpl tst1 = SerializableTestCaseHelper.cloneOverSerialization( imp1 );

        // Pas de problème dans ce cas (on fix la classe support)
        MyImpl tst2 = SerializableHelper.clone( imp2, MyImpl.class );

        // Là il faut que le mécanisme de serialization retrouve la classe support
        MyInterfaceSerializable tst3 = SerializableTestCaseHelper.cloneOverSerialization( imp3 );

        print( imp1 , tst1 );
        print( imp2 , tst2 );
        print( imp3 , tst3 );
    }

    private void print( MyInterface source, MyInterface serialized )
    {
        System.out.println( "source     = " + source );
        System.out.println( "serialized = " + serialized );
        System.out.println( "source.hashCode()     = " + source.hashCode() );
        System.out.println( "serialized.hashCode() = " + serialized.hashCode() );
        System.out.println( "same? = " + source.equals( serialized ) );
        System.out.println();
    }
}
