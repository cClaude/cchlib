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
        I18nInterface a = null; // FIX ME
        AutoI18nExceptionHandler c = null; // FIX ME
        AutoI18nEventHandler d = null; // FIX ME
        EnumSet<AutoI18n.Attribute> e = null; // FIX ME

		autoI18n = new AutoI18n(a, null, null, c, d, e);
    }

    public void doI18n( final FakePanel fp )
    {
        autoI18n.performeI18n( fp, FakePanel.class );
    }
}
