/*
 ** -----------------------------------------------------------------------
 ** Nom           : org/homedns/chez/jtools/webapptools/debug/jndi/JNDIContextLoader.java
 ** Description   :
 ** Encodage      : ANSI
 **
 **  1.01.001 2007.01.01 Claude CHOISNET - Version initiale
 ** -----------------------------------------------------------------------
 **
 ** org.homedns.chez.jtools.webapptools.debug.jndi.JNDIContextLoader
 **
 */
package org.homedns.chez.jtools.webapptools.debug.jndi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.activation.DataSource;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import org.apache.log4j.Logger;

/**
 **
 ** @author Claude CHOISNET
 ** @since 1.01.001
 ** @version 1.01.001
 */
public class JNDIContextLoader
{
    private static final Logger LOGGER = Logger.getLogger( JNDIContextLoader.class );

    private String                       initialContextNameInNamespace;
    private String                       queryMessage;

    /**
     ** Couples nom/valeur
     */
    private final Map<String, String>    map           = new HashMap<String, String>();

    public static final String           DEFAULT_QUERY = "java:comp/env";
    private final Context                initCtx;
    private final String                 currentQuery;

    public JNDIContextLoader( // ----------------------------------------------
            final String paramValue // request.getParameter( "P" );
    ) throws javax.naming.NamingException
    {
        this.initCtx = new InitialContext();
        this.currentQuery = paramValue == null ? DEFAULT_QUERY : paramValue;

        try {
            this.initialContextNameInNamespace = initCtx.getNameInNamespace();
        }
        catch( Exception e ) {
            // Xystem.out.println( "***Error: initCtx.getNameInNamespace() = ["
            // + e + "]" );
            this.initialContextNameInNamespace = null;
        }

        this.queryMessage = doQuery( this.currentQuery );

        // if( this.query != null ) {
        NamingEnumeration<Binding> nameEnum;

        try {
            nameEnum = initCtx.listBindings( this.currentQuery );
        }
        catch( javax.naming.NamingException e ) {
            nameEnum = null;
            LOGGER.error( "Exception 4", e );
        }

        if( nameEnum != null ) {
            final String prefix = this.currentQuery + "/";

            try {
                while( nameEnum.hasMore() ) {
                    Binding binding = nameEnum.next();
                    String name = binding.getName();
                    String fullname = prefix + name;

                    this.map.put( fullname, doQuery( fullname ) );
                }
            }
            catch( javax.naming.NamingException e ) {
                LOGGER.error( "Exception 5", e );
            }
        }
        // }

        // this.queryMessage = msg.toString();
    }

    private String doQuery( final String query ) // -----------------------------------------------
    {
        try {
            Object o = initCtx.lookup( query );

            LOGGER.info( "doQuery(): lookup( \"" + query + "\" ) = " + o );

            if( o instanceof Context ) {
                Context ctxt = (Context)o;

                LOGGER.info( "TYPE:Context" );

                try {
                    LOGGER.error( "ctxt.getNameInNamespace() = "
                            + ctxt.getNameInNamespace() );
                }
                catch( javax.naming.OperationNotSupportedException e ) {
                    LOGGER.error( "NOT DEFINE: ", e );
                }
                catch( Exception e ) {
                    LOGGER.error( "Exception: " + e.getMessage() );
                }
            } else if( o instanceof DataSource ) {
                LOGGER.info( "TYPE:DataSource: " + o + "]" );
            } else {
                LOGGER.info( "TYPE:???: " + (o == null ? null : o.getClass()) );
            }

            return (o == null ? null : o.getClass().getName() + " : "
                    + o.toString());
        }
        catch( Exception e ) {
            LOGGER.error( "ERROR 3 : initCtx.lookup " + query + " *** " + e );
        }

        return null;
    }

    public String getInitialContextNameInNamespace() // -----------------------
    {
        return this.initialContextNameInNamespace;
    }

    public String getQueryMessage() // ----------------------------------------
    {
        return this.queryMessage;
    }

    public String getQuery() // -----------------------------------------------
    {
        return this.currentQuery;
    }

    public Set<Map.Entry<String, String>> entrySet() // ------------------------
    {
        return this.map.entrySet();
    }

} // class
