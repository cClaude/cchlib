package com.googlecode.cchlib.util.mappable;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.data.MapEntry.entry;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MappableTest
{
    private static final Logger LOGGER = Logger.getLogger( MappableTest.class );

    private final MappableBuilder defaultMB = MappableBuilder.createMappableBuilder();

    private final MappableBuilder defCopyMB = new MappableBuilder(
        new DefaultMappableBuilderFactory()
            .setMethodesNamePattern( DefaultMappableBuilderFactory.DEFAULT_METHODS )
            .add( MappableTypes.ALL_TYPES )
            .add( MappableItem.MAPPABLE_ITEM_SHOW_ALL )
            );

    private final MappableBuilder excessiveMB = new MappableBuilder( // excessive
        new DefaultMappableBuilderFactory()
            .setMethodesNamePattern( ".*" )
            .add( MappableTypes.STANDARD_TYPES )
            .add( MappableItem.MAPPABLE_ITEM_SHOW_ALL )
            );

    private final MappableBuilder lightMB = new MappableBuilder( // light
        new DefaultMappableBuilderFactory()
            .setMethodesNamePattern( DefaultMappableBuilderFactory.DEFAULT_METHODS )
            .add( MappableTypes.STANDARD_TYPES )
            .add( MappableItem.MAPPABLE_ITEM_SHOW_ALL )
            );

    private final MappableBuilder smartMB = new MappableBuilder(
        new DefaultMappableBuilderFactory()
            .setMethodesNamePattern( DefaultMappableBuilderFactory.DEFAULT_METHODS )
            .add( MappableTypes.BASIC_TYPES )
            .add( MappableItem.MAPPABLE_ITEM_SHOW_ALL )
            .setStringNullValue( "" )
            .setMessageFormatMethodName( "{0}" )
            );

    @Test
    public void test_StringBuilder_defaultMB()
    {
        final StringBuilder object = new StringBuilder( "test" );

        final Map<String, String> map = this.defaultMB.toMap( object );
        LOGGER.info( "test_StringBuilder_defaultMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 1 );
        assertThat( map ).containsKey( "getClass()" );
        assertThat( map ).containsValue( "class java.lang.StringBuilder" );
    }

    @Test
    public void test_StringBuilder_defCopyMB()
    {
        final StringBuilder object = new StringBuilder( "test" );

        final Map<String, String> map = this.defCopyMB.toMap( object );
        LOGGER.info( "test_StringBuilder_defCopyMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 1 );
        assertThat( map ).containsKey( "getClass()" );
        assertThat( map ).containsValue( "class java.lang.StringBuilder" );
    }

    @Test
    public void test_StringBuilder_lightMB()
    {
        final StringBuilder object = new StringBuilder( "test" );

        final Map<String, String> map = this.lightMB.toMap( object );
        LOGGER.info( "test_StringBuilder_lightMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 0 );
    }

    @Test
    public void test_StringBuilder_excessiveMB()
    {
        final StringBuilder object = new StringBuilder( "test" );

        final Map<String, String> map = this.excessiveMB.toMap( object );
        LOGGER.info( "test_StringBuilder_excessiveMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 8 );
        assertThat( map ).containsKey( "capacity()" );
        assertThat( map ).containsKey( "hashCode()" );
        assertThat( map ).containsKey( "length()" );
        assertThat( map ).containsKey( "notify()" );
        assertThat( map ).containsKey( "notifyAll()" );
        assertThat( map ).containsKey( "toString()" );
        assertThat( map ).containsKey( "trimToSize()" );
        assertThat( map ).containsKey( "wait()" );

        assertThat( map ).contains(
            entry( "length()", "4" ),
            entry( "toString()", "test" )
            );
    }

    @Test
    public void test_MBTestObject_defaultMB()
    {
        final MBTestObject object = new MBTestObject();

        final Map<String, String> map = this.defaultMB.toMap( object );
        LOGGER.info( "test_MBTestObject_defaultMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 7 );
        assertThat( map ).containsKey( "getClass()" );
        assertThat( map ).containsKey( "getInteger()" );
        assertThat( map ).containsKey( "getListOfString()" );
        assertThat( map ).containsKey( "getListOfString2()" );
        assertThat( map ).containsKey( "getNullString()" );
        assertThat( map ).containsKey( "getString()" );
        assertThat( map ).containsKey( "isBool()" );

        assertThat( map ).contains(
            entry( "getClass()", "class com.googlecode.cchlib.util.mappable.MBTestObject" ),
            entry( "getInteger()", "-10" ),
            entry( "getNullString()", null ),
            entry( "getString()", "string" ),
            entry( "isBool()", "true" )
            );
    }

    @Test
    public void test_MBTestObject_defCopyMB()
    {
        final MBTestObject object = new MBTestObject();

        final Map<String, String> map = this.defCopyMB.toMap( object );
        LOGGER.info( "test_MBTestObject_defCopyMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 7 );
        assertThat( map ).containsKey( "getClass()" );
        assertThat( map ).containsKey( "getInteger()" );
        assertThat( map ).containsKey( "getListOfString()" );
        assertThat( map ).containsKey( "getListOfString2()" );
        assertThat( map ).containsKey( "getNullString()" );
        assertThat( map ).containsKey( "getString()" );
        assertThat( map ).containsKey( "isBool()" );

        assertThat( map ).contains(
            entry( "getClass()", "class com.googlecode.cchlib.util.mappable.MBTestObject" ),
            entry( "getInteger()", "-10" ),
            entry( "getNullString()", null ),
            entry( "getString()", "string" ),
            entry( "isBool()", "true" )
            );
    }

    @Test
    public void test_MBTestObject0_lightMB()
    {
        final MBTestObject object = new MBTestObject();

        final Map<String, String> map = this.lightMB.toMap( object );
        LOGGER.info( "test_MBTestObject0_lightMB() map = " + map );

        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 5 );
        assertThat( map ).containsKey( "getInteger()" );
        assertThat( map ).containsKey( "getListOfString()" );
        assertThat( map ).containsKey( "getString()" );
        assertThat( map ).containsKey( "getNullString()" );
        assertThat( map ).containsKey( "isBool()" );

        assertThat( map ).contains(
            entry( "getInteger()", "-10" ),
            entry( "getListOfString()", "[Str1, Str2]" ),
            entry( "getString()", "string" ),
            entry( "getNullString()", null ),
            entry( "isBool()", "true" )
            );
    }

    @Test
    public void test_MBTestObject2_lightMB()
    {
        final MBTestObject2 object = new MBTestObject2();

        final Map<String, String> map = this.lightMB.toMap( object, MBTestObject.class );

        LOGGER.info( "test_MBTestObject2_lightMB() map = " + map );
        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 5 );
        assertThat( map ).containsKey( "getInteger()" );
        assertThat( map ).containsKey( "getListOfString()" );
        assertThat( map ).containsKey( "getString()" );
        assertThat( map ).containsKey( "getNullString()" );
        assertThat( map ).containsKey( "isBool()" );

        assertThat( map ).contains(
            entry( "getInteger()", "-10" ),
            entry( "getListOfString()", "[Str1, Str2]" ),
            entry( "getString()", "string" ),
            entry( "getNullString()", null ),
            entry( "isBool()", "true" )
            );
    }

    @Test
    public void test_MBTestObject22_lightMB()
    {
        final MBTestObject2 object = new MBTestObject2();

        final Map<String, String> map = this.lightMB.toMap( object, MBTestObject2.class );

        LOGGER.info( "test_MBTestObject2_lightMB() map = " + map );
        assertThat( map ).isNotNull();
        assertThat( map ).hasSize( 6 );
        assertThat( map ).containsKey( "getInteger()" );
        assertThat( map ).containsKey( "getListOfString()" );
        assertThat( map ).containsKey( "getString()" );
        assertThat( map ).containsKey( "getNullString()" );
        assertThat( map ).containsKey( "isBool()" );
        assertThat( map ).containsKey( "getMBTestObject2()" );

        assertThat( map ).contains(
            entry( "getInteger()", "-10" ),
            entry( "getListOfString()", "[Str1, Str2]" ),
            entry( "getString()", "string" ),
            entry( "getNullString()", null ),
            entry( "isBool()", "true" ),
            entry( "getMBTestObject2()", "MBTestObject2" )
            );
    }

    @Test
    public void test_MBTestObject_excessiveMB()
    {
        final MBTestObject object = new MBTestObject();

        final Map<String, String> map = this.excessiveMB.toMap( object );
        LOGGER.info( "test_MBTestObject_excessiveMB() map = " + map );

        assertThat( map ).isNotNull();

        assertThat( map ).hasSize( 10 );
        assertThat( map ).containsKey( "getInteger()" );
        assertThat( map ).containsKey( "getListOfString()" );

        assertThat( map ).containsKey( "getString()" );
        assertThat( map ).containsKey( "getNullString()" );
        assertThat( map ).containsKey( "hashCode()" );

        assertThat( map ).containsKey( "isBool()" );
        assertThat( map ).containsKey( "notify()" );

        assertThat( map ).containsKey( "notifyAll()" );
        assertThat( map ).containsKey( "toString()" );
        assertThat( map ).containsKey( "wait()" );

        assertThat( map ).contains(
            entry( "getInteger()", "-10" ),
            entry( "getListOfString()", "[Str1, Str2]" ),
            entry( "getString()", "string" ),

            entry( "getNullString()", null ),
            entry( "hashCode()", "1262" ),

            entry( "isBool()", "true" ),
            entry( "notify()", null ),

            entry( "notifyAll()", null ),
            entry( "toString()", "MBTestObject [bool=true]" ),
            entry( "wait()", null )
            );
    }

    @Test
    public void test_MBTestObject_smartMB()
    {
        final MBTestObject object = new MBTestObject();

        final Map<String, String> map = this.smartMB.toMap( object );
        LOGGER.info( "test_MBTestObject_smartMB() map = " + map );

        assertThat( map ).isNotNull();

        assertThat( map ).hasSize( 5 );
        assertThat( map ).containsKey( "getInteger" );
        assertThat( map ).containsKey( "getListOfString" );
        assertThat( map ).containsKey( "getString" );

        assertThat( map ).containsKey( "getNullString" );
        assertThat( map ).containsKey( "isBool" );

        assertThat( map ).contains(
            entry( "getInteger", "-10" ),
            entry( "getListOfString", "[Str1, Str2]" ),
            entry( "getString", "string" ),

            entry( "getNullString", null ),
            entry( "isBool", "true" )
            );
    }
}
