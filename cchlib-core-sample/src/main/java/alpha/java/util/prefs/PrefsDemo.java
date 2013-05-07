package alpha.java.util.prefs;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import com.googlecode.cchlib.lang.StringHelper;

/**
  * Simple demonstration of the most common usage of the Java
  * Preferences API using the user and package based storage
  * node. This app uses the user tree to avoid collisions with
  * other users and uses the package name, as is conventional,
  * to avoid collisions with other applications in other packages.
  *
  * This is a simple command-line application. It stores only one
  * key/value pair, in which key is the string "PrefsValue".
  *
  * Argument 1 may be either "get", "clear", or "put".
  *
  * If "get", the value stored under the key "PrefsValue" is
  * fetched and displayed.
  *
  * If "clear", all prefs items for this package are cleared.
  *
  * If "put", the second command-line argument provides the value
  * to be stored. If the second argument is null, a suitable default
  * value is used.
  *
  * If "get" is requested the first time this application is run
  * or after a "clear" operation, a suitable default value is
  * returned.
  *
 **/
public class PrefsDemo 
{
  // Define constants for the three possible operations.
  private static final int GET   = 1;
  private static final int CLEAR = 2;
  private static final int PUT   = 3;

  /** 
   * Constructs the PrefsDemo application. 
   * @param args 
  **/
  public PrefsDemo( final String...args ) 
  {
    // Get the preferences node for this user and this package.
    Preferences prefs = Preferences.userNodeForPackage (getClass ());

    // Decode the command-line arguments.
    String command  = null;
    String param2   = null;
    String param3   = null;
    String newvalue = null;
    boolean export  = false;

    System.err.println( StringHelper.EMPTY );
    if (args.length == 0) {
        System.err.println ("No command given, assuming 'get'");
        command = "get";
        }
    else if (args.length == 1) {
        command = args[0];
        }
    else if (args.length == 2) {
        command = args[0];
        param2  = args[1];
        }
    else if (args.length == 3) {
        command = args[0];
        param2  = args[1];
        param3  = args[2];
        }

    // Turn the string commands into ints so they can be used
    // in a switch.
    int operation;
    if (command.equals ("get")) {
        operation = GET;
        }
    else if (command.equals ("clear")) {
        operation = CLEAR;
        }
    else if (command.equals ("put")) {
        operation = PUT;
        newvalue =
          param2!=null ? param2 : "you forgot the value, dummy";
        }
    else {
        System.err.printf(
                "Don't understand command '%s', assuming 'get'\n", 
                command
                );
        operation = GET;
        }

    // See if the 2nd parameter (for GET and CLEAR) or
    // 3rd parameter (for PUT) is the string "export".
    if (operation == GET || operation == CLEAR) {
        export = "export".equalsIgnoreCase (param2);
        }
    else if (operation == PUT) {
        export = "export".equalsIgnoreCase (param3);
        }

    // Do the operation requested by the command-line argument(s).
    switch (operation) {
        case CLEAR:
            System.err.println ("Clearing preferences");
            try {
                prefs.clear ();
                }
            catch( BackingStoreException bse ) {
                System.err.println (bse);
                }
            break;
            
        case GET:
            String prefs_value = prefs.get ("PrefsValue", "default value");
            System.err.printf(
                    "Got PrefsValue '%s' from prefs\n",
                    prefs_value
                    );
            break;
            
        case PUT:
            System.err.printf(
                    "Putting '%s' into prefs\n", 
                    newvalue
                    );
            prefs.put ("PrefsValue", newvalue);
            int num_puts = prefs.getInt ("num_puts", 0);
            prefs.putInt ("num_puts", num_puts+1);
            System.err.printf(
                    "Number of puts since clear is %d\n",
                    (num_puts+1)
                    );
            break;
        } // switch

        if( export ) {
            try {
                System.err.println( "export begin" );
                prefs.exportNode( System.out );
                System.err.println( "export end" );
                }
            catch( java.io.IOException ioe ) {
                System.err.println( ioe );
                }
            catch( BackingStoreException bse ) {
                System.err.println( bse );
                }
            }
    } // constructor

    @SuppressWarnings("unused")
    public static void main( String[] args )
    {
        new PrefsDemo( args );
        new PrefsDemo( "put", "something for prefs !" );
        new PrefsDemo( "get", "export" );
    } // main

} // class PrefsDemoApp