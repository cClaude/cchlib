package com.googlecode.cchlib.io.filetype;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @since 4.1.7
 */
class FilterDataTypes extends FilterInputStream
{
    private static final Logger LOGGER = Logger.getLogger( FilterDataTypes.class );

    private int offset = 0;
    private boolean ready = false;
    private FileDataTypeDescription currentType = null;
    private final List<FileDataTypeMatch> fileDataTypeMatchList;

    /**
     * Create a FilterDataTypes based on giving {@link InputStream}
     * @param in {@link InputStream} to analyze
     * @param fileDataTypeMatchList {@link List} of {@link FileDataTypeMatch}
     */
    public FilterDataTypes(
        final InputStream in,
        final List<FileDataTypeMatch> fileDataTypeMatchList
        )
    {
        super( in );

        this.fileDataTypeMatchList = new ArrayList<>( fileDataTypeMatchList );
    }

    public boolean isReady()
    {
        return this.ready;
    }

    public FileDataTypeDescription getFileDataTypeDescription()
    {
        return this.currentType;
    }

    @Override
    public int read() throws IOException
    {
        final int b = this.in.read();
        if( b != -1 ) {
            update( b );
            }
        return b;
    }

    @Override
    public int read(final byte[] b) throws IOException
    {
        final int len = this.in.read(b, 0, b.length);
        if( len != -1 ) {
            update(b, 0, len);
            }
        return len;
    }

    @Override
    public int read(final byte[] b, final int off, int len) throws IOException
    {
        len = this.in.read(b, off, len);
        if( len != -1 ) {
            update(b, off, len);
            }
        return len;
    }

    private void update( final int b ) throws IOException
    {
        if( ! this.ready ) {
            // Check offset with value b

            if( (b<0) || (b>255) ) {
                throw new IOException( "unsupported value: " + b );
                }

            if( LOGGER.isTraceEnabled() ) {
                LOGGER.info( "read 0x" + Integer.toHexString( b ) + " : " + this );
                }

            final Iterator<FileDataTypeMatch> iter = this.fileDataTypeMatchList.iterator();

            while( iter.hasNext() ) {
                final FileDataTypeMatch e = iter.next();

                if( e.isValid( this.offset, b ) ) {
                    // Check if it is the last byte
                    if( e.isLastOffset( this.offset ) ) {
                        this.ready = true;
                        this.currentType = e.getFileDataTypeDescription();
                        }
                    // This type remind valid for this stream
                    }
                else {
                    // This type is no more valid for this stream
                    iter.remove();
                    }
                }

            if( ! this.ready ) {
                if( this.fileDataTypeMatchList.isEmpty() ) {
                    // Unknown that type !
                    this.ready = true;
                    }
                }

            this.offset++;
            }
    }

    private void update( final byte[] b, final int off, final int len ) throws IOException
    {
        if( ! this.ready ) {
            for( int i = off; i < (len + off); i++ ) {
                update( 0x000000FF & (b[ i ]) );

                if( this.ready ) {
                    break;
                    }
                }
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "FilterDataTypes [offset=" );
        builder.append( this.offset );
        builder.append( ", ready=" );
        builder.append( this.ready );
        builder.append( ", currentType=" );
        builder.append( this.currentType );
        builder.append( ", fileDataTypeMatchList=" );
        for( final FileDataTypeMatch e : this.fileDataTypeMatchList ) {
            builder.append( e );
            builder.append( ',' );
            }
        builder.append( ']' );
        return builder.toString();
    }
}
