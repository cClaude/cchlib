package com.googlecode.cchlib.apps.emptyfiles.interfaces;

import java.io.File;
import java.io.Serializable;

public interface FileInfoFormater extends Serializable
{
    String formatAttributs( File file );
    String formatAttributsDelete();
    String formatLength( File file );
}
