package com.googlecode.cchlib.util.duplicate.digest;

import java.io.File;
import org.apache.log4j.Logger;

//not public
final class MyFileDigestListener implements FileDigestListener {

    private final Logger logger;

    public MyFileDigestListener( final Logger logger )
    {
        this.logger = logger;
    }

    @Override
    public boolean isCancel()
    {
        return false;
    }

    @Override
    public void computeDigest( final File file, final int position )
    {
        this.logger.info( "File:" + file + "@" + position );
    }
}