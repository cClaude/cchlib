package com.googlecode.cchlib.json;

class BeanInCollection
{
    private String beanName;

    public BeanInCollection()
    {
        // Bean
    }

    public String getBeanName()
    {
        return this.beanName;
    }

    public BeanInCollection setBeanName( final String beanName )
    {
        this.beanName = beanName;

        return this;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.beanName == null) ? 0 : this.beanName.hashCode());
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final BeanInCollection other = (BeanInCollection)obj;
        if( this.beanName == null ) {
            if( other.beanName != null ) {
                return false;
            }
        } else if( !this.beanName.equals( other.beanName ) ) {
            return false;
        }
        return true;
    }
}
