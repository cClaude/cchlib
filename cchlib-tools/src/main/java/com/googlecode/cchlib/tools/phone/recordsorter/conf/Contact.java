package com.googlecode.cchlib.tools.phone.recordsorter.conf;

/**
 *
 */
public interface Contact extends Comparable<Contact>
{
    String getName();
    void setName( String name );

    boolean isBackup();
    void setBackup( boolean backup );

    boolean contains( String number );
    boolean addNumber( String number );
    Iterable<String> getNumbers();
}
