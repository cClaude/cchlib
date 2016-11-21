package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;
import org.apache.log4j.Logger;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import com.googlecode.cchlib.apps.duplicatefiles.FilesPathsTestHelper;
import com.googlecode.cchlib.util.CancelRequestException;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesLookup;

public abstract class EmptyDirectoriesFinder_Base<FILTER> {
    private MyEmptyDirectoriesCollector collector;
    private File[]                      rootDirs;
    private MyExcludeDirectoriesFilter  myFilter;

    protected abstract Logger getLogger();
    protected abstract FILTER getFilter();
    protected abstract EmptyDirectoriesLookup<FILTER> getEmptyDirectoriesLookup();

    @Before
    public void before()
    {
        this.collector = new MyEmptyDirectoriesCollector();
        this.rootDirs  = FilesPathsTestHelper.getStartFiles();
        this.myFilter  = new MyExcludeDirectoriesFilter();
    }

    protected MyExcludeDirectoriesFilter getMyFilter()
    {
        return this.myFilter;
    }

    protected File[] getRootDirs()
    {
        return this.rootDirs;
    }

    @Test
    public void testFindDirNoCheckDebug()
    {
        final FILTER filter = getFilter();

        getLogger().info( "testFindDirNoCheckDebug()" );
        getLogger().info( "#################################" );

        getLogger().info( "#################################" );
        final EmptyDirectoriesLookup<FILTER> emptyDirs
            = getEmptyDirectoriesLookup();

        emptyDirs.addListener( this.collector.toEmptyDirectoriesListener() );

        try {
            emptyDirs.lookup( filter );
            }
        catch( final CancelRequestException e ) { // $codepro.audit.disable logExceptions
            getLogger().warn( "testFindDir() - emptyDirs.lookup() - CancelRequestException" );
            }

        checkResult( this.myFilter );
    }

    private void checkResult( final MyExcludeDirectoriesFilter myFilter )
    {
        getLogger().info( "#########[FILTER]#######" );
        getLogger().info( "filter.getPathList().size() = " + myFilter.getPathList().size() );

        {
            final Set<Path> pathSet = myFilter.createPathSet();

            getLogger().info( "filter.createPathSet().size() = " + pathSet.size() );

            Assertions.assertThat( myFilter.getPathList().size() ).isEqualTo( pathSet.size() );

            for( final Path p : myFilter.getPathList() ) {
                getLogger().info( "p = " + p );
                }
        }

        getLogger().info( "#################################" );
        getLogger().info( "testFindDir() - done" );
        getLogger().info( "NEWVersion getNewIsEmptyList()     = " + this.collector.getIsEmptyList().size() );
        getLogger().info( "NEWVersion getNewCouldBeEmptyList()= " + this.collector.getCouldBeEmptyList().size() );
        getLogger().info( "#################################" );
    }
}
