package com.googlecode.cchlib.i18n;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import com.googlecode.cchlib.i18n.missing.MissingInfo;

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
    public void handleInvocationTargetException( InvocationTargetException e );

    /**
     * Invoke when an {@link IllegalAccessException} occurred
     *
     * @param e The exception to handle
     */
    public void handleIllegalAccessException( IllegalAccessException e );

    /**
     * Invoke when an {@link IllegalArgumentException} occurred
     *
     * @param e The exception to handle
     */
    public void handleIllegalArgumentException( IllegalArgumentException e );

    /**
     * Invoke when an {@link NoSuchMethodException} occurred
     *
     * @param e The exception to handle
     */
    public void handleNoSuchMethodException( NoSuchMethodException e );

    /**
     * Invoke when an {@link SecurityException} occurred
     *
     * @param e The exception to handle
     */
    public void handleSecurityException( SecurityException e );

    /**
     * Invoke when an {@link MissingResourceException} occurred
     *
     * @param e           The exception to handle
     * @param field       The {@link Field} concern by the exception
     * @param missingInfo Informations to retrieve default value
     */
    public void handleMissingResourceException( MissingResourceException e, Field field, MissingInfo missingInfo );
}
