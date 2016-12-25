package com.googlecode.cchlib.io.serializable;

class BasicTypes implements java.io.Serializable, Comparable<BasicTypes>
{
    private static final long serialVersionUID = 1L;

    private final String      content;
    private final boolean     bool;
    private final boolean     notBool;
    private final int         oInterger;
    private final long        oLong;
    private final int[]       arrayOfInt = new int[ 5 ];

    public BasicTypes( final boolean bool, final int anInteger, final String content )
    {
        this.bool      = bool;
        this.notBool   = !bool;
        this.oInterger = anInteger;
        this.oLong     = anInteger * 1000001L;
        this.content   = content;

        for( int i = 0; i < this.arrayOfInt.length; i++ ) {
            this.arrayOfInt[ i ] = i + anInteger;
        }
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder( "{"
                + this.bool + ";"
                + this.notBool + ";"
                + this.oInterger + ";"
                + this.oLong + ";"
                + this.content
                + "}" );

        for( int i = 0; i < this.arrayOfInt.length; i++ ) {
            sb.append( ";" + this.arrayOfInt[ i ] );
        }

        sb.append( "}" );

        return sb.toString();
    }

    @Override
    public int compareTo( final BasicTypes anOtherTboolean )
    {
        if( this.bool == anOtherTboolean.bool ) {
            if( this.notBool == anOtherTboolean.notBool ) {
                if( this.oInterger == anOtherTboolean.oInterger ) {
                    if( this.oLong == anOtherTboolean.oLong ) {
                        return this.content.compareTo( anOtherTboolean.content );
                    }
                }
            }
        }

        return -1;
    }

}
