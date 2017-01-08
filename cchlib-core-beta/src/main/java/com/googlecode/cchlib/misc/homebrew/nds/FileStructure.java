package com.googlecode.cchlib.misc.homebrew.nds;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class FileStructure
{
    @FunctionalInterface
    private interface Action<O,F,N,I> {
        void accept(O o, F f, N n, I i);
    }

    public enum FSType {
        STRING( NDSFileFormat::printString ),
        HEX( NDSFileFormat::printField ),
        UNKNOWN( NDSFileFormat::printUField ),
        ;

        private Action<PrintStream,FSField,NDSFileFormat,Integer> action;

        FSType( final Action<PrintStream,FSField,NDSFileFormat,Integer> action )
        {
            this.action = action;
        }

        public void printField(
                final PrintStream out,
                final FSField f,
                final NDSFileFormat nds,
                final int i
                )
        {
            this.action.accept( out, f, nds, i );
        }
    }

    protected static class FSField {
        private final String field;
        private final int    start;
        private final int    end;
        private final int    length;
        private final FSType type;

        public FSField( final String field, final int start, final int end, final int length )
        {
            this( field, start, end, length, FSType.UNKNOWN );
        }

        public FSField(
            final String field,
            final int    start,
            final int    end,
            final int    length,
            final FSType type )
        {
            this.field  = field;
            this.start  = start;
            this.end    = end;
            this.length = length;
            this.type   = type;

            assert (start - end) == length;
        }

        public String getField()
        {
            return this.field;
        }

        public int getStart()
        {
            return this.start;
        }

        public int getEnd()
        {
            return this.end;
        }

        public int getLength()
        {
            return this.length;
        }

        public FSType getType()
        {
            return this.type;
        }

        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();
            builder.append( getClass().getSimpleName() );
            builder.append( " [field=" );
            builder.append( this.field );
            builder.append( ", start=" );
            builder.append( this.start );
            builder.append( ", end=" );
            builder.append( this.end );
            builder.append( ", length=" );
            builder.append( this.length );
            builder.append( ", type=" );
            builder.append( this.type );
            builder.append( ']' );
            return builder.toString();
        }
    }

    private final byte[] bytes;
    private Map<String,Integer> nameIndexMap;

    public FileStructure( final byte[] bytes )
    {
        this.bytes = bytes;
    }

    protected abstract FSField   getFieldInfo( int i );
    protected abstract int getFieldCount();

    protected Integer getNameIndex( final String fieldname )
    {
        if( this.nameIndexMap == null ) {
            this.nameIndexMap = new HashMap<>();

            for( int i = 0; i<getFieldCount(); i++ ) {
                this.nameIndexMap.put( getFieldName( i ), Integer.valueOf( i ) );
                }
            }

        return this.nameIndexMap.get( fieldname );
    }

    public final String getFieldName( final int i ) {
        return getFieldInfo( i ).getField();
    }

    public final byte[] getBytes( final int i ) {
        final FSField   f      = getFieldInfo( i );
        final int start  = f.getStart();
        final int length = f.getLength();

       final byte[] b = new byte[ length ];
       System.arraycopy( this.bytes, start, b, 0, length );
       return b;
    }

    public String getFieldAsString( final String fieldname )
    {
        return getFieldAsString( getNameIndex( fieldname ).intValue() );
    }

    public String getFieldAsString( final int i ) {
        return new String( getBytes( i ) );
    }

}
