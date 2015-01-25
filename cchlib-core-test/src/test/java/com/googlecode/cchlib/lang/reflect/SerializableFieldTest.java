package com.googlecode.cchlib.lang.reflect;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.lang.reflect.Field;
import org.fest.assertions.Assertions;
import org.junit.Test;
import com.googlecode.cchlib.io.SerializableHelper;

public class SerializableFieldTest {


    @Test
    public void testSerializable() throws NoSuchMethodException, SecurityException, InvalidClassException, NotSerializableException, IOException, ClassNotFoundException, NoSuchFieldException
    {
        final Field field = String.class.getFields()[0];
        final SerializableField serializableField = new SerializableField( field );

        Assertions.assertThat( serializableField.getField() ).isEqualTo( field );

        final byte[] bytes = SerializableHelper.toByteArray( serializableField );

        final SerializableField object = SerializableHelper.toObject( bytes, SerializableField.class );

        Assertions.assertThat( object.getField() ).isEqualTo( field );
    }
}
