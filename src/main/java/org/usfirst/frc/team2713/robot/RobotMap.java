package org.usfirst.frc.team2713.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Motors
	public static int frontLeft = 1;
	public static int frontRight = 2;
	public static int backLeft = 3;
	public static int backRight = 4;
	
	// Controllers
  public static int BACKUP_XBOX_PORT = 0;
  public static int BACKUP_ATTACK_PORT = 1;
  public static String XBOX_NAME = "Controller (XBOX 360 For Windows)";
  public static String FIGHT_NAME = "Mayflash Arcade Stick";
	
	// Sensors
  
  // SmartDash Settings
  
  // Misc
  public static int ledPort = 50;
}
