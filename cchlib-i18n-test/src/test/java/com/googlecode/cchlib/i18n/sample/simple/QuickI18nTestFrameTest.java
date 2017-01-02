package com.googlecode.cchlib.i18n.sample.simple;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import javax.swing.JButton;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class QuickI18nTestFrameTest
{
    @Test
    public void test_QuickI18nTestFrame()
    {
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final QuickI18nTestFrame frame = QuickI18nTestFrame.newQuickI18nTestFrame();

        assertThat( frame ).isNotNull();

        final JButton b1 = frame.getMyButtonWithToolTipText1();

        assertThat( b1.getText() ).isEqualTo( "I18n(my button with tool tip text 1)" );
        assertThat( b1.getToolTipText() ).isEqualTo( "I18n(my tool tip text 1)" );

        final JButton b2 = frame.getMyButtonWithToolTipText2();

        // No I18n for text in resource
        assertThat( b2.getText() ).isEqualTo( "my button with tool tip text 2 (no I18n)" );
        assertThat( b2.getToolTipText() ).isEqualTo( "I18n(my tool tip text 2)" );
    }
}
