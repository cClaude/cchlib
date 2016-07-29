package com.googlecode.cchlib.servlet.simple.debug.impl;

import java.io.Serializable;
import com.googlecode.cchlib.servlet.simple.debug.InfosServletDisplayAnchor;

//NOT public
class DefaultInfosServletDisplayAnchor implements InfosServletDisplayAnchor, Serializable
{
    private static final long serialVersionUID = 1L;

    private final String anchorName;

    DefaultInfosServletDisplayAnchor( final String anchorName )
    {
        this.anchorName = anchorName;
    }

    @Override
    public String getDisplay()
    {
        return this.anchorName;
    }

    @Override
    public String getId()
    {
        return this.anchorName.replaceAll("[\\)\\(\\.]", "_");
    }
}
