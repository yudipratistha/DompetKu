package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class BuatTransaksi{

	@SerializedName("buatTransaksi")
	private BuatTransaksi buatTransaksi;

	@SerializedName("status_update")
	private String statusUpdate;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("status_delete")
	private String statusDelete;

	@SerializedName("catatan")
	private String catatan;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("id")
	private int id;

	public void setBuatTransaksi(BuatTransaksi buatTransaksi){
		this.buatTransaksi = buatTransaksi;
	}

	public BuatTransaksi getBuatTransaksi(){
		return buatTransaksi;
	}

	public void setStatusUpdate(String statusUpdate){
		this.statusUpdate = statusUpdate;
	}

	public String getStatusUpdate(){
		return statusUpdate;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
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

	public void setStatusDelete(String statusDelete){
		this.statusDelete = statusDelete;
	}

	public String getStatusDelete(){
		return statusDelete;
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

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"BuatTransaksi{" + 
			"buatTransaksi = '" + buatTransaksi + '\'' + 
			",status_update = '" + statusUpdate + '\'' + 
			",id_kategori = '" + idKategori + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",status_delete = '" + statusDelete + '\'' + 
			",catatan = '" + catatan + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}