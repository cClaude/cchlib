package com.googlecode.cchlib.i18n.unit.util;

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
import com.googlecode.cchlib.i18n.resources.MissingResourceException;

public class AutoI18nExceptionCollector implements AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;

    private final CollectorImpl collectI18nSyntaxeException            = new CollectorImpl();
    private final CollectorImpl collectIllegalAccessException          = new CollectorImpl();
    private final CollectorImpl collectIllegalArgumentException        = new CollectorImpl();
    private final CollectorImpl collectInvocationTargetException       = new CollectorImpl();
    private final CollectorImpl collectMissingKeyException             = new CollectorImpl();
    private final CollectorImpl collectMissingResourceException        = new CollectorImpl();
    private final CollectorImpl collectNoSuchMethodException           = new CollectorImpl();
    private final CollectorImpl collectMethodProviderSecurityException = new CollectorImpl();
    private final CollectorImpl collectSecurityException               = new CollectorImpl();
    private final CollectorImpl collectSetFieldException               = new CollectorImpl();

    @Override
    public void handleI18nSyntaxException( final I18nSyntaxException cause, final Field field )
    {
        this.collectI18nSyntaxeException.add( cause, field );
    }

    @Override
    public void handleIllegalAccessException(
        final IllegalAccessException cause,
        final I18nField              i18nField
        )
    {
        this.collectIllegalAccessException.add( cause, i18nField );
    }

    @Override
    public void handleIllegalArgumentException(
        final IllegalArgumentException cause,
        final I18nField                i18nField
        )
    {
        this.collectIllegalArgumentException.add( cause, i18nField );
    }

    @Override
    public void handleInvocationTargetException(
        final InvocationTargetException cause,
        final I18nField                 i18nField
        )
    {
        this.collectInvocationTargetException.add( cause, i18nField );
    }

    @Override
    public void handleMissingKeyException(
        final MissingKeyException cause,
        final I18nField           i18nField,
        final I18nResolver        i18nResolver
        )
    {
        this.collectMissingKeyException.add( cause, i18nField, i18nResolver );
    }

    @Override
    public <T> void handleMissingResourceException(
            final MissingResourceException cause,
            final I18nField                i18nField,
            final T                        objectToI18n,
            final I18nResource             i18nResource
            )
    {
        this.collectMissingResourceException.add( cause, i18nField, objectToI18n, i18nResource );
    }

    @Override
    public void handleNoSuchMethodException( final NoSuchMethodException cause, final Field field )
    {
        this.collectNoSuchMethodException.add( cause, field );
    }

    @Override
    public void handleNoSuchMethodException(
        final NoSuchMethodException cause,
        final I18nField             i18nField
        )
    {
        this.collectNoSuchMethodException.add( cause, i18nField );
    }

    @Override
    public void handleSecurityException(
        final MethodProviderSecurityException cause,
        final Field                           field
        )
    {
        this.collectMethodProviderSecurityException.add( cause, field );
    }

    @Override
    public void handleSecurityException( final SecurityException cause, final I18nField i18nField )
    {
        this.collectSecurityException.add( cause, i18nField );
    }

    @Override
    public void handleSetFieldException(
        final SetFieldException cause,
        final I18nField         i18nField,
        final I18nResolver      i18nResolver
        )
    {
        this.collectSetFieldException.add( cause, i18nField, i18nResolver );
    }

    public Collector getI18nSyntaxeExceptionCollector()
    {
        return this.collectI18nSyntaxeException;
    }

    public Collector getIllegalAccessExceptionCollector()
    {
        return this.collectIllegalAccessException;
    }

    public Collector getIllegalArgumentExceptionCollector()
    {
        return this.collectIllegalArgumentException;
    }

    public Collector getInvocationTargetExceptionCollector()
    {
        return this.collectInvocationTargetException;
    }

    public Collector getMissingKeyExceptionCollector()
    {
        return this.collectMissingKeyException;
    }

    public Collector getMissingResourceExceptionCollector()
    {
        return this.collectMissingResourceException;
    }

    public Collector getNoSuchMethodExceptionCollector()
    {
        return this.collectNoSuchMethodException;
    }

    public Collector getMethodProviderSecurityExceptionCollector()
    {
        return this.collectMethodProviderSecurityException;
    }

    public Collector getSecurityExceptionCollector()
    {
        return this.collectSecurityException;
    }

    public Collector getSetFieldExceptionCollector()
    {
        return this.collectSetFieldException;
    }
}
