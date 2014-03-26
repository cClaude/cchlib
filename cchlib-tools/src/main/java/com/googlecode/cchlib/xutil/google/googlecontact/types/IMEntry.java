package com.googlecode.cchlib.xutil.google.googlecontact.types;

import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

public interface IMEntry extends BasicEntry {
    String getService();
    @Header("Service")
    void setService( String service );
}