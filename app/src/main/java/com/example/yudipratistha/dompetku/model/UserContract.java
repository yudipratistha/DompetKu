package com.example.yudipratistha.dompetku.model;

import android.provider.BaseColumns;

public class UserContract implements BaseColumns {
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_STATUS_SYNC = "status_sync";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.TABLE_NAME + " (" +
                    UserContract._ID + " INTEGER PRIMARY KEY," +
                    UserContract.COLUMN_NAME + " TEXT," +
                    UserContract.COLUMN_EMAIL + " TEXT," +
                    UserContract.COLUMN_STATUS_SYNC + " INTEGER," +
                    UserContract.COLUMN_CREATED_AT + " TEXT," +
                    UserContract.COLUMN_UPDATED_AT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.TABLE_NAME;
}
