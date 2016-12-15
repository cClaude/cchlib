package com.googlecode.cchlib.io.checksum;

import java.io.File;

//Not public
class ToolBox
{
    private ToolBox()
    {
        // All static
    }

    public static boolean fileNotChanged(
        final File file,
        final long lastModified,
        final long length
        )
    {
        if( lastModified != file.lastModified() ) {
            return false;
        }

        if( length != file.length() ) {
            return false;
        }

        return true;
    }

}
