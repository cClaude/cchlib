package com.googlecode.cchlib.i18n.core.resolve;

import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

public class ValuesFromKeys extends IndexValues
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( ValuesFromKeys.class );

    public ValuesFromKeys( final I18nResource i18nInterface, final Keys keys )
        throws MissingResourceException
    {
        super( createValuesFromKeys( i18nInterface, keys ) );
    }

    @SuppressWarnings("squid:S3346") // assert usage
    private static String[] createValuesFromKeys(
        final I18nResource i18nInterface,
        final Keys         keys
        ) throws MissingResourceException
    {
        assert keys.size() > 0;

        final String[] values = new String[ keys.size() ];
        int            index  = 0;

        for( final String key : keys ) {
            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "try key = " + key );
            }

            final String value = i18nInterface.getString( key );

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "value for key = " + key + " is: " + value );
            }

            values[ index++ ] = value;
        }

        return values;
    }
}
