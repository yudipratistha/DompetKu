package com.example.yudipratistha.dompetku.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class Success{

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("token")
	private String token;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Success{" + 
			"name = '" + name + '\'' + 
			",email = '" + email + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}