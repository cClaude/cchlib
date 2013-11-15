package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;

/**
 * Handler to manage exception or errors during internationalization
 */
public interface AutoI18nExceptionHandler
    extends Serializable
{
    /**
     * Invoke when an {@link InvocationTargetException} occur
     *
     * @param e The exception to handle
     */
    void handleInvocationTargetException( InvocationTargetException e );

    /**
     * Invoke when an {@link IllegalAccessException} occurred
     *
     * @param e The exception to handle
     */
    void handleIllegalAccessException( IllegalAccessException e );

    /**
     * Invoke when an {@link IllegalArgumentException} occurred
     *
     * @param e The exception to handle
     */
    void handleIllegalArgumentException( IllegalArgumentException e );

    /**
     * Invoke when an {@link NoSuchMethodException} occurred
     *
     * @param e The exception to handle
     */
    void handleNoSuchMethodException( NoSuchMethodException e );

    /**
     * Invoke when an {@link SecurityException} occurred
     *
     * @param e The exception to handle
     */
    void handleSecurityException( SecurityException e );

    /**
     * Invoke when an {@link MissingResourceException} occurred
     *
     * @param e         The exception to handle
     * @param field     The {@link Field} concern by the exception
     * @param key       The key ({@link String}) to use for translation
     * @param methods   Array of {@link Method}, array of 2 methods (setter and getter).
     */
    void handleMissingResourceException( MissingResourceException e, Field field, String key, Method[] methods);

    /**
     * Invoke when an {@link MissingResourceException} occurred
     *
     * @param e     The exception to handle
     * @param field The {@link Field} concern by the exception
     * @param key   The key ({@link String}) to use for translation
     */
    void handleMissingResourceException( MissingResourceException e, Field field, String key);

    /**
     * Invoke when an {@link MissingResourceException} occur
     *
     * @param e     The exception to handle
     * @param field The {@link Field} concern by the exception
     * @param key   The key ({@link AutoI18n.Key}) to use for translation
     */
    void handleMissingResourceException( MissingResourceException e, Field field, AutoI18n.Key key);
}
