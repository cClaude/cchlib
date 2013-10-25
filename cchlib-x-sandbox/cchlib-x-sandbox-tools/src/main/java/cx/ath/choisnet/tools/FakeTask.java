/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/tools/FakeTask.java
** Description   :
**
** 1.00 2005.08.21 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.FakeTask
**
*/
package cx.ath.choisnet.tools;

import javax.servlet.ServletConfig;
import cx.ath.choisnet.tools.servlets.InitServletTask;

/**
**
** @author Claude CHOISNET
** @version 1.0
*/
public class FakeTask extends InitServletTask
{
/**
** Gestion des traces
*/
protected final org.apache.commons.logging.Log logger
              = org.apache.commons.logging.LogFactory.getLog( this.getClass() );

/**
**
*/
public void init( // ------------------------------------------------------
    ServletConfig servletConfig
    )
{
 log( " *****************************************" );
 log( " * " + this.hashCode() + ".init(" + servletConfig + ")" );
 log( " *****************************************" );
}

/**
**
*/
public void run() // ------------------------------------------------------
{
 log( " *****************************************" );
 log( " * " + this.hashCode() + ".run()" );
 log( " *****************************************" );
}

/**
**
*/
public void log( String message ) // --------------------------------------
{
 logger.trace( message );
}

} // class
