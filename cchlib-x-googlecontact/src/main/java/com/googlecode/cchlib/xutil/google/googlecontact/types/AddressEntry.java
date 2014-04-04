package com.googlecode.cchlib.xutil.google.googlecontact.types;

import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;
import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

public interface AddressEntry extends GoogleContactType {
    public String getType();
    @Header("Type")
    public void setType( String type );

    public String getFormatted();
    @Header("Formatted")
    public void setFormatted( String formatted );

    public String getStreet();
    @Header("Street")
    public void setStreet( String street );

    public String getCity();
    @Header("City")
    public void setCity( String city );

    public String getPoBox();
    @Header("PO Box")
    public void setPoBox( String poBox );

    public String getRegion();
    @Header("Region")
    public void setRegion( String region );

    public String getPostalCode();
    @Header("Postal Code")
    public void setPostalCode( String postalCode );

    public String getCountry();
    @Header("Country")
    public void setCountry( String country );

    public String getExtendedAddress();
    @Header("Extended Address")
    public void setExtendedAddress( String extendedAddress );
}
