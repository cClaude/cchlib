package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.Field;
import javax.swing.JTabbedPane;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.I18nFieldType;
import com.googlecode.cchlib.i18n.core.MethodContener;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.lang.reflect.SerializableField;

//NOT public
final class I18nFieldFactory
{
    private static final class I18nFieldOnlyForExceptions implements I18nField
    {
        private static final long serialVersionUID = 1L;
        private final SerializableField autoUpdatableSerializableField;

        private I18nFieldOnlyForExceptions( final Field autoUpdatableField )
        {
            this.autoUpdatableSerializableField = new SerializableField( autoUpdatableField );
        }

        @Override
        public MethodContener getMethodContener()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getKeyBase()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public I18nFieldType getFieldType()
        {
            return I18nFieldType.AUTO_UPDATABLE_FIELD;
        }

        @Override
        public Field getField()
        {
            return this.autoUpdatableSerializableField.getField();
        }

        @Override
        public AutoI18nType getAutoI18nTypes()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> I18nResolver createI18nResolver( final T objectToI18n, final I18nResource i18nResource )
        {
            throw new UnsupportedOperationException();
        }
    }

    private I18nFieldFactory()
    {
        // All static
    }

    public static I18nField newI18nFieldToolTipText(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener
        ) throws I18nSyntaxException
    {
        if( methodContener != null ) {
            return new I18nFieldMethodsResolution(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue,
                            methodContener,
                            null
                            );
            }
        else if( JTabbedPane.class.isAssignableFrom( field.getType() ) ) {
            return new I18nFieldToolTipTextForJTabbedPane(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue
                            );
            }
        else {
            return new I18nFieldToolTipText(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue
                            );
            }
    }

    public static I18nField newI18nField(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener,
        final AutoI18nType      autoI18nType
        ) throws I18nSyntaxException
    {
        if( methodContener != null ) {
            return new I18nFieldMethodsResolution(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue,
                            methodContener,
                            autoI18nType
                            );
            }
        else {
            return new I18nFieldAutoI18nTypes(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue,
                            autoI18nType
                            );
            }
    }

    public static I18nField newI18nStringField(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener
        ) throws I18nSyntaxException
    {
        if( methodContener != null ) {
            return new I18nFieldMethodsResolution(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue,
                            methodContener,
                            null
                            );
            }
        else if( String.class.isAssignableFrom( field.getType() ) ) {
            return new I18nFieldString(
                        i18nDelegator,
                        i18nKeyFactory,
                        field,
                        keyIdValue
                        );
            }
        else {
            return new I18nFieldStringArray(
                            i18nDelegator,
                            i18nKeyFactory,
                            field,
                            keyIdValue
                            );
            }
    }

    public static I18nField newI18nField( final Field autoUpdatableField )
    {
        return new I18nFieldOnlyForExceptions( autoUpdatableField );
    }
}
