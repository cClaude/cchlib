package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.core.resolve.GetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldGetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolvedFieldSetter;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.IndexValues;
import com.googlecode.cchlib.i18n.core.resolve.Keys;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.core.resolve.UniqKeys;
import com.googlecode.cchlib.i18n.core.resolve.Values;

// NOT public
final class I18nFieldString  extends AbstractI18nField
{
    private final class I18nResolverForString<T> implements I18nResolver
    {
        private final T objectToI18n;

        private I18nResolverForString( final T objectToI18n )
        {
            this.objectToI18n = objectToI18n;
        }

        @Override
        public Keys getKeys()
        {
            return new UniqKeys( getKeyBase() );
        }

        @Override
        public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
        {
            return keys -> _getValues( this.objectToI18n, getField() );
        }

        @Override
        public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
        {
            return ( keys, values ) -> _setValue(this.objectToI18n, getField(), keys, values);
        }

        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();

            builder.append( "I18nResolverForString [objectToI18n=" );
            builder.append( this.objectToI18n );
            builder.append( ", getKeys()=" );
            builder.append( getKeys() );
            builder.append( ", getI18nResolvedFieldGetter()=" );
            builder.append( getI18nResolvedFieldGetter() );
            builder.append( ", getI18nResolvedFieldSetter()=" );
            builder.append( getI18nResolvedFieldSetter() );
            builder.append( ']' );

            return builder.toString();
        }
    }

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({
        "squid:S3346" // assert usage
        })
    public I18nFieldString(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue
        ) throws I18nSyntaxException
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, null );

        assert field.getType().equals( String.class ) : "field.getType() = " + field.getType() + " != String.class";
    }

    @Override
    public FieldType getFieldType()
    {
        return (getMethodContener() != null) ? FieldType.METHODS_RESOLUTION : FieldType.SIMPLE_KEY;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, final I18nInterface i18nInterface )
    {
        return new I18nResolverForString<T>( objectToI18n );
    }

    @SuppressWarnings("squid:S00100") // Method name
    private static final <T> Values _getValues( final T objectToI18n, final Field field )
            throws GetFieldException //
    {
        try {
            final String value = _getComponent( objectToI18n, field );

            return new IndexValues( value );
            }
        catch( final IllegalArgumentException | IllegalAccessException e ) {
            throw new GetFieldException( e );
            }
    }

    @SuppressWarnings({
        "squid:S3346", // assert usage
        "squid:S1850", // assert 'useless' test
        "squid:S00100" // method name
        })
    private static final <T> void _setValue( //
            final T      objectToI18n, //
            final Field  field, //
            final Keys   keys, //
            final Values values //
            ) throws SetFieldException //
    {
        // Keys and Values inconsistent size
        assert keys.size() == values.size() : "Keys and Values inconsistent size";
        assert keys.size() == 1 : "Keys and Values should have only 1 value";
        assert values.size() == 1 : "Should one and only one value for a String";
        assert values.get( 0 ) instanceof String : "Value is not a String";
        assert !Modifier.isFinal( field.getModifiers() ) : "Field " + field + " is final";

        try {
            field.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)
            field.set( objectToI18n, values.get( 0 ) );

            assert field.get( objectToI18n ).equals( values.get( 0 ) );
            }
        catch( final IllegalArgumentException | IllegalAccessException e ) {
            throw new SetFieldException( e );
            }
    }

    @SuppressWarnings("squid:S00100") // Method name
    private static final <T> String _getComponent( final T objectToI18n, final Field field )
            throws IllegalArgumentException, IllegalAccessException
    {
        field.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)

        return (String)field.get( objectToI18n );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "I18nFieldString [getFieldType()=" );
        builder.append( getFieldType() );
        builder.append( ", getI18nDelegator()=" );
        builder.append( getI18nDelegator() );
        builder.append( ", getField()=" );
        builder.append( getField() );
        builder.append( ", getMethods()=" );
        builder.append( getMethodContener() );
        builder.append( ", getKeyBase()=" );
        builder.append( getKeyBase() );
        builder.append( ", getAutoI18nTypes()=" );
        builder.append( getAutoI18nTypes() );
        builder.append( ']' );
        return builder.toString();
    }
}
