package cx.ath.choisnet.tools.emptydirectories.checkbox;

import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import cx.ath.choisnet.tools.emptydirectories.checkbox.ui.BasicXCheckboxUI;
import cx.ath.choisnet.tools.emptydirectories.checkbox.ui.XCheckboxUI;

public class JXCheckbox extends JComponent
{
    private static final long serialVersionUID = 1L;

    /**
     * The UI class ID string.
     */
    private static final String uiClassID = "FlexiSliderUI";

//    /** 
//     * Sets the new UI delegate.
//     * @param ui New UI delegate.
//     */
//    // $codepro.audit.disable unnecessaryOverride
//    public void setUI( XCheckboxUI ui )
//    {
//        super.setUI( ui );
//    }

    /**
     * Resets the UI property to a value from the current look and feel.
     * @see JComponent#updateUI
     */
    @Override
    public void updateUI()
    {
        if ( UIManager.get(getUIClassID()) != null ) {
            setUI( (XCheckboxUI) UIManager.getUI(this) );
            }
        else {
            setUI( new BasicXCheckboxUI() );
            }
    }

    /**
     * Returns the UI object which implements the L&F for this component.
     * @return UI object which implements the L&F for this component.
     * @see #setUI
     */
    public XCheckboxUI getUI()
    {
        return (XCheckboxUI) ui;
    }

    /**
     * Returns the name of the UI class that implements the L&F for this
     * component.
     *
     * @return The name of the UI class that implements the L&F for this
     *         component.
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    @Override
    public String getUIClassID()
    {
        return uiClassID;
    }
}
