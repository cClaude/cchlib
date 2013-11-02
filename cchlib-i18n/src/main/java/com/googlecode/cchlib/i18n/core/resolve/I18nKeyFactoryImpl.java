package com.googlecode.cchlib.i18n.core.resolve;

import java.lang.reflect.Field;
import com.googlecode.cchlib.i18n.annotation.I18nName;

public class I18nKeyFactoryImpl implements I18nKeyFactory
{
    private static final long serialVersionUID = 1L;
    private String i18nNameValue;

    public I18nKeyFactoryImpl( final I18nName i18nName )
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

        this.i18nNameValue = value;
    }

    @Override
    public String getKeyBase( Field field, String keyIdValue )
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
