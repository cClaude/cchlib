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
    private final String[]   exportTables;
    private final String     schema;
    private final Timestamp  exportDate;

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

    /**
     * Returns export Time stamp (could be overwrite)
     * @return export Time stamp
     */
    public Timestamp getTimestamp()
    {
        return exportDate;
    }
    
    /**
     * Returns export Time stamp as a String (could be overwrite)
     * @return export Time stamp as a String
     */
    public String getTimestampString()
    {
        return getTimestamp().toString();
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
        exportTable.print( "-- Export `" ).print( schema ).print( "` : " ).println( getTimestampString() );
        exportTable.println( "-- ---------------------------" );
        exportTable.print( "USE `" ).print( schema ).println( "`;" );
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
            print( "DELETE FROM `" ).print( schema ).print( "`.`" ).print( tablename ).println( "`;" );
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
            print( "-- Data for `"  ).print( schema  ).print( "`.`" ).print( tablename ).println( "`" );
            println( "-- ---------------------------" );
            println( "SET AUTOCOMMIT=0;" );
            print( "USE `" ).print( schema ).println( "`;" );
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

        public ExportTable print( String s ) throws IOException
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
        public void close()
        {
            try { if(s != null) s.close(); } catch(Exception e) {}
            try { if(c != null) c.close(); } catch(Exception e) {}
        }
    }
}
