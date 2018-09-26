package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KineInfo {
	public KineInfo(String nomPrenom, String codeConv, String mf, String rip,
			String cnss) {
		super();
		 
		this.nomPrenom = nomPrenom;
		this.codeConv =codeConv;
		this.mf = mf;
		this.rip = rip;
		this.cnss = cnss;
	}
	private  String nomPrenom;
	private  String codeConv;
	private  String mf;
	private  String rip;
	private  String cnss;
	
	
	public String getNomPrenom() {
		return nomPrenom;
	}
	public void setNomPrenom(String nomPrenom) {
		this.nomPrenom=nomPrenom;
	}
	
	public KineInfo() {
		super();
		this.nomPrenom ="";
		this.codeConv = "";
		this.mf ="";
		this.rip ="";
		this.cnss ="";
	}
	public String getCodeConv() {
		return codeConv;
	}
	public void setCodeConv(String codeConv) {
		this.codeConv=codeConv;
	}
	public String getMf() {
		return mf;
	}
	public void setMf(String mf) {
		this.mf=mf;
	}
	public String getRip() {
		return rip;
	}
	public void setRip(String rip) {
		this.rip=rip;
	}
	public String getCnss() {
		return cnss;
	}
	public void setCnss(String cnss) {
		this.cnss=cnss;
	}

}
