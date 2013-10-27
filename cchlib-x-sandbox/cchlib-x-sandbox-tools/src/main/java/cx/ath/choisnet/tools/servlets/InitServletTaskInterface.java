/*
** -----------------------------------------------------------------------
** Nom           : cx\ath\choisnet\tools\servlets\InitServletTaskInterface.java
** Description   :
** Encodage      : ANSI
**
**  1.00 2004.12.01 Claude CHOISNET
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.tools.servlets.InitServletTaskInterface
**
*/
package cx.ath.choisnet.tools.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
**
**
** @author Claude CHOISNET
** @since   1.00
** @version 1.10
*/
public interface InitServletTaskInterface
    extends Runnable
{

/**
**
*/
public void init( ServletConfig servletConfig ) // ------------------------
    throws ServletException;

/**
**
*/
public String getTaskName(); // -------------------------------------------

/**
**
*/
public boolean continueRunning(); // --------------------------------------

/**
**
*/
public void log( String message ); // -------------------------------------

@Override
void run();

} // interface
