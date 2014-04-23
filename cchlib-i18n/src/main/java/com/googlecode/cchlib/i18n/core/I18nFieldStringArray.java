package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.I18nInterface;
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
    private static final long serialVersionUID = 1L;

    public I18nFieldStringArray(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue
        )
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, null );

        assert field.getType().equals( String[].class ) : "field.getType() = " + field.getType() + " != String[].class";
    }

    @Override
    public FieldType getFieldType()
    {
        return (getMethods() != null) ? FieldType.METHODS_RESOLUTION : FieldType.SIMPLE_KEY;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, final I18nInterface i18nInterface )
    {
        return new I18nResolver() {
            @Override
            public Keys getKeys()
            {
                try {
                    final String[] values = _getComponent( objectToI18n, getField() );

                    return new IndexKeys( getKeyBase(), values.length );
                    }
                catch( final IllegalArgumentException e ) {
                    throw new KeyException( "objectToI18n is: " + objectToI18n, e );
                    }
                catch( final IllegalAccessException e ) {
                    throw new KeyException( e );
                    }
            }

            @Override
            public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
            {
                return new I18nResolvedFieldGetter() {
                    @Override
                    public Values getValues( final Keys keys ) throws GetFieldException
                    {
                        return _getValues(objectToI18n, getField());
                     }
                };
            }

            @Override
            public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
            {
                return new I18nResolvedFieldSetter() {
                    @Override
                    public void setValues( final Keys keys, final Values values ) throws SetFieldException
                    {
                        _setValues(objectToI18n, getField(), keys, values);
                    }

                };
            }
        };
    }

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

    private static final <T> void _setValues( //
            final T objectToI18n, //
            final Field field, //
            final Keys keys, //
            final Values values //
            ) throws SetFieldException //
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
    private static final <T> String[] _getComponent( final T objectToI18n, final Field field )
            throws IllegalArgumentException, IllegalAccessException
    {
        field.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)

        return (String[])field.get( objectToI18n );
    }
}
