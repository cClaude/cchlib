package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;

/**
 * TODOC
 *
 */
public interface InfosServletDisplayer
{
    /**
     * Build result as HTML
     *
     * @param out Output for HTML
     */
    void appendHTML(Appendable out) throws IOException;
}
