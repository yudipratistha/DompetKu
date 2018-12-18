package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class LihatKategoriItem{


	public LihatKategoriItem(int id, int idUser, String namaKategori, int icon, String tipe) {
		this.id = id;

		this.namaKategori = namaKategori;
		this.icon = icon;
		this.tipe = tipe;
	}


	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("icon")
	private int icon;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("tipe")
	private String tipe;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public LihatKategoriItem(int idUser) {
		this.idUser = idUser;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIcon(int icon){
		this.icon = icon;
	}

	public int getIcon(){
		return icon;
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
				this.getNamaKategori();
		}

	@Override
	public boolean equals (Object obj)
	{
		if (this==obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;

		LihatKategoriItem type = (LihatKategoriItem) obj ;
		return this.id == type.getId();
	}
}