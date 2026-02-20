package org.firstinspires.ftc.teamcode.abstractions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoStopper {
    private Servo spinStopper;
    public ServoStopper(OpMode opMode){
        spinStopper = opMode.hardwareMap.get(Servo.class, "spinStopper");
        spinStopper.setDirection(Servo.Direction.FORWARD);
    }
    public void gate_close(){
        spinStopper.setPosition(0.8);
    }
    public void gate_open() {
        spinStopper.setPosition(0.6);
    }

}
