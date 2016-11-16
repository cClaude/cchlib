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
    private static class AcceptAllFilter implements DirectoryStream.Filter<Path>
    {
        static final AcceptAllFilter FILTER = new AcceptAllFilter();

        private AcceptAllFilter() { }

        @Override
        public boolean accept(final Path entry) { return true; }
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
     * NEEDDOC
     * @return NEEDDOC
     */
    public static DirectoryStream.Filter<Path> and( final DirectoryStream.Filter<Path> f1, final DirectoryStream.Filter<Path> f2 )
    {
        return new AndFilter( f1, f2 );
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
   public static DirectoryStream.Filter<Path> falseFilter()
    {
        return entry -> false;
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public static DirectoryStream.Filter<Path> newAcceptAllFilter()
    {
        return AcceptAllFilter.FILTER;
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
     * NEEDDOC
     * @return NEEDDOC
     */
    public static DirectoryStream.Filter<Path> not( final DirectoryStream.Filter<Path> f )
    {
        return new NotFilter( f );
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
    public static DirectoryStream.Filter<Path> or( final DirectoryStream.Filter<Path> f1, final DirectoryStream.Filter<Path> f2 )
    {
        return new OrFilter( f1, f2 );
    }

    /**
     * NEEDDOC
     * @return NEEDDOC
     */
   public static DirectoryStream.Filter<Path> toFilter( @Nonnull final FileFilter fileFilter )
    {
        return entry -> fileFilter.accept( entry.toFile() );
    }

    private FilterHelper() {}
}
