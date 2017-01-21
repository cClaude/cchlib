package com.googlecode.cchlib.util.populator.test;

import java.util.Arrays;
import com.googlecode.cchlib.util.populator.Populator;

//public access required by populator
public class BeanAnnotationOnGettersHeadLess implements BeanAnnotationHeadLess
{
    protected String    aString;
    private   int       aInt;
    private   float     aFloat;
    public    boolean[] someBooleans;

    public BeanAnnotationOnGettersHeadLess()
    {
        // Empty
    }

    public BeanAnnotationOnGettersHeadLess(
        final String    aString,
        final int       aInt,
        final float     aFloat,
        final boolean[] booleans
        )
    {
        this.aString        = aString;
        this.aInt           = aInt;
        this.aFloat         = aFloat;
        this.someBooleans   = booleans;
    }

    @Override
    @Populator
    public final String getaString()
    {
        return this.aString;
    }

    @Override
    public final void setaString( final String aString )
    {
        this.aString = aString;
    }

    @Override
    @Populator
    public final int getaInt()
    {
        return this.aInt;
    }

    @Override
    public final void setaInt( final int aInt )
    {
        this.aInt = aInt;
    }

    @Override
    @Populator
    public final float getaFloat()
    {
        return this.aFloat;
    }

    @Override
    public final void setaFloat( final float aFloat )
    {
        this.aFloat = aFloat;
    }

    @Override
    @Populator
    public final boolean[] getSomeBooleans()
    {
        return this.someBooleans;
    }

    @Override
    public final void setSomeBooleans( final boolean[] someBooleans )
    {
        this.someBooleans = someBooleans;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append( "BeanAnnotationOnGettersHeadLess [aString=" );
        builder.append( this.aString );
        builder.append( ", aInt=" );
        builder.append( this.aInt );
        builder.append( ", aFloat=" );
        builder.append( this.aFloat );
        builder.append( ", someBooleans=" );
        builder.append( Arrays.toString( this.someBooleans ) );
        builder.append( ']' );

        return builder.toString();
    }
}
