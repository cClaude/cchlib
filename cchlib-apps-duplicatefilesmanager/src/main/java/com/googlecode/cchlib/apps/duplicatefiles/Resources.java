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
    public Image getAppImage();
    public Icon getAppIcon();
    public Icon getContinueIcon();
    public Icon getRestartIcon();
    public Icon getRemoveIcon();
    public Icon getAddIcon();
    public Icon getLogoIcon();
    public Icon getFileIcon();
    public Icon getFolderIcon();
    public Icon getPrevIcon();
    public Icon getNextIcon();
    public Icon getRefreshIcon();
    public Properties getJPanelConfigProperties();
    public Icon getSmallOKIcon();
    public Icon getSmallKOIcon();
    public Icon getSmallOKButOKIcon();
    public String getAuthorName();
    public String getAboutVersion();
    public URI getSiteURI();
    public String getAboutVersionDate();
}
