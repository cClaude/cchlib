package com.googlecode.cchlib.i18n.core;

/**
 * TODOC
 *
 * A typically implementation of this interface is :<code><pre>
 *   autoI18n.performeI18n( this, this.getClass() );
 * </pre></code>
 * but could also perform internationalization on private objects.
 */
public interface I18nAutoCoreUpdatable
{
    /**
     * Apply internationalization on this object
     *
     * @param autoI18n The {@link AutoI18nCore} for current user.
     */
    public void performeI18n(AutoI18nCore autoI18n);
}
