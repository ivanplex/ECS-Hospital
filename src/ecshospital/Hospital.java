package ecshospital;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Hospital class implement all the core functionality of a hospital.
 * <p>
 * By default it has 50 beds and 4 theatres, but this could be customized in the configuration file.
 *
 * @author  Man-Leong Chan
 * @version 1.0
 */
public class Hospital{
	

	private ArrayList<Patient> beds;
	private ArrayList<Patient> theatres;
	
	/**
	 * Default Hospital Constructor. With 50 beds and 4 theaters.
	 */
	Hospital(){
		this(50,4);
	}
	
	/**
	 * Hospital Constructor.
	 * <p>
	 * A free bed/theatre is represented by "Null". The constructor will use for loop to create a number of "null" beds and theatres.
	 * 
	 * @param numOfBeds       An integer of the number of beds.
	 * @param numOfTheatres	  An integer of the number of theatres.
	 */
	Hospital(int numOfBeds,int numOfTheatres){
		
		beds = new ArrayList<Patient>(numOfBeds);
		theatres = new ArrayList<Patient>(numOfTheatres);

		for(int i=0;i<numOfBeds;i++){
			beds.add(null);
		}
		for(int i=0;i<numOfTheatres;i++){
			theatres.add(null);
		}
	}
	
	/**
	 * Return the current number of patients in hospital.
	 * 
	 * @return	An integer of the number of patients in hospital.
	 */
	public int size(){
		int numOfPatient=0;
		Iterator<Patient> it = beds.iterator();
		while(it.hasNext()){
			if(it.next() != null)
				numOfPatient++;
		}
		return numOfPatient;
	}

	/**
	 * Admit Patient.
	 * 
	 * @param patient   A patient who requested for admission.
	 * @return 			An integer value of the patient's bed number, return -1 if there's no bed.
	 * @throws			Exception if there is no free beds.
	 */
	public int admitPatient(Patient patient) throws Exception{
		Iterator<Patient> it = beds.iterator();
		int counter = 0;
		boolean ifFoundABed = false;
		
		/*
		 * The iterator will loop through and find if there is any free beds represented by "Null".
		 */
		while(it.hasNext()){
			Patient nextBed = it.next();
			if(nextBed == null){
				beds.set(counter, patient);
				ifFoundABed = true;
				//Stop the while loop once it found a free bed.
				break;
			}
			counter++;
		}
		if(ifFoundABed == false)
			throw new Exception("Cannot admit patient: aged "+patient.getAge()+", "+patient.getGender()+". Hospital is full!");
			//return -1;
		
		return counter;
	}
	
	/**
	 * Get patient from bed <i>bedIndex</i>.
	 * @param bedIndex	An integer of the patient's bed index.
	 * @return			The patient requested using the bed index provided. Null if no patient.
	 * @throws Exception 
	 */
	public Patient getPatient(int bedIndex) throws Exception{
		/*
		 * This returns null if the bed is empty as all beds are initially null.
		 */
		if(bedIndex<0 || bedIndex > (beds.size() -1))
			throw new Exception("Invalid bed number entered. The specified bed does not exist!");
		try {
			return beds.get(bedIndex);
		} catch ( IndexOutOfBoundsException e ) {
			System.err.println("Invalid bed number entered. The specified bed does not exist!");
		}
		return null;
	}
	
	/**
	 * Send patient back home according to the bed index.
	 * @param bedIndex	An integer of the patient's bed index.
	 */
	public void dischargePatient(int bedIndex){
		if(beds.get(bedIndex) != null){
			beds.set(bedIndex, null);
			System.out.println("Patient dismissed");
		}else
			System.out.println("There is no one in bed "+bedIndex+"! ERROR");
	}
	
	/**
	 * Check if the specific theatre given by the <i>theatreIndex</i> is free.
	 * @param theatreIndex  An integer of the theatre index.
	 * @return	A boolean of whether the specific theatre is free.
	 */
	public boolean isTheatreFree(int theatreIndex){
		if(theatres.get(theatreIndex) == null)
			return true;
		else
			return false;
	}
	
	/**
	 * Find a theatre for patient.
	 * @return	An integer of the theatre index. Return -1 if no theatre available.
	 */
	public int findFreeTheatre(){
		//Iterator<Patient> it = theatres.iterator();
		int counter;
		boolean ifFoundATheatre = false;

		for(counter=0;counter<theatres.size();counter++){
			if(isTheatreFree(counter)){
				ifFoundATheatre = true;
				break;
			}
		}
		if(ifFoundATheatre == false){
			
			return -1;
		}else
			return counter;
	
	}

	/**
	 * Unused method. Prepare for theatre operation.
	 * @param theatreIndex  Index of the theatre assigned.
	 * @param patient       The patient.
	 */
	public void prepForTheatre(int theatreIndex,Patient patient) throws Exception{
		if(theatres.get(theatreIndex) == null)
			theatres.set(theatreIndex,patient);
		else 
			throw new Exception("Theatre is not free! ERROR");
	}
	
	/**
	 * Prepare for theatre operation.
	 *
	 * @param patient       The patient.
	 * @return  Whether preparation for theatre operation is successful.
	 */
	public boolean prepForTheatre(Patient patient){
		//As default, the hospital has no free theatres.
		int theatreIndex = 0;
		
		//Unless the findFreeTheatre is successful.
		try{
			theatreIndex = findFreeTheatre();
		}catch (Exception e){
			System.err.println(e);
		}
		
		if(theatreIndex != -1){
			theatres.set(theatreIndex,patient);
			return true;
		}else{
			return false;
		}

		
	}
	
	/**
	 * Take patient from operation theatre back to their bed.
	 * @param theatreIndex		The theatre that the patient is in.
	 */
	public void takeForRecovery(int theatreIndex){
		theatres.set(theatreIndex, null);
	}
	
	/**
	 * Simulate all task that a Hospital should run. 
	 * <p>
	 * Take all patient from operation theatre back to their beds.
	 */
	public boolean aDayPasses(){
		
		//Trigger aDayPasses on all Patient
		Iterator<Patient> PatientADayPasses = beds.iterator();
		int bedIndex = 0;
		while(PatientADayPasses.hasNext()){
			Patient patient = PatientADayPasses.next();
			if(patient != null){
				patient.aDayPasses();
				if(patient.getHealthState() == 0)
					dischargePatient(bedIndex);
			}
			bedIndex++;
		}
		
		//Empty Theatres
		Iterator<Patient> emptyTheatre = theatres.iterator();
		int theatreIndex=0;
		while(emptyTheatre.hasNext()){
			Patient theatre = emptyTheatre.next();
			if(theatre != null)
				takeForRecovery(theatreIndex);
			theatreIndex++;
		}
		
		return true;
		
	}
}

