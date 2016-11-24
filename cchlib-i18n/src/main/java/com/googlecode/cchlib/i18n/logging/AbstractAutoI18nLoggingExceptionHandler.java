package com.googlecode.cchlib.i18n.logging;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.Set;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxeException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

/**
 * {@link AutoI18nExceptionHandler} using logging
 * to trace Localization exceptions.
 */
public abstract class AbstractAutoI18nLoggingExceptionHandler
    implements AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private final EnumSet<AutoI18nConfig> config;

    public AbstractAutoI18nLoggingExceptionHandler( final Set<AutoI18nConfig> userConfig )
    {
        this.config = (userConfig == null) ? EnumSet.noneOf( AutoI18nConfig.class ) : EnumSet.copyOf( userConfig );
    }

    protected abstract void doHandle( String msg, Throwable e );

    private void doHandleForField( final String msg, final Exception e )
    {
        doHandle( msg, e );
    }

    protected void doHandleMissingResourceException( final Exception e, final I18nField i18nField )
    {
        final String msg = "* error Missing Resource for: ["
                + i18nField.getKeyBase()
                + "] - "
                + e.getLocalizedMessage();

        doHandle( msg, e );
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

    protected EnumSet<AutoI18nConfig> getConfig()
    {
        return this.config;
    }

    @Override
    public void handleI18nSyntaxeException( final I18nSyntaxeException e, final Field f )
    {
        doHandle( e.getMessage(), e );
    }

    @Override
    public void handleIllegalAccessException( final IllegalAccessException cause, final I18nField i18nField )
    {
        doHandleForField( "IllegalAccessException", cause );
    }

    @Override
    public void handleIllegalArgumentException( final IllegalArgumentException cause, final I18nField i18nField )
    {
        doHandleForField( "IllegalArgumentException", cause );
    }

    @Override
    public void handleInvocationTargetException( final InvocationTargetException cause, final I18nField i18nField )
    {
        doHandleForField( "InvocationTargetException", cause );
    }

    @Override
    public void handleMissingKeyException(
        final MissingKeyException   e,
        final I18nField             i18nField,
        final I18nResolver          i18nResolver
        )
    {
        doHandleForField( "MissingKeyException", e );
    }

    @Override
    @SuppressWarnings({
        "squid:S1871", // Same blocks
        "squid:SwitchLastCaseIsDefaultCheck" // Switch on enum (default useless)
        })
    public <T> void handleMissingResourceException(
        final MissingResourceException e,
        final I18nField                i18nField,
        final T                        objectToI18n,
        final I18nInterface            i18nInterface
        )
    {
        switch( i18nField.getFieldType() ) {
            case SIMPLE_KEY :
                doHandleMissingResourceException( e, i18nField );
                break;
            case LATE_KEY :
                doHandleMissingResourceException( e, i18nField );
                break;
            case METHODS_RESOLUTION :
                doHandleMissingResourceException_MissingMethodsResolution( e, i18nField );
                break;
            case JCOMPONENT_TOOLTIPTEXT:
                doHandleMissingResourceException( e, i18nField );
                break;
            case JCOMPONENT_MULTI_TOOLTIPTEXT:
                doHandleMissingResourceException( e, i18nField ); // FIXME : todo check this !
                break;
            }
    }

    @Override
    public void handleNoSuchMethodException( final NoSuchMethodException cause, final Field field )
    {
        doHandleForField( "NoSuchMethodException", cause );
    }

    @Override
    public void handleNoSuchMethodException( final NoSuchMethodException cause, final I18nField i18nField )
    {
        doHandleForField( "NoSuchMethodException", cause );
    }

    @Override
    public void handleSecurityException( final MethodProviderSecurityException cause, final Field field )
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
        final SetFieldException e,
        final I18nField         i18nField,
        final I18nResolver      i18nResolver
        )
    {
        switch( i18nField.getFieldType() ) {
            case SIMPLE_KEY :
                doHandleMissingResourceException( e, i18nField );
                break;
            case LATE_KEY :
                doHandleMissingResourceException( e, i18nField );
                break;
            case METHODS_RESOLUTION :
                doHandleMissingResourceException_MissingMethodsResolution( e, i18nField );
                break;
            case JCOMPONENT_TOOLTIPTEXT:
                doHandleMissingResourceException( e, i18nField );
                break;
            case JCOMPONENT_MULTI_TOOLTIPTEXT:
                doHandleMissingResourceException( e, i18nField ); // FIXME : todo check this !
                break;
            }
    }
}
