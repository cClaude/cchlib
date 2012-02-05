package com.googlecode.cchlib.i18n.builder;
//package cx.ath.choisnet.i18n.builder;

import java.lang.reflect.InvocationTargetException;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;

/**
 * {@link AutoI18nExceptionHandler} build to collect missing keys !
 */
public abstract class AutoI18nUpdateEvent
    implements AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private AutoI18nExceptionHandler parentHandler;

    /**
     * Create an AutoI18nUpdateEvent, based on a parent {@link AutoI18nExceptionHandler}
     *
     * @param eHandler Parent {@link AutoI18nExceptionHandler}
     */
    public AutoI18nUpdateEvent(
            AutoI18nExceptionHandler eHandler
            )
    {
        this.parentHandler = eHandler;
    }

    @Override
    public void handleInvocationTargetException( InvocationTargetException e )
    {
        parentHandler.handleInvocationTargetException( e );
    }
    @Override
    public void handleIllegalAccessException( IllegalAccessException e )
    {
        parentHandler.handleIllegalAccessException( e );
    }
    @Override
    public void handleIllegalArgumentException( IllegalArgumentException e )
    {
        parentHandler.handleIllegalArgumentException( e );
    }
    @Override
    public void handleNoSuchMethodException( NoSuchMethodException e )
    {
        parentHandler.handleNoSuchMethodException( e );
    }
    @Override
    public void handleSecurityException( SecurityException e )
    {
        parentHandler.handleSecurityException( e );
    }

    /**
     * @return the parent {@link AutoI18nExceptionHandler}
     */
    protected AutoI18nExceptionHandler getParentHandler()
    {
        return parentHandler;
    }

}
