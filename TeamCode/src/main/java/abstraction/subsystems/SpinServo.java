package abstraction.subsystems;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.Timer;
/*import com.qualcomm.robotcore.hardware.DcMotorSimple;*/


public class SpinServo {
    private CRServo spinServo;
    private boolean opModeIsActive() {
        return true;
    }
    ElapsedTime myStopwatch;
    public SpinServo(OpMode opMode){
        spinServo = opMode.hardwareMap.get(CRServo.class, "spinServo");
        spinServo.setDirection(CRServo.Direction.FORWARD);
    }
//    public void spin_forward()  {
//        /*myStopwatch.reset();*/
//        spinServo.setPower(1.0);
//    }

    public void spin_forward_2(){
        spinServo.setPower(1.0);
    }

    public void spin_backward() {
//      myStopwatch.reset();
//      while (myStopwatch.seconds() < 0.5) {
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
