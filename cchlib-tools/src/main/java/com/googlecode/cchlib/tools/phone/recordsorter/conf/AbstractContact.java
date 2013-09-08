package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class AbstractContact implements Contact 
{
    private SortedSet<String> numberSet = new TreeSet<>();
    private String            name;
    private boolean           backup = true;

    public AbstractContact( String name )
    {
        setName( name );
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
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
    public void setName( String name )
    {
        this.name = name;
    }

    @Override
    public boolean isBackup()
    {
        return backup;
    }

    @Override
    public void setBackup( boolean backup )
    {
        this.backup = backup;
    }

    @Override
    public boolean addNumber( String number )
    {
        return internalNumberSet().add( number );
    }

    @Override
    public boolean contains( String number )
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
    public int compareTo( Contact o )
    {
        return getName().compareTo( o.getName() );
    }
}