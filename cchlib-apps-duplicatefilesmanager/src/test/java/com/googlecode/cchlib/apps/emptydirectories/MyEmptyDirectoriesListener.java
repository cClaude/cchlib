package com.googlecode.cchlib.apps.emptydirectories;

import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.SortedList;

final class MyEmptyDirectoriesListener
    implements EmptyDirectoriesListener
{
    private static final Logger LOGGER = Logger.getLogger( MyEmptyDirectoriesListener.class );

    private boolean isCancel = false;
    private List<EmptyFolder> newIsEmptyList      = new SortedList<>();
    private List<EmptyFolder> newCouldBeEmptyList = new SortedList<>();
    private StringBuilder     sb      = new StringBuilder();

    @Override
    public boolean isCancel()
    {
        return isCancel;
    }
    @Override
    public void findStarted()
    {
        LOGGER.info( "start finding" );
    }

    @Override
    public void findDone()
    {
        LOGGER.info( "find done" );
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
        LOGGER.info( sb.toString() );

    }

    public List<EmptyFolder> getIsEmptyList()
    {
        return newIsEmptyList;
    }

    public List<EmptyFolder> getCouldBeEmptyList()
    {
        return newCouldBeEmptyList;
    }
}
