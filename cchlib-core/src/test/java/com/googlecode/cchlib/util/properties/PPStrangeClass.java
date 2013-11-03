// $codepro.audit.disable numericLiterals
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
    public void setConvertToString( String s )
    {
        this.privateRealContent = s;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PPStrangeClass [privateRealContent=");
        builder.append(privateRealContent);
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
                + ((privateRealContent == null) ? 0 : privateRealContent
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
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
        PPStrangeClass other = (PPStrangeClass) obj;
        if (privateRealContent == null) {
            if (other.privateRealContent != null) {
                return false;
            }
        } else if (!privateRealContent.equals(other.privateRealContent)) {
            return false;
        }
        return true;
    }
}
