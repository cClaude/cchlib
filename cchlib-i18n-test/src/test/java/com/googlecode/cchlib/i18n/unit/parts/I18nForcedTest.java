package com.googlecode.cchlib.i18n.unit.parts;

import org.junit.Assume;
import org.junit.Test;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;
import com.googlecode.cchlib.i18n.unit.util.I18nITBase;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class I18nForcedTest extends I18nITBase
{
    static final String INIT_myJLabel           = "my JLabel text";
    static final String DEFAULT_BUNDLE_myJLabel = "OK(myJLabel)";

    static final String INIT_myJButton           = "my JButton text";
    static final String DEFAULT_BUNDLE_myJButton = "OK(myJButton)";

    static final String INIT_myJCheckBox = "my JCheckBox text";
    static final String DEFAULT_BUNDLE_myJCheckBox = "OK(myJCheckBox)";

    static final String INIT_myJTabbedPane1           = "my JTabbedPane panel1";
    static final String DEFAULT_BUNDLE_myJTabbedPane1 = "OK(myJTabbedPane1)";

    static final String INIT_myJTabbedPane2           = "my JTabbedPane panel2";
    static final String DEFAULT_BUNDLE_myJTabbedPane2 = "OK(myJTabbedPane2)";

    static final String INIT_myTitledBorder           = "my myTitledBorder text";
    static final String DEFAULT_BUNDLE_myTitledBorder = "OK(myTitledBorder)";

    static final String INIT_myJTextArea           = "this is my JTextArea text";
    static final String DEFAULT_BUNDLE_myJTextArea = "OK(myJTextArea)";

    static final String INIT_myJTextField           = "this is my JTextField text";
    static final String DEFAULT_BUNDLE_myJTextField = "OK(myJTextField)";

    static final String INIT_myJEditorPane           = "this is my JEditorPane text";
    static final String DEFAULT_BUNDLE_myJEditorPane = "OK(myJEditorPane)";

    static final int LOCALIZED_FIELDS = 9;
    static final int IGNORED_FIELDS   = 5;

    @Test
    public void testI18n_WithValidBundle_I18nForcedPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nForcedPart part = new I18nForcedPart();

        // Apply tests
        runI18nTests_WithValidBundle( part );
    }

    @Test
    public void testI18n_WithNotValidBundle_I18nForcedPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nForcedPart part = new I18nForcedPart();

        runI18nTests_WithNotValidBundle( part );
    }

    @Test
    public void testI18nResourceBuilder_WithValidBundle_I18nForcedPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nForcedPart part = new I18nForcedPart();

        // Invoke I18nResourceBuilder
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithValidBundle( part );

        // Apply tests
        runI18nResourceBuilderTests_WithValidBundle( part, result );
    }

    @Test
    public void testI18nResourceBuilder_WithNotValidBundle_I18nForcedPart()
    {
        Assume.assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final I18nForcedPart            part   = new I18nForcedPart();
        final I18nResourceBuilderResult result = do_I18nResourceBuilder_WithNotValidBundle( part );

        runI18nResourceBuilderTests_WithNotValidBundle( part, result );
    }
}

