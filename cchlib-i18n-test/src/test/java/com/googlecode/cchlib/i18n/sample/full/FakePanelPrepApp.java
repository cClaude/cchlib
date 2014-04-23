package com.googlecode.cchlib.i18n.sample.full;

import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;
import java.io.IOException;

public class FakePanelPrepApp extends AbstractMessageBundleBaseName
{
    private FakePanelPrepApp()
    {
    }

    public static void main( String...args ) throws IOException
    {
        // Build frame
        FakePanel frameOrPanel = new FakePanel();

        // Other frames,panel,... if any
        I18nAutoCoreUpdatable[] i18nConteners = { frameOrPanel };

        FakePanelPrepApp instance = new FakePanelPrepApp();
        instance.start( i18nConteners );
    }

    @Override
    public I18nResourceBundleName createI18nResourceBundleName()
    {
        return FakePanelAppCore.createI18nResourceBundleName();
    }
}
