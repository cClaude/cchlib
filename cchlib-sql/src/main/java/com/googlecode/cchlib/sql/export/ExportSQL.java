package com.googlecode.cchlib.sql.export;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.EnumSet;

import com.googlecode.cchlib.sql.SQLCloseException;

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
    //private Statement                   s = null;
    private ExportSQLPrinter _exporter   = null;

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
     * {@link Config#ADD_DISABLE_AUTOCOMMIT} and {@link Config#ADD_USE_SCHEMA}
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
        ExportSQLPrinter export = getExportSQLPrinter( outputWriter );

        export.println( "-- ---------------------------" );
        export.print( "-- Export " );

       if( config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
           export.print( "`" ).print( schema ).print( "` " );
           }

       export.print( ": " ).println( getExportDateToString() );

       export.println( "-- ---------------------------" );

       if( config.contains( Config.ADD_USE_SCHEMA ) ) {
           export.print( "USE `" ).print( schema ).println( "`;" );
           }

       export.println();
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
        ExportSQLPrinter export = getExportSQLPrinter( outputWriter );

        export.println( "-- ---------------------------" );
        export.println( "-- Cleanup" );
        export.println( "-- ---------------------------" );

        for( int i = exportTables.length - 1; i>= 0; --i ) {
            export.doExportDeleteData( exportTables[ i ] );
            }

//        for( TableDescription table : exportTables ) {
//            export.doExportData( table );
//            }

        export.println( "-- Cleanup done." );
        export.println();
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
        ExportSQLPrinter export = getExportSQLPrinter( outputWriter );

        export.println( "-- ---------------------------" );
        export.println( "-- Export tables" );
        export.println( "-- ---------------------------" );

        try {
            for( TableDescription table : exportTables ) {
                export.doExportData( table );
                }

            export.println( "-- Export tables done." );
            export.println();
            }
        finally {
            close();
            }
    }

    protected String getSchemaName()
    {
        return this.schema;
    }

    protected EnumSet<Config> getConfigSet()
    {
        return this.config;
    }

    protected Connection getConnection()
    {
        return this.connection;
    }

    private ExportSQLPrinter getExportSQLPrinter( final Writer outputWriter )
    {
        if( _exporter == null ) {
            _exporter = new ExportSQLPrinter( this, outputWriter );
            }

        return _exporter;
    }

    @Override
    public void close() throws SQLCloseException
    {
        if( _exporter != null ) {
        	_exporter.close();
        	} 
    }

}
