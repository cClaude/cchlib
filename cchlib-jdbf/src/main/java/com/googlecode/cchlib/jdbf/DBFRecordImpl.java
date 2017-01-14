package com.googlecode.cchlib.jdbf;

import java.util.Date;
import java.util.Iterator;
import com.googlecode.cchlib.util.iterator.ArrayIterator;

/**
 * Default implementation of {@link DBFRecord}
 */
@SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
final class DBFRecordImpl implements DBFRecord
{
    private final DBFReader dbfReader;
    private final DBFEntry[] entries;

    DBFRecordImpl(
        final DBFReader     dbfReader,
        final DBFEntry[]    entries
        )
    {
        this.dbfReader = dbfReader;
        this.entries   = entries;
    }

    @Override
    public Iterator<DBFEntry> iterator()
    {
        return new ArrayIterator<>( this.entries );
    }

    @Override
    public DBFEntry getDBFEntry( final int columnNumber )
    {
        return this.entries[ columnNumber ];
    }

    @Override
    public DBFEntry getDBFEntry( final String columnName )
        throws DBFUnknownFieldNameException
    {
        return this.entries[ this.dbfReader.getColumnNumber( columnName ) ];
    }

    @Override
    public int getInt( final int columnNumber )
        throws DBFEntryException
    {
        return getDBFEntry( columnNumber ).getInt();
    }

    @Override
    public int getInt( final String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException
    {
        return getInt( this.dbfReader.getColumnNumber( columnName ) );
    }

    @Override
    public String getString( final int columnNumber )
        throws DBFEntryException
    {
        return getDBFEntry( columnNumber ).getString();
    }

    @Override
    public String getString( final String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException
    {
        return getString( this.dbfReader.getColumnNumber( columnName ) );
    }

    @Override
    public boolean getBoolean( final int columnNumber ) throws DBFEntryException
    {
        return getDBFEntry( columnNumber ).getBoolean();
    }

    @Override
    public boolean getBoolean( final String columnName )
            throws DBFUnknownFieldNameException, DBFEntryException
    {
        return getBoolean( this.dbfReader.getColumnNumber( columnName ) );
     }

    @Override
    public double getDouble( final int columnNumber )
        throws DBFEntryException
    {
        return getDBFEntry( columnNumber ).getDouble();
    }

    @Override
    public double getDouble( final String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException
    {
        return getDouble( this.dbfReader.getColumnNumber( columnName ) );
    }

    @Override
    public Date getDate( final int columnNumber ) throws DBFEntryException
    {
        return getDBFEntry( columnNumber ).getDate();
    }

    @Override
    public Date getDate( final String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException
    {
        return getDate( this.dbfReader.getColumnNumber( columnName ) );
    }
}
