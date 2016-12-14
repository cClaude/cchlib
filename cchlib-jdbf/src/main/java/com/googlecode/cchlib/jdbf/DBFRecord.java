package com.googlecode.cchlib.jdbf;

import java.util.Date;

/**
 * Row in a dbf file
 */
public interface DBFRecord extends Iterable<DBFEntry>
{
    /**
     * Return {@link DBFEntry}
     *
     * @param columnNumber
     *            Column/field number
     * @return {@link DBFEntry} for this column/field number
     * @throws DBFEntryException
     *             if any
     */
    DBFEntry getDBFEntry( int columnNumber )
        throws DBFEntryException;

    /**
     * Return {@link DBFEntry}
     *
     * @param columnName
     *            Column/field name
     * @return {@link DBFEntry} for this column/field name
     * @throws DBFUnknownFieldNameException
     *             if field name if unknown
     * @throws DBFEntryException
     *             if any
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    DBFEntry getDBFEntry( String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException;

    /**
     * Returns value as an integer for this column/field
     *
     * @param columnNumber
     *            Column/field number
     * @return value for this column/field
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getInt()
     */
    int getInt( int columnNumber ) throws DBFEntryException;

    /**
     * Returns value as an integer for this column/field
     *
     * @param columnName
     *            Column/field name
     * @return value for this column/field
     * @throws DBFUnknownFieldNameException
     *             if field name if unknown
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getInt()
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    int getInt( String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException;


    /**
     * Returns value as a {@link String} for this column/field
     *
     * @param columnNumber
     *            Column/field number
     * @return value for this column/field
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getString()
     */
    String getString( int columnNumber ) throws DBFEntryException;

    /**
     * Returns value as a {@link String} for this column/field
     *
     * @param columnName
     *            Column/field name
     * @return value for this column/field
     * @throws DBFUnknownFieldNameException
     *             if field name if unknown
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getString()
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    String getString( String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException;

    /**
     * Returns value as a boolean for this column/field
     *
     * @param columnNumber
     *            Column/field number
     * @return value for this column/field
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getInt()
     */
    boolean getBoolean( int columnNumber ) throws DBFEntryException;

    /**
     * Returns value as a boolean for this column/field
     *
     * @param columnName
     *            Column/field name
     * @return value for this column/field
     * @throws DBFUnknownFieldNameException
     *             if field name if unknown
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getInt()
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    boolean getBoolean( String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException;

    /**
     * Returns value as a double for this column/field
     *
     * @param columnNumber
     *            Column/field number
     * @return value for this column/field
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getInt()
     */
    double getDouble( int columnNumber ) throws DBFEntryException;

    /**
     * Returns value as a double for this column/field
     *
     * @param columnName
     *            Column/field name
     * @return value for this column/field
     * @throws DBFUnknownFieldNameException
     *             if field name if unknown
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getInt()
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    double getDouble( String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException;

    /**
     * Returns value as a {@link Date} for this column/field
     *
     * @param columnNumber
     *            Column/field number
     * @return value for this column/field
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getDate()
     */
    Date getDate( int columnNumber ) throws DBFEntryException;

    /**
     * Returns value as a {@link Date} for this column/field
     *
     * @param columnName
     *            Column/field name
     * @return value for this column/field
     * @throws DBFUnknownFieldNameException
     *             if field name if unknown
     * @throws DBFEntryException
     *             if any
     * @see DBFEntry#getDate()
     */
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck","squid:S1160"})
    Date getDate( String columnName )
        throws DBFUnknownFieldNameException, DBFEntryException;

}
