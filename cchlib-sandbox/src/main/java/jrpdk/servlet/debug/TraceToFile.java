/*
** $VER: TraceToFile.java
*/
package jrpdk.servlet.debug;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
**
**
** @author Claude CHOISNET
** @version 1.00 30/01/2001
*/
public class TraceToFile
{
private static TraceToFile  trace;
private PrintWriter         output = null;

static {
 try {
    trace = new TraceToFile();
    }
 catch( IOException ignore ) {
    System.err.println( ignore );

    ignore.printStackTrace( System.err );
    }
};


private TraceToFile() throws IOException // -------------------------------
{
 open( "C:\\RalfServlet.log" );
}

private void open( String filename ) throws java.io.IOException // --------
{
 this.output =
    new PrintWriter(
        new FileWriter( filename, true /* append */ )
        );

// SimpleDate date = new SimpleDate();
// SimpleTime time = new SimpleTime();

// this.output.println( "-- open -- " + date + " - " + time );
 this.output.println( "-- open -- " );
}

protected void close() // -------------------------------------------------
{
 if( this.output != null ) {
    this.output.println( "-- close --" );
    this.output.flush();
    this.output.close();
    this.output = null;
    }
}

private void private_println( String s ) // -------------------------------
{
 this.output.println( s );
 this.output.flush();
}

@Override
protected void finalize() throws Throwable // -----------------------------
{
 this.close();

 super.finalize();
}


public static void println( String s ) // ---------------------------------
{
 try {
    trace.private_println( s );
    }
 catch( NullPointerException e ) {
    System.err.println( s );
    }
}

} // class

