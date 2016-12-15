package com.googlecode.cchlib.util.properties;


//NOT public
class BeanAnnotationOnGettersHeadLess implements BeanAnnotationHeadLess
{
    protected String    aString;
    private   int       aInt;
    private   float     aFloat;
    private boolean[]   someBooleans;

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
    public String toString()
    {
        return BeanAnnotationHeadLess.toString( this );
    }

    @Override
    @Populator
    public final String getaString()
    {
        return this.aString;
    }

    @Override
    public
    final void setaString(final String aString)
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
    public
    final void setaInt(final int aInt)
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
    public final void setaFloat(final float aFloat)
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
}
