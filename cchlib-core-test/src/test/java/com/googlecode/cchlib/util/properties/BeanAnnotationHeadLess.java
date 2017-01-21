package com.googlecode.cchlib.util.properties;


//NOT public
interface BeanAnnotationHeadLess
{
    String getaString();
    void setaString( String aString );

    int getaInt();
    void setaInt( int aInt );

    float getaFloat();
    void setaFloat( float aFloat );

    boolean[] getSomeBooleans();
    void setSomeBooleans( boolean[] someBooleans );
}
