package com.googlecode.cchlib.apps.duplicatefiles.alpha.filefilter;

/**
 *
 *
 */
public abstract class AbstractXFileFilter
    implements XFileFilter
{
    private final String name;
    private final String description;
    private final XFileFilterType type;
    private final XFileFilterMode mode;

    /**
    *
    */
    protected AbstractXFileFilter(
        final String             name,
        final String             description,
        final XFileFilterType    type,
        final XFileFilterMode    mode
        )
    {
        this.name = name;
        this.description = description;
        this.type = type;
        this.mode = mode;
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

    @Override
    public XFileFilterType getType()
    {
        return this.type;
    }

    @Override
    public XFileFilterMode getMode()
    {
        return this.mode;
    }
}
