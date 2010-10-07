package cx.ath.choisnet.sql.mysql;

import cx.ath.choisnet.io.ConcateInputStream;
import cx.ath.choisnet.io.ParallelOutputStream;
import cx.ath.choisnet.util.ExternalApp;
import cx.ath.choisnet.util.ExternalAppException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p style="border:groove;">
 * <b>Warning:</b>
 * Insofar the code of this class comes from decompiling
 * my own code following the loss of source code, the use
 * of this class must do so under protest until I have
 * check its stability, it could be subject to significant
 * change.
 * <br/>
 * <br/>
 * <b>Attention:</b>
 * Dans la mesure où le code de cette classe est issue de
 * la décompilation de mon propre code, suite à la perte
 * du code source, l'utilisation de cette classe doit ce
 * faire sous toute réserve tant que je n'ai pas vérifier
 * sa stabilité, elle est donc sujette à des changements 
 * importants.
 * </p>
 *
 * @author Claude CHOISNET
 *
 */
public class MySQLAdmin
{
    private String mySQLExe;
    private String mySQLParams;
    private String mySQLDumpExe;
    private String mySQLDumpParams;

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

        if(outputStream != null) {
            try {
                outputStream.close();
            }
            catch(Exception ignore) { }
        }
//        break MISSING_BLOCK_LABEL_66;
//        Exception exception;
//        exception;

        if(outputStream != null) {
            try {
                outputStream.close();
            }
            catch(Exception ignore) { }
        }
    }

    public void createSQLDumpFile(OutputStream servletOuput, File outputFile)
        throws MySQLAdminException
    {
        OutputStream fileOutputStream;

        if(servletOuput == null) {
            if(outputFile == null) {
                throw new MySQLAdminException("servletOuput & outputFile nulls");
            }

            createSQLDumpFile(outputFile);
//            break MISSING_BLOCK_LABEL_108;
        }

        if(outputFile == null) {
            createSQLDumpFile(servletOuput);
//            break MISSING_BLOCK_LABEL_108;
        }

        fileOutputStream = null;

        try {
            fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

            createSQLDumpFile(servletOuput, fileOutputStream);
        }
        catch(java.io.IOException e) {
            throw new MySQLAdminException(e);
        }

        if(fileOutputStream != null) {
            try {
                fileOutputStream.close();
            }
            catch(Exception ignore) { }
        }
//        break MISSING_BLOCK_LABEL_108;
//        Exception exception;
//        exception;

        if(fileOutputStream != null) {
            try {
                fileOutputStream.close();
            }
            catch(Exception ignore) { }
        }
//        throw exception;
    }

    public void createSQLDumpFile(OutputStream servletOuput, OutputStream fileOutputStream)
        throws MySQLAdminException
    {
        ParallelOutputStream multipleOutputStream = null;

        try {
            multipleOutputStream = new ParallelOutputStream(servletOuput, fileOutputStream);

            createSQLDumpFile( multipleOutputStream );
        }
        catch(java.io.IOException e) {
            throw new MySQLAdminException(e);
        }
    }

    public void applySQL(File inputfile)
        throws  MySQLAdminException, 
                FileNotFoundException
    {
        applySQL(
            new BufferedInputStream(
                new FileInputStream(inputfile)
                )
            );
    }

    public void applySQL(InputStream sqlStream)
        throws MySQLAdminException
    {
        String command = (new StringBuilder()).append(mySQLExe).append(mySQLParams).toString();

        ConcateInputStream fullSQLStream = new ConcateInputStream(sqlStream, "quit\n");

        try {
            ExternalApp.execute(command, fullSQLStream, System.out, System.err);
        }
        catch(ExternalAppException e) {
            throw new MySQLAdminException(e);
        }
    }
}
