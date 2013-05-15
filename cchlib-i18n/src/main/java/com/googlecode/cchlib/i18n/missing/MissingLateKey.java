package com.googlecode.cchlib.i18n.missing;

import java.io.Serializable;
import com.googlecode.cchlib.i18n.AutoI18n;

/**
 *
 */
public class MissingLateKey implements MissingInfo, Serializable 
{
    private static final long serialVersionUID = 1L;
    private AutoI18n.Key key;

    public MissingLateKey( AutoI18n.Key key )
    {
        this.key = key;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.missing.MissingInfo#getType()
     */
    @Override
    public MissingInfo.Type getType()
    {
        return MissingInfo.Type.LATE_KEY;
    }

    @Override
    public String getKey()
    {
        return key.getKey();
    }

    public AutoI18n.Key getAutoI18nKey()
    {
        return key;
    }

}
