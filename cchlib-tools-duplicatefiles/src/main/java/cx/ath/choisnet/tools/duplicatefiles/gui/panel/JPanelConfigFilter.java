package cx.ath.choisnet.tools.duplicatefiles.gui.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import org.apache.log4j.Logger;

/**
 *
 *
 */
public class JPanelConfigFilter
    extends JPanelConfigFilterWB
        implements Iterable<FileTypeCheckBox>
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( JPanelConfigFilter.class );
    private static final  Color ERRORCOLOR = Color.RED;
    /** @serial */
    private Map<Integer,FileTypeCheckBox> fileTypeCheckBoxMap
        = new HashMap<Integer,FileTypeCheckBox>();

    /**
     *
     */
    public JPanelConfigFilter(
        final String titleBorderText,
        final String regExpText
        )
    {
        super();

        setBorder(
            BorderFactory.createTitledBorder(
                    null,
                    titleBorderText,
                    TitledBorder.LEADING,
                    TitledBorder.DEFAULT_POSITION,
                    new Font( "Dialog", Font.BOLD, 12),
                    new Color(51, 51, 51)
                    )
                );

        ActionListener l = new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                getXComboBoxPatternRegExp().setEnabled(
                        getJCheckBoxRegExp().isSelected()
                        );
            }
        };

        getJCheckBoxRegExp().setText( regExpText );
        getJCheckBoxRegExp().addActionListener( l );
        getJCheckBoxRegExp().setSelected( false );
        getXComboBoxPatternRegExp().setErrorBackGroundColor( ERRORCOLOR );
        getXComboBoxPatternRegExp().setEnabled( false );
    }

    /**
     *
     */
    public JPanelConfigFilter(
        final String            titleBorderText,
        final String            regExpText,
        final Properties        properties,
        final String            prefix,
        final ActionListener    listener
        )
    {
        this( titleBorderText, regExpText );

        for(int i=0;;i++) {
            String descKey = String.format( "%s.%d.description", prefix, i );
            String dataKey = String.format( "%s.%d.data", prefix, i );
            String desc    = properties.getProperty( descKey );
            String data    = properties.getProperty( dataKey );

            if( desc != null && data != null ) {
                FileTypeCheckBox box = new FileTypeCheckBox( desc, data );

                fileTypeCheckBoxMap.put( i, box );
                getJPanelCheckBox().add( box.getJCheckBox() );
                }
            else {
                logger.info( "Can't find :" +  descKey );
                logger.info( "or         :" +  dataKey );
                logger.info( "desc :" + desc );
                logger.info( "data :" + data );
                break;
                }
            }
    }

    /**
     * @return the JCheckBox for giving key
     */
    public FileTypeCheckBox getFileTypeCheckBox( final int key )
    {
        return fileTypeCheckBoxMap.get( key );
    }

    @Override
    public Iterator<FileTypeCheckBox> iterator()
    {
        return fileTypeCheckBoxMap.values().iterator();
    }
}
