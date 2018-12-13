package com.example.yudipratistha.dompetku.model;

import android.provider.BaseColumns;

public class KategoriContract implements BaseColumns {
    public static final String TABLE_NAME = "kategoris";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_NAMA_KATEGORI = "nama_kategori";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_TIPE= "tipe";
    public static final String COLUMN_STATUS_SYNC = "status_sync";
    public static final String COLUMN_STATUS_UPDATE = "status_update";
    public static final String COLUMN_STATUS_DELETE = "status_delete";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + KategoriContract.TABLE_NAME + " (" +
                    KategoriContract._ID + " INTEGER PRIMARY KEY," +
                    KategoriContract.COLUMN_ID_USER + " INTEGER," +
                    KategoriContract.COLUMN_NAMA_KATEGORI + " TEXT," +
                    KategoriContract.COLUMN_ICON + " TEXT," +
                    KategoriContract.COLUMN_TIPE + " TEXT," +
                    KategoriContract.COLUMN_STATUS_SYNC + " INTEGER," +
                    TransaksiContract.COLUMN_STATUS_UPDATE + " INTEGER," +
                    TransaksiContract.COLUMN_STATUS_DELETE + " INTEGER," +
                    KategoriContract.COLUMN_CREATED_AT + " TEXT," +
                    KategoriContract.COLUMN_UPDATED_AT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + KategoriContract.TABLE_NAME;
}
