package com.googlecode.cchlib.swing.textfield;

import java.util.List;
import com.googlecode.cchlib.swing.combobox.HiddenAutoCompleteTextField;

/**
 * Handle auto completion
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class AutoCompleteTextField extends HiddenAutoCompleteTextField
{
    private static final long serialVersionUID = 1L;

    /**
     * Create an {@link AutoCompleteTextField}
     *
     * @param valuesList Values to use for completion
     */
    public AutoCompleteTextField( final List<String> valuesList )
    {
        super( valuesList );
    }
}
