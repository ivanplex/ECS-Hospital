package ecshospital;

import java.util.HashMap;

/**
 * The are values and properties that are customizable in the ECS hospital, new properties could be imported from the configuration file.
 * <p>However, if the setting (E.g. modify recovery time) has a syntax error, the program will ignore the new configuration and continue the use of the default setting.
 * @author Man-Leong Chan
 *
 */
public interface Customizable {
	
	/*
	 * illnessRecoveryTimeMap maps the range of recovery time needed from a particular illness. 
	 * The key contains the illness that the patient could suffer from.
	 * The value contains an array of two numbers which indicates the range of possible recovery time needed.
	 */
	HashMap<Integer, Integer[]> illnessRecoveryTimeMap = new HashMap<Integer,Integer[]>();

	
}
