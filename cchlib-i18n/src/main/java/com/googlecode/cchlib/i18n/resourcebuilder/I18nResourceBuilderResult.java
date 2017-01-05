package com.googlecode.cchlib.i18n.resourcebuilder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

/**
 * Result for {@link I18nResourceBuilder} (read only)
 */
public interface I18nResourceBuilderResult
{
    /**
     * Returns an unmodifiable {@link Map} for entries that have been localized
     * by I18n module.
     * <p>
     * {@link Entry} key is the expected key for the entry, {@link Entry} value
     * is the number of potential customization of this field by I18n process.
     * Since you can customize key you are able to reuse a key for more than
     * one entry, this will return the number of entries than use the key.
     *
     * @return an unmodifiable {@link Map} of localized entries.
     */
    Map<String, Integer> getLocalizedFields();

    /**
     * Returns an unmodifiable {@link Map} for entries that have been ignored
     * by I18n module. This will contain all fields discover by I18n.
     * <p>
     * {@link Entry} key is the expected key for the entry, {@link Entry} value
     * is the number of potential customization of this field by I18n process
     * (should be always 1)
     *
     * @return an unmodifiable {@link Map} of ignored entries.
     */
    Map<String, Integer> getIgnoredFields();

    /**
     * Returns an unmodifiable {@link Map} for entries that have been localized
     * by I18n module.
     * <p>
     * {@link Entry} key is the expected key for the entry, {@link Entry} value
     * is the number of potential customization of this field by I18n process.
     * Since you can customize key you are able to reuse a key for more than
     * one entry, this will return the number of entries than use the key.
     *
     * @return an unmodifiable {@link Map} of missing entries.
     */
    Map<String, Integer> getMissingProperties();

    /**
     * Returns an unmodifiable {@link Map} of (key,values) discover by
     * {@link ResourceBundle} by not use by I18n.
     * <p>
     * {@link Entry} key is the expected key for the entry, {@link Entry} value
     * is the corresponding value in the {@link ResourceBundle}.
     *
     * @return an unmodifiable {@link Map} of unused entries.
     */
    Map<String,String> getUnusedProperties();
}
