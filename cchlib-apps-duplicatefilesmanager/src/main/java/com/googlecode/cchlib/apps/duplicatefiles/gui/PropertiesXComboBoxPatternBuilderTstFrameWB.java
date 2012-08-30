package com.googlecode.cchlib.apps.duplicatefiles.gui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import com.googlecode.cchlib.swing.XComboBoxPattern;

public class PropertiesXComboBoxPatternBuilderTstFrameWB extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static File propertiesFile = new File( "C:\\tst.properties" );
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PropertiesXComboBoxPatternBuilderTstFrameWB frame = new PropertiesXComboBoxPatternBuilderTstFrameWB();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            }

        if( comboBoxPattern == null ) {
            comboBoxPattern = new XComboBoxPattern();
            }

        return comboBoxPattern;
    }
}
