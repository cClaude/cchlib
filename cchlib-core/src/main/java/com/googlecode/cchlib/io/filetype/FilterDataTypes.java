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
    private final static Logger logger = Logger.getLogger( FilterDataTypes.class );
    private int offset = 0;
    private boolean ready = false;
    private FileDataTypeDescription currentType = null;
    private List<FileDataTypeMatch> fileDataTypeMatchList;

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

        this.fileDataTypeMatchList = new ArrayList<FileDataTypeMatch>( fileDataTypeMatchList );
    }

    public boolean isReady()
    {
        return ready;
    }

    public FileDataTypeDescription getFileDataTypeDescription()
    {
        return currentType;
    }

    @Override
    public int read() throws IOException
    {
        int b = in.read();
        if( b != -1 ) {
            update( b );
            }
        return b;
    }

    @Override
    public int read(byte[] b) throws IOException
    {
        int len = in.read(b, 0, b.length);
        if( len != -1 ) {
            update(b, 0, len);
            }
        return len;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        len = in.read(b, off, len);
        if( len != -1 ) {
            update(b, off, len);
            }
        return len;
    }

    private void update( int b ) throws IOException
    {
        if( ! ready ) {
            // Check offset with value b
            
            if( b<0 || b>255 ) {
                throw new IOException( "unsupported value: " + b );
                }
            
            if( logger.isTraceEnabled() ) {
                logger.info( "read 0x" + Integer.toHexString( b ) + " : " + this );
                }
            
            Iterator<FileDataTypeMatch> iter = fileDataTypeMatchList.iterator();

            while( iter.hasNext() ) {
                FileDataTypeMatch e = iter.next();

                if( e.isValid( offset, b ) ) {
                    // Check if it is the last byte
                    if( e.isLastOffset( offset ) ) {
                        ready = true;
                        currentType = e.getFileDataTypeDescription();
                        }
                    // This type remind valid for this stream
                    }
                else {
                    // This type is no more valid for this stream
                    iter.remove();
                    }
                }

            if( ! ready ) {
                if( fileDataTypeMatchList.size() == 0 ) {
                    // Unknown that type !
                    ready = true;
                    }
                }

            offset++;
            }
    }

    private void update( byte[] b, int off, int len ) throws IOException
    {
        if( ! ready ) {
            for( int i = off; i < len + off; i++ ) {
                update( 0x000000FF & (int)(b[ i ]) );
                
                if( ready ) {
                    break;
                    }
                }
            }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "FilterDataTypes [offset=" );
        builder.append( offset );
        builder.append( ", ready=" );
        builder.append( ready );
        builder.append( ", currentType=" );
        builder.append( currentType );
        builder.append( ", fileDataTypeMatchList=" );
        for( FileDataTypeMatch e : fileDataTypeMatchList ) {
            builder.append( e );
            builder.append( "," );
            }
        builder.append( "]" );
        return builder.toString();
    }
}
