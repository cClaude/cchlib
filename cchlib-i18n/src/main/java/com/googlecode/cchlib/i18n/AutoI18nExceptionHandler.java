package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import com.googlecode.cchlib.i18n.api.I18nResource;
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
     * @param cause The cause
     * @param field The {@link I18nField}
     */
    void handleI18nSyntaxException( I18nSyntaxException cause, Field field );

    /**
     * Invoke when an {@link IllegalAccessException} occurred
     *
     * @param cause     The cause
     * @param i18nField The {@link I18nField}
     */
    void handleIllegalAccessException( IllegalAccessException cause, I18nField i18nField );

    /**
     * Invoke when an {@link IllegalArgumentException} occurred
     *
     * @param cause     The cause
     * @param i18nField The {@link I18nField}
     */
    void handleIllegalArgumentException( IllegalArgumentException cause, I18nField i18nField );

    /**
     * Invoke when an {@link InvocationTargetException} occur
     *
     * @param cause     The cause
     * @param i18nField The {@link I18nField}
     */
    void handleInvocationTargetException( InvocationTargetException cause, I18nField i18nField );

    /**
     *
     * @param cause         The cause
     * @param i18nField     The {@link I18nField}
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
     * @param cause         The cause
     * @param i18nField     The {@link I18nField} concern by the exception
     * @param objectToI18n  Object to I18n that contain field.
     * @param i18nResource  The {@link I18nResource}
     */
    <T> void handleMissingResourceException(
        MissingResourceException cause,
        I18nField                i18nField,
        T                        objectToI18n,
        I18nResource             i18nResource
        );

    /**
     * NEEDDOC
     *
     * @param cause   The cause
     * @param field   The {@link I18nField}
     */
    void handleNoSuchMethodException( NoSuchMethodException cause, Field field );

    /**
     * Invoke when an {@link NoSuchMethodException} occurred
     *
     * @param cause     The cause
     * @param i18nField The {@link I18nField}
     */
    void handleNoSuchMethodException( NoSuchMethodException cause, I18nField i18nField );

    /**
     * Invoke when a {@link SecurityException} occur.
     *
     * @param cause The cause
     * @param field The {@link Field} that concern
     */
    void handleSecurityException( MethodProviderSecurityException cause, Field field );

    /**
     * Invoke when an {@link SecurityException} occurred
     *
     * @param cause The cause
     * @param i18nField The {@link I18nField}
     */
    void handleSecurityException( SecurityException cause, I18nField i18nField );

    /**
     * Invoke when field can not be set
     *
     * @param cause The cause
     * @param i18nField The {@link I18nField}
     * @param i18nResolver The {@link I18nResolver}
     */
    void handleSetFieldException(
        SetFieldException cause,
        I18nField         i18nField,
        I18nResolver      i18nResolver
        );

    /**
     * Invoke when field is not initialized
     * @param i18nField The {@link I18nField}
     */
    void handleI18nNullPointer( I18nField i18nField );
}
