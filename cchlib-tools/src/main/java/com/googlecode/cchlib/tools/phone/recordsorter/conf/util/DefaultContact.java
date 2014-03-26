package com.googlecode.cchlib.tools.phone.recordsorter.conf.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class DefaultContact implements Contact, Serializable
{
    private static final long serialVersionUID = 1L;
    private SortedSet<String> numberSet = new TreeSet<>();
    private String            name;
    private boolean           backup = true;
    private Comparator<Contact> byNameComparator;

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( " [numberSet=" );
        builder.append( numberSet );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", backup=" );
        builder.append( backup );
        builder.append( ']' );
        return builder.toString();
    }

    protected Set<String> internalNumberSet()
    {
        return numberSet;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Contact setName( final String name )
    {
        this.name = name;
        return this;
    }

    @Override
    public boolean isBackup()
    {
        return backup;
    }

    @Override
    public Contact setBackup( final  boolean backup )
    {
        this.backup = backup;
        return this;
   }

    @Override
    public boolean addNumber( final String number )
    {
        return internalNumberSet().add( number );
    }

    @Override
    public boolean contains( final String number )
    {
        return internalNumberSet().contains( number );
    }

    @Override
    public Iterable<String> getNumbers()
    {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator()
            {
                return internalNumberSet().iterator();
            }
        };
    }

    @Override
    public Comparator<Contact> getByNameComparator()
    {
        if( byNameComparator == null ) {
            byNameComparator = new Comparator<Contact>() {
                @Override
                public int compare( final Contact o1, final Contact o2 )
                {
                    return o1.getName().compareTo( o2.getName() );
                }
            };
        }
        return byNameComparator;
    }

    @Override
    public int compareTo( final Contact o )
    {
        return this.getName().compareTo( o.getName() );
    }
}
