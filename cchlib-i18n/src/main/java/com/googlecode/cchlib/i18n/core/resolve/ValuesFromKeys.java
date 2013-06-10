package com.googlecode.cchlib.i18n.core.resolve;

import com.googlecode.cchlib.i18n.I18nInterface;

public class ValuesFromKeys extends IndexValues
{
    private static final long serialVersionUID = 1L;

    public ValuesFromKeys( I18nInterface i18nInterface, Keys keys )
        throws java.util.MissingResourceException
    {
        super( createValuesFromKeys( i18nInterface, keys ) );
    }

    private static String[] createValuesFromKeys( I18nInterface i18nInterface, Keys keys )
        throws java.util.MissingResourceException
    {
        assert keys.size() > 0;

        String[] values = new String[ keys.size() ];
        int      index  = 0;

        for( String key : keys ) {
            values[ index++ ] = i18nInterface.getString( key );
            }

        return values;
    }
}
