package cnamts.gui;

import javax.swing.JPanel;

import com.googlecode.cchlib.swing.batchrunner.lazy.LazyBatchRunnerCustomJPanelFactory;

/**
 *
 */
public class CustomJPanelB2TransformFactory
    implements LazyBatchRunnerCustomJPanelFactory
{
    private CustomJPanelB2Transform customJPanelB2Transform;

	public CustomJPanelB2TransformFactory()
    {
    }

    @Override
    public JPanel createCustomJPanel()
    {
        // WARN: Create only once ! 
    	return getCustomJPanel();
    }

    public JPanel getCustomJPanel()
    {
    	if( this.customJPanelB2Transform == null ) {
            this.customJPanelB2Transform = new CustomJPanelB2Transform();
    		}
    	return this.customJPanelB2Transform;
    }

    @Override
    public BorderLayoutConstraints getCustomJPanelLayoutConstraints()
    {
        return BorderLayoutConstraints.NORTH;
    }

}
