package com.googlecode.cchlib.sql.mysql;

import cx.ath.choisnet.io.ParallelOutputStream;
import cx.ath.choisnet.util.ExternalApp;
import cx.ath.choisnet.util.ExternalAppException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.io.ConcateInputStream;

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
    private String mySQLExe;
    private String mySQLParams;
    private String mySQLDumpExe;
    private String mySQLDumpParams;

    /**
     * TODOC
     * @param mySQLExe
     * @param mySQLParams
     * @param mySQLDumpExe
     * @param mySQLDumpParams
     */
    public MySQLAdmin(
            String mySQLExe,
            String mySQLParams,
            String mySQLDumpExe,
            String mySQLDumpParams
            )
    {
        this.mySQLExe = mySQLExe;
        this.mySQLParams = mySQLParams;
        this.mySQLDumpExe = mySQLDumpExe;
        this.mySQLDumpParams = mySQLDumpParams;
    }

    /**
     * TODOC
     * @param outputStream
     * @throws MySQLAdminException
     */
    public void createSQLDumpFile(OutputStream outputStream)
        throws MySQLAdminException
    {
        String command = (new StringBuilder())
                            .append(mySQLDumpExe)
                            .append(mySQLDumpParams)
                            .toString();

        try {
            ExternalApp.execute(command, outputStream, System.err);
        }
        catch(ExternalAppException e) {
            throw new MySQLAdminException(e);
        }
    }

    /**
     * TODOC
     * @param outputFile
     * @throws MySQLAdminException
     */
    @SuppressWarnings("resource")
    public void createSQLDumpFile(File outputFile)
        throws MySQLAdminException
    {
        OutputStream outputStream = null;

        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

            createSQLDumpFile(outputStream);
            }
        catch(java.io.IOException e) {
            throw new MySQLAdminException(e);
            }
        finally {
            if(outputStream != null) {
                try { outputStream.close(); } catch(Exception ignore) { }
                }
            if(outputStream != null) {
                try { outputStream.close(); } catch(Exception ignore) { }
            }
        }
    }

    /**
     * TODOC
     * @param servletOuput
     * @param outputFile
     * @throws MySQLAdminException
     */
    @SuppressWarnings("resource")
    public void createSQLDumpFile(OutputStream servletOuput, File outputFile)
        throws MySQLAdminException
    {
        OutputStream fileOutputStream = null;

        try {
            if(servletOuput == null) {
                if(outputFile == null) {
                    throw new MySQLAdminException("servletOuput & outputFile nulls");
                    }
                createSQLDumpFile(outputFile);
                }

            if(outputFile == null) {
                createSQLDumpFile(servletOuput);
                }

            fileOutputStream = null;

            try {
                fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

                createSQLDumpFile(servletOuput, fileOutputStream);
                }
            catch( IOException e) {
                throw new MySQLAdminException(e);
                }
            }
        finally {
            if(fileOutputStream != null) {
                try { fileOutputStream.close(); } catch(Exception ignore) { }
                }
            }
    }

    /**
     * TODOC
     * @param servletOuput
     * @param fileOutputStream
     * @throws IOException 
     */
    public void createSQLDumpFile(
            OutputStream servletOuput,
            OutputStream fileOutputStream
            )
        throws IOException
    {
        ParallelOutputStream multipleOutputStream = new ParallelOutputStream(servletOuput, fileOutputStream);

        try {

            createSQLDumpFile( multipleOutputStream );
            }
        catch(IOException e) {
            throw new MySQLAdminException(e);
            }
        finally {
            multipleOutputStream.close();
            }
    }

    /**
     * TODOC
     * @param inputfile
     * @throws IOException 
     */
    public void applySQL(File inputfile)
        throws  IOException
    {
        InputStream is = new BufferedInputStream( new FileInputStream( inputfile ) );
        try {
            applySQL( is );
             }
        finally {
            is.close();
        }
    }

    /**
     * TODOC
     * @param sqlStream
     * @throws IOException 
     */
    public void applySQL(InputStream sqlStream)
        throws IOException
    {
        final String command = mySQLExe + mySQLParams;
        ConcateInputStream fullSQLStream = new ConcateInputStream(sqlStream, "quit\n");

        try {
            ExternalApp.execute(command, fullSQLStream, System.out, System.err);
            }
        catch(ExternalAppException e) {
            throw new MySQLAdminException(e);
            }
        finally {
            fullSQLStream.close();
            }
    }
}
