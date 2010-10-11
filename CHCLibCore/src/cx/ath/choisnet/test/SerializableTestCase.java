package cx.ath.choisnet.test;

import java.io.IOException;
import java.io.Serializable;
import cx.ath.choisnet.io.SerializableHelper;
import junit.framework.TestCase;

/**
 * Base for making a {@link TestCase} for testing
 * {@link Serializable} objects
 *
 * @author Claude CHOISNET
 */
public class SerializableTestCase extends TestCase
{
    /**
     * Clone giving object using Serialization and
     * verify that objects are different (not same reference)
     * and verify that objects are equals (using {@link Object#equals(Object)}).
     *
     * @param <T>       Type of object
     * @param anObject  Object to clone
     * @return a clone copy of giving object
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T testSerialization(
            T anObject
            )
        throws IOException, ClassNotFoundException
    {
        T copy = cloneOverSerialization(anObject);

        assertEquals( "Values do not matches", anObject, copy);
        assertFalse( "Must be not same Object !", anObject==copy);

        return copy;
    }

    /**
     * Clone giving object using Serialization.
     *
     * @param <T>       Type of object
     * @param anObject  Object to clone
     * @return a clone copy of giving object
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public static <T extends Serializable> T cloneOverSerialization(
            T anObject
            )
        throws java.io.IOException, ClassNotFoundException
    {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>)anObject.getClass();

        return SerializableHelper.clone( anObject, clazz );
    }
}
