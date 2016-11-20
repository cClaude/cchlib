package com.googlecode.cchlib.util;

/**
 * The result type of a {@link Visitor}.
 */
public enum VisitResult
{
    /**
     * Continue.
     */
    CONTINUE,
    /**
     * Continue without visiting the siblings entry.
     */
    SKIP_SIBLINGS,
    /**
     * Continue without visiting the child entries (if
     * any) in this entry.
     */
    SKIP_SUBTREE,
    /**
     * Terminate.
     */
    TERMINATE
}
