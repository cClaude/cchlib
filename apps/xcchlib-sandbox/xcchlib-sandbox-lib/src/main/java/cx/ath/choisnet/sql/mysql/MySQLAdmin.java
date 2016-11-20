/*
 ** -----------------------------------------------------------------------
 ** Nom           : cx/ath/choisnet/sql/mysql/MySQLAdmin.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.30.010 2005.05.15 Claude CHOISNET
 **                      Nettoyage du code,
 ** -----------------------------------------------------------------------
 **
 ** cx.ath.choisnet.sql.mysql.MySQLAdmin
 **
 */
package cx.ath.choisnet.sql.mysql;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cx.ath.choisnet.io.ConcateInputStream;
import cx.ath.choisnet.io.ParallelOutputStream;
import cx.ath.choisnet.util.ExternalApp;
import cx.ath.choisnet.util.ExternalAppException;

/**
 ** <P>
 * Classe prenant en charge l'excution des requetes SQL vers une base de donnée
 ** MySQL en utilisant les commandes
 * d'administration de la base.
 * </P>
 **
 ** @author Claude CHOISNET
 ** @version 1.30.010
 */
public class MySQLAdmin {

    private final String mySQLExe;
    private final String mySQLParams;
    private final String mySQLDumpExe;
    private final String mySQLDumpParams;

    /**
     **
     ** @since 1.30.010
     */
    public MySQLAdmin( // -----------------------------------------------------
            final String mySQLExe,
            final String mySQLParams,
            final String mySQLDumpExe,
            final String mySQLDumpParams )
    {
        this.mySQLExe = mySQLExe;
        this.mySQLParams = mySQLParams;
        this.mySQLDumpExe = mySQLDumpExe;
        this.mySQLDumpParams = mySQLDumpParams;
    }

    /**
     ** @param outputStream
     **
     ** @since 1.49
     */
    public void createSQLDumpFile( // -----------------------------------------
            final OutputStream outputStream ) throws MySQLAdminException
    {
        final String command = mySQLDumpExe + mySQLDumpParams;

        try {
            ExternalApp.execute( command, outputStream, System.err );
        }
        catch( final cx.ath.choisnet.util.ExternalAppException e ) {
            throw new MySQLAdminException( e );
        }
    }

    /**
     **
     **
     ** @param outputFile
     **
     */
    public void createSQLDumpFile( // -----------------------------------------
            final File outputFile ) throws MySQLAdminException
    {
        OutputStream outputStream = null;

        try {
            outputStream = new BufferedOutputStream( new FileOutputStream( outputFile ) );

            createSQLDumpFile( outputStream );
        }
        catch( final java.io.IOException e ) {
            throw new MySQLAdminException( e );
        }
        finally {
            if( outputStream != null ) {
                try {
                    outputStream.close();
                }
                catch( final Exception ignore ) {
                    // Ignore
                }
            }
        }
    }

    /**
     ** <P>
     * NOTE: Les paramtéres <code>servletOuput</code> et <code>outputFile</code> ne doivent pas étre null en méme temps.
     * </P>
     **
     ** @param servletOuput
     *            Flux de la servlet ou null
     ** @param outputFile
     *            Fichier de sortie ou null
     **
     */
    public void createSQLDumpFile( // -----------------------------------------
            final OutputStream servletOuput, final File outputFile ) throws MySQLAdminException
    {
        if( servletOuput == null ) {
            if( outputFile == null ) {
                throw new MySQLAdminException( "servletOuput & outputFile nulls" );
            } else {
                createSQLDumpFile( outputFile );
            }
        } else {
            if( outputFile == null ) {
                createSQLDumpFile( servletOuput );
            } else {
                OutputStream fileOutputStream = null;

                try {
                    fileOutputStream = new BufferedOutputStream( new FileOutputStream( outputFile ) );

                    createSQLDumpFile( servletOuput, fileOutputStream );
                }
                catch( final java.io.IOException e ) {
                    throw new MySQLAdminException( e );
                }
                finally {
                    if( fileOutputStream != null ) {
                        try {
                            fileOutputStream.close();
                        }
                        catch( final Exception ignore ) {
                            // Ignore
                        }
                    }
                }
            }
        }

    }

    public void createSQLDumpFile( // -----------------------------------------
            final OutputStream servletOuput, final OutputStream fileOutputStream ) throws MySQLAdminException
    {
        try( ParallelOutputStream multipleOutputStream = new ParallelOutputStream( servletOuput, fileOutputStream ) ) {
            createSQLDumpFile( multipleOutputStream );
        }
        catch( final IOException e ) {
            throw new MySQLAdminException( e );
        }
    }

    public void applySQL( final File inputfile ) throws IOException
    {
        try( BufferedInputStream intput = new BufferedInputStream( new FileInputStream( inputfile ) ) ) {
            applySQL( intput );
        }
    }

    /**
     *
     * @param sqlStream
     * @throws MySQLAdminException
     */
    public void applySQL( final InputStream sqlStream ) throws MySQLAdminException
    {
        final String command = mySQLExe + mySQLParams;

        try (final ConcateInputStream fullSQLStream = new ConcateInputStream( sqlStream, "quit\n" )) {

            try {
                ExternalApp.execute( command, fullSQLStream, System.out, System.err );
            }
            catch( final ExternalAppException e ) {
                throw new MySQLAdminException( e );
            }
        }
        catch( final IOException e ) {
            throw new MySQLAdminException( e );
        }
    }

} // class
