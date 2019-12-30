package com.rmuhamed.params.providerlibrary;

import android.net.Uri;

public final class ParamContract {
    /** Just for clients */
    public static final String TABLE_NAME = "Param";
    public static final String AUTHORITY = "com.rmuhamed.params.app.provider";
    public static final Uri CONTENT_URI = Uri.parse(String.format("content://%s/%s", AUTHORITY, TABLE_NAME));
    /** Columns */
    public static final String ID_PARAM_KEY = "_id";
    public static final String NAME_PARAM_KEY = "name";
    public static final String FORMAT_PARAM_KEY = "format";
    /** Format types */
    public static final String ALPHANUMERIC_FORMAT = "ALPHANUMERIC";
    public static final String NUMERIC_FORMAT = "NUMERIC";
}
