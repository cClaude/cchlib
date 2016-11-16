package com.googlecode.cchlib.sql.mysql;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.io.ConcateInputStream;
import cx.ath.choisnet.io.ParallelOutputStream;
import cx.ath.choisnet.util.ExternalApp;
import cx.ath.choisnet.util.ExternalAppException;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * </p>
 */
public class MySQLAdmin
{
    private final String mySQLExe;
    private final String mySQLParams;
    private final String mySQLDumpExe;
    private final String mySQLDumpParams;

    /**
     * NEEDDOC
     * @param mySQLExe
     * @param mySQLParams
     * @param mySQLDumpExe
     * @param mySQLDumpParams
     */
    public MySQLAdmin(
            final String mySQLExe,
            final String mySQLParams,
            final String mySQLDumpExe,
            final String mySQLDumpParams
            )
    {
        this.mySQLExe = mySQLExe;
        this.mySQLParams = mySQLParams;
        this.mySQLDumpExe = mySQLDumpExe;
        this.mySQLDumpParams = mySQLDumpParams;
    }

    /**
     * NEEDDOC
     * @param outputStream
     * @throws MySQLAdminException
     */
    public void createSQLDumpFile(final OutputStream outputStream)
        throws MySQLAdminException
    {
        final String command = (new StringBuilder())
                            .append(this.mySQLDumpExe)
                            .append(this.mySQLDumpParams)
                            .toString();

        try {
            ExternalApp.execute(command, outputStream, System.err);
        }
        catch(final ExternalAppException e) {
            throw new MySQLAdminException(e);
        }
    }

    /**
     * NEEDDOC
     * @param outputFile
     * @throws MySQLAdminException
     */
    public void createSQLDumpFile(final File outputFile)
        throws MySQLAdminException
    {
        try( final OutputStream outputStream = new BufferedOutputStream( new FileOutputStream( outputFile ) ) ) {
            createSQLDumpFile(outputStream);
            }
        catch(final IOException e) {
            throw new MySQLAdminException(e);
            }
    }

    /**
     * NEEDDOC
     * @param servletOuput
     * @param outputFile
     * @throws MySQLAdminException
     */
    public void createSQLDumpFile(final OutputStream servletOuput, final File outputFile)
        throws MySQLAdminException
    {
        if( servletOuput == null ) {
            if( outputFile == null ) {
                throw new MySQLAdminException( "servletOuput & outputFile nulls" );
            }
            createSQLDumpFile( outputFile );
        }

        if( outputFile == null ) {
            createSQLDumpFile( servletOuput );
        }

        try (final OutputStream fileOutputStream = new BufferedOutputStream( new FileOutputStream( outputFile ) )) {
            createSQLDumpFile( servletOuput, fileOutputStream );
        }
        catch( final IOException e ) {
            throw new MySQLAdminException( e );
        }
    }

    /**
     * NEEDDOC
     * @param servletOuput
     * @param fileOutputStream
     * @throws IOException
     */
    public void createSQLDumpFile(
            final OutputStream servletOuput,
            final OutputStream fileOutputStream
            )
        throws IOException
    {
        try( final ParallelOutputStream multipleOutputStream = new ParallelOutputStream( servletOuput, fileOutputStream ) ) {

            createSQLDumpFile( multipleOutputStream );
            }
        catch(final IOException e) {
            throw new MySQLAdminException(e);
            }
    }

    /**
     * NEEDDOC
     * @param inputfile
     * @throws IOException
     */
    public void applySQL(final File inputfile)
        throws  IOException
    {
        try( final InputStream is = new BufferedInputStream( new FileInputStream( inputfile ) ) ) {
            applySQL( is );
            }
    }

    /**
     * NEEDDOC
     * @param sqlStream
     * @throws IOException
     */
    public void applySQL(final InputStream sqlStream)
        throws IOException
    {
        final String command = this.mySQLExe + this.mySQLParams;

        try( final ConcateInputStream fullSQLStream = new ConcateInputStream( sqlStream, "quit\n" ) ) {
            ExternalApp.execute(command, fullSQLStream, System.out, System.err);
            }
        catch(final ExternalAppException e) {
            throw new MySQLAdminException(e);
            }
    }
}
