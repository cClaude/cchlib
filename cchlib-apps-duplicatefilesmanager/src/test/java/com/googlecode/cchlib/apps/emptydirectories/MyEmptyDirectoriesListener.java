package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.folders.EmptyFolder;
import com.googlecode.cchlib.apps.emptydirectories.lookup.EmptyDirectoriesListener;
import com.googlecode.cchlib.util.SortedList;

final class MyEmptyDirectoriesListener implements EmptyDirectoriesListener, com.googlecode.cchlib.apps.emptydirectories.previousversion.EmptyDirectoriesListener
{
    private final static Logger logger = Logger.getLogger( MyEmptyDirectoriesListener.class );
    private boolean isCancel = false;
    private List<EmptyFolder> newIsEmptyList      = new SortedList<>();
    private List<EmptyFolder> newCouldBeEmptyList = new SortedList<>();
    private List<File>        oldList = new SortedList<>();
    private StringBuilder     sb      = new StringBuilder();

    @Override
    public boolean isCancel()
    {
        return isCancel;
    }
    @Override
    public void findStarted()
    {
        logger.info( "start finding" );
    }

    @Override
    public void findDone()
    {
        logger.info( "find done" );
    }
    @Override
    public void newEntry( EmptyFolder emptyFolder )
    {
        if( emptyFolder.isEmpty() ) {
            newIsEmptyList.add( emptyFolder );
            }
        else {
            newCouldBeEmptyList.add( emptyFolder );
            }

        sb.setLength( 0 );
        sb.append( "NEW-newEntry: EmptyFolder=" ).append( emptyFolder );
        logger.info( sb.toString() );

    }
    @Override
    public void newEntry( File emptyDirectoryFile )
    {
        oldList.add( emptyDirectoryFile );

        sb.setLength( 0 );
        sb.append( "OLD-newEntry: emptyDirectoryFile=" ).append( emptyDirectoryFile );
        logger.info( sb.toString() );
    }

    public List<EmptyFolder> getNewIsEmptyList()
    {
        return newIsEmptyList;
    }

    public List<EmptyFolder> getNewCouldBeEmptyList()
    {
        return newCouldBeEmptyList;
    }

    public List<File> getOldList()
    {
        return oldList;
    }
}
