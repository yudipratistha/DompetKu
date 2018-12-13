package com.example.yudipratistha.dompetku.sqllite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.yudipratistha.dompetku.R;
import com.example.yudipratistha.dompetku.model.KategoriContract;
import com.example.yudipratistha.dompetku.model.LihatKategoriItem;
import com.example.yudipratistha.dompetku.model.LihatTransaksiItem;
import com.example.yudipratistha.dompetku.model.TransaksiContract;
import com.example.yudipratistha.dompetku.model.User;
import com.example.yudipratistha.dompetku.model.UserContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DompetkuSqLite extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 23;
    public static final String DATABASE_NAME = "Dompetku.db";
    public static DompetkuSqLite instance;

    private DompetkuSqLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DompetkuSqLite getInstance(Context context){
        if (instance == null){
            return new DompetkuSqLite(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TransaksiContract.SQL_CREATE_ENTRIES);
        db.execSQL(KategoriContract.SQL_CREATE_ENTRIES);
        db.execSQL(UserContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TransaksiContract.SQL_DELETE_ENTRIES);
        db.execSQL(KategoriContract.SQL_DELETE_ENTRIES);
        db.execSQL(UserContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    //Transaksi
    public List<LihatTransaksiItem> getTransaksiMonth(String month){
        List<LihatTransaksiItem> transaksis = new ArrayList<>();
        String qry = "SELECT * FROM " + TransaksiContract.TABLE_NAME + " WHERE " + "strftime('%Y %m' ," +  TransaksiContract.COLUMN_TANGGAL + ")= '"+ month +"'" + " ORDER BY " + TransaksiContract.COLUMN_TANGGAL + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                LihatTransaksiItem transaksi = new LihatTransaksiItem();

                transaksi.setId(cursor.getInt(cursor.getColumnIndex(TransaksiContract._ID)));
                transaksi.setIdUser(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_ID_USER)));
                transaksi.setIdKategori(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_ID_KATEGORI)));
                transaksi.setTanggal(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_TANGGAL)));
                transaksi.setCatatan(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_CATATAN)));
                transaksi.setJumlah(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_JUMLAH)));
                transaksi.setStatusSync(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_STATUS_SYNC)));
                transaksi.setCreatedAt(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_CREATED_AT)));
                transaksi.setUpdatedAt(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_UPDATED_AT)));
                transaksis.add(transaksi);
            } while (cursor.moveToNext());
        }
        db.close();
        return transaksis;
    }

    public int getTransaksiTotal(String month){
        int total= 0;
        String qry = "SELECT ifnull\n" +
                            "   ((SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori`\n" +
                            "    WHERE tipe = \"Pemasukan\" AND strftime('%Y %m' ,tanggal)<='"+ month +"'),0) - \n" +
                            "    ifnull((SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori`\n" +
                            "    WHERE tipe = \"Pengeluaran\" AND strftime('%Y %m' ,tanggal)<='"+ month +"'),0) as total \n" +
                            "    FROM transaksis where strftime('%Y %m' ,tanggal)<='"+ month +"' group by total";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total = cursor.getInt(cursor.getColumnIndex("total"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total;
    }

    public int getTransaksiMonthTotal(String month){
        int total= 0;
        String qry = "SELECT tanggal as tgl, ifnull" +
                "((SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori` " +
                "WHERE tipe = "+"'Pemasukan'"+" AND tanggal = t.tanggal),0) - " +
                "ifnull((SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori` " +
                "WHERE tipe = "+"'Pengeluaran'"+" AND tanggal = t.tanggal),0) as total " +
                "FROM transaksis AS t where tanggal = '"+ month +"' group by tanggal";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total = cursor.getInt(cursor.getColumnIndex("total"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total;
    }

    public int getTransaksiMonthPeng(String month){
        int total_pengeluaran= 0;
        String qry = "SELECT SUM(jumlah) As jumlah FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori` WHERE tipe = 'Pengeluaran' AND strftime('%Y %m' , tanggal)='"+ month +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total_pengeluaran = cursor.getInt(cursor.getColumnIndex("jumlah"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total_pengeluaran;
    }

    public int getTransaksiMonthPem(String month){
        int total_pengeluaran= 0;
        String qry = "SELECT SUM(jumlah) As jumlah FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori` WHERE tipe = 'Pemasukan' AND strftime('%Y %m' , tanggal)='"+ month +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total_pengeluaran = cursor.getInt(cursor.getColumnIndex("jumlah"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total_pengeluaran;
    }

    public List<LihatTransaksiItem> getHistoriTransaksi(String filterStartStr, String filterEndStr, String tipe){
        List<LihatTransaksiItem> transaksis = new ArrayList<>();
        String qry = "SELECT * FROM transaksis inner join kategoris on kategoris._id = transaksis.`id_kategori`" +
                " WHERE tanggal >= '"+ filterStartStr +"' and tanggal <= '"+ filterEndStr +"'  and tipe='"+ tipe +"' ORDER BY tanggal DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                LihatTransaksiItem transaksi = new LihatTransaksiItem();

                transaksi.setId(cursor.getInt(cursor.getColumnIndex(TransaksiContract._ID)));
                transaksi.setIdUser(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_ID_USER)));
                transaksi.setIdKategori(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_ID_KATEGORI)));
                transaksi.setTanggal(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_TANGGAL)));
                transaksi.setCatatan(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_CATATAN)));
                transaksi.setJumlah(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_JUMLAH)));
                transaksi.setStatusSync(cursor.getInt(cursor.getColumnIndex(TransaksiContract.COLUMN_STATUS_SYNC)));
                transaksi.setCreatedAt(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_CREATED_AT)));
                transaksi.setUpdatedAt(cursor.getString(cursor.getColumnIndex(TransaksiContract.COLUMN_UPDATED_AT)));
                transaksis.add(transaksi);
            } while (cursor.moveToNext());
        }
        db.close();
        return transaksis;
    }

    public int getHistTransaksiFilter(String date, String date2, String tipe){
        int total= 0;
        String qry = "SELECT tanggal as tgl, ifnull((\n" +
                "                SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.id_kategori\n" +
                "                WHERE tipe = '"+tipe+"' AND tanggal = t.tanggal),0) as total\n" +
                "                FROM transaksis AS t where tanggal >= '"+ date +"' and tanggal <= '"+ date2 +"'group by tanggal";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total = cursor.getInt(cursor.getColumnIndex("total"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total;
    }

    public int getLaporanSaldoTotal(String date, String date2){
        int total= 0;
        String qry = "SELECT ifnull\n" +
                "        ((SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.id_kategori\n" +
                "        WHERE tipe = "+"'Pengeluaran'"+" AND tanggal >='"+ date +"' and tanggal <='"+ date2 +"'),0) - \n" +
                "        ifnull((SELECT SUM(jumlah) FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori`\n" +
                "        WHERE tipe = "+"'Pemasukan'"+" AND tanggal >='"+ date +"' and tanggal <='"+ date2 +"'),0) as total\n" +
                "        FROM transaksis where tanggal >='"+ date +"' AND tanggal <='"+ date2 +"' group by total";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total = cursor.getInt(cursor.getColumnIndex("total"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total;
    }

    public int getLaporanPeng(String date, String date2){
        int total_pengeluaran= 0;
        String qry = "SELECT SUM(jumlah) As jumlah FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori` WHERE tipe = 'Pengeluaran' AND tanggal >='"+ date +"' AND tanggal <='"+ date2 +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total_pengeluaran = cursor.getInt(cursor.getColumnIndex("jumlah"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total_pengeluaran;
    }

    public int getLaporanPem(String date, String date2){
        int total_pengeluaran= 0;
        String qry = "SELECT SUM(jumlah) As jumlah FROM transaksis INNER JOIN kategoris on kategoris._id = transaksis.`id_kategori` WHERE tipe = 'Pemasukan' AND tanggal >='"+ date +"' AND tanggal <='"+ date2 +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                total_pengeluaran = cursor.getInt(cursor.getColumnIndex("jumlah"));
            } while (cursor.moveToNext());
        }
        db.close();
        return total_pengeluaran;
    }

    //Insert Transaksi Online
    public long insertTransaksi(LihatTransaksiItem transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TransaksiContract.COLUMN_ID_USER, transaksi.getIdUser());
        values.put(TransaksiContract.COLUMN_ID_KATEGORI, transaksi.getIdKategori());
        values.put(TransaksiContract.COLUMN_TANGGAL, transaksi.getTanggal());
        values.put(TransaksiContract.COLUMN_CATATAN, transaksi.getCatatan());
        values.put(TransaksiContract.COLUMN_JUMLAH, transaksi.getJumlah());
        values.put(TransaksiContract.COLUMN_STATUS_SYNC, 0);
        values.put(TransaksiContract.COLUMN_CREATED_AT, transaksi.getCreatedAt());
        values.put(TransaksiContract.COLUMN_UPDATED_AT, transaksi.getUpdatedAt());

        long id = db.insert(TransaksiContract.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    //Insert Transaksi Offline
    public long insertTransaksiId(LihatTransaksiItem transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TransaksiContract.COLUMN_ID_USER, transaksi.getIdUser());
        values.put(TransaksiContract.COLUMN_ID_KATEGORI, transaksi.getIdKategori());
        values.put(TransaksiContract.COLUMN_TANGGAL, transaksi.getTanggal());
        values.put(TransaksiContract.COLUMN_CATATAN, transaksi.getCatatan());
        values.put(TransaksiContract.COLUMN_JUMLAH, transaksi.getJumlah());
        values.put(TransaksiContract.COLUMN_CREATED_AT, transaksi.getCreatedAt());
        values.put(TransaksiContract.COLUMN_UPDATED_AT, transaksi.getUpdatedAt());

        long id = db.insert(TransaksiContract.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void insertTransaksiLogin(List<LihatTransaksiItem> transaksis) {
        emptyTransaksi();
        for (LihatTransaksiItem transaksi: transaksis) {
            insertTransaksiId(transaksi);
        }
    }

    public int editTransaksi(LihatTransaksiItem transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TransaksiContract.COLUMN_ID_USER, transaksi.getIdUser());
        values.put(TransaksiContract.COLUMN_ID_KATEGORI, transaksi.getIdKategori());
        values.put(TransaksiContract.COLUMN_TANGGAL, transaksi.getTanggal());
        values.put(TransaksiContract.COLUMN_CATATAN, transaksi.getCatatan());
        values.put(TransaksiContract.COLUMN_JUMLAH, transaksi.getJumlah());
        values.put(TransaksiContract.COLUMN_CREATED_AT, transaksi.getCreatedAt());
        values.put(TransaksiContract.COLUMN_UPDATED_AT, transaksi.getUpdatedAt());

        Log.d("aaaaa", String.valueOf(transaksi));
        return db.update(TransaksiContract.TABLE_NAME,  values,TransaksiContract._ID + " = ?",
                new String[]{String.valueOf(transaksi.getId())});
    }

    public void emptyTransaksi(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "DELETE FROM " + TransaksiContract.TABLE_NAME;
        db.execSQL(qry);
        db.close();
    }

    public void deleteTransaksi(LihatTransaksiItem transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TransaksiContract.TABLE_NAME,TransaksiContract._ID + " = ?",
                new String[]{String.valueOf(transaksi.getId())});
        db.close();
    }

    //Kategori
    public List<LihatKategoriItem> getAllType(){
        List<LihatKategoriItem> kategoris = new ArrayList<>() ;
        String qry = "SELECT * FROM " + KategoriContract.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                LihatKategoriItem kategori = new LihatKategoriItem(
                        cursor.getInt(cursor.getColumnIndex(KategoriContract._ID)),
                        cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ID_USER)),
                        cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_NAMA_KATEGORI)),
                        cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ICON)),
                        cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_TIPE))
                );
                kategoris.add(kategori);
            } while (cursor.moveToNext());
        }
        db.close();
        return kategoris;
    }

    public List<LihatKategoriItem> getPengeluaranKategori(String tipe){
        List<LihatKategoriItem> kategoris = new ArrayList<>() ;
        String qry = "SELECT * FROM " + KategoriContract.TABLE_NAME + " WHERE " + KategoriContract.COLUMN_TIPE + "= ? " ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, new String[] { tipe });
        if (cursor.moveToFirst()) {
            do {
                LihatKategoriItem kategori = new LihatKategoriItem(
                        cursor.getInt(cursor.getColumnIndex(KategoriContract._ID)),
                        cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ID_USER)),
                        cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_NAMA_KATEGORI)),
                        cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ICON)),
                        cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_TIPE))
                );
                kategoris.add(kategori);
            } while (cursor.moveToNext());
        }
        db.close();
        return kategoris;
    }

    public List<LihatKategoriItem> getPemasukanKategori(){
        List<LihatKategoriItem> kategoris = new ArrayList<>() ;
        String qry = "SELECT * FROM " + KategoriContract.TABLE_NAME + " WHERE " + KategoriContract.COLUMN_TIPE + "= 'Pemasukan' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                LihatKategoriItem kategori = new LihatKategoriItem(
                        cursor.getInt(cursor.getColumnIndex(KategoriContract._ID)),
                        cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ID_USER)),
                        cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_NAMA_KATEGORI)),
                        cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ICON)),
                        cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_TIPE))
                );
                kategoris.add(kategori);
            } while (cursor.moveToNext());
        }
        db.close();
        return kategoris;
    }

    public LihatKategoriItem getTransaksi(int id){
        String qry = "SELECT * FROM " + KategoriContract.TABLE_NAME + " WHERE " + KategoriContract._ID + "="+ id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        LihatKategoriItem kategori = new LihatKategoriItem(
                cursor.getInt(cursor.getColumnIndex(KategoriContract._ID)),
                cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ID_USER)),
                cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_NAMA_KATEGORI)),
                cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ICON)),
                cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_TIPE))
        );
        db.close();
        return kategori;
    }

    public LihatKategoriItem getType(int id){
        String qry = "SELECT * FROM " + KategoriContract.TABLE_NAME + " WHERE " + KategoriContract._ID + "="+ id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        LihatKategoriItem type = new LihatKategoriItem(
                cursor.getInt(cursor.getColumnIndex(KategoriContract._ID)),
                cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ID_USER)),
                cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_NAMA_KATEGORI)),
                cursor.getInt(cursor.getColumnIndex(KategoriContract.COLUMN_ICON)),
                cursor.getString(cursor.getColumnIndex(KategoriContract.COLUMN_TIPE))
        );
        db.close();
        return type;
    }

    public void insertKategoris(int idUser){
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> type_names = Arrays.asList("Belanja","Makanan & Minuman","Gaji");
        List<Integer> type_icons = Arrays.asList(R.drawable.ic_cat_local_grocery_store_black_24dp,R.drawable.ic_cat_local_dining_black_24dp,R.drawable.ic_cat_work_black_24dp);
        List<String> type_expense = Arrays.asList("Pengeluaran","Pengeluaran","Pemasukan");

        for (int i = 0; i < type_names.size(); i++){
            ContentValues values = new ContentValues();
            values.put(KategoriContract.COLUMN_ID_USER, idUser);
            values.put(KategoriContract.COLUMN_NAMA_KATEGORI, type_names.get(i));
            values.put(KategoriContract.COLUMN_ICON, type_icons.get(i));
            values.put(KategoriContract.COLUMN_TIPE, type_expense.get(i));
            db.insert(KategoriContract.TABLE_NAME, null, values);
        }
    }

    //insertKategoriUser
    //Insert Kategori User Online
    public long insertKategoriUser(LihatKategoriItem kategori) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KategoriContract.COLUMN_ID_USER, kategori.getIdUser());
        values.put(KategoriContract.COLUMN_NAMA_KATEGORI, kategori.getNamaKategori());
        values.put(KategoriContract.COLUMN_ICON, kategori.getIcon());
        values.put(KategoriContract.COLUMN_TIPE, kategori.getTipe());
        values.put(KategoriContract.COLUMN_STATUS_SYNC, 0);
        values.put(KategoriContract.COLUMN_CREATED_AT, kategori.getCreatedAt());
        values.put(KategoriContract.COLUMN_UPDATED_AT, kategori.getUpdatedAt());

        long id = db.insert(TransaksiContract.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    //Insert Kategori User Offline
    public long insertKategoriUserId(LihatTransaksiItem transaksi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TransaksiContract.COLUMN_ID_USER, transaksi.getIdUser());
        values.put(TransaksiContract.COLUMN_ID_KATEGORI, transaksi.getIdKategori());
        values.put(TransaksiContract.COLUMN_TANGGAL, transaksi.getTanggal());
        values.put(TransaksiContract.COLUMN_CATATAN, transaksi.getCatatan());
        values.put(TransaksiContract.COLUMN_JUMLAH, transaksi.getJumlah());
        values.put(TransaksiContract.COLUMN_CREATED_AT, transaksi.getCreatedAt());
        values.put(TransaksiContract.COLUMN_UPDATED_AT, transaksi.getUpdatedAt());

        long id = db.insert(TransaksiContract.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    //PROFILE
    public long insertProfile(User profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_NAME, profile.getName());
        values.put(UserContract.COLUMN_EMAIL, profile.getEmail());
        values.put(UserContract.COLUMN_CREATED_AT, profile.getCreatedAt());
        values.put(UserContract.COLUMN_UPDATED_AT, profile.getUpdatedAt());
        values.put(TransaksiContract.COLUMN_STATUS_SYNC, profile.getStatusSync());

        long id = db.insert(UserContract.TABLE_NAME, null, values);
        return id;
    }

    //PROFILE
    public long insertProfileId(User profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract._ID, profile.getId());
        values.put(UserContract.COLUMN_NAME, profile.getName());
        values.put(UserContract.COLUMN_EMAIL, profile.getEmail());
        values.put(UserContract.COLUMN_CREATED_AT, profile.getCreatedAt());
        values.put(UserContract.COLUMN_UPDATED_AT, profile.getUpdatedAt());
        values.put(TransaksiContract.COLUMN_STATUS_SYNC, 0);

        long id = db.insert(UserContract.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void insertProfiles(List<User> profiles) {
        emptyProfiles();
        for (User profile: profiles) {
            insertProfileId(profile);
        }
    }

    public void emptyProfiles(){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "DELETE FROM " + UserContract.TABLE_NAME;
        db.execSQL(qry);
        db.close();
    }

    public int editProfile(User profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.COLUMN_NAME, profile.getName());
        values.put(UserContract.COLUMN_EMAIL, profile.getEmail());
        values.put(UserContract.COLUMN_CREATED_AT, profile.getCreatedAt());
        values.put(UserContract.COLUMN_UPDATED_AT, profile.getUpdatedAt());

        return db.update(UserContract.TABLE_NAME,  values,UserContract._ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
    }

    public User getProfile(){
        String qry = "SELECT * FROM " + UserContract.TABLE_NAME + " LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);

        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        User profile = new User();
        profile.setUpdatedAt(cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_UPDATED_AT)));
        profile.setName(cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_NAME)));
        profile.setCreatedAt(cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_CREATED_AT)));
        profile.setId(cursor.getInt(cursor.getColumnIndex(UserContract._ID)));
        profile.setStatusSync(cursor.getInt(cursor.getColumnIndex(UserContract.COLUMN_STATUS_SYNC)));
        profile.setEmail(cursor.getString(cursor.getColumnIndex(UserContract.COLUMN_EMAIL)));
        db.close();
        return profile;
    }
}
