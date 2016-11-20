package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.googlecode.cchlib.lang.StringHelper;


/**
 * Implements user interface and generates FindFilter for selecting
 * files by name.
 */
class FindByNameJPanel extends JPanel implements FindFilterFactory
{
    private static final long serialVersionUID = 1L;
    protected String        NAME_CONTAINS = "contains";
    protected String        NAME_IS = "is";
    protected String        NAME_STARTS_WITH = "starts with";
    protected String        NAME_ENDS_WITH = "ends with";
    protected int           NAME_CONTAINS_INDEX = 0;
    protected int           NAME_IS_INDEX = 1;
    protected int           NAME_STARTS_WITH_INDEX = 2;
    protected int           NAME_ENDS_WITH_INDEX = 3;
    protected String[]      criteria = {NAME_CONTAINS,
                                        NAME_IS,
                                        NAME_STARTS_WITH,
                                        NAME_ENDS_WITH};

    protected JTextField        nameField = null;
    protected JComboBox<String> combo = null;
    protected JCheckBox         ignoreCaseCheck = null;

    FindByNameJPanel()
    {
        super();
        setLayout(new BorderLayout());

        // Grid Layout
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(0,2,2,2));
        // Name
        combo = createJComboBox();
        p.add(combo);

        nameField = new JTextField(12);
        nameField.setFont(new Font("Helvetica",Font.PLAIN,10));
        p.add(nameField);

        // ignore case
        p.add( new JLabel( StringHelper.EMPTY, SwingConstants.RIGHT ) );

        ignoreCaseCheck = new JCheckBox("ignore case",true);
        ignoreCaseCheck.setForeground(Color.black);
        ignoreCaseCheck.setFont(new Font("Helvetica",Font.PLAIN,10));
        p.add(ignoreCaseCheck);

        add(p,BorderLayout.NORTH);
    }

    /**
     * @wbp.factory
     */
    public JComboBox<String> createJComboBox()
    {
        JComboBox<String> comboBox = new JComboBox<String>(criteria);

        comboBox.setFont(new Font("Helvetica",Font.PLAIN,10));
        comboBox.setPreferredSize(comboBox.getPreferredSize());

        return comboBox;
    }

    @Override
    public FindFilter createFindFilter()
    {
        return new NameFilter(nameField.getText(),combo.getSelectedIndex(),
                                ignoreCaseCheck.isSelected());
    }

    /**
     * Filter object for selecting files by name.
     */
    class NameFilter implements FindFilter
    {
        protected String    match = null;
        protected int       howToMatch = -1;
        protected boolean   ignoreCase = true;

        NameFilter (String name, int how, boolean ignore)
        {
            match = name;
            howToMatch = how;
            ignoreCase = ignore;
        }

        @Override
        public boolean accept (File f, FindProgressCallback callback)
        {
            if (f == null) {
                return false;
            }

            if ((match == null) || (match.length() == 0)) {
                return true;
            }
            if (howToMatch < 0) {
                return true;
            }

            String filename = f.getName();


            if (howToMatch == NAME_CONTAINS_INDEX) {
                if (ignoreCase) {
                    return filename.toLowerCase().indexOf(match.toLowerCase()) >= 0;
                }
                else {
                    return filename.indexOf(match) >= 0;
                }
            }
            else if (howToMatch == NAME_IS_INDEX)
            {
                if (ignoreCase)
                {
                    return filename.equalsIgnoreCase(match);
                }
                else
                {
                    return filename.equals(match);
                }
            }
            else if (howToMatch == NAME_STARTS_WITH_INDEX)
            {
                if (ignoreCase)
                {
                    return filename.toLowerCase().startsWith(match.toLowerCase());
                }
                else
                {
                    return filename.startsWith(match);
                }
            }
            else if (howToMatch == NAME_ENDS_WITH_INDEX)
            {
                if (ignoreCase)
                {
                    return filename.toLowerCase().endsWith(match.toLowerCase());
                }
                else
                {
                    return filename.endsWith(match);
                }
            }

            return true;
        }
    }
}
