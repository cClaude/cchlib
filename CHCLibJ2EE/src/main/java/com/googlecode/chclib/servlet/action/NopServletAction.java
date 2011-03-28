/**
 * 
 */
package com.googlecode.chclib.servlet.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.chclib.servlet.ActionServlet;
import com.googlecode.chclib.servlet.exception.ServletActionException;

/**
 * Does nothing.
 * 
 * @author Claude CHOISNET
 */
public class NopServletAction implements ServletAction
{
    /**
     * Just return {@link ActionServlet.Action#FORWARD}.
     */
    @Override
    public ActionServlet.Action doAction(
            HttpServletRequest request,
            HttpServletResponse response,
            ServletContext context
            )
            throws ServletActionException
    {
        return ActionServlet.Action.FORWARD;
    }
}
