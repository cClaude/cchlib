package com.googlecode.cchlib.i18n.prep;

import com.googlecode.cchlib.i18n.resourcebuilder.I18nResourceBuilderFactory;

/**
 * @deprecated use {@link I18nResourceBuilderFactory} instead
 */
@Deprecated
public interface I18nPrepStatResult {
    int getFound();

    int getKnow();

    int getUnknow();
}
