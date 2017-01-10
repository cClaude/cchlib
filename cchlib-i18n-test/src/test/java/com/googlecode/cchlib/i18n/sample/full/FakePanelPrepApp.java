package com.googlecode.cchlib.i18n.sample.full;

import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

public class FakePanelPrepApp extends AbstractMessageBundleBaseName
{
    private FakePanelPrepApp()
    {
    }

    public static void main( final String...args ) throws Exception
    {
        // Build frame
        final FakePanel frameOrPanel = new FakePanel();

        // Other frames,panel,... if any
        final I18nAutoUpdatable[] i18nConteners = { frameOrPanel };

        final FakePanelPrepApp instance = new FakePanelPrepApp();
        instance.start( i18nConteners );
    }

    @Override
    public I18nResourceBundleName createI18nResourceBundleName()
    {
        return FakePanelAppCore.createI18nResourceBundleName();
    }
}
