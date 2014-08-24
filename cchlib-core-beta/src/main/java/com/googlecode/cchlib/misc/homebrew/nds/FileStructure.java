package com.googlecode.cchlib.misc.homebrew.nds;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class FileStructure
{
    public enum FSType {
        STRING, HEX, UNKNOWN
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
            return field;
        }

        public int getStart()
        {
            return start;
        }

        public int getEnd()
        {
            return end;
        }

        public int getLength()
        {
            return length;
        }

        public FSType getType()
        {
            return type;
        }

        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();
            builder.append( getClass().getSimpleName() );
            builder.append( " [field=" );
            builder.append( field );
            builder.append( ", start=" );
            builder.append( start );
            builder.append( ", end=" );
            builder.append( end );
            builder.append( ", length=" );
            builder.append( length );
            builder.append( ", type=" );
            builder.append( type );
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
        if( nameIndexMap == null ) {
            nameIndexMap = new HashMap<>();

            for( int i = 0; i<getFieldCount(); i++ ) {
                nameIndexMap.put( getFieldName( i ), Integer.valueOf( i ) );
                }
            }

        return nameIndexMap.get( fieldname );
    }

    public final String getFieldName( final int i ) {
        return getFieldInfo( i ).getField();
    }

    public final byte[] getBytes( final int i ) {
        final FSField   f      = getFieldInfo( i );
        final int start  = f.getStart();
        final int length = f.getLength();

       final byte[] b = new byte[ length ];
       System.arraycopy( bytes, start, b, 0, length );
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
