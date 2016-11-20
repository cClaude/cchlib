package com.googlecode.cchlib.xutil.google.googlecontact.types;


//not public
class AddressEntryImpl implements AddressEntry {

    private static final long serialVersionUID = 1L;

    private String type;
    private String formatted;
    private String street;
    private String city;
    private String poBox;
    private String region;
    private String postalCode;
    private String country;
    private String extendedAddress;

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
    public final String getFormatted()
    {
        return formatted;
    }
    @Override
    public final void setFormatted( final String formatted )
    {
        this.formatted = formatted;
    }

    @Override
    public final String getStreet()
    {
        return street;
    }

    @Override
    public final void setStreet( final String street )
    {
        this.street = street;
    }

    @Override
    public final String getCity()
    {
        return city;
    }
    @Override
    public final void setCity( final String city )
    {
        this.city = city;
    }


    @Override
    public final String getPoBox()
    {
        return poBox;
    }
    @Override
    public final void setPoBox( final String poBox )
    {
        this.poBox = poBox;
    }

    @Override
    public final String getRegion()
    {
        return region;
    }
    @Override
    public final void setRegion( final String region )
    {
        this.region = region;
    }

    @Override
    public final String getPostalCode()
    {
        return postalCode;
    }
    @Override
    public final void setPostalCode( final String postalCode )
    {
        this.postalCode = postalCode;
    }

    @Override
    public final String getCountry()
    {
        return country;
    }
    @Override
    public final void setCountry( final String country )
    {
        this.country = country;
    }

    @Override
    public final String getExtendedAddress()
    {
        return extendedAddress;
    }
    @Override
    public final void setExtendedAddress( final String extendedAddress )
    {
        this.extendedAddress = extendedAddress;
    }
}
