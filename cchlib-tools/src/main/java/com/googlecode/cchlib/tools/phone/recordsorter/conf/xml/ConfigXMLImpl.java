package com.googlecode.cchlib.tools.phone.recordsorter.conf.xml;

import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractConfig;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

/**
 *
 */
public class ConfigXMLImpl extends AbstractConfig implements Config
{
    /**
     *
     */
    public ConfigXMLImpl()
    {
    }

    public Collection<Contact> getContactList()
    {
        return super.internalContactCollection();
    }

    public void setContactList( List<Contact> contactList )
    {
        Collection<Contact> cc = super.internalContactCollection();
        
        cc.clear();
        for( Contact c : contactList ) {
            cc.add( c );
        }
    }

    @Override
    protected Contact newContact( String name )
    {
        return new ContactXMLImpl( name );
    }
}
