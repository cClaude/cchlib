package com.googlecode.cchlib.i18n.unit.parts;

import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.Assert;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.PrepTestPartInterface;
import com.googlecode.cchlib.i18n.unit.TestReference;
import com.googlecode.cchlib.i18n.unit.util.TestUtils;

public class I18nToolTipTextIgnorePart implements I18nAutoCoreUpdatable, TestReference
{
    private static final Logger LOGGER = Logger.getLogger( I18nToolTipTextIgnorePart.class );
    private static final String TOOLTIPTEXT_INIT = "my tool tip text 1";
    private static final String TOOLTIPTEXT_DEFAULT_BUNDLE = "OK(ToolTipText)";

    private static final String TEXT_INIT = "my button with tool tip text 1";

    @I18nToolTipText @I18nIgnore private final JButton myButtonWithToolTipText1;

    public I18nToolTipTextIgnorePart()
    {
        this.myButtonWithToolTipText1 = new JButton( TEXT_INIT );
        this.myButtonWithToolTipText1.setToolTipText( TOOLTIPTEXT_INIT );
    }

    @Override // I18nAutoCoreUpdatable
    public void performeI18n( final AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest(final PrepTestPartInterface prepTest)
    {
        TestUtils.preparePrepTest( prepTest, this );
    }

    @Override
    public void afterPrepTest()
    {
        Assert.assertEquals( TEXT_INIT, this.myButtonWithToolTipText1.getText() );
        Assert.assertEquals( TOOLTIPTEXT_INIT, this.myButtonWithToolTipText1.getToolTipText() );
    }

    @Override
    public void performeI18n()
    {
        Assert.assertEquals( TEXT_INIT, this.myButtonWithToolTipText1.getText() );
        Assert.assertEquals( TOOLTIPTEXT_INIT, this.myButtonWithToolTipText1.getToolTipText() );

        TestUtils.performeI18n( this );

        final String localised = this.myButtonWithToolTipText1.getToolTipText();

        LOGGER.info( "TEST RESULT: getToolTipText() " + localised );

        Assert.assertEquals( TOOLTIPTEXT_DEFAULT_BUNDLE, localised );
        Assert.assertEquals( TEXT_INIT, this.myButtonWithToolTipText1.getText() );
    }

    @Override
    public int getSyntaxeExceptionCount()
    {
        return 0;
    }

    @Override
    public int getMissingResourceExceptionCount()
    {
        return 1;
    }
}
