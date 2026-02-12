package org.firstinspires.ftc.teamcode.abstractions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeServo {
    private Servo outtakeServo;
    public OuttakeServo(OpMode opMode){
        outtakeServo = opMode.hardwareMap.get(Servo.class, "outtakeServo");
        outtakeServo.setDirection(Servo.Direction.FORWARD);
    }
    public void outtake_shift_close(){
        outtakeServo.setPosition(0.95);
    }
    public void outtake_shift_far() {
        outtakeServo.setPosition(0.65);
    }

}
