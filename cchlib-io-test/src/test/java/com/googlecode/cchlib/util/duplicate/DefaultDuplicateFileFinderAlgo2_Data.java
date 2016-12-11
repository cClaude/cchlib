package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import com.googlecode.cchlib.io.IOTestHelper;
import com.googlecode.cchlib.io.IOHelper;


abstract class DefaultDuplicateFileFinderAlgo2_Data extends Base {

    protected File createPNGTempFile( final String prefixName ) throws IOException
    {
        return IOTestHelper.createPNGTempFile( prefixName );
    }

    private File createPNGTempFile( final String prefixName, final int alterOffset, final byte aByte ) throws IOException
    {
        final byte[] png = IOTestHelper.createPNG();

        assert png.length > alterOffset;
        assert png[ alterOffset ] != aByte;

        png[ alterOffset ] = aByte;

        return IOHelper.toFile( png, File.createTempFile( prefixName + '-', ".png" ) );
    }

    protected File createPNGTempFile2( final String prefixName ) throws IOException
    {
        return  createPNGTempFile( prefixName, (DEFAULT_ALTER_INDEX * 2) + 1, (byte)255 );
    }

    protected File createPNGTempFile3( final String prefixName ) throws IOException
    {
        return  createPNGTempFile( prefixName, (DEFAULT_ALTER_INDEX * 4) + 1, (byte)255 );
    }

}
