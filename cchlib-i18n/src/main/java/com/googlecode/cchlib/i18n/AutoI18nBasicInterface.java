package com.googlecode.cchlib.i18n;

/**
 * AutoI18nBasicInterface is design to help localization
 * process for objects which have only one value to
 * localize.
 */
public interface AutoI18nBasicInterface
{
    /**
     * Returns current value (not localized) for field.
     * @return current value (not localized) for field.
     */
    String getI18nString();

    /**
     * Set current value (localized) for field.
     *
     * @param localString localized value
     */
    void setI18nString(String localString);
}
