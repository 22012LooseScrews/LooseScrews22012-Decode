package abstraction.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;

public class IntakeMotor {
    private static DcMotor intakeMotor;

    public IntakeMotor(OpMode opMode) {
        intakeMotor = opMode.hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void intake_intake(){
        intakeMotor.setPower(1);
    }
    public void intake_outtake(){
        intakeMotor.setPower(-1);
    }
    public void intake_stop(){
        intakeMotor.setPower(0);
    }

}
