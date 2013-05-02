package com.googlecode.cchlib.apps.emptydirectories;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import com.googlecode.cchlib.io.FileFilterHelper;

public class FolderFilterHelper 
{
    private FolderFilterHelper() {}
    
    private static class MyPathFilter implements DirectoryStream.Filter<Path>
    {
        private FileFilter fileFilter;
        public MyPathFilter( FileFilter filter )
        {
            this.fileFilter = filter;
        }
        @Override
        public boolean accept( Path entry ) throws IOException
        {
            return fileFilter.accept( entry.toFile() );
        }
    }
    private static class MyFileFilter implements FileFilter
    {
        private DirectoryStream.Filter<Path> pathFilter;
        public MyFileFilter( DirectoryStream.Filter<Path> filter )
        {
            this.pathFilter = filter;
        }
        @Override
        public boolean accept( File file )
        {
            try {
                return pathFilter.accept( file.toPath() );
                }
            catch( IOException e ) {
                throw new RuntimeException( e );
                }
        }
    }
    private static class XFilter implements FolderFilter
    {
        private FileFilter fileFilter;
        private DirectoryStream.Filter<Path> pathFilter;
        public XFilter( FileFilter filter )
        {
            this.fileFilter = filter;
            this.pathFilter = new MyPathFilter( filter );
        }
        public XFilter( DirectoryStream.Filter<Path> filter )
        {
            this.fileFilter = new MyFileFilter( filter );
            this.pathFilter = filter;
        }
        @Override
        public DirectoryStream.Filter<Path> toFilter()
        {
            return pathFilter;
        }
        @Override
        public FileFilter toFileFilter()
        {
            return fileFilter;
        }
    }

    public static FolderFilter falseFileFilter()
    {
        return createFolderFilter( FileFilterHelper.falseFileFilter() );
    }
    public static FolderFilter createFolderFilter( FileFilter filter )
    {
        return new XFilter( filter );
    }
    public static FolderFilter createFolderFilter( DirectoryStream.Filter<Path> filter )
    {
        return new XFilter( filter );
    }
}
