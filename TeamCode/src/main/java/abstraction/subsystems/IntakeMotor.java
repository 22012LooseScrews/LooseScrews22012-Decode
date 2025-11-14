package abstraction.subsystems;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class IntakeMotor {
    private static DcMotorEx intakeMotor;

    public IntakeMotor() {
        IntakeMotor.intakeMotor = intakeMotor;
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
