package com.googlecode.cchlib.i18n.resourcebuilder;

import java.io.File;
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
     * @param outputFile
     *            A file where missing entries will be stores.
     * @return a {@link I18nResourceBuilderResult} - mainly for non
     *         regressions tests.
     */
    I18nResourceBuilderResult buildResult( File outputFile );
}
