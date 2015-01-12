package cx.ath.choisnet.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.io.EmptyInputStream;
import cx.ath.choisnet.io.StreamCopyThread;

/**
 *
 *
 */
public class ExternalApp
{
    private static class OutputImpl implements Output
    {
        private final byte[] stdout;
        private final byte[] stderr;
        private final int returnCode;

        @Override
        public byte[] getStdOut()
        {
            return this.stdout;
        }

        @Override
        public byte[] getStdErr()
        {
            return this.stderr;
        }

        @Override
        public int getReturnCode()
        {
            return this.returnCode;
        }

        public OutputImpl(final byte[] stdout, final byte[] stderr, final int returnCode)
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
            final Process      process = Runtime.getRuntime().exec(command); // $codepro.audit.disable commandExecution
            final InputStream  procIn  = new BufferedInputStream(process.getInputStream());
            final InputStream  procErr = new BufferedInputStream(process.getErrorStream());
            final OutputStream procOut = new BufferedOutputStream(process.getOutputStream());
            ExternalAppException exception = null;
            int c;

            try {
                while((c = input.read()) >= 0) {
                    procOut.write(c);
                    }

                procOut.flush();
                procOut.close();
                }
            catch( final IOException e ) { // $codepro.audit.disable logExceptions
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
        catch(final java.io.IOException e) {
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
        try( final InputStream empty = new EmptyInputStream() ) {
            return ExternalApp.execute(command, empty , stdout, stderr);
            }
        catch( final IOException e ) {
            throw new RuntimeException( e );
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
            final String command,
            final InputStream input
            )
        throws ExternalAppException
    {
        final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        final ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        final int result = ExternalApp.execute(command, input, stdout, stderr);

        return new OutputImpl(stdout.toByteArray(), stderr.toByteArray(), result);
    }

    /**
     *
     * @param command
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     */
    public static final Output execute(final String command)
        throws ExternalAppException
    {
        try( final InputStream empty = new EmptyInputStream() ) {
            return ExternalApp.execute( command, empty );
            }
        catch( final IOException e ) {
            throw new RuntimeException( e );
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
            final Process          process       = Runtime.getRuntime().exec(command); // $codepro.audit.disable commandExecution
            final StreamCopyThread procOutThread = new StreamCopyThread("OutputStream", input, process.getOutputStream());
            final StreamCopyThread procInThread  = new StreamCopyThread("InputStream", process.getInputStream(), stdout);
            final StreamCopyThread procErrThread = new StreamCopyThread("ErrorStream", process.getErrorStream(), stderr);

            procOutThread.start();
            procInThread.start();
            procErrThread.start();

            exitValue = process.waitFor();

            procOutThread.cancel();
            procInThread.cancel();
            procErrThread.cancel();
            }
        catch( final IOException e ) {
            throw new ExternalAppException(
                "IOException while running extern application",
                e
                );
            }

        return exitValue;
    }
}
