package abstraction.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class SpinServo {
    private CRServo spinServo;
    public SpinServo(OpMode opMode){
        spinServo = opMode.hardwareMap.get(CRServo.class, "spinServo");
        spinServo.setDirection(CRServo.Direction.FORWARD);
    }
    public void spin_forward_2(){
        spinServo.setPower(1.0);
    }
    public void spin_backward() {
        spinServo.setPower(-1.0);
    }
    public void spin_stop(){
        spinServo.setPower(0);
    }
//    public void spin_auto(){
//        myStopwatch.reset();
//            if (myStopwatch.seconds() < 0.5) {
//                spin_forward();
//            }
//            myStopwatch.reset();
//            while (myStopwatch.seconds() < 0.5){
//                spin_stop();
//            }
//            myStopwatch.reset();
//            while (myStopwatch.seconds() < 0.5) {
//                spin_forward();
//            }
//            myStopwatch.reset();
//            while (myStopwatch.seconds() < 0.5){
//                spin_stop();
//            }
//            myStopwatch.reset();
//            while (myStopwatch.seconds() < 0.5) {
//                spin_forward();
//        }
//
//    }
}
