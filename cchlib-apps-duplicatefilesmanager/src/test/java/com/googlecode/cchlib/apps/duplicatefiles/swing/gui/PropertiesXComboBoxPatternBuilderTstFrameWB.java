// $codepro.audit.disable
package com.googlecode.cchlib.apps.duplicatefiles.swing.gui;

import com.googlecode.cchlib.swing.combobox.XComboBoxPattern;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class PropertiesXComboBoxPatternBuilderTstFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final File propertiesFile = new File( "C:\\tst.properties" );
    private final JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PropertiesXComboBoxPatternBuilderTstFrameWB frame = new PropertiesXComboBoxPatternBuilderTstFrameWB();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public PropertiesXComboBoxPatternBuilderTstFrameWB() {
        setTitle("Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);

        XComboBoxPattern comboBox = createXComboBoxPattern0();
        sl_contentPane.putConstraint(SpringLayout.NORTH, comboBox, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, comboBox, 10, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, comboBox, 310, SpringLayout.WEST, contentPane);
        contentPane.add(comboBox);

        XComboBoxPattern comboBox_1 = PropertiesXComboBoxPatternBuilderTstFrameWB.createXComboBoxPattern1();
        sl_contentPane.putConstraint(SpringLayout.NORTH, comboBox_1, 7, SpringLayout.SOUTH, comboBox);
        sl_contentPane.putConstraint(SpringLayout.EAST, comboBox_1, -121, SpringLayout.EAST, contentPane);
        contentPane.add(comboBox_1);
    }

    /**
     * @wbp.factory
     */
    public static XComboBoxPattern createXComboBoxPattern0()
    {
        XComboBoxPattern comboBoxPattern = null;
        try {
            PropertiesXComboBoxPatternBuilder builder = new PropertiesXComboBoxPatternBuilder(
                propertiesFile,
                "combo0"
                );
            comboBoxPattern = builder.createXComboBoxPattern();
            }
        catch( FileNotFoundException e ) {
            e.printStackTrace(); // Warning
            }
        catch( IOException e ) {
            e.printStackTrace(); // Warning
            }

        if( comboBoxPattern == null ) {
            comboBoxPattern = new XComboBoxPattern();
            }

        return comboBoxPattern;
    }
    /**
     * @wbp.factory
     */
    public static XComboBoxPattern createXComboBoxPattern1()
    {
        XComboBoxPattern comboBoxPattern = null;

        try {
            PropertiesXComboBoxPatternBuilder builder =
                PropertiesXComboBoxPatternBuilder.createPropertiesXComboBoxPatternBuilder(
                    propertiesFile,
                    "combo1"
                       );
            comboBoxPattern = builder.createXComboBoxPattern();
            }
        catch( IOException e ) {
            e.printStackTrace(); // Warning
            }

        if( comboBoxPattern == null ) {
            comboBoxPattern = new XComboBoxPattern();
            }

        return comboBoxPattern;
    }
}
