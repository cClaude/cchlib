package com.googlecode.cchlib.servlet.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.cchlib.servlet.ActionServlet;
import com.googlecode.cchlib.servlet.ActionServlet.Action;
import com.googlecode.cchlib.servlet.exception.ServletActionException;

/**
 * TODOC
 *
 */
public interface ServletAction
{
    /**
     * TODOC
     *
     * @param request
     * @param response
     * @param context
     * @return {@link Action} describing what
     * {@link ActionServlet} should do after running doAction() method.
     */
    ActionServlet.Action doAction(
            final HttpServletRequest    request,
            final HttpServletResponse   response,
            final ServletContext        context
            )
        throws ServletActionException;
}
