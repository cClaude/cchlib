package com.googlecode.cchlib.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * For test ONLY
 */
public class IO
{
    public final static String PNG_FILE = "test.png";

    /**
     * Returns an InputStream within a valid PNG file
     * @return an InputStream within a valid PNG file
     * @throws FileNotFoundException if file not found
     */
    public final static InputStream getPNGFile() 
        throws FileNotFoundException
    {
        InputStream is = IO.class.getResourceAsStream( PNG_FILE );
        
        if( is == null ) {
            throw new FileNotFoundException( PNG_FILE );
            }
        
        return is;
    }
}
