package com.googlecode.cchlib.tools.downloader;

import java.util.List;

/**
 * TODOC
 *
 */
public interface GenericDownloaderAppComboBoxConfig
{
   /**
    *
    * @return TODOC
    */
   int getSelectedIndex();

   /**
    *
    * @param selectedIndex
    */
   void setSelectedIndex( int selectedIndex );

   /**
    *
    * @return TODOC
    */
   String getComboBoxSelectedValue();

   /**
   *
   * @return TODOC
   */
   String getDescription();

   /**
   *
   * @return TODOC
   */
   List<GenericDownloaderUIPanelEntry.Item> getJComboBoxEntry();
}