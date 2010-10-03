package cx.ath.choisnet.util;

import cx.ath.choisnet.io.EmptyInputStream;
import cx.ath.choisnet.io.StreamCopyThread;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class ExternalApp
{
    private static class OutputImpl implements Output
    {
        private byte[] stdout;
        private byte[] stderr;
        private int returnCode;

        public byte[] getStdOut()
        {
            return stdout;
        }

        public byte[] getStdErr()
        {
            return stderr;
        }

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
        public abstract byte[] getStdOut();
        public abstract byte[] getStdErr();
        public abstract int getReturnCode();
    }

    public ExternalApp()
    {

    }

    public static final int execute(String command, InputStream input, OutputStream stdout, OutputStream stderr)
        throws cx.ath.choisnet.util.ExternalAppException
    {
        int exitValue;

        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStream procIn = new BufferedInputStream(process.getInputStream());
            InputStream procErr = new BufferedInputStream(process.getErrorStream());
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
            catch(java.io.IOException e) {
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
            throw new ExternalAppException("IOException while running extern application", e);
        }

        return exitValue;
    }

    public static final int execute(String command, OutputStream stdout, OutputStream stderr)
        throws cx.ath.choisnet.util.ExternalAppException
    {
        return ExternalApp.execute(command, new EmptyInputStream(), stdout, stderr);
    }

    public static final Output execute(String command, java.io.InputStream input)
        throws cx.ath.choisnet.util.ExternalAppException
    {
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();

        int result = ExternalApp.execute(command, input, stdout, stderr);

        return new OutputImpl(stdout.toByteArray(), stderr.toByteArray(), result);
    }

    public static final Output execute(String command)
        throws cx.ath.choisnet.util.ExternalAppException
    {
        return ExternalApp.execute(command, new EmptyInputStream() );
    }

    public static final int run(
            String command,
            InputStream input,
            OutputStream stdout,
            OutputStream stderr
            )
        throws cx.ath.choisnet.util.ExternalAppException, InterruptedException
    {
        int exitValue;

        try {
            Process process = Runtime.getRuntime().exec(command);
            StreamCopyThread procOutThread = new StreamCopyThread("OutputStream", input, process.getOutputStream());
            StreamCopyThread procInThread = new StreamCopyThread("InputStream", process.getInputStream(), stdout);
            StreamCopyThread procErrThread = new StreamCopyThread("ErrorStream", process.getErrorStream(), stderr);

            procOutThread.start();
            procInThread.start();
            procErrThread.start();
            exitValue = process.waitFor();

            procOutThread.cancel();
            procInThread.cancel();
            procErrThread.cancel();
        }
        catch(java.io.IOException e) {
            throw new ExternalAppException("IOException while running extern application", e);
        }

        return exitValue;
    }

//    /**
//     * @param command
//     * @param input
//     * @param inputCharsetName
//     * @param stdout
//     * @param stdoutCharsetName
//     * @param stderr
//     * @param stderrCharsetName
//     * @return  xx
//     * @throws cx.ath.choisnet.util.ExternalAppException
//     * @deprecated Method execute is deprecated
//     */
//    public static final int execute(
//        String command,
//        Reader input,
//        String inputCharsetName,
//        Writer stdout,
//        String stdoutCharsetName,
//        Writer stderr,
//        String stderrCharsetName
//        )
//        throws cx.ath.choisnet.util.ExternalAppException
//    {
//        int exitValue;
//
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//            Writer procOut  = new OutputStreamWriter(process.getOutputStream(), inputCharsetName);
//            Reader procIn   = new InputStreamReader(process.getInputStream(), stdoutCharsetName);
//            Reader procErr  = new InputStreamReader(process.getErrorStream(), stderrCharsetName);
//
//            ExternalAppException exception = null;
//            int c;
//
//            try {
//                while((c = input.read()) >= 0) {
//                    procOut.write(c);
//                }
//
//                procOut.flush();
//                procOut.close();
//            }
//            catch(java.io.IOException e) {
//                exception = new ExternalAppException("IOException while running extern application", e);
//            }
//
//            while((c = procIn.read()) >= 0) {
//                stdout.write(c);
//            }
//
//            while((c = procErr.read()) >= 0) {
//                stderr.write(c);
//            }
//
//            if(exception != null) {
//                throw exception;
//            }
//
//            exitValue = process.exitValue();
//        }
//        catch(java.io.IOException e) {
//            throw new ExternalAppException("IOException while running extern application", e);
//        }
//
//        return exitValue;
//    }

//    /**
//     * @param command
//     * @param stdout
//     * @param stderr
//     * @return xxx
//     * @throws cx.ath.choisnet.util.ExternalAppException
//     * @deprecated Method execute is deprecated
//     */
//    public static final int execute(
//        String command,
//        Writer stdout,
//        Writer stderr
//        )
//        throws cx.ath.choisnet.util.ExternalAppException
//    {
//        return ExternalApp.execute(command, new EmptyReader(), "Cp850", stdout, "Cp850", stderr, "Cp850");
//    }
}
