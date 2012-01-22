package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import com.googlecode.cchlib.swing.XComboBoxPattern;
import cx.ath.choisnet.i18n.I18nIgnore;

/**
 *
 *
 */
public class JPanelConfigFilterWB extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JPanel jPanelCheckBox;
    @I18nIgnore
    private JCheckBox jCheckBoxRegExp;
    @I18nIgnore
    private XComboBoxPattern xComboBoxPatternRegExp;

    /**
     * Create the panel.
     */
    public JPanelConfigFilterWB() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        jPanelCheckBox = new JPanel();
        GridBagConstraints gbc_jPanelCheckBox = new GridBagConstraints();
        gbc_jPanelCheckBox.insets = new Insets(0, 0, 5, 0);
        gbc_jPanelCheckBox.fill = GridBagConstraints.BOTH;
        gbc_jPanelCheckBox.gridx = 1;
        gbc_jPanelCheckBox.gridy = 0;
        add(jPanelCheckBox, gbc_jPanelCheckBox);
        jPanelCheckBox.setLayout(new BoxLayout(jPanelCheckBox, BoxLayout.Y_AXIS));

//        JCheckBox chckbxCheckbox = new JCheckBox("checkbox1");
//        jPanelCheckBox.add(chckbxCheckbox);
//
//        JCheckBox chckbxCheckbox_1 = new JCheckBox("checkbox2");
//        jPanelCheckBox.add(chckbxCheckbox_1);

        jCheckBoxRegExp = new JCheckBox();
        jCheckBoxRegExp.setHorizontalAlignment(SwingConstants.TRAILING);
        jCheckBoxRegExp.setHorizontalTextPosition( SwingConstants.LEFT );
        GridBagConstraints gbc_jCheckBoxRegExp = new GridBagConstraints();
        gbc_jCheckBoxRegExp.fill = GridBagConstraints.HORIZONTAL;
        gbc_jCheckBoxRegExp.insets = new Insets(0, 0, 0, 5);
        gbc_jCheckBoxRegExp.gridx = 0;
        gbc_jCheckBoxRegExp.gridy = 1;
        add(jCheckBoxRegExp, gbc_jCheckBoxRegExp);

        xComboBoxPatternRegExp = new XComboBoxPattern();
        GridBagConstraints gbc_xComboBoxPatternRegExp = new GridBagConstraints();
        gbc_xComboBoxPatternRegExp.fill = GridBagConstraints.HORIZONTAL;
        gbc_xComboBoxPatternRegExp.gridx = 1;
        gbc_xComboBoxPatternRegExp.gridy = 1;
        add(xComboBoxPatternRegExp, gbc_xComboBoxPatternRegExp);

    }

    protected JPanel getJPanelCheckBox() {
        return jPanelCheckBox;
    }
    protected JCheckBox getJCheckBoxRegExp() {
        return jCheckBoxRegExp;
    }
    protected XComboBoxPattern getXComboBoxPatternRegExp() {
        return xComboBoxPatternRegExp;
    }
}
