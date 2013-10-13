/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/TaskFailException.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.TaskFailException
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
public class TaskFailException
    extends Exception
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;


/**
**
*/
public TaskFailException( Throwable cause ) // ----------------------------
{
 super( cause );
}

/**
**
*/
public TaskFailException( String message, Throwable cause ) // ------------
{
 super( message, cause );
}

} // class

