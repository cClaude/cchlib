package cx.ath.choisnet.swing.filechooser.accessory;

import java.awt.Component;
import java.io.Serializable;
import javax.swing.Icon;

/**
 * 
 * @author Claude CHOISNET
 */
public interface TabbedAccessoryInterface 
    extends Serializable
{
    /**
     * @return String (or null) for tab name
     */
    public String getTabName();
    
    /**
     * @return Icon (or null) for tab icon
     */
    public Icon getTabIcon();
    
    /**
     * @return Component
     */
    public Component getComponent();
    
    /**
     * Register Component, to be active when tab is selected
     */
    public void register();

    /**
     * Unregister Component, to be inactive when tab is unselected.
     * <br/>
     * Must be work, even if Component is not register.
     */
    public void unregister();

}
