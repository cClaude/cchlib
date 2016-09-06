package com.googlecode.cchlib.servlet.simple.debug;

import java.io.IOException;

/**
 * Define a section of {@link InfosServlet} output
 */
public interface InfosServletDisplay
{
    /**
     * Anchor for this section
     *
     * @return anchor for this section
     */
    InfosServletDisplayAnchor getAnchor();

    /**
     * Define couple (name,value) to add in this section
     *
     * @param name Name of the value
     * @param value String representation of the value
     *
     * @return TODOC
     */
    InfosServletDisplay put(String name, String value);

    /**
     * TODOC
     *
     * @param appendable
     * @throws IOException
     */
    void appendHTML(Appendable appendable) throws IOException;
}
