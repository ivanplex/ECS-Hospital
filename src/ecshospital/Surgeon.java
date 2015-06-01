package ecshospital;

/**
 * <b>Surgeon</b> inherits from Doctors with specified skills including the ability to operate surgeries. 
 * @author Man-Leong Chan
 *
 */
public class Surgeon extends Doctors{
	/**
	 * Constructor for pure Surgeon only. This is not used by LimbSurgeon or OrganSurgeon.
	 * @param iD			Surgeon ID. All Surgeon has ID so that we can easily identify their movement.
	 * @param gender		Surgeon's gender in char.
	 * @param age			Surgeon's age.
	 * @param hospital		The hospital that the Surgeon works in.
	 * @throws Exception	From person class if illegal age or gender is enter.
	 */
	Surgeon(int iD,char gender, int age, Hospital hospital) throws Exception{
		//This pass specialism 2, the specialism ID for surgeons.
		super(iD,gender,age,2,hospital);
	}
	
	/**
	 * @param iD			Surgeon ID. All Doctor/Surgeon has ID so that we can easily identify their movement.
	 * @param gender		Surgeon's gender in char.
	 * @param age			Surgeon's age.
	 * @param specialism	The specialism representation in int.
	 * @param hospital		The hospital that the Surgeon works in.
	 * @throws Exception	From person class if illegal age or gender is enter.
	 */
	Surgeon(int iD,char gender, int age, int specialism, Hospital hospital) throws Exception{
		super(iD,gender,age,specialism,hospital);
	}
	
	/**
	 * Try to assign patient to a specific surgeon.
	 * <p>
	 * It checks whether the surgeon has the specialism required.
	 * 
	 * @param patient  The patient to be assigned.
	 * @return	Whether the assignment is successful.
	 */
	public boolean tryAssignPatient(Patient patient){
		int illness = patient.getIllness();
		if(illness >= 1 && illness <= 4){
			setAssignedPatient(patient);
			getAssignedPatient().setTakenCareOf(true);
			System.out.println("Surgeon "+getID()+" is assigned to patient: Aged "+getAssignedPatient().getAge()+" ,"+getAssignedPatient().getGender()+".");
			return true;
		}else{
			System.out.println("Surgeon "+getID()+" CANNOT be assigned with patient: Aged "+patient.getAge()+" ,"+patient.getGender()+".");
			return false;
		}	
	}
	
	/**
	 * Treat the patient.
	 * <p>
	 * First check if the surgeon is assigned with a patient, else it assumes that the surgeon has treat a patient.
	 * @throws Exception 
	 */
	public void treatPatient() throws Exception{
		//If the surgeon is assigned with a patient
		if(getAssignedPatient() != null){
			int illness = getAssignedPatient().getIllness();
			if(illness >= 1 && illness <=3){
				//Set patient to recovering
				getAssignedPatient().setHealthState(2);
				//Set recovery time for patient
				getAssignedPatient().setRecoveryTime(getAssignedPatient().getIllnessRecoveryTime(getAssignedPatient().getIllness()));
				System.out.println("Treat patient successfully!");
				System.out.println("Patient requires "+getAssignedPatient().getRecoveryTime()+" days to recover.");
			}else if(illness == 4){
				System.out.println("Operation NEEDED!");
				operate();
			}else
				throw new Exception("This surgeon cannot treat this illness!");
			
		}

	}
	
	/**
	 * Treat patient using one of the operation theatre. This requires a free theatre.
	 */
	public void operate() throws Exception{
		if(getHospital().prepForTheatre(getAssignedPatient())){
			
			//Set patient to recovering
			getAssignedPatient().setHealthState(2);
			
			//Set recovery time for patient
			getAssignedPatient().setRecoveryTime(getAssignedPatient().getIllnessRecoveryTime(getAssignedPatient().getIllness()));
			
			System.out.println("Operation successful!");
			System.out.println("Patient requires "+getAssignedPatient().getRecoveryTime()+" days to recover.");
		}else
			throw new Exception("No operation theatre free! Patient:"+getAssignedPatient().getGender()+","+getAssignedPatient().getAge()
					+" with illness "+getAssignedPatient().getIllness()+" cannot be cured! ERROR");
	}
}
