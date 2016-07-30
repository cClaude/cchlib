package com.googlecode.cchlib.apps.emptydirectories.file.lookup;

import java.io.FileFilter;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesFinder_Base;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.util.emptydirectories.lookup.DefaultEmptyDirectoriesLookup;

public class EmptyDirectoriesFinderTest extends EmptyDirectoriesFinder_Base<FileFilter>
{
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoriesFinderTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected FileFilter getFilter()
    {
        return getMyFilter().toFileFilter();
    }

    @Override
    protected EmptyDirectoriesLookup<FileFilter> getEmptyDirectoriesLookup()
    {
        return new DefaultEmptyDirectoriesLookup( getRootDirs() );
    }
}
