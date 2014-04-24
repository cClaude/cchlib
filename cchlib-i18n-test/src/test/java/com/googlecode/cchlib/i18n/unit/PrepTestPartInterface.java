package com.googlecode.cchlib.i18n.unit;

import java.io.PrintStream;

import com.googlecode.cchlib.i18n.core.I18nAutoCoreUpdatable;
import com.googlecode.cchlib.i18n.core.I18nPrep;
import com.googlecode.cchlib.i18n.unit.util.AutoI18nExceptionCollector;

public interface PrepTestPartInterface
{
    void add(I18nAutoCoreUpdatable frame);

    I18nPrep getAutoI18n();
    PrintStream getUsageStatPrintStream();
    PrintStream getNotUsePrintStream();
    I18nAutoCoreUpdatable[] getI18nConteners();

    AutoI18nExceptionCollector getAutoI18nExceptionHandlerCollector();
}
