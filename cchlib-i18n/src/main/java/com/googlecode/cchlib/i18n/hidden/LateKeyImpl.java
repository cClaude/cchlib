package com.googlecode.cchlib.i18n.hidden;

import java.util.MissingResourceException;
import com.googlecode.cchlib.i18n.LateKey;

/**
 * Private class use to identify current field
 * and to resolve value.
 */
// not public 
class LateKeyImpl implements LateKey
{
    private static final long serialVersionUID = 1L;
    private AutoI18nImpl autoI18n;
    private String       key;

    public LateKeyImpl( final AutoI18nImpl autoI18n, final String key )
    {
        this.autoI18n = autoI18n;
        this.key      = key;
    }
    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.LateKey_#getKey()
     */
    @Override
    public String getKey()
    {
        return key;
    }
    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.LateKey_#getKey(int)
     */
    @Override
    public String getKey( final int index )
    {
        return key + '.' + index;
    }
    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.LateKey_#getValue()
     */
    @Override
    public String getValue()
        throws MissingResourceException
    {
        return this.autoI18n.getI18n().getString( key );
    }
    /* (non-Javadoc)
     * @see com.googlecode.cchlib.i18n.LateKey_#getValue(int)
     */
    @Override
    public String getValue( final int index )
        throws MissingResourceException
    {
        return this.autoI18n.getI18n().getString( getKey(index) );
    }
}
