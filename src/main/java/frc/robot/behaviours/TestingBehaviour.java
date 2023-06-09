package frc.robot.behaviours;

import java.util.function.Consumer;

import frc.robot.Robot;
import frc.robot.controllers.PIDController;
import frc.robot.functions.telemetryUtil;
import frc.robot.functions.telemetryUtil.Tabs;


public class TestingBehaviour {


    public static Consumer<Robot> testLog = r -> {
        telemetryUtil.put("Testing str", "HI THERE", Tabs.DEBUG);
    };

    public static PIDController armPID = new PIDController(0.001, 0.0f, 0.0f);



    /*
    public static Consumer<Robot> systemTest = r -> {

        //DRIVE
        double x = inputUtil.deadzoneAxis(r.input.controller.getLeftX(), 0.20);
        double y = inputUtil.deadzoneAxis(-r.input.controller.getLeftY(), 0.20) * .5;
        double z = inputUtil.deadzoneAxis(r.input.controller.getRightX(), 0.20) * .6;

        if (DriveData.joystickDrive){

            x = inputUtil.deadzoneAxis(r.input.joystick.getX(), 0.20);
            y = inputUtil.deadzoneAxis(-r.input.joystick.getY(), 0.20);
            z = inputUtil.deadzoneAxis(r.input.joystick.getZ(), 0.20);

        }

        r.drive.pidController.target += Robot.dt * z * 60;

        //double PIDOut = -r.drive.pidController.tick(r.gyro.globGyroscope.getAngle(), Robot.dt, true);
        driveUtil.setPowerMechanum(r.drive, x, y, z, 0.22);


        //PNEUMATICS
        if (r.input.controllerMech.getAButtonPressed()){ IntakeData.status = !IntakeData.status; }
        if (r.input.controllerMech.getBButtonPressed()){ PneumaticData.status = !PneumaticData.status; }
        pneumaticUtil.runCondition(r.brakes, PneumaticData.status);
        armUtil.runCondition(r.grabber, IntakeData.status);


        //ARM
        telescopeUtil.liftScale(r.telescope, inputUtil.deadzoneAxis(-r.input.controllerMech.getRightY(), .1), .8, .2);
        r.telescope.retractMotor.set(inputUtil.deadzoneAxis(r.input.controllerMech.getLeftY(), .1)/1.4);

        //TURRET
        double flymer = inputUtil.deadzoneAxis(r.input.controllerMech.getRightTriggerAxis() - 
                                               r.input.controllerMech.getLeftTriggerAxis(), .1)/8;
        turretUtil.run(r.turret, flymer);



    }
    */


}
