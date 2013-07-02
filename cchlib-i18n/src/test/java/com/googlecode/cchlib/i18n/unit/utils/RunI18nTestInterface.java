package com.googlecode.cchlib.i18n.unit.utils;

import java.io.PrintStream;
import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;

public interface RunI18nTestInterface extends I18nAutoCoreUpdatable
{
    public interface PrepTest
    {
        public void add(I18nAutoCoreUpdatable frame);

        public I18nPrep getAutoI18n();
        public PrintStream getUsageStatPrintStream();
        public PrintStream getNotUsePrintStream();
        public I18nAutoCoreUpdatable[] getI18nConteners();

        public AutoI18nExceptionCollector getAutoI18nExceptionHandlerCollector();
    }

    public void beforePrepTest( PrepTest prepTest );
    public void afterPrepTest();
    public void runPerformeI18nTest();

    public int getSyntaxeExceptionCount();
    public int getMissingResourceExceptionCount();
}
