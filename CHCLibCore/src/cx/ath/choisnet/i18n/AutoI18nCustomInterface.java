package cx.ath.choisnet.i18n;

import java.util.Map;

/**
 * TODO: doc
 * @author Claude CHOISNET
 */
public interface AutoI18nCustomInterface 
{
    /**
     *
     * @param i18n
     */
    public void setI18n(I18nInterface i18n);
    public Map<String,String> getI18n(I18nInterface i18n);
}
