package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

/**
 * Field is not a String
 *
 * @since 4.1.8
 */
public class I18nStringNotAStringException extends I18nSyntaxException
{
    private static final long serialVersionUID = 1L;

    public I18nStringNotAStringException( final Field field )
    {
        super( "Field is not a String", field );
    }
}
