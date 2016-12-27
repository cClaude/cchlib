package com.googlecode.cchlib.tools.phone.recordsorter.conf.xml;

import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.util.AbstractReadWriteConfig;

public class XMLConfigImpl extends AbstractReadWriteConfig
{
    private static final long serialVersionUID = 1L;

    public Collection<Contact> getContactList()
    {
        return super.internalContactCollection();
    }

    public void setContactList( final List<Contact> contactList )
    {
        final Collection<Contact> cc = super.internalContactCollection();

        cc.clear();
        for( Contact c : contactList ) {
            cc.add( c );
        }
    }

    @Override
    public Contact newContact()
    {
        return new XMLContactImpl();
    }
}
