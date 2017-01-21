package com.googlecode.cchlib.util.populator.test;

import com.googlecode.cchlib.util.populator.PopulatorContener;


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

    public String getPrivateRealContent()
    {
        return this.privateRealContent;
    }
}
