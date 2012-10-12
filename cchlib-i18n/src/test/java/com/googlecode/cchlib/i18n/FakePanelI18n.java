package com.googlecode.cchlib.i18n;

import java.util.EnumSet;

/**
 *
 */
public class FakePanelI18n
{
    private final AutoI18n autoI18n;

    /**
     * 
     */
    public FakePanelI18n()
    {
        I18nInterface a = null; // FIXME
        AutoI18nTypes b = null; // FIXME
        AutoI18nExceptionHandler c = null; // FIXME
        AutoI18nEventHandler d = null; // FIXME
        EnumSet<AutoI18n.Attribute> e = null; // FIXME
        autoI18n = new AutoI18n(a, b, c, d, e);
    }

    public void doI18n( final FakePanel fp )
    {
        autoI18n.performeI18n( fp, FakePanel.class );
    }
}
