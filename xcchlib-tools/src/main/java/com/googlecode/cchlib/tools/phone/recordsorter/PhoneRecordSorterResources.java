package com.googlecode.cchlib.tools.phone.recordsorter;

import java.awt.Image;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.BRLocaleResourcesAgregator;
import com.googlecode.cchlib.tools.Tools;

public class PhoneRecordSorterResources extends DefaultBRLocaleResources implements BRLocaleResourcesAgregator
{
    private ResourceBundle resourceBundle;

    public PhoneRecordSorterResources()
    {
        this.resourceBundle = ResourceBundle.getBundle(
                PhoneRecordSorterResources.class.getPackage().getName()
                    + ".ResourceBundle"
                );
    }

    @Override
    public String getProgressMonitorMessage()
    {
        return getLocaleString( "ProgressMonitorMessage" );
    }

    @Override
    public String getFrameTitle()
    {
        return getLocaleString( "FrameTitle" );
    }

    private String getLocaleString( final String key )
    {
        try {
            return resourceBundle.getString( key );
        } catch( MissingResourceException e ) {
            throw new MissingLocaleStringException( key, e );
        }
    }

    @Override
    public Image getFrameIconImage()
    {
        return Tools.getToolsIconImage();
    }
}
