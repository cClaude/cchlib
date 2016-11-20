package com.googlecode.cchlib.servlet.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.cchlib.servlet.ActionServlet;
import com.googlecode.cchlib.servlet.ActionServlet.Action;
import com.googlecode.cchlib.servlet.exception.ServletActionException;

/**
 * Does nothing.
 */
public class NopServletAction implements ServletAction
{
    /**
     * Just return {@link Action#FORWARD}.
     * @return always {@link Action#FORWARD}
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
