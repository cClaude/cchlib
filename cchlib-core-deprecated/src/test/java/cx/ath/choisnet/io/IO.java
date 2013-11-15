package cx.ath.choisnet.io;

import java.io.InputStream;

public class IO
{
    public static final String PNG_FILE = "test.png";

    public static final InputStream getPNGFile()
    {
        return IO.class.getResourceAsStream( PNG_FILE );
    }
}
