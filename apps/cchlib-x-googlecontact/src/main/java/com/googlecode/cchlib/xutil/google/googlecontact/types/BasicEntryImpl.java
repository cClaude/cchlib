package com.googlecode.cchlib.xutil.google.googlecontact.types;


//not public
class BasicEntryImpl implements BasicEntry {

    private static final long serialVersionUID = 1L;

    private String type;
    private String value;

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
    public final String getValue()
    {
        return value;
    }

    @Override
    public final void setValue( final String value )
    {
        this.value = value;
    }
}
