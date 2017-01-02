package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldSetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.Values;

//NOT public
final class I18nFieldAutoI18nTypes extends AbstractI18nField
{
    private final class FieldI18nResolver<T> implements I18nResolver
    {
        private final T objectToI18n;

        private FieldI18nResolver( final T objectToI18n )
        {
            this.objectToI18n = objectToI18n;
        }

        @Override
        public Keys getKeys() throws MissingKeyException
        {
            try {
               final Object fieldObject = _getComponent( this.objectToI18n, getField() );

               return getAutoI18nTypes().getKeys( fieldObject, getKeyBase() );
                }
            catch( final IllegalArgumentException | IllegalAccessException e ) {
                throw new MissingKeyException( e );
                 }
         }

        @Override
        public I18nResolvedFieldGetter getI18nResolvedFieldGetter() {
            return keys -> _getValues( this.objectToI18n, getField(), getAutoI18nTypes() );
        }

        @Override
        public I18nResolvedFieldSetter getI18nResolvedFieldSetter() {
            return ( keys, values ) -> _setValues(this.objectToI18n, values, getField(), getAutoI18nTypes());
        }
    }

    private static final long serialVersionUID = 1L;

    public I18nFieldAutoI18nTypes(
            final I18nDelegator     i18nDelegator,
            final I18nKeyFactory    i18nKeyFactory,
            final Field             field,
            final String            keyIdValue,
            final AutoI18nType      autoI18nType
            ) throws I18nSyntaxException
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, autoI18nType );

        assert autoI18nType != null : "Parameter autoI18nTypes must not be null";
   }

    @Override
    public FieldType getFieldType()
    {
        return FieldType.SIMPLE_KEY;
    }

    @Override
    public <T> I18nResolver createI18nResolver(
        final T            objectToI18n,
        final I18nResource i18nResource // TODO investigate why not use ?
        )
    {
        return new FieldI18nResolver<T>( objectToI18n );
    }

    @SuppressWarnings({
        "squid:S3398", // can not move this method into a non static class
        "squid:S00100" // Method name
    })
    private static final <T> Values _getValues( //
            final T            objectToI18n, //
            final Field        field, //
            final AutoI18nType autoI18nType //
            ) throws GetFieldException //
    {
        try {
            final Object fieldObject = _getComponent(objectToI18n, field );

            return autoI18nType.getText(fieldObject);
        } catch (final IllegalArgumentException | IllegalAccessException e) {
            throw new GetFieldException(e);
        }
    }

    @SuppressWarnings({
        "squid:S3398", // can not move this method into a non static class
        "squid:S00100" // Method name
    })
    private static final <T> void _setValues( //
            final T            objectToI18n, //
            final Values       values, //
            final Field        field, //
            final AutoI18nType autoI18nType //
            ) throws SetFieldException //
    {
        try {

            final Object fieldObject = _getComponent(objectToI18n, field );

            autoI18nType.setText(fieldObject, values);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new SetFieldException(e);
        }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:S00100" // Method name
    })
    private static final <T> Object _getComponent( final T objectToI18n, final Field field )
            throws IllegalArgumentException, IllegalAccessException
    {
        field.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)

        return field.get( objectToI18n );
    }
}
