package com.googlecode.cchlib.swing.batchrunner.ihm;

import com.googlecode.cchlib.swing.batchrunner.misc.BRXLocaleResources;

public abstract class AbstractBRFrameBuilder implements BRFrameBuilder
{
    private static final long serialVersionUID = 1L;

    private final BRPanelLocaleResources theBRPanelLocaleResources;
    private final BRXLocaleResources     theBRXLocaleResources;

    public AbstractBRFrameBuilder(
        final BRPanelLocaleResources aBRPanelLocaleResources,
        final BRXLocaleResources     aBRXLocaleResources
        )
    {
        this.theBRPanelLocaleResources = aBRPanelLocaleResources;
        this.theBRXLocaleResources     = aBRXLocaleResources;
    }

    @Override
    public final BRPanelLocaleResources getBRPanelLocaleResources()
    {
        return this.theBRPanelLocaleResources;
    }

    @Override
    public final BRXLocaleResources getBRXLocaleResources()
    {
        return this.theBRXLocaleResources;
    }
}
