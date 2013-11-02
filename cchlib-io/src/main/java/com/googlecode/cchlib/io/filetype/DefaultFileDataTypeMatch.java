package com.googlecode.cchlib.io.filetype;

/**
 *
 * @since 4.1.7
 */
class DefaultFileDataTypeMatch implements FileDataTypeMatch
{
    private FileDataTypeDescription typeDescription;
    private int[] sample;
    
    /**
     * 
     * @param typeDescription
     * @param sample ( -1 is a ignored value)
     */
    public DefaultFileDataTypeMatch(
        final FileDataTypeDescription   typeDescription,
        final int[]                     sample
        )
    {
        this.typeDescription = typeDescription;
        this.sample = sample;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.io.filetype.FileDataTypeMatch#getFileDataTypeDescription()
     */
    @Override
    public FileDataTypeDescription getFileDataTypeDescription()
    {
        return this.typeDescription;
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.io.filetype.FileDataTypeMatch#isValid(int, int)
     */
    @Override
    public boolean isValid( int offset, int b ) throws StreamOverrunException
    {
        if( offset < sample.length ) {
            int v = sample[ offset ];
            
            if( v == -1 ) {
                // ignore this value
                return true;
                }
            
            return v == b;
            }
        else {
            throw new StreamOverrunException();
            }
    }

    /* (non-Javadoc)
     * @see com.googlecode.cchlib.io.filetype.FileDataTypeMatch#isLastOffset(int)
     */
    @Override
    public boolean isLastOffset( int offset )
    {
        return offset == (sample.length - 1);
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append( "DefaultFileDataTypeMatch [typeDescription=" );
        builder.append( typeDescription );
        builder.append( ", sample[" );
        builder.append( sample.length );
        builder.append( "]=" );
        
        for( int v : sample ) {
            if( v == -1 ) {
                builder.append( "-1," );
                }
            else {
                builder.append( Integer.toHexString( v ) );
                builder.append( ',' );
                }
            }
        builder.append( ']' );
        
        return builder.toString();
    }

}
