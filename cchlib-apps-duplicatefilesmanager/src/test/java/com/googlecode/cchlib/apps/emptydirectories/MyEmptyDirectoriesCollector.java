package com.googlecode.cchlib.apps.emptydirectories;

import java.util.List;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.SortedList;

public final class MyEmptyDirectoriesCollector
{
    private final class Listener implements EmptyDirectoriesListener {
        private final StringBuilder sb = new StringBuilder();

        @Override
        public boolean isCancel()
        {
            return false;
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
        public void newEntry( final EmptyFolder emptyFolder )
        {
            if( emptyFolder.isEmpty() ) {
                MyEmptyDirectoriesCollector.this.newIsEmptyList.add( emptyFolder );
                }
            else {
                MyEmptyDirectoriesCollector.this.newCouldBeEmptyList.add( emptyFolder );
                }

            this.sb.setLength( 0 );
            this.sb.append( "NEW-newEntry: EmptyFolder=" ).append( emptyFolder );

            LOGGER.info( this.sb.toString() );
        }
    }

    private static final Logger LOGGER = Logger.getLogger( MyEmptyDirectoriesCollector.class );

    private final List<EmptyFolder>         newIsEmptyList      = new SortedList<>();
    private final List<EmptyFolder>         newCouldBeEmptyList = new SortedList<>();
    private final EmptyDirectoriesListener  listener            = new Listener();

    public EmptyDirectoriesListener toEmptyDirectoriesListener()
    {
        return this.listener;
    }

    public List<EmptyFolder> getIsEmptyList()
    {
        return this.newIsEmptyList;
    }

    public List<EmptyFolder> getCouldBeEmptyList()
    {
        return this.newCouldBeEmptyList;
    }
}
