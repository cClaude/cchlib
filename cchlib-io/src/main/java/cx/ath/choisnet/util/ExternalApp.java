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
 * Allow to launch external process
 *
 * @since 3.01=
 *
 * @see Process
 * @see Runtime
 * @see Runtime#exec( String )
 * @see EmptyInputStream
 */
public class ExternalApp
{
    private static final String IO_ERROR_WHILE_RUNNING_EXTERN_APPLICATION = "IO error while running extern application";

    private static class OutputImpl implements Output
    {
        private final byte[] stdout;
        private final byte[] stderr;
        private final int returnCode;

        OutputImpl(
                final byte[] stdout,
                final byte[] stderr,
                final int    returnCode
                )
        {
            this.stdout     = stdout;
            this.stderr     = stderr;
            this.returnCode = returnCode;
        }

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
    }

    /**
     * NEEDDOC
     */
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
     * Executes the specified string command in a separate process.
     *
     * @param command
     *            Command line to execute
     * @param input
     *            Input stream
     * @param stdout
     *            Standard output stream
     * @param stderr
     *            Error output stream
     * @return the exit value for the subprocess. By convention, the
     *         value 0 indicates normal termination.
     * @throws ExternalAppException if any
     * @see Process#exitValue()
     * @see #run(String,InputStream,OutputStream,OutputStream)
     * @see EmptyInputStream
     */
    public static final int execute(
        final String        command,
        final InputStream   input,
        final OutputStream  stdout,
        final OutputStream  stderr
        ) throws ExternalAppException
    {
        int exitValue;

        try {
            final Process      process = Runtime.getRuntime().exec( command );
            final InputStream  procIn  = new BufferedInputStream( process.getInputStream() );
            final InputStream  procErr = new BufferedInputStream( process.getErrorStream() );
            final OutputStream procOut = new BufferedOutputStream( process.getOutputStream() );

            ExternalAppException exception = null;
            int c;

            try {
                while((c = input.read()) >= 0) {
                    procOut.write(c);
                    }

                procOut.flush();
                procOut.close();
                }
            catch( final IOException cause ) {
                exception = new ExternalAppException(
                        IO_ERROR_WHILE_RUNNING_EXTERN_APPLICATION,
                        cause
                        );
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
        catch( final IOException cause ) {
            throw new ExternalAppException(
                IO_ERROR_WHILE_RUNNING_EXTERN_APPLICATION,
                cause
                );
            }

        return exitValue;
    }

    /**
     * Executes the specified string command in a separate process.
     *
     * @param command
     *            Command line to execute
     * @param stdout
     *            Standard output stream
     * @param stderr
     *            Error output stream
     * @return the exit value for the subprocess. By convention, the
     *         value 0 indicates normal termination.
     * @throws ExternalAppException if any
     * @see Process#exitValue()
     * @see #run(String,InputStream,OutputStream,OutputStream)
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException
     */
    public static final int execute(
        final String        command,
        final OutputStream  stdout,
        final OutputStream  stderr
        ) throws ExternalAppException
    {
        try( final InputStream empty = new EmptyInputStream() ) {
            return ExternalApp.execute(command, empty , stdout, stderr);
            }
        catch( final IOException cause ) {
            throw new ExternalAppException( cause );
            }
    }

    /**
     * Executes the specified string command in a separate process.
     *
     * @param command
     *            Command line to execute
     * @param input
     *            Input stream
     * @return the exit value for the subprocess. By convention, the
     *         value 0 indicates normal termination.
     * @throws ExternalAppException if any
     * @see Process#exitValue()
     * @see #run(String,InputStream,OutputStream,OutputStream)
     * @see EmptyInputStream
     */
    public static final Output execute(
        final String      command,
        final InputStream input
        ) throws ExternalAppException
    {
        final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        final ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        final int result = ExternalApp.execute( command, input, stdout, stderr );

        return new OutputImpl(
                stdout.toByteArray(),
                stderr.toByteArray(),
                result
                );
    }

    /**
     * Executes the specified string command in a separate process.
     *
     * @param command
     *            Command line to execute
     * @return the exit value for the subprocess. By convention, the
     *         value 0 indicates normal termination.
     * @throws ExternalAppException if any
     * @see Process#exitValue()
     * @see #run(String,InputStream,OutputStream,OutputStream)
     * @see EmptyInputStream
     */
    public static final Output execute( final String command )
        throws ExternalAppException
    {
        try( final InputStream empty = new EmptyInputStream() ) {
            return ExternalApp.execute( command, empty );
            }
        catch( final IOException cause ) {
            throw new ExternalAppException( cause );
            }
    }

    /**
     * Executes the specified string command in a separate process.
     *
     * @param command
     *            Command line to execute
     * @param input
     *            Input stream
     * @param stdout
     *            Standard output stream
     * @param stderr
     *            Error output stream
     * @return the exit value for the subprocess.
     *         By convention, the value 0 indicates normal termination.
     * @throws ExternalAppException if any
     * @throws InterruptedException if any
     */
    @SuppressWarnings({"squid:S1160"})
    public static final int run(
        final String        command,
        final InputStream   input,
        final OutputStream  stdout,
        final OutputStream  stderr
        ) throws ExternalAppException, InterruptedException
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
                IO_ERROR_WHILE_RUNNING_EXTERN_APPLICATION,
                e
                );
            }

        return exitValue;
    }
}
