package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Some useful tools for java.io.Serializable
 *
 * @see java.io.Serializable
 * @deprecated use {@link com.googlecode.cchlib.io.SerializableHelper} instead.
 */
@Deprecated
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
     * @return a byte array build using standard serialization
     *         process for this object
     * @throws IOException if any I/O occurred
     */
    public static <T extends Serializable> byte[] toByteArray( final T anObject )
        throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream    oos    = new ObjectOutputStream(output);

        oos.writeObject(anObject);
        oos.flush();
        oos.close();

        return output.toByteArray();
    }

    /**
     * Retrieve a Object from a byte array and it's Class Object
     * using standard serialization process.
     *
     * @param <T> type of the object
     * @param aSerializedObject
     * @param clazz
     * @return the object.
     * @throws IOException if any I/O occurred
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T toObject(
            final byte[]              aSerializedObject,
            final Class<? extends T>  clazz
            )
        throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream input = new ByteArrayInputStream(aSerializedObject);
        ObjectInputStream    ois   = new ObjectInputStream(input);

        T objectClone = clazz.cast(ois.readObject());
        ois.close();

        return objectClone;
    }

    /**
     * Clone using Serialization process
     *
     * @param <T> type of the object
     * @param anObject
     * @param clazz
     * @return a clone of the giving object
     * @throws IOException if any I/O occurred
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T clone(
            final T                   anObject,
            final Class<? extends T>  clazz
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
     * @throws IOException if any I/O occurred
     * @throws ClassNotFoundException
     * @see #toFile(Serializable, File)
     * @since 4.1.5
     */
    public static <T extends Serializable> T loadObject(
            final File                aFile,
            final Class<? extends T>  clazz
            )
        throws IOException, ClassNotFoundException
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
     * @param aFile     {@link File} to use to store object.
     * @throws IOException if any I/O occurred
     * @see #loadObject(File, Class)
     * @since 4.1.5
     */
    public static <T extends Serializable> void toFile(
            final T    anObject,
            final File aFile
            )
        throws IOException
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
