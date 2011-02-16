package cx.ath.choisnet.sql;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.sql.DataSource;
import cx.ath.choisnet.sql.mysql.MySQLTools;

/**
 * Create a SQL script to export values from tables.
 * <br/>
 * 
 * 
 * @author Claude CHOISNET
 */
public class ExportSQL 
{
    private final DataSource ds;
    private final String[] exportTables;
    private final String schema;
    private final Timestamp exportDate;

    /**
     * Initialize ExportSQL object
     * 
     * @param dataSource DataSource to retrieve database connection.
     * @param schemaName Schema name of database.
     * @param exportTables Array of table names (order could be important
     * while running result SQL). 
     * @param exportTimestamp Time stamp to identify SQL export.
     */
    public ExportSQL( 
            final DataSource    dataSource,
            final String        schemaName,
            final String[]      exportTables,
            final Timestamp     exportTimestamp
            ) 
    {
        this.ds             = dataSource;
        this.schema         = schemaName;
        this.exportTables   = exportTables;
        this.exportDate     = exportTimestamp; 
    }

    /*
    public String getSafeTimeStampForFileName()
    {
        return exportDate.toString().replace( ':', '-' ).replace( ' ', '_' );
    }
    */

    /**
     * Returns export Time stamp
     * @return export Time stamp
     */
    final // TODO remove this
    public Timestamp getTimestamp()
    {
        return exportDate;
    }
    
    /**
     * Export table content
     * 
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    final //TODO remove this
    public void export( final Writer outputWriter ) 
        throws SQLException, 
               IOException
    {
        ExportTable exportTable = new ExportTable( outputWriter );

        exportTable.println( "-- ---------------------------" );
        exportTable.println( "-- Export `" + schema + "` : " + exportDate );
        exportTable.println( "-- ---------------------------" );
        exportTable.println( "USE `" +schema + "`;" );
        exportTable.println();

        try {
            exportTable.println( "-- ---------------------------" );
            exportTable.println( "-- Cleanup" );
            exportTable.println( "-- ---------------------------" );
            for( int i = exportTables.length - 1; i>= 0; --i ) {
                exportTable.doExportDeleteData( exportTables[ i ] );
                }
            for( String tablename : exportTables ) {
                exportTable.doExportData( tablename );
                }
            exportTable.println( "-- done." );
            exportTable.println();
            }
        finally {
            exportTable.close();
            }
    }

    private final class ExportTable implements Closeable
    {
        private Connection c = null;
        private Statement  s = null;
        private Writer out;

        public ExportTable( Writer out )
        {
            this.out = out;
        }

        public void doExportDeleteData(
                final String tablename
                ) 
            throws IOException
        {
            println( "DELETE FROM `" +schema + "`.`" + tablename + "`;" );
        }
        
        
        public void doExportData(
                final String tablename
                )
            throws SQLException, IOException
        {
            if( c == null ) {
                c = ds.getConnection();
                }
            if( s == null ) {
                s = c.createStatement();
                }

            final String selectQry = "select * from `" + schema + "`.`" + tablename + "`;";

            s.execute( selectQry );

            // Get result set
            ResultSet r = s.getResultSet();
            ResultSetMetaData rm = r.getMetaData(); // call before r.next() see note 4 above in JDBC hints

            println();
            println( "-- ---------------------------" );
            println( "-- Data for `" +schema + "`.`" + tablename + "`" );
            println( "-- ---------------------------" );
            println( "SET AUTOCOMMIT=0;" );
            println( "USE `" +schema + "`;" );
            println( "" );
            println( "" );

            while( r.next() ) {
                print( "INSERT INTO `" );
                print( schema );
                print( "`.`" );
                print( tablename );
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
                    print( "'" );
                    print(
                        MySQLTools.parseFieldValue( 
                                r.getString( rm.getColumnName( i ) )
                                )
                        );
                    print( "'" );
                    ;
                    }
                println( ");" );
                }

            println();
            println( "COMMIT;" );
            //println( "-- OPTIMIZE TABLE `" +schema + "`.`" + tablename + "`;" );
            println();
        }

        public void println() throws IOException
        {
            out.write( '\n' );
        }

        public void print( String s ) throws IOException
        {
            out.write( s );
        }

        public void println( String s ) throws IOException
        {
            print( s );
            println();
        }

        @Override
        public void close()
        {
            try { if(s != null) s.close(); } catch(Exception e) {}
            try { if(c != null) c.close(); } catch(Exception e) {}
        }
    }
}
