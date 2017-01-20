package com.googlecode.cchlib.lang.reflect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;

/**
 * This class is design to have a {@link Serializable} {@link Method}
 * @since 4.2
 */
public class SerializableMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Method method;

    /**
     * Create a {@link SerializableMethod}
     *
     * @param method Embed {@link Method}
     */
    public SerializableMethod( @Nonnull final Method method )
    {
        this.method = method;
    }

    /**
     * Return original method that can survive to a serialization.
     * @return original method.
     */
    public Method getMethod()
    {
        return this.method;
    }

    //Serializable
    private void writeObject(final ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();

        out.writeObject( this.method.getDeclaringClass() );
        out.writeObject( this.method.getName() );

        final Class<?>[] params = this.method.getParameterTypes();
        final int        count  = params.length;

        out.writeInt( count );

        for( int i = 0; i<count; i++ ) {
            out.writeObject( params[ i ] );
        }
    }

    //Serializable
    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        // Restore transient fields !
        final Class<?> parentClass = (Class<?>)in.readObject();
        final String   name        = (String)in.readObject();

        final int        count  = in.readInt();
        final Class<?>[] params = new Class<?>[ count ];

        for( int i = 0; i<count; i++ ) {
            params[ i ] = (Class<?>)in.readObject();
        }

        try {
            this.method = parentClass.getDeclaredMethod( name, params );
        }
        catch( SecurityException | NoSuchMethodException e ) {
            throw new IOException( e );
        }
    }

    @Override
    public String toString()
    {
        return "SerializableMethod [method=" + this.method + "]";
    }
}
