package ecshospital;

/**
 * The <b>Patient</b> class models behaviours which a ECS hospital's patient could have.
 * @author Man-Leong Chan
 */
public class Patient extends Person{
	
	/**
	 * @param takenCareOf   A boolean representation for whether the patient is taken care of by a doctor.
	 */
	private boolean takenCareOf;
	
	/**
	 * This is a default constructor for patients with no specified illness and recoveryTime. 
	 * <p>This constructor passes the parameters to the second constructor {@link #Patient(char, int, int, int, int)}.
	 * <p>The patient healthState is initialized as 1, indicating that they are ill.
	 * <p>The illness is pre-set to 2: They have Java Flu by default.
	 * <p>The recovery time is -1, meaning that they are untreated.
	 * 
	 * @param gender       		A character of the patient's gender.
	 * @param age         		An integer representation of the patient's age.
	 * @throws Exception		A constructor error.
	 * @see Health
	 */
	Patient(char gender, int age) throws Exception{
		this(gender,age,1,2,-1);
	}
	
	/**
	 * Constructor for the Patient.
	 * 
	 * @param gender       		A character of the patient's gender.
	 * @param age         		An integer representation of the patient's age.
	 * @param healthState		An integer representation of the patient's health state.
	 * @param illness      		An integer representation of the patient's illness. Denoted in the {@link Health} class.
	 * @param recoveryTime 		An integer representation of the patient's recovery time in days.
	 * @throws Exception		A constructor error from {@link (default package).Person#Patient(char, int, int, int, int)}.
	 * @see Health
	 */
	Patient(char gender, int age,int healthState,int illness, int recoveryTime) throws Exception{
		super(gender,age,healthState,illness,recoveryTime);
		takenCareOf = false;
	}
	
	/**
	 * Whether the patient is taken care by a doctor.
	 * @return	A boolean.
	 */
	public boolean takenCareOf(){
		return takenCareOf;
	}
	
	/**
	 * Set the instance takenCareOf
	 * @param takenCareOf	A new boolean value to be set.
	 */
	public void setTakenCareOf(boolean takenCareOf){
		this.takenCareOf = takenCareOf;
	}
	
	/**
	 * A workflow of what a patient will do in a day.
	 * <p> It decrement the recoveryTime by a day if the patient is recovering.
	 * <p> It set the patient to healthy if their recoveryTime is 0.
	 * <p> Output an error if the patient is healthy but not discharged.
	 * 
	 * @return	A boolean of whether the workflow was successful.
	 */
	public boolean aDayPasses(){
		if(getHealthState() == 2){
			setRecoveryTime(getRecoveryTime()-1);
			System.out.println("Patient (Age "+getAge()+", "+getGender()+") Recovery Time Remaining: "+getRecoveryTime()+" days.");
		}
		if(getRecoveryTime() <= 0 && getHealthState() == 2){
			setHealthState(0);
			System.out.println("Patient now Healthy.");
		}
			
		if(getRecoveryTime() < 0 && getHealthState() == 2)
			System.out.println("ERROR: This patient has not been discharged!");
		
		return true;
	}
	
}