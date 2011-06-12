package cx.ath.choisnet.io;

import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

public class InputStreamHelperTest
{
    public final static String PNG_FILE = "test.png";
    

    public final static InputStream getPNGFile()
    {
        return InputStreamHelperTest.class.getResourceAsStream( PNG_FILE );
    }
   
    @Test
    public void test_getPNGFile() throws IOException
    {
        InputStream is = getPNGFile();
        
        Assert.assertNotNull( is );
        is.close();
    }

    @Test
    public void test_isEquals() throws IOException
    {
        InputStream is1 = getPNGFile();
        InputStream is2 = getPNGFile();
        boolean     r   = InputStreamHelper.isEquals( is1, is2 );
        
        Assert.assertNotNull( r );
        
        is1.close();
        is2.close();
    }
}
