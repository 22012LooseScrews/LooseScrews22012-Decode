package abstraction.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class driveTrain {
    String WhoseTheBoss;
    String WhoseTheBaby;
    OpMode opmode;
    public driveTrain(OpMode op) {
        WhoseTheBoss = "Andrew ";
        WhoseTheBaby = "Arthur";
        this.opmode = op;
    }

    public void telemetryShoot(int times) {
        for (int i = times; i > 0; i--) {
            opmode.telemetry.addLine("Boss: " + WhoseTheBoss);
            opmode.telemetry.addLine("Baby: " + WhoseTheBaby);
        }
    }
}
