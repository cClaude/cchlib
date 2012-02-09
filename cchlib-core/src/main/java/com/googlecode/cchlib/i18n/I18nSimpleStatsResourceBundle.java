package com.googlecode.cchlib.i18n;
//package cx.ath.choisnet.i18n;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Extend default implementation based on {@link ResourceBundle}
 * for {@link I18nInterface} and add keep trace of use of
 * each key.
 * <br/>
 * This class could be use to identify dead keys.
 * @see #getUsageMap()
 */
public class I18nSimpleStatsResourceBundle extends I18nSimpleResourceBundle
{
    private static final long serialVersionUID = 1L;
    private HashMap<String,Integer> map = new HashMap<String,Integer>();

    /**
     * @param resourceBundle
     */
    public I18nSimpleStatsResourceBundle(
            final Locale locale,
            final String resourceBundle
            )
    {
        super( locale, resourceBundle );
    }

    @Override
    public String getString( final String key )
        throws MissingResourceException
    {
        final Integer count = map.get( key );

        if( count == null ) {
            map.put( key, 1 );
            }
        else {
            map.put( key, count + 1 );
            }

        return super.getString( key );
    }

    /**
     * returns a Map that contain count of use of each key
     * @return an unmodifiable Map
     */
    public Map<String,Integer> getUsageMap()
    {
        return Collections.unmodifiableMap( map );
    }
}
