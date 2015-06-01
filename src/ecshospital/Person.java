package ecshospital;

/**
 * The <b>Person</b> class specifies the behaviour of a person including Patient and Doctors.
 * @author Man-Leong Chan
 */
public abstract class Person extends Health{
	private char gender;
	private int age;
	private Health health;
	
	/**
	 * Constructor for a healthy person.
	 * <p>This uses the second constructor {@link #Person(char, int, int, int, int)}.
	 * @param gender   		A String of the person's gender.
	 * @param age      		An integer representation of the person's age.
	 * @throws Exception 	Throw Exception from the second Perosn constructor {@link #Person(char, int, int, int, int)}. 
	 */
	Person(char gender, int age) throws Exception{
		this(gender,age,0,0,0);			//All healthy
	}
	
	/**
	 * Constructor of an incoming patient
	 * @param gender         A String of the person's gender.
	 * @param age            An integer representation of the person's age.
	 * @param healthState    An integer representation of the person's heath state. Denoted in the {@link ecshospital.health} class.
	 * @param illness		 An integer representation of the person's illness. Denoted in the {@link ecshospital.health} class.
	 * @param recoveryTime	 Time until the patient full recovery in days.
	 * @throws 	Exception if illegal gender or age format.
	 */
	Person(char gender, int age, int healthState, int illness,int recoveryTime) throws Exception{
		super(healthState,illness,recoveryTime);
		if(gender != 'M' && gender != 'F')
			throw new Exception("Patient can only be Male or Female! ERROR");
		else
			this.gender = gender;
		
		if(age<0)
			throw new Exception("Invalid age entered! ERROR");
		else
			this.age = age;
	}
	
	/**
	 * Set person's gender.
	 * @param gender	The person's gender in char.
	 */
	public void setGender(char gender){
		this.gender = gender;
	}
	
	/**
	 * Get person's gender.
	 * @return	The person's gender in char.
	 */
	public char getGender(){
		return gender;
	}
	
	/**
	 * Set person's age.
	 * @param age	The person's age in int.
	 */
	public void setAge(int age){
		this.age = age;
	}

	/**
	 * Get person's age.
	 * @return The person's age in int.
	 */
	public int getAge(){
		return age;
	}
	
	/**
	 * Get the person's health status.
	 * @return	The class health.
	 */
	public Health getHealth(){
		return health;
	}
	
	/**
	 * A workflow of a person in a day.
	 * @return	Whether the workflow is successful.
	 */
	public abstract boolean aDayPasses();
}