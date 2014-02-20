package com.googlecode.cchlib.apps.emptydirectories.debug;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.googlecode.cchlib.apps.duplicatefiles.IconResources;
import com.googlecode.cchlib.apps.emptydirectories.debug.lib.DualIcon;


public class DebugJCheckBoxDualIcon {

    private JCheckBox customCheckBox;

    private void createAndShowUI() {
        final JFrame frame = new JFrame();

        this.customCheckBox  = new JCheckBox();
        this.customCheckBox.setIcon( new DualIcon( getDefaultUnSelectedIcon(), getRightIcon() ) );
        frame.add(this.customCheckBox);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    private Icon getRightIcon()
    {
        Icon icon = IconResources.getInstance().getEmptyIcon();
        assert icon != null;
        return icon;
    }

    private Icon getDefaultUnSelectedIcon()
    {
        Icon icon = UIManager.getIcon("CheckBox.icon");
        assert icon != null;
        return icon;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DebugJCheckBoxDualIcon().createAndShowUI();
            }
        });
    }
}
