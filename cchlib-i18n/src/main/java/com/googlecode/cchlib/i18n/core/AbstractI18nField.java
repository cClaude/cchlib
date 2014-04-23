package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import java.lang.reflect.Field;

/**
 *
 */
//NOT public
abstract class AbstractI18nField implements I18nField
{
    private static final long serialVersionUID = 1L;
    private final I18nDelegator  i18nDelegator;
    private final I18nKeyFactory i18nKeyFactory;
    private final Field field;
    private final String keyIdValue;
    private final MethodContener methodContener;
    private final AutoI18nType autoI18nType;

    protected AbstractI18nField(
        final I18nDelegator  i18nDelegator,
        final I18nKeyFactory i18nKeyFactory, 
        final Field          field,
        final String         keyIdValue, 
        final MethodContener        methodContener,
        final AutoI18nType   autoI18nType
        )
    {
        this.i18nDelegator  = i18nDelegator;
        this.i18nKeyFactory = i18nKeyFactory;
        this.field          = field;
        this.keyIdValue     = keyIdValue;
        this.methodContener        = methodContener;
        this.autoI18nType  = autoI18nType;
    }

    public I18nDelegator getI18nDelegator()
    {
        return i18nDelegator;
    }

    @Override
    public Field getField()
    {
        return field;
    }

    @Override
    public MethodContener getMethods()
    {
        return methodContener;
    }

    @Override
    public String getKeyBase()
    {
        return this.i18nKeyFactory.getKeyBase( this.field, this.keyIdValue );
    }

    @Override
    public AutoI18nType getAutoI18nTypes()
    {
        return autoI18nType;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( this.getClass() );
        builder.append( " [i18nDelegator=" );
        builder.append( i18nDelegator );
        builder.append( ", i18nKeyFactory=" );
        builder.append( i18nKeyFactory );
        builder.append( ", field=" );
        builder.append( field );
        builder.append( ", keyIdValue=" );
        builder.append( keyIdValue );
        builder.append( ", methods=" );
        builder.append( methodContener );
        builder.append( ", autoI18nType=" );
        builder.append( autoI18nType );
        builder.append( ']' );
        return builder.toString();
    }
}
