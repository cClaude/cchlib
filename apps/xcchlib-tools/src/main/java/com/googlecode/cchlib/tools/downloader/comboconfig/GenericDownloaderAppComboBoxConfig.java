package com.googlecode.cchlib.tools.downloader.comboconfig;

import java.util.List;
import com.googlecode.cchlib.tools.downloader.GenericDownloaderUIPanelEntry;

public interface GenericDownloaderAppComboBoxConfig
{
   int getSelectedIndex();
   void setSelectedIndex( int selectedIndex );
   String getComboBoxSelectedValue();
   String getDescription();
   List<GenericDownloaderUIPanelEntry.Item> getJComboBoxEntry();
}