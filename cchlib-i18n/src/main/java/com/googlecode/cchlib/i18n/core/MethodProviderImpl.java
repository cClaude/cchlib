package com.googlecode.cchlib.i18n.core;

import java.lang.reflect.Field;
import java.util.EnumSet;
import com.googlecode.cchlib.i18n.AutoI18nConfig;

class MethodProviderImpl implements MethodProvider
{
    private static final long serialVersionUID = 1L;
    private EnumSet<AutoI18nConfig> config;
    
    public MethodProviderImpl( EnumSet<AutoI18nConfig> config )
    {
        this.config = config;
    }
    
    @Override
    public MethodContener getMethods( Class<?> clazz, Field f, String methodName )
            throws MethodProviderNoSuchMethodException, MethodProviderSecurityException
    {
        // FIXME look for f.getDeclaringClass() up to clazz (when enable access to not public methods)
        Class<?>[] todo_improve_this_but_not_so_bad = I18nClassImpl.NOT_HANDLED_CLASS_TYPES;
        
        try {
            return getValidMethods( f.getDeclaringClass(), methodName );
            }
        catch( SecurityException e ) {
            throw new MethodProviderSecurityException( f, methodName, clazz, e );
            }
        catch( NoSuchMethodException e ) {
            throw new MethodProviderNoSuchMethodException( f, methodName, clazz, e );
            }
    }

    private MethodContener getValidMethods( Class<?> clazz, String methodName )
        throws SecurityException, NoSuchMethodException
    {
        MethodContener methodContener = new MethodContenerImpl( clazz, methodName );

        methodContener.getInvokeMethod();

        return methodContener;
    }
}
