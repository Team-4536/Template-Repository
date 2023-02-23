// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.behaviours.AutoBehaviours;
import frc.robot.behaviours.FinalBehaviour;
import frc.robot.constants.Constants;
import frc.robot.functions.pneumaticUtil;
import frc.robot.functions.robotUtil;
import frc.robot.functions.telemetryUtil;
import frc.robot.subsystems.AutoData;
import frc.robot.subsystems.DriveData;
import frc.robot.subsystems.GyroData;
import frc.robot.subsystems.InputData;
import frc.robot.subsystems.PositionData;
import frc.robot.subsystems.IntakeData;
import frc.robot.subsystems.PneumaticData;
import frc.robot.subsystems.TelescopeData;
import frc.robot.subsystems.TurretData;
import frc.robot.subsystems.VisionData;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    //#region fns!
    private static final Consumer<Robot> NULL_FUNC = r -> { };

    public static Consumer<Robot> ROBOT_INIT_FUNC = NULL_FUNC;
    public static Consumer<Robot> ROBOT_PER_FUNC = NULL_FUNC;

    public static Consumer<Robot> TELEOP_INIT_FUNC = FinalBehaviour.teleOpInit;
    public static List<Consumer<Robot>> TELEOP_PER_FUNCS = List.of(NULL_FUNC, NULL_FUNC, NULL_FUNC, NULL_FUNC, NULL_FUNC);

    public static Consumer<Robot> AUTO_INIT_FUNC = NULL_FUNC;

    public static Consumer<Robot> TEST_INIT_FUNC = NULL_FUNC;
    public static Consumer<Robot> TEST_PER_FUNC = NULL_FUNC;

    //#endregion



    public static Instant startTime;
    public static Instant prevtime;
    public static double dt;
    public static double timeSinceInit;



    public static Robot instance;



    public DriveData drive;
    public InputData input;
    public VisionData vision;
    public GyroData gyro;
    public PositionData positionData;
    public TurretData turret;
    public TelescopeData telescope;
    public PneumaticData brakes;
    public IntakeData grabber;
    public AutoData autoData;





    // runs once when the robot is turned on
    @Override
    public void robotInit() {

        instance = this;

        startTime = Instant.now();
        prevtime = Instant.now();


        telemetryUtil.makeChooser("Auto Init", x -> { Robot.AUTO_INIT_FUNC = x; }, "nothing", AutoBehaviours.class);
        telemetryUtil.makeChooser("Teleop Init", x -> { Robot.TELEOP_INIT_FUNC = x; }, "nothing", FinalBehaviour.class);
        telemetryUtil.makeChooser("TeleOp Per 0", x -> { Robot.TELEOP_PER_FUNCS.set(0, x); }, "nothing", FinalBehaviour.class);
        telemetryUtil.makeChooser("TeleOp Per 1", x -> { Robot.TELEOP_PER_FUNCS.set(1, x); }, "nothing", FinalBehaviour.class);
        telemetryUtil.makeChooser("TeleOp Per 2", x -> { Robot.TELEOP_PER_FUNCS.set(2, x); }, "nothing", FinalBehaviour.class);
        telemetryUtil.makeChooser("TeleOp Per 3", x -> { Robot.TELEOP_PER_FUNCS.set(3, x); }, "nothing", FinalBehaviour.class);
        telemetryUtil.makeChooser("TeleOp Per 4", x -> { Robot.TELEOP_PER_FUNCS.set(4, x); }, "nothing", FinalBehaviour.class);

        this.drive = new DriveData();
        this.input = new InputData();
        this.vision = new VisionData();
        this.gyro = new GyroData();
        this.positionData = new PositionData(gyro, drive);
        this.turret = new TurretData();
        this.telescope = new TelescopeData();
        this.brakes = new PneumaticData();
        this.grabber = new IntakeData();
        this.autoData = new AutoData();


        this.vision.pipelineTag(1);
        pneumaticUtil.runSolenoid(grabber.grabberSolenoid, true);

        ROBOT_INIT_FUNC.accept(this);
    }

    // runs constantly, no matter the mode
    // don't put motor control stuff in here lol
    @Override
    public void robotPeriodic() {

        dt = Duration.between(prevtime, Instant.now()).toNanos() * (1.0/Constants.NANOS_PER_SECOND);
        timeSinceInit = Duration.between(startTime, Instant.now()).toNanos() * (1.0/Constants.NANOS_PER_SECOND);
        prevtime = Instant.now();

        vision.pipelineTag(1);

        telemetryUtil.grabChoosers();

        this.drive.sendTelemetry();
        this.input.sendTelemetry();
        this.gyro.sendTelemetry();
        this.vision.sendTelemetry();
        this.positionData.sendTelemetry();
        this.turret.sendTelemetry();
        this.telescope.sendTelemetry();
        this.brakes.sendTelemetry();
        this.grabber.sendTelemetry();
        this.autoData.sendTelemetry();

        ROBOT_PER_FUNC.accept(this);
    }








    @Override
    public void autonomousInit() {
        this.autoData.reset(this);
        autoData.autoRunning = true;
        Robot.AUTO_INIT_FUNC.accept(this);
    }

    @Override
    public void autonomousPeriodic() {
        autoData.update(this);
    }

    @Override
    public void autonomousExit() {
        autoData.autoRunning = false;
        robotUtil.stopRobot(this);
    }








    @Override
    public void teleopInit() {  TELEOP_INIT_FUNC.accept(this); }
    @Override
    public void teleopPeriodic() {
        for(Consumer<Robot> c : TELEOP_PER_FUNCS) {
            c.accept(this); }
    }






    @Override
    public void disabledInit() { robotUtil.stopRobot(this); }
    @Override
    public void disabledPeriodic() { robotUtil.stopRobot(this); }








    @Override
    public void testInit() { TEST_INIT_FUNC.accept(this); }
    @Override
    public void testPeriodic() { TEST_PER_FUNC.accept(this); }
}
