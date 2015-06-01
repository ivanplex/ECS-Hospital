package ecshospital;

/**
 * <b>Organ Surgeon</b> inherits from <b>Surgeon</b> with the ability to cure specific organ related illnesses.
 * @author Man-Leong Chan
 */
public class OrganSurgeon extends Surgeon{
	/**
	 * @param iD			Organ Surgeon ID. All Surgeon has ID so that we can easily identify their movement.
	 * @param gender		Organ Surgeon's gender in char.
	 * @param age			Organ Surgeon's age.
	 * @param hospital		The hospital that the Organ Surgeon works in.
	 * @throws Exception	From person class if illegal age or gender is enter.
	 */
	OrganSurgeon(int iD,char gender, int age,Hospital hospital) throws Exception{
		//This pass specialism 4, the specialism ID for Organ Surgeons.
		super(iD,gender,age,4,hospital);
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
		if(illness >= 1 && illness <= 6){
			setAssignedPatient(patient);
			getAssignedPatient().setTakenCareOf(true);
			System.out.println("Organ Surgeon "+getID()+" is assigned to patient: Aged "+getAssignedPatient().getAge()+" ,"+getAssignedPatient().getGender()+".");
			return true;
		}else{
			System.out.println("Organ Surgeon "+getID()+" CANNOT be assigned with patient: Aged "+patient.getAge()+" ,"+patient.getGender()+".");
			return false;
		}	
	}
	
	/**
	 * Treat the patient.
	 * <p>
	 * First check if the organ surgeon is assigned with a patient, else it assumes that the surgeon has treat a patient.
	 * @throws Exception 	If the Organ surgeon can't treat the illness.
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
			}else if(illness >= 4 && illness <= 6){
				System.out.println("Operation NEEDED!");
				operate();
			}else
				throw new Exception("This Organ Surgeon cannot treat this illness!");
		}
	}
}
