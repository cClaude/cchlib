// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result.selector;

import com.googlecode.cchlib.apps.duplicatefiles.DFToolKit;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.util.HashMapSet;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;

@I18nName("JPanelResult.SelectBySameFolder")
public class SelectBySameFolder extends SelectorPanel
{
    private static final Comparator<KeyFileState> FIRST_NAME_ALPHA_ORDER_IGNORE_EXT_COMPARATOR = new Comparator<KeyFileState>() {
        @Override
        public int compare( final KeyFileState o1, final KeyFileState o2 )
        {
            return o1.getFileNameWithoutExtention().compareTo( o2.getFileNameWithoutExtention() );
        }};
        private static final Comparator<KeyFileState> FIRST_NAME_ALPHA_ORDER_INCLUDE_EXTENTION_COMPARATOR = new Comparator<KeyFileState>() {
            @Override
            public int compare( final KeyFileState o1, final KeyFileState o2 )
            {
                return o1.getFileNameWithExtention().compareTo( o2.getFileNameWithExtention() );
            }};
    private static final Comparator<KeyFileState> MOST_RECENT_COMPARATOR = new Comparator<KeyFileState>() {
        @Override
        public int compare( final KeyFileState o1, final KeyFileState o2 )
        {
            return (o1.getFile().lastModified() > o2.getFile().lastModified()) ? 1 : -1;
        }};
    private static final Comparator<KeyFileState> OLDEST_COMPARATOR = new Comparator<KeyFileState>() {
        @Override
        public int compare( final KeyFileState o1, final KeyFileState o2 )
        {
            return (o1.getFile().lastModified() < o2.getFile().lastModified()) ? 1 : -1;
        }};

    private enum Mode {
        KEEP_FIRST_NAME_ALPHA_ORDER_IGNORE_EXT(FIRST_NAME_ALPHA_ORDER_IGNORE_EXT_COMPARATOR),
        KEEP_FIRST_NAME_ALPHA_ORDER_INCLUDE_EXTENTION(FIRST_NAME_ALPHA_ORDER_INCLUDE_EXTENTION_COMPARATOR),
        KEEP_MOST_RECENT(MOST_RECENT_COMPARATOR),
        KEEP_OLDEST(OLDEST_COMPARATOR),
        ;
        private Comparator<KeyFileState> comparator;

        private Mode(Comparator<KeyFileState> comparator)
        {
            this.comparator = comparator;
        }

        protected Comparator<KeyFileState> getComparator()
        {
            return comparator;
        }
    }

    private interface ActionPerform
    {
        void perform( Set<KeyFileState> set, KeyFileState selected );
    }

    public static final ActionPerform DELETE_ACTION = new ActionPerform(){
        @Override
        public void perform( Set<KeyFileState> set, KeyFileState selected )
        {
            // if delete -> select others for delete
            for( KeyFileState f : set ) {
                selected.setSelectedToDelete( f != selected );
                }
        }};
    public static final ActionPerform RESTORE_ACTION = new ActionPerform(){
        @Override
        public void perform( Set<KeyFileState> set, KeyFileState selected )
        {
            // if keep -> mask this file as not delete (ignore others)
            selected.setSelectedToDelete( false );
        }};

    private enum Action {
        DELETE(DELETE_ACTION),
        RESTORE(RESTORE_ACTION);

        private ActionPerform actionPerform;
        private Action( final ActionPerform actionPerform )
        {
            this.actionPerform = actionPerform;
        }
        public void perform( Set<KeyFileState> set, KeyFileState selected )
        {
            this.actionPerform.perform( set, selected );
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( SelectBySameFolder.class );

    private DuplicateData duplicateData;

    @I18nString private String[] modes = {
            "Keep first name (alpha order, ignore extension)",
            "Keep first name (alpha order, with extension)",
            "Keep most recent file",
            "Keep oldest file",
            };
    private JComboBox<String> modeComboBox;
    private JButton deleteButton;
    private JButton retoreButton;

    public SelectBySameFolder( DFToolKit dFToolKit, DuplicateData duplicateData )
    {
        assert Mode.values().length == modes.length;

        this.duplicateData = duplicateData;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{80, 10, 0, 0, 80, 0};
        gridBagLayout.rowHeights = new int[]{23, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            this.modeComboBox = new JComboBox<String>();
            this.modeComboBox.setModel(new DefaultComboBoxModel<String>(modes));
            GridBagConstraints gbc_modeComboBox = new GridBagConstraints();
            gbc_modeComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_modeComboBox.insets = new Insets(0, 0, 0, 5);
            gbc_modeComboBox.gridx = 0;
            gbc_modeComboBox.gridy = 0;
            add(this.modeComboBox, gbc_modeComboBox);
        }
        {
            this.deleteButton = new JButton("Delete");
            this.deleteButton.setToolTipText("Move files to the to delete list");
            this.deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onDelete();
                }
            });
            GridBagConstraints gbc_deleteButton = new GridBagConstraints();
            gbc_deleteButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_deleteButton.anchor = GridBagConstraints.NORTH;
            gbc_deleteButton.gridx = 4;
            gbc_deleteButton.gridy = 0;
            add(this.deleteButton, gbc_deleteButton);
        }
        {
            this.retoreButton = new JButton("Restore");
            this.retoreButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onRestore();
                }
            });
            GridBagConstraints gbc_retoreButton = new GridBagConstraints();
            gbc_retoreButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_retoreButton.insets = new Insets(0, 0, 0, 5);
            gbc_retoreButton.gridx = 3;
            gbc_retoreButton.gridy = 0;
            add(this.retoreButton, gbc_retoreButton);
        }
    }

    private Mode getMode()
    {
        return Mode.values()[ modeComboBox.getSelectedIndex() ];
    }

    private void onApply(final Action action, final Mode mode)
    {
        if( logger.isDebugEnabled() ) {
            logger.debug( "onApply : " + action + " : " + mode );
            }
        final HashMapSet<File,KeyFileState> byFolderMapSet = new HashMapSet<>();

        for( Entry<String,Set<KeyFileState>> duplicateEntry : this.duplicateData.getListModelDuplicatesFiles().getStateEntrySet() ) {
            final Set<KeyFileState> duplicateFiles = duplicateEntry.getValue();

            byFolderMapSet.clear();

            // Sort file by folders
            for( KeyFileState f : duplicateFiles ) {
                File folder = f.getFile().getParentFile();

                byFolderMapSet.add( folder, f );
                }

            //
            for( Set<KeyFileState> set : byFolderMapSet.values() ) {
                if( set.size() > 1 ) {
                    // More than duplicate in this folder

                    // Find files to delete/keep
                    final KeyFileState selected = findMax( set, mode.getComparator() );

                    // if delete -> select others for delete
                    // if keep -> mask this file as not delete (ignore others)
                    action.perform( set, selected );
                    }
                }
            }

        this.duplicateData.updateDisplay();
    }

    private void onDelete()
    {
        onApply( Action.DELETE, getMode() );
    }

    private void onRestore()
    {
        onApply( Action.RESTORE, getMode() );
    }

    @Override
    public void updateDisplay()
    {
        // Empty (Nothing specific according user level?)
    }

    private KeyFileState findMax(
        final Collection<KeyFileState> collection,
        final Comparator<KeyFileState> comparator
        )
    {
        KeyFileState max = null;

        for( KeyFileState entry : collection ) {
            if( max == null ) {
                max = entry;
            } else if( comparator.compare( entry, max ) > 0 ) {
                max = entry;
            }
        }
        return max;
    }

//    @Override
//    public void performeI18n( AutoI18nCore autoI18n )
//    {
//        // TODO Auto-generated method stub
//
//    }
}
