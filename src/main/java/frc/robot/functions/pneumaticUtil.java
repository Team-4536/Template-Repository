package frc.robot.functions;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.subsystems.PneumaticData;

public class pneumaticUtil {
    


    public static void toggleSolenoid(PneumaticData pData){

        
        pData.brakeSolenoid.toggle();
        
    }

    public static void retractAll(PneumaticData pData){
        
        pData.brakeSolenoid.set(Value.kReverse);

    }

    public static void extendAll(PneumaticData pData){
        
        pData.brakeSolenoid.set(Value.kForward);

    }
    

    }

