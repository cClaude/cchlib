package com.googlecode.cchlib.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import com.googlecode.cchlib.util.mappable.Mappable;

class MapBuilder implements Mappable
{
    /** @Serial */
    private final Map<String, String> values = new LinkedHashMap<>();

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

        final Comparator<Method> methodComparator = ( m0, m1 ) -> m0.getName().compareTo( m1.getName() );

        methodsToInvoke = getMethodsToInvokeForResultSet();

        Collections.sort( methodsToInvoke, methodComparator );
        this.values.put( "# of methods that return a ResultSet", Integer.toString( methodsToInvoke.size() ) );

        for( final Method m:methodsToInvoke ) {
            final Object rSet  = invoke( databaseMetaData, m );

            this.values.put( "Method that return a ResultSet", m.toString() );
            addResultSet( m, rSet );
            }
    }

    private void addResultSet( final Method m, final Object resultSet )
    {
        if( resultSet == null ) {
            this.values.put( String.format( "%s ResultSet", m.getName() ), null );
        } else if( !(resultSet instanceof ResultSet) ) {
            if( resultSet instanceof Throwable ) {
                this.values.put( String.format( "%s ResultSet=>Throwable", m.getName() ), Throwable.class.cast( resultSet ).getMessage() );
            } else {
                this.values.put( String.format( "%s ResultSet=>?", m.getName() ), resultSet.getClass().getName() + " : " + resultSet.toString() );
            }
        } else {
            addResultSet2( m, resultSet );

        }
    }

    private void addResultSet2( final Method m, final Object resultSet )
    {
        try (final ResultSet rSet = ResultSet.class.cast( resultSet )) {
            int      cCount = 0; // not yet populate
            String[] cNames = null; // not yet populate

            try {
                final ResultSetMetaData metaData = rSet.getMetaData();

                cCount = metaData.getColumnCount();
                cNames = new String[cCount + 1];

                for( int i = 1; i <= cCount; i++ ) {
                    final String cName = metaData.getColumnName( i );
                    cNames[ i ] = cName;

                    // values.put(
                    // String.format( "%s ResultSetMetaData[%d]", m.getName(), i ),
                    // cName
                    // );
                }
            }
            catch( final SQLException e ) {
                DatabaseMetaDataCollector.LOGGER.warn( "Error while reading ResultSetMetaData return by " + m, e );

                this.values.put( String.format( "%s ResultSetMetaData[SQLException]", m.getName() ), e.getMessage() );

                if( cNames == null ) {
                    cNames = new String[cCount + 1];
                }
            }

            int row = 0;

            try {
                while( rSet.next() ) {
                    for( int i = 1; i <= cCount; i++ ) {
                        this.values.put(
                                String.format( "%s ResultSet[%d][%s].%d", m.getName(), Integer.valueOf( i ), cNames[ i ], Integer.valueOf( row ) ),
                                rSet.getString( i ) );
                    }
                    row++;
                }
            }
            catch( final SQLException e ) {
                DatabaseMetaDataCollector.LOGGER.warn( "Error while reading ResultSet return by " + m, e );

                this.values.put( String.format( "%s ResultSet=>SQLException (row=%d)", m.getName(), Integer.valueOf( row ) ), e.getMessage() );
            }
        }
        catch( final SQLException e ) {
            DatabaseMetaDataCollector.LOGGER.warn( "Unknown error while reading ResultSetMetaData return by " + m, e );
        }
    }

    /**
     * Build list of methods to check that return ResultSet
     */
    private static List<Method> getMethodsToInvokeForResultSet()
    {
        final Method[]      methods = DatabaseMetaData.class.getMethods();
        final List<Method>  methodsToInvoke = new ArrayList<Method>();

        for(final Method m:methods) {
            final Class<?> returnType = m.getReturnType();

            if( (m.getParameterTypes().length == 0) && (returnType == ResultSet.class) ) {
                methodsToInvoke.add( m );
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
        catch( final IllegalArgumentException | IllegalAccessException | InvocationTargetException e ) {
            DatabaseMetaDataCollector.LOGGER.warn( "Error while invoke " + m, e );
            return e;
            }
    }

    @Override
    public Map<String,String> toMap()
    {
        return this.values;
    }
}