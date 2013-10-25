package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractConfig implements Config
{
    protected SortedSet<Contact> contactSet = new TreeSet<>();

    public AbstractConfig()
    {
        super();
    }

    protected Collection<Contact> internalContactCollection()
    {
        return contactSet;
    }

    public Contact addContact( Contact person )
    {
        internalContactCollection().add( person );

        return person;
    }

    @Override
    public Iterable<Contact> getContacts()
    {
        return new Iterable<Contact>() {
            @Override
            public Iterator<Contact> iterator()
            {
                return internalContactCollection().iterator();
            }
        };
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
    public Contact findContactByNumber( String number )
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
    public Contact addContact( String name )
    {
        return addContact( newContact( name ) );
    }

    protected abstract Contact newContact( String name );

    @Override
    public Contact addContact( String name, String number )
    {
        Contact c = addContact( name );

        c.addNumber( number );

        return c;
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
