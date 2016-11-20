package com.googlecode.cchlib.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.io.SerializableFileFilter;

/**
 * NEEDDOC
 *
 * @since 4.1.7
 */
public final class ORFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private final List<FileFilter> filters = new ArrayList<>();

    /**
     * NEEDDOC
     */
    public ORFileFilter()
    {
        // empty
    }

    /**
     * NEEDDOC
     * @param fileFilters
     */
    public ORFileFilter( final FileFilter...fileFilters )
    {
        for( final FileFilter ff : fileFilters ) {
            this.add( ff );
            }
    }

    /**
     * NEEDDOC
     * @param filter a {@link SerializableFileFilter} to include in matching
     * @return the caller. This allows for easy chaining of invocations.
     */
    public ORFileFilter add( final FileFilter filter )
    {
        this.filters.add( filter );
        return this;
    }

    /**
     * NEEDDOC
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
      for( final FileFilter ff : this.filters ) {
            if( ff.accept( file ) ) {
                return true;
                }
            }
        return false;
    }
}
