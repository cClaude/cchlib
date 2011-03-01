/**
 *
 */
package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Some useful tools for java.io.Serializable
 *
 * @author Claude CHOISNET
 * @see java.io.Serializable
 */
public class SerializableHelper
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
     * @throws java.io.IOException
     */
    public static <T extends Serializable> byte[] toByteArray(
            T   anObject
            )
        throws java.io.IOException
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
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T toObject(
            byte[]              aSerializedObject,
            Class<? extends T>  clazz
            )
        throws java.io.IOException, ClassNotFoundException
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
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T clone(
            T                   anObject, 
            Class<? extends T>  clazz
            )
    throws java.io.IOException, ClassNotFoundException
    {
        return toObject( toByteArray( anObject ), clazz );
    }
    
}
