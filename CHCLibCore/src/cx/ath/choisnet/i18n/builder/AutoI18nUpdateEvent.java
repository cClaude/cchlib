package cx.ath.choisnet.i18n.builder;

import java.lang.reflect.InvocationTargetException;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;

/**
 * TODO: Doc!
 * 
 * Can collect missing key !
 */
public abstract class AutoI18nUpdateEvent
    implements AutoI18nExceptionHandler
{
    private static final long serialVersionUID = 1L;
    private AutoI18nExceptionHandler parentHandler;
    
    /**
     * TODO: Doc!
     * 
     * @param eHandler
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
     * @return the parentHandler
     */
    protected AutoI18nExceptionHandler getParentHandler()
    {
        return parentHandler;
    }

}
