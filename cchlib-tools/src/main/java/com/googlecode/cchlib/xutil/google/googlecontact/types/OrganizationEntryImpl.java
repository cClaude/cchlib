package com.googlecode.cchlib.xutil.google.googlecontact.types;


//not public
class OrganizationEntryImpl implements OrganizationEntry {

    private static final long serialVersionUID = 1L;

    private String type;
    private String name;
    private String yomiName;
    private String title;
    private String department;
    private String symbol;
    private String location;
    private String jobDescription;


    @Override
    public final String getType()
    {
        return type;
    }
    @Override
    public final void setType( final String type )
    {
        this.type = type;
    }

    @Override
    public final String getName()
    {
        return name;
    }
    @Override
    public final void setName( final String name )
    {
        this.name = name;
    }

    @Override
    public final String getYomiName()
    {
        return yomiName;
    }
    @Override
    public final void setYomiName( final String yomiName )
    {
        this.yomiName = yomiName;
    }

    @Override
    public final String getTitle()
    {
        return title;
    }
    @Override
    public final void setTitle( final String title )
    {
        this.title = title;
    }

    @Override
    public final String getDepartment()
    {
        return department;
    }
    @Override
    public final void setDepartment( final String department )
    {
        this.department = department;
    }

    @Override
    public final String getSymbol()
    {
        return symbol;
    }
    @Override
    public final void setSymbol( final String symbol )
    {
        this.symbol = symbol;
    }

    @Override
    public final String getLocation()
    {
        return location;
    }
    @Override
    public final void setLocation( final String location )
    {
        this.location = location;
    }

    @Override
    public final String getJobDescription()
    {
        return jobDescription;
    }
    @Override
    public final void setJobDescription( final String jobDescription )
    {
        this.jobDescription = jobDescription;
    }
}
