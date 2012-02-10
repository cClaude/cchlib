package yann;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import cx.ath.choisnet.test.SerializableTestCaseHelper;

/**
 *
 */
public class ParentAccessTest
{
    @Test
    public void test_Serialization() throws ClassNotFoundException, IOException
    {
        ParentAccess<ParentClass> pa0 = new ParentAccess<ParentClass>( ParentClass.class );

        // Launch serialization
        ParentAccess<ParentClass> pa1 = SerializableTestCaseHelper.cloneOverSerialization( pa0 );

        List<Field> hiddenFields0 = pa0.getHiddenFieldList();
        List<Field> hiddenFields1 = pa1.getHiddenFieldList();

        Assert.assertEquals( hiddenFields0.size(), hiddenFields1.size() );
    }

}
