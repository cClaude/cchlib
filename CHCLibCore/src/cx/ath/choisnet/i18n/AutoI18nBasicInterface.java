package cx.ath.choisnet.i18n;

/**
 * AutoI18nBasicInterface is design to help localization
 * process for objects which have only one value to
 * localize.
 * 
 * @author Claude CHOISNET
 * @see AutoI18n
 */
public interface AutoI18nBasicInterface 
{
    /**
     * Returns current value (not localized) for field.
     * @return current value (not localized) for field.
     */
    public String getI18nString();
    
    /**
     * Set current value (localized) for field.
     * 
     * @param localString localized value
     */
    public void setI18nString(String localString);
}
