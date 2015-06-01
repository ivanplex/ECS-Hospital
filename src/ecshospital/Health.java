package ecshospital;
import java.util.Random;

/**
 * <b>Health</b> models a person's health. 
 * @author Man-Leong Chan
 */
public abstract class Health implements Customizable{
	/**
	 * 0: Healthy
	 * 1: Sick
	 * 2: Recovering
	 */
	private int healthState;
	/**
	 * Time to recover in days
	 * recoveryTime for healthy is 0
	 */
	private int recoveryTime;
	/**
	 * The type of illness
	 */
	private int illness;
	
	/**
	 * Constructor including a HashMap for illness-recovery time. 
	 * <p>This maps the illness with the required recovery time for illness 1,2&3.
	 * 
	 * @param healthState				Integer representation of the person's health state.
	 * @param illness					Integer representation of the person's illness.
	 * @param recoveryTime				Time in days until full recovery.
	 * @param illnessRecoveryTimeMap	A HashMap for all illness's recovery time.
	 */
	Health(int healthState, int illness, int recoveryTime){
		this.healthState = healthState;
		this.illness = illness;
		this.recoveryTime = recoveryTime;
	}
	
	/**
	 * Set health status.
	 * @param healthState	healthStatus to be set.
	 */
	public void setHealthState(int healthState){
		this.healthState = healthState;
	}
	
	/**
	 * Get health status.	
	 * @return	Person's health status.
	 */
	public int getHealthState(){
		return healthState;
	}
	
	/**
	 * Set illness status.
	 * @param illness	The illness status to be set.
	 */
	public void setIllness(int illness){
		this.illness = illness;
	}

	/**
	 * Get person's illness status.
	 * @return	Person's illness status.
	 */
	public int getIllness(){
		return illness;
	}

	/**
	 * Set person's recovery time.
	 * @param recoveryTime	person's recovery time to be set.
	 */
	public void setRecoveryTime(int recoveryTime){
		this.recoveryTime = recoveryTime;
	}
	
	/**
	 * Get recovery time.
	 * @return	recovery time in days.
	 */
	public int getRecoveryTime(){
		return recoveryTime;
	}
	
	/**
	 * This method helps to create the random recovery time for illnesses.
	 * @param illness	The illness that the patient suffers.
	 * @return		The randomized recovery time.
	 */
	public int randomizeRecoveryTime(int illness) throws Exception{
		if(illnessRecoveryTimeMap.containsKey(illness)){
			Random randomGenerator = new Random();
			int randomRecoveryTime;
			
			Integer[] illnessRecoveryTimeRange = illnessRecoveryTimeMap.get(illness);
			int minRecoveryTime = illnessRecoveryTimeRange[0];
			int maxRecoveryTime = illnessRecoveryTimeRange[1];
			
			do{
				/*
				 * Generate a range of number between 0 - 99.
				 * Although the maximum illness recovery time so far is 8 days(Semicolon Missing, Trapped Exception),
				 * This allow the configuration file to create illness with recovery time exceeding 10 days.
				 */
				randomRecoveryTime = randomGenerator.nextInt(100);
			}while(randomRecoveryTime < minRecoveryTime || randomRecoveryTime > maxRecoveryTime);
			return randomRecoveryTime;
		}else{
			throw new Exception("This illness does not exist!");
		}
	}
	
	/**
	 * This method uses the {@link ecshospital.Customizable#illnessRecoveryTimeMap} to check the recovery time for illness 1-3. The Map is initialized by the constructor.
	 * @param illness	The illness that the patient suffers.
	 * @return			The recovery time in days.
	 * @throws Exception	If illegal illness entered.
	 */
	public int getIllnessRecoveryTime(int illness) throws Exception{
		return randomizeRecoveryTime(illness);
	}
}
