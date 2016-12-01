package com.googlecode.cchlib.sql;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.util.mappable.DefaultMappableBuilderFactory;
import com.googlecode.cchlib.util.mappable.Mappable;
import com.googlecode.cchlib.util.mappable.MappableBuilder;
import com.googlecode.cchlib.util.mappable.MappableItem;

/**
 * Collect informations on {@link DatabaseMetaData} and put then into a
 * {@link Map} of strings.
 */
public class DatabaseMetaDataCollector implements Mappable, Serializable
{
    private static final long serialVersionUID = 2L;
    static final transient Logger LOGGER = Logger.getLogger( DatabaseMetaDataCollector.class );

    /** @serial */
    private final DatabaseMetaData databaseMetaData;

    /**
     * Build internal Map of methods names and values
     *
     * @param databaseMetaData DatabaseMetaData to use to build object
     */
    public DatabaseMetaDataCollector(
            final DatabaseMetaData databaseMetaData
            )
    {
        this.databaseMetaData = databaseMetaData;
    }

    /**
     * Build internal Map of methods names and values
     *
     * @param connection Connection to use to build object
     * @throws SQLException if a database access error occurs
     */
    public DatabaseMetaDataCollector(
            final Connection connection
            )
        throws SQLException
    {
        this( connection.getMetaData() );
    }

    /**
     * Build internal Map of methods names and values
     *
     * @param dataSource DataSource to use to build object
     * @throws SQLException if a database access error occurs
     */
    public DatabaseMetaDataCollector(
            final DataSource dataSource
            )
        throws SQLException
    {
        this( dataSource.getConnection() );
    }

    /**
     * Build internal Map of methods names and values
     *
     * @param contextName context name to use to build object
     * @throws SQLException if a database access error occurs
     * @throws NamingException if a naming exception is encountered
     */
    public DatabaseMetaDataCollector(
            final String contextName
            )
        throws SQLException, NamingException
    {
        this( (DataSource)new InitialContext().lookup( contextName ) );
    }

    /**
     * Returns {@link Map} of methods/results
     * @return {@link Map} of methods/results
     */
    @Override
    public Map<String,String> toMap()
    {
        final Map<String,String> map = new LinkedHashMap<>();

        map.putAll( toMapAllMethods() );
        map.putAll( toMapDefault() );

        return Collections.unmodifiableMap( map );
    }

    private Map<String,String> toMapDefault()
    {
        final MapBuilder builder = new MapBuilder( this.databaseMetaData );

        return builder.toMap();
    }

    private Map<String, String> toMapAllMethods()
    {
        final MappableBuilder mb = new MappableBuilder(
                new DefaultMappableBuilderFactory()
                    .setMethodesNamePattern( ".*" )
                    .add( Object.class )
                    .add( MappableItem.MAPPABLE_ITEM_SHOW_ALL )
                );

        return mb.toMap( this.databaseMetaData );
    }

    /**
     * Returns list of table name for current schema
     *
     * @param tableTypes
     * @return list of table name for current schema
     * @throws SQLException
     *             if a database access error occurs
     */
    public List<String> getTableList(final String...tableTypes)
        throws SQLException
    {
        final List<String> values = new ArrayList<>();

        try( final ResultSet allTables = this.databaseMetaData.getTables(null,null,null,tableTypes) ) {
            while( allTables.next() ) {
                final String tableName = allTables.getString("TABLE_NAME");

                values.add( tableName );
                }
        }

        return values;
    }

    /**
     * Returns list of table name for current schema
     * @return list of table name for current schema
     * @throws SQLException if a database access error occurs
     */
    public List<String> getTableList() throws SQLException
    {
        return getTableList( "TABLE" );
    }

    /**
     * Returns list of view name for current schema
     * @return list of view name for current schema
     * @throws SQLException if a database access error occurs
     */
    public List<String> getViewList() throws SQLException
    {
        return getTableList( "VIEW" );
    }

    /**
     * Wrapper for {@link DatabaseMetaData#getTableTypes()} Retrieves the
     * table types available in this database. The results are ordered
     * by table type.
     *
     * @return a List of string that is a table type
     * @throws SQLException
     *             if any
     */
    public List<String> getTableTypes() throws SQLException
    {
        final ResultSet tableTypes = this.databaseMetaData.getTableTypes();

        return transformResultSetToList( tableTypes, 1 );
    }

    private List<String> transformResultSetToList(
            final ResultSet resultSet,
            final int       columnIndex
            ) throws SQLException
    {
        final List<String> contentList = new ArrayList<>();

        try( final ResultSet rSet = resultSet ) {
            while( rSet.next() ) {
                final String value = rSet.getString( columnIndex );

                contentList.add( value );
                }
        }

        return contentList;
    }
    /**
     * Returns a Map of tables names associate to a List of columns names
     * for current schema
     *
     * @param tableTypes
     *      a list of table types, which must be from the list of table types
     *      returned from {@link DatabaseMetaData#getTableTypes()} or
     *      {@link #getTableTypes()},to include; null returns all types
     * @return
     *      a Map of tables names associate to a List of columns names for
     *      current schema
     * @throws
     *      SQLException if a database access error occurs
     */
    public Map<String,List<String>> getTableMap(final String...tableTypes) throws SQLException
    {
        final Map<String, List<String>> values = new LinkedHashMap<>();

        try (final ResultSet allTables = getTables( tableTypes ) ) {
            while( allTables.next() ) {
                final String tableName = allTables.getString( "TABLE_NAME" );

                handleTable( tableName, values );
            }
        }

        return values;
    }

    private void handleTable( final String tableName, final Map<String, List<String>> values )
        throws SQLException
    {
        try (final ResultSet colList = this.databaseMetaData.getColumns( null, null, tableName, null )) {
            final List<String> columnsList = new ArrayList<>();

            while( colList.next() ) {
                columnsList.add( colList.getString( "COLUMN_NAME" ) );
            }

            values.put( tableName, columnsList );
        }
    }

    private ResultSet getTables( final String... tableTypes ) throws SQLException
    {
        final String catalog          = null;
        final String schemaPattern    = null;
        final String tableNamePattern = "%";

        /*
         * catalog
         *      a catalog name; must match the catalog name as it is stored in the
         *      database; "" retrieves those without a catalog; null means that the
         *      catalog name should not be used to narrow the search
         *
         * schemaPattern
         *      a schema name pattern; must match the schema name as it is stored in
         *      the database; "" retrieves those without a schema; null means that
         *      the schema name should not be used to narrow the search
         *
         * tableNamePattern
         *      a table name pattern; must match the table name as it is stored in
         *      the database
         *
         * types
         *      a list of table types, which must be from the list of table types
         *      returned from getTableTypes(),to include; null returns all types
         */
        return this.databaseMetaData.getTables( catalog, schemaPattern, tableNamePattern, tableTypes );
    }

    /**
     * Returns a Map of tables names associate to a List of columns names for current schema
     * @return a Map of tables names associate to a List of columns names for current schema
     * @throws SQLException if a database access error occurs
     */
    public Map<String,List<String>> getTableMap() throws SQLException
    {
        return getTableMap( "TABLE" );
    }

    /**
     * Returns a Map of view names associate to a List of columns names for current schema
     * @return a Map of view names associate to a List of columns names for current schema
     * @throws SQLException if a database access error occurs
     */
    public Map<String,List<String>> getViewMap() throws SQLException
    {
        return getTableMap( "VIEW" );
    }
}
