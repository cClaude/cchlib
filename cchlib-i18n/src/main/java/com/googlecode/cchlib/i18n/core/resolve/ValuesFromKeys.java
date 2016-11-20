package com.googlecode.cchlib.i18n.core.resolve;

import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import org.apache.log4j.Logger;

public class ValuesFromKeys extends IndexValues
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( ValuesFromKeys.class );

    public ValuesFromKeys( I18nInterface i18nInterface, Keys keys )
        throws MissingResourceException
    {
        super( createValuesFromKeys( i18nInterface, keys ) );
    }

    private static String[] createValuesFromKeys( I18nInterface i18nInterface, Keys keys )
        throws MissingResourceException
    {
        assert keys.size() > 0;

        String[] values = new String[ keys.size() ];
        int      index  = 0;

        for( String key : keys ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "try key = " + key );
                }
            
            values[ index++ ] = i18nInterface.getString( key );
            
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "value for key = " + key + " is: " + values[ index-1 ] );
                }
            }

        return values;
    }
}
