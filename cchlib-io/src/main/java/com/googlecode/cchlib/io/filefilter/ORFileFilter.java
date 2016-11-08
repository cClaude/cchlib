package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODOC
 *
 * @since 4.1.7
 */
public final class ORFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private List<FileFilter> filters = new ArrayList<>();

    /**
     * TODOC
     */
    public ORFileFilter()
    {
        // empty
    }

    /**
     * TODOC
     * @param fileFilters
     */
    public ORFileFilter( final FileFilter...fileFilters )
    {
        for( FileFilter ff : fileFilters ) {
            this.add( ff );
            }
    }

    /**
     * TODOC
     * @param filter a {@link SerializableFileFilter} to include in matching
     * @return the caller. This allows for easy chaining of invocations.
     */
    public ORFileFilter add( final FileFilter filter )
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
    public ORFileFilter addAll(
        final Collection<SerializableFileFilter> filtersCollection
        )
    {
        this.filters.addAll( filtersCollection );
        return this;
    }

    @Override
    public boolean accept( final File file )
    {
      for( FileFilter ff : filters ) {
            if( ff.accept( file ) ) {
                return true;
                }
            }
        return false;
    }
}
