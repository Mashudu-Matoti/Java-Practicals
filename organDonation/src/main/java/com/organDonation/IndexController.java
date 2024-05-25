package com.organDonation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.organDonation.HashMap.HashMap;
import com.organDonation.blockchain.Patient;
import com.organDonation.blockchain.Entity;
import com.organDonation.blockchain.Institution;

import acsse.csc03a3.Block;
import acsse.csc03a3.Blockchain;
import acsse.csc03a3.Transaction;
/**
 * @author NM-217023902
 */
@Controller
public class IndexController {
	
	private Blockchain<Entity> blockChain = new Blockchain<>();
	private ArrayList<Patient> arraypatients = new ArrayList<>();
	private ArrayList<Patient> arrDonors = new ArrayList<>();
	private ArrayList<Patient> arrDonees = new ArrayList<>();
	private ArrayList<Institution> Institution = new ArrayList<>();
	private List<Transaction<Entity>> arrayTransactions = new ArrayList<>();
	private ArrayList<Integer> transactionID = new  ArrayList<>();
	private int transplantCount =0;
	private int c1;
	private int c2;
	private int  d1;
	private int d2;
	//private final BackgroundTaskService backgroundTaskService;
	 @Autowired
	 private RestTemplate restTemplate;
	  /*  public IndexController(BackgroundTaskService backgroundTaskService) {
	        this.backgroundTaskService = backgroundTaskService;
	    }

	 */
	
	@GetMapping
	public String index() {
		GenerateRandomData();
		d1 = arrDonors.size();
		d2 = arrDonees.size();
		return "index";
	}
	
	@GetMapping("/login.html")
	public String login(Model model) {
		Institution inst = new Institution();
		model.addAttribute("allHospitals", List.of("Lenmed","Mediclinic","Netcare","Life","Busamed","Cornmed","Baragwanath ","Helen Joseph", "Bheki Mlangeni","Steve Biko"));
		model.addAttribute("inst", inst);
		return "login";
	}
	
	@GetMapping("/transactions.html")
	public String transactions(Model model) {
		model.addAttribute("transactions", arrayTransactions);
		model.addAttribute("block", blockChain);
		model.addAttribute("verify", blockChain.isChainValid());
		return "transactions";
	}
	@GetMapping("/trans.html")
	public String trans(Model model) {
		Institution inst = new Institution();
		model.addAttribute("inst", inst);
		return "trans";
	}
	
	@GetMapping("/admin.html")
	public String admin(Model model) {
		
		int num1= c1+d1;
		int num2= c2+d2;
		transactionID.add(num1);
		model.addAttribute("d", num1);
		model.addAttribute("d2", num2);

		return "admin";
	}
	@GetMapping("list.html")
	public String list(Model model) {
		model.addAttribute("p",arraypatients);
		return "list";
	}
	
	
	
	@GetMapping("/registration.html")
	public String registration(Model model) {
		Patient patient = new Patient();
		model.addAttribute("allDepartments", List.of("Donor","Receipient"));
		model.addAttribute("allOrgans", List.of("lung", "kidney", "liver","tissue", "heart", "pancrease","intestines", "cornea", "vein", "skin", "bone", "valve"));
		model.addAttribute("patient", patient);
		return "registration";
	}
	
	/**
	 * Retrieves patient inputs, performs validation checks and then 
	 * @param patient
	 * @return html page 
	 */
	
	@PostMapping("/trans.html")
		String displayTrans(@ModelAttribute("inst") Institution inst) {
		String organ = null;
		
		transplantCount++;
		List<Transaction<Entity>> arrTrans = new ArrayList<>();
		Transaction t = new Transaction(inst.getPatientA(),inst.getPatientB(), inst.getfName()+" transplant"+ "by practinioner "+inst.getPracticeNumber());
		arrTrans.add(t);
		arrayTransactions.add(t);
		blockChain.registerStake(inst.getPracticeNumber(),100);
		blockChain.addBlock(arrTrans);
		System.out.println("Is blockchain valid?: "+blockChain.isChainValid());
		

		System.out.print(organ+" transplant successful");
			return "admin";
		}
	

	@PostMapping("/login.html")
	public String displayLogin(@ModelAttribute("inst") Institution inst) {
		
		System.out.println("Success"+ inst);
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
		return "admin";
	}
	@PostMapping("/registration.html")
	public String displayRegister(@ModelAttribute("patient") Patient patient ,Model model) {
		String apiUrl = "https://bdupreez-south-african-id-no-validator-v1.p.rapidapi.com/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "bca2550de6msh7b6c814f392dbafp10a386jsne7b6d6863b9a");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"idno\": \"" + patient.getIdNum() + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
      //If id is not valid, do not add patient to hashmap (writes "Failure" on terminal)
        if((response.getBody().contains("false"))) {
        	
        	model.addAttribute("message",patient.idError());
        	System.out.println("Failure"+patient);
        	return "registration";// Return to the form page with the result displayed
        	
        }
        
        //All checks passed, add patient to pool of patients
        else {
        	arraypatients.add(patient);
        	if(patient.getpatientType().equals("Donor")) {
        		arrDonors.add(patient);
        		c1++;
        	}
        	else {
        		arrDonees.add(patient);
        		c2++;
;        	}
        	System.out.println("Success"+patient);
        	addToBlockChain(patient);
   
        	
        	return "font_end";
        	
        }
		
	}
	
	
	//
	/**
	 * Checks if organs specified are donatable
	 * @param inputString
	 * @param substrings
	 * @return
	 */
	public boolean containsAnySubstring(String inputString, List<String> substrings) {
        for (String substring : substrings) {
            if (inputString.contains(substring)) {
                return true; // Return true if any substring is found in the input string
            }
        }
        return false; // Return false if none of the substrings are found
    }
	
	public void addToBlockChain( Object o) {

	Transaction<Entity> transaction;
	Patient e = (Patient) o;
	List<Transaction<Entity>> arrTrans = new ArrayList<>();
	//Block<Entity> block;
		if(e instanceof Patient) {
			transaction = new Transaction<>(e.getIdNum(), "Organ Bank", e);
			arrayTransactions.add(transaction);
			arrTrans.add(transaction);
			blockChain.registerStake(e.getIdNum(),100);
			blockChain.addBlock(arrTrans);
			System.out.println(blockChain.toString());
			System.out.println("Is blockchain valid?: "+blockChain.isChainValid());
		}
		
		

		
	}
	/**
	 * The function generates pseudo patients and donees for demonstration purposes
	 */
	public void GenerateRandomData() {
		Patient person1 = new Patient();
		Patient person2 = new Patient();
		Patient person3 = new Patient();
		Patient person4 = new Patient();
		Patient person5 = new Patient();
		Patient person6 = new Patient();
		Patient person7 = new Patient();
		
		person1.setInfo("John Doe", "9503057898765", "lungs", "Recently had heart surgery","Donor");
		person2.setInfo("Alice Time", "7711179670918", "kidney", "Tendon split  on ankle","Donor");
		person3.setInfo("Bob Builder", "8910289670945", "heart", "Gout","Donor");
		person4.setInfo("Spencer James", "0044557898765", "conea", "Healthy","Donor");
		person5.setInfo("Louis Handy", "8601193898765", "kidney", "Chronic Migrane","Receipient");
		person6.setInfo("Mash Mat", "9801257898765", "cornea", "Cold and fever","Receipient");
		person7.setInfo("Junior Child", "9901092898765", "lungs", "Cold and fever","Receipient");
		
		
		arrDonees.add(person7);
		arrDonees.add(person6);
		arrDonees.add(person5);
		arrDonors.add(person4);
		arrDonors.add(person3);
		arrDonors.add(person2);
		arrDonors.add(person1);
		arraypatients.add(person7);
		arraypatients.add(person6);
		arraypatients.add(person5);
		arraypatients.add(person4);
		arraypatients.add(person3);
		arraypatients.add(person3);
		arraypatients.add(person1);
	}
	
	
}




