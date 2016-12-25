package com.googlecode.cchlib.io.serializable;

abstract class AbstractComputeString
{
    abstract public String getContent();

    abstract public String getSubContent( int maxlength );

    public String getFullContent( final int maxlength )
    {
        final StringBuilder sb  = new StringBuilder( getContent() );
        final int           len = sb.length();

        if( len < maxlength ) {
            sb.append( getSubContent( maxlength - len ) );
        }

        return sb.toString();
    }
}
