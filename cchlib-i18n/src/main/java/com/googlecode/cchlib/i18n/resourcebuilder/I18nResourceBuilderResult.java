package com.googlecode.cchlib.i18n.resourcebuilder;

import java.util.Map;

/**
 * Result for {@link I18nResourceBuilder} (read only)
 */
public interface I18nResourceBuilderResult
{
    Map<String, Integer> getLocalizedFields();
    Map<String, Integer> getIgnoredFields();
    Map<String, Integer> getMissingProperties();
}
