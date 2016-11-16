package com.googlecode.cchlib.sql.export;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import com.googlecode.cchlib.sql.SQLCloseException;

/**
 * Create a SQL script to export values from tables.
 */
public class ExportSQL implements Closeable
{
    private static final int NO_LIMIT_ROWS = -1;
    private static final String SEPARATOR_LINE = "-- ---------------------------";

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
    private final TableDescription[]    tables2Export;
    private final String                schema;
    private final Date                  exportDate;
    private final EnumSet<Config>       config;

    // FIXME this is not thread safe
    private ExportSQLPrinter _exporter   = null;

    /**
     * Initialize ExportSQL object
     *
     * @param connection Connection to database.
     * @param schemaName Schema name of database.
     * @param config {@link Set}({@link EnumSet}= of {@link Config} to configure output.
     * @param exportDate {@link Date} to identify SQL export (just in comment section),
     *        if null use current date.
     * @param tables Array of TableDescription (order could be important
     *        while running result SQL - see {@link TableDescriptionHelper}
     */
    public ExportSQL(
            final Connection            connection,
            final String                schemaName,
            final Set<Config>           config,
            final Date                  exportDate,
            final TableDescription...   tables
            )
    {
        this.connection     = connection;
        this.schema         = schemaName;
        this.tables2Export  = tables;

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
     * @param connection
     *      Connection to database.
     * @param schemaName
     *      Schema name of database. If null, do not add schema in export.
     * @param exportDate
     *      {@link Date} to identify SQL export (just in comment section),
     *      if null use current date.
     * @param tables
     *      Array of TableDescription (order could be important
     *      while running result SQL - see {@link TableDescriptionHelper}
     */
    public ExportSQL(
            final Connection            connection,
            final String                schemaName,
            final Date                  exportDate,
            final TableDescription...   tables
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
                tables
                );
    }

    /**
     * Returns export Date (could be overwrite)
     * @return export Date
     */
    public Date getExportDate()
    {
        return this.exportDate;
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
    @SuppressWarnings("squid:S1160")
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
     * Export all content according to configuration.
     * <p>
     * Call first {@link #exportHeader(Writer)},
     * then {@link #exportDeleteContent(Writer)},
     * then {@link #exportTablesContent(Writer)}
     * and then {@link #close()}.
     * </P>
     *
     * @param outputWriter
     *      output for SQL export
     * @param limitRows
     *      Max entries to export per table
     * @throws SQLException
     *      if a error occurred while reading database
     * @throws IOException
     *      if a error occurred while write to outputWriter
     */
    @SuppressWarnings("squid:S1160")
    public void exportAll(
            final Writer outputWriter,
            final int    limitRows
            )
        throws SQLException,
               IOException
    {
        try {
            exportHeader( outputWriter );
            exportDeleteContent( outputWriter );
            exportTablesContent( outputWriter, limitRows );
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
    @SuppressWarnings("squid:S1160")
    public void exportHeader( final Writer outputWriter )
        throws SQLException,
               IOException
    {
        try( final ExportSQLPrinter export = getExportSQLPrinter( outputWriter, NO_LIMIT_ROWS ) ) {
            export.println( SEPARATOR_LINE );
            export.print( "-- Export " );

            if( this.config.contains( Config.ADD_PREFIX_SCHEMA ) ) {
                export.print( "`" ).print( this.schema ).print( "` " );
            }

            export.print( ": " ).println( getExportDateToString() );

            export.println( SEPARATOR_LINE );

            if( this.config.contains( Config.ADD_USE_SCHEMA ) ) {
                export.print( "USE `" ).print( this.schema ).println( "`;" );
            }

            export.println();
        }
    }

    /**
     * Export table content
     *
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    @SuppressWarnings("squid:S1160")
    public void exportDeleteContent( final Writer outputWriter )
        throws SQLException,
               IOException
    {
        try( final ExportSQLPrinter export = getExportSQLPrinter( outputWriter, NO_LIMIT_ROWS ) ) {
            export.println( SEPARATOR_LINE );
            export.println( "-- Cleanup" );
            export.println( SEPARATOR_LINE );

            for( int i = this.tables2Export.length - 1; i >= 0; --i ) {
                export.doExportDeleteData( this.tables2Export[ i ] );
            }

            export.println( "-- Cleanup done." );
            export.println();
        }
    }

    /**
     * Export table content
     *
     * @param outputWriter output for SQL export
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    @SuppressWarnings("squid:S1160")
    public void exportTablesContent( final Writer outputWriter )
        throws SQLException,
               IOException
    {
        exportTablesContent( outputWriter, NO_LIMIT_ROWS );
    }

    /**
     * Export table content
     *
     * @param outputWriter
     *      output for SQL export
     * @param limitRows
     *      Max entries to export per table
     * @throws SQLException if a error occurred while reading database
     * @throws IOException if a error occurred while write to outputWriter
     */
    @SuppressWarnings("squid:S1160")
    public void exportTablesContent(
            final Writer outputWriter,
            final int    limitRows
            )
        throws SQLException,
               IOException
    {
         try( final ExportSQLPrinter export = getExportSQLPrinter( outputWriter, limitRows ) ) {
            export.println( SEPARATOR_LINE );
            export.println( "-- Export " + this.tables2Export.length + " tables" );
            export.println( SEPARATOR_LINE );

            for( final TableDescription table : this.tables2Export ) {
                export.doExportData( table );
                }

            export.println( "-- Export tables done." );
            export.println();
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

    private ExportSQLPrinter getExportSQLPrinter( //
        final Writer outputWriter, //
        final int    limitRows //
        )
    {
        if( this._exporter == null ) {
            this._exporter = new ExportSQLPrinter( this, outputWriter, limitRows );
            }

        return this._exporter;
    }

    @Override
    public void close() throws SQLCloseException
    {
        if( this._exporter != null ) {
            this._exporter.close();
            }
    }

}
