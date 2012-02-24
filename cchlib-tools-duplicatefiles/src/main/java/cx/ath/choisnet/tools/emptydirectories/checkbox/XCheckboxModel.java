package cx.ath.choisnet.tools.emptydirectories.checkbox;

import javax.swing.event.ChangeListener;

public interface XCheckboxModel
{
    /**
     * Adds a ChangeListener to the model's listener list.
     *
     * @param l the ChangeListener to add
     * @see #removeChangeListener
     */
    void addChangeListener(ChangeListener l);

    /**
     * Removes a ChangeListener from the model's listener list.
     *
     * @param l the ChangeListener to remove
     * @see #addChangeListener
     */
    void removeChangeListener(ChangeListener l);

}
