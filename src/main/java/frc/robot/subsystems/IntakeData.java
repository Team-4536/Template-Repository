package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import frc.robot.constants.Hardware;
import frc.robot.controllers.PIDController;
import frc.robot.functions.telemetryUtil;
import frc.robot.functions.telemetryUtil.Tabs;

public class IntakeData {

    public PneumaticsControlModule pcm = PneumaticData.pcm;

    public PIDController robAldrich = new PIDController(0.001, 0.5, 0.3);

    public static boolean status = true;

    public DoubleSolenoid grabberSolenoid = pcm.makeDoubleSolenoid(Hardware.GRABBER_SOLENOID_FORWARD_PORT,
        Hardware.GRABBER_SOLENOID_REVERSE_PORT);





    public void sendTelemetry(){
        telemetryUtil.put("Grabber is extended", grabberSolenoid.get() == DoubleSolenoid.Value.kForward,Tabs.ROBOT);
    }

}
