package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

/**
 * Field is declare as final
 *
 * @since 4.2
 */
public class I18nStringIsFinalException extends I18nSyntaxeException {
    private static final long serialVersionUID = 1L;

    public I18nStringIsFinalException( final Field field )
    {
        super( "Field is declare 'final'", field );
    }
}
