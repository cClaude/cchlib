package com.googlecode.cchlib.util.duplicate;

import com.googlecode.cchlib.io.FileIterable;
import java.io.File;

//NOT public
class FileIteratorBuilder
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
        return new FileIterable(
                root,
                f -> {
                    if( f.isFile() ) {
                        if( f.length() < fileMaxLength ) {
                            if( fileCount++ < fileMaxCount ) {
                                return true;
                                }
                            }
                        }
                    return false;
                },
                f -> {
                    if( f.isDirectory() ) {
                        if( fileCount < fileMaxCount ) {
                            return true;
                            }
                       }
                    return false;
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
