package cx.ath.choisnet.io;

import java.io.IOException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.googlecode.cchlib.io.SerializableHelper;

public class SerializationTest extends TestCase
{
    protected final A getA1()
    {
        return new A( "a1" );
    }

    protected final A getA2()
    {
        return new A( "a2" );
    }

    protected final B getB3()
    {
        return new B( "b3" );
    }

    protected final C getC4()
    {
        return new C( "c4" );
    }

    protected final C getC5()
    {
        return new C( "c5" );
    }

    public static Test suite() // ---------------------------------------------
    {
        return new TestSuite( SerializationTest.class );
    }

    public void testSerialization1()
            throws IOException, ClassNotFoundException
    {
        final A a1 = getA1();
        final A s1 = SerializableHelper.clone( a1, A.class );

        System.out.println( "----" );
        System.out.println( "a1 = " + a1 );
        System.out.println( "s1 = " + s1 );

        assertEquals( "compare( a1, a1 )", A.scompare( a1, a1 ), 0 );
        assertEquals( "compare( a1, s1 )", A.scompare( a1, s1 ), 0 );
    }

    public void testSerialization2() // ---------------------------------------
        throws IOException, ClassNotFoundException
    {
        final B b3 = getB3();
        final A a2 = getA1().setB( b3 );
        final A s2 = SerializableHelper.clone( a2, A.class );

        System.out.println( "----" );
        System.out.println( "a2 = " + a2 );
        System.out.println( "s2 = " + s2 );

        assertEquals( "compare( a2, s2 )", A.scompare( a2, s2 ), 0 );
    }

    public void testSerialization3() // ---------------------------------------
        throws IOException, ClassNotFoundException
    {
        final B b3 = getB3();
        final A ab3 = b3.setB( b3 );
        final A ss3 = SerializableHelper.clone( ab3, A.class );

        System.out.println( "----" );
        System.out.println( "b3  = " + b3 );
        System.out.println( "ab3 = " + ab3 );
        System.out.println( "ss3 = " + ss3 );

        assertEquals( "compare( ab3, ab3 )", A.scompare( ab3, ab3 ), 0 );
        assertEquals( "compare( ab3, ss3 )", A.scompare( ab3, ss3 ), 0 );
        assertEquals( "compare( ss3, ab3 )", A.scompare( ss3, ab3 ), 0 );
    }

    public void testSerialization4()
        throws IOException, ClassNotFoundException
    {
        final C c4 = getC4();
        final C s4 = SerializableHelper.clone( c4, C.class );

        System.out.println( "----" );
        System.out.println( "c4 = " + c4 );
        System.out.println( "s4 = " + s4 );

        assertEquals( "compare( c4, s4 )", C.scompare( c4, s4 ), 0 );
        assertEquals( "compare( s4, c4 )", C.scompare( s4, c4 ), 0 );
    }

    public void testSerialization5() // ---------------------------------------
        throws IOException, ClassNotFoundException
    {
        final A a5 = getA1();
        final C c5 = getC4().setA( a5 );
        final C s5 = SerializableHelper.clone( c5, C.class );

        System.out.println( "----" );
        System.out.println( "a5 = " + a5 );
        System.out.println( "c5 = " + c5 );
        System.out.println( "s5 = " + s5 );

        assertEquals( "compare( c5, c5 )", C.scompare( c5, c5 ), 0 );
        assertEquals( "compare( c5, s5 )", C.scompare( c5, s5 ), 0 );
        assertEquals( "compare( s5, c5 )", C.scompare( s5, c5 ), 0 );
    }

    public void testSerialization6() // ---------------------------------------
        throws IOException, ClassNotFoundException
    {
        final A a6 = getA1();
        final C c6 = getC4().setA( a6 );
        final A aa6 = getA2().setC( c6 );
        final A ss6 = SerializableHelper.clone( aa6, A.class );

        System.out.println( "----" );
        System.out.println( "a6  = " + a6 );
        System.out.println( "c6  = " + c6 );
        System.out.println( "aa6 = " + aa6 );
        System.out.println( "ss6 = " + ss6 );
        System.out.println( "ss6 =>" + ss6.getFullContent( 80 ) );

        assertEquals( "compare( aa6, ss6 )", A.scompare( aa6, ss6 ), 0 );

        final C ccc6 = c6.setA( aa6 );
        // System.out.println( "ccc6= " + ccc6 ); // Plante (boucle)
        System.out.println( "ccc6=> " + ccc6.getFullContent( 80 ) );

        final C sss6 = SerializableHelper.clone( ccc6, C.class );
        // System.out.println( "sss6= " + sss6 ); // Plante (boucle)
        System.out.println( "sss6=> " + sss6.getFullContent( 80 ) );

        assertEquals( "compare( ccc6, sss6 )", C.scompare( ccc6, sss6 ), 0 );
        assertEquals( "compare( sss6, ccc6 )", C.scompare( sss6, ccc6 ), 0 );
    }

    public void testSerialization_BasicTypes() // -----------------------------
        throws IOException, ClassNotFoundException
    {
        final BasicTypes v1 = new BasicTypes( true, 9, "zozo" );

        System.out.println( "v1 = " + v1 );

        assertEquals( "v1.compareTo( v1 )", v1.compareTo( v1 ), 0 );

        final BasicTypes v2 = SerializableHelper.clone( v1, BasicTypes.class );

        System.out.println( "v2 = " + v2 );

        assertEquals( "v1.compareTo( v2 )", v1.compareTo( v2 ), 0 );
    }
}
