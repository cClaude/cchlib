/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/ExternalApp.java
** Description   :
** Encodage      : ANSI
**
**  1.02.000 2005.04.19 Claude CHOISNET
**  1.10.000 2005.04.28 Claude CHOISNET
**                      Ajout de la gestion des charsets
**  1.51.002 2005.04.28 Claude CHOISNET
**                      Mise en place d'un premier jet de doc.
**  2.01.000 2005.09.30 Claude CHOISNET
**                      La methode execute(String, Writer, Writer) est
**                      'deprecated'
**  2.02.034 2005.12.26 Claude CHOISNET
**                      Ajout de execute( String, InputStream )
**                      Ajout de execute( String )
**                      La methode execute(String,Reader,String,Writer,String,Writer,String)
**                      est 'deprecated'
**  3.01.011 2005.12.26 Claude CHOISNET
**                      La methode run(String,InputStream,OutputStream,OutputStream)
**                      est reintroduite, car elle travaille differement
**                      de la methode execute(String,InputStream,OutputStream,OutputStream)
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.ExternalApp
**
*/
package cx.ath.choisnet.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import cx.ath.choisnet.io.EmptyInputStream;
import cx.ath.choisnet.io.StreamCopyThread;

/**
** <p>Version simplifiee de la methode {@link Runtime#exec(String)}</p>
**
**
** @author Claude CHOISNET
** @version 3.01.011
**
** @see Process
** @see Runtime
** @see Runtime#exec( String )
** @see EmptyInputStream
*/
public class ExternalApp
{

/**
** Execute la commande donnee
**
** @param command   Chaine contenant la commande e executer
** @param input     Flux d'entree
** @param stdout    Flux de sortie standard
** @param stderr    Flux d'erreur
**
** @return un int correspondant e la valeur retournee par la methode
**         exitValue() processus.
**
** @see Process#exitValue()
** @see #run(String,InputStream,OutputStream,OutputStream)
** @see EmptyInputStream
*/
public final static int execute( // ---------------------------------------
    final String        command,
    final InputStream   input,
    final OutputStream  stdout,
    final OutputStream  stderr
    )
    throws ExternalAppException
{
 int exitValue;

 try {
    final Process         process = Runtime.getRuntime().exec( command );
    final InputStream     procIn  = new BufferedInputStream( process.getInputStream() );
    final InputStream     procErr = new BufferedInputStream( process.getErrorStream() );
    final OutputStream    procOut = new BufferedOutputStream( process.getOutputStream() );
    ExternalAppException exception = null;
    int             c;

    try {
        while( (c = input.read()) >= 0 ) {
            procOut.write( c );
            }

        procOut.flush();
        procOut.close();
        }
    catch( final IOException e ) {
        exception = new ExternalAppException(
            "IOException while running extern application",
            e
            );
        }

    //
    // Traite la sortie standard
    //
    while( (c = procIn.read()) >= 0 ) {
        stdout.write( c );
        }

    //
    // Traite la sortie d'erreur
    //
    while( (c = procErr.read()) >= 0 ) {
        stderr.write( c );
        }

    if( exception != null ) {
        throw exception;
        }

    exitValue = process.exitValue();
    }
 catch( final IOException e ) {
    throw new ExternalAppException(
        "IOException while running extern application",
        e
        );
    }

 return exitValue;
}

/**
** Execute la commande donnee, avec un flux d'entree vide, cette methode
** est basee sur {@link #execute(String,InputStream,OutputStream,OutputStream)}
**
** @param command   commande e executer
** @param stdout    Flux de sortie standard
** @param stderr    Flux d'erreur
**
** @return un int correspondant e la valeur retournee par la methode
**         exitValue() processus.
**
** @see #execute(String,InputStream,OutputStream,OutputStream)
** @see EmptyInputStream
*/
@SuppressWarnings("resource")
public final static int execute( // ---------------------------------------
    final String        command,
    final OutputStream  stdout,
    final OutputStream  stderr
    )
    throws ExternalAppException
{
 return ExternalApp.execute( command, new EmptyInputStream(), stdout, stderr );
}

    /**
    ** Resultats de l'execution.
    **
    ** @since 2.02.034
    **
    ** @see #execute( String )
    ** @see #execute( String, InputStream )
    */
    public static interface Output
    {
        /**
        ** Tableau de byte correspondant au flux stdout brut.
        **
        ** @return un byte[] correspondant au flux stdout de la commande execute.
        */
        public byte[] getStdOut(); // - - - - - - - - - - - - - - - - - - -

        /**
        ** Tableau de byte correspondant au flux sdterr brut.
        **
        ** @return un byte[] correspondant au flux sdterr de la commande execute.
        */
        public byte[] getStdErr(); // - - - - - - - - - - - - - - - - - - -

        /**
        ** Entier correspondant au code erreur retour par l'application.
        **
        ** @return un int contenant la valeur retourne par l'application.
        **
        ** @see Process#exitValue()
        */
        public int getReturnCode(); //- - - - - - - - - - - - - - - - - - -
    }

    /**
    ** Implementation privee de Output
    **
    ** @since 2.02.034
    */
    private static class OutputImpl
        implements Output
    {
        private final byte[] stdout;
        private final byte[] stderr;
        private final int returnCode;

        public OutputImpl( // - - - - - - - - - - - - - - - - - - - - - - -
                    final byte[]  stdout,
                    final byte[]  stderr,
                    final int     returnCode
                    )
        {
            this.stdout     = stdout;
            this.stderr     = stderr;
            this.returnCode = returnCode;
        }

        @Override
        public byte[] getStdOut() //- - - - - - - - - - - - - - - - - - - -
        {
            return stdout;
        }

        @Override
        public byte[] getStdErr() //- - - - - - - - - - - - - - - - - - - -
        {
            return stderr;
        }

        @Override
        public int getReturnCode() // - - - - - - - - - - - - - - - - - - -
        {
            return returnCode;
        }
    }

/**
** Execute la commande donnee, avec un flux d'entree vide, cette methode
** est basee sur {@link #execute(String,InputStream,OutputStream,OutputStream)}
**
** @param command   Commande e executer
** @param input     Flux e envoyer e la commande.
**
** @return un Object Output correspondant aux resultats du traitement
**
** @since 2.02.034
**
*/
public final static Output execute( // ------------------------------------
    final String        command,
    final InputStream   input
    )
    throws ExternalAppException
{
 final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
 final ByteArrayOutputStream stderr = new ByteArrayOutputStream();

 final int result = execute( command, input, stdout, stderr );

 return new OutputImpl( stdout.toByteArray(), stderr.toByteArray(), result );
}

/**
** Execute la commande donnee, avec un flux d'entree vide, cette methode
** est basee sur {@link #execute(String,InputStream,OutputStream,OutputStream)}
**
** @param command   Commande e executer
**
** @return un Object Output correspondant aux resultats du traitement
**
** @since 2.02.034
**
*/
@SuppressWarnings("resource")
public final static Output execute( // ------------------------------------
    final String command
    )
    throws ExternalAppException
{
 return execute( command, new EmptyInputStream() );
}

/**
** Execute la commande donnee, commande equivalente e
** {@link #execute(String,InputStream,OutputStream,OutputStream)}
** mais e la difference de cette derniere les flux sont copies
** e l'aide de {@link StreamCopyThread} permettant un traitement
** assynchrone des flux. Par ailleurs la fin de la teche est
** traite e l'aide de {@link Process#waitFor()}.
**
** @param command   Chaine contenant la commande e executer
** @param input     Flux d'entree
** @param stdout    Flux de sortie standard
** @param stderr    Flux d'erreur
**
** @return un int correspondant e la valeur retournee par la methode
**         {@link Process#waitFor()}. processus.
**
** @see Process#waitFor()
** @see #execute(String,InputStream,OutputStream,OutputStream)
** @see StreamCopyThread
** @see EmptyInputStream
*/
public final static int run( // -------------------------------------------
    final String        command,
    final InputStream   input,
    final OutputStream  stdout,
    final OutputStream  stderr
    )
    throws
        ExternalAppException,
        InterruptedException
// Code a garder ! utiliser dans JMIB ! et pas que !!!
{
 int exitValue;

 try {
    final Process process = Runtime.getRuntime().exec( command );

    final StreamCopyThread procOutThread  = new StreamCopyThread( "OutputStream"  , input, process.getOutputStream() );
    final StreamCopyThread procInThread   = new StreamCopyThread( "InputStream"   , process.getInputStream(), stdout );
    final StreamCopyThread procErrThread  = new StreamCopyThread( "ErrorStream"   , process.getErrorStream(), stderr );

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

/**
** Excute la commande donnee
**
** @param command           commande e executer
** @param input
** @param inputCharsetName
** @param stdout
** @param stdoutCharsetName
** @param stderr
** @param stderrCharsetName
**
** @return un int correspondant e la valeur retournee par la methode
**         exitValue() processus.
**
** @see Process#exitValue()
**
** @deprecated use execute(String,InputStream,OutputStream,OutputStream)
** <br />
** Le code :<br />
** <pre>
**  Writer stdout = ...
**  Writer stderr = ...
**
**  ExternalApp.execute(
**      cmd,
**      new cx.ath.choisnet.io.EmptyReader(), "Cp850",
**      stdout, "Cp850",
**      stderr, "Cp850"
**      );
** </pre>
** Peut etre remplace par<br />
** <pre>
**  Writer stdout = ...
**  Writer stderr = ...
**
**  java.io.ByteArrayOutputStream stdout0 = new java.io.ByteArrayOutputStream();
**  java.io.ByteArrayOutputStream stderr0 = new java.io.ByteArrayOutputStream();
**
**  ExternalApp.execute( "cmd /c " + cmd, stdout0, stderr0 );
**
**  stdout.write( stdout0.toString( "Cp850" ) );
**  stderr.write( stderr0.toString( "Cp850" ) );
** </pre>
** ou mieux utiliser (solution mixte) :
** <pre>
**  Writer stdout = ...
**  Writer stderr = ...
**
**  final ByteArrayOutputStream stderr0         = new ByteArrayOutputStream();
**  final PipedInputStream      streamStdOutIn  = new PipedInputStream();
**  final PipedOutputStream     streamStdOutOut = new PipedOutputStream( streamStdOutIn );
**  final WriterCopyThread      copyStdout
**              = new WriterCopyThread(
**                      new InputStreamReader( streamStdOutIn, "Cp850" ),
**                      stdout
**                      );
**
**  copyStdout.start();
**
**  ExternalApp.execute( command, streamStdOutOut, stderr0 );
**
**  stderr.write( stderr0.toString( "Cp850" ) );
** </pre>
**
*/
@Deprecated
public final static int execute( // ---------------------------------------
    final String    command,
    final Reader    input,
    final String    inputCharsetName,
    final Writer    stdout,
    final String    stdoutCharsetName,
    final Writer    stderr,
    final String    stderrCharsetName
    )
    throws ExternalAppException
{
 int exitValue;

 try {
    final Process process = Runtime.getRuntime().exec( command );
    final Writer  procOut = new OutputStreamWriter( process.getOutputStream() , inputCharsetName  );
    final Reader  procIn  = new InputStreamReader(  process.getInputStream()  , stdoutCharsetName );
    final Reader  procErr = new InputStreamReader(  process.getErrorStream()  , stderrCharsetName );

    ExternalAppException exception = null;
    int             c;

    try {
        while( (c = input.read()) >= 0 ) {
            procOut.write( c );
            }

        procOut.flush();
        procOut.close();
        }
    catch( final IOException e ) {
        exception = new ExternalAppException(
            "IOException while running extern application",
            e
            );
        }

    //
    // Traite la sortie standard
    //
    while( (c = procIn.read()) >= 0 ) {
        stdout.write( c );
        }

    //
    // Traite la sortie d'erreur
    //
    while( (c = procErr.read()) >= 0 ) {
        stderr.write( c );
        }

    if( exception != null ) {
        throw exception;
        }

    exitValue = process.exitValue();
    }
 catch( final IOException e ) {
    throw new ExternalAppException(
        "IOException while running extern application",
        e
        );
    }

 return exitValue;
}

/**
** Excute la commande donnee, avec un flux d'entree vide
**
** @param command   commande e executer
** @param stdout
** @param stderr
**
** @return un int correspondant e la valeur retournee par la methode
**         exitValue() processus.
**
** @see #execute( String, Reader, String, Writer, String, Writer, String )
**
** @deprecated use execute(String,InputStream,OutputStream,OutputStream)
*/
@SuppressWarnings("resource")
@Deprecated
public final static int execute( // ---------------------------------------
    final String    command,
    final Writer    stdout,
    final Writer    stderr
    )
    throws ExternalAppException
{
 return ExternalApp.execute( command, new  cx.ath.choisnet.io.EmptyReader(), "Cp850", stdout, "Cp850", stderr, "Cp850" );
}

} // class

