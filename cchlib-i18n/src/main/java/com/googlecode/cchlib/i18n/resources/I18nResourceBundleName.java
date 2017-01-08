package com.googlecode.cchlib.i18n.resources;


/**
 * Retrieve messages bundle name
 *
 * @see I18nResourceBundleNameFactory
 * @deprecated use {@link I18nResourceFactory} or {@link ResourceBundleHelper} instead
 */
@Deprecated
@FunctionalInterface
public interface I18nResourceBundleName
{
    /**
     * @return message bundle name
     */
    String getName();
}
