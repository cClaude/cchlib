package com.googlecode.cchlib.i18n.core;

/**
 * Describe how an {@link I18nField} should be handle.
 */
public enum I18nFieldType
{
    SIMPLE_KEY,
    LATE_KEY,
    METHODS_RESOLUTION,

    /** Handle tool tip text */
    JCOMPONENT_TOOLTIPTEXT,

    /** Handle tool tip text for JTabbedPane */
    JCOMPONENT_MULTI_TOOLTIPTEXT,

    AUTO_UPDATABLE_FIELD,
    }
