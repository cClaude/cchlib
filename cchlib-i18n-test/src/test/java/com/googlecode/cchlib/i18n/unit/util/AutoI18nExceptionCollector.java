package com.googlecode.cchlib.i18n.unit.util;

import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.I18nInterface;
import com.googlecode.cchlib.i18n.I18nSyntaxeException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.core.I18nField;
import com.googlecode.cchlib.i18n.core.resolve.I18nResolver;
import com.googlecode.cchlib.i18n.core.resolve.MissingKeyException;
import com.googlecode.cchlib.i18n.core.resolve.SetFieldException;
import com.googlecode.cchlib.i18n.resources.MissingResourceException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class AutoI18nExceptionCollector implements AutoI18nExceptionHandler // $codepro.audit.disable largeNumberOfFields
{
    private static final long serialVersionUID = 1L;

    private final CollectorImpl collectI18nSyntaxeException = new CollectorImpl();
    private final CollectorImpl collectIllegalAccessException = new CollectorImpl();
    private final CollectorImpl collectIllegalArgumentException = new CollectorImpl();
    private final CollectorImpl collectInvocationTargetException = new CollectorImpl();
    private final CollectorImpl collectMissingKeyException = new CollectorImpl();
    private final CollectorImpl collectMissingResourceException = new CollectorImpl();
    private final CollectorImpl collectNoSuchMethodException = new CollectorImpl();
    private final CollectorImpl collectMethodProviderSecurityException = new CollectorImpl();
    private final CollectorImpl collectSecurityException = new CollectorImpl();
    private final CollectorImpl collectSetFieldException = new CollectorImpl();

    @Override
    public void handleI18nSyntaxeException( I18nSyntaxeException cause, Field field )
    {
        this.collectI18nSyntaxeException.add( cause, field );
    }

    @Override
    public void handleIllegalAccessException( IllegalAccessException cause, I18nField i18nField )
    {
        this.collectIllegalAccessException.add( cause, i18nField );
    }

    @Override
    public void handleIllegalArgumentException( IllegalArgumentException cause, I18nField i18nField )
    {
        this.collectIllegalArgumentException.add( cause, i18nField );
    }

    @Override
    public void handleInvocationTargetException( InvocationTargetException cause, I18nField i18nField )
    {
        this.collectInvocationTargetException.add( cause, i18nField );
    }

    @Override
    public void handleMissingKeyException( MissingKeyException cause, I18nField i18nField, I18nResolver i18nResolver )
    {
        this.collectMissingKeyException.add( cause, i18nField, i18nResolver );
    }

    @Override
    public <T> void handleMissingResourceException(
            MissingResourceException cause,
            I18nField                i18nField,
            T                        objectToI18n,
            I18nInterface            i18nInterface
            )
    {
        this.collectMissingResourceException.add( cause, i18nField, objectToI18n, i18nInterface );
    }

    @Override
    public void handleNoSuchMethodException( NoSuchMethodException cause, Field field )
    {
        this.collectNoSuchMethodException.add( cause, field );
    }

    @Override
    public void handleNoSuchMethodException( NoSuchMethodException cause, I18nField i18nField )
    {
        this.collectNoSuchMethodException.add( cause, i18nField );
    }

    @Override
    public void handleSecurityException( MethodProviderSecurityException cause, Field field )
    {
        this.collectMethodProviderSecurityException.add( cause, field );
    }

    @Override
    public void handleSecurityException( SecurityException cause, I18nField i18nField )
    {
        this.collectSecurityException.add( cause, i18nField );
    }

    @Override
    public void handleSetFieldException( SetFieldException cause, I18nField i18nField, I18nResolver i18nResolver )
    {
        this.collectSetFieldException.add( cause, i18nField, i18nResolver );
    }

    public Collector getI18nSyntaxeExceptionCollector()
    {
        return collectI18nSyntaxeException;
    }

    public Collector getIllegalAccessExceptionCollector()
    {
        return collectIllegalAccessException;
    }

    public Collector getIllegalArgumentExceptionCollector()
    {
        return collectIllegalArgumentException;
    }

    public Collector getInvocationTargetExceptionCollector()
    {
        return collectInvocationTargetException;
    }

    public Collector getMissingKeyExceptionCollector()
    {
        return collectMissingKeyException;
    }

    public Collector getMissingResourceExceptionCollector()
    {
        return collectMissingResourceException;
    }

    public Collector getNoSuchMethodExceptionCollector()
    {
        return collectNoSuchMethodException;
    }

    public Collector getMethodProviderSecurityExceptionCollector()
    {
        return collectMethodProviderSecurityException;
    }

    public Collector getSecurityExceptionCollector()
    {
        return collectSecurityException;
    }

    public Collector getSetFieldExceptionCollector()
    {
        return collectSetFieldException;
    }
}
