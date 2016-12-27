package com.googlecode.cchlib.apps.duplicatefiles.swing;

import java.util.Collection;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface FileFilterBuilder
{
    @Nonnull
    Collection<String> getNamePart();

    @Nullable
    Pattern getRegExp();
}
