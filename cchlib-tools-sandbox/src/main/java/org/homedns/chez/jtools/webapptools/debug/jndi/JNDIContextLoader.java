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
/*
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import org.homedns.chez.jtools.lib.ContextInitializable;
import org.homedns.chez.jtools.lib.ContextInitializationException;
import org.homedns.chez.jtools.lib.ContextLoader;
*/

/**
**
** @author Claude CHOISNET
** @since   1.01.001
** @version 1.01.001
*/
public class JNDIContextLoader
//    implements java.io.Serializable
{
// /** serialVersionUID */
// private static final long serialVersionUID = 1L;
/**
** Gestion des traces
*/
final org.apache.commons.logging.Log logger
    = org.apache.commons.logging.LogFactory.getLog( this.getClass() );


private String initialContextNameInNamespace;
private String queryMessage;

/**
** Couples nom/valeur
*/
private final Map<String,String> map = new HashMap<String,String>();

/**
**
*/
public final static String DEFAULT_QUERY = "java:comp/env";

/**
**
*/
private final Context initCtx;

/**
**
*/
private final String currentQuery;

/**
**
*/
public JNDIContextLoader( // ----------------------------------------------
    final String paramValue // request.getParameter( "P" );
    )
throws javax.naming.NamingException
{
 this.initCtx       = new InitialContext();
 this.currentQuery  = paramValue == null ? DEFAULT_QUERY : paramValue;

 try {
    this.initialContextNameInNamespace = initCtx.getNameInNamespace();
    }
 catch( Exception e ) {
    // Xystem.out.println( "***Error: initCtx.getNameInNamespace() = [" + e + "]" );
    this.initialContextNameInNamespace = null;
    }

/*
 final StringBuilder msg = new StringBuilder();

 msg.append( "Ctx.lookup( \"" + this.currentQuery + "\" ) = " );

// if( this.query != null ) {
    Object o;

    try {
      o = initCtx.lookup( this.currentQuery );

logger.info( "Ctx.lookup( \"" + this.currentQuery + "\" ) = " + o );

      if( o instanceof Context ) {
          Context ctxt = (Context)o;

logger.info( "cas 1" );

          try {
              msg.append( "ctxt.getNameInNamespace() = " );
              msg.append( ctxt.getNameInNamespace() );
              }
          catch( javax.naming.OperationNotSupportedException e ) {
logger.error( "Exception 1", e );
              msg.append( "* <B>NOT DEFINE</B>" );
              }
          catch( Exception e ) {
logger.error( "Exception 2", e );
              msg.append( "* <B>" + e.getMessage() + "</B>" );
              }
        }
      else if( o instanceof DataSource ) {
logger.info( "cas 2" );
          msg.append( "<br />[DataSource:" + o + "]" );
      }
      else {
logger.info( "cas 3" );
        msg.append( "<br />[<i>" + (o == null ? null : o.getClass()) + "</i>]" );
        }
      }
    catch( Exception e ) {
logger.error( "Exception 3 : initCtx.lookup " + this.currentQuery + " *** " + e );

      msg.append( "<B>ERROR</B> [" + this.currentQuery + "] = <B>" + e + "</B>" );
      }

//    }
*/

 this.queryMessage = doQuery( this.currentQuery );

// if( this.query != null ) {
    NamingEnumeration nameEnum;

    try {
        nameEnum = initCtx.listBindings( this.currentQuery );
        }
      catch( javax.naming.NamingException e ) {
        nameEnum = null;
logger.error( "Exception 4", e );
        }

    if( nameEnum != null ) {
        final String prefix = this.currentQuery + "/";

        try {
            while( nameEnum.hasMore() ) {
                Binding binding     = (Binding) nameEnum.next();
                String  name        = binding.getName();
                String  fullname    = prefix + name;

                this.map.put( fullname, doQuery( fullname ) );
                }
            }
        catch( javax.naming.NamingException e ) {
logger.error( "Exception 5", e );
            }
        }
//    }

// this.queryMessage = msg.toString();
}


/**
**
*/
private String doQuery( final String query ) // -----------------------------------------------
{
 try {
    Object o = initCtx.lookup( query );

    logger.info( "doQuery(): lookup( \"" + query + "\" ) = " + o );

    if( o instanceof Context ) {
        Context ctxt = (Context)o;

        logger.info( "TYPE:Context" );

        try {
            logger.error( "ctxt.getNameInNamespace() = " + ctxt.getNameInNamespace() );
            }
        catch( javax.naming.OperationNotSupportedException e ) {
            logger.error( "NOT DEFINE: ", e );
            }
        catch( Exception e ) {
            logger.error( "Exception: " + e.getMessage() );
            }
        }
    else if( o instanceof DataSource ) {
        logger.info( "TYPE:DataSource: " + o + "]" );
        }
    else {
        logger.info( "TYPE:???: " + (o == null ? null : o.getClass()) );
        }

    return (o == null ? null : o.getClass().getName() + " : " + o.toString() );
    }
 catch( Exception e ) {
    logger.error( "ERROR 3 : initCtx.lookup " + query + " *** " + e );
    }

 return null;
}

/**
**
*/
public String getInitialContextNameInNamespace() // -----------------------
{
 return this.initialContextNameInNamespace;
}

/**
**
*/
public String getQueryMessage() // ----------------------------------------
{
 return this.queryMessage;
}

/**
**
*/
public String getQuery() // -----------------------------------------------
{
 return this.currentQuery;
}

/**
**
*/
public Set<Map.Entry<String,String>> entrySet() // ------------------------
{
 return this.map.entrySet();
}

} // class
