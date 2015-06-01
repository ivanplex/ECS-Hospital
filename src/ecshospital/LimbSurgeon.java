package ecshospital;

/**
 * <b>Limb Surgeon</b> inherits from <b>Surgeon</b> with the ability to cure specific limb related illnesses.
 * @author Man-Leong Chan
 */
public class LimbSurgeon extends Surgeon{
	/**
	 * @param iD			Limb Surgeon ID. All Surgeon has ID so that we can easily identify their movement.
	 * @param gender		Limb Surgeon's gender in char.
	 * @param age			Limb Surgeon's age.
	 * @param hospital		The hospital that the Limb Surgeon works in.
	 * @throws Exception	From person class if illegal age or gender is enter.
	 */
	LimbSurgeon(int iD,char gender, int age,Hospital hospital) throws Exception{
		//This pass specialism 3, the specialism ID for Limb Surgeons.
		super(iD,gender,age,3,hospital);
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
		if((illness >= 1 && illness <= 4) || (illness >= 7 && illness <= 8)){
			setAssignedPatient(patient);
			getAssignedPatient().setTakenCareOf(true);
			System.out.println("Limb Surgeon "+getID()+" is assigned to patient: Aged "+getAssignedPatient().getAge()+" ,"+getAssignedPatient().getGender()+".");
			return true;
		}else{
			System.out.println("Limb Surgeon "+getID()+" CANNOT be assigned with patient: Aged "+patient.getAge()+" ,"+patient.getGender()+".");
			return false;
		}	
	}
	
	/**
	 * Treat the patient.
	 * <p>
	 * First check if the limb surgeon is assigned with a patient, else it assumes that the surgeon has treat a patient.
	 * @throws Exception 	If the limb surgeon can't treat the illness.
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
			}else if((illness == 4) || (illness >= 7 && illness <= 8)){
				System.out.println("Operation NEEDED!");
				operate();
			}else
				throw new Exception("This Limb Surgeon cannot treat this illness!");
		}
	}
	
}
