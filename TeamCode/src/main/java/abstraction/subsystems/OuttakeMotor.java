package abstraction.subsystems;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class OuttakeMotor {
    private static DcMotorEx outtakeMotor;

    public OuttakeMotor() {
        OuttakeMotor.outtakeMotor = outtakeMotor;
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void outtake_close(){
        outtakeMotor.setPower(0.75);
    }
    public void outtake_far(){
        outtakeMotor.setPower(0.95);
    }
    public void outtake_stop(){
        outtakeMotor.setPower(0);
    }

}
