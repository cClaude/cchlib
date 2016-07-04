package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.filtersconfig;


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
    EXCLUDE_FILTER,;

    /**
     * Return value according to <code>ordinal</code>
     *
     * @param ordinal
     * @return
     */
    public static FilterType buildFromOrdinal( final int ordinal )
    {
        return FilterType.values()[ ordinal ];
    }
}
