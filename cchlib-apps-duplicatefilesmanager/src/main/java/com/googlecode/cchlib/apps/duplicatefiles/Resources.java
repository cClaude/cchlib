package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Image;
import java.net.URI;
import java.util.Properties;
import javax.swing.Icon;

/**
 * List all application resources
 */
public interface Resources
{
    Icon getAddIcon();
    Icon getAppIcon();
    Icon getContinueIcon();
    Icon getDeselectAllIcon();
    Icon getFileIcon();
    Icon getFolderImportIcon();
    Icon getFolderRemoveIcon();
    Icon getFolderSelectIcon();
    Icon getLogoIcon();
    Icon getNextIcon();
    Icon getPrevIcon();
    Icon getRefreshIcon();
    Icon getRestartIcon();
    Icon getSelectAllIcon();
    Icon getSmallKOIcon();
    Icon getSmallOKButOKIcon();
    Icon getSmallOKIcon();

    Image getAppImage();

    Properties getJPanelConfigProperties(); // $codepro.audit.disable declareAsInterface

    String getAboutVersion();
    String getAboutVersionDate();
    String getAuthorName();

    URI getSiteURI();
}
