package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;

/**
 * Handle HTML rendering
 */
@FunctionalInterface
public interface InfosServletDisplayer
{
    /**
     * Build result as HTML
     *
     * @param out Output for HTML
     */
    void appendHTML(Appendable out) throws IOException;
}
