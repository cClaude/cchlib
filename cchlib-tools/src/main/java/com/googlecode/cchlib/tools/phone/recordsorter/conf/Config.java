package com.googlecode.cchlib.tools.phone.recordsorter.conf;

/**
 *
 */
public interface Config
{
    Contact addContact( String name );
    Contact addContact( String name, String number );

    Contact findContactByName( String name );
    Contact findContactByNumber( String number );

    public Iterable<Contact> getContacts();
}
