package com.googlecode.cchlib.dhcp;

/*
 ** -----------------------------------------------------------------------
 **  3.02.014 2006.06.21 Claude CHOISNET - Version initiale
 **                      Adapte du code de Jason Goldschmidt and Nick Stone
 **                      edu.bucknell.net.JDHCP.DHCPOptions
 **                      http://www.eg.bucknell.edu/~jgoldsch/dhcp/
 **                      et base sur les RFCs 1700, 2131 et 2132
 **  3.02.015 2006.06.22 Claude CHOISNET
 **                      implemente java.io.Serializable
 ** -----------------------------------------------------------------------
 */

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

/**
 **
 ** <pre>
 * * CHAMP   OCTETS  DESCRIPTION
 * *
 * * op          1   Code operation du message:/
 * *                 type du message. 1 = BOOTREQUEST, 2 = BOOTREPLY
 * *
 * * htype       1   Adresse materielle, voir la section ARP dans le RFC "Assigned Numbers" ;
 * *                 par ex., '1' = Ethernet 10Mb.
 * *
 * * hlen        1   Longueur de l'adresse materielle (par ex. '6' for Ethernet 10Mb).
 * *
 * * hops        1   Mis e zero par le client, utilise de maniere optionnelle par les agents
 * *                 de relais quand on demarre via un agent de relais
 * *
 * * xid         4   Identifiant de transaction, un nombre aleatoire choisi par le client,
 * *                 utilise par le client et le serveur pour associer les messages et les
 * *                 reponses entre un client et un serveur
 * *
 * * secs        2   Rempli par le client, les secondes s'ecoulent depuis le processus
 * *                 d'acquisition ou de renouvellement d'adresse du client
 * *
 * * flags       2   Drapeaux (voir figure 2).
 * *
 * * ciaddr      4   Adresse IP des clients, rempli seulement si le client est dans un etat
 * *                 AFFECTe, RENOUVELLEMENT ou REAFFECTATION
 * *                 et peut repondre aux requetes ARP
 * *
 * * yiaddr      4   'votre' (client) adresse IP.
 * *
 * * siaddr      4   Adresse IP du prochain serveur e utiliser pour le processus de demarrage;
 * *                 retournee par le serveur dans DHCPOFFER et DHCPACK.
 * *
 * * giaddr      4   Adresse IP de l'agent de relais, utilisee pour demarrer via un agent de relais.
 * *
 * * chaddr     16   Adresse materielle des clients (Address MAC).
 * *
 * * sname      64   Nom d'hete du serveur optionnel, chaene de caracteres terminee par
 * *                 un caractere nul.
 * *
 * * fichier   128   Nom du fichier de demarrage, chaene terminee par un caractere nul;
 * *                 nom "generic" ou nul dans le DHCPDISCOVER,
 * *                 nom du repertoire explicite dans DHCPOFFER.
 * *
 * * options   var   Champ de parametres optionnels.
 **
 ** </pre>
 **
 ** @author Jason Goldschmidt
 ** @author Claude CHOISNET
 */
public class DHCPOptions implements Serializable {
    private static final String DHCP_OPTIONS_PROPERTIES = "DHCPOptions.properties";

    /** serialVersionUID */
    private static final long                serialVersionUID          = 1L;

    public static final byte[]               MAGIC_COOKIE              = { 0x63, (byte)0x82, 0x53, 0x63 };

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 REQUESTED_IP              = 50;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 LEASE_TIME                = 51;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 MESSAGE_TYPE              = 53;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 T1_TIME                   = 58;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 T2_TIME                   = 59;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 CLASS_ID                  = 60;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     **
     */
    public static final byte                 CLIENT_ID                 = 61;

    /**
     ** <p>
     * DHCP option constants
     * </p>
     **
     ** End option
     */
    public static final byte                 END_OPTION                = (byte)0xFF;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPDISCOVER Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPDISCOVER = 1;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPOFFER Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPOFFER    = 2;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPREQUEST Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPREQUEST  = 3;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPDECLINE Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPDECLINE  = 4;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPACK Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPACK      = 5;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPNAK Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPNAK      = 6;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPRELEASE Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPRELEASE  = 7;

    /**
     ** <p>
     * DHCP option value for {@link #MESSAGE_TYPE} (RFC 2132)
     * </p>
     **
     ** Code for DHCPINFORM Message
     */
    public static final byte                 MESSAGE_TYPE_DHCPINFORM   = 8;

    private final Map<Byte, DHCPOptionEntry> optionsTable;

    public DHCPOptions() // ---------------------------------------------------
    {
        this.optionsTable = new HashMap<Byte, DHCPOptionEntry>();
    }

    /**
     ** Changes an existing option to new value
     **
     ** @param option
     *            The node's option code
     ** @param value
     *            Content of node option
     **
     */
    public void setOption( final byte option, final byte[] value ) // ---------
    {
        setOption( option, new DHCPOptionEntry( value ) );
    }

    /**
     ** Changes an existing option to new value
     **
     ** @param option
     *            The node's option code
     ** @param value
     *            Content of node option (1 byte)
     **
     */
    public void setOption( final byte option, final byte value ) // -----------
    {
        setOption( option, new DHCPOptionEntry( value ) );
    }

    /**
     ** Changes an existing option to new value
     **
     ** @param option
     *            The node's option code
     ** @param value
     *            Content of node option
     **
     */
    public void setOption( // -------------------------------------------------
            final byte option, final DHCPOptionEntry value )
    {
        this.optionsTable.put( new Byte( option ), value );
    }

    /**
     ** Changes or Append all options found in anOtherDHCPOptions
     **
     ** @param anOtherDHCPOptions
     *            collection to append
     **
     ** @see #setOptions(Collection)
     */
    public void setOptions( // ------------------------------------------------
            final DHCPOptions anOtherDHCPOptions )
    {
        setOptions( anOtherDHCPOptions.optionsTable.entrySet() );
    }

    /**
     **
     ** @param aCollection
     *            collection to append
     **
     ** @see #setOptions(DHCPOptions)
     */
    public void setOptions( // ------------------------------------------------
            final Collection<Map.Entry<Byte, DHCPOptionEntry>> aCollection )
    {
        for( final Map.Entry<Byte, DHCPOptionEntry> entry : aCollection ) {
            setOption( entry.getKey().byteValue(), entry.getValue().getClone() );
        }
    }

    /**
     ** Clear internal options list
     */
    public void clear() // ----------------------------------------------------
    {
        this.optionsTable.clear();
    }

    /**
     ** Removes option with specified bytecode
     **
     ** @param code
     *            The code of option to be removed
     */
    public void removeOption( final Byte code ) // ----------------------------
    {
        this.optionsTable.remove( code );
    }

    /**
     ** Removes option with specified bytecode
     **
     ** @param code
     *            The code of option to be removed
     */
    public void removeOption( final byte code ) // ----------------------------
    {
        removeOption( new Byte( code ) );
    }

    /**
     ** Fetches value of option by its option code
     **
     ** @param code
     *            The node's option code
     **
     ** @return a valid DHCPOptionEntry containing the value of option code. null is returned if option is not set.
     */
    public DHCPOptionEntry getDHCPOptionEntry( final byte code ) // -----------
    {
        return this.optionsTable.get( new Byte( code ) );
    }

    /**
     ** Fetches value of option by its option code
     **
     ** @param code
     *            The node's option code
     **
     ** @return byte array containing the value of option entryCode. null is returned if option is not set.
     */
    public byte[] getOption( final byte code ) // -----------------------------
    {
        final DHCPOptionEntry entry = getDHCPOptionEntry( code );

        if( entry != null ) {
            return entry.getOptionValue();
        }

        return null;
    }

    // /**
    // ** Clear internal options list, and converts an options byte array to a list (ignore 4 first bytes, vendor magic
    // * cookie)
    // **
    // ** @param optionsArray
    // * The byte array representation of the options list
    // * */
    // public void _init( final byte[] optionsArray ) throws ArrayIndexOutOfBoundsException
    // {
    // clear();
    //
    // // Assume options valid and correct // ignore vendor magic cookie
    // int pos = 4;
    //
    // while( optionsArray[ pos ] != END_OPTION ) { // until end option
    // final byte code = optionsArray[ pos++ ];
    // final byte length = optionsArray[ pos++ ];
    //
    // setOption( code, new DHCPOptionEntry( optionsArray, pos, length ) );
    //
    // pos += length; // increment position pointer
    // }
    // }

    /**
     ** Clear internal options list, and converts an options DataInputStream stream to a list (ignore 4 first bytes,
     * vendor magic cookie)
     **
     ** @param dis
     *            DataInputStream representation of the options list
     */
    public void init( final DataInputStream dis ) // --------------------------
            throws java.io.IOException
    {
        clear();

        // Assume options valid and correct
        // ignore vendor magic cookie
        dis.readByte();
        dis.readByte();
        dis.readByte();
        dis.readByte();

        byte code;

        while( (code = dis.readByte()) != END_OPTION ) { // until end option
            final byte length = dis.readByte();
            final byte[] datas = new byte[length];

            dis.readFully( datas );

            setOption( code, new DHCPOptionEntry( datas ) );
        }
    }

    /**
     ** Converts a linked options list to a byte array
     **
     ** @return array representation of optionsTable
     */
    public byte[] toByteArray() // --------------------------------------------
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //
        // insert vendor magic cookie
        //
        for( int i = 0; i < MAGIC_COOKIE.length; i++ ) {
            baos.write( MAGIC_COOKIE[ i ] );
        }

        for( final Map.Entry<Byte, DHCPOptionEntry> entry : this.getOptionSet() ) {
            baos.write( entry.getKey().intValue() );

            final byte[] content = entry.getValue().getOptionValue();

            baos.write( (byte)content.length );
            baos.write( content, 0, content.length );
        }

        //
        // insert end option
        //
        baos.write( END_OPTION );

        return baos.toByteArray();
    }

    /** TODOC */
    public Set<Map.Entry<Byte, DHCPOptionEntry>> getOptionSet() // -------------
    {
        return Collections.unmodifiableSet( new TreeMap<Byte, DHCPOptionEntry>( this.optionsTable ).entrySet() );
    }

    /** TODOC */
    public String toHexString() // --------------------------------------------
    {
        return DHCPParameters.toHexString( this.toByteArray() );
    }

    /** TODOC */
    public DHCPOptions getClone() // ------------------------------------------
    {
        final DHCPOptions copy = new DHCPOptions();

        for( final Map.Entry<Byte, DHCPOptionEntry> entry : this.optionsTable.entrySet() ) {
            copy.setOption( entry.getKey().byteValue(), entry.getValue().getClone() );
        }

        return copy;
    }

    /** */
    private final static String        messageFmtString = "OPT[{0,number,##0}]\t=";

    /** */
    private final static MessageFormat msgFmt           = new MessageFormat( messageFmtString );

    /** */
    private final Object[]             msgFmtObjects    = new Object[1];

    private String format( final byte optionNumber ) // -----------------------
    {
        this.msgFmtObjects[ 0 ] = new Byte( optionNumber );

        return msgFmt.format( this.msgFmtObjects );
    }

    @Override
    public String toString() // -----------------------------------------------
    {
        final StringBuilder sb = new StringBuilder();

        for( final Map.Entry<Byte, DHCPOptionEntry> entry : this.getOptionSet() ) {
            final String comment = getOptionComment( entry.getKey().byteValue() );
            final char type = comment.charAt( 2 );
            final DHCPOptionEntry value = entry.getValue();
            String displayValue;

            switch( type ) {
                case 'A':
                    displayValue = DHCPParameters.ip4AddrToString( value.getOptionValue() );
                    break;

                case 'C':
                    displayValue = getSubComment( entry.getKey().byteValue(), value.getOptionValue() );
                    break;

                case 'D':
                    displayValue = value.toString() + "=" + DHCPParameters.byteToLong( value.getOptionValue() ) + " secs";
                    break;

                case 'S':
                    displayValue = DHCPParameters.toString( value.getOptionValue() );
                    break;

                default:
                    displayValue = value.toString();
                    break;
            }

            sb.append( format( entry.getKey().byteValue() ) );
            sb.append( displayValue );
            sb.append( " # " );
            sb.append( comment );
            sb.append( "\n" );
        }

        return sb.toString();
    }

    /** Calculer lors du premier appel */
    private transient static Properties prop;

    /** TODOC */
    public String getProperty( final String name ) // -------------------------
    {
        if( DHCPOptions.prop == null ) {
            final String ressourceName = DHCP_OPTIONS_PROPERTIES;

            DHCPOptions.prop = new Properties();

            try( final InputStream is = getClass().getResourceAsStream( ressourceName ) ) {
                DHCPOptions.prop.load( is );

                is.close();
            }
            catch( final java.io.IOException e ) {
                throw new RuntimeException( "Can't read :" + ressourceName, e );
            }
            catch( final NullPointerException e ) {
                throw new RuntimeException( "Can't find :" + ressourceName, e );
            }
        }

        return DHCPOptions.prop.getProperty( name );
    }

    /** TODOC */
    public String getOptionComment( final byte option ) // --------------------
    {
        final String value = getProperty( "OPTION_NUM." + option );

        if( value != null ) {
            final int end = value.indexOf( '\t' );

            if( end != -1 ) {
                return value.substring( 0, end );
            } else {
                return value;
            }
        } else {
            return "Unkown option " + option;
        }
    }

    /** TODOC */
    public String getSubComment( final byte option, final byte[] code ) // ----
    {
        if( code.length != 1 ) {
            return "Bad size for data expected 1, found " + code.length;
        } else {
            return getSubComment( option, code[ 0 ] );
        }
    }

    /** TODOC */
    public String getSubComment( final byte option, final byte code ) // ------
    {
        final String value = getProperty( option + "." + code );

        if( value != null ) {
            return value;
        } else {
            return "Unkown option.code " + option + "." + code;
        }
    }

} // class

