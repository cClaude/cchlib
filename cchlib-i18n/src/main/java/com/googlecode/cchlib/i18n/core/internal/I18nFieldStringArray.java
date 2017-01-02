package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldSetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.IndexKeys;
import com.googlecode.cchlib.i18n.core.resolve.IndexValues;
import com.googlecode.cchlib.i18n.core.resolve.KeyException;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.Values;

//NOT public
final class I18nFieldStringArray  extends AbstractI18nField
{
    private final class StringArrayI18nResolver<T> implements I18nResolver
    {
        private final T objectToI18n;

        private StringArrayI18nResolver( final T objectToI18n )
        {
            this.objectToI18n = objectToI18n;
        }

        @Override
        public Keys getKeys()
        {
            try {
                final String[] values = _getComponent( this.objectToI18n, getField() );

                return new IndexKeys( getKeyBase(), values.length );
                }
            catch( final IllegalArgumentException e ) {
                throw new KeyException( "objectToI18n is: " + this.objectToI18n, e );
                }
            catch( final IllegalAccessException e ) {
                throw new KeyException( e );
                }
        }

        @Override
        public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
        {
            return keys -> _getValues(this.objectToI18n, getField());
        }

        @Override
        public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
        {
            return ( keys, values ) -> _setValues(this.objectToI18n, getField(), keys, values);
        }
    }
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("squid:S3346") // assert usage
    public I18nFieldStringArray(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue
        ) throws I18nSyntaxException
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, null );

        assert field.getType().equals( String[].class ) : "field.getType() = " + field.getType() + " != String[].class";
    }

    @Override
    public FieldType getFieldType()
    {
        return (getMethodContener() != null) ?
            FieldType.METHODS_RESOLUTION : FieldType.SIMPLE_KEY;
    }

    @Override
    public <T> I18nResolver createI18nResolver(
        final T            objectToI18n,
        final I18nResource i18nResource // TODO investigate why not use ?
        )
    {
        return new StringArrayI18nResolver<T>( objectToI18n );
    }

    @SuppressWarnings({
        "squid:S3398", // Can not move a static method
        "squid:S00100" // Naming conventions
        })
    private static final <T> Values _getValues( final T objectToI18n, final Field field ) //
        throws GetFieldException //
    {
        try {
            final String[] values = _getComponent( objectToI18n, field );

            return new IndexValues( values );
            }
        catch( final IllegalArgumentException | IllegalAccessException e ) {
            throw new GetFieldException( e );
            }
    }

    @SuppressWarnings({
        "squid:S3398", // Can not move a static method
        "squid:S3346", // assert usage
        "squid:S00100" // Naming conventions
        })
    private static final <T> void _setValues(
        final T      objectToI18n,
        final Field  field,
        final Keys   keys,
        final Values values
        ) throws SetFieldException
    {
        // Keys and Values inconsistent size
        assert keys.size() == values.size() : "Keys and Values inconsistent size";

        try {
            field.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)
            field.set( objectToI18n, values.toArray() );
            }
        catch( final IllegalArgumentException | IllegalAccessException e ) {
            throw new SetFieldException( e );
            }
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck",
        "squid:S00100" // Naming conventions
        })
    private static final <T> String[] _getComponent( final T objectToI18n, final Field field )
            throws IllegalArgumentException, IllegalAccessException
    {
        field.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)

        return (String[])field.get( objectToI18n );
    }
}
