package cx.ath.choisnet.test;

import java.io.IOException;
import java.io.Serializable;
import cx.ath.choisnet.io.SerializableHelper;
import junit.framework.TestCase; // Use TestCase instead Assert to not confused with Assert that exist in this package

/**
 * Helper to build {@link TestCase} to test {@link Serializable} objects
 * 
 * @since 1.4.5
 */
final
public class SerializableTestCaseHelper
{
    /**
     * Clone giving object using Serialization and
     * verify that objects are different (not same reference)
     * and verify that objects are equals (using {@link Object#equals(Object)}).
     * <P>
     * T <b>MUST</b> implement equals, if not you
     * must consider to use {@link #cloneOverSerialization(Serializable)}
     * and make you own test.
     * </P>
     * @param <T>       Type of object
     * @param anObject  Object to clone
     * @return a clone copy of giving object
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     * @see #cloneOverSerialization(Serializable)
     */
    public static <T extends Serializable> T testSerialization(
            T anObject
            )
        throws IOException, ClassNotFoundException
    {
        T copy = cloneOverSerialization(anObject);

        TestCase.assertEquals( "Values do not matches", anObject, copy);
        TestCase.assertFalse( "Must be not same Object !", anObject==copy);

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
