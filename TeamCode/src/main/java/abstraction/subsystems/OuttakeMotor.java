package abstraction.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class OuttakeMotor {
    private DcMotorEx outtakeMotor;

    public OuttakeMotor(OpMode opMode) {
    //    this.Opmode = opMode;
        outtakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void outtake_close() {
        outtakeMotor.setPower(0.75);
    }

    public void outtake_far() {
        outtakeMotor.setPower(0.95);
    }

    public void outtake_stop() {
        outtakeMotor.setPower(0);
    }
}
