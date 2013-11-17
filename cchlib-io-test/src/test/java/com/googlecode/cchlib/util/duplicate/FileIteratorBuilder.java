package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import java.io.FileFilter;
import com.googlecode.cchlib.io.FileIterable;

public class FileIteratorBuilder
{
    private final File root;
    private final long fileMaxLength;
    private final int fileMaxCount;
    private int fileCount = 0;

    private FileIteratorBuilder(
        final File root,
        final long fileMaxLength,
        final int  fileMaxCount
        )
    {
        this.root = root;
        this.fileMaxLength = fileMaxLength;
        this.fileMaxCount  = fileMaxCount;
    }

    private Iterable<File> getFileIterator() {
        // TODO Auto-generated method stub
        return new FileIterable(
                root,
                new FileFilter() // Filter for files
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isFile() ) {
                            if( f.length() < fileMaxLength ) {
                                if( fileCount++ < fileMaxCount ) {
                                    return true;
                                    }
                                }
                            }
                        return false;
                    }
                },
                new FileFilter() // Filter for directories
                {
                    @Override
                    public boolean accept( File f )
                    {
                        if( f.isDirectory() ) {
                            if( fileCount < fileMaxCount ) {
                                return true;
                                }
                           }
                        return false;
                    }
                }
                );
    }

    public static Iterable<File> createFileIterator(
            final File root,
            final long fileMaxLength,
            final int  fileMaxCount
            )
    {
        return new FileIteratorBuilder( root, fileMaxLength, fileMaxCount ).getFileIterator();
    }
}
