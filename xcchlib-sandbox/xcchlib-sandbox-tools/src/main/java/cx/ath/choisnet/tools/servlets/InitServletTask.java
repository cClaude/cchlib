/*
** -----------------------------------------------------------------------
** Nom           : cx\ath\choisnet\tools\servlets\InitServletTask.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2004.12.01 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.servlets.InitServletTask
**
*/
package cx.ath.choisnet.tools.servlets;

/**
**
**
** @author Claude CHOISNET
** @version 1.00
** @since 1.00
*/
public abstract class InitServletTask
    implements InitServletTaskInterface
{
/** */
private String name = null;

/**
**
*/
public InitServletTask() // -----------------------------------------------
{
 // empty
}

/**
**
*/
public InitServletTask( String name ) // ----------------------------------
{
 this.name = name;
}

/**
**
public void init( // ------------------------------------------------------
    ServletConfig servletConfig
    ) throws ServletException
{
 // empty
}
*/

/**
**
public void run() // ------------------------------------------------------
{
 // empty
}
*/

/**
**
*/
@Override
public String getTaskName() // --------------------------------------------
{
 if( name == null ) {
    return this.getClass().getName();
    }
 else {
    return this.getClass().getName() + "(" + this.name + ")";
    }
}

/**
**
*/
@Override
public boolean continueRunning() // ---------------------------------------
{
 return true;
}

} // interface
