package paper.reflexion.serialization;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;
import paper.reflexion.LegacyParentClass;
import paper.reflexion.serialization.ParentAccessSerializable;

/**
 *
 */
public class ParentAccessSerializableTest
{
    @Test
    public void test_Serialization() throws ClassNotFoundException, IOException
    {
        ParentAccessSerializable<LegacyParentClass> pa0 = new ParentAccessSerializable<LegacyParentClass>( LegacyParentClass.class );

        // Launch serialization
        ParentAccessSerializable<LegacyParentClass> pa1 = SerializableTestCaseHelper.cloneOverSerialization( pa0 );

        List<Field> hiddenFields0 = pa0.getHiddenFieldList();
        List<Field> hiddenFields1 = pa1.getHiddenFieldList();

        Assert.assertEquals( hiddenFields0.size(), hiddenFields1.size() );

        Iterator<Field> i0 = hiddenFields0.iterator();
        Iterator<Field> i1 = hiddenFields1.iterator();

        while( i0.hasNext() && i1.hasNext() ) {
            Field v0 = i0.next();
            Field v1 = i1.next();

            Assert.assertEquals( v0, v1 );
            }

        Assert.assertFalse( i0.hasNext() );
        Assert.assertFalse( i1.hasNext() );
    }

}