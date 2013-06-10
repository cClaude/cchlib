package com.googlecode.cchlib.i18n.prep;

//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;
//import java.util.Set;
//import com.googlecode.cchlib.i18n.resources.I18nSimpleResourceBundle;
//
///**
// *
// */
//public class I18nPrepI18nInterfaceImpl implements I18nPrepI18nInterface
//{
//    private static final long serialVersionUID = 1L;
//    private I18nSimpleResourceBundle i18nSimpleResourceBundle;
//    private Map<String,Integer>      keyUsageCountMap = new HashMap<String,Integer>();
//    private Set<String>              missingKeySet    = new HashSet<String>();
//
//    public I18nPrepI18nInterfaceImpl(
//        final I18nSimpleResourceBundle i18nSimpleResourceBundle
//        )
//    {
//        this.i18nSimpleResourceBundle = i18nSimpleResourceBundle;
//    }
//
//    @Override
//    public ResourceBundle getResourceBundle()
//    {
//        int todo;
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /* (non-Javadoc)
//     * @see com.googlecode.cchlib.i18n.I18nInterface#getString(java.lang.String)
//     */
//    @Override
//    public String getString( String key ) throws MissingResourceException
//    {
//        incForKey( key );
//        
//        try {
//            return this.i18nSimpleResourceBundle.getString( key );
//            }
//        catch( MissingResourceException e ) {
//            this.missingKeySet.add( key );
//            
//            throw e;
//            }
//    }
//
//    private void incForKey( String key )
//    {
//        final Integer countInteger = keyUsageCountMap.get( key );
//        final int     count;
//        
//        if( countInteger == null ) {
//            count = 1;
//            }
//        else {
//            count = countInteger.intValue() + 1;
//            }
//
//        keyUsageCountMap.put( key, Integer.valueOf( count ) );
//    }
//
//    @Override
//    public Map<String,Integer> getUsageMap()
//    {
//        return Collections.unmodifiableMap( keyUsageCountMap );
//    }
//}
