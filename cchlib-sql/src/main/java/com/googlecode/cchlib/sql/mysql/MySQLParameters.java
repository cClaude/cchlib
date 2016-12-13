package com.googlecode.cchlib.sql.mysql;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Configuration for {@link MySQL#getURL(String, int, java.util.Set)}
 *
 * @since 4.2
 */
public enum MySQLParameters implements MySQLParametersConfig
{
    /**
     *  Handle : Value '0000-00-00 00:00:00' can be represented as {@link Timestamp}
     *  <p>
     *  Required for compatibility with MySQL 3.0
     */
    ZERO_DATE_TIME_BEHAVIOR( "zeroDateTimeBehavior=convertToNull" ),

    /**
     * Required for full debug
     */
    EXPLAIN_SLOW_QUERIES( "explainSlowQueries=true" ),

    /**
     * Required for full debug
     */
    LOG_SLOW_QUERIES( "logSlowQueries=true" ),

    /**
     * Required for full debug
     */
    USE_USAGE_ADVISOR( "useUsageAdvisor=true" ),

    /**
     * Required for full debug
     */
    GATHER_PERF_METRICS( "gatherPerfMetrics=true" ),

    /**
     * Required for full debug
     */
    PROFILE_SQL( "profileSQL=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    EMPTY_STRINGS_CONVERT_TO_ZERO( "emptyStringsConvertToZero=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    JDBC_COMPLIANT_TRUNCATION( "jdbcCompliantTruncation=false" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    NO_DATETIME_STRING_SYNC( "noDatetimeStringSync=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    NULL_CATALOG_MEANS_CURRENT( "nullCatalogMeansCurrent=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    NULL_NAME_PATTERN_MATCHES_ALL( "nullNamePatternMatchesAll=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    TRANSFORMED_BIT_IS_BOOLEAN( "transformedBitIsBoolean=false" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    DONT_TRACK_OPEN_RESOURCES( "dontTrackOpenResources=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    USE_SERVER_PREP_STMTS( "useServerPrepStmts=false" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    AUTO_CLOSEP_STMT_STREAMS( "autoClosePStmtStreams=true" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    PROCESS_ESCAPE_CODES_FOR_PREP_STMTS( "processEscapeCodesForPrepStmts=false" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    USE_FAST_DATE_PARSING( "useFastDateParsing=false" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    POPULATE_INSERT_ROW_WITH_DEFAULT_VALUES( "populateInsertRowWithDefaultValues=false" ),

    /**
     *  Required for compatibility with MySQL 3.0
     */
    USE_DIRECT_ROW_UNPACK( "useDirectRowUnpack=false" ),
    ;

    /**
     * Configuration required for compatibility with MySQL 3.0
     */
    public static final Set<MySQLParameters> MYSQL3 = Collections.unmodifiableSet(
        EnumSet.of(
            AUTO_CLOSEP_STMT_STREAMS,
            DONT_TRACK_OPEN_RESOURCES,
            EMPTY_STRINGS_CONVERT_TO_ZERO,
            JDBC_COMPLIANT_TRUNCATION,
            NO_DATETIME_STRING_SYNC,
            NULL_CATALOG_MEANS_CURRENT,
            NULL_NAME_PATTERN_MATCHES_ALL,
            POPULATE_INSERT_ROW_WITH_DEFAULT_VALUES,
            PROCESS_ESCAPE_CODES_FOR_PREP_STMTS,
            TRANSFORMED_BIT_IS_BOOLEAN,
            USE_DIRECT_ROW_UNPACK,
            USE_FAST_DATE_PARSING,
            USE_SERVER_PREP_STMTS,
            ZERO_DATE_TIME_BEHAVIOR
            )
        );

    /**
     * Alias for {@link ZERO_DATE_TIME_BEHAVIOR}
     */
    public static final MySQLParameters HANDLE_0000_00_00_00_00_00_TIMESTAMP
        = ZERO_DATE_TIME_BEHAVIOR;

    private String parameterConfig;

    private MySQLParameters( final String parameterConfig )
    {
        this.parameterConfig = parameterConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParameterConfig()
    {
        return this.parameterConfig;
    }
}
