package abstraction.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class OuttakeMotor {
    private DcMotorEx outtakeMotor;

    public OuttakeMotor(OpMode opMode) {
        outtakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setVelocityPIDFCoefficients(
                17.5,
                0.5,
                1,
                10
        );
    }
    public void outtake_close() {
        outtakeMotor.setVelocity(2100);
    }
    public void outtake_far() {
        outtakeMotor.setVelocity(2650);
    }
    public void outtake_stop() {
        outtakeMotor.setVelocity(0);
    }
    public double getVel(){
        return outtakeMotor.getVelocity();
    }
}
