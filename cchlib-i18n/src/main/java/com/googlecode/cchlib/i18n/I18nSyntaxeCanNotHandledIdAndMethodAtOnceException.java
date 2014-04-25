package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

/**
 * Can not handled id and method at once on the same field
 *
 * @since 4.2
 */
public class I18nSyntaxeCanNotHandledIdAndMethodAtOnceException extends I18nSyntaxeException {
    private static final long serialVersionUID = 1L;

    public I18nSyntaxeCanNotHandledIdAndMethodAtOnceException( final Field field )
    {
        super( "Can not handled id and method at once on the same field", field );
    }
}
