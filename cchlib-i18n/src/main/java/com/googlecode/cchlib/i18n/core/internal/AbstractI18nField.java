package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nSyntaxCanNotHandledIdAndMethodAtOnceException;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.MethodContener;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.lang.reflect.SerializableField;

/**
 * Common base implementation for {@link I18nField}
 */
//NOT public
abstract class AbstractI18nField implements I18nField
{
    private static final long serialVersionUID = 1L;

    private final I18nDelegator     i18nDelegator;
    private final I18nKeyFactory    i18nKeyFactory;
    private final SerializableField serializableField;
    private final String            keyIdValue;
    private final MethodContener    methodContener;
    private final AutoI18nType      autoI18nType;

    protected AbstractI18nField(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final SerializableField serializableField,
        final String            keyIdValue,
        final MethodContener    methodContener,
        final AutoI18nType      autoI18nType
        ) throws I18nSyntaxException
    {
        if( (! keyIdValue.isEmpty()) && (methodContener != null ) ) {
            throw new I18nSyntaxCanNotHandledIdAndMethodAtOnceException( this.serializableField.getField() );
        }

        this.i18nDelegator       = i18nDelegator;
        this.i18nKeyFactory      = i18nKeyFactory;
        this.serializableField   = serializableField;
        this.keyIdValue          = keyIdValue;
        this.methodContener      = methodContener;
        this.autoI18nType        = autoI18nType;
    }

    protected AbstractI18nField(
        final I18nDelegator  i18nDelegator,
        final I18nKeyFactory i18nKeyFactory,
        final Field          field,
        final String         keyIdValue,
        final MethodContener methodContener,
        final AutoI18nType   autoI18nType
        ) throws I18nSyntaxException
    {
        this(
            i18nDelegator,
            i18nKeyFactory,
            new SerializableField( field ),
            keyIdValue,
            methodContener,
            autoI18nType
            );
    }

    public I18nDelegator getI18nDelegator()
    {
        return this.i18nDelegator;
    }

    @Override
    public Field getField()
    {
        return this.serializableField.getField();
    }

    @Override
    public MethodContener getMethodContener()
    {
        return this.methodContener;
    }

    @Override
    public String getKeyBase()
    {
        return this.i18nKeyFactory.getKeyBase( getField(), this.keyIdValue );
    }

    @Override
    public AutoI18nType getAutoI18nTypes()
    {
        return this.autoI18nType;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "AbstractI18nField [i18nDelegator=" );
        builder.append( this.i18nDelegator );
        builder.append( ", i18nKeyFactory=" );
        builder.append( this.i18nKeyFactory );
        builder.append( ", serializableField=" );
        builder.append( this.serializableField );
        builder.append( ", keyIdValue=" );
        builder.append( this.keyIdValue );
        builder.append( ", methodContener=" );
        builder.append( this.methodContener );
        builder.append( ", autoI18nType=" );
        builder.append( this.autoI18nType );
        builder.append( ']' );

        return builder.toString();
    }
}
