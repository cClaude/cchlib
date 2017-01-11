package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nDefaultTest extends I18nITBase
{
    static final String INIT_myJLabel           = "my JLabel text";
    static final String DEFAULT_BUNDLE_myJLabel = "OK(myJLabel)";

    static final String INIT_myJButton           = "my JButton text";
    static final String DEFAULT_BUNDLE_myJButton = "OK(myJButton)";

    static final String INIT_myJCheckBox           = "my JCheckBox text";
    static final String DEFAULT_BUNDLE_myJCheckBox = "OK(myJCheckBox)";

    static final String INIT_myJTabbedPane1           = "my JTabbedPane panel1";
    static final String DEFAULT_BUNDLE_myJTabbedPane1 = "OK(myJTabbedPane1)";

    static final String INIT_myJTabbedPane2           = "my JTabbedPane panel2";
    static final String DEFAULT_BUNDLE_myJTabbedPane2 = "OK(myJTabbedPane2)";

    static final String INIT_myTitledBorder           = "my myTitledBorder text";
    static final String DEFAULT_BUNDLE_myTitledBorder = "OK(myTitledBorder)";

    static final int LOCALIZED_FIELDS = 6;
    static final int IGNORED_FIELDS   = 2 + 2;

    @Test
    public void testI18n_WithValidBundle_I18nDefaultPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nDefaultPart part = new I18nDefaultPart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nDefaultPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nDefaultPart part = new I18nDefaultPart();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nDefaultPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nDefaultPart part = new I18nDefaultPart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nDefaultPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nDefaultPart           part   = new I18nDefaultPart();
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

