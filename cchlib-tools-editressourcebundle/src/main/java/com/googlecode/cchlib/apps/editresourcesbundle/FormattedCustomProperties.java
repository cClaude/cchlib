package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import cx.ath.choisnet.util.FormattedProperties;

public class FormattedCustomProperties implements CustomProperties {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public FormattedCustomProperties(FileObject fileObject,
            FormattedProperties formattedProperties) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public FileObject getFileObject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean handleLinesNumbers() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getLineNumber(String key) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getProperty(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setProperty(String key, String value) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> stringPropertyNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean store() throws FileNotFoundException, IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEdited() {
        // TODO Auto-generated method stub
        return false;
    }

}
