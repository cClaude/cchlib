package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import java.lang.reflect.Field;

final /*not public*/class I18nFieldMethodsResolution
    extends AbstractI18nField
        implements I18nField
{
    private static final long serialVersionUID = 1L;

    public I18nFieldMethodsResolution(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener           methodContener,
        final AutoI18nType autoI18nType
        )
    {
        super( i18nDelegator, i18nKeyFactory, field, keyIdValue, methodContener, autoI18nType );

        assert methodContener != null;
    }

    /*
    @Override
    public <T> String[] getDefaultValues( T objectToI18n ) throws I18nFieldDefaultValueException
    {
        try {
            Method getter = getMethods().getGetter();
            Object r      = getter.invoke( objectToI18n, new Object[0] );

            return new String[]{ (String)r };
            }
        catch( SecurityException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
        catch( NoSuchMethodException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
        catch( IllegalArgumentException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
        catch( IllegalAccessException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
        catch( InvocationTargetException e ) {
            throw new I18nFieldDefaultValueException( e );
            }
    }*/

    @Override
    public FieldType getFieldType()
    {
        return FieldType.METHODS_RESOLUTION;
    }

    @Override
    public <T> I18nResolver createI18nResolver( final T objectToI18n, I18nInterface i18nInterface )
    {
        //return new I18nResolverForMethods<T>( this, objectToI18n );
        return null;
    }
}
