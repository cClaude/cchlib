package cx.ath.choisnet.sql.export;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.EnumSet;
import cx.ath.choisnet.sql.SQLTools;

/**
 * Create a SQL script to export values from tables.
 */
public class ExportSQL  implements Closeable
{
    /**
     * Configuration for {@link ExportSQL}
     */
    public enum Config
    {
        /**
         * Add Use `Schema`;
         */
        ADD_USE_SCHEMA,
        /**
         * Add `Schema`.;
         */
        ADD_PREFIX_SCHEMA,
        /**
         * Add SET AUTOCOMMIT=0 in export.
         */
        ADD_DISABLE_AUTOCOMMIT,

    }

    private final Connection            connection;
    private final TableDescription[]    exportTables;
    private final String                schema;
    private final Date                  exportDate;
    private final EnumSet<Config>       config;
    private Statement                   s = null;

    /**
     * Initialize ExportSQL object
     *
     * @param connection Connection to database.
     * @param schemaName Schema name of database.
     * @param config {@link EnumSet} of {@link Config} to configure output.
     * @param exportDate {@link Date} to identify SQL export (just in comment section),
     *        if null use current date.
     * @param exportTables Array of TableDescription (order could be important
     *        while running result SQL).
     */
    public ExportSQL(
            final Connection            connection,
            final String                schemaName,
            final EnumSet<Config>       config,
            final Date                  exportDate,
            final TableDescription...   exportTables
            )
    {
        this.connection     = connection;
        this.schema         = schemaName;
        this.exportTables   = exportTables;

        if( exportDate ==  null ) {
            this.exportDate = new Date(); // now
            }
        else {
            this.exportDate = exportDate;
            }

        if( config == null ) {
            this.config = EnumSet.noneOf( Config.class );
            }
        else {
            this.config = EnumSet.copyOf( config );

            if( schemaName == null ) {
                this.config.remove( Config.ADD_USE_SCHEMA );
                this.config.remove( Config.ADD_PREFIX_SCHEMA );
                }
            }
    }

    /**
     * Initialize ExportSQL object.
     * <p>
     * Set configuration with defaults: {@link Config#ADD_PREFIX_SCHEMA},
     * {@link Config#ADD_SET_AUTOCOMMIT} and {@link Config#ADD_USE_SCHEMA}
     * </p>
     *
     * @param connection Connection to database.
     * @param schemaName Schema name of database. If null, do not add schema in export.
     * @param exportDate {@link Date} to identify SQL export (just in comment section),
     *        if null use current date.
     * @param exportTables Array of TableDescription (order could be important
     * while running result SQL).
     */
    public ExportSQL(
            final Connection            connection,
            final String                schemaName,
            final Date                  exportDate,
            final TableDescription...   exportTables
             )
    {
        this(   connection,
                schemaName,
                EnumSet.of(
                        Config.ADD_PREFIX_SCHEMA,
                        Config.ADD_DISABLE_AUTOCOMMIT,
                        Config.ADD_USE_SCHEMA
                        ),
                exportDate,
                exportTables
                );
    }

    /**
     * Initialize ExportSQL object
     *
     * @param connection Connection to database.
     * @param schemaName Schema name of database. If null, do not add schema in export.
     * @param exportDate {@link Date} to identify SQL export (just in comment section),
     *        if null use current date.
     * @param exportTables Array of table names (order could be important
     * while running result SQL).
     */
    public ExportSQL(
            final Connection    connection,
            final String        schemaName,
            final Date          exportDate,
            final String ...    exportTables
            )
    {
        this(   connection,
                schemaName,
                exportDate,
                toTableDescription( exportTables )
                );
    }

    private static TableDescription[] toTableDescription(
            final String[] exportTablenames
            )
    {
        final TableDescription[] ed = new TableDescription[ exportTablenames.length ];

        for(int i = 0; i<exportTablenames.length; i++ ) {
            ed[i] = new TableDescriptionImpl( exportTablenames[i] );
            }

        return ed;
    }

    /**
     * Returns export Date (could be overwrite)
     * @return export Date
     */
    public Date getExportDate()
    {
        return exportDate;
    }

    /**
     * Returns export Date as a String (could be overwrite)
     * @return export Date as a String
     */
    public String getExportDateToString()
    {
        return getExportDate().toString();
    }

    /**
     * Export all content according to configuration.
     * <p>
     * Call first {@link #exportHeader(Writer)},
     * then {@link #exportDeleteContent(Writer)},
     * then {@link #exportTablesContent(Writer)}
     * and then {@link #close()}.
     * </P>
     *
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    public void exportAll( final Writer outputWriter )
        throws SQLException,
               IOException
    {
        try {
            exportHeader( outputWriter );
            exportDeleteContent( outputWriter );
            exportTablesContent( outputWriter );
            }
        finally {
            close();
            }
    }

    /**
     * Export comments about export and optionally define schema
     *
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    public void exportHeader( final Writer outputWriter )
        throws SQLException,
               IOException
    {
       ExportTable exportTable = new ExportTable( outputWriter );

       exportTable.println( "-- ---------------------------" );
       exportTable.print( "-- Export " );

       if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
           exportTable.print( "`" ).print( schema ).print( "` " );
           }

       exportTable.print( ": " ).println( getExportDateToString() );

       exportTable.println( "-- ---------------------------" );

       if( config.contains( Config.ADD_USE_SCHEMA ) ) {
           exportTable.print( "USE `" ).print( schema ).println( "`;" );
           }

       exportTable.println();
    }

    /**
     * Export table content
     *
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    public void exportDeleteContent( final Writer outputWriter )
        throws SQLException,
               IOException
    {
        ExportTable exportTable = new ExportTable( outputWriter );

        exportTable.println( "-- ---------------------------" );
        exportTable.println( "-- Cleanup" );
        exportTable.println( "-- ---------------------------" );

        for( int i = exportTables.length - 1; i>= 0; --i ) {
            exportTable.doExportDeleteData( exportTables[ i ] );
            }

        for( TableDescription table : exportTables ) {
            exportTable.doExportData( table );
            }

        exportTable.println( "-- done." );
        exportTable.println();
    }

    /**
     * Export table content
     *
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    public void exportTablesContent( final Writer outputWriter )
        throws SQLException,
               IOException
    {
        ExportTable exportTable = new ExportTable( outputWriter );

        try {
            for( TableDescription table : exportTables ) {
                exportTable.doExportData( table );
                }

            exportTable.println( "-- done." );
            exportTable.println();
            }
        finally {
            close();
            }
    }

    @Override
    public void close()
    {
        try { if(s != null) s.close(); } catch(Exception e) {}
    }

    private final class ExportTable
    {
        private Writer          out;
        private StringBuilder   q = new StringBuilder();

        public ExportTable( Writer out )
        {
            this.out = out;
        }

        public void doExportDeleteData(
                final TableDescription tableDescription
                )
            throws IOException
        {
            print( "DELETE FROM `" );

            if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
                print( schema ).print( "`.`" );
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
            if( s == null ) {
                s = connection.createStatement();
                }

            q.setLength( 0 );
            q.append(  "select * from `" );

            if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
                q.append( schema );
                q.append( "`.`" );
                }

            q.append( tableDesc.getName() );

            if( tableDesc.getWhereCondition() != null ) {
                q.append( "` WHERE " );
                q.append( tableDesc.getWhereCondition() );
                q.append( ";" );
                }
            else {
                q.append( "`;" );
                }

            s.execute( q.toString() );

            // Get result set
            ResultSet r = s.getResultSet();
            ResultSetMetaData rm = r.getMetaData(); // call before r.next() see note 4 above in JDBC hints

            println();
            println( "-- ---------------------------" );
            print( "-- Data for `"  );

            if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
                print( schema ).print( "`.`" );
                }

            print( tableDesc.getName() ).println( "`" );
            println( "-- ---------------------------" );

            if( config.contains( Config.ADD_DISABLE_AUTOCOMMIT ) ) {
                println( "SET AUTOCOMMIT=0;" );
                }

            if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
                print( "USE `" ).print( schema ).println( "`;" );
                }

            println( "" );
            println( "" );

            while( r.next() ) {
                print( "INSERT INTO `" );

                if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
                    print( schema );
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

    }
}
