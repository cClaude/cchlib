package com.googlecode.cchlib.tools.phone.recordsorter;

import com.googlecode.cchlib.swing.batchrunner.BRExitable;
import com.googlecode.cchlib.swing.batchrunner.ihm.AbstractDefaultBRFrameBuilderRessourceBundle;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResourcesBuilder;
import com.googlecode.cchlib.swing.batchrunner.misc.MissingResourceValueException;

public class PhoneRecordSorterResources extends AbstractDefaultBRFrameBuilderRessourceBundle
{
    private static final long serialVersionUID = 1L;

    public PhoneRecordSorterResources() throws MissingResourceValueException
    {
        super( getDefaultBRLocaleResourcesBuilder() );
    }

    public static DefaultBRLocaleResourcesBuilder getDefaultBRLocaleResourcesBuilder()
    {
        return new DefaultBRLocaleResourcesBuilder();
    }

    @Override
    @SuppressWarnings("squid:S1147")
    public BRExitable getBRExitable()
    {
        return () -> System.exit( 0 );
    }
}
