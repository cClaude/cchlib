package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

public interface TestReference extends I18nAutoCoreUpdatable
{
    void beforePerformeI18nTest();

    void afterPerformeI18nTest_WithValidBundle();
    void afterPerformeI18nTest_WithNotValidBundle();

    void afterResourceBuilderTest_WithValidBundle( I18nResourceBuilderResult result );
    void afterResourceBuilderTest_WithNotValidBundle( I18nResourceBuilderResult result );
}
