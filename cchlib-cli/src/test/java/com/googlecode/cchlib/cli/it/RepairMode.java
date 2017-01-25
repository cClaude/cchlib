package com.googlecode.cchlib.cli.it;

import org.apache.commons.cli.Option;
import com.googlecode.cchlib.cli.apachecli.InGroup;
import com.googlecode.cchlib.cli.apachecli.IsOption;

public enum RepairMode implements IsOption, InGroup
{
    NONE(
        "none",
        "No DB change (optionnal for " + CLIParametersOption.CheckupRemoveExtraEntries + ")"
        ),
    TRY_TO_FIX(
        "fix",
        "Do best effort to fix DB (optionnal for " + CLIParametersOption.CheckupRemoveExtraEntries + ")"
        ),
    CLEAN(
        "clean",
        "Only remove unused info in DB (optionnal for " + CLIParametersOption.CheckupRemoveExtraEntries + ")"
        ),
    ;

    private Option option;

    private RepairMode( final String shortOption, final String description )
    {
        this.option = new Option( shortOption, description );
        this.option.setArgs( 0 );
    }

    @Override
    public Option getOption()
    {
        return this.option;
    }

    @Override
    public Object getGroupId()
    {
        // Only one of these values (All values in same group)
        return RepairMode.class;
    }
}
