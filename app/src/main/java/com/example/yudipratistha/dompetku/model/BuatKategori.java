package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class BuatKategori{

	@SerializedName("buatKategori")
	private BuatKategori buatKategori;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("icon")
	private String icon;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("id")
	private int id;

	@SerializedName("tipe")
	private String tipe;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setBuatKategori(BuatKategori buatKategori){
		this.buatKategori = buatKategori;
	}

	public BuatKategori getBuatKategori(){
		return buatKategori;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTipe(String tipe){
		this.tipe = tipe;
	}

	public String getTipe(){
		return tipe;
	}

	public void setNamaKategori(String namaKategori){
		this.namaKategori = namaKategori;
	}

	public String getNamaKategori(){
		return namaKategori;
	}

	@Override
 	public String toString(){
		return 
			"BuatKategori{" + 
			"buatKategori = '" + buatKategori + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",icon = '" + icon + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",id = '" + id + '\'' + 
			",tipe = '" + tipe + '\'' + 
			",nama_kategori = '" + namaKategori + '\'' + 
			"}";
		}
}