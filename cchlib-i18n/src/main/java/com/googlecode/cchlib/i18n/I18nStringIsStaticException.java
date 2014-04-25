package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

public class I18nStringIsStaticException extends I18nSyntaxeException {
    private static final long serialVersionUID = 1L;

    public I18nStringIsStaticException( final Field field )
    {
        super( field );
    }
}
