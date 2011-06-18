package cx.ath.choisnet.sql;

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
import cx.ath.choisnet.lang.reflect.Mappable;
import cx.ath.choisnet.lang.reflect.MappableBuilder;
import cx.ath.choisnet.lang.reflect.MappableBuilderDefaultFactory;

/**
 * Collect informations on {@link DatabaseMetaData} and put then into a
 * {@link Map} of strings.
 */
public class DatabaseMetaDataCollector implements Mappable, Serializable
{
    private static final long serialVersionUID = 2L;
    private static final transient Logger slogger = Logger.getLogger( DatabaseMetaDataCollector.class );

//    /** Commons types */
//    private static final Class<?>[] validReturnClassesForString = {
//        Boolean.TYPE,
//        Integer.TYPE,
//        Long.TYPE,
//        Float.TYPE,
//        Double.TYPE,
//        Byte.TYPE,
//        Character.TYPE,
//        Short.TYPE,
//        String.class,
//    };
//    private static final String[] _tableTypes = { "TABLE" };

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
     * Returns {@value Map} of methods/results
     */
    @Override
    public Map<String,String> toMap()
    {
        final Map<String,String> map = new LinkedHashMap<String,String>();

        {
            final MappableBuilder mb = new MappableBuilder(
                    new MappableBuilderDefaultFactory()
                        .setMethodesNamePattern( ".*" )
                        .add( Object.class )
                        .add( MappableBuilder.MAPPABLE_ITEM_SHOW_ALL )
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
     *
     * @return
     * @throws SQLException if a database access error occurs
    private Map<String, String> getTablesMapV1() throws SQLException
    {
        Map<String, String> values  = new LinkedHashMap<String,String>();
        ResultSet           rs1     = databaseMetaData.getSchemas();

        final ResultSetMetaData rs1md = rs1.getMetaData();
        final int               rs1cc = rs1md.getColumnCount();

        for( int i = 1; i<=rs1cc; i++ ) {
            String cName = rs1md.getColumnName( i );

            values.put(
                String.format( "*** Schemas col[%d]", i ),
                cName
                );
            }

        while( rs1.next()) {
            final String ss = rs1.getString(1); // col: TABLE_SCHEM

            values.put("** TABLE_SCHEM", ss);
            values.put("** TABLE_CATALOG", rs1.getString(2));

            final ResultSet rs2 = databaseMetaData.getTables(null, ss, "%", null);

            while( rs2.next() ) {
                values.put(rs2.getString(3), rs2.getString(4));
                }

            rs2.close();
            }

        rs1.close();

        return values;
    }
     */

    /**
     * Returns list of table name for current schema
     * @param tableTypes
     * @return list of table name for current schema
     * @throws SQLException if a database access error occurs
     */
    public List<String> getTableList(final String...tableTypes) throws SQLException
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

//    /**
//     * Build list of methods to check that return something easy to put
//     * in a String
//     */
//    private static List<Method> getMethodsToInvokeForString()
//    {
//        final Method[]      methods = DatabaseMetaData.class.getMethods();
//        final List<Method>  methodsToInvoke = new ArrayList<Method>();
//
//        for(Method m:methods) {
//            final Class<?> returnType = m.getReturnType();
//
//            if( m.getParameterTypes().length == 0 ) {
//                for(Class<?> c: validReturnClassesForString) {
//                    if( returnType == c ) {
////                        System.out.println("m:" + m + " ** " + returnType.getCanonicalName() );
////                        System.err.println( "---" + returnType );
//                        methodsToInvoke.add( m );
//                        break;
//                    }
//                }
//            }
//        }
//
//        return methodsToInvoke;
//    }

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
//                    System.out.println("m:" + m + " ** " + returnType.getCanonicalName() );
//                    System.err.println( "---" + returnType );
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
            slogger.warn( "Error while invoke " + m, e );
            return e;
            }
        catch( IllegalAccessException e ) {
            slogger.warn( "Error while invoke " + m, e );
            return e;
            }
        catch( InvocationTargetException e ) {
            slogger.warn( "Error while invoke " + m, e );
            return e;
            }
    }

//    private String invokeAndGetString(
//            final DatabaseMetaData  databaseMetaData,
//            final Method            m
//            )
//    {
//        Object result = invoke( databaseMetaData, m );
//
//        return result == null ? null : result.toString();
//    }

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
//            List<Method> methodsToInvoke = getMethodsToInvokeForString();
//
            Comparator<Method> methodComparator = new Comparator<Method>()
            {
                @Override
                public int compare( Method m0, Method m1 )
                {
                    return m0.getName().compareTo( m1.getName() );
                }

            };
//
//            Collections.sort( methodsToInvoke, methodComparator );
//
//            for(Method m:methodsToInvoke) {
//                final String value = invokeAndGetString( databaseMetaData, m );
//                values.put( m.getName(), value );
//            }

            methodsToInvoke = getMethodsToInvokeForResultSet();

            Collections.sort( methodsToInvoke, methodComparator );
            values.put( "# of methods that return a ResultSet", Integer.toString( methodsToInvoke.size() ) );

            for(Method m:methodsToInvoke) {
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
                            String.format( "%s ResultSet=>Throwable", m.getName()),
                            Throwable.class.cast( resultSet ).getMessage()
                            );
                    }
                else {
                    values.put(
                            String.format( "%s ResultSet=>?", m.getName()),
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
                        cNames[ i ] = cName;

//                        values.put(
//                            String.format( "%s ResultSetMetaData[%d]", m.getName(), i ),
//                            cName
//                            );
                        }
                    }
                catch( SQLException e ) {
                    slogger.warn( "Error while reading ResultSetMetaData return by " + m, e );

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
                    slogger.warn( "Error while reading ResultSet return by " + m, e );

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
//            return Collections.unmodifiableMap( values );
            return values;
        }
    }
}
