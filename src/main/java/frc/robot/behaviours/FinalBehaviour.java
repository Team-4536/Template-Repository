package frc.robot.behaviours;

import java.util.function.Consumer;


import frc.robot.Robot;
import frc.robot.functions.driveUtil;
import frc.robot.functions.visionUtil;
import frc.robot.V2d;
import frc.robot.Constants.ControlInfo;
import frc.robot.functions.*;



public class FinalBehaviour {

    static double startAngle = 0;


    public static @Hidden Consumer<Robot> teleOpPeriodic = r -> {


        // angular
        double t = inputUtil.deadzoneAxis(r.input.controller.getRightX(), ControlInfo.TURNING_DEADZONE);

        double turningScalar = inputUtil.mapInput(
            r.input.controller.getLeftTriggerAxis(),
            1, 0, ControlInfo.MAX_TURNING_OUT, ControlInfo.DEFAULT_TURNING_OUT);
        r.drive.pidController.target += t * turningScalar * Robot.dt;


        double PIDOut = -r.drive.pidController.tick(r.gyro.globGyroscope.getAngle(), Robot.dt, true);



        // linear

        double xIn = inputUtil.deadzoneAxis(r.input.controller.getLeftX(), ControlInfo.MOVEMENT_DEADZONE);
        double yIn = inputUtil.deadzoneAxis(r.input.controller.getLeftY(), ControlInfo.MOVEMENT_DEADZONE);
        V2d input = new V2d(xIn, yIn);

        input = input.rotateDegrees(-gyroUtil.wrapAngle(r.gyro.globGyroscope.getAngle() - startAngle));



        double driveScalar = inputUtil.mapInput(
            r.input.controller.getRightTriggerAxis(),
            1, 0, ControlInfo.MAX_DRIVE_OUT, ControlInfo.DEFAULT_DRIVE_OUT);

        double strafeScalar = inputUtil.mapInput(
            r.input.controller.getRightTriggerAxis(),
            1, 0, ControlInfo.MAX_DRIVE_OUT, ControlInfo.DEFAULT_DRIVE_OUT);



        driveUtil.setPowerMechanum(r.drive,
            input.x * strafeScalar,
            input.y * driveScalar,
            PIDOut,
            0.5); 

            r.drive.backLeftDrive.set(.5);

            //driveUtil.setPowerUniform(r.drive, .5);
            
            // end of drive stuff :)






        visionUtil.distanceFrom(r.vision.getArea());
        //r.vision.pipelineTag(7);

    };

    @Hidden
    public static Consumer<Robot> teleOpInit = r -> {

        BehaviourUtil.stopDrive.accept(r);
        r.gyro.globGyroscope.reset();
        r.drive.pidController.target = r.gyro.globGyroscope.getAngle();
        startAngle = r.drive.pidController.target;



    };
}

