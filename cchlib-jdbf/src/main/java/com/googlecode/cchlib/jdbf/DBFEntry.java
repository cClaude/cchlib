package com.googlecode.cchlib.jdbf;

import java.util.Date;

/**
 * DBFEntry support informations for every cells in the DB
 */
public interface DBFEntry
{
    /**
     * Returns {@link DBFField} for this entry
     * @return {@link DBFField} for this entry
     */
    public DBFField getDBFField();

    /**
     * Returns row value for this cell.
     * @return row value for this cell.
     */
    public Object getValue();

    /**
     * Returns value as a {@link String} for this cell
     * @return value as a {@link String} for this cell
     * @throws DBFEntryException if value can't be convert to {@link String}
     */
    public String getString() throws DBFEntryException;

    /**
     * Returns value as a integer for this cell
     * @return value as a integer for this cell
     * @throws DBFEntryException if value can't be convert to integer
     */
    public int getInt() throws DBFEntryException;

    /**
     * Returns value as a boolean for this cell
     * @return value as a boolean for this cell
     * @throws DBFEntryException if value can't be convert to boolean
     */
    public boolean getBoolean() throws DBFEntryException;

    /**
     * Returns value as a double for this cell
     * @return value as a double for this cell
     * @throws DBFEntryException if value can't be convert to double
     */
    public double getDouble() throws DBFEntryException;

    /**
     * Returns value as a {@link Date} for this cell
     * @return value as a {@link Date} for this cell
     * @throws DBFEntryException if value can't be convert to {@link Date}
     */
    public Date getDate() throws DBFEntryException;
}
