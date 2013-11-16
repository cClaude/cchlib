package cx.ath.choisnet.util;

import cx.ath.choisnet.io.StreamCopyThread;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.io.EmptyInputStream;

/**
 *
 *
 */
public class ExternalApp
{
    private static class OutputImpl implements Output
    {
        private byte[] stdout;
        private byte[] stderr;
        private int returnCode;

        @Override
        public byte[] getStdOut()
        {
            return stdout;
        }

        @Override
        public byte[] getStdErr()
        {
            return stderr;
        }

        @Override
        public int getReturnCode()
        {
            return returnCode;
        }

        public OutputImpl(byte[] stdout, byte[] stderr, int returnCode)
        {
            this.stdout = stdout;
            this.stderr = stderr;
            this.returnCode = returnCode;
        }
    }

    public static interface Output
    {
        byte[] getStdOut();
        byte[] getStdErr();
        int getReturnCode();
    }

    private ExternalApp()
    {
        //All static
    }

    /**
     *
     * @param command
     * @param input
     * @param stdout
     * @param stderr
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     */
    public static final int execute(
            final String        command,
            final InputStream   input,
            final OutputStream  stdout,
            final OutputStream  stderr
            )
        throws ExternalAppException
    {
        int exitValue;

        try {
            Process      process = Runtime.getRuntime().exec(command); // $codepro.audit.disable commandExecution
            InputStream  procIn  = new BufferedInputStream(process.getInputStream());
            InputStream  procErr = new BufferedInputStream(process.getErrorStream());
            OutputStream procOut = new BufferedOutputStream(process.getOutputStream());
            ExternalAppException exception = null;
            int c;

            try {
                while((c = input.read()) >= 0) {
                    procOut.write(c);
                    }

                procOut.flush();
                procOut.close();
                }
            catch( IOException e ) { // $codepro.audit.disable logExceptions
                exception = new ExternalAppException("IOException while running extern application", e);
                }

            while((c = procIn.read()) >= 0){
                stdout.write(c);
                }

            while((c = procErr.read()) >= 0) {
                stderr.write(c);
                }

            if(exception != null) {
                throw exception;
                }

            exitValue = process.exitValue();
            }
        catch(java.io.IOException e) {
            throw new ExternalAppException(
                "IOException while running extern application",
                e
                );
            }

        return exitValue;
    }

    /**
     *
     * @param command
     * @param stdout
     * @param stderr
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     */
    public static final int execute(
            final String        command,
            final OutputStream  stdout,
            final OutputStream  stderr
            )
        throws ExternalAppException
    {
        final InputStream empty = new EmptyInputStream();

        try {
            return ExternalApp.execute(command, empty , stdout, stderr);
            }
        finally {
            try {
                empty.close();
                }
            catch( IOException e ) {
                throw new RuntimeException( e );
                }
            }
    }

    /**
     *
     * @param command
     * @param input
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     */
    public static final Output execute(
            String command,
            InputStream input
            )
        throws ExternalAppException
    {
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        int result = ExternalApp.execute(command, input, stdout, stderr);

        return new OutputImpl(stdout.toByteArray(), stderr.toByteArray(), result);
    }

    /**
     *
     * @param command
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     */
    public static final Output execute(String command)
        throws ExternalAppException
    {
        final InputStream empty = new EmptyInputStream();

        try {
            return ExternalApp.execute( command, empty );
            }
        finally {
            try {
                empty.close();
                }
            catch( IOException e ) {
                throw new RuntimeException( e );
                }
            }
    }

    /**
     *
     * @param command
     * @param input
     * @param stdout
     * @param stderr
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     * @throws InterruptedException
     */
    public static final int run(
            final String        command,
            final InputStream   input,
            final OutputStream  stdout,
            final OutputStream  stderr
            )
        throws ExternalAppException, InterruptedException
    {
        int exitValue;

        try {
            Process          process       = Runtime.getRuntime().exec(command); // $codepro.audit.disable commandExecution
            StreamCopyThread procOutThread = new StreamCopyThread("OutputStream", input, process.getOutputStream());
            StreamCopyThread procInThread  = new StreamCopyThread("InputStream", process.getInputStream(), stdout);
            StreamCopyThread procErrThread = new StreamCopyThread("ErrorStream", process.getErrorStream(), stderr);

            procOutThread.start();
            procInThread.start();
            procErrThread.start();

            exitValue = process.waitFor();

            procOutThread.cancel();
            procInThread.cancel();
            procErrThread.cancel();
            }
        catch( IOException e ) {
            throw new ExternalAppException(
                "IOException while running extern application",
                e
                );
            }

        return exitValue;
    }
}
