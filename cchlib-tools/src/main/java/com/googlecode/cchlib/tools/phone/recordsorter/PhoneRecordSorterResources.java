package samples.batchrunner.phone.recordsorter;

import java.awt.Image;
import java.util.ResourceBundle;
import samples.Samples;
import com.googlecode.cchlib.swing.batchrunner.ihm.DefaultBRLocaleResources;
import com.googlecode.cchlib.swing.batchrunner.misc.BRLocaleResourcesAgregator;

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
        return resourceBundle.getString( "ProgressMonitorMessage" );
    }

    @Override
    public String getFrameTitle()
    {
        return resourceBundle.getString( "FrameTitle" );
    }

    @Override
    public Image getFrameIconImage()
    {
        return Samples.getSampleIconImage();
    }
}
