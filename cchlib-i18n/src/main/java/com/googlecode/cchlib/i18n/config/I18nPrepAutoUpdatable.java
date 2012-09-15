package com.googlecode.cchlib.i18n.config;

/**
 * To add on a custom class to identify MessageBundle base name 
 */
public interface I18nPrepAutoUpdatable extends I18nAutoUpdatable
{
    /**
     * Returns message bundle base name
     * @return message bundle base name
     */
    public String getMessagesBundle();
}
