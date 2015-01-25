package com.googlecode.cchlib.lang.reflect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import javax.annotation.Nonnull;

/**
 * This class is design to have a {@link Serializable} {@link Field}
 * @since 4.2
 * @see SerializableMethod
 */
public class SerializableField implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Field field;

    /**
     * Create a {@link SerializableField}
     *
     * @param field  Embed {@link Field}
     */
    public SerializableField( @Nonnull final Field field )
    {
        this.field = field;
    }

    /**
     * Return original field that can survive to a serialization.
     * @return original field.
     */
    public Field getField()
    {
        return this.field;
    }

    //Serializable
    private void writeObject(final ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();

        out.writeObject( this.field.getDeclaringClass() );
        out.writeObject( this.field.getName() );
    }

    //Serializable
    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        // Restore transient fields !
        final Class<?> parentClass = (Class<?>)in.readObject();
        final String   name        = (String)in.readObject();

        try {
            this.field = parentClass.getDeclaredField( name );
        }
        catch( NoSuchFieldException | SecurityException e ) {
            throw new IOException( e );
        }
    }

    @Override
    public String toString()
    {
        return "SerializableField [field=" + this.field + "]";
    }
}
