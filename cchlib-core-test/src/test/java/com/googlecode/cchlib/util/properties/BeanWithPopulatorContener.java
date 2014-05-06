// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.instanceFieldSecurity, numericLiterals
package com.googlecode.cchlib.util.properties;

//NOT public
class BeanWithPopulatorContener
{
    private static final int PRIME = 31;

    @Populator
    PPStrangeClass myStrangeClass;

    public BeanWithPopulatorContener()
    {
        myStrangeClass = new PPStrangeClass( "DeFaUlTvAlUe");
    }

    public BeanWithPopulatorContener( final String str )
    {
        myStrangeClass = new PPStrangeClass( str );
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "PPBeanWithPopulatorContener [myStrangeClass=" );
        builder.append( myStrangeClass );
        builder.append( ']' );
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        return PRIME + ((myStrangeClass == null) ? 0 : myStrangeClass.hashCode());
    }

    @Override
    public boolean equals( final Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() )
         {
            return false; // $codepro.audit.disable useEquals
        }
        final BeanWithPopulatorContener other = (BeanWithPopulatorContener)obj;
        if( myStrangeClass == null ) {
            if( other.myStrangeClass != null ) {
                return false;
            }
        } else if( !myStrangeClass.equals( other.myStrangeClass ) ) {
            return false;
        }
        return true;
    }
}
