package com.googlecode.cchlib.io.serializable;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.io.SerializableHelper;

public class SerializableHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( SerializableHelperTest.class );

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

    @Test
    public void testSerialization1() throws IOException, ClassNotFoundException
    {
        final A a1 = getA1();
        final A s1 = SerializableHelper.clone( a1, A.class );

        LOGGER.info( "----" );
        LOGGER.info( "a1 = " + a1 );
        LOGGER.info( "s1 = " + s1 );

        final int cmp_a1_a1 = A.scompare( a1, a1 );
        final int cmp_a1_s1 = A.scompare( a1, s1 );
        final int cmp_s1_a1 = A.scompare( s1, a1 );

        assertThat( cmp_a1_a1 ).as( "compare( a1, a1 )" ).isZero();
        assertThat( cmp_a1_s1 ).as( "compare( a1, s1 )" ).isZero();
        assertThat( cmp_s1_a1 ).as( "compare( s1, a1 )" ).isZero();
    }

    @Test
    public void testSerialization2() throws IOException, ClassNotFoundException
    {
        final B b3 = getB3();
        final A a2 = getA1().setB( b3 );
        final A s2 = SerializableHelper.clone( a2, A.class );

        LOGGER.info( "----" );
        LOGGER.info( "a2 = " + a2 );
        LOGGER.info( "s2 = " + s2 );

        final int cmp_a2_s2 = A.scompare( a2, s2 );

        assertThat( cmp_a2_s2 ).as( "compare( a2, s2 )" ).isZero();
    }

    @Test
    public void testSerialization3() throws IOException, ClassNotFoundException
    {
        final B b3 = getB3();
        final A ab3 = b3.setB( b3 );
        final A ss3 = SerializableHelper.clone( ab3, A.class );

        LOGGER.info( "----" );
        LOGGER.info( "b3  = " + b3 );
        LOGGER.info( "ab3 = " + ab3 );
        LOGGER.info( "ss3 = " + ss3 );

        final int cmp_ab3_ab3 = A.scompare( ab3, ab3 );
        assertThat( cmp_ab3_ab3 ).as( "compare( ab3, ab3 )" ).isZero();

        final int cmp_ab3_ss3 = A.scompare( ab3, ss3 );
        assertThat( cmp_ab3_ss3 ).as( "compare( ab3, ss3 )" ).isZero();

        final int cmp_ss3_ab3 = A.scompare( ss3, ab3 );
        assertThat( cmp_ss3_ab3 ).as( "compare( ss3, ab3 )").isZero();
    }

    @Test
    public void testSerialization4() throws IOException, ClassNotFoundException
    {
        final C c4 = getC4();
        final C s4 = SerializableHelper.clone( c4, C.class );

        LOGGER.info( "----" );
        LOGGER.info( "c4 = " + c4 );
        LOGGER.info( "s4 = " + s4 );

        final int cmp_c4_s4 = C.scompare( c4, s4 );
        assertThat( cmp_c4_s4 ).as( "compare( c4, s4 )" ).isZero();
        final int cmp_s4_c4 = C.scompare( s4, c4 );
        assertThat( cmp_s4_c4 ).as( "compare( s4, c4 )" ).isZero();
    }

    @Test
    public void testSerialization5() throws IOException, ClassNotFoundException
    {
        final A a5 = getA1();
        final C c5 = getC4().setA( a5 );
        final C s5 = SerializableHelper.clone( c5, C.class );

        LOGGER.info( "----" );
        LOGGER.info( "a5 = " + a5 );
        LOGGER.info( "c5 = " + c5 );
        LOGGER.info( "s5 = " + s5 );

        assertThat( C.scompare( c5, c5 ) ).as( "compare( c5, c5 )" ).isZero();
        assertThat( C.scompare( c5, s5 ) ).as( "compare( c5, s5 )" ).isZero();
        assertThat( C.scompare( s5, c5 ) ).as( "compare( s5, c5 )" ).isZero();
    }

    @Test
    public void testSerialization6() throws IOException, ClassNotFoundException
    {
        final A a6 = getA1();
        final C c6 = getC4().setA( a6 );
        final A aa6 = getA2().setC( c6 );
        final A ss6 = SerializableHelper.clone( aa6, A.class );

        LOGGER.info( "----" );
        LOGGER.info( "a6  = " + a6 );
        LOGGER.info( "c6  = " + c6 );
        LOGGER.info( "aa6 = " + aa6 );
        LOGGER.info( "ss6 = " + ss6 );
        LOGGER.info( "ss6 =>" + ss6.getFullContent( 80 ) );

        assertThat( A.scompare( aa6, ss6 ) ).as( "compare( aa6, ss6 )" ).isZero();

        final C ccc6 = c6.setA( aa6 );
        // LOGGER.info( "ccc6= " + ccc6 ); // Plante (boucle)
        LOGGER.info( "ccc6=> " + ccc6.getFullContent( 80 ) );

        final C sss6 = SerializableHelper.clone( ccc6, C.class );
        // LOGGER.info( "sss6= " + sss6 ); // Plante (boucle)
        LOGGER.info( "sss6=> " + sss6.getFullContent( 80 ) );

        assertThat( C.scompare( ccc6, sss6 ) ).as( "compare( ccc6, sss6 )" ).isZero();
        assertThat( C.scompare( sss6, ccc6 ) ).as( "compare( sss6, ccc6 )" ).isZero();
    }

    @Test
    public void testSerialization_BasicTypes() throws IOException, ClassNotFoundException
    {
        final BasicTypes v1 = new BasicTypes( true, 9, "zozo" );

        LOGGER.info( "v1 = " + v1 );

        assertThat( v1.compareTo( v1 ) ).as( "v1.compareTo( v1 )" ).isZero();

        final BasicTypes v2 = SerializableHelper.clone( v1, BasicTypes.class );

        LOGGER.info( "v2 = " + v2 );

        assertThat( v1.compareTo( v2 ) ).as( "v1.compareTo( v2 )" ).isZero();
    }
}
