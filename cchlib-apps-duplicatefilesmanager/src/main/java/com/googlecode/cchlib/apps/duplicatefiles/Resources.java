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
    public Icon getAddIcon();
    public Icon getAppIcon();
    public Icon getContinueIcon();
    public Icon getDeselectAllIcon();
    public Icon getFileIcon();
    public Icon getFolderImportIcon();
    public Icon getFolderRemoveIcon();
    public Icon getFolderSelectIcon();
    public Icon getLogoIcon();
    public Icon getNextIcon();
    public Icon getPrevIcon();
    public Icon getRefreshIcon();
    public Icon getRestartIcon();
    public Icon getSelectAllIcon();
    public Icon getSmallKOIcon();
    public Icon getSmallOKButOKIcon();
    public Icon getSmallOKIcon();

    public Image getAppImage();

    public Properties getJPanelConfigProperties();

    public String getAboutVersion();
    public String getAboutVersionDate();
    public String getAuthorName();

    public URI getSiteURI();
}
