package com.googlecode.cchlib.lang.reflect;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.lang.reflect.Method;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.SerializableHelper;

public class SerializableMethodTest {


    @Test
    public void testSerializable() throws NoSuchMethodException, SecurityException, InvalidClassException, NotSerializableException, IOException, ClassNotFoundException
    {
        final Method method = String.class.getMethod( "length" );
        final SerializableMethod serializableMethod = new SerializableMethod( method );

        Assertions.assertThat( serializableMethod.getMethod() ).isEqualTo( method );

        final byte[] bytes = SerializableHelper.toByteArray( serializableMethod );

        final SerializableMethod object = SerializableHelper.toObject( bytes, SerializableMethod.class );

        Assertions.assertThat( object.getMethod() ).isEqualTo( method );
    }
}
