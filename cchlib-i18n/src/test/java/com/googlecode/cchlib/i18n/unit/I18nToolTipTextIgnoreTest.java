package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.AutoI18nCore;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.unit.utils.RunI18nTestInterface;
import com.googlecode.cchlib.i18n.unit.utils.TestUtils;
import javax.swing.JButton;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class I18nToolTipTextIgnoreTest implements I18nAutoCoreUpdatable, RunI18nTestInterface
{
    private final static Logger logger = Logger.getLogger( I18nToolTipTextIgnoreTest.class );
    private final static String TOOLTIPTEXT_INIT = "my tool tip text 1";
    private final static String TOOLTIPTEXT_DEFAULT_BUNDLE = "OK(ToolTipText)";

    private final static String TEXT_INIT = "my button with tool tip text 1";
    
    @I18nToolTipText @I18nIgnore private JButton myButtonWithToolTipText1;

    public I18nToolTipTextIgnoreTest()
    {
        this.myButtonWithToolTipText1 = new JButton( TEXT_INIT );
        this.myButtonWithToolTipText1.setToolTipText( TOOLTIPTEXT_INIT );
    }

    @Override // I18nAutoCoreUpdatable
    public void performeI18n( AutoI18nCore autoI18n )
    {
        autoI18n.performeI18n( this, this.getClass() );
    }

    @Override
    public void beforePrepTest(PrepTest prepTest)
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
    public void runPerformeI18nTest()
    {
        Assert.assertEquals( TEXT_INIT, this.myButtonWithToolTipText1.getText() );
        Assert.assertEquals( TOOLTIPTEXT_INIT, this.myButtonWithToolTipText1.getToolTipText() );

        TestUtils.runPerformeI18nTest( this );

        String localised = this.myButtonWithToolTipText1.getToolTipText();

        logger.info( "TEST RESULT: getToolTipText() " + localised );

        Assert.assertEquals( TOOLTIPTEXT_DEFAULT_BUNDLE, localised );
        Assert.assertEquals( TEXT_INIT, this.myButtonWithToolTipText1.getText() );
    }
}
