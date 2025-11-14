package abstraction.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
/*import com.qualcomm.robotcore.hardware.DcMotorSimple;*/

public class SpinServo {
    private CRServo spinServo;
    public SpinServo(OpMode opMode){
        spinServo = opMode.hardwareMap.get(CRServo.class, "spinServo");
        spinServo.setDirection(CRServo.Direction.FORWARD);
    }
    public void spin_forward(){
        spinServo.setPower(1);
    }
    public void spin_backward(){
        spinServo.setPower(-1);
    }
    public void spin_stop(){
        spinServo.setPower(0);
    }
}
