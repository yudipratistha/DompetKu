package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class DeleteTransaksi{

	@SerializedName("dataDeleteTransaksi")
	private DataDeleteTransaksi dataDeleteTransaksi;

	@SerializedName("deleteTransaksi")
	private String deleteTransaksi;

	public void setDataDeleteTransaksi(DataDeleteTransaksi dataDeleteTransaksi){
		this.dataDeleteTransaksi = dataDeleteTransaksi;
	}

	public DataDeleteTransaksi getDataDeleteTransaksi(){
		return dataDeleteTransaksi;
	}

	public void setDeleteTransaksi(String deleteTransaksi){
		this.deleteTransaksi = deleteTransaksi;
	}

	public String getDeleteTransaksi(){
		return deleteTransaksi;
	}

	@Override
 	public String toString(){
		return 
			"DeleteTransaksi{" + 
			"dataDeleteTransaksi = '" + dataDeleteTransaksi + '\'' + 
			",deleteTransaksi = '" + deleteTransaksi + '\'' + 
			"}";
		}
}