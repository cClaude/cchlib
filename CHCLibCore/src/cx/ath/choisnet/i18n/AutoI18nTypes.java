package cx.ath.choisnet.i18n;

import java.io.Serializable;

/**
 * 
 * 
 * @author Claude CHOISNET
 */
public interface AutoI18nTypes extends Serializable, Iterable<AutoI18nTypes.Type> 
{
    /**
     * 
     *
     * @author Claude CHOISNET
     */
    public interface Type extends Serializable 
    {
        /**
         * Returns class handled by this Type
         * @return class handled by this Type
         */
        public Class<?> getType();
        
//        /**
//         * Returns true if giving object is an
//         * instance of object handled by this Type
//         * @param toI18n object to localize
//         * @return  true if giving object is an
//         * instance of object handled by this Type
//         */
//        public boolean isInstanceOf(Object toI18n);
        
        /**
         * Set localized text
         * 
         * @param toI18n object to localize
         * @param key    key object for resolve value
         * @throws java.util.MissingResourceException
         */
        public void setText(Object toI18n, AutoI18n.Key key)
            throws java.util.MissingResourceException;

        /**
         * 
         * @param toI18n object to localize
         * @return not empty String
         *         array, null if not supported
         */
        public String[] getText(Object toI18n);
        //int getTextSize() ??
    }
}
