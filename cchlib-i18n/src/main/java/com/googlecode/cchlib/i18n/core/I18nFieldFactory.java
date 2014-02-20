package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import javax.swing.JTabbedPane;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;

final /* not public*/ class I18nFieldFactory
{
    public static I18nField createI18nFieldToolTipText(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener
        )
    {
        if( methodContener != null ) {
            return new I18nFieldMethodsResolution( i18nDelegator, i18nKeyFactory, field, keyIdValue, methodContener, null );
            }
        else if( JTabbedPane.class.isAssignableFrom( field.getType() ) ) {
            return new I18nFieldToolTipTextForJTabbedPane( i18nDelegator, i18nKeyFactory, field, keyIdValue );
            }
        else {
            return new I18nFieldToolTipText( i18nDelegator, i18nKeyFactory, field, keyIdValue );
            }
    }

    public static I18nField createI18nField(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener,
        final AutoI18nType      autoI18nType
        )
    {
        if( methodContener != null ) {
            return new I18nFieldMethodsResolution( i18nDelegator, i18nKeyFactory, field, keyIdValue, methodContener, autoI18nType );
            }
        else {
            return new I18nFieldAutoI18nTypes( i18nDelegator, i18nKeyFactory, field, keyIdValue, autoI18nType );
            }
    }

    public static I18nField createI18nStringField(
        final I18nDelegator     i18nDelegator,
        final I18nKeyFactory    i18nKeyFactory,
        final Field             field,
        final String            keyIdValue,
        final MethodContener    methodContener
        )
    {
        if( methodContener != null ) {
            return new I18nFieldMethodsResolution( i18nDelegator, i18nKeyFactory, field, keyIdValue, methodContener, null );
            }
        else if( String.class.isAssignableFrom( field.getType() ) ) {
            if( isStatic( field ) ) {
                // FIXME better handle this error !
                throw new RuntimeException( "Field is static " + field );
                }

            return new I18nFieldString( i18nDelegator, i18nKeyFactory, field, keyIdValue );
            }
        else {
            return new I18nFieldStringArray( i18nDelegator, i18nKeyFactory, field, keyIdValue );
            }
    }

    private static boolean isStatic( final Field field )
    {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers);
    }
}
