package com.googlecode.cchlib.util.properties;

//NOT public
class BeanWithPopulatorContener
{
    @Populator
    PPStrangeClass myStrangeClass;

    public BeanWithPopulatorContener()
    {
        this.myStrangeClass = new PPStrangeClass( "DeFaUlTvAlUe");
    }

    public BeanWithPopulatorContener( final String str )
    {
        this.myStrangeClass = new PPStrangeClass( str );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "PPBeanWithPopulatorContener [myStrangeClass=" );
        builder.append( this.myStrangeClass );
        builder.append( ']' );
        return builder.toString();
    }
}
