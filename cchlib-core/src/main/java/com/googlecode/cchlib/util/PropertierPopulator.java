package com.googlecode.cchlib.util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 *
 */
public class PropertierPopulator<E>
{
    private final static Logger logger = Logger.getLogger( PropertierPopulator.class );
    private Set<Field> keyFieldSet;

    /**
     *
     * @param clazz
     */
    public PropertierPopulator( Class<? extends E> clazz )
    {
        this.keyFieldSet     = new HashSet<>();
        Field[] fields  = clazz.getDeclaredFields();

        for( Field f : fields ) {
            if( f.getAnnotation( Populator.class ) != null ) {
                this.keyFieldSet.add( f );
                }
            }

        logger.info( "Found " + this.keyFieldSet.size() + " fields." );
    }

    /**
     *
     * @param bean
     * @param properties
     */
    public void populateProperties( E bean, Properties properties )
    {
        for( Field f : this.keyFieldSet ) {
            try {
                f.setAccessible( true );
                final Object o = f.get( bean );
                f.setAccessible( false );

                if( o != null ) {
                    properties.put( f.getName(), o.toString() );
                    }
                }
            catch( IllegalArgumentException | IllegalAccessException e ) {
                // ignore !
                logger .warn( "Cannot read field:" + f, e );
                }
            }
    }

    /**
     *
     * @param properties
     * @param bean
     */
    public void populateBean( Properties properties, E bean )
    {
        for( Field f : this.keyFieldSet ) {
            final String strValue = properties.getProperty( f.getName() );
            final Object value;

            if( f.getDeclaringClass().isInstance( String.class ) ) {
                value = strValue;
                }
            else if( f.getDeclaringClass().isInstance( Boolean.class ) ) {
                value = Boolean.valueOf( strValue );
                }
            else {
                logger .error( "Bad type for field:" + f + " * class=" + f.getDeclaringClass() );
                break;
                }

            try {
                f.setAccessible( true );
                f.set( bean, value );
                f.setAccessible( false );
                }
            catch( IllegalArgumentException | IllegalAccessException e ) {
                // ignore !
                logger .error( "Cannot set field:" + f, e );
                }
            }
    }


}
