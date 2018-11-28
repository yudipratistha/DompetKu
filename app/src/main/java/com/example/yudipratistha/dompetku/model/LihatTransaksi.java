package com.example.yudipratistha.dompetku.model;

import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class LihatTransaksi{

	@SerializedName("lihatTransaksi")
	private List<LihatTransaksiItem> lihatTransaksi;

	public void setLihatTransaksi(List<LihatTransaksiItem> lihatTransaksi){
		this.lihatTransaksi = lihatTransaksi;
	}

	public List<LihatTransaksiItem> getLihatTransaksi(){
		return lihatTransaksi;
	}

	@Override
 	public String toString(){
		return 
			"LihatTransaksi{" + 
			"lihatTransaksi = '" + lihatTransaksi + '\'' + 
			"}";
		}
}