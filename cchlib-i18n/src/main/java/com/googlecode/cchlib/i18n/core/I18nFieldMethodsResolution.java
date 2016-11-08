package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxeException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;

//NOT public
final class I18nFieldMethodsResolution
    extends AbstractI18nField
        implements I18nField
{
    private static final long serialVersionUID = 1L;

    public I18nFieldMethodsResolution(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener,
        final AutoI18nType      autoI18nType
        ) throws I18nSyntaxeException
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, methodContener, autoI18nType );

        assert methodContener != null;
    }

    @Override
    public FieldType getFieldType()
    {
        return FieldType.METHODS_RESOLUTION;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, final I18nInterface i18nInterface )
    {
        return null;
    }
}
