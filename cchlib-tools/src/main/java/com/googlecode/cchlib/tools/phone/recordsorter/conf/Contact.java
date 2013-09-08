package com.googlecode.cchlib.tools.phone.recordsorter.conf;

/**
 *
 */
public interface Contact extends Comparable<Contact>
{
    public String getName();
    public void setName( String name );

    public boolean isBackup();
    public void setBackup( boolean backup );

    public boolean contains( String number );
    public boolean addNumber( String number );
    public Iterable<String> getNumbers();
}
