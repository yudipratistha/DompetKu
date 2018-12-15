package com.example.yudipratistha.dompetku.model;

import android.provider.BaseColumns;

public class TransaksiContract implements BaseColumns {
    public static final String TABLE_NAME = "transaksis";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_ID_KATEGORI = "id_kategori";
    public static final String COLUMN_TANGGAL = "tanggal";
    public static final String COLUMN_CATATAN = "catatan";
    public static final String COLUMN_JUMLAH = "jumlah";
    public static final String COLUMN_STATUS_SYNC = "status_sync";
    public static final String COLUMN_STATUS_UPDATE = "status_update";
    public static final String COLUMN_STATUS_DELETE = "status_delete";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TransaksiContract.TABLE_NAME + " (" +
                    TransaksiContract._ID + " INTEGER PRIMARY KEY," +
                    TransaksiContract.COLUMN_ID_USER + " INTEGER," +
                    TransaksiContract.COLUMN_ID_KATEGORI + " INTEGER," +
                    TransaksiContract.COLUMN_TANGGAL + " TEXT," +
                    TransaksiContract.COLUMN_CATATAN + " TEXT DEFAULT '-'," +
                    TransaksiContract.COLUMN_JUMLAH + " TEXT," +
                    TransaksiContract.COLUMN_STATUS_SYNC + " INTEGER," +
                    TransaksiContract.COLUMN_STATUS_UPDATE + " INTEGER," +
                    TransaksiContract.COLUMN_STATUS_DELETE + " INTEGER," +
                    TransaksiContract.COLUMN_CREATED_AT + " TEXT," +
                    TransaksiContract.COLUMN_UPDATED_AT + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TransaksiContract.TABLE_NAME;
}
