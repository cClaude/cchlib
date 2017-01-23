package com.googlecode.cchlib.util.populator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.lang.reflect.InvokeByNameException;
import com.googlecode.cchlib.lang.reflect.Methods;

//NOT public
@SuppressWarnings("squid:S00119") // naming convention
abstract class AbstractPersistentAnnotation<E,METHOD_OR_FIELD extends Member>
    implements PersistentAnnotation<E,METHOD_OR_FIELD>
{
    private final Persistent         persistent;
    private final PersistentResolver resolver;

    AbstractPersistentAnnotation(
        @Nonnull final Persistent         persistent,
        @Nonnull final PersistentResolver resolver
        )
    {
        this.persistent = persistent;
        this.resolver   = resolver;
    }

    @Override
    public final boolean isDefaultValueNull()
    {
        return false;
    }

    @Override
    public final String defaultValue()
    {
        return this.persistent.defaultValue();
    }

    private Object toStringInvoke( final Object object )
    {
        try {
            return invoke( "toString", new Object[] { object } );
        }
        catch( InvocationTargetException | SecurityException | InvokeByNameException cause ) {
            throw new PersistentException( buildMessage( object ), cause );
        }
    }

    private void setValueInvoke( final Object object, final String value )
    {
        try {
            invoke( "setValue", new Object[] { object, value } );
        }
        catch( InvocationTargetException | SecurityException | InvokeByNameException cause ) {
            throw new PersistentException(
                    buildMessage( object ),
                    cause
                    );
        }
    }

    private String buildMessage( final Object object )
    {
        return "@Persistent does not handle type " + object.getClass();
    }

    @SuppressWarnings({
        "squid:RedundantThrowsDeclarationCheck","squid:S1160", // More than one exception
        "unchecked", "rawtypes",
        })
    private Object invoke( final String methodName, final Object[] arguments )
        throws InvocationTargetException, SecurityException, InvokeByNameException
    {
        return Methods.invokeByName(
                this.resolver,
                (Class)this.resolver.getClass(),
                methodName,
                arguments
                );
    }

    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public final String toString( final Object swingObject )
        throws PopulatorRuntimeException
    {
        // resolver.toString( swingObject ):
        return (String)toStringInvoke( swingObject );
    }

    protected void setValue( final Object swingObject, final String strValue )
    {
        // resolver.setValue( swingObject, strValue ):
        setValueInvoke( swingObject, strValue );
    }
}
