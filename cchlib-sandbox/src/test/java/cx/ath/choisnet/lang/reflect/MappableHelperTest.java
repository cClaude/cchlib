/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/lang/reflect/MappableHelperTest.java
** Description   :
** Encodage      : ANSI
**
**  3.01.012 2006.03.27 Claude CHOISNET - version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.lang.reflect.MappableHelperTest
*/
package cx.ath.choisnet.lang.reflect;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
**
*/
public class MappableHelperTest extends TestCase
{
/**
**
*/
public final static String[] STRINGS = { "str1", "str2" };

/**
**
*/
public final static String[] STRINGS_2 = { "Astr1", "Astr2" };

/**
**
*/
private final static Class<?>[] DEFAULT_RETURN_TYPE_CLASSES = {
    String.class,
    java.io.File.class,
    java.net.URL.class
    };

/**
**
*/
private final static java.util.regex.Pattern DEFAULT_PATTERN =
    java.util.regex.Pattern.compile( "(get|is).*" );


/**
**
*/
protected void setUp() // -------------------------------------------------
{
// this.globalMap = ;
}

/**
**
*/
public static Test suite() // ---------------------------------------------
{
 return new TestSuite( MappableHelperTest.class );
}

/**
**
*/
public void testToMap() // ------------------------------------------------
{
 final TestMap              testMap = new TestMap();
 final Map<String,String>   aMap    = testMap.toMap();

 assertEquals( "Size : " + aMap, 4, aMap.size() );
 assertEquals( "getClassName() <> getClassName0() : "
                    + testMap.getClassName()
                    + " - "
                    + testMap.getClassName0(),
                testMap.getClassName(),
                testMap.getClassName0()
                );
}

/**
**
*/
public void testToXAMap() // ----------------------------------------------
{
 final TestMap              testMap = new TestMap();
 final Map<String,String>   aMap    = testMap.toXAMap();

 assertEquals( "Size : " + aMap, 6, aMap.size() );
}

/**
**
*/
public void testToXPMap() // ----------------------------------------------
{
 final TestMap              testMap = new TestMap();
 final Map<String,String>   aMap    = testMap.toXPMap();

 assertEquals( "Size : " + aMap, 4, aMap.size() );
}

/**
**
*/
public void testToXAPMap() // ---------------------------------------------
{
 final TestMap              testMap = new TestMap();
 final Map<String,String>   aMap    = testMap.toXAPMap();

 assertEquals( "Size : " + aMap, 6, aMap.size() );
}

/**
**
*/
public void testDO_PARENT_CLASSES() // ------------------------------------
{
 final TestMap              testMap = new TestMap1();
 final Map<String,String>   aMap    = testMap.toXPMap();

 String className       = testMap.getClassName();
 String classNameValue  = aMap.get( "getClassName()" );

 assertEquals( "getClassName() <> toMap().getClassName() : "
                    + className
                    + " - "
                    + classNameValue,
                className,
                classNameValue
                );

 String className0      = testMap.getClassName0();
 String className0Value = aMap.get( "getClassName0()" );

 assertEquals( "getClassName0() <> toMap().getClassName0() : "
                    + className0
                    + " - "
                    + className0Value,
                className0,
                className0Value
                );


 assertEquals( "Size : " + aMap, 4, aMap.size() );
}


/**
**
*/
public void testTestMap() // ----------------------------------------------
{
 final TestMap testMap = new TestMap();

 toX_Map( testMap, testMap.toMap()   , false );
 toX_Map( testMap, testMap.toXAMap() , false );
 toX_Map( testMap, testMap.toXPMap() , false );
 toX_Map( testMap, testMap.toXAPMap(), false );
}

/**
**
*/
public void testTestMap1() // ----------------------------------------------
{
 final TestMap testMap = new TestMap1();

 toX_Map( testMap, testMap.toMap()   , true );
 toX_Map( testMap, testMap.toXAMap() , true );
 toX_Map( testMap, testMap.toXPMap() , false );
 toX_Map( testMap, testMap.toXAPMap(), false );
}

/**
**
*/
public void testTestMap2() // ----------------------------------------------
{
 final TestMap testMap = new TestMap2();

 toX_Map( testMap, testMap.toMap()          , true );
 toX_Map( testMap, testMap.toXAMap()        , true );
 toX_Map( testMap, testMap.toXPMap()        , false );
 toX_Map( testMap, testMap.toXAPMap()       , false );
 toX_Map( testMap, testMap.toIterableMap()  , false );
}

/**
**
*/
public void testTestMap3() // ----------------------------------------------
{
 final TestMap testMap = new TestMap3();

 toX_Map( testMap, testMap.toMap()   , true );
 toX_Map( testMap, testMap.toXAMap() , true );
 toX_Map( testMap, testMap.toXPMap() , false );
 toX_Map( testMap, testMap.toXAPMap(), false );
}

/**
**
*/
private void toX_Map( // --------------------------------------------------
    final TestMap               testMap,
    final Map<String,String>    aMap,
    final boolean               isNullAllowed
    )
{
 String className       = testMap.getClassName();
 String classNameValue  = aMap.get( "getClassName()" );

 assertEquals( "getClassName() <> toMap().getClassName() : "
                    + className
                    + " - "
                    + classNameValue,
                className,
                classNameValue
                );


 String className0      = testMap.getClassName0();
 String className0Value = aMap.get( "getClassName0()" );

 if( isNullAllowed ) {
    assertNull(
        "toMap().getClassName0() == null : " + className0Value,
        className0Value
        );
    }
 else {
    assertEquals( "getClassName0() <> toMap().getClassName0() : "
                    + className0
                    + " - "
                    + className0Value,
                className0,
                className0Value
                );
    }
}

    /**
    **
    */
    static class TestMap implements Mappable
    {
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    protected Collection<String> collection =
                new cx.ath.choisnet.util.ArrayCollection<String>( STRINGS_2 );
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public TestMap() {}
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public final static String getClassName( final Class clazz )
    {
        // return clazz.getName();
        // return clazz.getCanonicalName();
        return clazz.getSimpleName();
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName() { return getClassName( this.getClass() ); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName0() { return getClassName( this.getClass() ); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName1() { return "#TestMap"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getString() { return "string"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String[] getStringArray()
    {
        return STRINGS;
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Iterator<String> getStringIterator()
    {
        return new cx.ath.choisnet.util.ArrayIterator<String>( STRINGS_2 );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Enumeration<String> getStringEnumeration()
    {
        return new cx.ath.choisnet.util.EnumerationIterator<String>( getStringIterator() );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Map<String,String> toMap() //- - - - - - - - - - - - - - - - - -
    {
        return MappableHelper.toMap(
                new MappableHelperFactory()
                        .setMethodesNamePattern( DEFAULT_PATTERN )
                        .addClasses( DEFAULT_RETURN_TYPE_CLASSES )
                        .addAttributes( MappableHelper.Attributes.ALL_PRIMITIVE_TYPE ),
                this
                );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Map<String,String> toXAMap() //- - - - - - - - - - - - - - - - -
    {
        final MappableHelper aMappableHelper =
                new MappableHelperFactory()
                        .setMethodesNamePattern(
                            MappableHelperFactory.DEFAULT_METHODS
                            )
                        .addClasses(
                            MappableHelperFactory.STANDARDS_TYPES_CLASS
                            )
                        .addAttributes(
                            MappableHelper.Attributes.ALL_PRIMITIVE_TYPE,
                            MappableHelper.Attributes.DO_ARRAYS
                            )
                        .getInstance();

        return aMappableHelper.toMap( this );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Map<String,String> toXPMap() //- - - - - - - - - - - - - - - - -
    {
        final MappableHelper aMappableHelper =
                new MappableHelperFactory()
                        .setMethodesNamePattern(
                            MappableHelperFactory.DEFAULT_METHODS
                            )
                        .addClasses(
                            MappableHelperFactory.STANDARDS_TYPES_CLASS
                            )
                        .addAttributes(
                            MappableHelper.Attributes.ALL_PRIMITIVE_TYPE,
                            MappableHelper.Attributes.DO_PARENT_CLASSES
                            )
                        .getInstance();

        return aMappableHelper.toMap( this );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Map<String,String> toXAPMap() // - - - - - - - - - - - - - - - -
    {
        final MappableHelper aMappableHelper =
                new MappableHelperFactory()
                        .setMethodesNamePattern(
                            MappableHelperFactory.DEFAULT_METHODS
                            )
                        .addClasses(
                            MappableHelperFactory.STANDARDS_TYPES_CLASS
                            )
                        .addAttributes(
                            MappableHelper.Attributes.ALL_PRIMITIVE_TYPE,
                            MappableHelper.Attributes.DO_ARRAYS,
                            MappableHelper.Attributes.DO_PARENT_CLASSES
                            )
                        .getInstance();

        return aMappableHelper.toMap( this );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Map<String,String> toIterableMap() //- - - - - - - - - - - - - -
    {
        final MappableHelper aMappableHelper =
                new MappableHelperFactory()
                        .setMethodesNamePattern(
                            MappableHelperFactory.DEFAULT_METHODS
                            )
                        .addClasses(
                            MappableHelperFactory.STANDARDS_TYPES_CLASS
                            )
                        .addAttributes(
                            MappableHelper.Attributes.ALL_PRIMITIVE_TYPE,
                            MappableHelper.Attributes.DO_ARRAYS,
                            MappableHelper.Attributes.DO_PARENT_CLASSES,
                            MappableHelper.Attributes.DO_ITERABLE
                            )
                        .getInstance();

        return aMappableHelper.toMap( this );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    } // class

    /**
    **
    */
    static class TestMap1 extends TestMap implements Iterable<String>
    {
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public TestMap1() { super(); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName() { return getClassName( this.getClass() ); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName1() { return "#TestMap1"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Set<String> getStringSet()
    {
        return new HashSet<String>( this.collection );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Set<String> getStringSet2()
    {
        return Collections.unmodifiableSet( getStringSet() );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Iterator<String> getStringIterator()
    {
        return new cx.ath.choisnet.util.ArrayIterator<String>( STRINGS_2 );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Enumeration<String> getStringEnumeration()
    {
        return new cx.ath.choisnet.util.EnumerationIterator<String>( getStringIterator() );
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public Iterator<String> iterator()
    {
        return getStringIterator();
    }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    } // class

    /**
    **
    */
    static class TestMap2 extends TestMap
    {
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public TestMap2() { super(); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName() { return getClassName( this.getClass() ); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName1() { return "#TestMap2"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getString2() { return "string2"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    } // class

    /**
    **
    */
    static class TestMap3 extends TestMap
    {
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public TestMap3() { super(); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName() { return getClassName( this.getClass() ); }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getClassName1() { return "#TestMap3"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getString3() { return "string3"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public String getValue() { return "TestMap3"; }
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    } // class

/**
**
*/
public static void debug( // ----------------------------------------------
    final TestMap aTestMap
    )
{
 final String       className = aTestMap.getClass().getSimpleName();
 Map<String,String> currentMap;

 currentMap = aTestMap.toMap();

 System.out.println( "-- " + className + "().toMap().size() : " + currentMap.size() );
 System.out.println( "-- " + className + "().toMap() : " + currentMap );
 System.out.println( "-- " + className + "().getClassName() : " + aTestMap.getClassName() );
 System.out.println( "-- " + className + "().toMap().getClassName() > : " + currentMap.get( "getClassName()" ) );
 System.out.println( "" );

 currentMap = aTestMap.toXAMap();

 System.out.println( "-- " + className + "().toXAMap().size() : " + currentMap.size() );
 System.out.println( "-- " + className + "().toXAMap() : " + currentMap );
 System.out.println( "-- " + className + "().getClassName() : " + aTestMap.getClassName() );
 System.out.println( "-- " + className + "().toMap().getClassName() > : " + currentMap.get( "getClassName()" ) );
 System.out.println( "" );

 currentMap = aTestMap.toXPMap();

 System.out.println( "-- " + className + "().toXPMap().size() : " + currentMap.size() );
 System.out.println( "-- " + className + "().toXPMap() : " + currentMap );
 System.out.println( "-- " + className + "().getClassName() : " + aTestMap.getClassName() );
 System.out.println( "-- " + className + "().toMap().getClassName() > : " + currentMap.get( "getClassName()" ) );
 System.out.println( "" );

 currentMap = aTestMap.toXAPMap();

 System.out.println( "-- " + className + "().toXAPMap().size() : " + currentMap.size() );
 System.out.println( "-- " + className + "().toXAPMap() : " + currentMap );
 System.out.println( "-- " + className + "().getClassName() : " + aTestMap.getClassName() );
 System.out.println( "-- " + className + "().toMap().getClassName() > : " + currentMap.get( "getClassName()" ) );
 System.out.println( "" );

 currentMap = aTestMap.toIterableMap();

 System.out.println( "-- " + className + "().toIterableMap().size() : " + currentMap.size() );
 System.out.println( "-- " + className + "().toIterableMap() : " + currentMap );
 System.out.println( "-- " + className + "().getClassName() : " + aTestMap.getClassName() );
 System.out.println( "-- " + className + "().toMap().getClassName() > : " + currentMap.get( "getClassName()" ) );
}

/**
** .java cx.ath.choisnet.lang.reflect.MappableHelperTest
*/
public final static void main( final String[] arg ) // --------------------
{
 debug( new TestMap() );
 System.out.println( "--------------------------" );

 debug( new TestMap1() );
 System.out.println( "--------------------------" );

 debug( new TestMap2() );
 System.out.println( "--------------------------" );

 debug( new TestMap3() );
 System.out.println( "--------------------------" );
}

} // class


