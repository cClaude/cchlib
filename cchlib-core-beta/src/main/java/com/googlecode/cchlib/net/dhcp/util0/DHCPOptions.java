package com.googlecode.cchlib.net.dhcp.util0;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * This class represents a hash table of options for a DHCP message. Its purpose is to ease option handling such as add,
 * remove, or change.
 *
 */
public class DHCPOptions {
    // DHCP Message Types
    public static final int            DHCPDISCOVER         = 1;
    public static final int            DHCPOFFER            = 2;
    public static final int            DHCPREQUEST          = 3;
    public static final int            DHCPDECLINE          = 4;
    public static final int            DHCPACK              = 5;
    public static final int            DHCPNAK              = 6;
    public static final int            DHCPRELEASE          = 7;
    public static final int            DHCPINFORM           = 8;

    // DHCP Option Identifiers
    public static final int            DHCPSUBNETMASK       = 1;
    public static final int            DHCPROUTER           = 3;
    public static final int            DHCPHOSTNAME         = 12;
    public static final int            DHCPREQUESTIP        = 50;
    public static final int            DHCPMESSAGETYPE      = 53;
    public static final int            DHCPSERVERIDENTIFIER = 54;
    public static final int            DHCPPARAMREQLIST     = 55;
    public static final int            DHCPIPADDRLEASETIME  = 51;
    public static final int            DHCPCLIENTIDENTIFIER = 61;

    private static final int           MAX_OPTION_SIZE      = 320;

    // private LinkedList<byte[]> options = new LinkedList<byte[]>();
    private final Hashtable<Integer, byte[]> options;

    public DHCPOptions()
    {
        options = new Hashtable<Integer, byte[]>();
    }

    public DHCPOptions( final byte[] options )
    {
        this.options = new Hashtable<Integer, byte[]>();
        this.internalize( options );
    }

    public byte[] getOption( final int optionID )
    {
        return getOption( Integer.valueOf( optionID ) );
    }

    public byte[] getOption( final Integer optionID )
    {
        return options.get( optionID );
    }

    public void setOption( final int optionID, final byte[] option )
    {
        setOption( Integer.valueOf( optionID ), option );
    }

    public void setOption( final Integer optionID, final byte[] option )
    {
        options.put( optionID, option );
    }

    public byte[] getOptionData( final int optionID )
    {
        final byte[] option = options.get( optionID );
        final byte[] optionData = new byte[option.length - 2];
        for( int i = 0; i < optionData.length; i++ ) {
            optionData[ i ] = option[ 2 + i ];
        }
        return optionData;
    }

    public void setOptionData( final int optionID, final byte[] optionData )
    {
        final byte[] option = new byte[2 + optionData.length];
        option[ 0 ] = (byte)optionID;
        option[ 1 ] = (byte)optionData.length;
        for( int i = 0; i < optionData.length; i++ ) {
            option[ 2 + i ] = optionData[ i ];
        }
        options.put( optionID, option );
    }

    public String printOption( final int optionID, final boolean header )
    {
        String output;
        if( options.get( optionID ) != null ) {
            final byte[] option = options.get( optionID );
            switch( optionID ) {
                case DHCPHOSTNAME:
                    output = DHCPUtility.printString( option ).substring( 2 );
                    break;
                case DHCPREQUESTIP:
                    output = DHCPUtility.ip4ToString( option[ 2 ], option[ 3 ], option[ 4 ], option[ 5 ] );
                    break;
                case DHCPMESSAGETYPE:
                    output = printMessageType( option[ 2 ] );
                    break;
                case DHCPSERVERIDENTIFIER:
                    output = DHCPUtility.ip4ToString( option[ 2 ], option[ 3 ], option[ 4 ], option[ 5 ] );
                    break;
                case DHCPCLIENTIDENTIFIER:
                    if( option[ 1 ] == 7 && option[ 2 ] == DHCPMessage.ETHERNET10MB ) {
                        output = DHCPUtility.macToString( option[ 3 ], option[ 4 ], option[ 5 ], option[ 6 ], option[ 7 ], option[ 8 ] );
                    } else if( option[ 2 ] == 0 ) {
                        output = DHCPUtility.printString( option ).substring( 2 );
                    } else {
                        output = "";
                        final int head = (header ? 0 : 2);
                        for( int i = head; i < option.length; i++ ) {
                            output += option[ i ] + (i == option.length - 1 ? "" : ",");
                        }
                    }
                    break;
                default:
                    output = "";
                    final int head = (header ? 0 : 2);
                    for( int i = head; i < option.length; i++ ) {
                        output += option[ i ] + (i == option.length - 1 ? "" : ",");
                    }
                    break;
            }
        } else {
            output = "<Empty>";
        }
        // System.out.println(output);
        return output;
    }

    private String printMessageType( final byte b )
    {
        final String str;
        switch( b ) {
            case 1:
                str = "DHCPDISCOVER";
                break;
            case 2:
                str = "DHCPOFFER";
                break;
            case 3:
                str = "DHCPREQUEST";
                break;
            case 4:
                str = "DHCPDECLINE";
                break;
            case 5:
                str = "DHCPACK";
                break;
            case 6:
                str = "DHCPNAK";
                break;
            case 7:
                str = "DHCPRELEASE";
                break;
            case 8:
                str = "DHCPINFORM";
                break;
            default:
                str = "Unknown DHCP Message Type: " + b;
        }

        return str;
    }

    public String printOptions()
    {
        String str = "";
        for( final byte[] option : options.values() ) {
            str += printOption( option[ 0 ], true );
        }
        return str;
    }

    @Override
    public String toString()
    {
        String output = "";
        for( final byte[] option : options.values() ) {
            output += "optionID: " + option[ 0 ] + " optionLength: " + option[ 1 ] + " optionData: ";
            /* for (int i = 2; i < option.length; i++) { output += option[i] + (i == option.length - 1 ? "\n" : ", "); } */
            output += printOption( option[ 0 ], false ) + System.getProperty( "line.separator" );
        }
        return output;
    }

    protected void internalize( byte[] options )
    {
        // clear hash
        this.options.clear();

        // trim options
        options = trim( options );

        // get size
        final int totalBytes = options.length;
        assert (totalBytes >= 4 && totalBytes <= MAX_OPTION_SIZE);

        // read magic cookie
        final byte[] cookie = new byte[4];
        cookie[ 0 ] = options[ 0 ];
        cookie[ 1 ] = options[ 1 ];
        cookie[ 2 ] = options[ 2 ];
        cookie[ 3 ] = options[ 3 ];
        final String magicCookie = DHCPUtility.ip4ToString( cookie );
        // System.out.println("cookie received: " + magicCookie);
        assert (magicCookie.compareTo( "99.130.83.99" ) == 0) : "Cookie received \"" + magicCookie + "\"";

        // copy bytes
        int bytes = 0;
        for( int i = 4; i < totalBytes; i += bytes ) {
            for( int p = 0; p < 99999999; p++ ) {
                ;
            }
            // System.out.println("debug test1: iteration = " + i + " bytes = "
            // + bytes + " totalBytes = " + totalBytes);
            bytes = 1;

            // if option
            if( options[ i ] != (byte)0 && options[ i ] != (byte)255 ) {
                // length of the option specified in length field
                final int optionLength = options[ i + 1 ];

                // new byte array of size in length field
                final byte[] option = new byte[optionLength];

                // debug
                assert optionLength >= 1 : "option length: " + optionLength;

                // for each option data byte
                for( int j = 0; j < optionLength; j++ ) {
                    // set data
                    option[ j ] = options[ i + 2 + j ];
                }
                bytes = 2 + optionLength; // opcode(1b) + length(1b) +
                                          // data.length
                // set option data in this dhcpOptions object
                this.setOptionData( options[ i ], option );

            } else if( options[ i ] == (byte)255 ) { // end options
                assert (i == totalBytes - 1);
            } else if( options[ i ] == (byte)0 ) { // padding
                // keep reading if padding op code
                System.out.println( "padding op code" );
            } else {
                System.out.println( "malformed option, code out of range (0-255). Code: " + options[ i ] );
            }
        }

        System.out.println( "internalization complete: " + this.options.size() + " options entered" );
    }

    // returns option byte array with removed trailing 0's
    private byte[] trim( final byte[] options )
    {
        int i;
        for( i = options.length - 1; options[ i ] == 0; i-- ) {
            ;
        }
        assert DHCPUtility.byteToHex( options[ i ] ).compareToIgnoreCase( "FF" ) == 0 : "options[i]: \"" + DHCPUtility.byteToHex( options[ i ] ) + "\"";
        final int trimedLength = i + 1;
        final byte[] trimed = Arrays.copyOf( options, trimedLength );
        return trimed;
    }

    public byte[] externalize()
    {

        // get size
        int totalBytes = 0;
        for( final byte[] option : this.options.values() ) {
            totalBytes += option.length;
        }

        // account for magic cookie and end option code
        totalBytes += (4 + 1); // 4-magic cookie + 1-end options code

        final byte[] options = new byte[totalBytes];

        // add magic cookie
        options[ 0 ] = (byte)99;
        options[ 1 ] = (byte)130;
        options[ 2 ] = (byte)83;
        options[ 3 ] = (byte)99;

        // copy bytes
        int bytes = 4;
        for( final byte[] option : this.options.values() ) {
            for( int i = 0; i < option.length; i++ ) {
                options[ bytes + i ] = option[ i ];
            }
            bytes += option.length;
        }

        // add end options code
        options[ bytes ] = (byte)255;

        return options;
    }
}
