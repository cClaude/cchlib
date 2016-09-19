package com.googlecode.cchlib.sql.export;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.googlecode.cchlib.lang.StringHelper;
import com.googlecode.cchlib.sql.SQLCloseException;
import com.googlecode.cchlib.sql.SQLTools;

/**
 * Support class for {@link ExportSQL}
 */
final class ExportSQLPrinter implements Closeable
{
    private final ExportSQL exportSQL;
    private final Writer    out;
    private final int       limitRows;

    private Statement statement = null;

    public ExportSQLPrinter(
        final ExportSQL exportSQL, //
        final Writer    out, //
        final int       limitRows
        )
    {
        this.exportSQL  = exportSQL;
        this.out        = out;
        this.limitRows  = limitRows;
    }

    /**
     * Create SQL to delete data for a table
     *
     * @param tableDescription Tables to add
     * @throws IOException if any
     */
    public void doExportDeleteData(
        final TableDescription tableDescription
        ) throws IOException
    {
        print( "DELETE FROM `" );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            print( this.exportSQL.getSchemaName() ).print( "`.`" );
            }

        print( tableDescription.getName() ).print( "`" );

        final String wc = tableDescription.getWhereCondition();
        if( wc != null ) {
            // Delete only some values
            print( " WHERE " ).print( wc );
            }
        println( ";" );
    }

    /**
     * Export data for a Table
     *
     * @param tableDescription
     *      Table information
     * @throws SQLException if any
     * @throws IOException if any
     */
    public void doExportData( // NOSONAR
            final TableDescription tableDescription //
            ) //
        throws SQLException, IOException // NOSONAR
    {
        createStatementIfNeeded();

        this.statement.execute( createQuery( tableDescription ) );

        println();

        exportShema( tableDescription );

        println( StringHelper.EMPTY );
        println( StringHelper.EMPTY);

        // Get result set
        try( ResultSet r = this.statement.getResultSet() ) {
            extractData( tableDescription, r );
        }

        println();
        println( "COMMIT;" );
        // print( "-- OPTIMIZE TABLE `" ).print( schema ).print( "`.`" ).print( tablename ).println( "`;" );
        println();
    }

    private void extractData( //
            final TableDescription tableDescription, //
            final ResultSet        resultSet //
            ) throws SQLException, IOException
    {
        final ResultSetMetaData rm = resultSet.getMetaData(); // call before r.next() see note 4 above in JDBC hints

        int count = 0;

        while( resultSet.next() ) {
            print( "INSERT INTO `" );

            if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
                print( this.exportSQL.getSchemaName() );
                print( "`.`" );
            }

            print( tableDescription.getName() );
            print( "` (" );
            boolean isFirst = true;

            for( int i = 1; i <= rm.getColumnCount(); i++ ) {
                if( isFirst ) {
                    isFirst = false;
                } else {
                    print( "," );
                }
                print( "`" );
                print( rm.getColumnName( i ) );
                print( "`" );
            }
            print( ") VALUES (" );

            isFirst = true;

            for( int i = 1; i <= rm.getColumnCount(); i++ ) {
                if( isFirst ) {
                    isFirst = false;
                } else {
                    print( "," );
                }
                final String s = resultSet.getString( rm.getColumnName( i ) );

                if( s == null ) {
                    print( "NULL" );
                } else {
                    print( "'" );
                    print( SQLTools.parseFieldValue( s ) );
                    print( "'" );
                }
            }
            println( ");" );

            if( this.limitRows > 0 ) {
                if( count > this.limitRows ) {
                    break;
                }
                count++;
            }
        }
    }

    private void exportShema( final TableDescription tableDescription ) throws IOException
    {
        println( "-- ---------------------------" );
        print( "-- Data for `"  );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            print( this.exportSQL.getSchemaName() ).print( "`.`" );
            }

        print( tableDescription.getName() ).println( "`" );
        println( "-- ---------------------------" );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_DISABLE_AUTOCOMMIT ) ) {
            println( "SET AUTOCOMMIT=0;" );
            }

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            print( "USE `" ).print( this.exportSQL.getSchemaName() ).println( "`;" );
            }
    }

    private String createQuery( final TableDescription tableDescription ) throws SQLException
    {
        final StringBuilder query = new StringBuilder();

        query.append( "SELECT * FROM `" );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            query.append( this.exportSQL.getSchemaName() );
            query.append( "`.`" );
            }

        query.append( tableDescription.getName() );

        if( tableDescription.getWhereCondition() != null ) {
            query.append( "` WHERE " );
            query.append( tableDescription.getWhereCondition() );
            query.append( ';' );
            }
        else {
            query.append( "`;" );
            }

        return query.toString();
    }

    private void createStatementIfNeeded() throws SQLException
    {
        if( this.statement == null ) {
            synchronized( this ) {
                if( this.statement == null ) {
                    this.statement = this.exportSQL.getConnection().createStatement();
                }
            }
        }
    }

    void println() throws IOException
    {
        this.out.write( '\n' );
     }

    ExportSQLPrinter print( final String s ) throws IOException
    {
        this.out.write( s );
        return this;
    }

    void println( final String s ) throws IOException
    {
        print( s );
        println();
    }

    @Override
    public void close() throws SQLCloseException
    {
        if( this.statement != null ) {
            try {
                this.statement.close();
            }
            catch( final SQLException e ) {
                throw new SQLCloseException( e );
            }
        }
    }
}
