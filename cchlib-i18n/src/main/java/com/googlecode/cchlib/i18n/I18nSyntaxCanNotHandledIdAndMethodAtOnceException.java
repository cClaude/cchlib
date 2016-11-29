package com.googlecode.cchlib.i18n;

import java.lang.reflect.Field;

/**
 * Can not handled id and method at once on the same field
 *
 * @since 4.2
 */
public class I18nSyntaxCanNotHandledIdAndMethodAtOnceException extends I18nSyntaxException {
    private static final long serialVersionUID = 1L;

    public I18nSyntaxCanNotHandledIdAndMethodAtOnceException( final Field field )
    {
        super( "Can not handled id and method at once on the same field", field );
    }
}
