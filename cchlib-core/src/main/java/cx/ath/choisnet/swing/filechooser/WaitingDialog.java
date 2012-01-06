package cx.ath.choisnet.swing.filechooser;

/**
 *
 *
 */
public interface WaitingDialog
{
    /**
     *
     * @param string
     */
    public void setText( String string );

    /**
     *
     * @param title
     */
    public void setTitle( String title );

    /**
     *
     */
    public void dispose();

    /**
     *
     * @param disposeOnClose
     */
    public void setDefaultCloseOperation(int disposeOnClose);

    /**
     *
     * @param b
     */
    public void setVisible(boolean b);

}
