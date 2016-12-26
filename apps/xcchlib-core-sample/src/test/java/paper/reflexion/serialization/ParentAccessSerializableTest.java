package paper.reflexion.serialization;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import paper.reflexion.LegacyParentClass;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;

public class ParentAccessSerializableTest
{
    @Test
    public void test_Serialization() throws ClassNotFoundException, IOException
    {
        final ParentAccessSerializable<LegacyParentClass> pa0 = new ParentAccessSerializable<LegacyParentClass>( LegacyParentClass.class );

        // Launch serialization
        final ParentAccessSerializable<LegacyParentClass> pa1 = SerializableTestCaseHelper.cloneOverSerialization( pa0 );

        final List<Field> hiddenFields0 = pa0.getHiddenFields();
        final List<Field> hiddenFields1 = pa1.getHiddenFields();

        Assert.assertEquals( hiddenFields0.size(), hiddenFields1.size() );

        final Iterator<Field> i0 = hiddenFields0.iterator();
        final Iterator<Field> i1 = hiddenFields1.iterator();

        while( i0.hasNext() && i1.hasNext() ) {
            final Field v0 = i0.next();
            final Field v1 = i1.next();

            Assert.assertEquals( v0, v1 );
            }

        Assert.assertFalse( i0.hasNext() );
        Assert.assertFalse( i1.hasNext() );
    }

}
