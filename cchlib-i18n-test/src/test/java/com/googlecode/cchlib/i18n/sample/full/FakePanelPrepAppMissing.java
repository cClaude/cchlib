package com.googlecode.cchlib.i18n.sample.full;

import static org.fest.assertions.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.prep.I18nPrepException;
import com.googlecode.cchlib.i18n.prep.I18nPrepResult;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

public class FakePanelPrepAppMissing extends AbstractMessageBundleBaseName
{
    public static void main( final String...args ) throws Exception
    {
        final FakePanelPrepAppMissing instance = new FakePanelPrepAppMissing();

        instance.test_FakePanelPrepAppMissing();
    }

    @Test
    public void test_FakePanelPrepAppMissing() throws IOException, I18nPrepException
    {
        // Build frame
        final FakePanel frameOrPanel = new FakePanel();

        // Other frames,panel,... if any
        final I18nAutoCoreUpdatable[] i18nConteners = { frameOrPanel };

        final FakePanelPrepAppMissing instance = new FakePanelPrepAppMissing();
        final I18nPrepResult          result   = instance.start( i18nConteners );

        assertThat( result.getNotUseCollector().isEmpty() ).isTrue();
        // TODO need to get stats values
    }

    @Override
    public I18nResourceBundleName createI18nResourceBundleName()
    {
        return FakePanelAppCore.createI18nResourceBundleName( "MessagesBundleMissing" );
    }
}
