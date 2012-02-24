package cnamts.gui;

import java.awt.EventQueue;

public class B2TransformApp 
{
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    B2TransformUI frame = new B2TransformUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
