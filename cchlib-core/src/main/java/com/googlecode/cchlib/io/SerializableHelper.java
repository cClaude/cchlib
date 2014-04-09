// $codepro.audit.disable unnecessaryExceptions
package com.googlecode.cchlib.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Some useful tools to handle or verify {@link Serializable} objects.
 *
 * @see Serializable
 */
public final class SerializableHelper
{
    private SerializableHelper()
    {//All static !
    }

    /**
     * Serialize an Object
     *
     * @param <T> type of the object
     * @param anObject Object to serialize
     * @return a byte array build using standard serialization process for this object
     *
     * @throws InvalidClassException - Something is wrong with a class used by serialization.
     * @throws NotSerializableException - Some object to be serialized does not implement the java.io.Serializable interface.
     * @throws IOException - Any exception thrown by the underlying OutputStream.
     */
    public static <T extends Serializable> byte[] toByteArray( @Nullable final T anObject )
        throws InvalidClassException, NotSerializableException, IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream    oos    = new ObjectOutputStream( output );

        oos.writeObject( anObject );
        oos.flush();
        oos.close();

        return output.toByteArray();
    }

    /**
     * Retrieve a Object from a byte array and it's Class Object
     * using standard serialization process.
     *
     * @param <T> type of the object
     * @param aSerializedObject The serialized object
     * @param clazz             Class to use for deserialize
     * @return the object.
     *
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
     * @throws InvalidClassException Something is wrong with a class used by serialization.
     * @throws StreamCorruptedException Control information in the stream is inconsistent.
     * @throws OptionalDataException Primitive data was found in the stream instead of objects.
     * @throws IOException Any of the usual Input/Output related exceptions.
     */
    public static <T extends Serializable> T toObject(
            @Nonnull final byte[]              aSerializedObject,
            @Nonnull final Class<? extends T>  clazz
            )
        throws ClassNotFoundException, InvalidClassException, StreamCorruptedException, OptionalDataException, IOException // $codepro.audit.disable unnecessaryExceptions
    {
        final ByteArrayInputStream input = new ByteArrayInputStream(aSerializedObject);
        final ObjectInputStream    ois   = new ObjectInputStream(input);

        final T objectClone = clazz.cast( ois.readObject() );
        ois.close();

        return objectClone;
    }

    /**
     * Clone using Serialization process
     *
     * @param <T> type of the object
     * @param anObject  The object to Serialize
     * @param clazz     The class to use for serialization
     * @return a clone of the giving object
     * @throws IOException if any I/O occurred
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
     */
    public static <T extends Serializable> T clone(
            @Nullable final T                   anObject,
            @Nonnull  final Class<? extends T>  clazz
            )
        throws IOException, ClassNotFoundException
    {
        return toObject( toByteArray( anObject ), clazz );
    }

    /**
     * Retrieve a Object from a {@link File} and it's Class Object
     * using standard serialization process.
     *
     * @param <T> type of the object
     * @param aFile A {@link File} where is store the object.
     * @param clazz of this object.
     * @return the object.
     *
     * @throws ClassNotFoundException - Class of a serialized object cannot be found.
     * @throws InvalidClassException - Something is wrong with a class used by serialization.
     * @throws StreamCorruptedException - Control information in the stream is inconsistent.
     * @throws OptionalDataException - Primitive data was found in the stream instead of objects.
     * @throws IOException - Any of the usual Input/Output related exceptions.
     *
     * @see #toFile(Serializable, File)
     */
    public static <T extends Serializable> T loadObject(
            @Nonnull final File                aFile,
            @Nonnull final Class<? extends T>  clazz
            )
        throws ClassNotFoundException, InvalidClassException, StreamCorruptedException, OptionalDataException, IOException
    {
        final InputStream input = new FileInputStream( aFile );

        try {
            final ObjectInputStream ois = new ObjectInputStream( input );

            try {
                T objectClone = clazz.cast( ois.readObject() );

                return objectClone;
                }
            finally {
                ois.close();
                }
            }
        finally {
            input.close();
            }
     }

    /**
     * Serialize an Object in a file
     *
     * @param <T> type of the object
     * @param anObject Object to serialize
     * @param aFile    A {@link File} to use to store object.
     *
     * @throws InvalidClassException - Something is wrong with a class used by serialization.
     * @throws NotSerializableException - Some object to be serialized does not implement the {@link Serializable} interface.
     * @throws IOException - Any exception thrown by the underlying OutputStream.
     *
     * @see #loadObject(File, Class)
     */
    public static <T extends Serializable> void toFile(
            @Nullable final T    anObject,
            @Nonnull  final File aFile
            )
        throws InvalidClassException, NotSerializableException, IOException
    {
        final OutputStream output = new FileOutputStream( aFile );

        try {
            final ObjectOutputStream oos = new ObjectOutputStream( output );

            try {
                oos.writeObject(anObject);
                }
            finally {
                oos.close();
                }
            }
        finally {
            output.close();
            }
    }
}
