package com.googlecode.cchlib.swing.batchrunner;

/**
 * Define how to leave application
 */
@FunctionalInterface
public interface BRExitable
{
    /**
     * Method that leave the application
     */
    void exit();
}
