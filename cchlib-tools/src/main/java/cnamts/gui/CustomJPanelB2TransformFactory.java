package cnamts.gui;

import javax.swing.JPanel;

/**
 *
 */
@Deprecated
public class CustomJPanelB2TransformFactory
     implements com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerCustomJPanelFactory
{
    private CustomJPanelB2Transform customJPanelB2Transform;

    public CustomJPanelB2Transform getCustomJPanel()
    {
        if( this.customJPanelB2Transform == null ) {
            this.customJPanelB2Transform = new CustomJPanelB2Transform();
            }
        return this.customJPanelB2Transform;
    }

    @Override//LazyBatchRunnerCustomJPanelFactory
    public JPanel createCustomJPanel()
    {
        // WARN: Create only once !
        return getCustomJPanel();
    }

    @Override//LazyBatchRunnerCustomJPanelFactory
    public BorderLayoutConstraints getCustomJPanelLayoutConstraints()
    {
        return BorderLayoutConstraints.NORTH;
    }

    @Override
    public com.googlecode.cchlib.swing.batchrunner.EnableListener getEnableListener()
    {
        return getCustomJPanel();
    }

}
