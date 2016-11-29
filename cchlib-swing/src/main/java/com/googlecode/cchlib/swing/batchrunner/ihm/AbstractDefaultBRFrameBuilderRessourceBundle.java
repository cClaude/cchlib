package com.googlecode.cchlib.swing.batchrunner.ihm;

import com.googlecode.cchlib.swing.batchrunner.misc.DefaultBRXLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingResourceValueException;

/**
 * Default implementation for {@link BRFrameBuilder}
 *
 * @see {@link BRFrame}
 */
public abstract class AbstractDefaultBRFrameBuilderRessourceBundle extends AbstractBRFrameBuilder
{
    private static final long serialVersionUID = 1L;

    public AbstractDefaultBRFrameBuilderRessourceBundle(
        final DefaultBRLocaleResourcesBuilder builder
        ) throws MissingResourceValueException
    {
        super(
            new DefaultBRLocaleResources( builder ),
            new DefaultBRXLocaleResources( builder )
            );
    }
}
