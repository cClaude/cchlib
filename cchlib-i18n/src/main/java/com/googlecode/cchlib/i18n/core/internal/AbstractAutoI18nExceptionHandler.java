package com.googlecode.cchlib.i18n.core.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.I18nSyntaxException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.api.I18nResource;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilder;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 * {@link AbstractAutoI18nExceptionHandler} allow help
 * implementing an {@link AutoI18nExceptionHandler} mainly for logging
 * propose but also use for {@link I18nResourceBuilder}
 */
public abstract class AbstractAutoI18nExceptionHandler
    implements AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;

    protected abstract void doHandle( String msg, Throwable cause );

    private void doHandleForField( final String msg, final Exception cause )
    {
        doHandle( msg, cause );
    }

    protected void doHandleMissingResourceException(
        final Exception cause,
        final I18nField i18nField
        )
    {
        final String msg = "* error Missing Resource for: ["
                + i18nField.getKeyBase()
                + "] - "
                + cause.getLocalizedMessage();

        doHandle( msg, cause );
    }

    @SuppressWarnings("squid:S00100")
    protected void doHandleMissingResourceException_MissingMethodsResolution(
        final Exception cause,
        final I18nField i18nField
        )
    {
        final String msg = String.format(
                "* error Missing Resource  for: %s using [%s] - %s",
                i18nField.getKeyBase(),
                i18nField.getMethodContener().getMethodName(),
                cause.getLocalizedMessage()
                );

        doHandle( msg, cause );
    }

    @Override
    public void handleI18nSyntaxException(
        final I18nSyntaxException cause,
        final Field               field
        )
    {
        doHandle( cause.getMessage(), cause );
    }

    @Override
    public void handleIllegalAccessException(
        final IllegalAccessException cause,
        final I18nField              i18nField
        )
    {
        doHandleForField( "IllegalAccessException", cause );
    }

    @Override
    public void handleIllegalArgumentException(
        final IllegalArgumentException cause,
        final I18nField                i18nField
        )
    {
        doHandleForField( "IllegalArgumentException", cause );
    }

    @Override
    public void handleInvocationTargetException(
        final InvocationTargetException cause,
        final I18nField                 i18nField
        )
    {
        doHandleForField( "InvocationTargetException", cause );
    }

    @Override
    public void handleMissingKeyException(
        final MissingKeyException   cause,
        final I18nField             i18nField,
        final I18nResolver          i18nResolver
        )
    {
        doHandleForField( "MissingKeyException", cause );
    }

    @Override
    @SuppressWarnings({
        "squid:S1871", // Same blocks
        "squid:SwitchLastCaseIsDefaultCheck" // Switch on enum (default useless)
        })
    public <T> void handleMissingResourceException(
        final MissingResourceException cause,
        final I18nField                i18nField,
        final T                        objectToI18n,
        final I18nResource             i18nInterface
        )
    {
        switch( i18nField.getFieldType() ) {
            case SIMPLE_KEY :
                doHandleMissingResourceException( cause, i18nField );
                break;
            case LATE_KEY :
                doHandleMissingResourceException( cause, i18nField );
                break;
            case METHODS_RESOLUTION :
                doHandleMissingResourceException_MissingMethodsResolution( cause, i18nField );
                break;
            case JCOMPONENT_TOOLTIPTEXT:
                doHandleMissingResourceException( cause, i18nField );
                break;
            case JCOMPONENT_MULTI_TOOLTIPTEXT:
                doHandleMissingResourceException( cause, i18nField ); // FIXME : to do check this !
                break;
            case AUTO_UPDATABLE_FIELD:
                throw new UnsupportedOperationException();
            }
    }

    @Override
    public void handleNoSuchMethodException( final NoSuchMethodException cause, final Field field )
    {
        doHandleForField( "NoSuchMethodException", cause );
    }

    @Override
    public void handleNoSuchMethodException(
        final NoSuchMethodException cause,
        final I18nField             i18nField
        )
    {
        doHandleForField( "NoSuchMethodException", cause );
    }

    @Override
    public void handleSecurityException(
        final MethodProviderSecurityException cause,
        final Field                           field
        )
    {
        doHandleForField( "SecurityException", cause );
    }

    @Override
    public void handleSecurityException( final SecurityException cause, final I18nField i18nField )
    {
        doHandleForField( "SecurityException", cause );
    }

    @Override
    @SuppressWarnings({
        "squid:S1871", // Same blocks
        "squid:SwitchLastCaseIsDefaultCheck" // Switch on enum (default useless)
        })
    public void handleSetFieldException(
        final SetFieldException cause,
        final I18nField         i18nField,
        final I18nResolver      i18nResolver
        )
    {
        switch( i18nField.getFieldType() ) {
            case SIMPLE_KEY :
                doHandleMissingResourceException( cause, i18nField );
                break;
            case LATE_KEY :
                doHandleMissingResourceException( cause, i18nField );
                break;
            case METHODS_RESOLUTION :
                doHandleMissingResourceException_MissingMethodsResolution( cause, i18nField );
                break;
            case JCOMPONENT_TOOLTIPTEXT:
                doHandleMissingResourceException( cause, i18nField );
                break;
            case JCOMPONENT_MULTI_TOOLTIPTEXT:
                doHandleMissingResourceException( cause, i18nField ); // FIXME : todo check this !
                break;
            case AUTO_UPDATABLE_FIELD:
                throw new UnsupportedOperationException();
            }
    }
}
