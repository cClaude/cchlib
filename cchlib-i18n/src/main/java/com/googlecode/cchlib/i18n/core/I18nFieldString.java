package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.I18nInterface;
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

final /*not public*/ class I18nFieldString  extends AbstractI18nField
{
    private static final long serialVersionUID = 1L;

    public I18nFieldString(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory, 
        final Field             field,
        final String            keyIdValue
        )
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, null, null );

        assert field.getType().equals( String.class ) : "field.getType() = " + field.getType() + " != String.class";
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
                return new UniqKeys( getKeyBase() );
            }
            @Override
            public I18nResolvedFieldGetter getI18nResolvedFieldGetter()
            {
                return new I18nResolvedFieldGetter() {
                    @Override
                    public Values getValues( Keys keys ) throws GetFieldException
                    {
                        try {
//                            Field f = getField();
//                            f.setAccessible( true ); // TODO: try to restore ! (need to handle concurrent access)
//                            Object objectField = f.get( objectToI18n );
//                            String value       = (String)objectField;
                            String value = getComponent( objectToI18n );

                            return new IndexValues( value );
                            }
                        catch( IllegalArgumentException e ) {
                            throw new GetFieldException( e );
                            }
                        catch( IllegalAccessException e ) {
                            throw new GetFieldException( e );
                            }
                     }
                };
            }
            @Override
            public I18nResolvedFieldSetter getI18nResolvedFieldSetter()
            {
                return new I18nResolvedFieldSetter() {
                    @Override
                    public void setValues( Keys keys, Values values ) throws SetFieldException
                    {
                        // Keys and Values inconsistent size
                        assert keys.size() == values.size() : "Keys and Values inconsistent size";
                        assert keys.size() == 1 : "Keys and Values should have only 1 value";

                        try {
                            Field f = getField();
                            f.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)
                            f.set( objectToI18n, values.get( 0 ) );
                            }
                        catch( IllegalArgumentException e ) {
                            throw new SetFieldException( e );
                            }
                        catch( IllegalAccessException e ) {
                            throw new SetFieldException( e );
                            }
                    }
                };
            }
        };
    }
    
    private final <T> String getComponent( final T objectToI18n ) 
            throws IllegalArgumentException, IllegalAccessException
    {
        Field f = getField();
        f.setAccessible( true ); // FIXME: try to restore ! (need to handle concurrent access)

        return (String)f.get( objectToI18n );
    }
}
