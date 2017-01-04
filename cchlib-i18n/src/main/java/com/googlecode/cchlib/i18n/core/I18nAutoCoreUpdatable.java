package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;

/**
 *
 * A typically implementation of this interface is :
 * <pre>
 *   autoI18n.performeI18n( this, this.getClass() );
 * </pre>
 * but could also perform internationalization on private objects.
 * <p>
 * This interface is also use by {@link I18nResourceBuilder}
 */
@SuppressWarnings("squid:S1609") // This is probably not an functional interface
public interface I18nAutoCoreUpdatable
{
    /**
     * Apply internationalization on this object
     *
     * @param autoI18n The {@link AutoI18nCore} for current user.
     */
    void performeI18n( AutoI18nCore autoI18n );
}
