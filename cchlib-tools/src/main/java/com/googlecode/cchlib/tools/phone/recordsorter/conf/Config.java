package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.util.Collection;

/**
 *
 */
public interface Config
{
    Contact findContactByName( String name );
    Contact findContactByNumber( String number );

    Collection<Contact> getContacts();
    
    Contact newContact() throws UnsupportedOperationException;
    Contact addContact( Contact contact ) throws UnsupportedOperationException;
}
