package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class UserLogin_old {

	@SerializedName("success")
	private Success success;

	@SerializedName("status")
	private boolean status;

	public void setSuccess(Success success){
		this.success = success;
	}

	public Success getSuccess(){
		return success;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"UserLogin_old{" +
			"success = '" + success + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}