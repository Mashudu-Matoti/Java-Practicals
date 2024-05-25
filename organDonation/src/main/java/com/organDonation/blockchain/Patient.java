package com.organDonation.blockchain;


/**
 * 
 */

/**
 * @author NM Matoti - 217023902
 *
 */
public class Patient implements Entity {
	
	private String patientType;
	private String fName;
	private String idNum;  //ID number of donor
	private String organ; //List of organs to receive
	private String medicalHist;
	

	public String getpatientType() {
		return patientType;
	}

	public void setpatientType(String patientType) {
		this.patientType = patientType;
	}
	
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getIdNum() {
		return idNum;
	}
	
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getMedicalHist() {
		return medicalHist;
	}

	public void setMedicalHist(String medicalHist) {
		this.medicalHist = medicalHist;
	}
	
	

	@Override
	public String toString() {
		return "Full Name: " + fName+"\n"+ 
				"ID Num: " + idNum +"\n"+
				"Organ: " + organ+"\n"+
				"Medical History: " + medicalHist +"\n"+
				"Patient-Type="+ patientType ;
	}

	
	public void setInfo(String fN, String id, String organ, String medInfo, String pt) {
		// TODO Auto-generated method stub
		fName = fN;
		idNum = id;
		this.organ = organ;
		medicalHist = medInfo;
		patientType= pt;
	}
	
	public String idError() {
		return "Invalid ID: Please enter a valid 13-digit South African ID";
	}
	
}
