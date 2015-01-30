package com.googlecode.cchlib.apps.emptydirectories.path.lookup;

import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Path;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesFinder_Base;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesLookup;

public class EmptyDirectoriesFinderTest extends EmptyDirectoriesFinder_Base<Filter<Path>>
{
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoriesFinderTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected Filter<Path> getFilter()
    {
        return getMyFilter().toFilter();
    }

    @Override
    protected EmptyDirectoriesLookup<Filter<Path>> getEmptyDirectoriesLookup()
    {
        return new DefaultEmptyDirectoriesLookup( getRootDirs() );
    }
}
