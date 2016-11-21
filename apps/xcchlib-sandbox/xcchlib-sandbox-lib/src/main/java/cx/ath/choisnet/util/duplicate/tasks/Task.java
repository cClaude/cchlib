/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/util/duplicate/tasks/Task.java
** Description   :
** Encodage      : ANSI
**
**  3.01.008 2006.03.07 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.util.duplicate.tasks.Task
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
public interface Task
    extends java.io.Serializable
{
/** */
abstract public void doJob()
    throws TaskFailException, TaskRetryLaterException;

} // interface

