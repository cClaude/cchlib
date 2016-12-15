package com.googlecode.cchlib.util.properties;

class PPStrangeClass implements PopulatorContener
{
    private String privateRealContent;

    public PPStrangeClass( final String something )
    {
        this.privateRealContent = something;
    }

    @Override
    public String getConvertToString()
    {
        return this.privateRealContent;
    }

    @Override
    public void setConvertToString( final String s )
    {
        this.privateRealContent = s;
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("PPStrangeClass [privateRealContent=");
        builder.append(this.privateRealContent);
        builder.append(']');
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime
                * result)
                + ((this.privateRealContent == null) ? 0 : this.privateRealContent
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) {
            return true;
            }
        if (obj == null) {
            return false;
            }
        if (!(obj instanceof PPStrangeClass)) {
            return false;
            }
        final PPStrangeClass other = (PPStrangeClass) obj;
        if (this.privateRealContent == null) {
            if (other.privateRealContent != null) {
                return false;
            }
        } else if (!this.privateRealContent.equals(other.privateRealContent)) {
            return false;
        }
        return true;
    }
}
