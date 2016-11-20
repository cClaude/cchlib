package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.util.Comparator;

/**
 *
 */
public interface Contact extends Comparable<Contact>
{
    String getName();
    Contact setName( String name );

    boolean isBackup();
    Contact setBackup( boolean backup );

    boolean contains( String number );
    boolean addNumber( String number );
    Iterable<String> getNumbers();

    Comparator<Contact> getByNameComparator();
}
