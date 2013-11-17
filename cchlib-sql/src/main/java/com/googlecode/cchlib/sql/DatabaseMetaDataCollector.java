package com.googlecode.cchlib.sql;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private static final transient Logger LOGGER = Logger.getLogger( DatabaseMetaDataCollector.class );

    /** @serial */
    private DatabaseMetaData databaseMetaData;

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
     */
    @Override
    public Map<String,String> toMap()
    {
        final Map<String,String> map = new LinkedHashMap<String,String>();

        {
            final MappableBuilder mb = new MappableBuilder(
                    new DefaultMappableBuilderFactory()
                        .setMethodesNamePattern( ".*" )
                        .add( Object.class )
                        .add( MappableItem.MAPPABLE_ITEM_SHOW_ALL )
                    );

            map.putAll( mb.toMap( databaseMetaData ) );
        }

        {
            MapBuilder builder = new MapBuilder( databaseMetaData );

            map.putAll( builder.toMap() );
        }

        return Collections.unmodifiableMap( map );
    }

    /**
     * Returns list of table name for current schema
     * @param tableTypes
     * @return list of table name for current schema
     * @throws SQLException if a database access error occurs
     */
    public List<String> getTableList(final String...tableTypes) 
        throws SQLException
    {
        final List<String>  values      = new ArrayList<String>();
        final ResultSet     allTables   = databaseMetaData.getTables(null,null,null,tableTypes);

        while( allTables.next() ) {
            final String tableName = allTables.getString("TABLE_NAME");

            values.add( tableName );
            }
        allTables.close();

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
     * Returns a Map of tables names associate to a List of columns names for current schema
     * @param tableTypes
     * @return a Map of tables names associate to a List of columns names for current schema
     * @throws SQLException if a database access error occurs
     */
    public Map<String,List<String>> getTableMap(final String...tableTypes) throws SQLException
    {
        final Map<String,List<String>> values    = new LinkedHashMap<String,List<String>>();
        final ResultSet                allTables = databaseMetaData.getTables(null,null,null,tableTypes);

        while( allTables.next() ) {
            final String tableName = allTables.getString("TABLE_NAME");

            final ResultSet       colList       = databaseMetaData.getColumns(null,null,tableName,null);
            final List<String>    columnsList   = new ArrayList<String>();

            while( colList.next() ) {
                columnsList.add( colList.getString("COLUMN_NAME") );
                  }

            values.put( tableName, columnsList );
            colList.close();
              }

        allTables.close();

        return values;
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

    /**
     * Build list of methods to check that return ResultSet
     */
    private static List<Method> getMethodsToInvokeForResultSet()
    {
        final Method[]      methods = DatabaseMetaData.class.getMethods();
        final List<Method>  methodsToInvoke = new ArrayList<Method>();

        for(Method m:methods) {
            final Class<?> returnType = m.getReturnType();

            if( m.getParameterTypes().length == 0 ) {
                if( returnType == ResultSet.class ) {
                    methodsToInvoke.add( m );
                }
            }
        }

        return methodsToInvoke;
    }

    private Object invoke(
            final DatabaseMetaData  databaseMetaData,
            final Method            m
            )
    {
        try {
            return m.invoke( databaseMetaData );
            }
        catch( IllegalArgumentException e ) {
            LOGGER.warn( "Error while invoke " + m, e );
            return e;
            }
        catch( IllegalAccessException e ) {
            LOGGER.warn( "Error while invoke " + m, e );
            return e;
            }
        catch( InvocationTargetException e ) {
            LOGGER.warn( "Error while invoke " + m, e );
            return e;
            }
    }

    private class MapBuilder implements Mappable
    {
        /** @Serial */
        private Map<String, String> values = new LinkedHashMap<String,String>();

        /**
         * Build internal Map of methods names and values
         *
         * @param databaseMetaData
         */
        MapBuilder(
                final DatabaseMetaData databaseMetaData
                )
        {
            List<Method> methodsToInvoke;

            Comparator<Method> methodComparator = new Comparator<Method>()
            {
                @Override
                public int compare( Method m0, Method m1 )
                {
                    return m0.getName().compareTo( m1.getName() );
                }

            };

            methodsToInvoke = getMethodsToInvokeForResultSet();

            Collections.sort( methodsToInvoke, methodComparator );
            values.put( "# of methods that return a ResultSet", Integer.toString( methodsToInvoke.size() ) );

            for( Method m:methodsToInvoke ) {
                final Object rSet  = invoke( databaseMetaData, m );

                values.put( "Method that return a ResultSet", m.toString() );
                addResultSet( m, rSet );
                }
        }

        private void addResultSet( Method m, Object resultSet )
        {
            if( resultSet == null ) {
                values.put(
                    String.format( "%s ResultSet", m.getName()),
                    null
                    );
                }
            else if( !(resultSet instanceof ResultSet) ) {
                if( resultSet instanceof Throwable ) {
                    values.put(
                            String.format( "%s ResultSet=>Throwable", m.getName() ),
                            Throwable.class.cast( resultSet ).getMessage()
                            );
                    }
                else {
                    values.put(
                            String.format( "%s ResultSet=>?", m.getName() ),
                            resultSet.getClass().getName() + " : " + resultSet.toString()
                            );
                    }
                }
            else {
                ResultSet rSet   = ResultSet.class.cast( resultSet );
                int       cCount = 0;    // not yet populate
                String[]  cNames = null; // not yet populate

                try {
                    final ResultSetMetaData metaData = rSet.getMetaData();

                    cCount = metaData.getColumnCount();
                    cNames = new String[ cCount + 1 ];

                    for( int i = 1; i<=cCount; i++ ) {
                        String cName = metaData.getColumnName( i );
                        cNames[ i ]  = cName;

//                        values.put(
//                            String.format( "%s ResultSetMetaData[%d]", m.getName(), i ),
//                            cName
//                            );
                        }
                    }
                catch( SQLException e ) {
                    LOGGER.warn( "Error while reading ResultSetMetaData return by " + m, e );

                    values.put(
                        String.format( "%s ResultSetMetaData[SQLException]", m.getName() ),
                        e.getMessage()
                        );

                    if( cNames == null ) {
                        cNames = new String[ cCount + 1 ];
                        }
                    }

                int row = 0;

                try {
                    while( rSet.next() ) {
                        for( int i = 1; i <= cCount; i++ ) {
                            values.put(
                                    String.format(
                                        "%s ResultSet[%d][%s].%d",
                                        m.getName(),
                                        i,
                                        cNames[ i ],
                                        row
                                        ),
                                    rSet.getString( i ) );
                            }
                        row++;
                        }
                    }
                catch( SQLException e ) {
                    LOGGER.warn( "Error while reading ResultSet return by " + m, e );

                    values.put(
                        String.format( "%s ResultSet=>SQLException (row=%d)", m.getName(), row ),
                        e.getMessage()
                        );
                    }
                finally {
                    try { rSet.close(); } catch( SQLException ignore ) { }
                    }
                }
        }

        @Override
        public Map<String,String> toMap()
        {
            return values;
        }
    }
}
