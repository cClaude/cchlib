package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODOC
 */
public class AndFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 2L;
    private List<SerializableFileFilter> filters
          = new ArrayList<SerializableFileFilter>();

    /**
     * TODOC
     */
    public AndFileFilter()
    {
        // empty
    }

    /**
     * TODOC
     * @param filter a {@link SerializableFileFilter} to include in matching
     * @return the caller. This allows for easy chaining of invocations.
     */
    public AndFileFilter add( SerializableFileFilter filter )
    {
        this.filters.add( filter );
        return this;
    }

    /**
     * TODOC
     * @param filtersCollection a @{@link Collection} of
     * {@link SerializableFileFilter} to include in matching
     * @return the caller. This allows for easy chaining of invocations.
     */
    public AndFileFilter addAll( Collection<SerializableFileFilter> filtersCollection )
    {
        this.filters.addAll( filtersCollection );
        return this;
    }

    @Override
    public boolean accept( File file )
    {
      for( FileFilter ff : filters ) {
            if( !ff.accept( file ) ) {
                return false;
                }
            }
        return true;
    }

}
