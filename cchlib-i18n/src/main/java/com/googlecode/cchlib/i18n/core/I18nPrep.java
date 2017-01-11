package com.googlecode.cchlib.i18n.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.prep.I18nPrepFactory;
import com.googlecode.cchlib.i18n.prep.I18nPrepHelper;
import com.googlecode.cchlib.i18n.prep.I18nPrepStatResult;
import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;
import com.googlecode.cchlib.i18n.resources.I18nResourceBundleName;

/**
 *
 * @see I18nPrepFactory
 * @see I18nPrepHelper
 * @deprecated use {@link I18nResourceBuilderFactory} instead
 */
@Deprecated
public interface I18nPrep
{
    void addAutoI18nExceptionHandler( AutoI18nExceptionHandler exceptionHandler );

    void closeOutputFile() throws IOException;

    AutoI18n getAutoI18n();

    I18nPrepStatResult getI18nPrepStatResult();

    I18nResourceBundleName getI18nResourceBundleName();

    ResourceBundle getResourceBundle();

    Map<String, String> getResourceBundleMap();

    Map<String, Integer> getUsageMap();

    void openOutputFile( File outputFile );
}
