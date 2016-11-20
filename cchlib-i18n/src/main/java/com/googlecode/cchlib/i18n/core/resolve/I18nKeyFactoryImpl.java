package com.googlecode.cchlib.i18n.core.resolve;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.annotation.I18nName;

/**
 * Key builder for a class
 *
 */
public class I18nKeyFactoryImpl implements I18nKeyFactory
{
    private static final long serialVersionUID = 1L;

    private final String i18nNameValue;

    public I18nKeyFactoryImpl( final Class<?> objectToI18nClass )
    {
        this.i18nNameValue = findI18nNameValue( objectToI18nClass );
    }

    private static String findI18nNameValue( final Class<?> objectToI18nClass )
    {
        Class<?>    clazz = objectToI18nClass;
        String      value;

        do {
            value = getI18nNameValue( clazz.getAnnotation( I18nName.class ) );

            if( value != null ) {
                break;
            }
            clazz = clazz.getSuperclass();
        } while( clazz != Object.class && clazz != null );

        return value;
    }

    private static String getI18nNameValue( final I18nName i18nName )
    {
        String value;

        if( i18nName != null ) {
            value = i18nName.value();

            if( value.isEmpty() ) {
                value = null;
                }
            }
        else {
            value = null;
            }

         return value;
    }

    @Override
    public String getKeyBase( final Field field, final String keyIdValue )
    {
        if( i18nNameValue == null ) {
            if( (keyIdValue != null) && !keyIdValue.isEmpty() ) {
                return keyIdValue;
                }
            else {
                return field.getDeclaringClass().getName() + '.' + field.getName();
                }
            }
        else {
            if( (keyIdValue != null) && !keyIdValue.isEmpty() ) {
                assert ! keyIdValue.isEmpty();

                return this.i18nNameValue + '.' + keyIdValue;
                }
            else {
                return this.i18nNameValue + '.' + field.getName();
                }
            }
    }

}
