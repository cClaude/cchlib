package com.googlecode.cchlib.i18n.unit;

import com.googlecode.cchlib.i18n.core.I18nAutoUpdatable;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderResult;

/**
 * extends {@link I18nAutoUpdatable} is required by tests and I18nResourceBuilder
 * process, but not formally by I18n process
 */
public interface TestReference extends I18nAutoUpdatable
{
    void beforePerformeI18nTest();

    void afterPerformeI18nTest_WithValidBundle();
    void afterPerformeI18nTest_WithNotValidBundle();

    void afterResourceBuilderTest_WithValidBundle( I18nResourceBuilderResult result );
    void afterResourceBuilderTest_WithNotValidBundle( I18nResourceBuilderResult result );
}
