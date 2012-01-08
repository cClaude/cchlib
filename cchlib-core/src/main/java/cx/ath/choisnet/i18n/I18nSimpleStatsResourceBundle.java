package cx.ath.choisnet.i18n;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

/**
 *
 */
public class I18nSimpleStatsResourceBundle extends I18nSimpleResourceBundle
{
    private static final long serialVersionUID = 1L;
    private HashMap<String,Integer> map = new HashMap<String,Integer>();

    /**
     * @param resourceBundle
     */
    public I18nSimpleStatsResourceBundle( final String resourceBundle )
    {
        super( resourceBundle );
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
