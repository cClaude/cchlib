package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

/**
 * Field is declare as static
 *
 * @since 4.2
 */
public class I18nStringIsStaticException extends I18nSyntaxeException {
    private static final long serialVersionUID = 1L;

    public I18nStringIsStaticException( final Field field )
    {
        super( "Field is declare as static", field );
    }
}
