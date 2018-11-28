package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class UpdateTransaksi{

	@SerializedName("updateTransaksi")
	private UpdateTransaksi updateTransaksi;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("id_penyimpanan")
	private String idPenyimpanan;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("catatan")
	private String catatan;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("tanggal")
	private String tanggal;

	public void setUpdateTransaksi(UpdateTransaksi updateTransaksi){
		this.updateTransaksi = updateTransaksi;
	}

	public UpdateTransaksi getUpdateTransaksi(){
		return updateTransaksi;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
	}

	public void setIdPenyimpanan(String idPenyimpanan){
		this.idPenyimpanan = idPenyimpanan;
	}

	public String getIdPenyimpanan(){
		return idPenyimpanan;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setCatatan(String catatan){
		this.catatan = catatan;
	}

	public String getCatatan(){
		return catatan;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	@Override
 	public String toString(){
		return 
			"UpdateTransaksi{" + 
			"updateTransaksi = '" + updateTransaksi + '\'' + 
			",id_kategori = '" + idKategori + '\'' + 
			",id_penyimpanan = '" + idPenyimpanan + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",catatan = '" + catatan + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			"}";
		}
}