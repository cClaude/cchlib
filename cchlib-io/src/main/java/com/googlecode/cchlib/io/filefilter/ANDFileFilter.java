package com.googlecode.cchlib.io.filefilter;

import com.googlecode.cchlib.io.SerializableFileFilter;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Handle a collection of {@link FileFilter} to generate
 * this {@link FileFilter}.
 * <p>
 *
 * </p>
 * @since 4.1.7
 * @see SerializableFileFilter
 */
public class ANDFileFilter implements SerializableFileFilter
{
    private static final long serialVersionUID = 1L;
    private List<FileFilter> filters = new ArrayList<>();

    /**
     * Create an ANDFileFilter
     */
    public ANDFileFilter()
    {
        // empty
    }

    /**
     * Create an ANDFileFilter
     *
     * @param fileFilters Array of {@link FileFilter} to include in matching
     */
    public ANDFileFilter( FileFilter...fileFilters )
    {
        for( FileFilter ff : fileFilters ) {
            this.add( ff );
            }
    }

    /**
     * Add a {@link FileFilter}
     * @param filter a {@link SerializableFileFilter} to include in matching
     * @return the caller. This allows for easy chaining of invocations.
     */
    public ANDFileFilter add( final FileFilter filter )
    {
        this.filters.add( filter );
        return this;
    }

    /**
     * Add a @{@link Collection} of {@link FileFilter}
     * @param filtersCollection a @{@link Collection} of
     * {@link FileFilter} to include in matching
     * @return the caller. This allows for easy chaining of invocations.
     */
    public ANDFileFilter addAll(
        final Collection<FileFilter> filtersCollection
        )
    {
        this.filters.addAll( filtersCollection );
        return this;
    }

    @Override
    public boolean accept( final File file )
    {
      for( FileFilter ff : filters ) {
            if( !ff.accept( file ) ) {
                return false;
                }
            }
        return true;
    }

}
