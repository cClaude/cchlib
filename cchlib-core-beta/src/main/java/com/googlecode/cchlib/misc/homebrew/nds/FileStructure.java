package com.googlecode.cchlib.misc.homebrew.nds;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class FileStructure
{
    public enum T {
        STRING, HEX, UNKNOWN
    }

    protected static class F {
        private String field;
        private int    start;
        private int    end;
        private int    length;
        private T      type;

        public F( String field, int start, int end, int length )
        {
            this( field, start, end, length, T.UNKNOWN );
        }

        public F( String field, int start, int end, int length, T type )
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

        public T getType()
        {
            return type;
        }

        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
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

    private byte[] bytes;
    private Map<String,Integer> nameIndexMap;

    public FileStructure(byte[] bytes)
    {
        this.bytes = bytes;
    }

    protected abstract F   getFieldInfo( int i );
    protected abstract int getFieldCount();

    protected Integer getNameIndex( String fieldname )
    {
        if( nameIndexMap == null ) {
            nameIndexMap = new HashMap<>();

            for( int i = 0; i<getFieldCount(); i++ ) {
                nameIndexMap.put(  getFieldName( i ), i );
                }
            }

        return nameIndexMap.get( fieldname );
    }

    public final String getFieldName( int i ) {
        return getFieldInfo( i ).getField();
    }

    public final byte[] getBytes( int i ) {
        F   f      = getFieldInfo( i );
        int start  = f.getStart();
        int length = f.getLength();

       byte[] b = new byte[ length ];
       System.arraycopy( bytes, start, b, 0, length );
       return b;
    }

    public String getFieldAsString( String fieldname )
    {
        return getFieldAsString( getNameIndex( fieldname ).intValue() );
    }

    public String getFieldAsString( int i ) {
        return new String( getBytes( i ) );
    }

}
