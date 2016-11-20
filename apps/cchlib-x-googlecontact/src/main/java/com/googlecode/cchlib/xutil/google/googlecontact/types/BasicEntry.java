package com.googlecode.cchlib.xutil.google.googlecontact.types;

import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;
import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

public interface BasicEntry extends GoogleContactType {
    String getType();
    @Header("Type")
    void setType( String type );

    String getValue();
    @Header("Value")
    void setValue( String value );
}
