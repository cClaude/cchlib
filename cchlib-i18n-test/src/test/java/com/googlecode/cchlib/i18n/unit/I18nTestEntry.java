package com.googlecode.cchlib.i18n.unit;

import javax.annotation.Nonnull;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;

public class I18nTestEntry {
    private final TestReference testReference;
    private final I18nAutoCoreUpdatable testContener;

    public I18nTestEntry( //
        @Nonnull final TestReference testReference, //
        @Nonnull  final I18nAutoCoreUpdatable testContener )
    {
        this.testReference = testReference;
        this.testContener = testContener;
    }

    public final TestReference getTestReference()
    {
        return testReference;
    }

    public final I18nAutoCoreUpdatable getTestContener()
    {
        return testContener;
    }


}
