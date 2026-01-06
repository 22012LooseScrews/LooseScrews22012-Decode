package org.firstinspires.ftc.teamcode.abstractions;
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
    public void spin_forward_1() {spinServo.setPower(0.75);}
    public void spin_stop(){
        spinServo.setPower(0);
    }
}
