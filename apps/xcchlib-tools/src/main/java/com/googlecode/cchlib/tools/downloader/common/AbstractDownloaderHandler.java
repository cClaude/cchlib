package com.googlecode.cchlib.tools.downloader.common;

import java.util.Collection;
import java.util.Collections;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppButton;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderAppComboBoxConfig;

public abstract class AbstractDownloaderHandler implements DownloaderHandler
{
    private GenericDownloaderAppButton buttonConfig;
    private Collection<GenericDownloaderAppComboBoxConfig> comboBoxConfigList;

    @Override
    public GenericDownloaderAppButton getButtonConfig()
    {
        return this.buttonConfig;
    }

    @Override
    public Collection<GenericDownloaderAppComboBoxConfig> getComboBoxConfigCollection()
    {
        if( this.comboBoxConfigList == null ) {
            return Collections.emptyList();
            }

        return this.comboBoxConfigList;
    }
}
