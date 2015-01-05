package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import com.googlecode.cchlib.NeedDoc;

/**
 * @since 4.2
 */
@NeedDoc
public class DFFPass1Impl implements DFFPass1 {

    private final DFFConfig config;;

    public DFFPass1Impl( final DFFConfig config )
    {
        this.config = config;
    }

    @Override
    public void addFile( final File file )
    {
        final long size = file.length();

        if( this.config.isIgnoreEmptyFiles() && (size == 0) ) {
            return;
        }

        this.config.getMapLengthFiles().add( Long.valueOf( size ), file );

        if( this.config.isCancelProcess() ) {
            this.config.getMapLengthFiles().clear();
            return;
        }
    }

    @Override
    public void addFiles( final Iterable<File> files )
    {
        if( ! this.config.isCancelProcess() ) {
            for(final File file : files) {
                addFile( file );
            }
        }
    }
}
