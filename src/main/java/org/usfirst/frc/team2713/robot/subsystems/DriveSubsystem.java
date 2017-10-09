package org.usfirst.frc.team2713.robot.subsystems;

import com.ctre.MotorControl.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2713.robot.RobotMap;
import org.usfirst.frc.team2713.robot.commands.OIDrive;

public class DriveSubsystem extends Subsystem {
  public RobotDrive roboDrive;
  private CANTalon frontLeft = new CANTalon(RobotMap.frontLeft);
  private CANTalon frontRight = new CANTalon(RobotMap.frontRight);
  private CANTalon backLeft = new CANTalon(RobotMap.backLeft);
  private CANTalon backRight = new CANTalon(RobotMap.backRight);
  
  public DriveSubsystem() {
    startTeleop();
  }
  
  @Override
  protected void initDefaultCommand() {
    startTeleop();
  }
  
  public void startTeleop() {
    roboDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
    new OIDrive(this).start();
  }
  
  /**
   * Returns the speed, corrected for the deadband. This is used usually when getting
   * speed inputs from a Joystick, as joysticks usually report values slightly
   * different then what is intended
   * @param speed The current desired speed (usually from the joystick)
   * @param deadbandTolerance The amount of deadband to remove from speed
   * @return The corrected speed
   */
  public double getDeadband(double speed, double deadbandTolerance) {
    if (Math.abs(speed) < deadbandTolerance) return 0; // Desired speed is under the tolerance, ignore it
    return Math.abs(speed) - Math.abs(deadbandTolerance) * Math.signum(speed);
  }
}
