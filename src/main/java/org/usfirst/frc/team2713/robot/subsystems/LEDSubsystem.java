package org.usfirst.frc.team2713.robot.subsystems;

import com.mindsensors.CANLight;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team2713.robot.RGBValue;
import org.usfirst.frc.team2713.robot.RobotMap;
import org.usfirst.frc.team2713.robot.commands.led.LEDLoopCommand;

import java.util.ArrayList;
import java.util.List;

public class LEDSubsystem extends Subsystem {
  private CANLight lights = new CANLight(RobotMap.ledPort);
  private List<Command> commands = new ArrayList<>();
  
  public LEDSubsystem() {
    lights.reset();
  }
  
  /**
   * Sets the LEDs to the current alliance reported by the DriverStation
   */
  public void setToAllianceColor() {
    cancelAllCommands();
    switch (DriverStation.getInstance().getAlliance()){
      case Red:
        lights.showRGB(255, 0, 0);
        break;
    
      case Blue:
        lights.showRGB(0, 0, 255);
        break;
    
      case Invalid:
      default:
        lights.showRGB(255, 200, 0);
        break;
    }
  }
  
  public void rainbowLoop() {
    cancelAllCommands();
    
    LEDLoopCommand ledLoopCommand = new LEDLoopCommand(this);
    commands.add(ledLoopCommand);
    ledLoopCommand.start();
  }
  
  public void colorBlink(RGBValue color, double duration) {
    cancelAllCommands();
    
    List<RGBValue> colors = new ArrayList<>();
    colors.add(color); // Add main color
    colors.add(new RGBValue(0, 0, 0)); // All off
    
    LEDLoopCommand ledLoopCommand = new LEDLoopCommand(this, colors, Integer.MAX_VALUE, duration);
    ledLoopCommand.start();
    commands.add(ledLoopCommand);
  }
  
  public void cancelAllCommands() {
    for (Command command : commands) {
      command.cancel();
      commands.remove(command);
    }
  }
  
  /**
   * Returns a list of RGBValues to form a rainbow. For example,
   * if you pass 3 for numberShifts, you will get an array of
   * Red, Green, and Blue. Passing 255 will give you a complete
   * rainbow, but something like 100 should be sufficient enough
   *
   * @param numberShifts Number of Colors
   * @return An array of RGB Values
   * @see RGBValue
   */
  public static List<RGBValue> getRainbowLoop(int numberShifts) {
    // Adapted from https://tinyurl.com/y7ubkd9l
    List<RGBValue> colors = new ArrayList<>();
    int frequency = 5 / numberShifts;
    for (int i = 0; i < numberShifts; i++) {
      int r = (int) Math.sin(frequency * i + 0) * (127) + 128;
      int g = (int) Math.sin(frequency * i + 1) * (127) + 128;
      int b = (int) Math.sin(frequency * i + 3) * (127) + 128;
      colors.add(new RGBValue(r, g, b));
    }
    return colors;
  }
  
  @Override
  protected void initDefaultCommand() {
    setToAllianceColor();
  }
  
  public CANLight getLights() {
    return lights;
  }
}
