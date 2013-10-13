/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/TaskRetryLaterException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.TaskRetryLaterException
**
*/
package cx.ath.choisnet.util.duplicate.tasks;

/**
** <p>
** </p>
**
** <p>
** </p>
**
** @author Claude CHOISNET
** @since   3.01.008
** @version 3.01.008
**
*/
public class TaskRetryLaterException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/**
**
*/
public TaskRetryLaterException() // ---------------------------------------
{
 super();
}

/**
**
*/
public TaskRetryLaterException( Throwable cause ) // ----------------------
{
 super( cause );
}

/**
**
*/
public TaskRetryLaterException( String message, Throwable cause ) // ------
{
 super( message, cause );
}

/**
**
*/
public TaskRetryLaterException( String message ) // -----------------------
{
 super( message );
}

} // class

