package alpha.java.util.prefs;

import java.io.IOException;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import com.googlecode.cchlib.VisibleForTesting;

/**
 * Simple demonstration of the most common usage of the Java Preferences API
 * using the user and package based storage node. This app uses the user tree
 * to avoid collisions with other users and uses the package name, as is
 * conventional, to avoid collisions with other applications in other packages.
 *
 * This is a simple command-line application. It stores only one key/value pair,
 * in which key is the string "PrefsValue".
 *
 * Argument 1 may be either "get", "clear", or "put".
 *
 * If "get", the value stored under the key "PrefsValue" is fetched and displayed.
 *
 * If "clear", all prefs items for this package are cleared.
 *
 * If "put", the second command-line argument provides the value to be stored.
 * If the second argument is null, a suitable default value is used.
 *
 * If "get" is requested the first time this application is run or after a "clear"
 * operation, a suitable default value is returned.
 *
 */
@SuppressWarnings({
    "squid:S106", // Standard outputs
    "squid:S2275" // %n instead of /n
    })
public class PrefsDemoApp
{
    // Define constants for the three possible operations.
    private enum Operation
    {
        GET  ( "get"  , (prefs, value) -> doGet( prefs ) ),
        CLEAR( "clear", (prefs, value) -> doClear( prefs ) ),
        PUT  ( "put"  , Operation::doPut ),
        ;
        private String                                 command;
        private BiFunction<Preferences,String,String> function;

        private Operation(
            final String                                command,
            final BiFunction<Preferences,String,String> function
            )
        {
            this.command  = command;
            this.function = function;
        }

        public String doOperation( final Preferences prefs, final String value )
        {
            return this.function.apply( prefs, value );
        }

        private static String doClear( final Preferences prefs )
        {
            System.err.println( "Clearing preferences" );

            try {
                prefs.clear();
            }
            catch( final BackingStoreException bse ) {
                System.err.println( bse );
            }

            return null;
        }

        private static String doGet( final Preferences prefs )
        {
            final String prefsValue = prefs.get( "PrefsValue", "default value" );

            System.err.printf( "Got PrefsValue '%s' from prefs\n", prefsValue );

            return prefsValue;
        }

        private static String doPut( final Preferences prefs, final String newValue )
        {
            System.err.printf( "Putting '%s' into prefs\n", newValue );
            prefs.put( "PrefsValue", newValue );

            final int numPuts = prefs.getInt( "num_puts", 0 );
            prefs.putInt( "numPuts", numPuts + 1 );
            System.err.printf( "Number of puts since clear is %d\n", Integer.valueOf( numPuts + 1 ) );

            return newValue;
        }

        static Operation getOperation( final String command )
        {
            for( final Operation operation : Operation.values() ) {
                if( operation.command.equalsIgnoreCase( command ) ) {
                    return operation;
                }
            }

            System.err.printf( "Don't understand command '%s', assuming 'get'\n", command );

            return Operation.GET;
        }
    }

    static class Parameters
    {
        private static final String EXPORT = "export";

        private final Operation operation;
        private final String    value;
        private final boolean   shouldExport;

        public Parameters( final Operation operation, final String value, final boolean export )
        {
            this.operation    = operation;
            this.value        = value;
            this.shouldExport = export;
        }

        public Operation getOperation()
        {
            return this.operation;
        }

        public String getValue()
        {
            return this.value;
        }

        public boolean shouldExport()
        {
            return this.shouldExport;
        }

        public static Parameters getParameters( final String[] args )
        {
            final Operation operation;
            final  String   value;
            final boolean   export;

            if( args.length > 0 ) {
                operation = Operation.getOperation( args[ 0 ] );

                // See if the 2nd parameter (for GET and CLEAR) or
                // 3rd parameter (for PUT) is the string "export".
                if( operation.equals( Operation.GET ) || operation.equals( Operation.CLEAR ) ) {
                    value  = null;
                    export = (args.length > 1) && EXPORT.equals( args[ 1 ] );
                } else {
                    value  = (args.length > 1) ? args[ 1 ] : "you forgot the value, dummy";
                    export = (args.length > 2) && EXPORT.equals( args[ 2 ] );
                }
            } else {
                System.err.println( "No command given, assuming 'get'" );
                operation = Operation.GET;
                value     = null;
                export    = false;
            }

            return new Parameters( operation, value, export );
        }
    }

    private final Parameters parameters;
    private final Operation  operation;
    private final String     result;

    /**
     * Constructs the PrefsDemo application.
     *
     * @param args CLI Parameters
     */
    @VisibleForTesting
    PrefsDemoApp( final String... args )
    {
        // Get the preferences node for this user and this package.
        final Preferences prefs = Preferences.userNodeForPackage( getClass() );

        System.err.println();

        // Decode the command-line arguments.
        this.parameters = Parameters.getParameters( args );
        this.operation  = this.parameters.getOperation();

        // Do the operation requested by the command-line argument(s).
        this.result = this.operation.doOperation( prefs, this.parameters.getValue() );

        if( this.parameters.shouldExport() ) {
            try {
                System.err.println( "export begin" );
                prefs.exportNode( System.out );
                System.err.println( "export end" );
            }
            catch( final IOException ioe ) {
                System.err.println( ioe );
            }
            catch( final BackingStoreException bse ) {
                System.err.println( bse );
            }
        }
    } // constructor

    public Parameters getParameters()
    {
        return this.parameters;
    }

    public String getOperationName()
    {
        return this.operation.name();
    }

    public String getResult()
    {
        return this.result;
    }

    public static void main( final String[] args )
    {
        new PrefsDemoApp( args );
        new PrefsDemoApp( "put", "something for prefs ! (" + new Date() + ')' );
        new PrefsDemoApp( "get", "export" );
    }
}
