/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/io/SerializationTest.java
** Description   :
**
**  3.00.001 2006.01.31 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.io.SerializationTest
**
*/
package cx.ath.choisnet.io;

// import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class SerializationTest extends TestCase
{
protected final A getA1() { return new A( "a1" ); }
protected final A getA2() { return new A( "a2" ); }
protected final B getB3() { return new B( "b3" ); }
protected final C getC4() { return new C( "c4" ); }
protected final C getC5() { return new C( "c5" ); }

//ArrayIterator

//  protected int fValue1;
//  protected int fValue2;
//
//  protected void setUp() {
//      fValue1= 2;
//      fValue2= 3;
//  }

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( SerializationTest.class );
}


/**
** <T> T Serialization.clone( T anObject, Class<T> clazz )
*/
public void testSerialization1() // ---------------------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 A a1 = getA1();
 A s1 = Serialization.clone( a1, A.class );

 System.out.println( "----" );
 System.out.println( "a1 = " + a1 );
 System.out.println( "s1 = " + s1 );

 assertEquals( "compare( a1, a1 )", A.scompare( a1, a1 ), 0 );
 assertEquals( "compare( a1, s1 )", A.scompare( a1, s1 ), 0 );
}


/**
**
*/
public void testSerialization2() // ---------------------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 B b3 = getB3();
 A a2 = getA1().setB( b3 );
 A s2 = Serialization.clone( a2, A.class );

 System.out.println( "----" );
 System.out.println( "a2 = " + a2 );
 System.out.println( "s2 = " + s2 );

 assertEquals( "compare( a2, s2 )", A.scompare( a2, s2 ), 0 );
}

/**
**
*/
public void testSerialization3() // ---------------------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 B b3  = getB3();
 A ab3 = b3.setB( b3 );
 A ss3 = Serialization.clone( ab3, A.class );

 System.out.println( "----" );
 System.out.println( "b3  = " + b3 );
 System.out.println( "ab3 = " + ab3 );
 System.out.println( "ss3 = " + ss3 );

 assertEquals( "compare( ab3, ab3 )", A.scompare( ab3, ab3 ), 0 );
 assertEquals( "compare( ab3, ss3 )", A.scompare( ab3, ss3 ), 0 );
 assertEquals( "compare( ss3, ab3 )", A.scompare( ss3, ab3 ), 0 );
}

/**
**
*/
public void testSerialization4() // ---------------------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 C c4 = getC4();
 C s4 = Serialization.clone( c4, C.class );

 System.out.println( "----" );
 System.out.println( "c4 = " + c4 );
 System.out.println( "s4 = " + s4 );

 assertEquals( "compare( c4, s4 )", C.scompare( c4, s4 ), 0 );
 assertEquals( "compare( s4, c4 )", C.scompare( s4, c4 ), 0 );
}

/**
**
*/
public void testSerialization5() // ---------------------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 A a5 = getA1();
 C c5 = getC4().setA( a5 );
 C s5 = Serialization.clone( c5, C.class );

 System.out.println( "----" );
 System.out.println( "a5 = " + a5 );
 System.out.println( "c5 = " + c5 );
 System.out.println( "s5 = " + s5 );

 assertEquals( "compare( c5, c5 )", C.scompare( c5, c5 ), 0 );
 assertEquals( "compare( c5, s5 )", C.scompare( c5, s5 ), 0 );
 assertEquals( "compare( s5, c5 )", C.scompare( s5, c5 ), 0 );
}

/**
**
*/
public void testSerialization6() // ---------------------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 A  a6 = getA1();
 C  c6 = getC4().setA( a6 );
 A aa6 = getA2().setC( c6 );
 A ss6 = Serialization.clone( aa6, A.class );

 System.out.println( "----" );
 System.out.println( "a6  = " + a6 );
 System.out.println( "c6  = " + c6 );
 System.out.println( "aa6 = " + aa6 );
 System.out.println( "ss6 = " + ss6 );
 System.out.println( "ss6 =>" + ss6.getFullContent( 80 ) );

 assertEquals( "compare( aa6, ss6 )", A.scompare( aa6, ss6 ), 0 );

 C ccc6 = c6.setA( aa6 );
// System.out.println( "ccc6= " + ccc6 ); // Plante (boucle)
 System.out.println( "ccc6=> " + ccc6.getFullContent( 80 ) );

 C sss6 = Serialization.clone( ccc6, C.class );
// System.out.println( "sss6= " + sss6 ); // Plante (boucle)
 System.out.println( "sss6=> " + sss6.getFullContent( 80 ) );

 assertEquals( "compare( ccc6, sss6 )", C.scompare( ccc6, sss6 ), 0 );
 assertEquals( "compare( sss6, ccc6 )", C.scompare( sss6, ccc6 ), 0 );
}

/**
**
*/
public void testSerialization_BasicTypes() // -----------------------------
    throws
        java.io.IOException,
        java.lang.ClassNotFoundException
{
 BasicTypes v1 = new BasicTypes( true, 9, "zozo" );

 System.out.println( "v1 = " + v1 );

 assertEquals( "v1.compareTo( v1 )", v1.compareTo( v1 ), 0 );

 BasicTypes v2 = Serialization.clone( v1, BasicTypes.class );

 System.out.println( "v2 = " + v2 );

 assertEquals( "v1.compareTo( v2 )", v1.compareTo( v2 ), 0 );
}

} // class


abstract class AbstractComputeString
{
    abstract public String getContent();
    abstract public String getSubContent( int maxlength );

    public String getFullContent( int maxlength )
    {
        StringBuilder   sb  = new StringBuilder( getContent() );
        int             len = sb.length();

        if( len < maxlength ) {
            sb.append( getSubContent( maxlength - len ) );
            }

        return sb.toString();
    }

}

class A
    extends AbstractComputeString
        implements
            java.io.Serializable,
            java.util.Comparator<A>
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String  content;
    private String  hashCodeString;
    private B       b;
    private C       c;

    public A( String content )
    {
     this.content           = content;
     this.b                 = null;
     this.c                 = null;
     this.hashCodeString    = "HC:" + this.hashCode();
    }

    public A setB( B b )
    {
     this.b = b;

     return this;
    }

    public A setC( C c )
    {
     this.c = c;

     return this;
    }

    public C getC()
    {
     return this.c;
    }

    public A getCA()
    {
     final C c = this.getC();

     return c == null ? null : c.getA();
    }

    @Override
    public String getContent()
    {
     return this.content;
    }

    @Override
    public String getSubContent( int maxlength )
    {
     StringBuilder sb = new StringBuilder( " B:" );

     sb.append( this.b == null ? "*" : this.b.getFullContent( maxlength - 3 ) );

     sb.append( "; C:" );

     int len = sb.length();

     sb.append( this.c == null ? "*" : this.c.getFullContent( maxlength - len ) );

    return sb.toString();
    }

    @Override
    public String toString()
    {
     return "{" + this.getClass().getSimpleName()
                + "@"  + this.hashCode()
                + " :" + this.getContent()
                + ","  + this.hashCodeString
                + " (B:" + (
                        this.b == null ? "NULL" : this.b.hashCode()
                        )
                + ")(C:" + (
                        this.c == null ? "NULL" : this.c.toString()
                        )
                + ")}";
    }

    public final static int compare( final String s1, final String s2 )
    {
        if( s1 == null ) {
            if( s2 == null ) {
                return 0;
                }
            else {
                return -1;
                }
            }
        else {
            if( s2 == null ) {
                return 1;
                }
            else {
                return s1.compareTo( s2 );
                }
            }
    }

    @Override
    public int compare( final A a1, final A a2 )
    {
     return scompare( a1, a2 );
    }

    public static int scompare( final A a1, final A a2 )
    {
        if( a1 == a2 ) { return 0; }

        if( a1 == null ) {
            if( a2 == null ) {
                return 0;
                }
            else {
                return -1;
                }
            }
        else {
            if( a2 == null ) {
                return 1;
                }
            else {
                int res = A.compare( a1.content, a2.content );

                if( res == 0 ) {
                    res = A.compare( a1.hashCodeString, a2.hashCodeString );
                    }

                // Traitement du champ : B b - anti recursion infinie
                if( res == 0 ) {
                    if( a1 != a1.b || a2 != a2.b ) {
                        res = A.scompare( a1.b, a2.b );
                        }
                    }

                // Traitement du champ : C c - anti recursion infinie
                if( res == 0 ) {
                    if( a1 != a1.getCA() || a2 != a2.getCA() ) {
                        res = C.scompare( a1.c, a2.c );
                        }
                    }

                return res;
                }
            }
    }
}

class B extends A
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public B( String content )
    {
     super( content );
    }
}


class C
    extends AbstractComputeString
        implements
            java.io.Serializable,
            java.util.Comparator<C>
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String  content;
    private String  hashCodeString;
    private A       a;

    public C( String content )
    {
     this.content           = content;
     this.hashCodeString    = "HC:" + this.hashCode();
     this.a                 = null;
    }

    public C setA( A a )
    {
     this.a = a;

     return this;
    }

    public A getA()
    {
     return this.a;
    }

    @Override
    public String getContent()
    {
     return this.content;
    }

    @Override
    public String getSubContent( int maxlength )
    {
     return " A:" +
            (this.a == null ? "*" :this.a.getFullContent( maxlength - 3 ));
    }

    @Override
    public int compare( final C c1, final C c2 )
    {
        return scompare( c1, c2 );
    }

    public static int scompare( final C c1, final C c2 )
    {
        if( c1 == c2 ) { return 0; }

        if( c1 == null ) {
            if( c2 == null ) {
                return 0;
                }
            else {
                return -1;
                }
            }
        else {
            if( c2 == null ) {
                return 1;
                }
            else {
                int res = A.compare( c1.content, c2.content );

                if( res == 0 ) {
                    res = A.compare( c1.hashCodeString, c2.hashCodeString );
                    }

                if( res == 0 ) {
                    res = A.scompare( c1.a, c2.a );
                    }

                return res;
                }
            }
    }

    @Override
    public String toString()
    {
     return "{" + this.getClass().getSimpleName()
                + "@" + this.hashCode()
                + " :" + this.content
                + "," + this.hashCodeString
                + " (A:" + (
                        this.a == null ? "NULL" : this.a.toString()
                        )
                + ")}";
    }
}

class BasicTypes implements java.io.Serializable, Comparable<BasicTypes>
{
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String      content;
    private boolean     bool;
    private boolean     notBool;
    private int         oInterger;
    private long        oLong;
    private int[]       arrayOfInt = new int[ 5];

    public BasicTypes( boolean bool, int anInteger, String content )
    {
        this.bool       = bool;
        this.notBool    = ! bool;
        this.oInterger  = anInteger;
        this.oLong      = anInteger * 1000001L;
        this.content    = content;

        for( int i = 0; i< this.arrayOfInt.length; i++ ) {
            this.arrayOfInt[ i ] = i + anInteger;
            }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder( "{" + this.bool
                                                + ";" + this.notBool
                                                + ";" + this.oInterger
                                                + ";" + this.oLong
                                                + ";" + this.content
                                                +"}"
                                                );

        for( int i = 0; i< this.arrayOfInt.length; i++ ) {
            sb.append( ";" + this.arrayOfInt[ i ] );
            }

        sb.append( "}" );

        return sb.toString();
    }

    @Override
    public int compareTo( BasicTypes anOtherTboolean )
    {
        if( this.bool == anOtherTboolean.bool ) {
            if( this.notBool == anOtherTboolean.notBool ) {
                if( this.oInterger == anOtherTboolean.oInterger ) {
                    if( this.oLong == anOtherTboolean.oLong ) {
                        return this.content.compareTo( anOtherTboolean.content );
                        }
                    }
                }
            }

        return -1;
    }

}
