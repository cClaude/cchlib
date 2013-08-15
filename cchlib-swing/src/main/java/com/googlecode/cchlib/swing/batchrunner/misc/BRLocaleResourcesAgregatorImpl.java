package com.googlecode.cchlib.swing.batchrunner.misc;

import java.awt.Image;
import com.googlecode.cchlib.swing.batchrunner.ihm.BRPanelLocaleResources;

/**
 * Resources for localization
 *
 * @since 1.4.8
 * @see BRXLocaleResources
 * @see BRPanelLocaleResources
 */
public class BRLocaleResourcesAgregatorImpl 
    implements  BRLocaleResourcesAgregator
{
    private BRPanelLocaleResources panelLocaleResources;
    private BRXLocaleResources xLocaleResources;
    
    public BRLocaleResourcesAgregatorImpl(
        BRPanelLocaleResources panelLocaleResources,
        BRXLocaleResources     xLocaleResources 
        )
    {
        this.panelLocaleResources = panelLocaleResources;
        this.xLocaleResources     = xLocaleResources;
    }

    @Override//BRPanelLocaleResources
    public String getTextAddSourceFile()
    {
        return panelLocaleResources.getTextAddSourceFile();
    }

    @Override//BRPanelLocaleResources
    public String getTextSetDestinationFolder()
    {
        return panelLocaleResources.getTextSetDestinationFolder();
    }

    @Override//BRPanelLocaleResources
    public String getTextClearSourceFileList()
    {
        return panelLocaleResources.getTextClearSourceFileList();
    }

    @Override//BRPanelLocaleResources
    public String getTextDoAction()
    {
        return panelLocaleResources.getTextDoAction();
    }

    @Override//BRPanelLocaleResources
    public String getTextJFileChooserInitializerTitle()
    {
        return panelLocaleResources.getTextJFileChooserInitializerTitle();
    }

    @Override//BRPanelLocaleResources
    public String getTextJFileChooserInitializerMessage()
    {
        return panelLocaleResources.getTextJFileChooserInitializerMessage();
    }

    @Override//BRPanelLocaleResources
    public String getTextNoSourceFile()
    {
        return panelLocaleResources.getTextNoSourceFile();
    }

    @Override//BRPanelLocaleResources
    public String getTextNoDestinationFolder()
    {
        return panelLocaleResources.getTextNoDestinationFolder();
    }

    @Override//BRPanelLocaleResources
    public String getTextWorkingOn_FMT()
    {
        return panelLocaleResources.getTextWorkingOn_FMT();
    }

    @Override//BRPanelLocaleResources
    public String getTextUnexpectedExceptionTitle()
    {
        return panelLocaleResources.getTextUnexpectedExceptionTitle();
    }

    @Override//BRPanelLocaleResources
    public String getTextExitRequestTitle()
    {
        return panelLocaleResources.getTextExitRequestTitle();
    }

    @Override//BRPanelLocaleResources
    public String getTextExitRequestMessage()
    {
        return panelLocaleResources.getTextExitRequestMessage();
    }

    @Override//BRPanelLocaleResources
    public String getTextExitRequestYes()
    {
        return panelLocaleResources.getTextExitRequestYes();
    }

    @Override//BRPanelLocaleResources
    public String getTextExitRequestNo()
    {
        return panelLocaleResources.getTextExitRequestNo();
    }

    @Override//BRXLocaleResources
    public String getProgressMonitorMessage()
    {
        return xLocaleResources.getProgressMonitorMessage();
    }

    @Override//BRXLocaleResources
    public String getFrameTitle()
    {
        return xLocaleResources.getFrameTitle();
    }

    @Override
    public Image getFrameIconImage()
    {
        return xLocaleResources.getFrameIconImage();
    }
}
