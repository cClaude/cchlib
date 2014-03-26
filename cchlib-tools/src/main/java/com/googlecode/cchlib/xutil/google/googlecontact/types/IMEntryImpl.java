package com.googlecode.cchlib.xutil.google.googlecontact.types;

import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

//not public
class IMEntryImpl extends BasicEntryImpl implements IMEntry {

    private static final long serialVersionUID = 1L;


    private String service;

    @Override
    public String getService()
    {
        return service;
    }

    @Override
    @Header("Service")
    public void setService( final String service )
    {
        this.service = service;
    }

}
