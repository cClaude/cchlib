package com.googlecode.cchlib.i18n.prep;

import java.io.File;

final class I18nPrepResultImpl implements I18nPrepResult {
    private final PrepCollector<String>  notUseCollector;
    private final File                   outputFile;
    private final PrepCollector<Integer> usageStatCollector;
    private final I18nPrepStatResult     i18nPrepStatResult;

    I18nPrepResultImpl(
            final PrepCollector<String> notUseCollector,
            final File outputFile,
            final PrepCollector<Integer> usageStatCollector,
            final I18nPrepStatResult i18nPrepStatResult )
    {
        this.notUseCollector = notUseCollector;
        this.outputFile = outputFile;
        this.usageStatCollector = usageStatCollector;
        this.i18nPrepStatResult = i18nPrepStatResult;
    }

    @Override
    public PrepCollector<Integer> getUsageStatCollector()
    {
        return this.usageStatCollector;
    }

    @Override
    public PrepCollector<String> getNotUseCollector()
    {
        return this.notUseCollector;
    }

    @Override
    public File getOutputFile()
    {
        return this.outputFile;
    }

    @Override
    public I18nPrepStatResult getI18nPrepStatResult()
    {
        return this.i18nPrepStatResult;
    }
}