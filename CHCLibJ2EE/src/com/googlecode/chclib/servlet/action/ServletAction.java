package com.googlecode.chclib.servlet.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.chclib.servlet.ActionServlet;
import com.googlecode.chclib.servlet.exception.ServletActionException;

/**
 * 
 * @author Claude CHOISNET
 */
public interface ServletAction 
{
    /**
     * 
     * @param request
     * @param response
     * @param context
     * @return {@link ActionServlet.Action} describing what
     * {@link ActionServlet.Action} should do.
     */
    public ActionServlet.Action doAction(
            final HttpServletRequest    request, 
            final HttpServletResponse   response,
            final ServletContext        context
            )
        throws ServletActionException;
}
