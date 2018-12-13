package com.example.yudipratistha.dompetku.model;

import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class LihatKategori{

	@SerializedName("lihatKategori")
	private List<LihatKategoriItem> lihatKategori;

	public void setLihatKategori(List<LihatKategoriItem> lihatKategori){
		this.lihatKategori = lihatKategori;
	}

	public List<LihatKategoriItem> getLihatKategori(){
		return lihatKategori;
	}

	@Override
 	public String toString(){
		return 
			"LihatKategori{" + 
			"lihatKategori = '" + lihatKategori + '\'' + 
			"}";
		}
}