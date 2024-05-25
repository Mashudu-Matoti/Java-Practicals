package com.organDonation.blockchain;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
 * *
 * Class that represents an institution that will receive a copy of a the distributed ledger and also be able to add a block to it 
 */

/**
 * @author NM Matoti - 217023902
 *
 */
public class Institution implements Entity {
	
	private String institutionName;
	private String practiceNumber;
	private String patientA;
	private String patientB;
	private String fName;
	
	
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	private boolean isTransplantSuccessFull;
	private String password;
	
	@Override
	public String toString() {
		return "Institution [institutionName=" + institutionName + ", practiceNumber=" + practiceNumber + "]";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getPracticeNumber() {
		return practiceNumber;
	}
	public void setPracticeNumber(String practiceNumber) {
		this.practiceNumber = practiceNumber;
	}
	public String getPatientA() {
		return patientA;
	}
	public void setPatientA(String patientA) {
		this.patientA = patientA;
	}
	public String getPatientB() {
		return patientB;
	}
	public void setPatientB(String patientB) {
		this.patientB = patientB;
	}
	public boolean isTransplantSuccessFull() {
		return isTransplantSuccessFull;
	}
	public void setTransplantSuccessFull(boolean isTransplantSuccessFull) {
		this.isTransplantSuccessFull = isTransplantSuccessFull;
	}
	
	
}
