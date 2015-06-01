package ecshospital;

/**
 * <b>Doctor</b> models all doctors behaviours in ECS hospital.
 * @author Man-Leong Chan
 *
 */
public class Doctors extends Person{
	/**
	 * 1=Doctor, 2=Surgeon, 3=LimbSurgeon, 4=OrganSurgeon
	 * 
	 * @param checkCorrectAssignment Check if the doctor can treat the assigned patient
	 */
	private int specialism;
	
	private Patient assignedPatient;
	
	private int iD;

	private Hospital hospital;
	
	/**
	 * Constructor for pure Doctors only.This is not used by Surgeon, LimbSurgeon or OrganSurgeon.
	 * @param iD			Doctor ID. All doctors has ID so that we can easily identify their movement.
	 * @param gender		Doctor's gender in char.
	 * @param age			Doctor's age.
	 * @param hospital		The hospital that the doctor works in.
	 * @throws Exception	From person class if illegal age or gender is enter.
	 */
	Doctors(int iD, char gender, int age, Hospital hospital) throws Exception{
		//This pass specialism 1, the specialism ID for doctors.
		this(iD,gender,age,1,hospital);
	}
	
	/**
	 * @param iD			Doctor ID. All doctors has ID so that we can easily identify their movement.
	 * @param gender		Doctor's gender in char.
	 * @param age			Doctor's age.
	 * @param specialism	The specialism representation in int.
	 * @param hospital		The hospital that the doctor works in.
	 * @throws Exception	From person class if illegal age or gender is enter.
	 */
	Doctors(int iD,char gender, int age, int specialism, Hospital hospital) throws Exception{
		super(gender,age);
		this.specialism = specialism;
		this.iD = iD;
		this.hospital = hospital;
	}
	
	/**
	 * Get doctor's specialism.
	 * @return	The doctor's specialism represented in int.
	 */
	public int getSpecialism(){
		return specialism;
	}

	/**
	 * Get doctor ID.
	 * @return	Doctor's ID.
	 */
	public int getID(){
		return iD;
	}
	
	/**
	 * Get the hospital that the doctor is working in.
	 * @return	The hospital.
	 */
	public Hospital getHospital(){
		return hospital;
	}
	
	/**
	 * Try to assign patient to a specific doctor.
	 * <p>
	 * It checks whether the doctor has the specialism required.
	 * 
	 * @param patient  The patient to be assigned.
	 * @return	Whether the assignment is successful.
	 */
	public boolean tryAssignPatient(Patient patient){
		int illness = patient.getIllness();
		if(illness >= 1 && illness <= 3){
			assignedPatient = patient;
			assignedPatient.setTakenCareOf(true);
			System.out.println("Doctor "+iD+" is assigned to patient: Aged "+assignedPatient.getAge()+" ,"+assignedPatient.getGender()+".");
			return true;
		}else{
			System.out.println("Doctor "+iD+" CANNOT be assigned with patient: Aged "+patient.getAge()+" ,"+patient.getGender()+".");
			return false;
		}	
	}
	
	/**
	 * Officially assigning the patient to doctor.
	 * @param patient	The patient to assign.
	 */
	public void setAssignedPatient(Patient patient){
		assignedPatient = patient;
	}
	
	/**
	 * Get doctor's assigned patient.
	 * @return	The patient.
	 */
	public Patient getAssignedPatient(){
		return assignedPatient;
	}

	/**
	 * Treat the patient.
	 * <p>
	 * First check if the doctor is assigned with a patient, else it assumes that the doctor has treat a patient.
	 * @throws Exception 
	 */
	public void treatPatient() throws Exception{
		//If the surgeon is assigned with a patient
		if(assignedPatient != null){
			int illness = assignedPatient.getIllness();
			if(illness >= 1 && illness <=3){
				//Set patient to recovering
				assignedPatient.setHealthState(2);
				//Set recovery time for patient
				assignedPatient.setRecoveryTime(assignedPatient.getIllnessRecoveryTime(assignedPatient.getIllness()));
				System.out.println("Doctor "+iD+" Treat patient successfully!");
				System.out.println("Patient requires "+getAssignedPatient().getRecoveryTime()+" days to recover.");
			}else
				throw new Exception("This surgeon cannot treat this illness!");
		}
	}
	
	/**
	 * The workflow of a doctor in a day.
	 * <p>It treats the patient and release the patient.
	 * 
	 * @return	Whether the workflow is successful.
	 */
	public boolean aDayPasses(){
		try{
			treatPatient();
			//Release patient from doctor. He is now free after the day.
			assignedPatient = null;
			System.out.println("Doctor "+getID()+" ADayPasses.");
		}catch (Exception e){
			System.err.println(e);
		}
		return true;
	}
}
