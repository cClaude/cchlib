package com.googlecode.cchlib.sql.export;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.googlecode.cchlib.Const;
import com.googlecode.cchlib.sql.SQLCloseException;
import com.googlecode.cchlib.sql.SQLTools;

/**
 * Support class for {@link ExportSQL}
 */
final class ExportSQLPrinter implements Closeable
{
    private final StringBuilder   q = new StringBuilder();

    private final ExportSQL exportSQL;
    private final Writer          out;

    private Statement s = null;

    public ExportSQLPrinter(
            final ExportSQL         exportSQL,
            final Writer            out
            )
    {
        this.exportSQL  = exportSQL;
        this.out        = out;
    }

    public void doExportDeleteData(
            final TableDescription tableDescription
            )
        throws IOException
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

    public void doExportData(
            final TableDescription tableDesc
            )
        throws SQLException, IOException
    {
        if( this.s == null ) {
            this.s = this.exportSQL.getConnection().createStatement();
            }

        q.setLength( 0 );
        q.append(  "select * from `" );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            q.append( this.exportSQL.getSchemaName() );
            q.append( "`.`" );
            }

        q.append( tableDesc.getName() );

        if( tableDesc.getWhereCondition() != null ) {
            q.append( "` WHERE " );
            q.append( tableDesc.getWhereCondition() );
            q.append( ';' );
            }
        else {
            q.append( "`;" );
            }

        this.s.execute( q.toString() );

        // Get result set
        ResultSet r = this.s.getResultSet();
        ResultSetMetaData rm = r.getMetaData(); // call before r.next() see note 4 above in JDBC hints

        println();
        println( "-- ---------------------------" );
        print( "-- Data for `"  );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            print( this.exportSQL.getSchemaName() ).print( "`.`" );
            }

        print( tableDesc.getName() ).println( "`" );
        println( "-- ---------------------------" );

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_DISABLE_AUTOCOMMIT ) ) {
            println( "SET AUTOCOMMIT=0;" );
            }

        if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
            print( "USE `" ).print( this.exportSQL.getSchemaName() ).println( "`;" );
            }

        println( Const.EMPTY_STRING );
        println( Const.EMPTY_STRING );

        while( r.next() ) {
            print( "INSERT INTO `" );

            if( this.exportSQL.getConfigSet().contains( ExportSQL.Config.ADD_PREFIX_SCHEMA ) ) {
                print( this.exportSQL.getSchemaName() );
                print( "`.`" );
                }

            print( tableDesc.getName() );
            print( "` (" );
            boolean isFirst = true;

            for(int i = 1; i <= rm.getColumnCount(); i++) {
                if( isFirst ) {
                    isFirst = false;
                    }
                else {
                    print( "," );
                    }
                print( "`" );
                print( rm.getColumnName( i ) );
                print( "`" );
                }
            print( ") VALUES (" );

            isFirst = true;

            for(int i = 1; i <= rm.getColumnCount(); i++) {
                if( isFirst ) {
                    isFirst = false;
                    }
                else {
                    print( "," );
                    }
                String s = r.getString( rm.getColumnName( i ) );

                if( s == null ) {
                    print( "NULL" );
                    }
                else {
                    print( "'" );
                    print( SQLTools.parseFieldValue( s ) );
                    print( "'" );
                    }
                }
            println( ");" );
            }

        println();
        println( "COMMIT;" );
        //print( "-- OPTIMIZE TABLE `" ).print( schema ).print( "`.`" ).print( tablename ).println( "`;" );
        println();
    }

    public void println() throws IOException
    {
        out.write( '\n' );
     }

    public ExportSQLPrinter print( String s ) throws IOException
    {
        out.write( s );
        return this;
    }

    public void println( String s ) throws IOException
    {
        print( s );
        println();
    }

    @Override
    public void close() throws SQLCloseException
    {
        if(s != null) { 
        	try {
        		s.close();
				} 
        	catch ( SQLException e ) {
				throw new SQLCloseException( e );
				} 
        	}
    }

}