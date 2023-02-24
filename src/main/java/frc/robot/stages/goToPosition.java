package frc.robot.stages;

import frc.robot.Robot;
import frc.robot.V2d;
import frc.robot.controllers.PIDController;
import frc.robot.functions.driveUtil;
import frc.robot.functions.telemetryUtil;
import frc.robot.functions.telemetryUtil.Tabs;

public class goToPosition extends Stage {

    public V2d targetPos = new V2d();
    public V2d lastErr = new V2d();

    PIDController xpid = new PIDController(0.1, 0.01, 0.0);
    PIDController ypid = new PIDController(0.1, 0.01, 0.0);

    public goToPosition(V2d t) {
        this.targetPos = t;
        this.lastErr = this.targetPos;
    }

    @Override public void init() {
        driveUtil.pid.reset();
        driveUtil.pid.target = Robot.instance.gyro.getYaw();
    }

    @Override
    public boolean run(Robot r) {

        V2d pos = new V2d(r.positionData.pose);

        double temp = pos.x;
        pos.x = pos.y;
        pos.y = temp;

        V2d error = pos.sub(targetPos);

        telemetryUtil.put("Position Error X", error.x, Tabs.DEBUG);
        telemetryUtil.put("Position Error Y", error.y, Tabs.DEBUG);


        if(error.length() < 0.01) {
            if(this.lastErr.sub(error).length() < 0.1) { 
                return true; }
        }
        this.lastErr = error;


        V2d m = new V2d(
            xpid.tick(error.x, Robot.dt, false), 
            ypid.tick(error.y, Robot.dt, false)
            );

        final double c = 0.1;
        if(m.x > c) { m.x = c; }
        if(m.x < -c) { m.x = -c; }

        if(m.y > c) { m.y = c; }
        if(m.y < -c) { m.y = -c; }

        telemetryUtil.put("M.X", m.x, Tabs.DEBUG);
        telemetryUtil.put("M.Y", m.y, Tabs.DEBUG);

        driveUtil.setPowerMechPID(r, m.x, m.y, 0.8);
        // driveUtil.setPowerMechPID(r, 0, 0, 0.5);

        return false;


    }
}
