package com.googlecode.cchlib.xutil.google.googlecontact.extension;

import java.io.Serializable;
import java.util.List;
import com.googlecode.cchlib.xutil.google.googlecontact.GoogleContacts;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;

public class SimpifiedBasicEntryImpl implements SimpifiedBasicEntry, Serializable {

    private static final long serialVersionUID = 1L;
    private final BasicEntry basicEntry;

    public SimpifiedBasicEntryImpl( final BasicEntry basicEntry )
    {
        this.basicEntry = basicEntry;
    }

    @Override
    public BasicEntry getBasicEntry()
    {
        return basicEntry;
    }

    @Override
    public String getType()
    {
        return basicEntry.getType();
    }

    @Override
    public List<String> getValues()
    {
        return GoogleContacts.getValues( basicEntry );
    }
}
