
package cx.ath.choisnet.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Use {@link SerializableHelper} instead
 * 
 * @author Claude CHOISNET
 *
 */
@Deprecated
public class Serialization
{

    public Serialization()
    {

    }

    /** 
     * use {@link SerializableHelper#clone(java.io.Serializable, Class)}
     * 
     * @param <T>
     * @param anObject
     * @param clazz
     * @return a clone object using ObjectOutputStream
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public static <T> T clone(Object anObject, Class<T> clazz)
        throws java.io.IOException, ClassNotFoundException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(output);

        oos.writeObject(anObject);

        oos.flush();
        oos.close();
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(input);

        T objectClone = clazz.cast(ois.readObject());
        ois.close();

        return objectClone;
    }

    public static <T> byte[] toByteArray(T anObject, Class<T> clazz)
        throws java.io.IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(output);

        oos.writeObject(anObject);

        oos.flush();
        oos.close();
        return output.toByteArray();

    }

    public static <T> T newFromByteArray(byte[] aSerializedObject, Class<T> clazz)
        throws java.io.IOException, ClassNotFoundException
    {
        ByteArrayInputStream input = new ByteArrayInputStream(aSerializedObject);

        ObjectInputStream ois = new ObjectInputStream(input);

        T objectClone = clazz.cast(ois.readObject());

        ois.close();
        return objectClone;
    }
}
