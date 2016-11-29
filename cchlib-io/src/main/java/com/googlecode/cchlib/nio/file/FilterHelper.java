package com.googlecode.cchlib.nio.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Helper class for {@link File} (nio) manipulations
 */
public final class FilterHelper
{
    private FilterHelper()
    {
        // All static
    }

    private static class AndFilter implements DirectoryStream.Filter<Path>
    {
        private final DirectoryStream.Filter<Path> f1;
        private final DirectoryStream.Filter<Path> f2;

        private AndFilter( final DirectoryStream.Filter<Path> f1, final DirectoryStream.Filter<Path> f2 )
        {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public boolean accept(final Path entry) throws IOException
        {
            return this.f1.accept( entry ) && this.f2.accept( entry );
        }
    }

    private static class DirectoriesFilter implements DirectoryStream.Filter<Path>
    {
        static final DirectoriesFilter FILTER = new DirectoriesFilter();

        private DirectoriesFilter() { }

        @Override
        public boolean accept(final Path entry)
        {
            return Files.isDirectory( entry /*, LinkOption*/);
        }
    }


    private static class NotFilter implements DirectoryStream.Filter<Path>
    {
        private final DirectoryStream.Filter<Path> f;

        private NotFilter( final DirectoryStream.Filter<Path> f )
        {
            this.f = f;
        }

        @Override
        public boolean accept(final Path entry) throws IOException
        {
            return !this.f.accept( entry );
        }
    }

    private static class OrFilter implements DirectoryStream.Filter<Path>
    {
        private final DirectoryStream.Filter<Path> f1;
        private final DirectoryStream.Filter<Path> f2;

        private OrFilter( final DirectoryStream.Filter<Path> f1, final DirectoryStream.Filter<Path> f2 )
        {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public boolean accept(final Path entry) throws IOException
        {
            return this.f1.accept( entry ) || this.f2.accept( entry );
        }
    }

    /**
     * Create boolean and operation on two filters
     *
     * @param f1 First filter
     * @param f2 Second filter
     * @return a filter true if result of first filter is true
     *         and result of second filter is true
     */
    public static DirectoryStream.Filter<Path> and(
            final DirectoryStream.Filter<Path> f1,
            final DirectoryStream.Filter<Path> f2
            )
    {
        return new AndFilter( f1, f2 );
    }

    /**
     * Create a filter that return always false
     * @return a filter that result is always false
     */
    public static DirectoryStream.Filter<Path> falseFilter()
    {
        return entry -> false;
    }

    /**
     * Create a filter that return always true
     * @return a filter that result is always true
     */
    public static DirectoryStream.Filter<Path> newAcceptAllFilter()
    {
        return entry -> true;
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public static DirectoryStream.Filter<Path> newDirectoriesFilter()
    {
        return DirectoriesFilter.FILTER;
    }

    /**
     * Create a filter that return the opposite than the giving filter
     * @param filter filter to invert
     * @return a filter
     */
    public static DirectoryStream.Filter<Path> not(
        final DirectoryStream.Filter<Path> filter
        )
    {
        return new NotFilter( filter );
    }

    /**
     * Create boolean or operation on two filters
     *
     * @param f1 First filter
     * @param f2 Second filter
     * @return a filter true if result of first filter is true
     *         or result of second filter is true
     */
    public static DirectoryStream.Filter<Path> or(
            final DirectoryStream.Filter<Path> f1,
            final DirectoryStream.Filter<Path> f2
            )
    {
        return new OrFilter( f1, f2 );
    }

    /**
     * Create a {@link DirectoryStream.Filter} based on a {@link FileFilter}
     * @return a filter
     */
    public static DirectoryStream.Filter<Path> toFilter(
            @Nonnull final FileFilter fileFilter
            )
    {
        return entry -> fileFilter.accept( entry.toFile() );
    }
}
