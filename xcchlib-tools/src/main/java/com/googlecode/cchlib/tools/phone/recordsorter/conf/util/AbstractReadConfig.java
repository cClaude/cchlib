package com.googlecode.cchlib.tools.phone.recordsorter.conf.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public abstract class AbstractReadConfig implements Config, Serializable
{
    private static final long serialVersionUID = 1L;
    protected SortedSet<Contact> contactSet = new TreeSet<>();

    public AbstractReadConfig()
    {
    }

    protected Collection<Contact> internalContactCollection()
    {
        return contactSet;
    }

    @Override
    public Collection<Contact> getContacts()
    {
        return Collections.unmodifiableCollection( internalContactCollection() );
    }

    @Override
    public Contact findContactByName( final String name )
    {
        assert name != null;

        for( Contact p : getContacts() ) {
            if( p.getName().equals( name ) ) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Contact findContactByNumber( final String number )
    {
        assert number != null;

        for( Contact p : getContacts() ) {
            if( p.contains( number ) ) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( " [getContacts()=" );
        builder.append( getContacts() );
        builder.append( ']' );
        return builder.toString();
    }

}
