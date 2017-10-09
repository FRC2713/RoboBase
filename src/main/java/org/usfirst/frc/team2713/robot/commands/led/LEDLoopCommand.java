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
  
  public LEDLoopCommand(LEDSubsystem ledSubsystem) {
    this.ledSubsystem = ledSubsystem;
    this.colors = LEDSubsystem.getRainbowLoop(255);
    this.loops = 1;
    this.duration = 0.05;
    
    requires(ledSubsystem);
    this.setInterruptible(true);
  }
  
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
  protected boolean isFinished() {
    return isFinished;
  }
}
