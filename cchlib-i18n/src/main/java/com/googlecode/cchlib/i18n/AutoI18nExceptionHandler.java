package com.googlecode.cchlib.i18n;

import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Handler to manage exception or errors during internationalization
 */
public interface AutoI18nExceptionHandler
    extends Serializable
{
    /**
     *
     * @param cause
     * @param field
     */
    void handleI18nSyntaxeException( I18nSyntaxeException cause, Field field );

    /**
     * Invoke when an {@link IllegalAccessException} occurred
     *
     * @param cause The exception to handle
     * @param i18nField
     */
    void handleIllegalAccessException( IllegalAccessException cause, I18nField i18nField );

    /**
     * Invoke when an {@link IllegalArgumentException} occurred
     *
     * @param cause The exception to handle
     * @param i18nField
     */
    void handleIllegalArgumentException( IllegalArgumentException cause, I18nField i18nField );

    /**
     * Invoke when an {@link InvocationTargetException} occur
     *
     * @param cause The exception to handle
     * @param i18nField
     */
    void handleInvocationTargetException( InvocationTargetException cause, I18nField i18nField );

    /**
     *
     * @param cause
     * @param i18nField
     * @param i18nResolver
     */
    void handleMissingKeyException( MissingKeyException cause, I18nField i18nField, I18nResolver i18nResolver );

    /**
     * Invoke when an {@link MissingResourceException} occurred
     *
     * @param cause         The exception to handle
     * @param i18nField     The {@link I18nField} concern by the exception
     * @param objectToI18n  Object to I18n that contain field.
     * @param i18nInterface TODOC
     */
    <T> void handleMissingResourceException( MissingResourceException cause, I18nField i18nField, T objectToI18n, I18nInterface i18nInterface );

    /**
     *
     * @param cause
     * @param field
     */
    void handleNoSuchMethodException( NoSuchMethodException cause, Field field );

    /**
     * Invoke when an {@link NoSuchMethodException} occurred
     *
     * @param cause The exception to handle
     * @param i18nField
     */
    void handleNoSuchMethodException( NoSuchMethodException cause, I18nField i18nField );

    /**
     *
     * @param cause
     * @param field
     */
    void handleSecurityException( MethodProviderSecurityException cause, Field field );

    /**
     * Invoke when an {@link SecurityException} occurred
     *
     * @param cause The exception to handle
     * @param i18nField
     */
    void handleSecurityException( SecurityException cause, I18nField i18nField );

    /**
     *
     * @param cause
     * @param i18nField
     * @param i18nResolver
     */
    void handleSetFieldException( SetFieldException cause, I18nField i18nField, I18nResolver i18nResolver );
}
