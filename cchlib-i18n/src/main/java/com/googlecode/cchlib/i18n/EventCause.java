package com.googlecode.cchlib.i18n;

/**
 * Event type for {@link AutoI18n} system
 */
public enum EventCause {
    FIELD_TYPE_IS_ANNOTATION,
    FIELD_TYPE_IS_PRIMITIVE,
    //FIELD_TYPE_IS_NUMBER,
    FIELD_TYPE_IS_NOT_HANDLE,
    ANNOTATION_I18N_IGNORE_DEFINE,
    NOT_HANDLED,
    NOT_A_I18nString,
    /** v2 only */
    ERR_TOOL_TIP_NOT_A_JCOMPONENT,
    /** v2 only : other annotation already exist */
    OTHER_ANNOTATION_ALREADY_EXIST,
    //DEPRECATED,
    }