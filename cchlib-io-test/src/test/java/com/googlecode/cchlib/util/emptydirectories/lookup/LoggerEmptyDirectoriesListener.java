package com.googlecode.cchlib.util.emptydirectories.lookup;

import static org.fest.assertions.Assertions.assertThat;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesListener;
import com.googlecode.cchlib.util.emptydirectories.EmptyFolder;

class LoggerEmptyDirectoriesListener implements EmptyDirectoriesListener
{
    private static final Logger     LOGGER          = Logger.getLogger( DefaultEmptyDirectoriesLookupTest.class );
    private final List<EmptyFolder> emptyFolderList = new ArrayList<>();

    public List<EmptyFolder> getEmptyFolderList()
    {
        return this.emptyFolderList;
    }

    @Override
    public boolean isCancel()
    {
        return false;
    }

    @Override
    public void newEntry( final EmptyFolder emptyFolder )
    {
        LOGGER.info( "emptyFolder: " + emptyFolder );

        final File f = emptyFolder.getPath().toFile();
        assertThat( f ).as( "Not a directory" ).isDirectory();

        final File[] files = f.listFiles();
        assertThat( files ).as( "Not a valid directory" ).isNotNull();

        if( emptyFolder.isEmpty() ) {
            LOGGER.warn( "Warn found " + files.length + " file(s)" );

            for( final File file : files ) {
                LOGGER.warn( "> " + file + " (" + file.length() + ") D:" + file.isDirectory() );
            }

            assertThat( files ).as( "Not empty: " + emptyFolder ).isEmpty();
        } else {
            assertThat( files ).as( "Is empty: " + emptyFolder ).isNotEmpty();

            for( final File file : files ) {
                assertThat( file ).as( "found a none directory object: " + file ).isDirectory();
            }
        }

        this.emptyFolderList.add( emptyFolder );
    }

    @Override
    public void findStarted()
    {
        LOGGER.info( "findStarted()" );
    }

    @Override
    public void findDone()
    {
        LOGGER.info( "findDone()" );
    }
}
