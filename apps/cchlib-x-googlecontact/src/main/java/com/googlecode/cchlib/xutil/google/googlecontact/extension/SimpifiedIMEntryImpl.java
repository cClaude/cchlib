package com.googlecode.cchlib.xutil.google.googlecontact.extension;

import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.IMEntry;

public class SimpifiedIMEntryImpl
    extends SimpifiedBasicEntryImpl
        implements SimpifiedIMEntry {

    private static final long serialVersionUID = 1L;

    public SimpifiedIMEntryImpl( final BasicEntry basicEntry )
    {
        super( basicEntry );
    }

    @Override
    public IMEntry getIMEntry()
    {
        return IMEntry.class.cast( super.getBasicEntry() );
    }

    @Override
    public String getService()
    {
        return getIMEntry().getService();
    }
}
