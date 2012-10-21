package samples.downloader;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class GenericDownloaderUIPanelEntry extends JPanel 
{
	/**
	 *
	 */
	public interface Item 
	{
		public String getJComboBoxText();
		public String getSelectedDescription();

	}
	private static final long serialVersionUID = 1L;
	private JLabel jLabelDescription;
	private JComboBox<String> jComboBox;
	private JLabel jLabelSelectedDescription;
	private List<Item> itemList = new ArrayList<>();
	/**
	 * Create the panel.
	 */
	public GenericDownloaderUIPanelEntry( )
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{32, 32, 32, 0};
		gridBagLayout.rowHeights = new int[]{14, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 2.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		jLabelDescription = new JLabel();
		GridBagConstraints gbc_jLabelDescription = new GridBagConstraints();
		gbc_jLabelDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_jLabelDescription.insets = new Insets(0, 0, 0, 5);
		gbc_jLabelDescription.anchor = GridBagConstraints.NORTH;
		gbc_jLabelDescription.gridx = 0;
		gbc_jLabelDescription.gridy = 0;
		add(jLabelDescription, gbc_jLabelDescription);
		
		jComboBox = new JComboBox<String>();
		jComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JComboBox cb = (JComboBox)e.getSource();
		        int index = jComboBox.getSelectedIndex();
		        updateLabel( index );
			}
		});
		GridBagConstraints gbc_jComboBox = new GridBagConstraints();
		gbc_jComboBox.insets = new Insets(0, 0, 0, 5);
		gbc_jComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_jComboBox.gridx = 1;
		gbc_jComboBox.gridy = 0;
		add(jComboBox, gbc_jComboBox);
		
		jLabelSelectedDescription = new JLabel();
		GridBagConstraints gbc_jLabelSelectedDescription = new GridBagConstraints();
		gbc_jLabelSelectedDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_jLabelSelectedDescription.gridx = 2;
		gbc_jLabelSelectedDescription.gridy = 0;
		add(jLabelSelectedDescription, gbc_jLabelSelectedDescription);
	}

	private void updateLabel( final int index ) 
	{
		if( index < itemList.size() ) {
			jLabelSelectedDescription.setText( itemList.get( index ).getSelectedDescription() );
			}
	}
	
	public void setDescription( final String description )
	{
		jLabelDescription.setText( description );
	}
	
	public void setJComboBoxEntry( final List<Item> itemList ) 
	{
		this.jComboBox.removeAllItems();
		this.itemList.clear();
		
		for( Item item : itemList ) {
			this.itemList.add( item );
			this.jComboBox.addItem( item.getJComboBoxText() );
			}
		updateLabel( 0 );
	}
}
