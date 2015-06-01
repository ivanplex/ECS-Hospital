package ecshospital;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * <b>Hospital Administrator</b> controls the entire workflow of the ECS hospital. 
 * @author Man-Leong Chan
 *
 */
public class HospitalAdministrator implements Customizable{
	
	/**
	 * @param doctorList  An arrayList of Doctors.
	 */
	private ArrayList<Doctors> doctorList;
	/**
	 * @param incomingPatients  An arrayList of Patients.
	 */
	private ArrayList<Patient> incomingPatients;
	/**
	 * @param daysSinceStarted  An integer of Days since the hospital started working.
	 */
	private int daysSinceStarted;
	
	private BufferedReader reader;
	
	private Hospital hospital;
	
	/**
	 * It initialize a list of doctors and incoming Patients.
	 * <p>doctorList: A list of doctors working in the hospital.
	 * <p>incomingPatients: A list of incoming patients that are not yet admitted into the hospital.
	 */
	HospitalAdministrator(){
		doctorList = new ArrayList<Doctors>();
		incomingPatients= new ArrayList<Patient>();
		
		/*
		 * illnessRecoveryTimeMap maps the range of recovery time needed from a particular illness. 
		 * The key contains the illness that the patient could suffer from.
		 * The value contains an array of two numbers which indicates the range of possible recovery time needed.
		 */
		illnessRecoveryTimeMap.put(1, new Integer[] {5,5});
		illnessRecoveryTimeMap.put(2, new Integer[] {3,3});
		illnessRecoveryTimeMap.put(3, new Integer[] {1,1});
		illnessRecoveryTimeMap.put(4, new Integer[] {2,4});
		illnessRecoveryTimeMap.put(5, new Integer[] {5,8});
		illnessRecoveryTimeMap.put(6, new Integer[] {6,8});
		illnessRecoveryTimeMap.put(7, new Integer[] {4,6});
		illnessRecoveryTimeMap.put(8, new Integer[] {2,3});
	}
	
	/**
	 * Read configuration file "myHospital.txt".
	 * <p>The program will import all the patients and doctors, it will also construct the basics of the hospital.
	 * @param configurationFile		The configuration file imported.
	 * @return		The hospital constructed.
	 */
	public Hospital readConfigurationFile(File configurationFile){
		
		try{
			System.out.println("/////////////////////////////////");
			System.out.println("Importing settings...");
			System.out.println();
			
			reader = new BufferedReader(new FileReader(configurationFile));
			String str;
			
			//Tagging the doctors with ID, starting from 1.
			int doctorID = 1;
			
			//Read file line by line
			while ((str = reader.readLine()) != null){
	
				//Split them into questions and answers
				String[] parts = str.split(":",2);
				
				/*
				 * Prefix contain the class that would like to be initialized. E.g. 'hospital','Patient'...
				 * Suffix contain the parameters of the class which would be initialized.
				 */
				String prefix = parts[0];
				String suffix = parts[1];
				
				
				if(prefix.equals("hospital")){
					try{
						String[] spec = suffix.split(",", 2);
						try {
							//setHospital(Integer.parseInt(spec[0]),Integer.parseInt(spec[1]));
							hospital = new Hospital(Integer.parseInt(spec[0]),Integer.parseInt(spec[1]));
							System.out.println("Hospital with "+Integer.parseInt(spec[0])+" beds and "+Integer.parseInt(spec[1])+" theatres created!");
						} catch (NumberFormatException e) {
						      System.err.println("Invalid hospital configuration.");
						}
					}catch (Exception e){
						System.err.println(e);
					}
				}else if(prefix.equals("patient")){
					try{
						String[] spec = suffix.split(",", 4);
						try {
							addPatient(spec[0].charAt(0),Integer.parseInt(spec[1]),Integer.parseInt(spec[2]),Integer.parseInt(spec[3]));
							System.out.println("Patient: "+suffix+". IMPORTED.");
						} catch (NumberFormatException e) {
						      System.err.println("Invalid patient configuration.");
						}
					}catch (Exception e){
						System.err.println(e);
					}
				}else if(prefix.equals("doctor") || prefix.equals("surgeon") || prefix.equals("limbSurgeon") || prefix.equals("organSurgeon")){
					try{
						String[] spec = suffix.split(",", 2);
						try {
							addDoctor(doctorID,spec[0].charAt(0),Integer.parseInt(spec[1]),prefix,hospital);
							System.out.println(prefix+": "+suffix+". IMPORTED.");
						} catch (NumberFormatException e) {
						      System.err.println("Invalid doctor configuration.");
						}
						doctorID++;
					}catch (Exception e){
						System.err.println(e);
					}
				}else if (prefix.equals("illness")){
					try{
						String[] spec = suffix.split(",", 3);
						try {
							modifyIllnessRecoveryTime(Integer.parseInt(spec[0]),Integer.parseInt(spec[1]),Integer.parseInt(spec[2]));
							System.out.println("Illness configuration IMPORTED.");
						} catch (NumberFormatException e) {
						      System.err.println("Invalid patient configuration.");
						}
					}catch (Exception e){
						System.err.println(e);
					}
				}
			}
			System.out.println("File Successfully imported.");
		}catch (Exception e){
			System.err.println("Reading error occured");
		}
		
		
		System.out.println("/////////////////////////////////");
		System.out.println();
		System.out.println();
		
		return hospital;
	}

	/**
	 * Import patient using the information given in the configuration file "myHospital.txt".
	 * <p>
	 * Error checking is done including illness code check and correct recovery time.
	 * @param gender			The gender of the patient being imported.
	 * @param age				The age in integer of the patient imported.
	 * @param illness			The integer representation of illness that the patient suffers.
	 * @param recoveryTime		The time it takes to recover due to the illness (days).
	 * @throws Exception		Throw invalid information error.
	 */
	public void addPatient(char gender,int age,int illness,int recoveryTime) throws Exception{
		
		//Error checking, including illness and recovery time.
		if(illness < 0 || illness >8)
			throw new Exception("Invalid illness code entered! ERROR");
		else if(recoveryTime < -1)
			throw new Exception("Invalid recovery time entered! ERROR");
		else if(recoveryTime == -1){
			//If the patient requires treatment.
			incomingPatients.add(new Patient(gender,age,1,illness,recoveryTime));
		}else{
			//If the patient doesn't require doctor's attention.
			incomingPatients.add(new Patient(gender,age,2,illness,recoveryTime));
		}
	}

	/**
	 * Import doctors using the information given in the configuration file "myHospital.txt".
	 * @param iD			Doctor ID, automatically increment from {@link HospitalAdministrator#readConfigurationFile(File)}.
	 * @param gender		The doctor's gender.
	 * @param age			The doctor's age.
	 * @param specialism	An integer representation of the doctor's specialism.
	 * @param hospital		The hospital that the doctor will work in.
	 * @throws Exception	Throw invalid doctor specialism.
	 */
	public void addDoctor(int iD,char gender,int age,String specialism, Hospital hospital) throws Exception{
		if(specialism.equals("doctor"))
			doctorList.add(new Doctors(iD,gender,age,hospital));
		else if(specialism.equals("surgeon"))
			doctorList.add(new Surgeon(iD,gender,age,hospital));
		else if(specialism.equals("limbSurgeon"))
			doctorList.add(new LimbSurgeon(iD,gender,age,hospital));
		else if(specialism.equals("organSurgeon"))
			doctorList.add(new OrganSurgeon(iD,gender,age,hospital));
		else 
			throw new Exception("Create new Doctor error! Unknown doctor class"+specialism+". ERROR");
	}
	
	/**
	 * Modify the recovery time for illness. This changes the <i>illnessRecoveryTimeMap</i> in the Customizable interface.
	 * @param illness			The illness ID.
	 * @param minRecoveryTime	The minimum recovery time for the illness.
	 * @param maxRecoveryTime	The maximum recovery time for the illness.
	 * @throws Exception		If the illness entered does not exist or if illegal parameter passed.
	 */
	public void modifyIllnessRecoveryTime(int illness, int minRecoveryTime, int maxRecoveryTime) throws Exception{
		if(illness > 0 && minRecoveryTime > 0 && maxRecoveryTime > 0 && minRecoveryTime <= maxRecoveryTime){
			Integer[] illnessRecoveryTimeRange = new Integer[] {minRecoveryTime,maxRecoveryTime};
			if(illnessRecoveryTimeMap.containsKey(illness))
				illnessRecoveryTimeMap.put(illness, illnessRecoveryTimeRange);
			else{
				throw new Exception("This illness does not exist!");
			}	
		}else
			throw new Exception("Illegal parameters passed.");
	}

	/**
	 * Admits incoming patients from the arrayList incomingPatients.
	 * <p>
	 * If the method from {@link Hospital#admitPatient(Patient)} returns -1, it would output a line saying "Hospital is full".
	 * It will stop trying to admit more patients from the arrayList incomingPatients.
	 * <p>
	 * This admit patient right away when a bed is available. 
	 * 
	 * @param hospital  The Hospital.
	 */
	public void admitPatients(Hospital hospital){
		Iterator<Patient> it = incomingPatients.iterator();
		boolean hospitalUnderPressure = false;
		int incomingPatientsIndex = 0;
		while(it.hasNext()){
			Patient patientToAdmit = it.next();
			if(patientToAdmit != null){
				try{
					hospital.admitPatient(patientToAdmit);
					System.out.println("Patient admitted: Aged "+patientToAdmit.getAge()+", "+patientToAdmit.getGender()+".");
					//Remove the admitted patient from the arrayList incomingPatients
					incomingPatients.set(incomingPatientsIndex, null);
				}catch (Exception e){
					System.err.println(e);
				}
			}
			incomingPatientsIndex++;
		}
		if(hospitalUnderPressure){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("Hospital is under pressure: No Free Beds!");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		}
		
	}

	/**
	 * Start simulation.
	 * @param hA					The Hospital Administrator.
	 * @param configurationFile		The configuration file.
	 */
	public void go(HospitalAdministrator hA,File configurationFile){
		
		readConfigurationFile(configurationFile);
		
		//Simulate a day until all patients are cured.
		do{
			try {
				aDayPasses(hA);
				//Slow a day passes by 0.5 seconds
				/*try{
					Thread.sleep(500);
				}catch (InterruptedException e){}*/
			} catch (Exception e) {
				System.err.println(e);
			}
		}while(hospital.size() > 0);
		
	}

	/**
	 * Starts the program by calling go().
	 * This import the configuration file.
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		HospitalAdministrator hA = new HospitalAdministrator();
		
		//Create a file object for the configuration file
		File configurationFile = null;
		if (0 < args.length) {
		    String filename = args[0];
		    configurationFile = new File(filename);
		  }
		
		hA.go(hA,configurationFile);
	}
	
	/**
	 * Run the simulation for the day.
	 * @param hA			Hospital Administrator.
	 * @throws Exception	
	 */
	public void aDayPasses(HospitalAdministrator hA) throws Exception{
		
		System.out.println("=========================");
		System.out.println("Day "+daysSinceStarted);
		System.out.println("=========================");
		
		/**
		 * admitPatients will try to admit patients whenever there is a free bed available.
		 * This code is run once every day.
		 * @see admitPatients
		 */
		hA.admitPatients(hospital);
		
		System.out.println(">>>>>>>>>>>>>>>>>");
		for(int i= 0;i<10;i++){
			if(hospital.getPatient(i) ==null)
				System.out.println(i+": Empty");
			else
				System.out.println(i+": Health "+hospital.getPatient(i).getHealthState());
		}
		System.out.println(">>>>>>>>>>>>>>>>>");
		
		Iterator<Doctors> it = doctorList.iterator();
		//Looping through all the doctors
		while(it.hasNext()){
			Doctors doctor = it.next();
			/**
			 * If Doctor is occupied. 
			 * <p>
			 * Currently a doctor only takes a day to treat a patient, so a doctor is always free at the start of the day.
			 * This would come in handy if treatment or operation takes longer than a day.
			 */
			if(doctor.getAssignedPatient() == null){
				for(int i=0;i<hospital.size();i++){
					//Check if there is a patient at bed "i"
					if(hospital.getPatient(i) != null){
						//Check if the patient is already assigned with a doctor
						if(hospital.getPatient(i).takenCareOf() == false && hospital.getPatient(i).getHealthState() ==1){
							/*
							 * assignPatient outputs a boolean depending if the assignment is successful.
							 * If the assignment is successful, it would break the for loop and move on to the next doctor waiting for assignment.
							 * If however the assignment is unsuccessful, the for loop will continue to find the next suitable patient for the doctor.
							 */
							if(doctor.tryAssignPatient(hospital.getPatient(i)))
								break;
						}
					}
				}
			}
		}
		
		//Call aDayPasses on Doctors
		Iterator<Doctors> doctorADayPasses = doctorList.iterator();
		while(doctorADayPasses.hasNext()){
			doctorADayPasses.next().aDayPasses();
		}
		
		//Remove all patients from theatres & Call aDayPasses for all patients
		hospital.aDayPasses();
		
		//Increment daysSinceStarted
		incrementDays();
		
	}
	
	/**
	 * increment the daysSinceStarted
	 */
	public void incrementDays(){
		daysSinceStarted++;
	}
}
