package com.googlecode.cchlib.test;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.Nonnull;
import com.googlecode.cchlib.io.SerializableHelper;

/**
 * Helper to build {@link org.junit.Test} to test {@link Serializable} objects
 *
 * @since 4.1.5
 */
public final class SerializableTestCaseHelper
{
    private SerializableTestCaseHelper()
    {
        // All static
    }

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
     * @param anObject  Object to clone (not null)
     * @return a clone copy of giving object
     * @throws IOException if any
     * @throws ClassNotFoundException if class can not be found
     * @see #cloneOverSerialization(Serializable)
     */
    @SuppressWarnings("squid:S1160")
    public static <T extends Serializable> T testSerialization(
        @Nonnull final T anObject
        ) throws IOException, ClassNotFoundException
    {
        final T copy = cloneOverSerialization( anObject );

        org.junit.Assert.assertEquals( "Values do not matches", anObject, copy);
        org.junit.Assert.assertFalse( "Must be not same Object !", anObject==copy);

        return copy;
    }

    /**
     * Clone giving object using Serialization.
     *
     * @param <T>       Type of object
     * @param anObject  Object to clone (not null)
     * @return a clone copy of giving object
     * @throws IOException if any
     * @throws ClassNotFoundException if class can not be found
     */
    @SuppressWarnings("squid:S1160")
    public static <T extends Serializable> T cloneOverSerialization(
        @Nonnull final T anObject
        ) throws IOException, ClassNotFoundException
    {
        @SuppressWarnings("unchecked")
        final
        Class<T> clazz = (Class<T>)anObject.getClass();

        return SerializableHelper.clone( anObject, clazz );
    }
}
