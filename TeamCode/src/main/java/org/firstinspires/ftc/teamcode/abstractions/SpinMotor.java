package org.firstinspires.ftc.teamcode.abstractions;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class SpinMotor {
    private DcMotor spinMotor;
    public SpinMotor(OpMode opMode){
        spinMotor = opMode.hardwareMap.get(DcMotor.class, "spinMotor");
        spinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void spin_forward(){
        spinMotor.setPower(1);
    }
    public void spin_backward(){
        spinMotor.setPower(-1);
    }
    public void spin_stop(){
        spinMotor.setPower(0);
    }
}
