package org.usfirst.frc.team2713.robot.commands.led;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2713.robot.RGBValue;
import org.usfirst.frc.team2713.robot.subsystems.LEDSubsystem;

import java.util.List;

public class LEDLoopCommand extends Command {
  private LEDSubsystem ledSubsystem;
  private List<RGBValue> colors;
  private int loops;
  private double duration;
  private int executedLoops = 0;
  private boolean isFinished = false;
  
  /**
   * Fade through a rainbow once
   * @param ledSubsystem the LED Subsystem
   */
  public LEDLoopCommand(LEDSubsystem ledSubsystem) {
    this(ledSubsystem, LEDSubsystem.getRainbowLoop(255), 1, 0.05);
  }
  
  /**
   * Loops through a list of RGBValues, each color shift taking duration
   * @param ledSubsystem the LED Subsystem
   * @param colors List of RGBValues to cycle through
   * @param loops Number of loops to iterate through
   * @param duration How long each fade should last
   */
  public LEDLoopCommand(LEDSubsystem ledSubsystem, List<RGBValue> colors, int loops, double duration) {
    this.ledSubsystem = ledSubsystem;
    this.colors = colors;
    this.loops = loops;
    this.duration = duration;
    
    requires(ledSubsystem);
    this.setInterruptible(true);
  }
  
  @Override
  protected void execute() {
    for (int i = 0; i < colors.size(); i++) {
      ledSubsystem.getLights().writeRegister(6, duration, colors.get(i).red, colors.get(i).green, colors.get(i).blue);
      ledSubsystem.getLights().writeRegister(7, duration, colors.get(i+1).red, colors.get(i+1).green, colors.get(i+1).blue);
      ledSubsystem.getLights().fade(6, 7);
    }
    executedLoops++;
    if (executedLoops >= loops) isFinished = true;
  }
  
  @Override
  protected void end() {
    ledSubsystem.getLights().showRGB(0, 0, 0);
  }
  
  @Override
  protected void interrupted() {
    /*
    Do nothing. When gracefully ending, it is OK to go to black
    but interrupting usually means that another color command is taking over
    so to avoid a flash to black just keep the last set value
     */
  }
  
  @Override
  protected boolean isFinished() {
    return isFinished;
  }
}
