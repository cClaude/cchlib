package com.googlecode.cchlib.apps.duplicatefiles;

import java.awt.Image;
import java.util.Properties;
import javax.swing.Icon;

/**
 * List all application resources
 */
public interface Resources 
{
    public Image getAppIcon();
    public Icon getContinueIcon();
    public Icon getRestartIcon();
    public Icon getRemoveIcon();
    public Icon getAddIcon();
    public Icon getLogoIcon();
    public Icon getFileIcon();
    public Icon getFolderIcon();
    public Properties getJPanelConfigProperties();
    public Icon getSmallOKIcon();
    public Icon getSmallKOIcon();
    public Icon getSmallOKButOKIcon();

}