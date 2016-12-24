package com.googlecode.cchlib.tools.phone.recordsorter.conf.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class DefaultContact implements Contact, Serializable
{
    private static final long serialVersionUID = 1L;

    private final SortedSet<String> numberSet = new TreeSet<>();

    private String              name;
    private boolean             backup = true;
    private Comparator<Contact> byNameComparator;

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( getClass().getSimpleName() );
        builder.append( " [numberSet=" );
        builder.append( this.numberSet );
        builder.append( ", name=" );
        builder.append( this.name );
        builder.append( ", backup=" );
        builder.append( this.backup );
        builder.append( ']' );
        return builder.toString();
    }

    protected Set<String> internalNumberSet()
    {
        return this.numberSet;
    }

    @Override
    public String getName()
    {
        return this.name;
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
        return this.backup;
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
        return () -> internalNumberSet().iterator();
    }

    @Override
    public Comparator<Contact> getByNameComparator()
    {
        if( this.byNameComparator == null ) {
            this.byNameComparator = ( o1, o2 ) -> o1.getName().compareTo( o2.getName() );
        }
        return this.byNameComparator;
    }

    @Override
    public int compareTo( final Contact o )
    {
        return this.getName().compareTo( o.getName() );
    }
}
