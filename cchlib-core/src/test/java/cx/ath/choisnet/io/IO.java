package cx.ath.choisnet.io;

import java.io.InputStream;

public class IO
{
    public final static String PNG_FILE = "test.png";

    public final static InputStream getPNGFile()
    {
        return IO.class.getResourceAsStream( PNG_FILE );
    }
}
