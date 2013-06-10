package cx.ath.choisnet.swing;

import java.awt.Color;
import java.awt.Font;
import java.text.MessageFormat;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;
//import com.googlecode.cchlib.i18n.AutoI18nBasicInterface;

/**
 * JSlider than showing current value in title Border
 */
public class CustomJSlider
    extends JSlider
        //implements AutoI18nBasicInterface
{
    private static transient final Logger logger = Logger.getLogger( CustomJSlider.class );
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_NUMBER_FMT_STR = "{0,number,integer}";
    // Custom title
    ///** @serial */
    //private String customTitle = "customTitle {0,number,###}";
    private String customTitle;
    private TitledBorder titledBorder;

    /**
     *
     */
    public CustomJSlider()
    {
        super();
        setCustomTitle( null );
        customInit();
    }

    /**
     * @param customTitle
     */
    public CustomJSlider(String customTitle)
    {
        super();
        setCustomTitle( customTitle );
        customInit();
    }

    /**
     * @param brm
     */
    public CustomJSlider( BoundedRangeModel brm )
    {
        super( brm );
        setCustomTitle( null );
        customInit();
    }

    /**
     * @param orientation
     */
    public CustomJSlider( int orientation )
    {
        super( orientation );
        setCustomTitle( null );
        customInit();
    }

    /**
     * @param min
     * @param max
     */
    public CustomJSlider(int min, int max)
    {
        super( min, max );
        setCustomTitle( null );
        customInit();
    }

    /**
     * @param min
     * @param max
     * @param value
     */
    public CustomJSlider( int min, int max, int value )
    {
        super( min, max, value );
        setCustomTitle( null );
        customInit();
    }

    /**
     * @param orientation
     * @param min
     * @param max
     * @param value
     */
    public CustomJSlider( int orientation, int min, int max, int value )
    {
        super( orientation, min, max, value );
        setCustomTitle( null );
        customInit();
    }

    private void customInit()
    {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                builCustomTitle(),
                TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, // ABOVE_TOP, BELOW_TOP, BELOW_BOTTOM, DEFAULT_POSITION
                new Font("Tahoma", Font.PLAIN, 11),
                Color.black
                );

        setTitledBorder( titledBorder );

        CustomJSliderListener customJSliderListener = new CustomJSliderListener();

        super.addChangeListener(customJSliderListener);
    }

    private String builCustomTitle()
    {
        MessageFormat mf = new MessageFormat( getCustomTitle() );
        Object[] objs = {
                new Integer( super.getValue() ),
                new Integer( super.getMinimum() ),
                new Integer( super.getMaximum() )
                };
        // check ! mf.getFormats().length
        return mf.format( objs );

        //return getCustomTitle() + Integer.toString( super.getValue() )
    }

    private void refreshCustomTitle()
    {
        logger.info( "refreshCustomTitle()" );
        new Thread( new Runnable() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        titledBorder.setTitle( builCustomTitle() );
                    }
                });
            }
        }).start();
    }

    public String getCustomTitle()
    {
        return customTitle;
    }

    protected String formatCustomTitle( String customTitle )
    {
        if( customTitle == null || customTitle.isEmpty() ) {
            return DEFAULT_NUMBER_FMT_STR;
            }
        
        if( customTitle.contains( "{" ) ) {
            return customTitle;
            }

        return customTitle + ' ' + DEFAULT_NUMBER_FMT_STR;
    }
    
    public void setCustomTitle( String customTitle )
    {
        this.customTitle = formatCustomTitle( customTitle );
        
        refreshCustomTitle();
    }

    @Override
    public void setBorder( Border b )
    {
        if( b == null ) {
            return;
            }

        if( !(b instanceof TitledBorder) ) {
            logger.warn( "* Border = " + b.getClass() + " - " + b );
            //throw new IllegalArgumentException( "Can handle only TitledBorder" );
            }
        else {
            setTitledBorder( TitledBorder.class.cast( b ) );
            }
    }

    public TitledBorder getTitledBorder()
    {
        return TitledBorder.class.cast( super.getBorder() );
    }

    public void setTitledBorder( final TitledBorder tb )
    {
        if( tb == null ) {
            return;
            }

        super.setBorder( tb );
        this.titledBorder = tb;
        refreshCustomTitle();
    }

    class CustomJSliderListener
        implements  ChangeListener//,
                    //MouseMotionListener,
                    //MouseListener
    {
//        private JLabel lblPop = new JLabel(Const.EMPTY_STRING,SwingConstants.CENTER);
//        public void setPop(MouseEvent me)
//        {
//          lblPop.setText( Integer.toString( CustomJSlider.this.getValue() ) );
//          pop.setLocation(
//              parent.getX() + CustomJSlider.this..getX()  +me.getX() - 10,
//              parent.getY() + CustomJSlider.this..getY()
//              );
//        }
        @Override
        public void stateChanged(ChangeEvent event)
        {
            refreshCustomTitle();
        }
//        @Override
//        public void mouseClicked(MouseEvent event){}
//        @Override
//        public void mouseEntered(MouseEvent event){}
//        @Override
//        public void mouseExited(MouseEvent event){}
//        @Override
//        public void mousePressed(MouseEvent event){}
//        @Override
//        public void mouseReleased(MouseEvent event){}
//        @Override
//        public void mouseDragged(MouseEvent event)
//        {
//            refreshCustomTitle();
//        }
//        @Override
//        public void mouseMoved(MouseEvent event){}
    }

//    @Override
//    public void setI18nString( String localString )
//    {
//        setCustomTitle( localString );
//    }
//
//    @Override
//    public String getI18nString()
//    {
//        return getCustomTitle();
//    }

}
