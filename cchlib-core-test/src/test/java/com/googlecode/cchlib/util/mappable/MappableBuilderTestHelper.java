package com.googlecode.cchlib.util.mappable;

import java.util.Map;
import org.apache.log4j.Logger;

public class MappableBuilderTestHelper
{
    private static final Logger LOGGER = Logger.getLogger( MappableBuilderTestHelper.class );

    protected MappableBuilderFactory newMappableBuilder_TRY_PROTECTED_METHODS()
    {
        return new DefaultMappableBuilderFactory()
            .add( MappableItem.ALL_PRIMITIVE_TYPE )
            .add( MappableItem.DO_ARRAYS )
            .add( MappableItem.DO_PARENT_CLASSES )
            .add( MappableItem.TRY_PROTECTED_METHODS );
    }

    protected MappableBuilderFactory newMappableBuilder_TRY_PRIVATE_METHODS()
    {
        return new DefaultMappableBuilderFactory()
            .add( MappableItem.ALL_PRIMITIVE_TYPE )
            .add( MappableItem.DO_ARRAYS )
            .add( MappableItem.DO_PARENT_CLASSES )
            .add( MappableItem.TRY_PRIVATE_METHODS );
    }

    protected MappableBuilderFactory newMappableBuilder_only_public_METHODS()
    {
        return new DefaultMappableBuilderFactory()
            .add( MappableItem.ALL_PRIMITIVE_TYPE )
            .add( MappableItem.DO_ARRAYS )
            .add( MappableItem.DO_PARENT_CLASSES );
    }

    protected static void runTest(
        final MappableBuilderFactory factory,
        final Object                 object1,
        final Object                 object2
        )
    {
        final MappableBuilder      builder = new MappableBuilder( factory );
        final Map<String, String>  map1    = builder.toMap( object1 );
        final Map<String, String>  map2    = builder.toMap( object2 );

        LOGGER.info( "map1.entrySet().size(): " + map1.entrySet().size() );
        LOGGER.info( "map2.entrySet().size(): " + map2.entrySet().size() );

    }

    protected static void displayResult(
        final Map<String, String> map1,
        final Map<String, String> map2
        )
    {
        for( final Map.Entry<String,String> e : map2.entrySet() ) {
            final String key    = e.getKey();
            final String v2     = e.getValue();
            final String v1     = map1.get( key );
            boolean      nodiff;

            if( v2 == null ) {
                if( v1 == null ) {
                    nodiff = true;
                    }
                else {
                    nodiff = false;
                    }
                }
            else {
                if( v2.equals( v1 ) ) {
                    nodiff = true;
                    }
                else {
                    nodiff = false;
                    }
                }

            if( nodiff ) {
                LOGGER.trace( "no diff: " + key );
                }
            else {
                LOGGER.info(
                        "DIFF ."
                        + key
                        + " = "
                        + e.getValue()
                        + " was (" + v1 + ")"
                        );
                }
            }
    }

}
