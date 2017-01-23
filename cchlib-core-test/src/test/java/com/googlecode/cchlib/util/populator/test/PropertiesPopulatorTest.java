package com.googlecode.cchlib.util.populator.test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.entry;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.util.populator.MapPopulator;

public class PropertiesPopulatorTest
{
    private static final Logger LOGGER = Logger.getLogger( PropertiesPopulatorTest.class );

    @Test
    public void test_bean_to_map()
    {
        final MapPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new MapPopulator<>( BeanAnnotationOnGettersHeadLess.class );

        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess(
                "test_bean_to_map",
                42,
                3.1415F,
                new boolean[] { true, false }
                );

        final Map<String, String> map = new HashMap<>();

        pp.populateMap( bean, map );

        LOGGER.info( "test_bean_to_map() : map = " + map );

        assertThat( map ).hasSize( 5 );
        assertThat( map ).contains(
                entry( "aInt", "42" ),
                entry( "aString", "test_bean_to_map" ),
                entry( "aFloat", "3.1415" ),

                entry( "SomeBooleans.0", "true" ),
                entry( "SomeBooleans.1", "false" )
                );
    }

    @Test
    public void test_bean2_to_map()
    {
        final MapPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new MapPopulator<>( BeanAnnotationOnGettersHeadLess.class );

        final BeanAnnotationOnGettersHeadLess bean2
            = new BeanAnnotationOnGettersHeadLess(
                "test_bean2_to_map",
                42,
                3.1415F,
                null
                );

        final Map<String, String> map = new HashMap<>();

        pp.populateMap( bean2, map );

        LOGGER.info( "test_bean2_to_map() : map = " + map );

        assertThat( map ).hasSize( 3 );
        assertThat( map ).contains(
                entry( "aInt", "42" ),
                entry( "aString", "test_bean2_to_map" ),
                entry( "aFloat", "3.1415" )
                );
    }

    @Test
    public void test_null_to_map() throws InstantiationException, IllegalAccessException
    {
        final MapPopulator<BeanAnnotationHeadLess> pp
            = new MapPopulator<>( BeanAnnotationOnGettersHeadLess.class );

        final Map<String, String> map = pp.newMapForBean( BeanAnnotationOnGettersHeadLess.class );

        LOGGER.info( "test_bean_to_map() : map = " + map );

        assertThat( map ).hasSize( 3 );
        assertThat( map ).contains(
                entry( "aInt", "0" ),
                entry( "aString", "" ),
                entry( "aFloat", "0.0" )
                );
    }

    @Test
    public void test_bean_to_map_with_prefix()
    {
        final MapPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new MapPopulator<>( BeanAnnotationOnGettersHeadLess.class );

        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess(
                "test_bean_to_map",
                42,
                3.1415F,
                new boolean[] { true, false }
                );

        final Map<String, String> map = new HashMap<>();

        pp.populateMap( "prefix.", bean, map );

        LOGGER.info( "test_bean_to_map_with_prefix() : map = " + map );

        assertThat( map ).hasSize( 5 );
        assertThat( map ).contains(
                entry( "prefix.aInt", "42" ),
                entry( "prefix.aString", "test_bean_to_map" ),
                entry( "prefix.aFloat", "3.1415" ),

                entry( "prefix.SomeBooleans.0", "true" ),
                entry( "prefix.SomeBooleans.1", "false" )
                );
    }

    @Test
    public void test_map_to_bean()
    {
        final MapPopulator<BeanAnnotationOnGettersHeadLess> pp
            = new MapPopulator<>( BeanAnnotationOnGettersHeadLess.class );

        final Map<String, String> map = new HashMap<>();

        map.put( "aInt", "42" );
        map.put( "aString", "test_map_to_bean" );
        map.put( "aFloat", "3.1415" );

        map.put( "SomeBooleans.0", "true" );
        map.put( "SomeBooleans.1", "false" );

        final BeanAnnotationOnGettersHeadLess bean
            = new BeanAnnotationOnGettersHeadLess();

        pp.populateBean( map, bean );

        LOGGER.info( "test_map_to_bean() : bean = " + bean );

        assertThat( bean.getaInt() ).isEqualTo( 42 );
        assertThat( bean.getaString() ).isEqualTo( "test_map_to_bean" );
        assertThat( bean.getaFloat() ).isEqualTo( 3.1415F );

        assertThat( bean.getSomeBooleans() ).hasSize( 2 );
        assertThat( bean.getSomeBooleans() ).containsSequence( true, false );
    }
}
