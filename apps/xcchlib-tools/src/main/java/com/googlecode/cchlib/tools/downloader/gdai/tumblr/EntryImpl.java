package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

//not public
class EntryImpl implements Entry
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public EntryImpl()
    {
        // Required by JSON
    }

    public EntryImpl( final String blogName, final String blogDescription )
    {
        this.name        = blogName;
        this.description = blogDescription;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }
}
