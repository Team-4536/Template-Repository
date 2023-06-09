package frc.robot.behaviours;

import java.util.function.Consumer;

import frc.robot.Robot;
import frc.robot.V2d;
import frc.robot.stages.goToAngle;
import frc.robot.stages.goToAprilTagTrig;
import frc.robot.stages.goToPosition;

public class AutoBehaviours {


    public static Consumer<Robot> turn180init =
        r -> { r.autoData.stages.add(new goToAngle(180, 1)); };

    public static Consumer<Robot> alignTagTrigInit =
        r -> { r.autoData.stages.add(new goToAprilTagTrig(3, 24)); };

    public static Consumer<Robot> goToPosition0Init =
        r -> { r.autoData.stages.add(new goToPosition(new V2d(0, 0), r)); };
}

