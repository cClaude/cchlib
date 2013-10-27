/*
** -----------------------------------------------------------------------
** Nom           : cx\ath\choisnet\tools\servlets\InitServletAbstractTask.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2004.12.01 Claude CHOISNET
**  1.10 2005.10.17 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.servlets.InitServletAbstractTask
**
*/
package cx.ath.choisnet.tools.servlets;

/**
**
**
** @author Claude CHOISNET
** @since   1.00
** @version 1.10
*/
public abstract class InitServletAbstractTask
    implements InitServletTaskInterface

{
/**
** Gestion des traces
*/
private org.apache.commons.logging.Log logger
      = org.apache.commons.logging.LogFactory.getLog( this.getClass() );

/**
**
*/
private String instanceName;

/**
**
*/
private Boolean continueRunning;

/**
**
*/
public InitServletAbstractTask() // ---------------------------------------
{
 this.instanceName      = null;
 this.continueRunning   = Boolean.TRUE;
}

/**
**
*/
public InitServletAbstractTask( String instanceName ) // ------------------
{
 this.instanceName      = instanceName;
 this.continueRunning   = Boolean.TRUE;
}

/**
**
*/
public String getInstanceName() // ----------------------------------------
{
 return this.instanceName;
}

/**
**
*/
@Override
public String getTaskName() // --------------------------------------------
{
 final String name = this.getInstanceName();

 if( name == null ) {
    return this.getClass().getName();
    }
 else {
    return this.getClass().getName() + "." + name;
    }
}

/**
**
*/
public void stop() // -----------------------------------------------------
{
 this.continueRunning = Boolean.FALSE;
}

/**
**
*/
@Override
public boolean continueRunning() // ---------------------------------------
{
 return this.continueRunning;
}


/**
**
*/
@Override
public void log( String message ) // --------------------------------------
{
 logger.trace( message );
}

} // interface
