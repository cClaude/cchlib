package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig;


/**
 * Define filter type
 */
enum FilterType {
    /**
     * No filter
     */
    DISABLED,

    /**
     * Include entries define by filter
     */
    INCLUDE_FILTER,

    /**
     * Exclude entries define by filter
     */
    EXCLUDE_FILTER,
    ;

    @Override
    public String toString()
    {
        return "*"+super.toString();
    }
}
