package com.googlecode.cchlib.util.mappable;

import java.util.Map;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 */
public class MappableBuilderTest
{
    private final static Logger logger = Logger.getLogger( MappableHelperTest.class );

    @Test
    public void tstMappableJLabel()
    {
        final MappableBuilderFactory factory = new DefaultMappableBuilderFactory()
            .add( MappableItem.ALL_PRIMITIVE_TYPE )
            .add( MappableItem.DO_ARRAYS )
            .add( MappableItem.DO_PARENT_CLASSES )
            .add( MappableItem.TRY_PROTECTED_METHODS );
        final MappableBuilder		builder 	= new MappableBuilder( factory );
        final JLabel				object1 	= new JLabel( "testString" );
        final Map<String, String> 	map1		= builder.toMap( object1 );
/*
        for( Map.Entry<String,String> e : map1.entrySet() ) {
            logger.info(
                object1.getClass().getSimpleName()
                + "."
                + e.getKey()
                + " = "
                + e.getValue()
                );
            }
*/
        final JLabel					object2 = new JLabel( "testString2" );
        final Map<String, String> map2 	= builder.toMap( object2 );

        logger.info( ".entrySet().size(): " + map2.entrySet().size() );

        for( Map.Entry<String,String> e : map2.entrySet() ) {
            final String key	= e.getKey();
            final String v2 	= e.getValue();
            final String v1 	= map1.get( key );
            boolean	nodiff;
            
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
//                logger.info( "no diff: " + key );
                }
            else {
                logger.info(
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
