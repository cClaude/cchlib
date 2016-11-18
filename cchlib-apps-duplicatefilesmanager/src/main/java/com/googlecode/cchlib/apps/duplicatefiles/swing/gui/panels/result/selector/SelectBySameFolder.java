// $codepro.audit.disable numericLiterals
package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.result.selector;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.swing.KeyFileState;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.util.HashMapSet;

@I18nName("JPanelResult.SelectBySameFolder")
@SuppressWarnings({"squid:MaximumInheritanceDepth","squid:S00117"})
public class SelectBySameFolder extends SelectorPanel
{
    private static final Comparator<KeyFileState> FIRST_NAME_ALPHA_ORDER_IGNORE_EXT_COMPARATOR = (final KeyFileState o1, final KeyFileState o2) -> o1.getFileNameWithoutExtention().compareTo( o2.getFileNameWithoutExtention() );
    private static final Comparator<KeyFileState> FIRST_NAME_ALPHA_ORDER_INCLUDE_EXTENTION_COMPARATOR = (final KeyFileState o1, final KeyFileState o2) -> o1.getFileNameWithExtention().compareTo( o2.getFileNameWithExtention() );
    private static final Comparator<KeyFileState> MOST_RECENT_COMPARATOR = (final KeyFileState o1, final KeyFileState o2) -> (o1.getLastModified() > o2.getLastModified()) ? 1 : -1;
    private static final Comparator<KeyFileState> OLDEST_COMPARATOR = (final KeyFileState o1, final KeyFileState o2) -> (o1.getLastModified() < o2.getLastModified()) ? 1 : -1;

    private enum Mode {
        KEEP_FIRST_NAME_ALPHA_ORDER_IGNORE_EXT(FIRST_NAME_ALPHA_ORDER_IGNORE_EXT_COMPARATOR),
        KEEP_FIRST_NAME_ALPHA_ORDER_INCLUDE_EXTENTION(FIRST_NAME_ALPHA_ORDER_INCLUDE_EXTENTION_COMPARATOR),
        KEEP_MOST_RECENT(MOST_RECENT_COMPARATOR),
        KEEP_OLDEST(OLDEST_COMPARATOR),
        ;
        private final Comparator<KeyFileState> comparator;

        private Mode(final Comparator<KeyFileState> comparator)
        {
            this.comparator = comparator;
        }

        protected Comparator<KeyFileState> getComparator()
        {
            return this.comparator;
        }
    }

    @FunctionalInterface
    private interface ActionPerform
    {
        void perform( Set<KeyFileState> set, KeyFileState selected );
    }

    public static final ActionPerform DELETE_ACTION =
        (final Set<KeyFileState> set, final KeyFileState selected) -> {
            for( final KeyFileState f : set ) {
                selected.setSelectedToDelete( f != selected );
                }
            };
    public static final ActionPerform RESTORE_ACTION =
        (final Set<KeyFileState> set, final KeyFileState selected) ->
            selected.setSelectedToDelete( false );


    private enum Action {
        DELETE(DELETE_ACTION),
        RESTORE(RESTORE_ACTION);

        private final ActionPerform actionPerform;
        private Action( final ActionPerform actionPerform )
        {
            this.actionPerform = actionPerform;
        }
        public void perform( final Set<KeyFileState> set, final KeyFileState selected )
        {
            this.actionPerform.perform( set, selected );
        }
    }

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( SelectBySameFolder.class );

    private final DuplicateData duplicateData;

    @I18nString private final String[] modes = {
            "Keep first name (alpha order, ignore extension)",
            "Keep first name (alpha order, with extension)",
            "Keep most recent file",
            "Keep oldest file",
            };
    private final JComboBox<String> modeComboBox;
    private final JButton deleteButton;
    private final JButton retoreButton;

    @SuppressWarnings({"squid:S3346","squid:S1199"})
    public SelectBySameFolder( final DuplicateData duplicateData )
    {
        assert Mode.values().length == this.modes.length;

        this.duplicateData = duplicateData;

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{80, 10, 0, 0, 80, 0};
        gridBagLayout.rowHeights = new int[]{23, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        {
            this.modeComboBox = new JComboBox<>();
            this.modeComboBox.setModel(new DefaultComboBoxModel<>(this.modes));
            final GridBagConstraints gbc_modeComboBox = new GridBagConstraints();
            gbc_modeComboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_modeComboBox.insets = new Insets(0, 0, 0, 5);
            gbc_modeComboBox.gridx = 0;
            gbc_modeComboBox.gridy = 0;
            add(this.modeComboBox, gbc_modeComboBox);
        }
        {
            this.deleteButton = new JButton("Delete");
            this.deleteButton.setToolTipText("Move files to the to delete list");
            this.deleteButton.addActionListener( (final ActionEvent e) -> onDelete() );

            final GridBagConstraints gbc_deleteButton = new GridBagConstraints();
            gbc_deleteButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_deleteButton.anchor = GridBagConstraints.NORTH;
            gbc_deleteButton.gridx = 4;
            gbc_deleteButton.gridy = 0;
            add(this.deleteButton, gbc_deleteButton);
        }
        {
            this.retoreButton = new JButton("Restore");
            this.retoreButton.addActionListener((final ActionEvent e) -> onRestore() );

            final GridBagConstraints gbc_retoreButton = new GridBagConstraints();
            gbc_retoreButton.fill = GridBagConstraints.HORIZONTAL;
            gbc_retoreButton.insets = new Insets(0, 0, 0, 5);
            gbc_retoreButton.gridx = 3;
            gbc_retoreButton.gridy = 0;
            add(this.retoreButton, gbc_retoreButton);
        }
    }

    private Mode getMode()
    {
        return Mode.values()[ this.modeComboBox.getSelectedIndex() ];
    }

    private void onApply(final Action action, final Mode mode)
    {
        if( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "onApply : " + action + " : " + mode );
            }
        final HashMapSet<File,KeyFileState> byFolderMapSet = new HashMapSet<>();

        for( final Entry<String,Set<KeyFileState>> duplicateEntry : this.duplicateData.getListModelDuplicatesFiles().getStateEntrySet() ) {
            final Set<KeyFileState> duplicateFiles = duplicateEntry.getValue();

            byFolderMapSet.clear();

            // Sort file by folders
            for( final KeyFileState f : duplicateFiles ) {
                final File folder = f.getParentFile();

                byFolderMapSet.add( folder, f );
                }

            //
            for( final Set<KeyFileState> set : byFolderMapSet.values() ) {
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

    private static KeyFileState findMax(
        final Collection<KeyFileState> collection,
        final Comparator<KeyFileState> comparator
        )
    {
        KeyFileState max = null;

        for( final KeyFileState entry : collection ) {
            if( (max == null) || (comparator.compare( entry, max ) > 0) ) {
                max = entry;
            }
        }
        return max;
    }
}
