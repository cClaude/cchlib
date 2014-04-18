package com.googlecode.cchlib.nio.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * TODOC
 *
 */
public final class FilterHelper
{
    private FilterHelper() {}

    private static class AcceptAllFilter implements DirectoryStream.Filter<Path>
    {
        private AcceptAllFilter() { }

        @Override
        public boolean accept(Path entry) { return true; }

        static final AcceptAllFilter FILTER = new AcceptAllFilter();
    }

    /**
     * TODOC
     * @return TODOC
     */
    public static DirectoryStream.Filter<Path> newAcceptAllFilter()
    {
        return AcceptAllFilter.FILTER;
    }


    private static class DirectoriesFilter implements DirectoryStream.Filter<Path>
    {
        private DirectoriesFilter() { }

        @Override
        public boolean accept(Path entry)
        {
            return Files.isDirectory( entry /*, LinkOption*/);
        }

        static final DirectoriesFilter FILTER = new DirectoriesFilter();
    }

    /**
     * TODOC
     * @return TODOC
     */
    public static DirectoryStream.Filter<Path> newDirectoriesFilter()
    {
        return DirectoriesFilter.FILTER;
    }

    private static class OrFilter implements DirectoryStream.Filter<Path>
    {
        private final Filter<Path> f1;
        private final Filter<Path> f2;

        private OrFilter( final Filter<Path> f1, final Filter<Path> f2 )
        {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public boolean accept(Path entry) throws IOException
        {
            return this.f1.accept( entry ) || this.f2.accept( entry );
        }
    }

    public static Filter<Path> or( final Filter<Path> f1, final Filter<Path> f2 )
    {
        return new OrFilter( f1, f2 );
    }

    private static class AndFilter implements DirectoryStream.Filter<Path>
    {
        private final Filter<Path> f1;
        private final Filter<Path> f2;

        private AndFilter( final Filter<Path> f1, final Filter<Path> f2 )
        {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public boolean accept(Path entry) throws IOException
        {
            return this.f1.accept( entry ) && this.f2.accept( entry );
        }
    }

    public static Filter<Path> and( final Filter<Path> f1, final Filter<Path> f2 )
    {
        return new AndFilter( f1, f2 );
    }
    
    private static class NotFilter implements DirectoryStream.Filter<Path>
    {
        private final Filter<Path> f;

        private NotFilter( final Filter<Path> f )
        {
            this.f = f;
        }

        @Override
        public boolean accept(Path entry) throws IOException
        {
            return !this.f.accept( entry );
        }
    }

    public static Filter<Path> not( final Filter<Path> f )
    {
        return new NotFilter( f );
    }
}
