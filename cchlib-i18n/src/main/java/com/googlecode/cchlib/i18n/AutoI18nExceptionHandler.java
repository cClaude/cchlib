package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 * Handler to manage exception or errors during internationalization
 */
public interface AutoI18nExceptionHandler extends Serializable
{
    /**
     * Handle I18n syntax error
     *
     * @param cause the cause
     * @param field related field
     */
    void handleI18nSyntaxException( I18nSyntaxException cause, Field field );

    /**
     * Invoke when an {@link IllegalAccessException} occurred
     *
     * @param cause     the cause
     * @param i18nField related field
     */
    void handleIllegalAccessException( IllegalAccessException cause, I18nField i18nField );

    /**
     * Invoke when an {@link IllegalArgumentException} occurred
     *
     * @param cause     the cause
     * @param i18nField related field
     */
    void handleIllegalArgumentException( IllegalArgumentException cause, I18nField i18nField );

    /**
     * Invoke when an {@link InvocationTargetException} occur
     *
     * @param cause     the cause
     * @param i18nField related field
     */
    void handleInvocationTargetException( InvocationTargetException cause, I18nField i18nField );

    /**
     *
     * @param cause         the cause
     * @param i18nField     related field
     * @param i18nResolver  NEEDDOC
     */
    void handleMissingKeyException(
        MissingKeyException cause,
        I18nField           i18nField,
        I18nResolver        i18nResolver
        );

    /**
     * Invoke when an {@link MissingResourceException} occurred
     *
     * @param <T>           Type of {@code objectToI18n}
     * @param cause         The exception to handle
     * @param i18nField     The {@link I18nField} concern by the exception
     * @param objectToI18n  Object to I18n that contain field.
     * @param i18nInterface NEEDDOC
     */
    <T> void handleMissingResourceException( MissingResourceException cause, I18nField i18nField, T objectToI18n, I18nInterface i18nInterface );

    /**
     * NEEDDOC
     *
     * @param cause   the cause
     * @param field   related field
     */
    void handleNoSuchMethodException( NoSuchMethodException cause, Field field );

    /**
     * Invoke when an {@link NoSuchMethodException} occurred
     *
     * @param cause     the cause
     * @param i18nField related field
     */
    void handleNoSuchMethodException( NoSuchMethodException cause, I18nField i18nField );

    /**
     * NEEDDOC
     *
     * @param cause NEEDDOC
     * @param field NEEDDOC
     */
    void handleSecurityException( MethodProviderSecurityException cause, Field field );

    /**
     * Invoke when an {@link SecurityException} occurred
     *
     * @param cause The exception to handle
     * @param i18nField NEEDDOC
     */
    void handleSecurityException( SecurityException cause, I18nField i18nField );

    /**
     * NEEDDOC
     *
     * @param cause NEEDDOC
     * @param i18nField NEEDDOC
     * @param i18nResolver NEEDDOC
     */
    void handleSetFieldException( SetFieldException cause, I18nField i18nField, I18nResolver i18nResolver );
}
