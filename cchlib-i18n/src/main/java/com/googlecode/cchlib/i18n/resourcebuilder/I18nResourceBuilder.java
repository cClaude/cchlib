package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

/**
 * @see I18nResourceBuilderFactory
 */
public interface I18nResourceBuilder
{
    /**
     * Add a component to I18n, this method should be use for each
     * root component. I18n will produce the best effort to find sub
     * components, but some dynamic components can not be discovers
     * by a static analysis.
     *
     * @param i18nObject
     *            The component to internationalize.
     */
    void append( I18nAutoCoreUpdatable i18nObject );

    /**
     * Generate final report
     *
     * @return a {@link I18nResourceBuilderResult} - mainly for non
     *         regressions tests.
     */
    I18nResourceBuilderResult getResult();

    /**
     * Save {@link PropertyResourceBundle} with only missing keys.
     *
     * @param outputFile
     *            A file where missing entries will be stores.
     * @throws IOException if any
     */
    void saveMissingResourceBundle( File outputFile ) throws IOException;

    /**
     * Save {@link PropertyResourceBundle} with only missing keys.
     *
     * @param writer
     *            an output character stream writer
     * @param comments
     *            a description of the property list.
     * @throws IOException
     *             if any
     * @see Properties#store(Writer, String)
     */
    void saveMissingResourceBundle( Writer writer, String comments ) throws IOException;
}
