package com.googlecode.cchlib.text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Provide some commons {@link DateFormat} factories
 * @since 4.1.6
 */
public final class DateFormatHelper {

    private DateFormatHelper() {} // All static

    /**
     * Returns a new {@link DateFormat} according to
     * ISO date + time format. Returned object is not
     * thread safe.
     * @return a new {@link DateFormat} according to
     *   ISO date + time format
     */
    public static DateFormat createFullDateISODateFormat()
    {
        return new SimpleDateFormat( "yyyy-MM-dd.HH-mm-ss" );
    }

    /**
     * Returns a new {@link DateFormat} according to
     * ISO date format. Returned object is not
     * thread safe.
     * @return a new {@link DateFormat} according to ISO date format
     */
    public static DateFormat createDateISODateFormat()
    {
        return new SimpleDateFormat( "yyyy-MM-dd" );
    }
}
