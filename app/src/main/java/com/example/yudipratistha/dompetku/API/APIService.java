package com.example.yudipratistha.dompetku.API;


import com.example.yudipratistha.dompetku.model.BuatTransaksi;
import com.example.yudipratistha.dompetku.model.DeleteTransaksi;
import com.example.yudipratistha.dompetku.model.LihatKategori;
import com.example.yudipratistha.dompetku.model.LihatTransaksi;
import com.example.yudipratistha.dompetku.model.Signup;
import com.example.yudipratistha.dompetku.model.UpdateTransaksi;
import com.example.yudipratistha.dompetku.model.UserLogin;
import com.example.yudipratistha.dompetku.model.UserUpdate;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @FormUrlEncoded
    @POST("register")
    Call<Signup> saveRegisterPost(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("c_password") String c_password
    );
    @FormUrlEncoded
    @POST("login")
    Call<UserLogin> loginPost(@Field("email") String email,
                                  @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_user/{id}")
    Call<UserUpdate> updateUser(@Path("id") int id_user,
                                @Field("name") String nama
    );

    @FormUrlEncoded
    @POST("buat_transaksi")
    Call<BuatTransaksi> buatTransaksi(@Field("id_user") int id_user,
                                      @Field("id_kategori") int id_kategori,
                                      @Field("tanggal") String tanggal,
                                      @Field("catatan") String catatan,
                                      @Field("jumlah") int jumlah

    );

    @GET("lihat_transaksi/{id}")
    Call<LihatTransaksi>  lihatTransaksi(@Path("id") int id_user);

    @FormUrlEncoded
    @POST("update_transaksi/{id}")
    Call<UpdateTransaksi> updateTransaksi(@Path("id") int id_transaksi,
                                          @Field("jumlah") int jumlah,
                                          @Field("id_kategori") int id_kategori,
                                          @Field("tanggal") String tanggal,
                                          @Field("catatan") String catatan
    );

    @DELETE("delete_transaksi/{id}")
    Call<DeleteTransaksi> deleteTransaksi(@Path("id") int id_transaksi);

    @FormUrlEncoded
    @POST("buat_kategori")
    Call<BuatTransaksi> buatKategori(@Field("id_user") int id_user,
                                     @Field("nama_kategori") String id_kategori,
                                     @Field("icon") int icon,
                                     @Field("tipe") String tipe

    );

    @GET("lihat_kategori")
    Call<LihatKategori>  lihatKategori();

    @FormUrlEncoded
    @POST("update_transaksi/{id}")
    Call<UpdateTransaksi> updateKategori(@Path("id") int id_transaksi,
                                         @Field("jumlah") int jumlah,
                                         @Field("id_kategori") int id_kategori,
                                         @Field("tanggal") String tanggal,
                                         @Field("catatan") String catatan
    );

    @DELETE("delete_transaksi/{id}")
    Call<DeleteTransaksi> deleteKategori(@Path("id") int id_transaksi);
}