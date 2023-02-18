package frc.robot.behaviours;

import java.util.ArrayList;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.V2d;
import frc.robot.functions.driveUtil;
import frc.robot.stages.AutoStages;
import frc.robot.stages.Stage;
import frc.robot.stages.goToPosition;
import frc.robot.stages.AutoStages.Flymer;
import frc.robot.functions.telemetryUtil;
import frc.robot.functions.telemetryUtil.Tabs;

public class AutoBehaviours {

    public static int stage = -1;
    public static ArrayList<Stage> stages = new ArrayList<Stage>();


    public static Consumer<Robot> autoPeriodic = r -> {

        if(stage == -1) { stage++; stages.get(0).init(); }

        if(stage == stages.size()) {
            SmartDashboard.putBoolean("Auto running", false);
            BehaviourUtil.stopDrive.accept(r);
            return;
        }

        Boolean x = stages.get(stage).run(r);

        if(x) {
            stage++;
            stages.get(stage).init();
        }

        SmartDashboard.putBoolean("Auto running", true);
    };

    public static void resetAuto(Robot r) {

        r.gyro.globGyroscope.reset();
        stages.clear();
        stage = 0;
    }






    public static Consumer<Robot> turn180init = r -> {
        resetAuto(r);
        stages.add(new AutoStages.goTo180());
    };

    public static Consumer<Robot> alignTagTrigInit = r -> {
        resetAuto(r);

        stages.add(new AutoStages.goToAprilTagTrig(3, 24));
    };

    public static Consumer<Robot> goToPosition0Init = r -> {
        resetAuto(r);

        stages.add(new goToPosition(new V2d(0, 0), r));
    };


    public static double start = Robot.timeSinceInit;
    public static boolean firstFrame = true;
    public static Consumer<Robot> initWeek0 = r -> {
        
    };

    public static Consumer<Robot> periodicWeek0 = r -> {


        if(firstFrame) {

            start = Robot.timeSinceInit;
           
            Robot.flymer.start();
            Robot.flymer.reset();
            telemetryUtil.debugLog("W0 Auto initialized", Tabs.ROBOT);

            firstFrame = false;
        }


        telemetryUtil.put("W0 start", start, Tabs.ROBOT);
        telemetryUtil.put("W0 time", Robot.timeSinceInit, Tabs.ROBOT);
        telemetryUtil.put("W0 Flymer time", Robot.timeSinceInit, Tabs.ROBOT);

        if ((Robot.timeSinceInit - start) < 5){

            driveUtil.setPowerUniform(r.drive, -.3);
            telemetryUtil.put("W0 Auto on", true, Tabs.ROBOT);
            return;
        }
        else {

            telemetryUtil.put("W0 Auto on", false, Tabs.ROBOT);
            driveUtil.stop(r.drive);
        }
    };

}

