package samples.downloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class DefaultComboBoxConfig
    implements GenericDownloaderAppInterface.ComboBoxConfig
{
    private String labelString;
    private int selectedIndex;
    private List<String> comboBoxValueList = new ArrayList<>();
    private List<String> labelStringList   = new ArrayList<>();

    /**
     *
     */
    public DefaultComboBoxConfig(
            final String   labelString,
            final String[] comboBoxValues,
            final String[] labelStrings
       )
   {
       this.labelString = labelString;

       if( comboBoxValues.length != labelStrings.length ) {
           throw new RuntimeException( "Need same number of combobox description that labels" );
           }

       for( int i = 0; i < comboBoxValues.length; i++ ) {
           this.comboBoxValueList.add( comboBoxValues[ i ] );
           this.labelStringList.add( labelStrings[ i ] );
           }
   }

    @Override
    public int getSelectedIndex()
    {
        return this.selectedIndex;
    }

    @Override
    public void setSelectedIndex( final int selectedIndex )
    {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public String getLabelString()
    {
        return this.labelString;
    }

    @Override
    public String getLabelString( final int index )
    {
        return this.labelStringList.get( index );
    }

    @Override
    public Iterable<String> getComboBoxValues()
    {
        return Collections.unmodifiableCollection( this.comboBoxValueList );
    }

    @Override
    public String getComboBoxSelectedValue()
    {
        return this.comboBoxValueList.get( selectedIndex );
    }
}
