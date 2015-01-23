package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

//not public
abstract class Base {
    //not public
    final class LoggerEventListener implements DuplicateFileFinderEventListener {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCancel()
        {
            return false;
        }

        @Override
        public void analysisStart( final File file )
        {
            // Analysis start
            getLogger().info( "analysisStart for file: " + file );
        }

        @Override
        public void analysisStatus( final File file, final long length )
        {
            getLogger().info( "analysisStatus for file: " + file + " , " + length );
        }

        @Override
        public void analysisDone( final File file, final String hashString )
        {
            getLogger().info( "analysisDone for file: " + file + " : hash = " + hashString );
        }

        @Override
        public void ioError( final File file, final IOException ioe )
        {
            getLogger().info( "ioError on file: " + file, ioe );
        }
    }

    protected abstract Logger getLogger();
}
