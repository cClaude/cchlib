package com.googlecode.cchlib.apps.emptydirectories.file.lookup;

import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.emptydirectories.EmptyDirectoriesFinder_Base;
import com.googlecode.cchlib.util.emptydirectories.EmptyDirectoriesLookup;
import com.googlecode.cchlib.util.emptydirectories.lookup.DefaultEmptyDirectoriesLookup;
import com.googlecode.cchlib.util.emptydirectories.lookup.ExcludeDirectoriesFileFilter;

public class EmptyDirectoriesFinderTest
    extends EmptyDirectoriesFinder_Base<ExcludeDirectoriesFileFilter>
{
    private static final Logger LOGGER = Logger.getLogger( EmptyDirectoriesFinderTest.class );

    @Override
    protected Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    protected ExcludeDirectoriesFileFilter getFilter()
    {
        return getMyFilter().toExcludeDirectoriesFileFilter();
    }

    @Override
    protected EmptyDirectoriesLookup<ExcludeDirectoriesFileFilter> getEmptyDirectoriesLookup()
    {
        return new DefaultEmptyDirectoriesLookup( getRootDirs() );
    }
}
