package com.googlecode.cchlib.util.properties;

class PPBeanWithPopulatorContener
{
    @Populator
    PPStrangeClass myStrangeClass;

    public PPBeanWithPopulatorContener()
    {
        myStrangeClass = new PPStrangeClass( "DeFaUlTvAlUe");
    }

    public PPBeanWithPopulatorContener( final String str )
    {
        myStrangeClass = new PPStrangeClass( str );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "PPBeanWithPopulatorContener [myStrangeClass=" );
        builder.append( myStrangeClass );
        builder.append( ']' );
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((myStrangeClass == null) ? 0 : myStrangeClass.hashCode());
        return result;
    }

    @Override
    public boolean equals( Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
    {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( getClass() != obj.getClass() ) return false;
        PPBeanWithPopulatorContener other = (PPBeanWithPopulatorContener)obj;
        if( myStrangeClass == null ) {
            if( other.myStrangeClass != null ) return false;
        } else if( !myStrangeClass.equals( other.myStrangeClass ) )
            return false;
        return true;
    }
}