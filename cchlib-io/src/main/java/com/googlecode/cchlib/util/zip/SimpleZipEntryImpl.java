package com.googlecode.cchlib.util.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import com.googlecode.cchlib.util.zip.ZipEntryBuilder.Reference;

final class SimpleZipEntryImpl implements SimpleZipEntry
{
    private static final long serialVersionUID = 1L;

    private final     File            file;
    private final     ZipEntryBuilder builder;
    private transient ZipEntry        zipEntry;

    SimpleZipEntryImpl( final Reference reference, final File file )
        throws IOException
    {
        this.file     = file;
        this.builder  = new ZipEntryBuilder( reference );
        this.zipEntry = this.builder.newZipEntry( this.file );

        ZipEntryHelper.copyAttributes( file, this.zipEntry );
    }

    @Override
    public InputStream createInputStream() throws FileNotFoundException
    {
        return new BufferedInputStream(
                new FileInputStream( this.file )
                );
    }

    @Override
    public ZipEntry getZipEntry() throws IOException
    {
        if( this.zipEntry == null ) {
            this.zipEntry = this.builder.newZipEntry( this.file );
        }

        return this.zipEntry;
    }
}
